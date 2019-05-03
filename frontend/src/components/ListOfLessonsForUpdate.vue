<template>
    <div>
        <listOfStudents
                v-if="statusCreate || (!statusCreate && statusUpdate)"
                :dataForCreate="info"
                @hideContent="this.$emit('hideContent', $event)"
        />
        <div class="lesson-panel" v-else-if="!statusCreate && !statusUpdate">
            <div class="header">Please select a lesson</div>
            <div class="user-table">
                <b-table
                        fixed
                        hover
                        :items="courses"
                        :fields="fields"
                        selectable
                        :select-mode="selectMode"
                        @row-selected="rowSelected"
                        selectedVariant="none"
                >
                </b-table>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from "axios";
    import moment from "moment";
    import listOfStudents from "../components/listOfStudents";

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
                selectMode: "single",
                info: this.dataForCreate,
                statusCreate: false,
                statusUpdate: false,
                courses: [],
                fields: {
                    course: {
                        label: "Course",
                        sortable: true
                    },
                    teacher: {
                        label: "Teacher",
                        sortable: true
                    },
                    room: {
                        label: "Room",
                        sortable: true
                    },
                    date: {
                        label: "Date",
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
                    type: {
                        label: "Type",
                        sortable: true
                    }
                }
            };
        },
        components: {listOfStudents},
        created: function () {
            if (this.info.status === "create") this.statusCreate = true;
            else this.getCourses(this.info);
        },

        methods: {
            rowSelected(item) {
                this.info = {
                    lessonId: item[0].id,
                    status: "update",
                    course: this.info.courseId,
                    type: this.info.lectureType,
                    room: this.info.lectureRoom,
                    teacher: this.info.lectureTeacher,
                    bLectureDate: this.info.bLectureDate,
                    bLectureTime: this.info.bLectureTime,
                    aLectureDate: this.info.aLectureDate,
                    aLectureTime: this.info.aLectureTime
                };
                this.statusUpdate = true;
            },
            getCourses: async function (info) {
                let data = null;
                let start = null;
                let end = null;

                if (info.aLectureTime != null)
                    start =
                        moment(info.aLectureDate).format("DD.MM.YYYY") +
                        " " +
                        info.aLectureTime;
                if (info.bLectureTime != null)
                    end =
                        moment(info.bLectureDate).format("DD.MM.YYYY") +
                        " " +
                        info.bLectureTime;

                await AXIOS.get("/lesson/search", {
                    params: {
                        course: info.courseId,
                        start: start,
                        end: end,
                        room: info.room,
                        type: info.type,
                        teacher: info.teacher
                    }
                }).then(response => {
                    data = response.data;
                });
                for (let i = 0; i < data.length; i++) {
                    let date = data[i].dateTime.toString().split(" ");
                    let time = date[1].split(":");
                    let checkOut =
                        parseInt(time[0]) +
                        Math.floor((parseInt(time[1]) + 90) / 60) +
                        ":" +
                        ((parseInt(time[1]) + 90) % 60);

                    this.courses.push({
                        course: data[i].course.name,
                        checkIn: date[1],
                        checkOut: checkOut,
                        room: data[i].room,
                        date: date[0],
                        teacher: data[i].teacher.name + " " + data[i].teacher.surname,
                        type: data[i].type,
                        id: data[i].id
                    });
                }
            }
        }
    };
</script>
