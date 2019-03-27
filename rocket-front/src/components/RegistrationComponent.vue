<template>
    <div>
        <h1>Test Group Create</h1>
        <form class="user-add" @submit.prevent @submit="registerUser">
            <input type="text" v-model="User.username" placeholder="Username"/>
            <input type="text" v-model="User.password" placeholder="Password"/>
            <button type="submit">Register!</button>
        </form>
    </div>
</template>

<script>
    import axios from 'axios';
    export default {
        name: 'RegistrationComponent',

        data() {
            return {
                User: {
                    username: '',
                    password: ''
                }
            }
        },

        methods: {
            registerUser() {
                axios.post(window.$host+'registration',this.User)
                    .then((result) => {
                        console.log(result)
                    }).catch((err) => console.error([err]))
            }
        },

        components: {
        }
    }
</script>

<style>

</style>