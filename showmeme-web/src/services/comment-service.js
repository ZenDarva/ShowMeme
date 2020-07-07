import axios from 'axios'

import authHeader from './auth-header'
const API_URL = window.location.origin + '/api/comment'

class PostService {


  VoteComment (commentVote) {
    return axios.post(API_URL + '/vote', commentVote,
      {
        headers: authHeader({})
      }
    )
  }

  getComment(postId, commentId) {
    return axios.get(API_URL+"/"+postId+"/"+commentId,{
      headers: authHeader({})
    });
  }
  getCommentsForPost(postId) {
    return axios.get(API_URL + "/" + postId,{
      headers: authHeader({})
    })
  }
}

export default new PostService()
