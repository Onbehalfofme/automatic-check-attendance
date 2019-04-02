<template>
  <div id="app">
    <div class="login-field">
      <img src="./assets/logo.png"/>
      <div class="message-panel">Log in to your account</div>
      <div>
        <input class="input-panel" type="text" v-model="email" required placeholder="email"/>
      </div>

      <div>
        <input class="input-panel" type="password" v-model="password" required placeholder="password"/>
      </div>
      <div class="button-panel">
        <button class="btn btn-secondary" v-on:click="getAllGoods(email, password)">
          Log in
        </button>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from "axios";

  export default {
    data() {
      return {
        email: "",
        password: ""
      };
    },
    methods: {
      getAllGoods: function(email, password) {
     
        const instance = axios.create({
          baseURL: "localhost:8080",
          headers: {
            "Content-Type": "application/json; charset=UTF-8",
            "Access-Control-Allow-Origin": "*",
          }
        });

        return instance.post('/auth/login/', {
          email: email,
          password: password
        }).then(console.log("YES"));
      }
    }
  };
</script>

<style lang="scss">
  @import url("https://fonts.googleapis.com/css?family=Quicksand:300");

  #app {
    font-family: "Avenir", Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;

    margin: 0 0 0 60%;
    width: 40%;
    height: 100%;
    background: white;
    position: fixed;
  }
  img {
    margin-bottom: 10px;
  }
  body {
    margin: 0;
    width: 100%;
    height: 100%;
    position: fixed;
    background: url("assets/illustration.jpg") no-repeat center center fixed;
  }

  .login-field {
    margin: 10% 0 20%;
    width: 40%;
    height: 60%;
    position: fixed;
  }

  .message-panel {
    font-family: "Quicksand", sans-serif;
    font-size: 25px;
    margin: 0 0 20px;
  }

  .input-panel {
    font-family: "Quicksand", sans-serif;
    width: 200px;
    border: 0;
    font-size: 16px;
    border-bottom: 1px solid;
    margin-bottom: 15px;
  }

  .input-panel:focus {
    outline: 0;
    outline-offset: 0;
  }

  .btn-secondary {
    margin: 16px 0 0;
    width: 120px;
    background: #23c664;
  }

  h1,
  h2 {
    margin: 20% 0 0;
    font-weight: normal;
  }

  a {
    color: #42b983;
  }
</style>
