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

        <template v-else-if="isTeacher">
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

        <template v-else-if="isDoe">
          <div
            class="student-top"
            v-on:click="openTabAttendance('btn2')"
            :class="{ student_active: activeBtn === 'btn2' }"
          >
            <h2>Statistics</h2>
          </div>
        </template>
      </div>
      <div class="info-panel">
        <div class="top-name" v-on:click="sendLogoutReq()"><h2>Logout</h2></div>
        <div
          class="top-name"
          v-on:click="
            show = false;
            activeBtn = '';
            showCont = false;
          "
        >
          <h2>Dashboard</h2>
        </div>
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
        v-else-if="isTeacher && show"
        :statusToolBar="statusToolBar"
        @getLessInfo="paramOfCall = $event"
        @closeToolBar="show = $event"
        @closeTab="activeBtn = $event"
        @showContent="showCont = $event"
      />
      <ListOfLessonsForUpdate
        v-else-if="isTeacher && showCont"
        :dataForCreate="paramOfCall"
        @hideContent="showCont = $event"
      />
      <DoeToolBar
        v-else-if="isDoe && show"
        @getLessInfo="paramOfCall = $event"
        @showContent1="showCont1 = $event"
        @showContent2="showCont2 = $event"
        @closeTab="show = $event"
        @closeToolBar="activeBtn = $event"
      />
      <TeacherChart
        v-else-if="isDoe && showCont2"
        :dataForCreateChart="paramOfCall"
        @showContent="showCont2 = $event"
        @hideContent="showCont2 = $event"
      />
      <CourseChart
        v-else-if="isDoe && showCont1"
        :dataForCreate="paramOfCall"
        @showContent="showCont1 = $event"
        @hideContent="showCont1 = $event"
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
import ListOfLessonsForUpdate from "../components/ListOfLessonsForUpdate";
import StudentChart from "../components/StudentChart";
import DoeToolBar from "../components/DoeToolBar.vue";
import CourseChart from "../components/CourseChart";
import TeacherChart from "../components/TeacherChart";

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
                statusProf: false,
                isStudent: true,
                isTeacher: false,
                isDoe: false,
                paramOfCall: null
            };
        },
        components: {
            ListOfLessonsForUpdate,
            ToolBar,
            StudentToolBar,
            ListOfLessons,
            StudentChart,
            DoeToolBar,
            CourseChart,
            TeacherChart,
        },
        created: function () {
            let role = JSON.parse(
                JSON.stringify(jwt_decode(localStorage.getItem("token")))
            ).role;
            if (role === "ROLE_STUDENT") {
                this.isStudent = true;
                this.isTeacher = false;
                this.isDoe = false;
            } else if ((role === "ROLE_TA") || (role === "ROLE_PROFESSOR") || (role === "ROLE_ADMIN")) {
                this.isStudent = false;
                this.isTeacher = true;
                this.isDoe = false;
            } else if (role === "ROLE_DOE") {
                this.isStudent = false;
                this.isTeacher = false;
                this.isDoe = true;
            }
        },
        methods: {
            sendLogoutReq() {
                localStorage.removeItem("token");
                this.$emit("ChangeToken", false);
            },
            openTabAttendance: function (state) {
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
                    this.showCont = false;
                    this.showCont1 = false;
                    this.showCont2 = false;
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
