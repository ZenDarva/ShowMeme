import Vue from 'vue';
import Router from 'vue-router';

import Feed from "./views/Feed";
import Register from "./views/Register";
import Login from "./views/Login";
import Upload from "./views/Upload";

Vue.use(Router);

export const router = new Router({
    mode: 'history',
    routes: [
        {
            path: '/home',
            name: 'feed',
            component: Feed
        },
        {
            path: '/register',
            name: 'register',
            component: Register
        },
        {
            path: '/login',
            name: 'login',
            component: Login
        },
        {
            path: '/upload',
            name: 'upload',
            component: Upload
        }
    ]
});

// router.beforeEach((to, from, next) => {
//   const publicPages = ['/login', '/register', '/home'];
//   const authRequired = !publicPages.includes(to.path);
//   const loggedIn = localStorage.getItem('user');

//   // trying to access a restricted page + not logged in
//   // redirect to login page
//   if (authRequired && !loggedIn) {
//     next('/login');
//   } else {
//     next();
//   }
// });
