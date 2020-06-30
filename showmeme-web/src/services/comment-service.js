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

  getComment(commentId) {
    return axios.get(API_URL+"/"+commentId,{
      headers: authHeader({})
    });
  }
}

export default new PostService()
