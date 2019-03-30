import axios from "axios";

export default {
  data() {
    return {
      courses: null
    };
  },

  getCourses() {
    const AXIOS = axios.create({
      baseURL: "http://134.209.227.130:8080",
      headers: {
        Authorization: "JWT " + localStorage.getItem("token"),
        "Content-Type": "application/json; charset=UTF-8",
        "Access-Control-Allow-Origin": "*"
      }
    });

    AXIOS.get("/courses/enrolled").then(response => {
      this.courses = response.data;
      return response.data;
    });

    return this.courses;
  }
};
