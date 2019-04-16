<template>
    <div class="control-panel">
        <div class="list-option">
            <div class="headline"><h2>Course and teacher statistics</h2></div>
            <br>
            <div><h3>Choose time period:</h3></div>
            <br>
            <input
                    class="date"
                    type="date"
                    lang="en"
                    name="calendar"
                    v-model="date"
            />
            <div id="tool-bar1">
                <div><h3>Course:</h3></div>
                <select class="course" v-model="course">
                    <option v-for="option in courseOptions" v-bind:value="option.name">
                        {{ option.name }}
                    </option>
                </select>
                <button style="width: 55%" v-on:click="getCourseStatistics(new_date)">Course statistics</button>
            </div>
            <div id="tool-bar2">
                <div><h3>Professor/TA:</h3></div>
                <select class="teacher" v-model="teacher">
                    <option v-for="option in teacherOptions" v-bind:value="option.surname">
                        {{ option.name + " " + option.surname }}
                    </option>
                </select>
                <button style="width: 55%" v-on:click="getTeacherStatistics(new_date)">Teacher statistics</button>

            </div>
        </div>
    </div>
</template>

<script>
    import moment from "moment";
    import "bootstrap/dist/css/bootstrap.css";
    import "pc-bootstrap4-datetimepicker/build/css/bootstrap-datetimepicker.css";
    import axios from "axios";
    import * as queryString from "query-string";

    const AXIOS = axios.create({
        baseURL: "http://134.209.227.130:8080",
        headers: {
            Authorization: "JWT " + localStorage.getItem("token"),
            "Content-Type": "application/json; charset=UTF-8",
            "Access-Control-Allow-Origin": "*"
        }
    });

    export default {
        data() {
            return {
                // startDate: moment(new Date()).format("DD.MM.YYYY"),
                // endDate: moment(new Date()).format("DD.MM.YYYY"),
                date: moment(new Date()).format("DD.MM.YYYY"),
                teacherOptions: [],
                taOptions: [],
                courseOptions: [],
                course: "",
                teacher: "",
                new_date: moment(new Date()).format("DD.MM.YYYY"),
                new_week: moment(new Date()).format("DD.MM.YYYY")
            };
        },
        created: async function () {
            await this.getCourses();
            await this.getTeachers();
            console.log(this.taOptions);
        },
        methods: {
            getCourses: async function () {
                await AXIOS.get("/course/all").then(response => {
                    this.courseOptions = response.data;
                });
            },
            getTeachers: async function () {
                await AXIOS.get("/user/search", {
                    params: {role: ["ROLE_TA", "ROLE_PROFESSOR"] },
                    paramsSerializer: (params) => queryString.stringify(params, { arrayFormat: 'repeat' })
                }).then(response => {
                    this.teacherOptions = response.data;
                });
            },


            getCourseStatistics: function () {
                let start_date = moment(this.date).format("DD.MM.YYYY");
                let end_date = moment(this.date).format("DD.MM.YYYY");
                this.$emit("getLessInfo", {
                    after: start_date,
                    before: end_date,
                    course: this.course
                });
                this.$emit("showContent1", true);
                this.$emit("closeTab", false);
                this.$emit("closeToolBar", "");
            },
            getTeacherStatistics: function () {
                let start_date = moment(this.date).format("DD.MM.YYYY");
                let end_date = moment(this.date).format("DD.MM.YYYY");
                this.$emit("getLessInfo", {
                    after: start_date,
                    before: end_date,
                    teacher: this.teacher
                });
                this.$emit("showContent2", true);
                this.$emit("closeTab", false);
                this.$emit("closeToolBar", "");
            }
        }
    };
</script>
