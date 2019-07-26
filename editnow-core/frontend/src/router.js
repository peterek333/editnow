import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'

import store from './store'

Vue.use(Router);

const router = new Router({
    mode: 'history',
    routes: [
        { path: '/', component: Hello },
        { path: '*', redirect: '/' }
    ]
});

// router.beforeEach((to, from, next) => {
//     if (to.matched.some(record => record.meta.requiresAuth)) {
//         if (!store.getters.isLoggedIn) {
//             next({
//                 path: '/login'
//             })
//         } else {
//             next();
//         }
//     } else {
//         next(); // make sure to always call next()!
//     }
// });

export default router;