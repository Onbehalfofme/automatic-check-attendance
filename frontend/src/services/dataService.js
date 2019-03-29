import axios from "axios";
export function getCourses() {
  const AXIOS = axios.create({
    baseURL: "http://134.209.227.130:8080",
    headers: {
      Authorization: "JWT " + localStorage.getItem("token"),
      "Content-Type": "application/json; charset=UTF-8",
      "Access-Control-Allow-Origin": "*"
    }
  });

  AXIOS.get("/courses/enrolled").then(response => {
    concole.log(response.data);
  });

  return this.newVar;
}
