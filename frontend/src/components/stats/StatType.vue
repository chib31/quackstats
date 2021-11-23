<template>
    <v-container fluid>
      <v-row class="flex-nowrap">
        <v-col cols="auto" class="pa-0">

          <stat-options v-if="tempRawCols.length > 0"
                        :temp-raw-cols="tempRawCols"
                        :finalised-cols="finalisedCols"
                        v-on:group-toggle="groupToggle"
                        v-on:filter-change="filterChange"
                        v-on:range-change="rangeChange"
                        v-on:col-change="colChange"/>

        </v-col>
        <v-col class="pa-0 overflow-x-auto overflow-y-auto">
          <div v-if="constructedData.length > 0" class="scrollable-table-container pa-2">

            <div v-if="finalisedData.length > 0" class="stat-table-section">
              <stat-table :finalised-data="finalisedData"
                          :finalised-cols="displayedCols"
                          :key="tableKey"
                          v-on:sort-col="sortCol"
                          v-on:sort-col-shift="sortColShift"
                          v-on:sort-col-clear="sortColClear"
                          v-on:sort-col-reverse="sortColReverse"/>
            </div>
            <div v-else>No results</div>

          </div>
          <div v-else>
            No data found
          </div>
        </v-col>
      </v-row>
    </v-container>
</template>

<script>
import StatTable from "@/components/stats/StatTable";
import StatOptions from '@/components/stats/StatOptions';
import Vue from "vue";
import {mean, sum} from "d3-array";
import {Utils} from "@/utils";
import _cloneDeep from 'lodash/cloneDeep';
import statStore from "@/store/statStore";

