<template>
  <div id="app">
    <div class="nav-bar">
      <router-link class="nav-link" to="/">主页</router-link>
      <router-link class="nav-link" to="/cart">购物车</router-link>
      <router-link class="nav-link" to="/admin">后台管理</router-link>
    </div>
    <div class="router-view-container">
      <router-view/>
    </div>
  </div>
</template>

<script>
import localforage from 'localforage'
import api from './api/api.js'

export default {
  name: 'App',
  async created() {
    // 初始化购物车缓存
    localforage.config({
      name: 'cart',
    })
    const response = await api.getEmptyCart();
    const cartString = JSON.stringify(response.data)
    localforage.setItem('cart', cartString)
  },
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.nav-bar {
  display: flex;
  justify-content: center;
  background-color: #ffcc00; /* 浅黄色背景 */
  padding: 10px 0;
}

.nav-link {
  text-decoration: none;
  color: #d32f2f; /* 深红色文字 */
  background-color: #ffff00; /* 金黄色按钮 */
  padding: 10px 20px;
  margin: 0 10px;
  border-radius: 5px;
  transition: background-color 0.3s, color 0.3s;
}

.nav-link:hover {
  background-color: #d32f2f; /* 深红色背景 */
  color: #fff; /* 白色文字 */
}

.router-view-container {
  margin: 20px;
  border: 1px solid #ffcc00; /* 浅黄色边框 */
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 轻微阴影 */
}
</style>
