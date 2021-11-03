<template>
  <div>
    <div class="stat-section">
      <h3 class="stat-section-header">
        Group
        {{ groupedColCount > 0 ? (' (' + groupedColCount + ')') : '' }}
        <button v-on:click="displayGroups = !displayGroups" class="floating circle">
          <font-awesome-icon icon="angle-down" v-if="displayGroups"/>
          <font-awesome-icon icon="angle-right" v-else/>
        </button>
      </h3>
      <div v-if="displayGroups" class="stat-section-content">
        <span v-for="col of tempRawCols.filter(c => c.grouping)"
              :key="col.key"
              @click="toggleGroup(col)">
          <label :for="'group-input-' + col.key"> {{ col.grouping.label }} </label>
          <input :id="'group-input-' + col.key" type="checkbox" style="margin-left: 0; margin-right: 1em"
                 v-model="col.grouping.grouped"/>
        </span>
      </div>
    </div>
    <div class="stat-section">
      <h3 class="stat-section-header">
        Filter
        {{ filteredColCount > 0 ? (' (' + filteredColCount + ')') : '' }}
        <button v-on:click="displayFilters = !displayFilters" class="floating circle">
          <font-awesome-icon icon="angle-down" v-if="displayFilters"/>
          <font-awesome-icon icon="angle-right" v-else/>
        </button>
      </h3>
      <div v-if="displayFilters" class="stat-section-content max-width-600">
        <div v-for="col of tempRawCols.filter(c => c.selectFilters)"
             :key="col.key">
          <h4 class="filter-label"> {{ col.raw.label }} </h4>
          <v-select :options="Array.from(col.selectFilters.options)"
                    v-model="col.selectFilters.selected"
                    multiple/>
        </div>
      </div>
    </div>
    <div v-if="constructedData.length > 0">
      <stat-finalisation :constructed-data="constructedData"
                         :constructed-cols="constructedCols"
                         :key="finaliseKey"/>
    </div>
    <div v-else>
      No data found
    </div>
  </div>
</template>

<script>
import StatFinalisation from "@/components/stats/StatFinalisation";
import * as sg from "supergroup";
import Vue from "vue";
import {mean, sum} from "d3-array";
import {Utils} from "@/utils";
import _cloneDeep from 'lodash/cloneDeep';

