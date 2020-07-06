<template>
<v-card>
  <editor-content class="editor-box px-2" :editor="editor" />
</v-card>
</template>

<script>
  import { Editor, EditorContent, EditorMenuBar  } from 'tiptap';

  export default {
    components:{
      TipTapEditor,
      EditorContent
    },
    props:{
      content: String,
      promptText: String
    },
    data() {
      return {
        editor: new Editor({
          content: this.postBody
        }),
      }
    }
  }

</script>

<style >
  .editor-box> * {
    border-color: grey;
    border-style: solid;
    border-width: 1px;
    padding-left: 5px;

  }

  .is-active{
    border-color: grey;
    border-style: solid;
    border-width: 1px;
  }
  /* *:focus {
     outline: none;
 }  */
</style>
