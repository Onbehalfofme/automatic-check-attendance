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
            class="btn btn-success"
            v-on:click="sendData(email, password)"
          >
            Log in
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import axios from "axios";

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
    sendData(email, password) {
      const AXIOS = axios.create({
        baseURL: "http://134.209.227.130:8080",
        headers: {
          "Content-Type": "application/json; charset=UTF-8"
        }
      });

      AXIOS.post("/auth/login/", {
        email: email,
        password: password
      }).then(response => {
        localStorage.setItem("token", response.data.token);
        this.token = response.data.token;
        this.$emit("ChangeToken", true);
      });
    }
  }
};
</script>
