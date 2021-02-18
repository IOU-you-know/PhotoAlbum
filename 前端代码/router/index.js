import Vue from 'vue'
import Router from 'vue-router'
import picture from '../components/Picture'

Vue.use(Router)
//push 
const VueRouterPush = Router.prototype.push 
Router.prototype.push = function push (to) {
    return VueRouterPush.call(this, to).catch(err => err)
}
export default new Router({
  routes: [{
    path: '/picture',
    name: 'picture',
    component: picture
  }]
})
