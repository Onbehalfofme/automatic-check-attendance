<template>
  <div class="user-panel">
    <div class="header">Software project</div>
    <div class="user-table">
      <b-table
        hover
        selectable
        :select-mode="selectMode"
        selectedVariant="success"
        :items="users"
        @row-selected="rowSelected"
        :fields="fields"
      >
        <template slot="checkIn" slot-scope="data">
          <input class="form-control" type="time" v-model="data.item.checkIn" />
        </template>
        <template slot="checkOut" slot-scope="data">
          <input
            class="form-control"
            type="time"
            v-model="data.item.checkOut"
          />
        </template>
      </b-table>
      <button v-on:click="sentStudentAttendance()">Save</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  props: ["dataForCreate"],
  data() {
    return {
      info: this.dataForCreate,
      fields: {
        name: {
          label: "Person first name",
          sortable: true
        },
        surname: {
          label: "Person last name",
          sortable: false
        },
        group: {
          label: "Group",
          sortable: true
        },
        email: {
          label: "Email",
          sortable: true
        },
        checkIn: {
          label: "Check-in",
          sortable: true
        },
        checkOut: {
          label: "Check-out",
          sortable: true
        }
      },
      checkIn: Array,
      checkOut: Array,
      users: {},
      selectMode: "multi",
      selected: [],
      lessonId: ""
    };
  },

  created: function() {
    this.getUsers(
      this.info.courseId,
      this.info.dateTime,
      this.info.room,
      this.info.type
    );
  },

  methods: {
    sentStudentAttendance() {
      const AXIOS = axios.create({
        baseURL: "http://134.209.227.130:8080",
        headers: {
          Authorization: "JWT " + localStorage.getItem("token"),
          "Content-Type": "application/json; charset=UTF-8",
          "Access-Control-Allow-Origin": "*"
        }
      });

      AXIOS.post("/lesson/update/${this.lessonId}", this.selected);
    },
    rowSelected(items) {
      this.selected = items; // {items.email, items.checkIn, items.checkOut}
    },
    getUsers: function(courseId, dateTime, room, type) {
      const AXIOS = axios.create({
        baseURL: "http://134.209.227.130:8080",
        headers: {
          Authorization: "JWT " + localStorage.getItem("token"),
          "Content-Type": "application/json; charset=UTF-8",
          "Access-Control-Allow-Origin": "*"
        }
      });

      AXIOS.post("/lesson/create", { courseId, dateTime, room, type }).then(
        response => {
          this.users = response.data.students;
          this.lessonId = response.data.lessonId;
        }
      );
    }
  }
};
</script>
