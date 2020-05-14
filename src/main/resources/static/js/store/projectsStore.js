export default {
    state: {
        count: 0,
        projects: [],
        filter: "",
        fullProject: null,
        fullModule: null,
    },
    mutations: {
        setProjects(state, payload) {
            state.projects = payload;
        },
        setProjectFilter(state, payload) {
            state.filter = payload;
        },
        setFullProject(state, payload) {
            state.fullProject = payload;
        },
        setFullModule(state, payload) {
            state.fullModule = payload;
        },
    },
    getters: {
        getProjects: state => state.projects,
        getProjectFilter: state => state.filter,
        getFullProject: state => state.fullProject,
        getFullModule: state => state.fullModule,
    }
};