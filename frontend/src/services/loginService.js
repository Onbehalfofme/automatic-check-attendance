import axios from "axios";

export function login(email, password) {
  let result = null;
  const AXIOS = axios.create({
    baseURL: "http://134.209.227.130:8080",
    headers: {
      "Content-Type": "application/json; charset=UTF-8"
    }
  });

  AXIOS.post("/auth/login/", {
    email: email,
    password: password
  })
    .then(response => {
      localStorage.setItem("token", response.data.token);
      result = response.data.token;
    })
    .catch(() => {
      localStorage.removeItem("token");
    });
  return result;
}

export function logout() {
  localStorage.removeItem("token");
  //location.reload(true);
}
