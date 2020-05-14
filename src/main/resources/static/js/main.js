import Vue from 'vue'
import Vuetify from 'vuetify'
import VueRouter from 'vue-router'
import Vuex from 'vuex'
import Toasted from 'vue-toasted'

import mainStore from './store/mainStore.js'
import subscripModalStore from './store/subscripModalStore.js'
import pagerStore from './store/pagerStore.js'
import projectsStore from "./store/projectsStore.js";
import createEditEntityStore from "./store/createEditEntityStore.js";

import App from 'pages/App.vue'
import Home from 'components/home/Home.vue'
import Profile from 'components/profile/Profile.vue'
import UserMessages from 'components/messages/UserMessages.vue'
import Projects from "components/projects/Projects.vue";
import NewEditProject from "./components/projects/NewEditProject.vue";
import NewEditModule from "./components/modules/NewEditModule.vue";
import Project from "./components/projects/Project.vue";
import Module from "./components/modules/Module.vue";
import NewEditTestPlan from "./components/testplan/NewEditTestPlan.vue";
import NewEditRequirement from "./components/requirement/NewEditRequirement.vue";

Vue.use(VueRouter);
Vue.use(Vuex);
Vue.use(Toasted);
Vue.use(Vuetify);

const routes = [
    { path: '/', component: Home, props: true },
    { path: '/user/profile', component: Profile, props: true },
    { path: '/user-messages/:id', component: UserMessages, props: true },
    { path: '/projects/:id', component: Projects, props: true },
    { path: '/new-project', component: NewEditProject, props: true },
    { path: '/edit-project/:id', component: NewEditProject, props: true },
    { path: '/view-project/:id', component: Project, props: true },
    { path: '/new-module/:projectId', component: NewEditModule, props: true },
    { path: '/edit-module/:projectId/:id', component: NewEditModule, props: true },
    { path: '/view-module/:id', component: Module, props: true },
    { path: '/new-test-plan/:parentId', component: NewEditTestPlan, props: true },
    { path: '/edit-test-plan/:parentId/:id', component: NewEditTestPlan, props: true },
    { path: '/new-requirement/:parentId', component: NewEditRequirement, props: true },
    { path: '/edit-requirement/:parentId/:id', component: NewEditRequirement, props: true },
];

const router = new VueRouter({
    routes
});

const store = new Vuex.Store({
    modules: {
        main: mainStore,
        subscripModal: subscripModalStore,
        pager: pagerStore,
        projects: projectsStore,
        createEditEntity: createEditEntityStore,
    }
});

new Vue({
    el: '#app',
    router,
    store,
    vuetify: new Vuetify({ }),
    render: a => a(App)
});