<template>
    <div>
        <div v-bind:key="course.id" v-for="course in Courses">
            <h1>{{course.name}}</h1>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';

    export default {
        name: 'CourseListComponent',

        data() {
            return {
                Courses: []
            }
        },

        mounted() {
          this.getCourses();
        },

        methods: {
            getCourses() {
                axios.get(window.$host + 'courses')
                    .then(
                        result => this.Courses = result.data._embedded.courses
                    )
            }
        },

        components: {
        }
    }
</script>

<style>

</style>