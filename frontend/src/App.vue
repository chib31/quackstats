<template>
  <v-app>
    <v-app-bar app dark class="indigo" v-if="team.name">
      <v-avatar tile class="mr-2">
        <v-img :src="require('@/assets/images/plastics_logo_no_text.png')"/>
      </v-avatar>
      <v-toolbar-title>
        {{ team.name }}
      </v-toolbar-title>
      <v-spacer/>
      <v-toolbar-items>
        <v-btn v-for="item of menuItems" :key="item.to" :to=item.to> {{ item.label }} </v-btn>
      </v-toolbar-items>
    </v-app-bar>
    <v-main>
      <router-view v-if="team.name"/>
      <v-card v-else class="mx-auto my-12 text-center pa-4" max-width="500">
        <div v-if="!connectFailed">
          <div class="loader"/>
          <h3> {{ 'Loading... (' + connectAttempts + '/' + maxConnectAttempts + ')' }} </h3>
        </div>
        <div v-else>
          <h1>Connection failed</h1>
          <p>Sorry, there was a problem connecting to the database :(</p>
          <p>Please try again. If the problem persists, let Charlie know.</p>
          <v-btn @click="fetchTeam">Retry</v-btn>
        </div>
      </v-card>
    </v-main>
  </v-app>
</template>

<script>
import config from "./config";
import axios from 'axios';

export default {
  name: 'App',
  data() {
    return {
      team: {},
      connectAttempts: 0,
      maxConnectAttempts: 5,
      connectFailed: false,
      menuItems: [
        { to: "/home", label: "Home" },
        { to: "/results", label: "Results" },
        { to: "/players", label: "Players" },
        { to: "/stats", label: "Stats" }
      ]
    }
  },
  async mounted() {
    await this.fetchTeam();
  },
  methods: {
    async fetchTeam() {
      this.connectFailed = false;
      this.connectAttempts = 0;
      while(!this.team.name && this.connectAttempts <= this.maxConnectAttempts) {
        const endpoint = 'team/' + config.TEAM_UUID;
        const url = config.BASE_URL + '/' + endpoint;
        await axios.get(url, { timeout: 10000 }).then(response => {
          this.team = response.data;
        }, error => {
          this.connectAttempts = this.connectAttempts + 1;
          if(this.connectAttempts > this.maxConnectAttempts) {
            console.error('Max connection attempts exceeded!\n' + error);
            this.connectFailed = true;
          }
        });
      }
    }
  }
}
</script>

<style>
  html { overflow-y: auto !important }

  .loader {
    width: 100px;
    height: 100px;
    margin: 100px auto 100px auto;
    border: 16px solid #ffe6eb;
    border-top: 16px solid #fd6c9e;
    border-radius: 50%;
    -webkit-animation: spin 1.5s linear infinite;
    animation: spin 1.5s linear infinite;
  }

  @keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
  }
</style>
