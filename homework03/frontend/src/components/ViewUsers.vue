<template>
    <div id="view-users">
        <h2 class="center-align mb-3">All users</h2>
        <div v-for="user in users" v-bind:key="user">
            <b-card
                    v-bind:title="user"
                    tag="article"
                    style="max-width: 20rem;"
                    class="mb-2"
            >
                <b-button-group>
                    <router-link :to="{ path: '/user', query: { user: user }}">
                        <b-button variant="success">View</b-button>
                    </router-link>
                    <b-form @submit="onSubscribe">
                        <b-button v-on:click="this.publisherName = user" type="submit" variant="primary">Subscribe
                        </b-button>
                    </b-form>
                </b-button-group>
            </b-card>
        </div>

        <b-modal ref="my-modal" hide-footer v-bind:title="modalTitle">
            <template v-if="modalMode === 'input'">
                <b-form @submit="onSubmit" @reset="onReset">
                    <b-form-group
                            id="input-group-1"
                            label="Token: "
                            label-for="input-1"
                    >
                        <b-form-input id="input-1" v-model="form.userToken"
                                      placeholder="Enter your token"></b-form-input>
                    </b-form-group>

                    <div class="mt-3 mb-3">
                        <b-button-group>
                            <b-button type="submit" variant="success">Submit</b-button>
                            <b-button type="reset" variant="dark">Reset</b-button>
                        </b-button-group>
                    </div>
                </b-form>
            </template>
            <template v-else>
                <div class="d-block text-center">
                    {{ modalMessage }}
                </div>
            </template>
        </b-modal>
    </div>
</template>

<script>

    import axios from 'axios';

    export default {
        name: 'ViewUsers.vue',
        data() {
            return {
                form: {
                    userToken: ''
                },
                publisherName: '',
                users: [],
                errorMessage: null,
                modalMode: "input",
                modalTitle: "",
                modalMessage: ""
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
            },
            showModalForm() {
                this.$refs['my-modal'].show()
            },
            onSubscribe: function (event) {
                event.preventDefault()
                this.modalTitle = "Subscribe"
                this.modalMode = "input"
                this.showModalForm()
            },
            onSubmit: function (event) {
                event.preventDefault()
                axios.post('/subscribe', {"subscriberToken": this.form.userToken, "publisherName": this.publisherName})
                    .then(response => {
                        this.modalMode = "response"
                        if (response.data.errorMessage != null) {
                            this.modalTitle = "Error"
                            this.modalMessage = response.data.errorMessage
                        } else {
                            this.modalTitle = "Success"
                            this.modalMessage = "Successfully subscribed"
                        }
                    })
                    .catch(error => {
                        console.log('ERROR: ' + error.response.data);
                    })
                this.form.userToken = ''
                this.publisherName = ''
            },
            onReset(evt) {
                evt.preventDefault()
                this.form.userToken = ''
                this.publisherName = ''
            }
        },
        mounted() {
            this.load();
        }
    }
</script>

<style>
</style>