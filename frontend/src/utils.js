import Vue from "vue";
import {mean, sum} from "d3-array";

export const Utils = {
  methods: {
    toTitleCase(str) {
      return str.replace(
        /\w\S*/g,
        function(txt) {
          return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
        }
      );
    },

    calculateBattingStrikeRate(deliveries, runs) {
      return deliveries > 0 ? (runs * 100) / deliveries : null;
    },

    calculateBowlingStrikeRate(deliveries, wickets) {
      return wickets > 0 ? deliveries / wickets : null;
    },

    calculatePercentTotal(runs, teamTotal) {
      return teamTotal > 0 ? runs * 100 / teamTotal : null;
    },

    calculateAverage(runs, wickets) {
      return wickets < 1 ? null : runs / wickets;
    },

    calculateEconomy(deliveries, runs) {
      return deliveries < 1 ? null : runs * 6 / deliveries;
    },

    calculateOvers(deliveries) {
      const completeOvers = Math.floor(deliveries / 6);
      const extraDeliveries = (deliveries % 6) / 10;
      return completeOvers + extraDeliveries;
    },

    calculateAvgVictimRuns(runs, wickets) {
      return wickets < 1 ? null : runs / wickets;
    },

    calculateAvgVictimPos(position, wickets) {
      return wickets < 1 ? null : position / wickets;
    },

    hashCode(str) {
      return str.split('').reduce((prevHash, currVal) =>
          (((prevHash << 5) - prevHash) + currVal.charCodeAt(0))|0, 0);
    },

    // Converts "a/b/c" into a (b, c)
    formatGroupTerm(groupTerm) {
      const termsCount = (groupTerm.match(/\//g) || []).length + 1; // count number of "/" + 1
      if (termsCount > 1) {
        return groupTerm.replace("/", " (") // replace first "/" with open bracket
            .replace(/\//g, ", ") // replace subsequent "/" with comma
            .concat(")");
      } else return groupTerm;
    },


    // Aggregate a data group based on the aggregation type indicated in the column metadata
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
  }
};
