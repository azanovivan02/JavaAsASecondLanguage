<template>
    <div>
        <template v-if="isImplemented">
            <b-card class="card-login">
                <b-card-title>Register new user</b-card-title>
                <b-form @submit="onSubmit" @reset="onReset">
                    <b-form-group
                            id="input-group-1"
                            label="Name: "
                            label-for="input-1"
                    >
                        <b-form-input id="input-1" v-model="form.userName" placeholder="Enter your name"></b-form-input>
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
            <h2 class="center-align mb-3">You have not implemented it yet</h2>
            <p>Implement handle: "/user/register" to enable this page</p>
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
        name: 'AddUser',
        data() {
            return {
                form: {
                    userName: ""
                },
                modalTitle: "",
                message: "",
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
                    this.message = "Your new token: " + responseData.data.userToken
                }
                this.$refs['my-modal'].show()
            },
            onSubmit: function (event) {
                event.preventDefault()
                axios.post('/user/register', {
                    "userName": this.form.userName,
                }).then(response => {
                    this.showModal(response.data)
                }).catch(error => {
                    if (error.response.status === 404) {
                        this.isImplemented = false
                    } else if (error.response) {
                        this.showModal(error.response.data)
                    }
                    console.log('ERROR: ' + error);
                })
                this.form.userName = ''
            },
            onReset(evt) {
                evt.preventDefault()
                this.form.userName = ''
            }
        }
    }

</script>

<style>
</style>