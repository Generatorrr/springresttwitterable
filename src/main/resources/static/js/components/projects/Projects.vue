<template>
    <div>
        <div class="form-row">
            <div class="form-group col-md-6">
                <form class="form-inline">
                    <input type="text" name="filter" placeholder="Поиск"  v-model="filter" />
                    <button v-on:click="getFilteredProjects()" class="btn btn-primary ml-2" type="button">Найти</button>
                </form>
            </div>
        </div>

        <v-simple-table>
            <template v-slot:default>
                <thead>
                <tr>
                    <th class="text-left">Project</th>
                    <th class="text-left">Initial Date</th>
                    <th class="text-left">End Date</th>
                    <th class="text-left">Modules count</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="item in projects" :key="item.name">
                    <td>{{ item.name }}</td>
                    <td>{{ item.initialDate }}</td>
                    <td>{{ item.endDate }}</td>
                    <td>{{ item.moduleCount }}</td>
                </tr>
                </tbody>
            </template>
        </v-simple-table>
    </div>
</template>

<script>
    import axios from 'axios'

    export default {
        components: {  },
        computed: {
            projects() {
                return this.$store.getters.getProjects;
            },
            profile() {
                return this.$store.getters.getProfile;
            },
            pageInfo() {
                return this.$store.getters.getPageInfo;
            }
        },
        mounted() {
            this.fetchProjects();
        },
        data() {
            return {
                filter: ""
            }
        },
        methods: {
            getFilteredProjects() {
                if (this.filter !== "") {
                    this.$store.commit('setProjectFilter', this.filter);
                }
                return axios
                    .get(`${location.origin}/project?page=${this.pageInfo.currentPage}&filter=${this.filter}`)
                    .then(response => {
                        this.$store.commit('setProjects', response.data.projects);
                        this.$store.commit('setPageInfo', response.data.page);
                    })
                    .catch(err => {
                        debugger
                        console.log(err);
                    });
            },
            fetchProjects() {
                return axios
                    .get(`${location.origin}/project?page=${this.pageInfo.currentPage}`)
                    .then(response => {
                        this.$store.commit('setProjects', response.data.projects);
                        this.projects = response.data.projects;
                    });
            }
        }
    }
</script>

<style>

</style>