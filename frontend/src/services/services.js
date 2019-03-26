import axios from "axios";

export function login(email, password) {
  const instance = axios.create({
    baseURL: "http://134.209.227.130",
    headers: {
      "Content-Type": "application/json; charset=UTF-8",
      "Access-Control-Allow-Origin": "*"
    }
  });

  instance
    .post("/auth/login/", {
      email: email,
      password: password
    })
    .then(response => {
      localStorage.setItem("token", response.data.token);
    });
  return localStorage.getItem("token");
}

export function logout() {
  localStorage.removeItem("token");
}
