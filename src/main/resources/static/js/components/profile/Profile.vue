<template>
    <div>
        <!--${message?ifExists}-->
            <div class="form-group">
                <label class="col-sm-2 col-form-label"> Name: </label>
                <div class="col-sm-6">
                    <input class="form-control" type="text" name="name" :placeholder="getProfile.name" v-model="updatedName" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 col-form-label"> Email: </label>
                <div class="col-sm-6">
                    <input class="form-control" type="email" name="email" :placeholder="getProfile.email" v-model="updatedEmail" />
                </div>
            </div>
            <!--<input type="hidden" name="_csrf" value="${_csrf.token}" />-->
            <button v-on:click="updateProfile()" class="btn btn-primary">Save</button>
    </div>
</template>

<script>
    import { mapGetters } from 'vuex'
    import axios from 'axios'
    export default {
        computed: {
            ...mapGetters([
                'getProfile'
            ])
        },
        data() {
          return {
              updatedName: null,
              updatedEmail: null
          }
        },
        methods: {
            commitUpdatedProfile(data) {
                this.$store.commit('setProfile', data);
            },
            updateProfile() {
                debugger;
                axios
                    .post(location.origin + this.$route.path, {
                        name: this.updatedName,
                        email: this.updatedEmail
                    })
                    .then(() => {
                        this.commitUpdatedProfile({
                            ...this.getProfile,
                            name: this.updatedName,
                            email: this.updatedEmail
                        });
                    })
                    .catch(err => {
                        console.log(err);
                    });
            },
        }
    }
</script>

<style>

</style>