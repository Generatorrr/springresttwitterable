import Vue from 'vue'
import VueRouter from 'vue-router'
import Vuex from 'vuex'
import App from 'pages/App.vue'
import Greetings from 'components/greetings/Greetings.vue'
import Profile from 'components/profile/Profile.vue'

Vue.use(VueRouter);
Vue.use(Vuex);

const routes = [
    { path: '/', component: Greetings, props: true },
    { path: '/user/profile', component: Profile, props: true },
];

const router = new VueRouter({
    routes
});

const store = new Vuex.Store({
    state: {
        count: 0,
        profile: null
    },
    mutations: {
        increment (state) {
            state.count++
        },
        setProfile(state, payload) {
            state.profile = payload;
        }
    },
    getters: {
        getProfile: state => {
            return state.profile;
        }
    }
});

new Vue({
  el: '#app',
  router,
  store,
  render: a => a(App)
});