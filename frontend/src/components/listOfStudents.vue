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
import moment from "moment";

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
      this.info.lectureDate,
      this.info.lectureTime,
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

      AXIOS.patch("/lesson/update/" + this.lessonId, this.selected);
      this.$emit("showContent", false);
    },
    rowSelected(items) {
      let newVersion = [];
      let index;
      for (index = 0; index < items.length; ++index) {
        let dateTime1 = moment(this.info.lectureDate).format("DD.MM.YYYY") + " " + items[index].checkIn;
        let dateTime2 = moment(this.info.lectureDate).format("DD.MM.YYYY") + " " + items[index].checkOut;
        newVersion.push({
          attendance: "PRESENT",
          checkIn: dateTime1,
          checkOut: dateTime2,
          studentId: items[index].id
        });
      }
      this.selected = newVersion;
    },
    getUsers: function(courseId, lectureDate, lectureTime, room, type) {
      const AXIOS = axios.create({
        baseURL: "http://134.209.227.130:8080",
        headers: {
          Authorization: "JWT " + localStorage.getItem("token"),
          "Content-Type": "application/json; charset=UTF-8",
          "Access-Control-Allow-Origin": "*"
        }
      });
      let dateTime = moment(lectureDate).format("DD.MM.YYYY") + " " + lectureTime;
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
