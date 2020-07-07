<template>
  <v-app>
    <v-app-bar
      app
      color="primary"
      dark
    >
      <v-btn to="/">Feed</v-btn>
      <v-btn to='/Upload'>Upload</v-btn>
      <v-spacer></v-spacer>
      <v-btn @click="logout" v-if="this.loggedIn()">Logout</v-btn>
      <v-btn to="/login" v-else>Login</v-btn>


    </v-app-bar>

    <v-content>
      <router-view>
      </router-view>
    </v-content>
  </v-app>
</template>

<script>
  import auth from './services/auth.service.js'

  export default {

    name: 'App',
    computed: {

    },
    data() {
      return {
        currentUser () {
          return auth.getUser();
        },
        loggedIn(){
          return auth.hasUser()
        }
      }
    },
    methods: {
      logout () {
        auth.logout();
        this.$forceUpdate()
        window.location.reload()
      }
    }

  }
</script>
