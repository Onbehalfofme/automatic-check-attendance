<template>
  <div class = "main" id="app">
    <!--<div class="login-field">
      <img src="./assets/logo.png">
      <div id="mess" class="message-panel">Log in to your account</div>
        <form id="formss" name="person">
            <div>
              <input v-on:input="email = $event.target.value" class = "input-panel" placeholder="Username"/>
            </div>

            <div>
              <input v-on:input="password = $event.target.value" class = "input-panel" placeholder="Password"/>
            </div>

            <div class="button-panel">
              <button v-on:click="getAllGoods (email, password)" class="btn btn-secondary">Log in</button>
            </div>
        </form>
    </div>-->
    <h1>Log in</h1>
    <!--<form class="login-form">
      <input v-model="email" required placeholder="username"/>
      <input v-model="password" required placeholder="password"/>
    </form>-->
    <button v-on:click="getAllGoods ()" class="btn btn-secondary">Log in</button>
  </div>
</template>

<script>
  import axios from 'axios'
  export const AXIOS = axios.create({
    header: {
      'Access-Control-Allow-Origin': 'http://localhost:8080'
    }
  })
  export default {
    date() {
      return{
        email: '',
        password: ''
      }
    },
    methods: {
      getAllGoods: () => {
        return AXIOS.post('http://134.209.227.130/auth/login', {firstName: 'Fred', lastName: 'Flintstone'})
          .then(console.log("YES"))

        /*return fetch('http://134.209.227.130/auth/login/', {
          method: "POST",
          mode:"no-cors",
          headers: {"Content-type": "application/json; charset=UTF-8"},
          body: JSON.stringify({
            email: email,
            password: password})
        })
          .then(console.log("YES"))*/

        /* return instance.post('', {
          method: 'POST',
          mode:'no-cors',
          body: JSON.stringify({
            username: 'an.boiko@innopolis.ru',
            password: 'maximisverypidor'
          }),
        })
          .then(console.log("YES"))*/
      }
    }
  }
</script>

<style>
  @import url('https://fonts.googleapis.com/css?family=Quicksand:300');

#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;

  margin: 0 0 0 50%;
  width: 50%;
  height: 100%;
  background: white;
  position: fixed;
}
/*
body {
  margin: 0;
  width: 100%;
  height: 100%;
  position: fixed;
  background: url("assets/illustration.jpg") no-repeat center  center fixed;
}

.login-field {
  margin: 10% 0 20%;
  width: 50%;
  height: 60%;
  position: fixed;
}

.message-panel {
  font-family: 'Quicksand', sans-serif;
  font-size: 30px;
  margin: 0 0 30px;
}

.input-panel {
  font-family: 'Quicksand', sans-serif;
  width: 200px;
  border: 0;
  border-bottom: 1px solid;
  margin-bottom: 20px;
}
.btn-secondary {
  margin: 20px 0 0;
  width: 120px;
  background: coral;
}
h1, h2 {
  margin: 20% 0 0;
  font-weight: normal;
}

a {
  color: #42b983;
}*/
</style>
