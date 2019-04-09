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
                        <h2>Update lesson</h2>
                    </div>
                    <div
                            class="top-name"
                            v-on:click="openTabAttendance('btn2')"
                            :class="{ active: activeBtn === 'btn2' }"
                    >
                        <h2>Create lesson</h2>
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
            <StudentToolBar v-if="isStudent && show"
                            @getLessInfo="paramOfCall = $event"
                            @showContent1="showCont1 = $event"
                            @showContent2="showCont2 = $event"
                            @closeTab="show = $event"
                            @closeToolBar="activeBtn = $event"
            />
            <StudentChart v-else-if="isStudent && showCont2"
                          @showContent="showCont2 = $event"
                          :dataForCreateChart="paramOfCall"
                          @hideContent="showCont2 = $event"
            />
            <ListOfLessons v-else-if="isStudent && showCont1"
                           @showContent="showCont1 = $event"
                           :dataForCreate="paramOfCall"
                           @hideContent="showCont1 = $event"
            />
            <listOfStudents
                    v-else-if="showCont"
                    @showContent="showCont = $event"
                    :dataForCreate="paramOfCall"
                    @hideContent="showCont = $event"
            />
            <listForUpdate
                    v-else-if="showContUpd"
                    @showContent="showContUpd = $event"
                    :dataForCreate="paramOfCall"
                    @hideContent="showContUpd = $event"
            />
            <ToolBar
                    v-else-if="show"
                    @getLessInfo="paramOfCall = $event"
                    @showContent="showCont = $event"
                    @closeTab="show = $event"
                    @closeToolBar="activeBtn = $event"
            />
            <ToolBarForUpdate
                    v-else-if="showUpd"
                    @getLessInfo="paramOfCall = $event"
                    @showContent="showContUpd = $event"
                    @closeTab="showUpd = $event"
                    @closeToolBar="activeBtn = $event"
            />
        </div>
    </div>
</template>

<script>
    import StudentToolBar from "../components/StudentToolBar.vue";
    import ToolBar from "../components/ToolBar.vue";
    import ToolBarForUpdate from "../components/ToolBarForUpdate.vue";
    import jwt_decode from "jwt-decode";
    import {logout} from "../services/loginService";
    import listOfStudents from "../components/listOfStudents";
    import ListOfLessons from "../components/ListOfLessons";
    import listForUpdate from "../components/listForUpdate";
    import StudentChart from "../components/StudentChart";

    function getUserID() {
        let role = JSON.parse(
            JSON.stringify(jwt_decode(localStorage.getItem("token")))
        ).role;
        return role === "ROLE_STUDENT";
    }

    export default {
        name: "home",
        data() {
            return {
                activeBtn: "",
                show: false,
                showUpd: false,
                userID: "",
                isStudent: getUserID(),
                showCont: false,
                showCont1: false,
                showCont2: false,
                showContUpd: false,
                paramOfCall: {}
            };
        },
        components: {listOfStudents, listForUpdate, ToolBar, StudentToolBar, ToolBarForUpdate, ListOfLessons, StudentChart},
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
                        this.showUpd = false;
                    } else {
                        this.show = false;
                        this.showUpd = true;
                        this.activeBtn = "btn1";
                    }
                } else {
                    this.show = false;
                    if (this.activeBtn === "btn2") {
                        this.show = false;
                        this.activeBtn = "";
                    } else {
                        this.showCont = false;
                        this.showCont1 = false;
                        this.showCont2 = false;
                        this.show = true;
                        this.activeBtn = "btn2";
                    }
                }
            }
        }
    };
</script>
