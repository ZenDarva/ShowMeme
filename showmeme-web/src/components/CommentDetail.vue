<template>
  <v-card shaped :color="this.getColor()">
    <v-card-subtitle class="pb-0 mb-0 pa-2 pl-3">{{myComment.postedBy}}</v-card-subtitle>
    <v-card-text class="pb-0 mb-0" align="right"><span v-html="myComment.body"/></v-card-text>
    <v-card-actions class="my-0 py-0">
      <v-row class="pt-0 ma-0">
        <v-col cols="3" class="py-0 my-0">
          <v-btn icon small class="py-0 my-0 px-1" @click="voteUp" :disabled="!authService.hasUser()">
            <v-icon small :color="upvoteColor()">fas fa-thumbs-up</v-icon>
          </v-btn>
          <span>{{myComment.votes}}</span>
          <v-btn icon small class="py-0 my-0 px-1" @click="voteDown" :disabled="!authService.hasUser()">
            <v-icon small :color="downvoteColor()">fas fa-thumbs-down</v-icon>
          </v-btn>
        </v-col>
        <v-col cols="1" offset="8" class="pt-0 my-0">
          <v-tooltip left class="py-0 my-0">
            <template v-slot:activator="{ on,attrs }">
              <v-btn icon v-bind="attrs" v-on="on" class="pt-0 my-0" @click="replying=!replying"  :disabled="!authService.hasUser()" >
                <v-icon>fas fa-comment</v-icon>
              </v-btn>
            </template>
            Reply
          </v-tooltip>
        </v-col>
      </v-row>
    </v-card-actions>

    <PostBox v-if="replying" v-bind:parentId="myComment.id" v-bind:postId="myComment.postId" v-on:commentSent="reThrow()"
             class="mb-0 pb-0"/>

    <v-container v-if="hasChildren()" class="mt-n5 pt-0">
      <v-btn v-if="!showChildren" icon x-small @click="showChildren=!showChildren">
        <v-icon x-small>far fa-plus-square</v-icon>
      </v-btn>
      <v-btn v-if="showChildren" icon x-small @click="showChildren=!showChildren">
        <v-icon x-small>far fa-minus-square</v-icon>
      </v-btn>
    </v-container>

    <v-container v-if="showChildren">
      <CommentDetail class="ma-1" v-for="item in myComment.children" :key="item.id" v-bind:comment="item" v-bind:color="color-10"></CommentDetail>
    </v-container>

  </v-card>
</template>

<script>
  import auth from '../services/auth.service.js'
  import PostBox from './PostBox'
  import commentService from "../services/comment-service"
  import colors from 'vuetify/lib/util/colors'

  export default {
    name: 'CommentDetail',
    data () {
      return {
        newReply: '',
        replying: false,
        authService: auth,
        showChildren: false,
        myComment:this.comment
      }
    },
    methods: {
      upvoteColor(){
        if (this.myComment.userVote == 1){
          return colors.green.base
        }
          return colors.grey.darken1
      },
      downvoteColor(){
        if (this.myComment.userVote == -1){
          return colors.red.base
        }
        return colors.grey.darken1
      },
      voteUp(){
        commentService.VoteComment({
          vote:1,
          commentId:this.myComment.id
        }).then(
          unused=>{
            commentService.getComment(this.myComment.id).then(
              response=>{
                this.myComment=response.data
              }
            )
          },
          error=>{},
        )
      },
      voteDown(){
        commentService.VoteComment({
          vote:-1,
          commentId:this.myComment.id
        }).then(
          unused=>{
            commentService.getComment(this.myComment.id).then(
              response=>{
                this.myComment=response.data
              }
            )
          },
          error=>{},
        )
      },

      hasChildren () {
        if (this.myComment.children.length==0) {
          return false
        }
        return true
      },
      reThrow () {
        this.$emit('commentSent')
      },
      getColor(){
        let adjustColor= Math.max(130,this.color)
        return this.RGBToHex(adjustColor,adjustColor,adjustColor)
      },
      RGBToHex (r, g, b) {
        r = r.toString(16)
        g = g.toString(16)
        b = b.toString(16)

        if (r.length == 1) {
          r = '0' + r
        }
        if (g.length == 1) {
          g = '0' + g
        }
        if (b.length == 1) {
          b = '0' + b
        }

        return '#' + r + g + b
      }

    },
    props: {
      comment: Object,
      color: Number
    },
    components: {
      PostBox,
    },
  }
</script>
