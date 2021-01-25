// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from "vue";
import App from "./App";
import router from "./router";
import store from "./store";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";

Vue.config.productionTip = false;
Vue.use(ElementUI);
Vue.prototype.$global_ = {hostName: window.location.protocol+'//'+window.location.hostname+':8081'}
//挂载到Vue实例上面
new Vue({
    router,
    store,
    render: h => h(App)
}).$mount("#app");
