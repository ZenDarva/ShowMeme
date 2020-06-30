import axios from 'axios'
import authHeader from './auth-header'

const API_URL = window.location.origin + '/api/post'

class PostService {
  createPost (formData) {
    axios.post(API_URL + '/createPost', formData,
      {
        headers: authHeader({
          'Content-Type': 'multipart/form-data',
        })
      }
    ).then(function () {
      console.log('Success!')
    })
      .catch(function () {
        console.log('Failure!')
      })
  }

  getPosts () {
    return axios.get(API_URL + '/getNewest')
  }

  getPost(id) {
      return axios.get(API_URL + "/post/"+id)
  }

  createComment(comment){
      return axios.post(API_URL + "/addComment", comment,{
        headers: authHeader({})
      });
  }


}

export default new PostService()
