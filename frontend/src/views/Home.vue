<template>
  <div class="main-page">
    <div class="top-panel">
      <div class="func-panel">
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
      </div>
      <div class="info-panel">
        <div class="top-name"><h2>Logout</h2></div>
        <div class="top-name"><h2>Settings</h2></div>
        <div class="top-name"><h2>Dashboard</h2></div>
        <div class="top-name"><h2>Current lecture</h2></div>
      </div>
    </div>

    <div class="cont">
      <div v-if="show" class="control-panel">
        <div class="list-option">
          <div class="headline">Lecture creation</div>
          <div><h3>Course</h3></div>
          <select class="course">
            <option v-for="option in options" v-bind:key="option.value">
              {{ option.text }}
            </option>
          </select>

          <div><h3>Type</h3></div>
          <select class="type">
            <option v-for="option in options2" v-bind:key="option.value">
              {{ option.text }}
            </option>
          </select>

          <div><h3>Data</h3></div>
          <input class="date" type="date" lang="en" name="calendar" />

          <div><h3>Time of beginning</h3></div>
          <input
            class="time"
            type="time"
            name="cron"
            value="09:00"
            min="00:01"
            max="23:59"
          />
          <button>Create</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      activeBtn: "",
      show: false,
      options: [
        { text: "Introduction to AI" },
        { text: "Probstat" },
        { text: "SWP" }
      ],
      options2: [{ text: "Lecture" }, { text: "Tutorial" }, { text: "Lab" }]
    };
  },
  methods: {
    openTabAttendance: function(state) {
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
