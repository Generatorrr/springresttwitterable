export default {
  state: {
    count: 0,
    profile: null,
    messages: [],
    filter: "",
    editingMessage: null,
    channel: null,
  },
  mutations: {
    setProfile(state, payload) {
      state.profile = payload;
    },
    setMessages(state, payload) {
      state.messages = payload;
    },
    setEditingMessage(state, payload) {
      state.editingMessage = payload;
    },
    setChannel(state, payload) {
      state.channel = payload;
    },
    setFilter(state, payload) {
      state.filter = payload;
    },
  },
  getters: {
    getProfile: state => state.profile,
    getMessages: state => state.messages,
    getFilter: state => state.filter,
    getEditingMessage: state => state.editingMessage,
    getChannel: state => state.channel,
  }
};