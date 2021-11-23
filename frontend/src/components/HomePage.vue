<template>
  <v-container fluid>
    <h1>About Us</h1>
    <p>
      Formed from a collection of players who met on the internet via social cricket at Archbishop's Park, Plastics XI
      represents the foolhardy members of that group who decided they wanted a bash at proper cricket instead of
      playing with plastic balls.
    </p>
    <p>
      This is our stats website. You can find out more about us on:
    </p>
    <ul>
      <li><a href="https://plasticscc.weebly.com/">Main Website</a></li>
      <li><a href="https://www.instagram.com/plastics_cc/">Instagram</a></li>
      <li><a href="https://twitter.com/plastics_cc">Twitter</a></li>
    </ul>
    <v-card class="ma-4">
      <v-img :src="require('@/assets/images/plastics_background.jpg')"/>
    </v-card>
  </v-container>
</template>

<script>
  export default {
    name: 'HomePage'
  }
</script>

<style>
</style>