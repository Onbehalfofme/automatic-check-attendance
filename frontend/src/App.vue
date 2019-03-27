<template>
  <div id="app">
    <login v-if="false" />
    <home v-if="true" />
  </div>
</template>

<script>
import Login from "@/views/Login.vue";
import Home from "@/views/Home.vue";

export default {
  components: { Login, Home }
};
</script>

<style lang="scss">
@import "assets/css/style.css";
@import url("https://fonts.googleapis.com/css?family=Quicksand:300");
</style>
