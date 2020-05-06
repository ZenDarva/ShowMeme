<template>
    <div class="container">
        <header class="jumbotron">
            <div class = "card-deck">
                <div class="col">
                <li v-for="item in content" :key="item.name" style="list-style-type: none">
                    <div class="card" style="width: 18rem;">
                        <img class="card-img-top" :src="urlFromPost(item)" :title="item.title">
                        <div class="card-body">
                            <h5 class="card-title text-center">{{item.title}}</h5>
                        </div>
                    </div>
                </li>
                </div>
            </div>
        </header>
    </div>
</template>

<!--Consider switching to vue cards.-->

<script>
    import postService from "../services/post-service.js"

    export default {
        data() {
            return {
                content: ''
            };
        },
        mounted() {
            postService.getPosts().then(response => {
                this.content = response.data;
            });
        },
        methods: {
            urlFromPost(post) {
                var full = location.protocol + '//' + location.hostname + (location.port ? ':' + location.port : '');
                return full + "/api/post/image/" + post.imageHashes[0];
            }
        }

    };
</script>
