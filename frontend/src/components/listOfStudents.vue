<template>
    <div class="user-panel">
        <div class="header">Software project: {{ users[0].date }}</div>
        <br/>
        <b-input-group class="search">
            <b-form-input
                    class="search"
                    v-model="filter"
                    placeholder="Type to Search"
            ></b-form-input>
        </b-input-group>

        <div class="user-table">
            <b-table fixed hover :filter="filter" :items="users" :fields="fields">
                <template slot="checkIn" :width="20" slot-scope="data">
                    <input
                            class="form-control"
                            type="time"
                            v-model="data.item.checkIn"
                            @change="selectRow(data.item)"
                    />
                </template>

                <template slot="checkOut" slot-scope="data">
                    <input
                            class="form-control"
                            type="time"
                            v-model="data.item.checkOut"
                            @change="selectRow(data.item)"
                    />
                </template>

                <template slot="selected" slot-scope="data">
                    <input
                            type="checkbox"
                            v-model="data.item.selected"
                            @change="selectRow(data.item)"
                    />
                </template>
            </b-table>
            <button v-on:click="sentStudentAttendance()">Save</button>
        </div>
    </div>
</template>

<script>
    import axios from "axios";
    import moment from "moment";

    const AXIOS = axios.create({
        baseURL: "http://134.209.227.130:8080",
        headers: {
            Authorization: "JWT " + localStorage.getItem("token"),
            "Content-Type": "application/json; charset=UTF-8",
            "Access-Control-Allow-Origin": "*"
        }
    });

    export default {
        props: ["dataForCreate"],
        data() {
            return {
                info: this.dataForCreate,
                filter: "",
                selectMode: "multi",
                selected: [],
                users: {},
                lessonId: "",
                fields: {
                    selected: {
                        key: "selected",
                        sortable: false,
                        label: "",
                        class: "options-column"
                    },
                    name: {
                        label: "Person first name",
                        sortable: true
                    },
                    surname: {
                        label: "Person last name",
                        sortable: false
                    },
                    group: {
                        label: "Group",
                        sortable: true
                    },
                    checkIn: {
                        label: "Check-in",
                        sortable: true
                    },
                    checkOut: {
                        label: "Check-out",
                        sortable: true
                    },
                    attendance: {
                        label: "attendance",
                        sortable: true
                    }
                }
            };
        },

        created: function () {
            this.getUsers(this.info);
        },

        methods: {
            getUsers: async function (info) {
                let dateTime =
                    moment(this.info.lectureDate).format("DD.MM.YYYY") +
                    " " +
                    this.info.lectureTime;

                if (info.status === "create") {
                    await AXIOS.post("/lesson/create", {
                        courseId: info.courseId,
                        dateTime: dateTime,
                        room: info.room,
                        type: info.type
                    }).then(response => {
                        this.users = this.reformatData("create", response.data.students);
                        this.lessonId = response.data.lessonId;
                    });
                }

                if (info.status === "update") {
                    this.lessonId = this.info.lessonId;
                    await AXIOS.get("/lesson/" + this.lessonId).then(response => {
                        this.users = this.reformatData("update", response.data.students);
                    });
                }

                for (let i = 0; i < this.users.length; i++) {
                    let dateTime1 =
                        moment(this.users[i].date).format("DD.MM.YYYY") +
                        " " +
                        this.users[i].checkIn;

                    let dateTime2 =
                        moment(this.users[i].date).format("DD.MM.YYYY") +
                        " " +
                        this.users[i].checkOut;

                    this.selected.push({
                        attendance: this.users[i].attendance,
                        checkIn: dateTime1,
                        checkOut: dateTime2,
                        studentId: this.users[i].studentId
                    });
                }
            },
            sentStudentAttendance() {
                AXIOS.patch("/lesson/update/" + this.lessonId, this.selected);
            },
            reformatData(status, users) {
                let newVersion = [];
                for (let index = 0; index < users.length; ++index) {
                    if (status === "update") {
                        let dateTime1 = users[index].checkIn.split(" ");
                        let dateTime2 = users[index].checkOut.split(" ");
                        newVersion.push({
                            attendance: users[index].attendance,
                            checkIn: dateTime1[1],
                            checkOut: dateTime2[1],
                            date: dateTime1[0],

                            studentId: users[index].student.id,
                            name: users[index].student.name,
                            group: users[index].student.group,
                            surname: users[index].student.surname,
                            email: users[index].student.email
                        });
                    }
                    if (status === "create") {
                        let time = this.info.lectureTime.split(":");
                        let checkOut =
                            parseInt(time[0]) +
                            Math.floor((parseInt(time[1]) + 90) / 60) +
                            ":" +
                            ((parseInt(time[1]) + 90) % 60);
                        newVersion.push({
                            attendance: "ABSENT",
                            checkIn: this.info.lectureTime,
                            checkOut: checkOut,
                            date: this.info.lectureDate,
                            studentId: users[index].id,
                            name: users[index].name,
                            group: users[index].group,
                            surname: users[index].surname,
                            email: users[index].email
                        });
                    }
                }
                return newVersion;
            },
            selectRow(item) {
                if (item.selected) item._rowVariant = "info";
                else item._rowVariant = "default";

                let dateTime1 =
                    moment(item.date).format("DD.MM.YYYY") + " " + item.checkIn;

                let dateTime2 =
                    moment(item.date).format("DD.MM.YYYY") + " " + item.checkOut;

                for (let i = 0; i < this.selected.length; i++) {
                    if (item.selected)
                        if (item.studentId === this.selected[i].studentId) {
                            this.selected[i] = {
                                attendance: "PRESENT",
                                checkIn: dateTime1,
                                checkOut: dateTime2,
                                studentId: item.studentId
                            };
                        }

                    if (!item.selected)
                        if (item.studentId === this.selected[i].studentId) {
                            let attendance = null;
                            for (let j = 0; j <= this.users.length; j++)
                                if (this.users[j].studentId === item.studentId)
                                    attendance = this.users[j].attendance;

                            let dateTime1 =
                                moment(this.users[i].date).format("DD.MM.YYYY") +
                                " " +
                                this.users[i].checkIn;

                            let dateTime2 =
                                moment(this.users[i].date).format("DD.MM.YYYY") +
                                " " +
                                this.users[i].checkOut;

                            this.selected[i] = {
                                attendance: attendance,
                                checkIn: dateTime1,
                                checkOut: dateTime2,
                                studentId: item.studentId
                            };
                        }
                }
            }
        }
    };
</script>
