<template>
  <div class="main-page">
    <div class="top-panel">
      <div class="func-panel">
        <div
          class="top-name"
          v-on:click="openTabAttendance('btn1')"
          :class="{ active: activeBtn === 'btn1' }"
        >
          <h2>Statistics</h2>
        </div>
        <div
          class="top-name"
          v-on:click="openTabAttendance('btn2')"
          :class="{ active: activeBtn === 'btn2' }"
        >
          <h2>Check attendance</h2>
        </div>
      </div>
      <div class="info-panel">
        <div class="top-name" v-on:click="sendLogoutReq()"><h2>Logout</h2></div>
        <div class="top-name"><h2>Settings</h2></div>
        <div class="top-name"><h2>Dashboard</h2></div>
      </div>
    </div>

    <div class="cont">
      <listOfStudents
        v-if="showCont"
        :dataForCreate="paramOfCall"
        @hideContent="showCont = $event"
      />
      <ToolBar
        v-if="show"
        @getLessInfo="paramOfCall = $event"
        @showContent="showCont = $event"
      />
    </div>
  </div>
</template>

<script>
import ToolBar from "@/components/ToolBar.vue";
import { logout } from "../services/loginService";
import listOfStudents from "../components/listOfStudents";

export default {
  data() {
    return {
      activeBtn: "",
      show: false,
      showCont: false,
      paramOfCall: {}
    };
  },
  components: { listOfStudents, ToolBar },
  methods: {
    sendLogoutReq() {
      let some = "";
      logout();
      this.$emit("ChangeToken", some);
    },
    openTabAttendance: function(state) {
      if (state === "btn1") {
        if (this.activeBtn === "btn1") {
          this.activeBtn = "";
        } else {
          this.show = false;
          this.activeBtn = "btn1";
        }
      } else {
        this.show = false;
        if (this.activeBtn === "btn2") {
          this.show = false;
          this.activeBtn = "";
        } else {
          this.show = true;
          this.activeBtn = "btn2";
        }
      }
    }
  }
};
</script>
