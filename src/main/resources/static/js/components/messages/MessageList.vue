<template>
  <div>
    <div>Messages list:</div>
    <div class="card-columns">
      <div v-if="messages && messages.length">
        <div class="card my-3" v-for="message in messages">
          <div v-if="message.filename">
            <img class="card-img-top" :src="`/img/${message.filename}`" :alt="message.filename"
                 style="max-width: 250px;max-height: 150px;"/>
          </div>
          <div class="m-2">
            <span>{{message.text}}</span><br>
            <i>#{{message.tag}}</i>
          </div>
          <div class="card-footer text-muted message-footer_flex-block__parent">
            <div class="message-footer_flex-block" v-bind:class="{ directColumn: message.author.id !== profile.id }">
              <img v-if="message.author && message.author.userpic" :src="message.author.userpic" alt="User's avatar" />
              <router-link :to="`/user-messages/${message.author.id}`">{{message.author.name}}</router-link>
              <b><span v-if="message.edited">edited</span></b>
            </div>
            
            <a v-if="message.author.id === profile.id" class="btn btn-primary"
                         v-on:click="bindMessageForEdit(message)">Edit</a>
          </div>
        </div>
      </div>
      <p v-else>No messages found</p>
    </div>
  </div>
</template>

<script>
  export default {
    props: [ 'messages', 'profile' ],
    data() {
      return {
        message: this.message
      }
    },
    methods: {
      bindMessageForEdit(item) {
        this.$store.commit('setEditingMessage', item);
      }
    }
  }
  
</script>

<style lang="scss">
  
  .message-footer_flex-block__parent {
    display: flex;
    justify-content: space-between;
    align-items: center;
    .message-footer_flex-block {
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      align-items: center;
      width: 50%;
      max-width: 50%;
      word-break: keep-all;
      img {
        max-width: 70px;
        max-height: 70px;
        border-radius: 50%;
      }
    }
    .directColumn {
      flex-direction: column;
    }
  }  
</style>