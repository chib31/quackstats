<template>
  <div id="app" class="app-container">
    <div v-if="!connectFailed">
      <div v-if="team.name">
        <navbar :team-name="team.name"/>
        <router-view class="router-container"/>
      </div>
      <div v-else>
        <div class="loader"/>
        <h1 class="splashText"> {{ 'Loading... (' + connectAttempts + '/' + maxConnectAttempts + ')' }} </h1>
      </div>
    </div>
    <div v-else class="splashText">
      <h1><font-awesome-icon icon="plug"/> Connection failed</h1>
      <p>Sorry, there was a problem connecting to the database :(</p>
      <p>Please try again. If the problem persists, let Charlie know.</p>
      <button @click="fetchTeam">Retry</button>
    </div>
  </div>
</template>

<script>
import Navbar from './components/Navbar';
import config from "./config";
import axios from 'axios';

export default {
  name: 'App',
  components: {
    Navbar
  },
  data() {
    return {
      team: {},
      connectAttempts: 0,
      maxConnectAttempts: 5,
      connectFailed: false,
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
        await axios.get(url, { timeout: 1000 }).then(response => {
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

  .splashText {
    text-align: center;
  }
</style>
