<template>
    <div class="control-panel">
        <div class="list-option">
            <div class="headline"><h2>My attendance statistics</h2></div>
            <div><h3>Date</h3></div>
            <input class="date" type="date" lang="en" name="calendar" v-model="new_date"/>
            <button v-on:click="getStatistics(new_date)">Go</button>
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

    export default {
        data() {
            return {
                new_date: moment(new Date()).format("DD.MM.YYYY")
            }
        },
        methods: {
            getStatistics: function (new_date) {
                let date = moment(new_date).format("DD.MM.YYYY");
                this.$emit("getLessInfo", date);
                this.$emit("showContent", true);
                this.$emit("closeTab", false);
                this.$emit("closeToolBar", "");
            }
        }
    };
</script>
