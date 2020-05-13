export default {
    state: {
        name: null,
        description: null,
        initialDate: null,
        endDate: null,
    },
    mutations: {
        setName(state, payload) {
            state.name = payload;
        },
        setDescription(state, payload) {
            state.description = payload;
        },
        setInitialDate(state, payload) {
            state.initialDate = payload;
        },
        setEndDate(state, payload) {
            state.endDate = payload;
        },
    },
    getters: {
        getName: state => state.name,
        getDescription: state => state.description,
        getInitialDate: state => state.initialDate,
        getEndDate: state => state.endDate,
    }
};