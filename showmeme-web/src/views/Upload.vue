<template>
  <div>
    <v-container class="justify-center">
      <v-text-field label="Post Title" v-model="postTitle" :rules="[rules.notEmpty]" required>
      </v-text-field>
      <li v-for="image in files" :key="image.file.name" style="list-style-type: none">
        <v-row class="flex -align-center ma-0" no-gutters>
          <v-col cols="4"></v-col>
          <v-col cols="4">
            <v-img :src="image.image" class="ma-0"></v-img>
          </v-col>
          <v-col cols="4"></v-col>
        </v-row>
      </li>
      <tiptap-vuetify
        v-model="description"
        :hidden="uploadDisabled"
        placeholder="Description"

      />
      <div class="text-center">
        <v-btn class="text-none ma-2"
               rounded
               :loading="isSelecting"
               @click="onButtonClick"
        >Add File
        </v-btn>
        <v-spacer></v-spacer>

        <v-btn class="text-none ma-2"
               rounded
               :disabled="uploadDisabled"
               @click="submitFile">
          Upload
        </v-btn>
      </div>
      <input
        ref="uploader"
        class="d-none"
        type="file"
        accept="image/*"
        @change="onFileChanged"
      >
    </v-container>
  </div>
</template>

<script>
  import PostService from '../services/post-service.js'
  import auth from '../services/auth.service'
  import { TiptapVuetify } from 'tiptap-vuetify'

  export default {
    components: { TiptapVuetify },
    data () {
      return {
        files: [],
        isSelecting: false,
        rules: {
          notEmpty: value => (value !== null && value !== '') || 'You must have a Title!'
        },
        postTitle: '',
        description: ''
      }
    },
    computed: {
      uploadDisabled: function () {
        if (this.files.length <= 0) {
          return true
        }
        if (this.postTitle == null || this.postTitle == '') {
          return true
        }
        return false
      },
    },
    methods: {
      submitFile () {


        let formData = new FormData()
        let PostDTO = {
          'title': this.postTitle,
          'description': this.description,
          'altTexts': []
        }

        for (let filesKey in this.files) {
          formData.append('File', this.files[filesKey].file)
          PostDTO.altTexts.push(this.files[filesKey].file.name)
        }
        formData.append('postDTO', JSON.stringify(PostDTO))
        formData.append('File', this.file)

        PostService.createPost(formData)
      },
      onButtonClick () {
        this.isSelecting = true
        window.addEventListener('focus', () => {
          this.isSelecting = false
        }, { once: true })
        this.$refs.uploader.click()
      },
      onFileChanged (e) {
        this.files.push({
          'file': e.target.files[0],
          'image': URL.createObjectURL(e.target.files[0])
        })
      },
    }
  }
</script>
