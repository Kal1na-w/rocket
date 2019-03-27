import Router from 'vue-router';

import GroupListComponent from '../components/course/CourseListComponent';
import GroupCreateComponent from '../components/course/CourseCreateComponent';
import RegistrationComponent from '../components/RegistrationComponent'

let router = new Router({
    mode: 'history',
    routes: [
        {
            path: '/groups',
            name: 'GroupListComponent',
            component: GroupListComponent
        },
        {
            path: '/groups/create',
            name: 'GroupCreateComponent',
            component: GroupCreateComponent
        },
        {
            path: '/registration',
            name: 'Registration',
            component: RegistrationComponent
        }
    ]}
);

export default router;