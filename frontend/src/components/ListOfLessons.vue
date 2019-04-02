<template>
    <div class="user-panel">
        <div id="myTable"></div>
    </div>

</template>

<script>
    import axios from "axios";

    export default {
        props: ["dataForCreate"],
        data() {
            return {
                info: this.dataForCreate,
                data: []
            };
        },

        created: function () {
            this.getStatistics(
                this.info
            );
            this.buildTable();
        },

        methods: {
            buildTable() {
                let table;
                table = "<table class='table'><thead><tr>";
                table += "<th>Time</th>";
                table += "<th>Lesson</th>";
                table += "<th>Teacher</th>";
                table += "<th>Room</th>";
                table += "<th>Attendance</th></tr></thead><tbody>";

                for (let i = 0; i < this.data.length; i++) {
                    if (this.data[i].attendance === null) {
                        table += "<tr class=\"warning\">";
                    } else if (this.data[i].attendance === "PRESENT")
                        table += "<tr class=\"success\">";
                    else table += "<tr class=\"danger\">";

                    table += "<td>" + this.data[i].dateTime + "</td>";
                    table += "<td>" + this.data[i].course.name + " (" + this.data[i].type.toLowerCase() + ")" + "</td>";
                    table += "<td>" + this.data[i].teacher.name + " " + this.data[i].teacher.surname + "</td>";
                    table += "<td>" + this.data[i].room + "</td>";

                    if (this.data[i].attendance === null) {
                        table += "<td>REASONABLE</td></tr>";
                    } else if (this.data[i].attendance === "PRESENT")
                        table += "<td>PRESENT</td></tr>";
                    else table += "<td>ABSENT</td></tr>";
                }

                table += "</tbody></table>";
                document.getElementById("myTable").innerText = table;
            },
            getStatistics: function (date) {
                const AXIOS = axios.create({
                    baseURL: "http://134.209.227.130:8080",
                    headers: {
                        Authorization: "JWT " + localStorage.getItem("token"),
                        "Content-Type": "application/json; charset=UTF-8",
                        "Access-Control-Allow-Origin": "*"
                    }
                });

                console.log(date);
                AXIOS.get("/lesson/daily", {
                    params: {date}
                }).then(
                    response => {
                        this.data = response.data;
                    }
                );
            }
        }
    };
</script>

