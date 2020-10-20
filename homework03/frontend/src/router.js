import Vue from 'vue'
import Router from 'vue-router'

import ViewFlits from "./components/ViewFlits";
import AddFlit from "./components/AddFlit";
import Home from '@/components/Home'
import ViewUsers from "./components/ViewUsers";
import AddUser from "./components/AddUser";
import ViewUserFlits from "./components/ViewUserFlits";
import ViewFeed from "./components/ViewFeed";

Vue.use(Router)

export default new Router({
    mode: 'history',
    routes: [
      {
        path: '/',
        name: 'Home',
        component: Home
      },
      {
        path: '/discover',
        name: 'ViewFlits',
        component: ViewFlits
      },
      {
        path: '/user',
        name: 'ViewUserFlits',
        component: ViewUserFlits
      },
      {
        path: '/users',
        name: 'ViewUsers',
        component: ViewUsers
      },
      {
        path: '/add',
        name: 'AddFlit',
        component: AddFlit
      },
      {
        path: '/register',
        name: 'AddUser',
        component: AddUser
      },
      {
        path: '/feed',
        name: 'ViewFeed',
        component: ViewFeed
      }
    ]
})