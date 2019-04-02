<template>
    <div class="main-page">
        <div class="top-panel">
            <div class="func-panel">
                <template v-if="isStudent">
                    <div
                            class="student-top"
                            v-on:click="openTabAttendance('btn2')"
                            :class="{ student_active: activeBtn === 'btn2' }"
                    >
                        <h2>My attendance</h2>
                    </div>
                </template>

                <template v-else>
                    <div
                            class="top-name"
                            v-on:click="openTabAttendance('btn1')"
                            :class="{ active: activeBtn === 'btn1' }"
                    >
                        <h2>Statistics</h2>
                    </div>
                    <div
                            class="top-name"
                            v-on:click="openTabAttendance('btn2')"
                            :class="{ active: activeBtn === 'btn2' }"
                    >
                        <h2>Check attendance</h2>
                    </div>
                </template>
            </div>
            <div class="info-panel">
                <div class="top-name" v-on:click="sendLogoutReq()"><h2>Logout</h2></div>
                <div class="top-name"><h2>Settings</h2></div>
                <div class="top-name"><h2>Dashboard</h2></div>
            </div>
        </div>

        <div class="cont">
            <StudentToolBar v-if="show && isStudent"/>
            <listOfStudents v-else-if="showCont"
                            :dataForCreate="paramOfCall"
                            @hideContent="showCont = $event"
            />
            <ToolBar v-else-if="show"
                     @getLessInfo="paramOfCall = $event"
                     @showContent="showCont = $event"
            />
        </div>
    </div>
</template>

<script>
    import StudentToolBar from "../components/StudentToolBar.vue";
    import ToolBar from "../components/ToolBar.vue";
    import jwt_decode from 'jwt-decode';
    import {logout} from "../services/loginService";
    import listOfStudents from "../components/listOfStudents";

    function getUserID() {
        let role = JSON.parse(JSON.stringify(jwt_decode(localStorage.getItem("token")))).role;
        return (role === "ROLE_STUDENT");
    }

    export default {
        name: "home",
        data() {
            return {
                activeBtn: "",
                show: false,
                userID: "",
                isStudent: getUserID(),
                showCont: false,
                paramOfCall: {}
            };
        },
        components: {listOfStudents, StudentToolBar, ToolBar},
        methods: {
            sendLogoutReq() {
                let some = "";
                logout();
                this.$emit("ChangeToken", some);
            },
            openTabAttendance: function (state) {
                if (state === "btn1") {
                    if (this.activeBtn === "btn1") {
                        this.activeBtn = "";
                    } else {
                        this.show = false;
                        this.activeBtn = "btn1";
                    }
                } else {
                    this.show = false;
                    if (this.activeBtn === "btn2") {
                        this.show = false;
                        this.activeBtn = "";
                    } else {
                        this.show = true;
                        this.activeBtn = "btn2";
                    }
                }
            }
        }
    };
</script>
