<template>
  <div>
    <h3>Hello, friend!!!</h3>
    <div>
      <p>
        This application is my first experience with Spring framework!!!
      </p>
      <div v-if="!profile">It is necessary to authorize with <a href="/login">Google</a></div>
      <div v-else>
        <div>{{profile.name}}&nbsp;<a href="/logout">Log Out</a></div>
      </div>

    </div>
  </div>
</template>

<script>
  export default {
      data: () => ({
          messages: frontendData.messages,
          profile: frontendData.profile
      })
  }
</script>

<style>

</style>