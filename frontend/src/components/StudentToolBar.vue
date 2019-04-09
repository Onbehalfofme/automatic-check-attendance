<template>
    <div class="control-panel">
        <div class="list-option">
            <div class="headline"><h2>My attendance statistics</h2></div>
            <div><h3>Statistics of the day:</h3></div>
            <input class="date" type="date" lang="en" name="calendar" v-model="new_date"/>
            <button v-on:click="getStatistics(new_date)">Go</button>

            <div id="tool-bar">
            <div><h3>Statistics of the week:</h3></div>
            <input class="date" type="date" lang="en" name="calendar" v-model="new_week"/>
            <button v-on:click="getChart(new_week)">Go</button>
            </div>
        </div>
    </div>
</template>

<script>
    //import dataService from "../services/dataService.js";

    import moment from "moment";
    import 'bootstrap/dist/css/bootstrap.css';
    import 'pc-bootstrap4-datetimepicker/build/css/bootstrap-datetimepicker.css';

    export default {
        data() {
            return {
                new_date: moment(new Date()).format("DD.MM.YYYY"),
                new_week: moment(new Date()).format("DD.MM.YYYY"),
            }
        },
        methods: {
            getStatistics: function (new_date) {
                let date = moment(new_date).format("DD.MM.YYYY");
                this.$emit("getLessInfo", date);
                this.$emit("showContent1", true);
                this.$emit("closeTab", false);
                this.$emit("closeToolBar", "");
            },
            getChart: function (new_week) {
                this.$emit("getLessInfo", new_week);
                this.$emit("showContent2", true);
                this.$emit("closeTab", false);
                this.$emit("closeToolBar", "");
            }
        }
    };
</script>
