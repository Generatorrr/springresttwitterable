<template>
    <div>
        <v-row class="ma-1">
            <v-col cols="12" md="6">
                <v-text-field
                        :label="entityName"
                        :rules="rules"
                        v-model="nameValue"
                ></v-text-field>
            </v-col>
        </v-row>
    </div>
</template>

<script>
    import constants from "../../constants";
    export default {
        props: [ 'entityName', 'nameForUpdate' ],
        components: {},
        computed: {
        },
        mounted() {
        },
        data() {
            return {
                nameValue: null,
                rules: [
                    value => !!value || constants.REQUIRED_FIELD,
                    value => (value && value.length >= 3) || constants.MIN_3_CHARS_FIELD,
                ]
            }
        },
        watch: {
            nameValue: function (to, from) {

                for (let i = 0; i < this.rules.length; i++) {
                    if (this.rules[i](to) !== true) {
                        this.$store.commit('setName', null);
                        this.$parent.checkForDisable();
                        return;
                    }
                }
                this.$store.commit('setName', to);
                this.$parent.checkForDisable();
            },
            nameForUpdate: function (to, from) {
                this.nameValue = to;
            }
        },
        methods: {
        }
    }
</script>

<style lang="scss">
</style>