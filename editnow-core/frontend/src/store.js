import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        userName: null
    },
    mutations: {

    },
    actions: {
        login({commit}, {user, password}) {
            return new Promise((resolve, reject) => {

            })
        }
    },
    getters: {

    }
})