<template>
  <div v-if="channel && channel.userChannel">
    <h3>{{channel.userChannel.name}}</h3>
    <div v-if="!channel.currentUser">
      <a v-if="channel.subscriber" class="btn btn-info" :href="`/user/unsubscribe/${channel.userChannel.id}/list`">Unsubscribe</a>
      <a v-else class="btn btn-info" :href="`/user/subscribe/${channel.userChannel.id}/list`">Subscribe</a>
    </div>
    <div class="container my-3">
      <div class="row">
        <div class="col">
          <div class="card">
            <div class="card-title">Subscriptions</div>
            <h3 class="card-text">
              <a :href="`/user/subscriptions/${channel.userChannel.id}`">{{channel.subscriptionsCount}}</a>
            </h3>
          </div>
        </div>
        <div class="col">
          <div class="card">
            <div class="card-title">Subscribers</div>
            <h3 class="card-text">
              <a :href="`/user/subscribers/${channel.userChannel.id}`">{{channel.subscribersCount}}</a>
            </h3>
          </div>
        </div>
      </div>
      <message-edit v-if="channel.currentUser" class="mt-3"></message-edit>
      <message-list :messages="channel.messages" :profile="profile"></message-list>
    </div>
  </div>
</template>

<script>
  import MessageEdit from './MessageEdit.vue'
  import MessageList from './MessageList.vue'
  import axios from 'axios'
  
  export default {
    data() {
      return {
        channel: {},
        message: null,
      }
    },
    mounted() {
      axios
        .get(`${location.origin}/message/user/${this.$route.params.id}`)
        .then(response => {
          this.channel = response.data;
        });
    },
    components: {
      MessageEdit, MessageList
    },
    computed: {
      profile() {
        return this.$store.getters.getProfile;
      },
    },
    
  }
</script>

<style>
  
</style>