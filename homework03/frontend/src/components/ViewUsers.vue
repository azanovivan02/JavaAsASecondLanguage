<template>
    <div id="view-users">
        <h2 class="center-align mb-3">All users</h2>
        <div v-for="user in users" v-bind:key="user">
            <router-link :to="{ path: '/user', query: { user: user }}">
                <b-card
                        v-bind:title="user"
                        tag="article"
                        style="max-width: 20rem;"
                        class="mb-2"
                >
                </b-card>
            </router-link>
        </div>
    </div>
</template>

<script>

    import axios from 'axios';

    export default {
        name: 'ViewUsers.vue',
        data() {
            return {
                users: [],
                errorMessage: null
            }
        },
        methods: {
            load() {
                axios.get('/user/list')
                    .then(response => {
                        this.$data.users = response.data.data;
                        this.$data.errorMessage = response.data.errorMessage;
                    })
                    .catch(error => {
                        console.log('ERROR: ' + error.response.data);
                    })
            }
        },
        mounted() {
            this.load();
        }
    }
</script>

<style>
</style>