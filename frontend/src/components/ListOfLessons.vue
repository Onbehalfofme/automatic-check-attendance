<template>
    <div class="user-panel-lesson">
        <div class="header">My attendance statistics</div>
        <br/>
        <br/>
        <b-input-group>
            <b-form-input
                    v-model="filter"
                    placeholder="Type to Search"
            ></b-form-input>
        </b-input-group>
        <div class="user-table">
            <b-table
                    @filtered="onFiltered"
                    :filter="filter"
                    striped
                    hover
                    :items="data"
                    :fields="fields"
            >
                <template slot="actions" slot-scope="row">
                    <b-button size="m" @click="row.toggleDetails">
                        {{ row.detailsShowing ? "Hide" : "Show" }} Details
                    </b-button>
                </template>

                <template slot="row-details" slot-scope="row">
                    <b-card>
                        <ul>
                            <p>
                                You can notify the professor in advance about your future
                                absence.
                            </p>
                            <p>
                                You can send an explanatory letter to your professor, if you did
                                not attend the lesson for a good reason.
                            </p>
                            <p>You can dispute your attendance mark if it is unfair.</p>
                            <p>Please write here your letter to the professor:</p>
                            <textarea type="text"
                                      class="input-group"
                                      v-model="message"></textarea
                            >
                            If necessary, you can upload the justification:
                            <template>
                                <div>
                                    <b-form-file
                                            v-model="file"
                                            :state="Boolean(file)"
                                            placeholder="Choose a file..."
                                            drop-placeholder="Drop file here..."
                                    ></b-form-file>
                                </div>
                            </template>
                            <b-button id="show-btn" v-b-modal.my-modal v-on:click="upload()">Send!</b-button>
                            <!--<b-modal id="my-modal" hide-footer>-->
                            <!--<div class="d-block text-center">-->
                            <!--<h3>The file is successfully uploaded</h3>-->
                            <!--</div>-->
                            <!--<b-button class="mt-3" block @click="$bvModal.hide('bv-modal-example')">Ok</b-button>-->
                            <!--</b-modal>-->
                            <!--<b-modal id="my-modal">-->
                            <!--<h3>The file is successfully uploaded</h3>-->
                            <!--</b-modal>-->
                        </ul>
                    </b-card>
                </template>

                <template slot="attendance" slot-scope="row">
                    {{ row.value ? "PRESENT" : "ABSENT" }}
                </template>
            </b-table>
        </div>
    </div>
</template>

<script>
    import axios from "axios";

    export default {
        props: ["dataForCreateList"],
        data() {
            return {
                message: "",
                filter: "",
                file: null,
                file2: null,
                fields: {
                    time: {
                        key: "dateTime",
                        label: "Date",
                        sortable: true
                    },
                    lesson: {
                        key: "course.name",
                        label: "Course",
                        sortable: true
                    },
                    teacher: {
                        key: "teacher.surname",
                        label: "Teacher",
                        sortable: true
                    },
                    room: {
                        key: "room",
                        label: "Room",
                        sortable: true
                    },
                    attendance: {
                        key: "attendance",
                        label: "Attendance",
                        sortable: true
                    },
                    buttons: {key: "actions", label: ""}
                },
                info: this.dataForCreateList,
                data: [],
                currentPage: 1,
                totalRows: 0
            };
        },

        created: async function () {
            await this.getStatistics(this.info);
            console.log(this.data);
        },
        computed: {
            sortOptions() {
                // Create an options list from our fields
                return this.fields
                    .filter(f => f.sortable)
                    .map(f => {
                        return {text: f.label, value: f.lesson};
                    });
            }
        },
        methods: {
            onFiltered(filteredItems) {
                this.totalRows = filteredItems.length;
                this.currentPage = 1;
            },
            upload: async function () {
                const AXIOS = axios.create({
                    baseURL: "http://134.209.227.130:8080",
                });
                let formData = new FormData();
                formData.append('file', this.file);
                await AXIOS.post("/reason/upload",
                    formData,
                    {
                        headers: {
                            Authorization: "JWT " + localStorage.getItem("token"),
                            'Content-Type': 'multipart/form-data',
                            "Access-Control-Allow-Origin": "*"
                        },
                        body: {
                            lessonId: this.data.id,
                            message: this.message
                        }

                    }
                )
            },
            getStatistics: async function (date) {
                const AXIOS = axios.create({
                    baseURL: "http://134.209.227.130:8080",
                    headers: {
                        Authorization: "JWT " + localStorage.getItem("token"),
                        "Content-Type": "application/json; charset=UTF-8",
                        "Access-Control-Allow-Origin": "*"
                    }
                });
                await AXIOS.get("/lesson/daily", {params: {date}}).then(response => {
                    this.data = response.data;
                    this.totalRows = this.data.length;
                });
            }
        }
    };
</script>
