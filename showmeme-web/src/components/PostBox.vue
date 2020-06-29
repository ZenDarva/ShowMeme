<template>
  <v-row dense align="center">
    <v-col cols="12">
      <tiptap-vuetify v-model="postBody" placeholder="Add a comment" class="px-2"></tiptap-vuetify>
    </v-col>
    <v-col cols="1"></v-col>
    <v-col cols="1" offset="9">
      <v-btn tile class="text-none ml-6" small :disabled="this.typedPost()" @click="createComment">
        Post
      </v-btn>
    </v-col>
  </v-row>
</template>
<script>

  import { TiptapVuetify } from 'tiptap-vuetify'
  import Comment from '../models/comment'
  import PostService from '../services/post-service'

  export default {
    components: {
      TiptapVuetify
    },
    props: {
      parentId: String,
      postId: String,
    },
    data () {
      return {
        postBody: '',
        message: ''
      }
    },
    computed: {

    },
    methods: {
      typedPost () {

        if (this.postBody.length == 0 || this.postBody == '<p></p>') {
          return true
        }
        return false
      },
      getParentId () {
        if (typeof this.parentId == 'undefined') {
          return null
        }
        return this.parentId
      },
      getPostId () {
        if (typeof this.postId == 'undefined') {
          return null
        }
        return this.postId
      },
      comment () {

      },
      createComment () {
        let cmnt = new Comment()
        cmnt.body = this.postBody
        cmnt.parentCommentId = this.getParentId()
        cmnt.postId = this.getPostId()
        PostService.createComment(cmnt).then(
          response => {
            this.postBody = ''
            this.$emit("commentSent")
          },
          error => {
            this.message =
              (error.response && error.response.data) ||
              error.message ||
              error.toString()
          }
        )

      }
    }
  }

</script>
