<template>
    <div class="home">
        <v-container fluid>
            <v-row dense>
                <v-col
                        v-for="item in content"
                        :key="item.id"
                        cols="3"
                >
                    <v-card class="ma-1"
                            @click="loadPost(item.id)"
                    >
                        <v-img :src="urlFromPost(item)"></v-img>
                        <v-card-text class="text-center">{{item.title}}</v-card-text>
                        <v-list-item class="grow">
                        <v-row align="center" justify="end">
                            <v-icon class="mr-2" small>fa-eye</v-icon>
                            <span class="subheading mr-2">{{item.views}}</span>
                            <v-icon class="mr-2" small>fa-thumbs-up</v-icon>
                            <span class="subheading mr-2">0</span>
                            <v-icon class="mr-2" small>fa-thumbs-down</v-icon>
                            <span class="subheading mr-2">0</span>
                        </v-row>
                        </v-list-item>
                    </v-card>

                </v-col>
            </v-row>
        </v-container>
    </div>
</template>

<script>
    import PostService from '../services/post-service.js'

    export default {
        name: 'Home',
        components: {},
        methods: {
            urlFromPost(post) {
                var full = location.protocol + '//' + location.hostname + (location.port ? ':' + location.port : '');
                return full + "/api/post/image/" + post.imageHashes[0];
            },
            loadPost(id) {
                // console.log("We should load post " + id + " here");
                this.$router.push("/post/" + id);
            }
        },
        data() {
            return {
                content: []
            };
        },
        mounted() {
            PostService.getPosts().then(response => {
                this.content = response.data;
            });
        }
    }
</script>
