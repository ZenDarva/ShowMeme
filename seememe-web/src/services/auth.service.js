import axios from 'axios';
import User from '../models/user';

const API_URL = 'http://localhost:4000/api/auth/';

class AuthService {
  login(user) {
    return axios
      .post(API_URL + 'signin', {
        username: user.username,
        password: user.password
      })
      .then(response => {
        if (response.data.accessToken) {
          var remoteUser = new User();
          remoteUser.fromToken(response.data.accessToken)
          localStorage.setItem('user', JSON.stringify(remoteUser));
          return remoteUser;
        }

        return response.data;
      });
  }

  logout() {
    localStorage.removeItem('user');
  }

  register(user) {
    return axios.post(API_URL + 'register', {
      username: user.username,
      email: user.email,
      password: user.password
    });
  }
}

export default new AuthService();
