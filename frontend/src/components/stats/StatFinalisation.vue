<template>
  <div>
    <div class="stat-section">
      <h3 class="stat-section-header">
        Refine
        {{ rangedColCount > 0 ? (' (' + rangedColCount + ')') : '' }}
        <button v-on:click="displayRangeFilters = !displayRangeFilters" class="floating circle">
          <font-awesome-icon icon="angle-down" v-if="displayRangeFilters"/>
          <font-awesome-icon icon="angle-right" v-else/>
        </button>
      </h3>
      <div v-if="displayRangeFilters" class="stat-section-content max-width-600">
        <div v-for="col of finalisedCols.filter(c => c.rangeFilters)"
             :key="col.key">
          <h4 class="filter-label"> {{ col.label }} </h4>
          <vue-slider v-model="col.rangeFilters.range"
                      :min="col.rangeFilters.min"
                      :max="col.rangeFilters.max"
                      :lazy="true"
                      @change="applyRangeFilters"/>
        </div>
      </div>
    </div>
    <div class="stat-section">
      <h3 class="stat-section-header">
        Columns
        <button v-on:click="displayColumnOptions = !displayColumnOptions" class="floating circle">
          <font-awesome-icon icon="angle-down" v-if="displayColumnOptions"/>
          <font-awesome-icon icon="angle-right" v-else/>
        </button>
      </h3>
      <div v-if="displayColumnOptions" class="stat-section-content">
        <span v-for="col of finalisedCols.filter(c => c.display && !c.display.alwaysVisible)"
              :key="col.key"
              @click="toggleDisplay(col)">
          <label :for="'display-input-' + col.key"> {{ col.label }} </label>
          <input :id="'display-input-' + col.key" class="checkbox" type="checkbox" v-model="col.display.displayed"/>
        </span>
      </div>
    </div>
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
</template>

<script>
import StatTable from '@/components/stats/StatTable';
import _cloneDeep from 'lodash/cloneDeep';
import {Utils} from "@/utils";

export default {
  name: "StatFinalisation",
  components: {StatTable},
  mixins: [Utils],
  props: {
    constructedData: Array,
    constructedCols: Array,
  },
  data() {
    return {
      finalisedData: [],
      finalisedCols: [],

      tableKey: 0, // triggers table re-render on change

      // Display
      displayRangeFilters: false,
      displayColumnOptions: false,
    }
  },
  computed: {
    displayedCols() {
      return this.finalisedCols.filter(c => c.display && (c.display.displayed || c.display.alwaysVisible));
    },
    rangedColCount() {
      return this.finalisedCols.filter(c => c.rangeFilters && this.rangeApplied(c.rangeFilters)).length;
    }
  },
  mounted() {
    this.resetRangesAndSorting();
  },
  methods: {
    resetRangesAndSorting() {
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

</style>
