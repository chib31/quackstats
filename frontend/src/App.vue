<template>
  <div id="app" class="app-container">
    <div v-if="team.name">
      <navbar :team-name="team.name"/>
      <router-view class="router-container"/>
    </div>
    <div v-else>
      Can't connect to quackstats...
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
    }
  },
  mounted() {
    const url = config.BASE_URL + '/team/' + config.TEAM_UUID;
    axios.get(url).then(response => (this.team = response.data));
  },
}
</script>
