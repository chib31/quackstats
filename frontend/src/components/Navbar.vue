<template>
  <div class="navbar">
    <router-link to="/home">Home</router-link>
    <router-link to="/results">Results</router-link>
    <router-link to="/players">Players</router-link>
    <router-link to="/stats">Stats</router-link>
  </div>
</template>
<script>
  import config from '../config';
  export default {
    props: {
      teamName: String
    },
    data() {
      return {
        devMode: config.RUN_MODE  === 'development',
      }
    }
  }
</script>

<style>

.navbar {
  background-color: #333;
  overflow: hidden;
}

/* Style the links inside the navigation bar */
.navbar a {
  float: left;
  color: #f2f2f2;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  font-size: 17px;
}

.navbar a:hover {
  background-color: #ddd;
  color: black;
}

</style>
