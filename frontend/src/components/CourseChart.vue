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
                info.after = moment(info.after).format("DD.MM.YYYY") + " 09:00";
                info.before = moment(info.before).format("DD.MM.YYYY") + " 09:00";

                let date = new Date(info.after);

                let number = date.getDay();

                if (number === 0) date.setDate(date.getDate() - 6);
                else date.setDate(date.getDate() - (number - 1));

                for (let i = 0; i < 5; i++) {
                    let new_date = moment(date).format("DD.MM.YYYY");
                    await this.getChart(new_date, i);
                    date.setDate(date.getDate() + 1);
                }
                await new Promise((resolve, reject) => setTimeout(resolve, 1000));
            },
            getChart: async function (date, i) {
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
                        after: this.info.after,
                        before: this.info.before,
                    }
                }).then(response => {
                    console.log(response);
                    if (response.data.length === 0) this.lessonID = "";
                    else this.lessonId = response.data[0].id;
                });

                if (this.lessonID === "") this.barChartData.datasets[0].data[i] = 0;
                else {
                    await AXIOS.get("/lesson/" + this.lessonId).then(response => {
                        this.students = this.reformatData("update", response.data.students);

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
