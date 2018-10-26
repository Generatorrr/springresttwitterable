<template>
    <div>
        <h3>Hello, friend!!!</h3>
        <div>
            <p>
                This application is my first experience with Spring framework!!!
            </p>
            <p v-if="!getProfile">It is necessary to authorize with your Google account</p>
        </div>
    </div>
</template>

<script>
    import { mapGetters } from 'vuex'
    export default {
        computed: {
            ...mapGetters([
                'getProfile'
            ])
        }
    }
</script>

<style>

</style>