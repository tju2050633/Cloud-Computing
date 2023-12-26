<template>
  <div class="cart-page">
    <div class="cart-header">
      <h1>购物车</h1>
      <p class="total-price">总价: <span>{{ totalPrice.toFixed(2) }}</span></p>
      <button @click="goToCheckout(orderId)" class="checkout-button">去结算</button>
    </div>
    <div v-if="displayedCartItems.length" class="cart-items">
      <div v-for="item in displayedCartItems" :key="item.productId" class="cart-item">
        <img :src="getImagePath(item.productId)" alt="商品图片" class="product-thumbnail">
        <div class="item-info">
          <h2>{{ item.productName }}</h2>
          <p class="item-description">{{ item.description }}</p>
          <p class="item-price">单价: {{ item.price }}</p>
          <div class="quantity-control">
            <button @click="decreaseQuantity(item)">-</button>
            <span>{{ item.quantity }}</span>
            <button @click="increaseQuantity(item)">+</button>
          </div>
          <p class="item-total">总价: {{ (item.quantity * item.price).toFixed(2) }}</p>
        </div>
        <button @click="removeFromCart(item.productId)" class="remove-button">移除</button>
      </div>
      <button v-if="cartItems.length > 5" @click="loadAllItems" class="load-more">加载更多</button>
    </div>
    <p v-else class="empty-cart">购物车为空</p>
  </div>
</template>

<script>
import api from "../api/api.js";
export default {
  name: "CartPage",
  data() {
    return {
      cartItems: [ ],
      itemsToShow: 5,
      orderId: 0,
    };
  },
  computed: {
    totalPrice() {
      return this.cartItems.reduce((total, item) => total + item.price * item.quantity, 0);
    },
    displayedCartItems() {
      return this.cartItems.slice(0, this.itemsToShow);
    },
  },
  methods: {
    getImagePath(number) {
      return require(`../assets/images/${number}.jpg`);
    },
    increaseQuantity(item) {
      item.quantity += 1;
    },
    decreaseQuantity(item) {
      if (item.quantity > 1) item.quantity -= 1;
    },
    removeFromCart(productId) {
      this.cartItems = this.cartItems.filter(item => item.productId !== productId);
    },
    goToCheckout(orderId) {
      this.$router.push(`/checkout/${orderId}`);
    },
    loadAllItems() {
      this.itemsToShow = this.cartItems.length;
    },
    async fetchCartItems() {
      let response;
      let list;

      while (list === undefined) {
        this.orderId = Math.floor(Math.random() * 500) + 1;
        response = await api.getOrderItemsByOrderId(this.orderId);
        list = response.data;
      }
      for (let i = 0; i < list.length; i++) {
        const item = list[i];
        const product = await api.getProductById(item.productId);
        this.cartItems.push({
          productId: item.productId,
          productName: product.data.name,
          description: product.data.description,
          price: product.data.price,
          quantity: item.quantity,
        });
      }
    },
  },
  created() {
    this.fetchCartItems();
  },
};
</script>
  
  <style >
  .cart-page {
    padding: 20px;
  }
  
  .cart-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 20px;
  }
  
  .cart-item {
    display: flex;
    align-items: center;
    margin-bottom: 15px;
    background-color: #fff;
    padding: 10px;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
  
  .product-thumbnail {
    width: 80px;
    height: 80px;
    margin-right: 15px;
    object-fit: cover;
  }
  
  .item-info {
    flex-grow: 1;
  }
  
  .item-description {
    color: #666;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  
  .quantity-control {
    display: flex;
    align-items: center;
    margin-top: 10px;
  }
  
  .quantity-control button {
    background-color: #ffd700;
    border: none;
    color: #d32f2f;
    padding: 5px 10px;
    margin: 0 5px;
    cursor: pointer;
  }
  
  .quantity-control span {
    min-width: 20px;
    text-align: center;
  }
  
  .checkout-button, .remove-button {
    background-color: #d32f2f;
    color: #fff;
    border: none;
    padding: 10px 20px;
    cursor: pointer;
    border-radius: 5px;
  }
  
  .checkout-button:hover, .remove-button:hover {
    background-color: #ffd700;
    color: #d32f2f;
  }
  
  .empty-cart {
    text-align: center;
    color: #666;
  }

  .total-price span {
  color: red;
  font-size: 1.2em;
}

.load-more {
  background-color: #ffd700;
  color: #d32f2f;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  margin-top: 20px;
  border-radius: 5px;
}

.load-more:hover {
  background-color: #d32f2f;
  color: #fff;
}
  </style>
  