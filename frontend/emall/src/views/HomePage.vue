<template>
    <div class="home">

      <h1>商品列表</h1>

      <div class="products-container">
        <div v-for="product in displayedProducts" :key="product.productId" class="product">
          <img :src="getImagePath(product.productId)" alt="商品预览图" class="product-image">
          <h2>{{ product.name }}</h2>
          <button @click="goToProduct(product.productId)">查看详情</button>
        </div>
      </div>

      <button v-if="moreProductsAvailable" @click="loadMoreProducts" class="load-more">加载更多</button>

    </div>
</template>
  
<script >
import api from '../api/api.js';
  export default {
    name: "HomePage",
    data() {
      return {
        products: [],
        productsPerRow: 4, // 每行显示的商品数量
        rowsToShow: 2, // 初始显示的行数
      };
    },
    computed: {
      displayedProducts() {
        const totalProductsToShow = this.productsPerRow * this.rowsToShow;
        return this.products.slice(0, totalProductsToShow);
      },
      moreProductsAvailable() {
        return this.displayedProducts.length < this.products.length;
      },
    },
    methods: {
      goToProduct(productId) {
        this.$router.push(`/products/${productId}`);
      },
      loadMoreProducts() {
        this.rowsToShow += 5;
      },
      async fetchProducts() {
        try {
          const response = await api.getRandProducts();
          this.products = response.data;
        } catch (error) {
          console.error('Error fetching products:', error);
        }
      },
      getImagePath(number) {
        return require(`../assets/images/${number}.jpg`);
      }
    },
    created() {
      this.fetchProducts();
    },
  };
  </script>

<style>
.home {
text-align: center;
color: #2c3e50;
}

.products-container {
display: flex;
flex-wrap: wrap;
justify-content: center;
margin: 20px;
}

.product {
background-color: #fff;
border: 1px solid #ffcc00;
border-radius: 10px;
margin: 10px;
padding: 20px;
width: 200px;
height: 400px;
box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
transition: transform 0.3s, box-shadow 0.3s;
}

.home .products-container .product .product-image {
  width: 90%; /* 容器的80%宽度 */
  height: 70%;
  object-fit: cover; /* 拉伸并裁剪图片 */
  border-bottom: 1px solid #ffcc00;
  margin-bottom: 15px;
  background-size: cover;
  background-position: center;
}

.product h2 {
color: #d32f2f;
margin: 10px 0;
}

.product button {
background-color: #ffd700;
border: none;
border-radius: 5px;
padding: 10px 20px;
color: #d32f2f;
cursor: pointer;
transition: background-color 0.3s;
}

.product button:hover {
background-color: #d32f2f;
color: #fff;
}

.product:hover {
transform: translateY(-10px);
box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}

.load-more {
  background-color: #ffd700;
  border: none;
  border-radius: 5px;
  color: #d32f2f;
  cursor: pointer;
  left: 50%;
  font-size: large;
}

.load-more:hover {
  background-color: #d32f2f;
  color: #fff;
}
</style>
