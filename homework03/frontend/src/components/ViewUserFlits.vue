<template>
    <div id="view-flits">
        <h2 class="center-align mb-3">Flits from {{ this.$route.query.user }}</h2>
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
    </div>
</template>

<script>

    import axios from 'axios';

    export default {
        name: 'ViewUserFlits',
        data() {
            return {
                flits: [],
                errorMessage: null
            }
        },
        methods: {
            load() {
                const currentUserName = this.$route.query.user;
                axios.get('flit/list/'+currentUserName)
                    .then(response => {
                        this.$data.flits = response.data.data;
                        this.$data.errorMessage = response.data.errorMessage;
                    })
                    .catch(error => {
                        console.log('ERROR: ' + error.response.errorMessage);
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