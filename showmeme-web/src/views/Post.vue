<template>
  <div class="post">
    <v-container fluid>
      <v-row dense>
        <v-col
          v-for="item in content.imageHashes"
          :key="item.id"
          cols="8"
          offset="1"
        >
          <v-card class="mt-1 mb-1">
            <v-img :src="urlFromHash(item)"></v-img>
          </v-card>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="1"></v-col>
        <v-col cols="8">
          <span v-html="content.description"></span>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="8" offset="1">
          <PostBox v-if="this.loggedIn" v-bind:postId="this.$route.params.id"
                   v-on:commentSent="updateContent"></PostBox>
        </v-col>
      </v-row>
      <CommentSection v-if="this.hasComment" v-bind:comments="this.comments" v-on:commentSent="updateContent"/>
    </v-container>
  </div>
</template>

<script>
  import CommentSection from '../components/CommentSection'
  import PostService from '../services/post-service.js'
  import auth from '../services/auth.service.js'
  import Comment from '../models/comment'
  import PostBox from '../components/PostBox'
  import commentService from '../services/comment-service'

  export default {
    name: 'Home',
    components: {
      PostBox,
      CommentSection
    },
    methods: {
      urlFromHash (id) {
        var full = location.protocol + '//' + location.hostname + (location.port ? ':' + location.port : '')
        return full + '/api/post/image/' + id
      },
      updateContent () {
        commentService.getCommentsForPost(this.$route.params.id).then(comments =>
          this.comments = comments.data)
      }

    },
    data () {
      return {
        content: {},
        newComment: '',
        comments: [],
        loggedIn () {
          return auth.hasUser()
        },
        typedPost () {
          if (this.newComment.length == 0 || this.newComment == '<p></p>') {
            return true
          }
          return false
        },
        hasComment () {
          if (Object.keys(this.content).length == 0 || Object.keys(this.content.comments).length == 0) {
            return false
          }
          return true
        }
      }
    },
    mounted () {
      PostService.getPost(this.$route.params.id).then(response => {
        this.content = response.data
      })
      commentService.getCommentsForPost(this.$route.params.id).then(comments =>
        this.comments = comments.data)
    },


  }
</script>
