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
        },
        checkflag: {
          label: "Check-flag",
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
      this.info.course,
      this.info.bLectureDate,
      this.info.bLectureTime,
      this.info.aLectureDate,
      this.info.aLectureTime,
      this.info.room,
      this.info.type,
      this.info.lectureTeacher
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
        let dateTime1 =
          moment(this.info.lectureDate).format("MM.DD.YYYY") +
          " " +
          items[index].checkIn;
        let dateTime2 =
          moment(this.info.lectureDate).format("MM.DD.YYYY") +
          " " +
          items[index].checkOut;
        newVersion.push({
          attendance: "PRESENT",
          checkIn: dateTime1,
          checkOut: dateTime2,
          studentId: items[index].id
        });
      }
      this.selected = newVersion;
    },
    getUsers: function(
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
        headers: {
          Authorization: "JWT " + localStorage.getItem("token"),
          "Content-Type": "application/json; charset=UTF-8",
          "Access-Control-Allow-Origin": "*"
        }
      });
      let after =
        moment(aLectureDate).format("YYYY-MM-DD") + "T" + aLectureTime;
      let before =
        moment(bLectureDate).format("YYYY-MM-DD") + "T" + bLectureTime;
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
        this.lessonId = response.data.lessonId;
      });
    }
  }
};
</script>
