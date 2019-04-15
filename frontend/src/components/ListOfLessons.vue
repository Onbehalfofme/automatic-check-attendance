<template>
  <div class="user-panel">
    <div class="header">My attendance statistics</div>
    <br />
    <br />
    <b-input-group>
      <b-form-input
        v-model="filter"
        placeholder="Type to Search"
      ></b-form-input>
    </b-input-group>
    <div class="user-table">
      <b-table
        @filtered="onFiltered"
        :filter="filter"
        striped
        hover
        :items="data"
        :fields="fields"
      >
        <template slot="actions" slot-scope="row">
          <b-button size="m" @click="row.toggleDetails">
            {{ row.detailsShowing ? "Hide" : "Show" }} Details
          </b-button>
        </template>

        <template slot="row-details" slot-scope="row">
          <b-card>
            <ul>
              <p>
                You can notify the professor in advance about your future
                absence.
              </p>
              <p>
                You can send an explanatory letter to your professor, if you did
                not attend the lesson for a good reason.
              </p>
              <p>You can dispute your attendance mark if it is unfair.</p>
              <textarea type="text" class="input-group">
Please write here your letter to the professor</textarea
              >
              <br />
              If necessary, you can upload the justification:
              <div class="input-default-wrapper mt-3">
                <span class="input-group-text mb-3" id="input1">Upload</span>
                <input
                  type="file"
                  id="file-with-current"
                  class="input-default-js"
                />
                <label
                  class="label-for-default-js rounded-right mb-3"
                  for="file-with-current"
                  ><span class="span-choose-file"> </span>
                  <div class="float-right span-browse"></div>
                </label>
              </div>
            </ul>
          </b-card>
        </template>

        <template slot="attendance" slot-scope="row">
          {{ row.value ? "PRESENT" : "ABSENT" }}
        </template>
      </b-table>
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
          label: "Date",
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
        },
        buttons: { key: "actions", label: "" }
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
