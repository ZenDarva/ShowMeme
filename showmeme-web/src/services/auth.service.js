import axios from 'axios'
import User from '../models/user'

const API_URL = window.location.origin+ '/api/auth/';

class AuthService {

  bareLogin(user) {
    return axios.post(API_URL + 'signin', {
      username: user.username,
      password: user.password
    })
  }

  logout () {
    localStorage.removeItem('user')
  }

  register (user) {
    return axios.post(API_URL + 'register', {
      username: user.username,
      email: user.email,
      password: user.password
    })
  }
  storeUser(userToken){
    var remoteUser = new User()
    remoteUser.fromToken(userToken)
    localStorage.setItem("user", JSON.stringify(userToken))
  }

  hasUser(){
    return localStorage.getItem("user") != null
  }

  getUser(){
    var remoteUser = new User().fromToken(localStorage.getItem("user"));
  }
}

export default new AuthService()
