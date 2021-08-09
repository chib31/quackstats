<!--<template>-->
<!--  <div>-->
<!--    <b-form>-->
<!--      <b-form-group label="Select players">-->
<!--        <b-form-select v-model="selectedPlayers" :options="allPlayers" multiple :select-size="12"></b-form-select>-->
<!--      </b-form-group>-->
<!--    </b-form>-->
<!--  </div>-->
<!--</template>-->

<!--<script>-->
<!--import config from "@/config";-->
<!--import axios from 'axios'-->

<!--export default {-->
<!--  name: "Scorecard",-->
<!--  data() {-->
<!--    return {-->
<!--      allPlayers: [],-->
<!--      selectedPlayers: [],-->
<!--      fixture: {-->
<!--        date: '17-06-2020',-->
<!--        location: 'Middlesex CC',-->
<!--        homeTeam: {-->
<!--          name: 'Railway Taverners',-->
<!--          squad: [-->
<!--            {name: 'Steve'},-->
<!--            {name: 'Timmy'},-->
<!--            {name: 'Rachid'},-->
<!--            {name: 'Unknown'},-->
<!--            {name: 'Unknown'},-->
<!--            {name: 'Unknown'},-->
<!--            {name: 'J Kaillioiiop'},-->
<!--            {name: 'Boomer'},-->
<!--            {name: 'Big D'},-->
<!--            {name: 'Unknown'},-->
<!--            {name: 'Unknown'},-->
<!--          ],-->
<!--        },-->
<!--        awayTeam: 'Plastics CC',-->
<!--        squad: [-->
<!--          {name: 'C Bradbury'},-->
<!--          {name: 'A Webster'},-->
<!--          {name: 'M Davies'},-->
<!--          {name: 'M Webb'},-->
<!--          {name: 'P Oliver'},-->
<!--          {name: 'Saril'},-->
<!--          {name: 'I Whitfield'},-->
<!--          {name: 'N Whitfield'},-->
<!--          {name: 'B Soomra'},-->
<!--          {name: 'J Jamieson'},-->
<!--          {name: 'J Doy'},-->
<!--        ]-->

<!--      }-->
<!--    }-->
<!--  },-->
<!--  mounted() {-->
<!--    this.allPlayers = this.fetchPlayers();-->
<!--  },-->
<!--  methods: {-->
<!--    async fetchPlayers() {-->
<!--      const requestUrl = config.BASE_URL + '/api/playerList';-->
<!--      const requestConfig = {-->
<!--        auth: {username: config.API_USER, password: config.API_PASSWORD},-->
<!--        timeout: config.REQUEST_TIMEOUT-->
<!--      }-->

<!--      try {-->
<!--        const response = await axios.get(requestUrl, requestConfig);-->
<!--        this.allPlayers = response.data;-->

<!--      } catch (error) {-->
<!--        if (error.request) {-->
<!--          // Request not received-->
<!--          console.log(error.message);-->
<!--        } else {-->
<!--          // Connected to backend received error message (or other)-->
<!--          console.log('Unexpected error');-->
<!--          console.log(error.message);-->
<!--          alert("An error has occurred whilst fetching the stats. This shouldn't happen - please tell Charlie!");-->
<!--        }-->
<!--      }-->
<!--    }-->
<!--  }-->
<!--}-->
<!--</script>-->

<!--<style scoped>-->

<!--</style>-->