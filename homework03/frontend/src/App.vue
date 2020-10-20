<template>
    <b-app id="app">
        <b-navbar style="width: 100%" type="dark" variant="dark">
            <router-link to="/register" class="nav-link text-light">Register</router-link>
            <router-link to="/add" class="nav-link text-light">Add flit</router-link>
            <router-link to="/discover" class="nav-link text-light">Discover flits</router-link>
            <router-link to="/users" class="nav-link text-light">View users</router-link>
        </b-navbar>
        <b-main>
            <b-container class="back" fill-height fluid>
                <b-row class="mt-5 mb-5">
                    <b-col></b-col>
                    <b-col>
                        <router-view></router-view>
                    </b-col>
                    <b-col></b-col>
                </b-row>
            </b-container>
        </b-main>
    </b-app>
</template>

<script>

    export default {
        name: 'app',
        methods: {},
    }
</script>

<style>
    /*@import 'https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css';*/
</style>
