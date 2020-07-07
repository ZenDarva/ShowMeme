
<template>
  <v-container  >
    <v-row >
      <v-col v-for="item in comments" :key="item.id" cols="8" offset="1" class="pb-1 pa-1">
        <CommentDetail v-bind:comment="item" :color=245 v-on:commentSent="rethrow()"/>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
  import CommentDetail from "./CommentDetail"
  export default {
    name: 'CommentSection',
    props: {
      comments: Array
    },
    components: {
      CommentDetail
    },
    methods:{
      rethrow(){
        this.$emit("commentSent")
      }
    }
  };
</script>
