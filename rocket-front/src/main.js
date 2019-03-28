import Vue from 'vue';
import Router from 'vue-router';


import App from './App.vue';
import router from './router';

window.$host = 'http://127.0.0.1:9000/api/';

Vue.use(Router);


new Vue({
    router,
    render: h => h(App),
}).$mount('#app');