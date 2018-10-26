<template>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="/">Twitterable</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <router-link class="nav-link" to="/">Home</router-link>
                </li>
                <li v-if="profile && profile.roles.indexOf('ADMIN') !== -1" class="nav-item">
                    <a class="nav-link" href="/user">User List</a>
                </li>
                <li v-if="profile" class="nav-item">
                    <router-link class="nav-link" to="/message">Messages</router-link>
                </li>
                <li v-if="profile" class="nav-item">
                    <router-link class="nav-link" to="/user/profile">Profile</router-link>
                </li>
                <li v-if="profile" class="nav-item">
                    <a class="nav-link" :href="`/user-messages/${profile.id}`">My messages</a>
                </li>
            </ul>
            <div v-if="profile" class="navbar-text mr-3">{{profile.name}}</div>
            <div v-if="profile">
                <a class="btn btn-primary" href="/logout">Log Out</a>
            </div>
            <div v-else>
                <a class="btn btn-primary" href="/login">Sign In</a>
            </div>
        </div>
    </nav>
</template>

<script>
    export default {
        computed: {
            profile() {
                return this.$store.getters.getProfile;
            }
        }
    }
</script>

<style>

</style>