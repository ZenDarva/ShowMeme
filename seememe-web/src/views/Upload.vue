<template>
    <div class="container">
        <div class="large-12 medium-12 small-12 cell">
            <div class = "card-deck">
                <li v-for="item in files" :key="item.name">
                    <div class="card" style="width: 18rem;">
                        <img class="card-img-top" :src="urlFromFile(item)" :title="item.name">
                        <div class="card-body">
                            <h5 class="card-title text-center">{{item.name}}</h5>
                        </div>
                    </div>
                </li>
            </div>
            <label>File
                <input type="file" id="file" ref="file" v-on:change="handleFileUpload()"/>
            </label>
            <button v-on:click="submitFile()">Submit</button>
        </div>
    </div>
</template>

<script>
    import postService from "../services/post-service.js"
    export default {
        data() {
            return {
                content: '',
                files: [],
            }
        },
        methods: {
            handleFileUpload() {
                this.content=this.content+"\n" +this.$refs.file.files[0].name;
                this.files.push(this.$refs.file.files[0]);
            },
            submitFile() {
                let formData = new FormData();
                let PostDTO = {
                    "title":"Third post ever",
                    "description": "This is an even newer post.",
                    "altTexts": []
                }

                for (let filesKey in this.files) {
                    formData.append('File',this.files[filesKey]);
                    PostDTO.altTexts.push(this.files[filesKey].name);
                    console.log(this.files[filesKey].name);
                    console.log(PostDTO);
                }
                formData.append('postDTO', JSON.stringify(PostDTO));
                formData.append('File', this.file);
                postService.createPost(formData);
            },
            urlFromFile(file){
                return URL.createObjectURL(file);
            }

        }
    }
</script>