<template>
    <div class="user-panel">
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

    export default {
        props: ["dataForCreateChart"],
        name: 'StudentChart',
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
                info: this.dataForCreateChart,
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
            console.log(this.info);
            let date = moment(this.info).format("DD.MM.YYYY");
            console.log(date);
            await this.getChart(date);
            this.ready = true;
        },
        methods: {
            getChart: async function (date) {
                const AXIOS = await axios.create({
                    baseURL: "http://134.209.227.130:8080",
                    headers: {
                        Authorization: "JWT " + localStorage.getItem("token"),
                        "Content-Type": "application/json; charset=UTF-8",
                        "Access-Control-Allow-Origin": "*"
                    }
                });
                await AXIOS.get("/lesson/weekly", {params: {date}}).then(async response => {
                    let weekly = await response.data;
                    for (let i = 0; i < 5; i++) {
                        let array = weekly[i];

                        let present = 0;
                        let all = 0;

                        for (let j = 0; j < array.length; j++) {
                            if (array[j].attendance === 'PRESENT') present++;
                            all++;
                        }
                        if (all === 0) {
                            this.barChartData.datasets[0].data[i] = 0;
                        } else {
                            this.barChartData.datasets[0].data[i] = present / all * 100;
                        }
                    }

                });
            }
        }
    }
</script>

<style scoped>

</style>
