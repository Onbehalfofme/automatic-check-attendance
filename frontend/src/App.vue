<template>
  <div id="app">
    <login v-if="!token.length" @ChangeToken="token = $event" />
    <home v-if="token.length" @ChangeToken="token = $event" />
    <router-view />
  </div>
</template>

<script>
import Login from "./views/Login.vue";
import Home from "./views/Home.vue";

export default {
  data() {
    return {
      token:
        localStorage.getItem("token") === null
          ? ""
          : localStorage.getItem("token")
    };
  },
  components: { Login, Home }
};
</script>

<style lang="css">
@import "assets/css/style.css";
@import url('https://fonts.googleapis.com/css?family=Quicksand');
</style>
