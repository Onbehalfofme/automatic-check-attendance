<template>
  <div class="control-panel">
    <div class="list-option">
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
      <input class="date" type="number" size="3" min="100" max="500" v-model="lectureRoom"/>

      <div><h3>Date</h3></div>
      <input class="date" type="date" lang="en" name="calendar" v-model="lectureDate"/>

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
      <button v-on:click="getUsers(courseId, lectureType, lectureRoom, lectureDate, lectureTime)">Create</button>
    </div>
  </div>
</template>

<script>
import moment from "moment";
import axios from "axios";

export default {
  data() {
    return {
      options: null,
      options2: [{ text: "LECTURE" }, { text: "TUTORIAL" }, { text: "LAB" }],
      courseId: null,
      lectureType: null,
      lectureDate: new Date(),
      lectureTime: null,
      lectureRoom: null,
      users: null
    };
  },

  created: function() {
    this.getCourses();
  },

  methods: {
    getCourses: function() {
      const AXIOS = axios.create({
        baseURL: "http://134.209.227.130:8080",
        headers: {
          Authorization: "JWT " + localStorage.getItem("token"),
          "Content-Type": "application/json; charset=UTF-8",
          "Access-Control-Allow-Origin": "*"
        }
      });

      AXIOS.get("/courses/enrolled").then(response => {
        this.options = response.data;
      });
    },

    getUsers: function(courseId, type, room, lectureDate, lectureTime) {
      let dateTime = moment(lectureDate).format("MM.DD.YYYY") + " " + lectureTime;
      this.$emit("getLessInfo", {courseId, type, room, dateTime});
      this.$emit("showContent", true);
    }
  }
};
</script>
