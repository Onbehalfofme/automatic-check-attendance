<template>
  <div class="control-panel">
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
</template>

<script>
export default {
  data() {
    return {
      options: [{ text: "Introduction to AI" }],
      options2: [{ text: "Lecture" }, { text: "Tutorial" }, { text: "Lab" }]
    };
  }
};
</script>
