export default {
    state: {
        count: 0,
        projects: [],
        filter: "",
    },
    mutations: {
        setProjects(state, payload) {
            state.projects = payload;
        },
        setProjectFilter(state, payload) {
            state.filter = payload;
        },
    },
    getters: {
        getProjects: state => state.projects,
        getProjectFilter: state => state.filter,
    }
};