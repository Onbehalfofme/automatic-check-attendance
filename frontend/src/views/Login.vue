<template>
  <div class="login-page">
    <div class="login-panel">
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
          <button
            class="btn btn-secondary"
            v-on:click="sendData(email, password)"
          >
            Log in
          </button>
          <p>{{ token }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { login } from "../services/loginService";
import Vue from "vue";

export default {
  data() {
    return {
      email: "",
      password: "",
      token: ""
    };
  },
  methods: {
    sendData(email, password) {
      this.token = login(email, password);
      this.$emit("ChangeToken", this.token);
    }
  }
};
</script>
