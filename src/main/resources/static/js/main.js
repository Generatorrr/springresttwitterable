import Vue from 'vue'
import VueRouter from 'vue-router'
import Vuex from 'vuex'
import Toasted from 'vue-toasted'

import App from 'pages/App.vue'
import Home from 'components/home/Home.vue'
import Profile from 'components/profile/Profile.vue'
import UserMessages from 'components/messages/UserMessages.vue'

Vue.use(VueRouter);
Vue.use(Vuex);
Vue.use(Toasted);

const routes = [
    { path: '/', component: Home, props: true },
    { path: '/user/profile', component: Profile, props: true },
    { path: '/user-messages/:id', component: UserMessages, props: true },
];

const router = new VueRouter({
    routes
});

const store = new Vuex.Store({
    state: {
        count: 0,
        profile: null,
        messages: [],
        editingMessage: null
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
        setEditingMessage(state, payload) {
            state.editingMessage = payload;
        },
    },
    getters: {
        getProfile: state => state.profile,
        getMessages: state => state.messages,
        getEditingMessage: state => state.editingMessage,
    }
});

new Vue({
  el: '#app',
  router,
  store,
  render: a => a(App)
});