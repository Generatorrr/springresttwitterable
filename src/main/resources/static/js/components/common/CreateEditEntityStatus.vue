<template>
    <div>
        <v-row class="ma-1">
            <v-col class="d-flex" cols="12" sm="6">
                <v-select
                        :items="items"
                        :label="entityName"
                        v-model="selectedValue"
                ></v-select>
            </v-col>
        </v-row>
    </div>
</template>

<script>

    export default {
        props: [ 'entityName', 'statusForUpdate' ],
        components: {},
        computed: {
        },
        mounted() {
        },
        data() {
            return {
                items: [ 'TO_DO', 'IN_PROGRESS', 'IN_ANALYSIS', 'NEED_CLARIFY', 'READY_FOR_QA', 'IN_TEST', 'DONE', 'CANCELLED' ],
                selectedValue: null
            }
        },
        watch: {
            selectedValue: function (to, from) {

                this.$store.commit('setStatus', to);
                this.$parent.checkForDisable();
            },
            statusForUpdate: function (to, from) {
                this.selectedValue = to;
            }
        },
        methods: {
        }
    }
</script>

<style lang="scss">
</style>