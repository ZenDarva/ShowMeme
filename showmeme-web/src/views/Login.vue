<template>
  <div class="login">
    <v-container>
      <v-row justify="center">
        <v-col cols="8">
          <v-tabs right v-on:change="clearInput">
            <v-tab>Login</v-tab>
            <v-tab>Register</v-tab>
            <v-tab-item>
              <v-card>
                <v-form>
                  <v-text-field label="User Name" v-model="user.username" required class="ma-2 pt-5">
                  </v-text-field>
                  <v-text-field label="Password" v-model="user.password" required type="password" class="ma-2">
                  </v-text-field>
                </v-form>
                <v-row justify="end">
                  <v-btn class="mb-2 mr-4" @click="sendLogin">Login</v-btn>
                </v-row>
              </v-card>
            </v-tab-item>
            <v-tab-item>
              <v-card>
                <v-form>
                  <v-text-field label="Email Address" v-model="user.email" required class="ma-2 pt-5"/>
                  <v-text-field label="User Name" v-model="user.username" required class="ma-2"/>
                  <v-text-field label="Password" v-model="user.password" required type="password" class="ma-2"/>
                </v-form>
                <v-row justify="end">
                  <v-btn class="mb-2 mr-4" @click="sendRegister">Register</v-btn>
                </v-row>
              </v-card>
            </v-tab-item>
          </v-tabs>
<!--          <div v-if="message" class="alert alert-danger" role="alert">{{message}}</div>-->
          <v-alert v-if="message" type="error" class="text-center">{{message}}</v-alert>
        </v-col>
      </v-row>
    </v-container>

  </div>
</template>

<script>
  import auth from "../services/auth.service.js"
  import User from "../models/user.js"

  export default {
    data () {
      return {
        user: new User(),
        message:'',
      }
    },
    methods: {
      clearInput(){
        console.log("Called")
        this.user={};
      },
      sendRegister(){
        auth.register(this.user).then(
          data => {
            this.message = data.message;
            this.successful = true;
          },
          error => {
            this.message =
              (error.response && error.response.data) ||
              error.message ||
              error.toString();
            this.successful = false;
          }
        );
      },
      sendLogin(){
        auth.bareLogin(this.user).then(
          response => {
            this.message = response.message;
            this.successful=true;
            auth.storeUser(response.data.accessToken)
            this.user={}
            this.$router.push("/")
          },
          error => {
            this.message = error.response.data;
            this.successful=false;
          }
        )
      }
    }
  }
</script>
