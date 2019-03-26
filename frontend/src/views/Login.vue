<template>
  <div class="login-field">
    <img src="./../assets/logo.png" />
    <div class="message-panel">Log in to your account</div>
    <div>
      <input
        class="input-panel"
        type="text"
        v-model="email"
        required
        placeholder="email"
      />
    </div>

    <div>
      <input
        class="input-panel"
        type="password"
        v-model="password"
        required
        placeholder="password"
      />
    </div>
    <div class="button-panel">
      <button class="btn btn-secondary" v-on:click="sendData(email, password)">
        Log in
      </button>
    </div>
  </div>
</template>

<script>
import { login } from "../services/services";

export default {
  name: "login",
  data() {
    return {
      email: "",
      password: "",
      token: ""
    };
  },
  methods: {
    sendData: function(email, password) {
      return (this.token = login(email, password));
    }
  }
};
</script>
