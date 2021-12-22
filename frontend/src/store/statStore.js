import Vue from 'vue';
import Vuex from 'vuex';
import * as sg from "supergroup";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    statType: null,
    initialColMeta: [],
    activeColMeta: [],

    initialData: [],

    tableCols: [],
    tableData: [],
  },

  getters: {
    statType: state => {
      return state.statType;
    },
    initialColMeta: state => {
      return state.initialColMeta;
    },
    initialData: state => {
      return state.initialData;
    }
  },

  mutations: {
    SET_STAT_TYPE(state, statType) {
      state.statType = statType;
    },

    SET_INITIAL_COL_META(state, colMeta) {
      state.initialColMeta = colMeta;
    },

    SET_INITIAL_DATA(state, data) {
      state.initialData = data;
    },
  },
  actions: {

    setInitial({commit}, payload) {
      commit('SET_STAT_TYPE', payload.statType);

      let colMeta = payload.cols;
      for (const col of colMeta.filter(c => c.selectFilters)) {
        col.selectFilters.options = new Set(payload.data.map(e => e[col.key]).sort());
      }

      commit('SET_INITIAL_COL_META', colMeta);
      commit('SET_INITIAL_DATA', payload.data);
    },

    updateFilterSelections(state, payload) {
      Vue.set(state.initialColMeta.find(c =>
        c.key === payload.key).selectFilters, "selected", payload.selectFilters);
      this.applySelectionFilters(state);
    },

    applySelectionFilters(state) {
      const filterCols = state.initialColMeta.filter(c => c.selectFilters && c.selectFilters.selected
        && c.selectFilters.selected.length > 0);
      for (const col of filterCols) {
        state.initialData = state.initialData.filter(r => col.selectFilters.selected.includes(r[col.key]));
      }
      // Change to selection filters requires reconstructing group data
      this.applyGrouping(state);
    },

    updateGrouping(state, payload) {
      Vue.set(state.initialColMeta.find(c => c.key === payload.key).grouping, "grouped", payload.group);
      this.applyGrouping(state);
    },

    applyGrouping(state) {
      const groupedCols = state.initialColMeta.filter(c => c.grouping && c.grouping.grouped);
      const groupingActive = groupedCols.length > 0;
      if (groupingActive) {
        const groups = sg.supergroup(state.initialData, groupedCols.map(gs => gs.key)).leafNodes();

        // give group column a meaningful name e.g. Player (Wicket Type, Position)
        const groupTermLabel = groupedCols.map(gs => gs.raw.label).join("/");
        Vue.set(state.initialColMeta.find(e => e.key === 'groupTerm').aggregate, "label",
          this.formatGroupTerm(groupTermLabel));

        // for each row in our supergroup, aggregate the columns that require it
        state.initialData = groups.map(e => this.aggregateGroup(e, state.initialColMeta));
      }

      // Now we know if the table will show aggregate or raw data, we can extract just the col metadata we need
      const pathName = groupingActive ? 'aggregate' : 'raw';
      for (const c of state.initialColMeta) {
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
          state.activeColMeta.push(newCol);
        }
      }

      if (state.statType === 'batting') {
        for(const r of state.initialData) {
          Vue.set(r, 'strikeRate', this.calculateBattingStrikeRate(r.deliveries, r.runs));
          Vue.set(r, 'percentTotal', this.calculatePercentTotal(r.runs, r.teamTotal));
          Vue.set(r, 'average', this.calculateAverage(r.runs, r.wickets));
        }
      } else if (state.statType === 'bowling') {
        for(const r of state.initialData) {
          Vue.set(r, 'economy', this.calculateEconomy(r.deliveries, r.runs));
          Vue.set(r, 'overs', this.calculateOvers(r.deliveries));
          Vue.set(r, 'strikeRate', this.calculateBowlingStrikeRate(r.deliveries, r.wickets));
          Vue.set(r, 'average', this.calculateAverage(r.runs, r.wickets));
          Vue.set(r, 'avgVictimRuns', this.calculateAvgVictimRuns(r.sumVictimRuns, r.wickets));
          Vue.set(r, 'avgVictimPosition', this.calculateAvgVictimPos(r.sumVictimPosition, r.wickets));
        }
      }

      // Now we have all the possible columns we can calculate the range limits
      for(const col of state.activeColMeta) {
        const values = state.initialData.map(r => r[col.key]);
        const min = col.rangeFilters.min ? col.rangeFilters.min : 0;
        const max = col.rangeFilters.max ? col.rangeFilters.max : Math.ceil(Math.max(...values));
        Vue.set(col.rangeFilters, "min", min);
        Vue.set(col.rangeFilters, "max", max);
        Vue.set(col.rangeFilters, "range", [min, max]);
      }
    }
  }
})