export default {
  name: "StatType",
  components: {StatTable, StatOptions},
  mixins: [Utils],
  props: {
    statType: String,
    rawData: Array,
    rawCols: Array,
  },
  data() {
    return {
      tempRawCols: [], // construction functions (i.e. grouping and sorting) are applied to these, before simplifying
      constructedCols: [], // simplified version of tempRawCols (with raw / agg removed as required)
      constructedData: [],

      finaliseKey: 0, // incremented to trigger re-render of statFinalisation component

      // Display
      displayGroups: false,
      displayFilters: false,

      //------------From finalisation

      finalisedData: [],
      finalisedCols: [],

      tableKey: 0, // triggers table re-render on change

      // Display
      displayRangeFilters: false,
      displayColumnOptions: false,
    }
  },
  mounted() {
    this.resetStatFromRaw();
  },
  computed: {
    // generates a unique key value based on current select-filters (used to identify changes)
    selectFilterKey() {
      if (this.tempRawCols.length > 0) {
        const str = this.tempRawCols.filter(c => c.selectFilters)
            .map(c => c.selectFilters.selected.join(''))
            .join('-');
        return this.hashCode(str);
      } else {
        return null;
      }
    },
    groupedColCount() {
      return this.tempRawCols.filter(c => c.grouping && c.grouping.grouped).length;
    },
    filteredColCount() {
      return this.tempRawCols.filter(c => c.selectFilters && c.selectFilters.selected.length).length;
    },
    displayedCols() {
      return this.finalisedCols.filter(c => c.display && (c.display.displayed || c.display.alwaysVisible));
    },
    rangedColCount() {
      return this.finalisedCols.filter(c => c.rangeFilters && this.rangeApplied(c.rangeFilters)).length;
    }
  },
  methods: {
    // Restores columns from raw, re-constructs selection filter options
    resetStatFromRaw() {
      const payload = {
        statType: this.statType,
        cols: this.rawCols,
        data: this.rawData
      }
      statStore.commit('setInitial', payload);
    },

    // ---------FINALISATION

    resetRangeValuesAndSorting() {
      this.finalisedData = _cloneDeep(this.constructedData);
      this.finalisedCols = _cloneDeep(this.constructedCols);

      this.sortData();
    },

    applyRangeFilters() {
      this.finalisedData = _cloneDeep(this.constructedData); // we must reset to cached data if a range is changed

      const rangedCols = this.finalisedCols.filter(c => c.rangeFilters && c.rangeFilters.range.length === 2);
      for (const col of rangedCols) {
        this.finalisedData = this.finalisedData.filter(r => this.isWithinRange(r, col));
      }

      this.sortData(); // Range changes require reloading from cache, therefore we must re-sort
    },

    sortData() {
      const sortCols = this.finalisedCols.filter(c => c.sorted && c.sorted.priority && c.sorted.priority > 0);
      const orderedSortCols = sortCols.sort((a, b) => a.sorted.priority - b.sorted.priority);
      this.finalisedData = this.finalisedData.sort((a, b) => this.compareRows(a, b, orderedSortCols));

      this.tableKey = this.tableKey + 1;
    },

    sortCol(clickedCol) {
      // clear all existing priorities
      for (const c of this.finalisedCols.filter(e => e.sorted)) {
        c.sorted.priority = null;
      }

      clickedCol.sorted.priority = 1;

      this.sortData();
    },

    sortColShift(clickedCol) {
      /* There are 3 possible options here:
        1) The clicked col is already maxed out - priority is removed and all other priority cols are upgraded
        2) The clicked col replaces the col that currently has the target priority
        3) The clicked col takes the vacant target priority
       */
      const currentPriority = clickedCol.sorted.priority;

      if (currentPriority === 1) { // already at max priority
        clickedCol.sorted.priority = null;
        for (const c of this.finalisedCols.filter(e => e.sorted && e.sorted.priority)) {
          c.sorted.priority = c.sorted.priority - 1;
        }
      } else {
        let targetPriority;
        if (currentPriority) {
          targetPriority = currentPriority - 1; // col already has a priority, next priority is just -1
        } else {
          // col is not yet prioritised - find next available priority (or MAX, whichever is lower)
          const existingPriorityCount = this.finalisedCols.filter(c => c.sorted && c.sorted.priority).length;
          targetPriority = Math.min(existingPriorityCount + 1, 3);
        }
        const targetCol = this.finalisedCols.find(c => c.sorted && c.sorted.priority === targetPriority);
        if (targetCol) { // is target priority already taken? - if so downgrade (or nullify) target col
          targetCol.sorted.priority = currentPriority;
        }
        clickedCol.sorted.priority = targetPriority; // now upgrade the priority of the clicked col
      }

      this.sortData();
    },

    sortColReverse(clickedCol) {
      clickedCol.sorted.desc = !clickedCol.sorted.desc;
      this.sortData();
    },

    sortColClear(clickedCol) {
      const currentPriority = clickedCol.sorted.priority;

      // upgrade any cols with a lower priority than the clicked col
      for (const c of this.finalisedCols.filter(e => e.sorted && e.sorted.priority
          && e.sorted.priority > currentPriority)) {
        c.sorted.priority = c.sorted.priority - 1;
      }

      clickedCol.sorted.priority = null;

      this.sortData();
    },

    isWithinRange(row, col) {
      const lower = col.rangeFilters.range[0];
      const upper = col.rangeFilters.range[1];
      const value = row[col.key];
      return value >= lower && value <= upper;
    },

    // Determines which of 2 rows should come first based on current sorting rules
    compareRows(rowA, rowB, sortCols) {
      let result = 1;
      for (const col of sortCols) {
        result = this.compareByCol(rowA, rowB, col); // returns 1 if rowA should come first (-1 for rowB)
        if (result !== 0) { // if 0 both rows are equal for this column - continue loop
          return result;
        }
      }
      return 1; // if rows are equal across all sort columns just preserve current order
    },

    // Compares 2 rows by a specific column value
    compareByCol(rowA, rowB, col) {
      const dModifier = col.sorted.desc ? -1 : 1;
      const valA = rowA[col.key];
      const valB = rowB[col.key];

      if ((valA === null && valB === null) || (valA === valB)) {
        return 0; // If both null, or equal, return 0
      } else if (valB === null || valA > valB) {
        return dModifier; // If B is null, or A > B, return modifier
      } else {
        return -dModifier; // Only remaining option is that B is non-null and > than A
      }
    },

    toggleDisplay(col) {
      col.display.displayed = !col.display.displayed;
      this.tableKey = this.tableKey + 1;
    },

    // Given a rangeFilter component, returns true if either the min or max has deviated from the range limit
    rangeApplied(rangeFilters) {
      const minApplied = rangeFilters.range[0] !== rangeFilters.min;
      const maxApplied = rangeFilters.range[1] !== rangeFilters.max;
      return minApplied || maxApplied;
    }

  }
}
</script>

<style scoped>

  div.scrollable-table-container {
    max-height: 82vh;
  }

</style>
