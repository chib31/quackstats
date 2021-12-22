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
      <stat-type :stat-type="statTypeKey"/>
    </v-row>
  </v-container>
</template>

<script>
import StatType from "@/components/stats/StatType";

export default {
  components: {StatType},
  name: "Stats",

  data() {
    return {
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
