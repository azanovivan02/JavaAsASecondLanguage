<template>
    <div>
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
                }
            }
        },
        methods: {
            onSubmit: function (event) {
                event.preventDefault()
                axios.post('/user/register', {
                    "userName": this.form.userName,
                }).then(response => {
                    alert(JSON.stringify(response.data))
                }).catch(error => {
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