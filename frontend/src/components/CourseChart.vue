<template>
    <div class="user-panel">
        <b-button v-b-toggle.collapse-1>Average attendance</b-button>
        <b-collapse id="collapse-1" :width="600"
                    :heigth="800">
            <template v-if="ready2">
                <div class="text">
                    <h1>Comparison of the course average attendance rate with the total average attendance</h1>
                </div>
                <mdb-container class="chart">
                    <mdb-bar-chart :data="pieChartData" :options="pieChartOptions" :width="600"
                                   :height="300"></mdb-bar-chart>
                </mdb-container>
            </template>
        </b-collapse>
        <div class="chart" v-if="ready">
            <mdb-container>
                <mdb-bar-chart :data="barChartData" :options="barChartOptions" :width="600"
                               :heigth="800"></mdb-bar-chart>
            </mdb-container>
        </div>
    </div>
</template>


<script>
    import moment from "moment";
    import {mdbBarChart, mdbContainer} from 'mdbvue';
    import axios from "axios";
    import {mdbPieChart} from 'mdbvue';

    export default {
        props: ["dataForCreateCourseChart"],
        name: 'CourseChart',
        components: {
            mdbBarChart,
            mdbPieChart,
            mdbContainer
        },
        data() {
            return {
                ready: false,
                ready2: false,
                array: [],
                daily: [],
                barChartData: {
                    labels: ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"],
                    datasets: [{
                        label: '% of attendance',
                        data: [],
                        backgroundColor: 'rgba(0, 250, 154, 0.5)',
                        borderColor: 'rgba(0, 250, 154, 1)',
                        borderWidth: 1,
                    }]
                },
                pieChartData: {
                    labels: ["This course", "Average"],
                    datasets: [{
                        label: '% of attendance',
                        data: [],
                        backgroundColor: "rgba(127, 255, 212, 0.5)",
                        borderColor: "rgba(127, 255, 212, 1)",
                        borderWidth: 1,
                    }]
                },
                pieChartOptions: {
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
            await this.getAverage();
            this.ready2 = true;
        },
        methods: {
            getWeekStatistics: async function () {
                let date = moment(this.info.start).format("DD.MM.YYYY");
                console.log(this.info.start);
                console.log(date);
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
                let start = moment(date).format("DD.MM.YYYY") + " 01:00";
                let end = moment(date).format("DD.MM.YYYY") + " 23:00";
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
                        course: this.info.course.name,
                        start: start,
                        end: end,
                    }
                }).then(response => {
                    if (response.data.length === 0) this.lessonId = "";
                    else this.lessonId = response.data[0].id;
                });
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
            },

            getAverage: async function () {
                const AXIOS = await axios.create({
                    baseURL: "http://134.209.227.130:8080",
                    headers: {
                        Authorization: "JWT " + localStorage.getItem("token"),
                        "Content-Type": "application/json; charset=UTF-8",
                        "Access-Control-Allow-Origin": "*"
                    }
                });
                await AXIOS.get("/lesson/courseAverage/" + this.info.course.id).then(response => {
                    this.pieChartData.datasets[0].data[0] = (response.data * 100);
                });
                await AXIOS.get("/lesson/average").then(response => {
                    this.pieChartData.datasets[0].data[1] = (response.data * 100);
                });

                console.log(this.pieChartData.datasets[0].data);
            }
        }
    }
</script>

<style scoped>

</style>
