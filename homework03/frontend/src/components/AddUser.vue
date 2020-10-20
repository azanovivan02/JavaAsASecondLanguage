<template>
    <div>
<!--        <b-card class="card-login">-->
<!--            <b-card-title>Register</b-card-title>-->
<!--            <b-form @submit="onSubmit" @reset="onReset">-->
<!--                <b-form-group-->
<!--                        id="input-group-1"-->
<!--                        label="Name: "-->
<!--                        label-for="input-1"-->
<!--                >-->
<!--                    <b-form-input id="input-1" v-model="form.userName" placeholder="Enter your name"></b-form-input>-->
<!--                </b-form-group>-->

<!--                <b-button type="submit" variant="primary">Submit</b-button>-->
<!--                <b-button type="reset" variant="danger">Reset</b-button>-->
<!--            </b-form>-->
<!--        </b-card>-->
<!--        <b-card class="mt-3" header="Form Data Result">-->
<!--            <pre class="m-0">{{ form }}</pre>-->
<!--        </b-card>-->
    </div>
</template>

<script>

    import axios from 'axios';

    export default {
        // name: 'AddUser',
        // data() {
        //     return {
        //         form: {
        //             userName: "",
        //         }
        //     }
        // },
        // methods: {
        //     onSubmit: function (event) {
        //         event.preventDefault()
        //         axios.post('/user/register', {
        //             "userName": this.form.userName
        //         }).then(response => {
        //             alert(JSON.stringify(response.data))
        //         }).catch(error => {
        //             console.log('ERROR: ' + error);
        //         })
        //         this.form.userToken = ''
        //         this.form.content = ''
        //     },
        //     onReset(evt) {
        //         evt.preventDefault()
        //         this.form.userName = ''
        //     }
        // }
    }

</script>

<style>
</style>