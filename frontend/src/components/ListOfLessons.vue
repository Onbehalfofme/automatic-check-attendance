<template>
  <div class="user-panel">
    <div class="header">Statistics</div>
    <div class="user-table">
      <b-form-group label-cols-sm="3" label="Filter" class="mb-0">
        <b-input-group>
          <b-form-input
            v-model="filter"
            placeholder="Type to Search"
          ></b-form-input>
        </b-input-group>
      </b-form-group>

      <b-table
        @filtered="onFiltered"
        :filter="filter"
        striped
        hover
        :items="data"
        :fields="fields"
      ></b-table>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  props: ["dataForCreate"],
  data() {
    return {
      filter: "",
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
      data: [],
      currentPage: 1,
      totalRows: 0
    };
  },

  created: function() {
    this.getStatistics(this.info);
  },
  computed: {
    sortOptions() {
      // Create an options list from our fields
      return this.fields
        .filter(f => f.sortable)
        .map(f => {
          return { text: f.label, value: f.lesson };
        });
    }
  },
  methods: {
    onFiltered(filteredItems) {
      this.totalRows = filteredItems.length;
      this.currentPage = 1;
    },
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
        this.totalRows = this.data.length;
      });
    }
  }
};
</script>
