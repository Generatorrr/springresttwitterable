<template>
  <div>
    <div class="form-row">
      <div class="form-group col-md-6">
        <form class="form-inline">
          <input type="text" name="filter" placeholder="Search by tag"  v-model="filter"/>
          <button v-on:click="getFilteredMessages()" class="btn btn-primary ml-2" type="submit">Search</button>
        </form>
      </div>
    </div>
    <message-edit></message-edit>
    <message-list :messages="messages" :profile="profile"></message-list>
  </div>
</template>

<script>
  import MessageEdit from './MessageEdit.vue'
  import MessageList from './MessageList.vue'

  import axios from 'axios'
  
  export default {
    components: {
      MessageList, MessageEdit
    },
    computed: {
      profile() {
        return this.$store.getters.getProfile;
      },
      messages() {
        return this.$store.getters.getMessages;
      }
    },
    data() {
      return {
        filter: ""
      }
    },
    methods: {
      getFilteredMessages() {
        debugger;
        axios
          .get(`${location.origin}${this.$route.path}?filter=${this.filter}`)
          .then(response => {
            debugger;
            this.$store.commit('setMessages', JSON.parse(response.data));
          })
          .catch(err => {
            console.log(err)
          });
      }
    }
  }
</script>

<style>

</style>