export default {
  name: "StatType",
  components: {StatFinalisation},
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
    }
  },
  mounted() {
    this.resetFiltersAndGroups();
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
    }
  },
  watch: {
    selectFilterKey() {
      this.filterAndGroup();
    }
  },
  methods: {
    // Restores columns from raw, re-constructs selection options
    resetFiltersAndGroups() {
      this.tempRawCols = _cloneDeep(this.rawCols);
      this.getSelectionOptions();

      this.filterAndGroup();
    },

    // Resets data from raw, but preserves columns (i.e. preserves filters and groups)
    filterAndGroup() {
      this.constructedData = _cloneDeep(this.rawData);

      this.applySelectionFilters();
      this.applyGrouping();
      this.calculateCompositeCols();

      this.constructSimplifiedCols();

      this.addAllRangeLimits();

      this.finaliseKey = this.finaliseKey + 1;
    },

    getSelectionOptions() {
      for (const col of this.tempRawCols.filter(c => c.selectFilters)) {
        col.selectFilters.options = new Set(this.rawData.map(e => e[col.key]).sort());
      }
    },

    constructSimplifiedCols() {
      this.constructedCols = [];
      const groupingActive = this.tempRawCols.some(e => e.grouping && e.grouping.grouped);
      const pathName = groupingActive ? 'aggregate' : 'raw';
      for (const c of this.tempRawCols) {
        if (c[pathName]) {
          const newCol = {};
          Vue.set(newCol, "key", c.key);
          if (c[pathName].label) {
            Vue.set(newCol, "label", c[pathName].label);
          }
          if (c[pathName].display) {
            Vue.set(newCol, "display", c[pathName].display);
          }
          if (c[pathName].sorted) {
            Vue.set(newCol, "sorted", c[pathName].sorted);
          }
          if (c[pathName].rangeFilters) {
            Vue.set(newCol, "rangeFilters", c[pathName].rangeFilters);
          }
          if (c[pathName].formatter) {
            Vue.set(newCol, "formatter", c[pathName].formatter);
          }
          this.constructedCols.push(newCol);
        }
      }
    },

    toggleGroup(col) {
      if (col.grouping) {
        col.grouping.grouped = !col.grouping.grouped;
        this.filterAndGroup();
      } else {
        console.error('Error: ' + col.key + ' column is not groupable');
      }
    },

    applySelectionFilters() {
      const filterCols = this.tempRawCols.filter(c => c.selectFilters && c.selectFilters.selected
          && c.selectFilters.selected.length > 0);

      for (const col of filterCols) {
        this.constructedData = this.constructedData.filter(r => col.selectFilters.selected.includes(r[col.key]));
      }
    },

    applyGrouping() {
      const groupedCols = this.tempRawCols.filter(c => c.grouping && c.grouping.grouped);
      if (groupedCols.length > 0) {
        const groups = sg.supergroup(this.constructedData, groupedCols.map(gs => gs.key)).leafNodes();

        // give group column a meaningful name e.g. Player (Wicket Type, Position)
        const groupTermLabel = groupedCols.map(gs => gs.raw.label).join("/");
        const groupTermCol = this.tempRawCols.find(e => e.key === 'groupTerm');
        groupTermCol.aggregate.label = this.formatGroupTerm(groupTermLabel);

        // for each row in our supergroup, aggregate the columns that require it
        this.constructedData = groups.map(e => this.aggregateGroup(e, this.tempRawCols));
      }
    },

    aggregateGroup(group, cols) {
      const result = {};
      const groupTerm = this.formatGroupTerm(group.namePath());

      Vue.set(result, 'groupTerm', groupTerm);

      for(const col of cols.filter(c => c.aggregate)) {
        if (col.aggregate.aggType === 'sum') {
          Vue.set(result, col.key, group.aggregate(sum, col.key));
        } else if (col.aggregate.aggType === 'mean') {
          Vue.set(result, col.key, group.aggregate(mean, col.key));
        }
      }
      return result;
    },

    // columns calculated AFTER grouping / selecting
    calculateCompositeCols() {
      switch(this.statType) {
        case 'batting':
          this.calculateBattingCompositeCols(this.constructedData, this.tempRawCols);
          break;
        case 'bowling':
          this.calculateBowlingCompositeCols(this.constructedData, this.tempRawCols);
          break;
        default:
          console.error('Unknown stat type');
      }
    },

    calculateBattingCompositeCols() {
      for(const r of this.constructedData) {
        Vue.set(r, 'strikeRate', this.calculateBattingStrikeRate(r.deliveries, r.runs));
        Vue.set(r, 'percentTotal', this.calculatePercentTotal(r.runs, r.teamTotal));
        Vue.set(r, 'average', this.calculateAverage(r.runs, r.wickets));
      }
    },

    calculateBowlingCompositeCols() {
      for(const r of this.constructedData) {
        Vue.set(r, 'economy', this.calculateEconomy(r.deliveries, r.runs));
        Vue.set(r, 'overs', this.calculateOvers(r.deliveries));
        Vue.set(r, 'strikeRate', this.calculateBowlingStrikeRate(r.deliveries, r.wickets));
        Vue.set(r, 'average', this.calculateAverage(r.runs, r.wickets));
        Vue.set(r, 'avgVictimRuns', this.calculateAvgVictimRuns(r.sumVictimRuns, r.wickets));
        Vue.set(r, 'avgVictimPosition', this.calculateAvgVictimPos(r.sumVictimPosition, r.wickets));
      }
    },

    addAllRangeLimits() {
      /* Min/Max values can be provided in config. Otherwise use 0 --> max existing value */
      const rangeableCols = this.constructedCols.filter(c => c.rangeFilters);
      for(const col of rangeableCols) {
        const values = this.constructedData.map(r => r[col.key]);
        const min = col.rangeFilters.min ? col.rangeFilters.min : 0;
        const max = col.rangeFilters.max ? col.rangeFilters.max : Math.ceil(Math.max(...values));
        col.rangeFilters.min = min;
        col.rangeFilters.max = max;
        col.rangeFilters.range = [min, max];
      }
    },

  }
}
</script>

<style scoped>

</style>
