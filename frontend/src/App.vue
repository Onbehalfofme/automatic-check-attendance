<template lang="eng">
  <div id="app">
    <login v-if="!token" @ChangeToken="token = $event" />
    <home v-if="token" @ChangeToken="token = $event" />
    <router-view />
  </div>

</template>

<script>
    import Login from "./views/Login.vue";
    import Home from "./views/Home.vue";

    export default {
        data() {
            return {
                token: false
            };
        },
        components: {Login, Home},
        created() {
            this.token = localStorage.getItem("token") != null;
        }
    };
</script>

<style lang="css">
    @import "assets/css/style.css";
    @import url('https://fonts.googleapis.com/css?family=Quicksand');
</style>
