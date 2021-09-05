// External components / libraries
import Vue from 'vue'
import VueRouter from "vue-router";
import App from './App.vue';
import vSelect from 'vue-select';
import VueSlider from 'vue-slider-component';

// Font Awesome
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faEye } from '@fortawesome/free-solid-svg-icons';
import { faEyeSlash } from '@fortawesome/free-solid-svg-icons';
import { faAngleUp } from '@fortawesome/free-solid-svg-icons';
import { faAngleDown } from '@fortawesome/free-solid-svg-icons';
import { faAngleRight } from '@fortawesome/free-solid-svg-icons';
import { faTimes } from '@fortawesome/free-solid-svg-icons';

// CSS
import './assets/css/style.css';
import 'vue-slider-component/theme/default.css';
import 'vue-select/dist/vue-select.css';

// My components
import HomePage from "./components/HomePage";
import ResultsPage from "./components/ResultsPage";
import PlayersPage from "./components/PlayersPage";
import Stats from "./components/stats/Stats";

library.add(faEye, faEyeSlash, faAngleUp, faAngleDown, faAngleRight, faTimes);

Vue.config.productionTip = false;
Vue.use(VueRouter);
Vue.component('v-select', vSelect);
Vue.component('VueSlider', VueSlider);
Vue.component('font-awesome-icon', FontAwesomeIcon)

const routes = [
  {
    path: '/',
    alias: '/home',
    component: HomePage,
    meta: {
      auth: false,
      title: '',
    },
  }, {
    path: '/results',
    component: ResultsPage,
    meta: {
      auth: false,
      title: 'Results',
    },
  }, {
    path: '/players',
    component: PlayersPage,
    meta: {
      auth: false,
      title: 'Players',
    },
  }, {
    path: '/stats',
    component: Stats,
    meta: {
      auth: false,
      title: 'Stats',
    },
  },
];

const router = new VueRouter({
  routes
});

new Vue({
  router,
  render: h => h(App),
}).$mount('#app');
