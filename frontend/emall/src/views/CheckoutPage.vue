<template>
  <div class="checkout-page">
    <button @click="confirmOrder" class="confirm-button fixed-button">确认订单</button>
    <h1>结算</h1>
    <div v-if="orderDetails" class="order-details">
      <h2>订单确认</h2>
      <div v-for="item in displayedItems" :key="item.productId" class="order-item">
        <img :src="getImagePath(item.productId)" alt="商品图片" class="product-thumbnail">
        <div class="item-info">
          <h3>{{ item.productName }}</h3>
          <p class="item-description">{{ item.description }}</p>
          <div class="price-quantity">
            <span>数量: {{ item.quantity }}</span>
            <span>单价: {{ item.price }}</span>
          </div>
        </div>
        <div class="item-total">
          总价: {{ item.quantity * item.price }}
        </div>
      </div>
      <button v-if="moreItemsAvailable" @click="loadMoreItems" class="load-more">加载更多</button>
      <div class="order-total">
        <p style="color: red; font-size: large;">订单总价: <span>{{ totalPrice.toFixed(2) }}</span></p>
      </div>
    </div>
    <p v-else class="loading-info">订单信息正在加载...</p>
  </div>
</template>

<script>
import api from "../api/api.js";
export default {
  name: "CheckoutPage",
  data() {
    return {
      orderDetails: {
        // 示例数据，后续可替换为动态加载的数据
        items: [ ],
        totalPrice: 0,
      },
      itemsToShow: 5, // 初始显示的商品种类数量
    };
  },
  computed: {
    displayedItems() {
      return this.orderDetails.items.slice(0, this.itemsToShow);
    },
    moreItemsAvailable() {
      return this.orderDetails.items.length > this.itemsToShow;
    },
    totalPrice() {
      return this.orderDetails.items.reduce((total, item) => total + item.price * item.quantity, 0);
    },
  },
  methods: {
    getImagePath(number) {
      return require(`../assets/images/${number}.jpg`);
    },
    confirmOrder() {
      alert("支付成功");
      this.orderDetails.items = [];
      this.orderDetails.totalPrice = 0;
    },
    loadMoreItems() {
      this.itemsToShow = this.orderDetails.items.length;
    },
    async fetchOrderDetails() {
      let orderId = this.$route.params.orderId;
      const response = await api.getOrderItemsByOrderId(orderId);
      let list = response.data;

      for (let i = 0; i < list.length; i++) {
        const item = list[i];
        const product = await api.getProductById(item.productId);
        this.orderDetails.items.push({
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
    this.fetchOrderDetails();
  },
};
</script>

<style >
.checkout-page {
  position: relative;
  padding: 20px;
}

.fixed-button {
  position: fixed;
  bottom: 20px;
  left: 50%;
  z-index: 100;
}

.order-details {
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.order-item {
  display: flex;
  align-items: center;
  border-bottom: 1px solid #eee;
  padding-bottom: 10px;
  margin-bottom: 10px;
}

.product-thumbnail {
  width: 80px;
  height: 80px;
  margin-right: 15px;
  object-fit: cover;
}

.item-info {
  flex-grow: 1;
  text-align: left;
}

.item-description {
  color: #666;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.price-quantity {
  display: flex;
  justify-content: space-between;
  color: #666;
}

.item-total {
  font-weight: bold;
  color: #d32f2f;
}

.load-more {
  background-color: #ffd700;
  color: #d32f2f;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  margin: 20px 0;
  border-radius: 5px;
}

.load-more:hover {
  background-color: #d32f2f;
  color: #fff;
}

.order-total {
  margin-top: 10px;
}

.confirm-button {
  background-color: #d32f2f;
  color: #fff;
  border: none;
  padding: 15px 30px;
  cursor: pointer;
  border-radius: 5px;
}

.confirm-button:hover {
  background-color: #ffd700;
  color: #d32f2f;
}

.loading-info {
  text-align: center;
  color: #666;
}
</style>
