<template>
  <div>

    <v-container>
      <v-btn v-if="mini" icon @click.stop="mini = !mini">
        <v-icon>fa-angle-right</v-icon>
      </v-btn>
      <v-spacer/>
      <v-btn v-if="!mini" icon @click.stop="mini = !mini">
        <v-icon>fa-angle-left</v-icon>
      </v-btn>
    </v-container>

    <v-divider/>

    <div>
      <v-container>
        <v-row>
          <v-col>
            <v-btn icon @click="groupingExpand = !groupingExpand">
              <v-icon>fa-object-group</v-icon>
            </v-btn>
          </v-col>
          <v-col v-if="!mini">
            <h4 @click="groupingExpand = !groupingExpand">Grouping</h4>
          </v-col>
        </v-row>
        <div v-if="groupingExpand">
          <v-row v-for="col of tempRawCols.filter(c => c.grouping)" :key="col.key">
            <v-checkbox v-model="col.grouping.grouped"
                        :label="col.grouping.label"
                        @click="toggleGroup(col)"/>
          </v-row>
        </div>
      </v-container>

      <v-container>
        <v-row>
          <v-col>
            <v-btn icon @click="filtersExpand = !filtersExpand">
              <v-icon>fa-filter</v-icon>
            </v-btn>
          </v-col>
          <v-col v-if="!mini">
            <h4 @click="filtersExpand = !filtersExpand">Filters</h4>
          </v-col>
        </v-row>
        <div v-if="filtersExpand">
          <v-row v-for="col of tempRawCols.filter(c => c.selectFilters)" :key="col.key">
            <v-col>
              <v-select :options="Array.from(col.selectFilters.options)"
                        v-model="col.selectFilters.selected"
                        label="Test"
                        multiple/>
            </v-col>
          </v-row>
        </div>
      </v-container>

      <v-container>
        <v-row>
          <v-col>
            <v-btn icon @click="refineExpand = !refineExpand">
              <v-icon>fa-sliders-h</v-icon>
            </v-btn>
          </v-col>
          <v-col v-if="!mini">
            <h4 @click="refineExpand = !refineExpand">Ranges</h4>
          </v-col>
        </v-row>
        <div v-if="refineExpand">


              <v-row v-for="col of finalisedCols.filter(c => c.rangeFilters)" :key="col.key">
                {{ col.label }}
                <vue-slider v-model="col.rangeFilters.range"
                            :min="col.rangeFilters.min"
                            :max="col.rangeFilters.max"
                            :lazy="true"
                            @change="applyRangeFilters"/>
              </v-row>



        </div>
      </v-container>

      <v-container>
        <v-row>
          <v-col>
            <v-btn icon @click="columnsExpand = !columnsExpand">
              <v-icon>fa-columns</v-icon>
            </v-btn>
          </v-col>
          <v-col v-if="!mini">
            <h4 @click="columnsExpand = !columnsExpand">Columns</h4>
          </v-col>
        </v-row>
        <div v-if="columnsExpand">


          <div class="stat-section">
            <h3 class="stat-section-header">
              Columns
              <button v-on:click="displayColumnOptions = !displayColumnOptions" class="floating circle">
                <v-icon icon="angle-down" v-if="displayColumnOptions"/>
                <v-icon icon="angle-right" v-else/>
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



        </div>
      </v-container>
    </div>

  </div>
</template>

<script>
export default {
  name: "StatOptions",
  props: {
    tempRawCols: Array,
    finalisedCols: Array,
  },
  data() {
    return {
      mini: true,
      groupingExpand: false,
      filtersExpand: false,
      refineExpand: false,
      columnsExpand: false,
    }
  },
  methods: {
    toggleGroup(col) {
      console.log(col.key);
    }
  }
}
</script>

<style scoped>

</style>