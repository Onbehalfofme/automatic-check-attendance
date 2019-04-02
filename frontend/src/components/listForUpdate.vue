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
          key: "name",
          label: "Person first name",
          sortable: true
        },
        surname: {
          key: "surname",
          label: "Person last name",
          sortable: false
        },
        checkIn: {
          key: "checkIn",
          label: "Check-in",
          sortable: true
        },
        checkOut: {
          key: "checkOut",
          label: "Check-out",
          sortable: true
        },
        attendance: {
          label: "attendance",
          sortable: true
        }
      },
      checkIn: Array,
      checkOut: Array,
      users: {},
      selectMode: "multi",
      selected: [],
      lessonId: "2095"
    };
  },

  created: function() {
    this.getCourse(
      this.info.course,
      this.info.bLectureDate,
      this.info.bLectureTime,
      this.info.aLectureDate,
      this.info.aLectureTime,
      this.info.room,
      this.info.type,
      this.info.lectureTeacher
    );
    this.getUsers(this.lessonId);
  },

  methods: {
    sentStudentAttendance: async function() {
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
        let dateTime1 =
          moment(items[index].date).format("DD.MM.YYYY") +
          " " +
          items[index].checkIn;
        let dateTime2 =
          moment(items[index].date).format("DD.MM.YYYY") +
          " " +
          items[index].checkOut;
        newVersion.push({
          attendance: "PRESENT",
          checkIn: dateTime1,
          checkOut: dateTime2,
          studentId: items[index].studentId
        });
      }
      this.selected = newVersion;
    },
    getCourse: async function(
      course,
      bLectureDate,
      bLectureTime,
      aLectureDate,
      aLectureTime,
      room,
      type,
      teacher
    ) {
      const AXIOS = axios.create({
        baseURL: "http://134.209.227.130:8080",
        Timeout: 1000,
        headers: {
          Authorization: "JWT " + localStorage.getItem("token"),
          "Content-Type": "application/json; charset=UTF-8",
          "Access-Control-Allow-Origin": "*"
        }
      });
      let after = null;
      let before = null;

      if (aLectureTime != null)
        after = moment(aLectureDate).format("DD.MM.YYYY") + " " + aLectureTime;
      if (bLectureTime != null)
        before = moment(bLectureDate).format("DD.MM.YYYY") + " " + bLectureTime;

      AXIOS.get("/lesson/search", {
        params: {
          course,
          after,
          before,
          room,
          type,
          teacher
        }
      }).then(response => {
        this.lessonId = response.data[0].id;
      });
    },
    getUsers: async function(id) {
      const AXIOS = axios.create({
        baseURL: "http://134.209.227.130:8080",
        Timeout: 1000,
        headers: {
          Authorization: "JWT " + localStorage.getItem("token"),
          "Content-Type": "application/json; charset=UTF-8",
          "Access-Control-Allow-Origin": "*"
        }
      });
      AXIOS.get("/lesson/" + id).then(response => {
        this.users = response.data.students;
        let newVersion = [];
        let index;
        for (index = 0; index < this.users.length; ++index) {
          let dateTime1 = this.users[index].checkIn.split(" ");
          let dateTime2 = this.users[index].checkOut.split(" ");
          newVersion.push({
            attendance: "PRESENT",
            checkIn: dateTime1[1],
            checkOut: dateTime2[1],
            date: dateTime1[0],
            studentId: this.users[index].student.id,
            name: this.users[index].student.name,
            surname: this.users[index].student.surname
          });
        }
        this.users = newVersion;
      });
    }
  }
};
</script>
