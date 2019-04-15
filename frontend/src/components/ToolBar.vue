<template>
  <div class="control-panel">
    <div class="list-option" v-if="statusToolBar === 'create'">
      <div class="headline">Lecture creation</div>
      <div><h3>Course</h3></div>
      <select class="course" v-model="courseId">
        <option v-for="option in options" v-bind:value="option.id">
          {{ option.name }}
        </option>
      </select>

      <div><h3>Type</h3></div>
      <select class="type" v-model="lectureType">
        <option v-for="option in options2" v-bind:key="option.value">
          {{ option.text }}
        </option>
      </select>

      <div><h3>Room</h3></div>
      <input
        class="date"
        type="number"
        size="3"
        min="100"
        max="500"
        v-model="lectureRoom"
      />

      <div><h3>Date</h3></div>
      <input
        class="date"
        type="date"
        lang="en"
        name="calendar"
        v-model="lectureDate"
      />

      <div><h3>Time of beginning</h3></div>
      <input
        class="time"
        type="time"
        name="cron"
        value="09:00"
        min="00:01"
        max="23:59"
        v-model="lectureTime"
      />
      <button v-on:click="getUsers('create')">
        Create
      </button>
    </div>

    <div class="list-option" v-if="statusToolBar === 'update'">
      <div class="headline">Lecture creation</div>
      <div><h3>Course</h3></div>
      <select class="course" v-model="courseId">
        <option v-for="option in options" v-bind:key="option.id">
          {{ option.name }}
        </option>
      </select>

      <div><h3>Type</h3></div>
      <select class="type" v-model="lectureType">
        <option v-for="option in options2" v-bind:key="option.value">
          {{ option.text }}
        </option>
      </select>

      <div><h3>Room</h3></div>
      <input
        class="date"
        type="number"
        size="3"
        min="100"
        max="500"
        v-model="lectureRoom"
      />
      <div><h3>Teacher (First name)</h3></div>
      <input
        class="date"
        type="text"
        size="3"
        min="100"
        max="500"
        v-model="lectureTeacher"
      />

      <div><h3>Date (after)</h3></div>
      <input
        class="date"
        type="date"
        lang="en"
        name="calendar"
        v-model="aLectureDate"
      />

      <div><h3>Time (after)</h3></div>
      <input
        class="time"
        type="time"
        name="cron"
        value="09:00"
        min="00:01"
        max="23:59"
        v-model="aLectureTime"
      />

      <div><h3>Date (before)</h3></div>
      <input
        class="date"
        type="date"
        lang="en"
        name="calendar"
        v-model="bLectureDate"
      />

      <div><h3>Time (before)</h3></div>
      <input
        class="time"
        type="time"
        name="cron"
        value="09:00"
        min="00:01"
        max="23:59"
        v-model="bLectureTime"
      />

      <button v-on:click="getUsers('update')">
        Update
      </button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
const AXIOS = axios.create({
  baseURL: "http://134.209.227.130:8080",
  headers: {
    Authorization: "JWT " + localStorage.getItem("token"),
    "Content-Type": "application/json; charset=UTF-8",
    "Access-Control-Allow-Origin": "*"
  }
});

export default {
  props: ["statusToolBar"],
  data() {
    return {
      options: null,
      options2: [{ text: "LECTURE" }, { text: "TUTORIAL" }, { text: "LAB" }],
      courseId: null,
      lectureRoom: null,
      lectureTeacher: null,
      lectureType: null,
      lectureDate: null,
      aLectureDate: null,
      bLectureDate: null,
      lectureTime: null,
      aLectureTime: null,
      bLectureTime: null
    };
  },

  created: function() {
    console.log(localStorage.getItem("token"));
    this.getCourses();
  },

  methods: {
    getCourses: function() {
      AXIOS.get("/course/enrolled").then(response => {
        this.options = response.data;
      });
    },
    getUsers: function(status) {
      if (status === "update") {
        this.$emit("getLessInfo", {
          status: "update",
          course: this.courseId,
          type: this.lectureType,
          room: this.lectureRoom,
          teacher: this.lectureTeacher,
          bLectureDate: this.bLectureDate,
          bLectureTime: this.bLectureTime,
          aLectureDate: this.aLectureDate,
          aLectureTime: this.aLectureTime
        });
      } else {
        this.$emit("getLessInfo", {
          status: "create",
          courseId: this.courseId,
          type: this.lectureType,
          room: this.lectureRoom,
          lectureDate: this.lectureDate,
          lectureTime: this.lectureTime
        });
      }
      this.$emit("showContent", true);
      this.$emit("closeTab", "");
      this.$emit("closeToolBar", false);
    }
  }
};
</script>
