import Vue from 'vue'
import VueRouter from 'vue-router'
import Vuex from 'vuex'
import Toasted from 'vue-toasted'

import mainStore from './store/mainStore.js'
import subscripModalStore from './store/subscripModalStore.js'

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
    modules: {
        main: mainStore,
        subscripModal: subscripModalStore,
    }
});

new Vue({
  el: '#app',
  router,
  store,
  render: a => a(App)
});