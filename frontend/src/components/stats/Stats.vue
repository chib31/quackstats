<template>
  <v-container fluid>
    <v-row>
      <v-col cols="auto">
        <h1 class="header-with-widget"> {{ statType.label + ' Stats' }} </h1>
      </v-col>
      <v-col align-self="center">
        <v-select :options="statTypes" v-model="statType" :clearable="false" class="header-selector"/>
      </v-col>
    </v-row>
    <v-row>
      <v-divider/>
    </v-row>
    <v-row>
      <stat-type v-if="dataReady"
                 :stat-type="statTypeKey"
                 :raw-data="rawData"
                 :raw-cols="rawCols"/>
      <p v-else>Fetching data...</p>
    </v-row>
  </v-container>
</template>

<script>
import columnConfig from '../../columns.json';
import StatType from "@/components/stats/StatType";
import axios from 'axios'
import config from "@/config";

export default {
  components: {StatType},
  name: "Stats",

  data() {
    return {
      dataReady: false,

      rawData: [],
      rawCols: [],

      statType: {key: 'batting', label: 'Batting'},
      teamId: 1,

      statTypes: [
        {key: 'batting', label: 'Batting'},
        {key: 'bowling', label: 'Bowling'},
        {key: 'fielding', label: 'Fielding'}
      ],
    }
  },

  computed: {
    statTypeKey() {
      return this.statType.key;
    }
  },

  watch: {
    statTypeKey() {
      this.onTypeChange();
    }
  },

  mounted() {
    this.onTypeChange();
  },

  methods: {
    onTypeChange() {
      this.fetchRawColumns();
      this.fetchRawData();
    },
    async fetchRawData() {
      this.dataReady = false;
      await axios.get(config.BASE_URL + '/stats/' + config.TEAM_UUID + '/' + this.statType.key)
          .then(response => {
            const result = response.data.stats;
            console.log('Data fetch complete: ' + result.length + ' rows returned');
            this.rawData = result;
            this.dataReady = true;
          }).catch(error => {
            console.log(error);
          });
    },
    fetchRawColumns() {
      if (this.statType.key === 'batting') {
        this.rawCols = [...columnConfig.batting];
      } else if (this.statType.key === 'bowling') {
        this.rawCols = [...columnConfig.bowling];
      } else if (this.statType.key === 'fielding') {
        this.rawCols = [...columnConfig.fielding];
      } else {
        console.error('Unrecognised statType: ' + this.statType.key);
      }
    },
  },

}
</script>

<style scoped>

/* Style the buttons that are used to open the tab content */
.tab button {
  background-color: inherit;
  float: left;
  border: none;
  outline: none;
  cursor: pointer;
  padding: 14px 16px;
  transition: 0.3s;
}

/* Change background color of buttons on hover */
.tab button:hover {
  background-color: #ddd;
}

/* Create an active/current tablink class */
.tab button:active {
  background-color: #ccc;
}

/* Style the tab content */
.tab-content {
  display: none;
  padding: 6px 12px;
  border: 1px solid #ccc;
  border-top: none;
}
</style>
