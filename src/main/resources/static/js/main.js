import Vue from 'vue'
import VueRouter from 'vue-router'
import Vuex from 'vuex'
import App from 'pages/App.vue'
import Greetings from 'components/greetings/Greetings.vue'
import Profile from 'components/profile/Profile.vue'
import Messages from 'components/messages/Messages.vue'

Vue.use(VueRouter);
Vue.use(Vuex);

const routes = [
    { path: '/', component: Greetings, props: true },
    { path: '/user/profile', component: Profile, props: true },
    { path: '/message', component: Messages, props: true },
];

const router = new VueRouter({
    routes
});

const store = new Vuex.Store({
    state: {
        count: 0,
        profile: null,
        messages: [],
    },
    mutations: {
        increment (state) {
            state.count++
        },
        setProfile(state, payload) {
            state.profile = payload;
        },
        setMessages(state, payload) {
            state.messages = payload;
        },
    },
    getters: {
        getProfile: state => state.profile,
        getMessages: state => state.messages,
    }
});

new Vue({
  el: '#app',
  router,
  store,
  render: a => a(App)
});