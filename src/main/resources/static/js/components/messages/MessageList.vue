<template>
  <div>
    <div>Messages list:</div>
    <div class="card-columns">
      <ul v-if="messages && messages.length">
        <li v-for="message in messages">
          <div class="card my-3">
            <div>
              <img v-if="message.filename" class="card-img-top" :src="`/img/${message.filename}`" :alt="message.filename"
                style="max-width: 250px;max-height: 150px;"/>
            </div>
            <div class="m-2">
              <span>{{message.text}}</span><br>
              <i>#{{message.tag}}</i>
            </div>
          </div>
          <div class="card-footer text-muted">
            <a class="pr-5" :href="`/user-messages/${message.author.id}`">{{message.author.name}}</a>
            <b><span v-if="message.edited">edited</span></b>
            <a v-if="message.author.id == profile.id" class="btn btn-primary"
               :href="`/user-messages/${message.author.id}?message=${message.id}`">Edit</a>
          </div>
        </li>
      </ul>
      <p v-else>No messages found</p>
    </div>
  </div>
</template>

<script>
  export default {
    props: [ 'messages', 'profile' ],
    
  }
  
</script>

<style>
  
</style>