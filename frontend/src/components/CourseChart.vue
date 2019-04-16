<template>
    <div class="user-panel">
        <div class="chart" v-if="ready">
            <mdb-container>
                <mdb-bar-chart :data="barChartData" :options="barChartOptions"></mdb-bar-chart>
            </mdb-container>
        </div>
    </div>
</template>


<script>
    import moment from "moment";
    import {mdbBarChart, mdbContainer} from 'mdbvue';
    import axios from "axios";

    export default {
        props: ["dataForCreateCourseChart"],
        name: 'CourseChart',
        components: {
            mdbBarChart,
            mdbContainer
        },
        data() {
            return {
                after: moment(new Date()).format("DD.MM.YYYY"),
                before: moment(new Date()).format("DD.MM.YYYY"),
                ready: false,
                array: [],
                daily: [],
                barChartData: {
                    labels: ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"],
                    datasets: [{
                        label: '% of attendance',
                        data: [],
                        backgroundColor: 'rgba(35, 198, 100, 0.5)',
                        borderColor: '#23c664',
                        borderWidth: 1,
                    }]
                },
                students: [],
                lessonId: "",
                info: this.dataForCreateCourseChart,
                barChartOptions: {
                    responsive: false,
                    maintainAspectRatio: false,
                    scales: {
                        xAxes: [{
                            barPercentage: 1,
                            gridLines: {
                                display: true,
                                color: "rgba(0, 0, 0, 0.1)"
                            }
                        }],
                        yAxes: [{
                            gridLines: {
                                display: true,
                                color: "rgba(0, 0, 0, 0.1)"
                            }
                        }]
                    }
                }
            };
        },
        created: async function () {
            await this.getWeekStatistics(this.info);
            this.ready = true;
        },
        methods: {
            getWeekStatistics: async function (info) {
                let date = moment(this.info.after).format("DD.MM.YYYY");
                date = new Date(date);
                let number = date.getDay();

                if (number === 0) date.setDate(date.getDate() - 6);
                else date.setDate(date.getDate() - (number - 1));

                for (let i = 0; i < 5; i++) {
                    await this.getChart(date, i);
                    date.setDate(date.getDate() + 1);
                }
                await new Promise((resolve, reject) => setTimeout(resolve, 1000));
            },
            getChart: async function (date, i) {
                let after = moment(date).format("DD.MM.YYYY") + " 01:00";
                let before = moment(date).format("DD.MM.YYYY") + " 23:00";
                const AXIOS = await axios.create({
                    baseURL: "http://134.209.227.130:8080",
                    headers: {
                        Authorization: "JWT " + localStorage.getItem("token"),
                        "Content-Type": "application/json; charset=UTF-8",
                        "Access-Control-Allow-Origin": "*"
                    }
                });
                await AXIOS.get("/lesson/search", {
                    params: {
                        course: this.info.course,
                        after: after,
                        before: before,
                    }
                }).then(response => {
                    if (response.data.length === 0) this.lessonId= "";
                    else this.lessonId = response.data[0].id;
                });
                console.log(this.lessonId);
                if (this.lessonId === "") this.barChartData.datasets[0].data[i] = 0;
                else {
                    await AXIOS.get("/lesson/" + this.lessonId).then(response => {
                        this.students = response.data.students;

                        let present = 0;
                        let all = 0;

                        for (let j = 0; j < this.students.length; j++) {
                            if (this.students[j].attendance === 'PRESENT') present++;
                            all++;
                        }
                        if (all === 0) {
                            this.barChartData.datasets[0].data[i] = 0;
                        } else {
                            this.barChartData.datasets[0].data[i] = present / all * 100;
                        }
                    });
                }
            }
        }
    }
</script>

<style scoped>

</style>
