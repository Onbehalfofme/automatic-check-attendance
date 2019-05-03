import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import BootstrapVue from 'bootstrap-vue'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import BTable from "bootstrap-vue/es/components/table/table";
import IGroups from "bootstrap-vue/es/components/input-group/input-group";
import InputGroupAppend from "bootstrap-vue/es/components/input-group/input-group-append";
import FInput from "bootstrap-vue/es/components/form-input/form-input";
import Button from "bootstrap-vue/es/components/button/button";
import FormGroup from "bootstrap-vue/es/components/form-group/form-group";
import CheckBox from "bootstrap-vue/es/components/form-checkbox/form-checkbox";
import BCollapse from 'bootstrap-vue/es/components/collapse/collapse';
import vBToggle from 'bootstrap-vue/es/directives/toggle/toggle';
import BModal from 'bootstrap-vue/es/components/modal/modal';
import vBModal from 'bootstrap-vue/es/directives/modal/modal';
// import Modal from 'bootstrap-vue/es/components/modal';
// Vue.use(Modal);
import BCard from 'bootstrap-vue/es/components/card/card';
import BFormFile from 'bootstrap-vue/es/components/form-file/form-file';

Vue.component('b-form-file', BFormFile);
Vue.component('b-card', BCard);
Vue.directive('b-modal', vBModal);
Vue.component('b-modal', BModal);
Vue.directive('b-toggle', vBToggle);
Vue.component("b-table", BTable);
Vue.component('b-collapse', BCollapse);
Vue.component("b-input-group", IGroups);
Vue.component("b-form-input", FInput);
Vue.component("b-form-group", FormGroup);
Vue.component("b-input-group-append", InputGroupAppend);
Vue.component("b-button", Button);
Vue.component("b-form-checkbox", CheckBox);

Vue.config.productionTip = false;
Vue.use(BootstrapVue);

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount("#app");
