import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
//import SmartTable from "vuejs-smart-table";
import BTable from "bootstrap-vue/es/components/table/table";

Vue.component("b-table", BTable);
Vue.component("b-input-group", BTable);
Vue.component("b-form-input", BTable);
Vue.component("b-input-group-append", BTable);
Vue.component("b-button", BTable);

//Vue.use(SmartTable);
Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
