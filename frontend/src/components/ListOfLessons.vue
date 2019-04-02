<template>
  <div class="user-panel">
    <div class= "header">Statistics</div>
    <div class="user-table">
      <b-table striped hover :items="data" :fields="fields"></b-table>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  props: ["dataForCreate"],
  data() {
    return {
      fields: {
        time: {
          key: "dateTime",
          label: "Time",
          sortable: true
        },
        lesson: {
          key: "course.name",
          label: "Course",
          sortable: true
        },
        teacher: {
          key: "teacher.surname",
          label: "Teacher",
          sortable: true
        },
        room: {
          key: "room",
          label: "Room",
          sortable: true
        },
        attendance: {
          key: "attendance",
          label: "Attendance",
          sortable: true
        }
      },
      info: this.dataForCreate,
      data: []
    };
  },

  created: function() {
    this.getStatistics(this.info);
  },

  methods: {
    getStatistics: function(date) {
      const AXIOS = axios.create({
        baseURL: "http://134.209.227.130:8080",
        headers: {
          Authorization: "JWT " + localStorage.getItem("token"),
          "Content-Type": "application/json; charset=UTF-8",
          "Access-Control-Allow-Origin": "*"
        }
      });
      AXIOS.get("/lesson/daily", { params: { date } }).then(response => {
        this.data = response.data;
      });
    }
  }
};
</script>
