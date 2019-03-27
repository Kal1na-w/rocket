<template>
    <div>
        <h1>Test Group Create</h1>
        <form class="user-add" @submit.prevent @submit="createGroup">
            <input type="text" v-model="Course.name" placeholder="Username"/>
            <button type="submit">Add!</button>
        </form>
    </div>
</template>

<script>
    import axios from 'axios';
    export default {
        name: 'CourseCreateComponent',

        data() {
            return {
                Course: {
                    name: ''
                }
            }
        },

        methods: {
            createGroup() {
                axios.post(window.$host+'courses',this.Course)
                    .then((result) => {
                        console.log(result)
                    })
            }
        },

        components: {
        }
    }
</script>

<style>

</style>