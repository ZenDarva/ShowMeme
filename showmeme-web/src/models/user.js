import jwtDecode from 'jwt-decode'

export default class User {
    fromForm(username, email, password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles=[];
    }
    fromToken(token){
        var decoded = jwtDecode(token)

        this.username=decoded.sub;
        this.roles=decoded.roles;
        this.token=token;

        Object.assign(this,decoded);

        return this;
    }
}
