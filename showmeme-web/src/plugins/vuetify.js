import Vue from 'vue'
import Vuetify from 'vuetify/lib'
import '@fortawesome/fontawesome-free/css/all.css' // Ensure you are using css-loader
import { TiptapVuetifyPlugin } from 'tiptap-vuetify'
import 'tiptap-vuetify/dist/main.css'
import 'vuetify/dist/vuetify.min.css'



const vuetify = new Vuetify()

Vue.use(Vuetify, {
  iconfont: 'fa'
})


Vue.use(TiptapVuetifyPlugin, {
  vuetify,
  iconsGroup: 'fa'
})



export default vuetify
