<template>
    <div>
        <template v-if="isImplemented">
            <template v-if="mode === 'input'">
                <b-card class="card-login">
                    <b-card-title>View feed for user</b-card-title>
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
                </b-card>
            </template>
            <template v-else>
                <b-form @reset="onReset">
                    <b-button type="reset" variant="dark">Reset</b-button>
                </b-form>
                <h2 class="center-align mb-3">Your feed</h2>
                <div v-for="flit in flits" v-bind:key="flit">
                    <b-card
                            v-bind:title="flit.userName"
                            tag="article"
                            style="max-width: 20rem;"
                            class="mb-2"
                    >
                        <b-card-text>
                            {{flit.content}}
                        </b-card-text>
                    </b-card>
                </div>
            </template>
        </template>
        <template v-else>
            <h2 class="center-align mb-3">You have not implemented it yet</h2>
            <p>Implement handle: "list/feed/{userToken}" to enable this page</p>
        </template>

        <b-modal ref="my-modal" hide-footer v-bind:title="modalTitle">
            <div class="d-block text-center">
                {{ message }}
            </div>
        </b-modal>
    </div>
</template>

<script>

    import axios from 'axios';

    export default {
        name: 'ViewFeed',
        data() {
            return {
                form: {
                    userToken: ""
                },
                modalTitle: "",
                message: "",
                flits: [],
                mode: "input",
                isImplemented: true
            }
        },
        methods: {
            showModal(responseData) {
                if (responseData.errorMessage != null) {
                    this.modalTitle = "Error"
                    this.message = responseData.errorMessage
                } else {
                    this.modalTitle = "Success"
                    this.message = "You can view feed!"
                }
                this.$refs['my-modal'].show()
            },
            onSubmit: function (event) {
                event.preventDefault()
                axios.get('/flit/list/feed/' + this.form.userToken)
                    .then(response => {
                        this.flits = response.data.data
                        this.mode = "feed"
                    }).catch(error => {
                    if (error.response.status === 404) {
                        this.isImplemented = false
                    } else if (error.response) {
                        this.showModal(error.response.data)
                    }
                    console.log('ERROR: ' + error);
                })
                this.form.userToken = ''
            },
            onReset(evt) {
                evt.preventDefault()
                this.mode = "input"
                this.form.userToken = ''
                this.flits = []
            }
        }
    }

</script>

<style>
</style>