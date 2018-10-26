<template>
  <div>
    <nav-bar :profile="profile"></nav-bar>
    <div class="container mt-5">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
  import NavBar from 'components/navbar/NavBar.vue'
  import GreetingsBlock from 'components/greetings/Greetings.vue'
  export default {
      components: {
        NavBar, GreetingsBlock
      },
      data: () => {
          return {
              messages: frontendData.messages,
              profile: frontendData.profile
          }
      },
      mounted() {
          this.commitProfile();
      },
      methods: {
          commitProfile() {
              this.$store.commit('setProfile', frontendData.profile);
              this.$store.commit('setMessages', frontendData.messages);
          }
      }
  }
</script>

<style>

</style>