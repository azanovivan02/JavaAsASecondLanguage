<template>
    <div id="view-flits">
        <template v-if="isImplemented">
            <h2 class="center-align mb-3">All flits</h2>
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
        <template v-else>
            <h2 class="center-align mb-3">You have not implemented it yet</h2>
            <p>Implement handle: "/flit/discover" to enable this page</p>
        </template>
    </div>
</template>

<script>

    import axios from 'axios';

    export default {
        name: 'ViewFlits',
        data() {
            return {
                flits: [],
                errorMessage: null,
                isImplemented: true
            }
        },
        methods: {
            load() {
                axios.get('/flit/discover')
                    .then(response => {
                        this.$data.flits = response.data.data;
                        this.$data.errorMessage = response.data.errorMessage;
                    })
                    .catch(error => {
                        if (error.response.status === 404) {
                            this.isImplemented = false
                        }
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