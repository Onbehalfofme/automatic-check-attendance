import axios from "axios";
import Vue from "vue";
import Vuex from "vuex";
import moment from "moment";

Vue.use(Vuex);

export const store = new Vuex.Store({
  state: {
    courses: null,
    students: []
  },
  mutations: {
    getCourses(state) {
      const AXIOS = axios.create({
        baseURL: "http://134.209.227.130:8080",
        headers: {
          Authorization: "JWT " + localStorage.getItem("token"),
          "Content-Type": "application/json; charset=UTF-8",
          "Access-Control-Allow-Origin": "*"
        }
      });

      AXIOS.get("/courses/enrolled").then(response => {
        state.courses = response.data;
      });
    },

    getStudents(state, courseId, type, room, lectureDate, lectureTime) {
      const AXIOS = axios.create({
        baseURL: "http://134.209.227.130:8080",
        headers: {
          Authorization: "JWT " + localStorage.getItem("token"),
          "Content-Type": "application/json; charset=UTF-8",
          "Access-Control-Allow-Origin": "*"
        }
      });
      let dateTime = moment(lectureDate).format("DD.MM.YYYY") + " " + lectureTime;

      AXIOS.post("/lesson/create", { courseId, dateTime, room, type });
    }
  }
});
