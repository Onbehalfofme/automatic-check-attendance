<template>
  <div class="main-page">
    <div class="top-panel">
      <div class="func-panel">
        <template v-if="isStudent">
          <div
            class="student-top"
            v-on:click="openTabAttendance('btn2')"
            :class="{ student_active: activeBtn === 'btn2' }"
          >
            <h2>My attendance</h2>
          </div>
        </template>

        <template v-else>
          <div
            class="top-name"
            v-on:click="openTabAttendance('btn1')"
            :class="{ active: activeBtn === 'btn1' }"
          >
            <h2>Update lesson</h2>
          </div>
          <div
            class="top-name"
            v-on:click="openTabAttendance('btn2')"
            :class="{ active: activeBtn === 'btn2' }"
          >
            <h2>Create lesson</h2>
          </div>
        </template>
      </div>
      <div class="info-panel">
        <div class="top-name" v-on:click="sendLogoutReq()"><h2>Logout</h2></div>
        <div class="top-name"><h2>Settings</h2></div>
        <div class="top-name"><h2>Dashboard</h2></div>
      </div>
    </div>

    <div class="cont">
      <StudentToolBar
        v-if="isStudent && show"
        @getLessInfo="paramOfCall = $event"
        @showContent1="showCont1 = $event"
        @showContent2="showCont2 = $event"
        @closeTab="show = $event"
        @closeToolBar="activeBtn = $event"
      />
      <StudentChart
        v-else-if="isStudent && showCont2"
        :dataForCreateChart="paramOfCall"
        @showContent="showCont2 = $event"
        @hideContent="showCont2 = $event"
      />
      <ListOfLessons
        v-else-if="isStudent && showCont1"
        :dataForCreate="paramOfCall"
        @showContent="showCont1 = $event"
        @hideContent="showCont1 = $event"
      />
      <ToolBar
        v-else-if="!isStudent && show"
        :statusToolBar="statusToolBar"
        @getLessInfo="paramOfCall = $event"
        @closeToolBar="show = $event"
        @closeTab="activeBtn = $event"
        @showContent="showCont = $event"
      />
      <listOfStudents
        v-else-if="!isStudent && showCont"
        :dataForCreate="paramOfCall"
        @hideContent="showCont = $event"
      />
    </div>
  </div>
</template>

<script>
import StudentToolBar from "../components/StudentToolBar.vue";
import ToolBar from "../components/ToolBar.vue";
import jwt_decode from "jwt-decode";
import listOfStudents from "../components/listOfStudents";
import ListOfLessons from "../components/ListOfLessons";
import StudentChart from "../components/StudentChart";

export default {
  name: "home",
  data() {
    return {
      statusToolBar: "",
      activeBtn: "",
      show: false,
      showCont: false,
      showCont1: false,
      showCont2: false,
      isStudent: false,
      paramOfCall: null
    };
  },
  components: {
    listOfStudents,
    ToolBar,
    StudentToolBar,
    ListOfLessons,
    StudentChart
  },
  created() {
    let role = JSON.parse(
      JSON.stringify(jwt_decode(localStorage.getItem("token")))
    ).role;

    this.isStudent = role === "ROLE_STUDENT";
  },
  methods: {
    sendLogoutReq() {
      localStorage.removeItem("token");
      this.$emit("ChangeToken", false);
    },
    openTabAttendance: function(state) {
      if (state === "btn1") {
        if (this.activeBtn === "btn1") {
          this.show = false;
          this.activeBtn = "";
        } else {
          this.statusToolBar = "update";
          this.show = true;
          this.activeBtn = "btn1";
        }
      } else {
        this.show = false;
        if (this.activeBtn === "btn2") {
          this.show = false;
          this.activeBtn = "";
        } else {
          this.statusToolBar = "create";
          this.show = true;
          this.activeBtn = "btn2";
        }
      }
    }
  }
};
</script>
