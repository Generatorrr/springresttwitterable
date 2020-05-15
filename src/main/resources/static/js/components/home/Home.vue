<template>
    <div v-if="!getProfile">
        <h3>Hello, friend!!!</h3>
        <div>
            <p>It is necessary to authorize with your Google account</p>
        </div>
    </div>
    <messages v-else></messages>
</template>

<script>
    import { mapGetters } from 'vuex'
    import Messages from '../messages/Messages.vue'
    export default {
        computed: {
            ...mapGetters([
                'getProfile'
            ])
        },
        components: { Messages }
    }
</script>

<style>

</style>