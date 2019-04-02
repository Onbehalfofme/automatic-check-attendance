<template>
    <div class="control-panel">
        <div class="list-option">
            <div class="headline"><h2>My attendance statistics</h2></div>
            <div><h3>Date</h3></div>
            <input class="date" type="date" lang="en" name="calendar" v-model="lectureDate"/>

            <button v-on:click="getStatistics(lectureDate)">Go</button>
        </div>
    </div>
</template>

<script>
    //import dataService from "../services/dataService.js";
    import axios from "axios";
    import moment from "moment";
    import 'bootstrap/dist/css/bootstrap.css';
    import datePicker from 'vue-bootstrap-datetimepicker';
    import 'pc-bootstrap4-datetimepicker/build/css/bootstrap-datetimepicker.css';

    let attendance;
    export default {
        data () {
            return {
                lectureDate: moment(new Date()).format("DD.MM.YYYY")
            }
        },
        methods: {
            getStatistics: function (date) {
                const AXIOS = axios.create({
                    baseURL: "http://134.209.227.130:8080",
                    headers: {
                        Authorization: "JWT " + localStorage.getItem("token"),
                        "Content-Type": "application/json; charset=UTF-8",
                        "Access-Control-Allow-Origin": "*"
                    }
                });
                let rightDate = moment(date).format("DD.MM.YYYY");
                AXIOS.get("/lesson/daily", {
                    params: {
                        date: rightDate
                    }
                }).then(response => {
                    attendance = response.data;
                    console.log(attendance);
                })
            }
        }
    };
</script>
