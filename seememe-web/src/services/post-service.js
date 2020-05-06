import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:4000/api/post';

class postService {
    createPost(formData){
        axios.post(API_URL+"/createPost", formData,
            {
                headers: {
                    'Content-Type': 'multipart/form-data',

                }
            }
            ).then(function(){
                console.log("Success!");
        })
            .catch(function(){
                console.log("Failure!")
            })
    }

    getPosts(){
        return axios.get(API_URL+"/getNewest", authHeader())
    }
}

export default new postService();