import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

import BTable from "bootstrap-vue/es/components/table/table";
import IGroups from "bootstrap-vue/es/components/input-group/input-group";
import InputGroupAppend from "bootstrap-vue/es/components/input-group/input-group-append";
import FInput from "bootstrap-vue/es/components/form-input/form-input";
import Button from "bootstrap-vue/es/components/button/button";
import FormGroup from "bootstrap-vue/es/components/form-group/form-group";
import CheckBox from "bootstrap-vue/es/components/form-checkbox/form-checkbox";

Vue.component("b-table", BTable);
Vue.component("b-input-group", IGroups);
Vue.component("b-form-input", FInput);
Vue.component("b-form-group", FormGroup);
Vue.component("b-input-group-append", InputGroupAppend);
Vue.component("b-button", Button);
Vue.component("b-form-checkbox", CheckBox);

Vue.config.productionTip = false;

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount("#app");
