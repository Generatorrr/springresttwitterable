<template>
  <div v-if="channel && channel.userChannel">
    <h3>{{channel.userChannel.name}}</h3>
    <div v-if="!channel.currentUser">
      <a v-if="channel.subscriber" v-on:click="subscribeOrUnsubscribe" class="btn btn-info">Unsubscribe</a>
      <a v-else class="btn btn-info" v-on:click="subscribeOrUnsubscribe">Subscribe</a>
    </div>
    <div class="container my-3">
      <div class="row">
        <div class="col">
          <div class="card">
            <div class="card-title">Subscriptions</div>
            <h3 class="card-text">
              <a v-on:click="showSubscribtionsModal">{{channel.subscriptionsCount}}</a>
            </h3>
          </div>
        </div>
        <div class="col">
          <div class="card">
            <div class="card-title">Subscribers</div>
            <h3 class="card-text">
              <a v-on:click="showSubscribersModal">{{channel.subscribersCount}}</a>
            </h3>
          </div>
        </div>
      </div>
      <message-edit v-if="channel.currentUser" class="mt-3"></message-edit>
      <message-list :messages="channel.messages" :profile="profile"></message-list>
    </div>
    <subscrib-modal :channel="channel" :type="type" :users="users" v-show="isModalVisible" @close="closeModal"></subscrib-modal>
  </div>
</template>

<script>
  import MessageEdit from './MessageEdit.vue'
  import MessageList from './MessageList.vue'
  import SubscribModal from './../subscrib/Subscrib.vue'
  import axios from 'axios'
  
  export default {
    components: {
      MessageEdit, MessageList, SubscribModal
    },
    data() {
      return {
        channel: {},
        message: null,
        isModalVisible: false,
        type: null,
        users: null,
      }
    },
    mounted() {
      axios
        .get(`${location.origin}/message/user/${this.$route.params.id}`)
        .then(response => {
          this.channel = response.data;
        });
    },
    computed: {
      profile() {
        return this.$store.getters.getProfile;
      },
    },
    methods: {
      subscribeOrUnsubscribe() {
        debugger;
        const actionUrl = (this.channel && this.channel.subscriber) 
          ? `${location.origin}/user/unsubscribe/${this.channel.userChannel.id}`
          : `${location.origin}/user/subscribe/${this.channel.userChannel.id}`;
        const successMessage = (this.channel && this.channel.subscriber)
          ? `You have successfully unsubscribed from ${this.channel.userChannel.name}.`
          : `You have successfully subscribed to ${this.channel.userChannel.name}!!!`;
        return axios
          .get(actionUrl)
          .then(res => {
            if (res.status === 200) {
              this.$toasted.success(successMessage, {
                theme: "bubble",
                position: "top-center",
                duration : 4000,
                Icon: "assignment_ind"
              });
              this.channel.subscribersCount = this.channel.subscriber 
                ? this.channel.subscribersCount -= 1 
                : this.channel.subscribersCount += 1;
              this.channel.subscriber = !this.channel.subscriber;
            }
          })
          .catch(err => this.$toasted.error('Something went wrong!'));
        
      },
      showSubscribersModal() {
        this.type = 'subscribers';
        return axios
          .get(`${location.origin}/user/${this.type}/${this.channel.userChannel.id}/list`)
          .then(res => {
            this.users = res.data;
          })
          .then(() => {
            this.isModalVisible = true;
          })
          .catch(err => this.$toasted.error('Something went wrong!'));
      },
      showSubscribtionsModal() {
        this.type = 'subscribtions';
        return axios
          .get(`${location.origin}/user/${this.type}/${this.channel.userChannel.id}/list`)
          .then(res => {
            this.users = res.data;
          })
          .then(() => {
            this.isModalVisible = true;
          })
          .catch(err => this.$toasted.error('Something went wrong!'));
      },
      closeModal() {
        this.isModalVisible = false;
      }
    }
    
  }
</script>

<style>
  
</style>