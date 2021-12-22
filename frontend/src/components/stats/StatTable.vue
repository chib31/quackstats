<template>
  <div>
    <table>
      <thead>
        <tr>
          <th>
            <div class="table-header-container">#</div>
          </th>
          <th v-for="col of pageCols" :key="col.key" style="white-space: nowrap">
            <div class="table-header-container">
              <div v-if="col.sorted">
                <div class="table-header-container">
                  <button class="table-header-button" @click="$emit('sort-col', col)">
                    {{ col.label }}
                  </button>
                </div>
                <div class="table-header-container">
                  <button v-if="col.sorted.priority !== null"
                          @click="$emit('sort-col-reverse', col)"
                          class="table-header-small-button">
                    <v-icon icon="angle-down" v-if="col.sorted.desc"/>
                    <v-icon icon="angle-up" v-else/>
                  </button>
                  <button v-if="col.sorted.priority !== null"
                          @click="$emit('sort-col-shift', col)"
                          class="table-header-small-button">
                    {{ col.sorted.priority }}
                  </button>
                  <button v-if="col.sorted.priority === null"
                          @click="$emit('sort-col-shift', col)"
                          class="table-header-small-button parent-hover-only">
                    {{ nextPriority }}
                  </button>
                  <button v-if="col.sorted.priority !== null"
                          @click="$emit('sort-col-clear', col)"
                          class="table-header-small-button parent-hover-only">
                    <v-icon icon="times"/>
                  </button>
                </div>
              </div>
              <div v-else class="table-header-container no-sort">
                {{ col.label }}
              </div>
            </div>
          </th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(row, idx) of finalisedData" :key="idx">
          <td style="white-space: nowrap"> {{ idx + 1 }} </td>
          <td style="white-space: nowrap" v-for="col of finalisedCols" :key="col.key"> {{ formattedValue(row, col) }} </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import moment from "moment";

export default {
  name: "StatTable",
  props: {
    finalisedData: Array,
    finalisedCols: Array,
    maxSortCols: Number,
  },
  computed: {
    nextPriority() {
      const currentPrioritisedCols = this.finalisedCols.filter(c => c.sorted && c.sorted.priority).length + 1;
      return Math.min(currentPrioritisedCols, 3);
    }
  },
  data() {
    return {
      pageData: [],
      pageCols: [],
    }
  },
  mounted() {
    this.pageData = [...this.finalisedData];
    this.pageCols = [...this.finalisedCols];
  },
  methods: {
    formattedValue(row, col) {
      const rawValue = row[col.key];
      if (!col.formatter) {
        return rawValue;
      } else if (col.formatter === 'dec2Always') {
        return rawValue === null ? '' : parseFloat(`${rawValue}`).toFixed(2);
      } else if (col.formatter === 'dec2NoTrail') {
        return rawValue === null ? '' : Math.round((parseFloat(`${rawValue}`) + Number.EPSILON) * 100) / 100;
      } else if (col.formatter === 'percent1Always') {
        return rawValue === null ? '' : parseFloat(`${rawValue}`).toFixed(1) + '%';
      } else if (col.formatter === 'date') {
        return moment(new Date(rawValue)).format("Do MMM YYYY");
      } else {
        console.error('Unknown formatter type: ' + col.formatter);
      }
    },
  }
}
</script>

<style scoped>

</style>
