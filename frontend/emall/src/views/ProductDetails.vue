<template>
    <div class="product-details-container">
      <div class="product-details">
        <div class="product-image-container">
          <img :src="getImagePath(this.$route.params.productId)" alt="商品图片" class="product-image">
        </div>
        <div class="product-info">
          <h1>{{ product.name }}</h1>
          <hr class="divider" />
          <p>{{ product.description }}</p>
          <p class="price">价格: {{ product.price }}</p>
          <p class="stock">库存: {{ product.stock }}</p>
          <button @click="addToCart(product.productId)">加入购物车</button>
        </div>
      </div>
      <div class="shopping-cart">
        <h2>购物车</h2>
        <ul v-if="cart && cart.size > 0">
          <li v-for="(productId, quantity) in cart" :key="productId">
            {{ getProductDetails(productId).name }} - 单价: {{ getProductDetails(productId).price }} - 总价: {{ getProductDetails(productId).price * quantity }}
          </li>
        </ul>
        <p v-if="!cart || cart.size === 0">购物车为空</p>
      </div>
    </div>
  </template>
  
  <script>
  import api from "../api/api.js";
  import localforage from 'localforage'

  export default {
    name: "ProductDetails",
    data() {
      return {
        product: { },
        // 购物车数据
        cart: new Map(),
      };
    },
    methods: {
      getImagePath(number) {
        return require(`../assets/images/${number}.jpg`);
      },
      async addToCart(productId) {
        // 将商品添加到购物车
        const response = await api.addProductToCart(this.cart, productId, 1);
        this.cart = response.data;

        // 将购物车数据保存到缓存
        const cartString = JSON.stringify(this.cart);
        localforage.setItem("cart", cartString);
      },
      async getProductDetails(productId) {
        const response = await api.getProductById(productId);
        const product = response.data;
        return {
          productId: productId,
          name: product.name,
          price: product.price,
        };
      },
      
      async fetchProductDetails() {
        const response = await api.getProductById(this.$route.params.productId);
        this.product = response.data;
      },
      async loadCart() {
        // 从缓存中加载购物车数据
        localforage.getItem("cart").then((cartString) => {
          if (cartString) {
            this.cart = JSON.parse(cartString).items;
            console.log(cartString);
          }
        });
      }
    },
    created() {
      this.fetchProductDetails();
      this.loadCart();
    },
  };
  </script>
  
  <style >
  .product-details-container {
    display: flex;
    justify-content: space-around;
    margin: 20px;
  }
  
  .product-details {
    flex-basis: 60%;
    background-color: #fff;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
  
  .product-image-container {
    width: 80%;
    margin: auto;
    overflow: hidden;
  }
  
  .product-image {
    width: 100%;
    height: auto;
    transition: transform 0.3s ease-in-out;
  }
  
  .product-image-container:hover .product-image {
    transform: scale(1.1);
  }
  
  .product-info {
    margin-top: 20px;
  }
  
  .divider {
    border: 0;
    height: 1px;
    background-image: linear-gradient(to right, #ffcc00, #d32f2f, #ffcc00);
    margin: 10px 0;
  }
  
  .price {
    color: #d32f2f;
    font-weight: bold;
  }
  
  .stock {
    color: #2c3e50;
    font-weight: bold;
  }
  
  .shopping-cart {
    flex-basis: 30%;
    background-color: #fff;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
  
  button {
    background-color: #ffd700;
    border: none;
    border-radius: 5px;
    padding: 10px 20px;
    color: #d32f2f;
    cursor: pointer;
    transition: background-color 0.3s;
  }
  
  button:hover {
    background-color: #d32f2f;
    color: #fff;
  }
  </style>