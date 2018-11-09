export default {
  state: {
    pageInfo: null
  },
  mutations: {
    setPageInfo(state, payload) {
      state.pageInfo = payload;
    },
  },
  getters: {
    getPageInfo: state => state.pageInfo,
  }
};