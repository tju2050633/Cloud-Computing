<template>
  <div class="admin-page">
    <h1>后台管理</h1>

    <!-- 商品管理部分 -->
    <section class="product-management">
      <h2>商品管理</h2>
      <div>
        <button @click="showAddProductForm = !showAddProductForm">添加商品</button>
        <button @click="fetchProducts('all')">全部商品</button>
        <button @click="fetchProducts('price_desc')">按单价降序</button>
        <button @click="fetchProducts('price_asc')">按单价升序</button>
        <button @click="fetchProducts('sold_desc')">按已售降序</button>
        <button @click="fetchProducts('sold_asc')">按已售升序</button>
        <button @click="fetchProducts('create_desc')">按创建日期降序</button>
        <button @click="fetchProducts('create_asc')">按创建日期升序</button>
      </div>
      <div v-if="showAddProductForm" class="add-product-form">
        <!-- 添加商品表单内容 -->
        <!-- ... -->
      </div>
      <table class="product-table">
        <thead>
          <tr>
            <th>图片</th>
            <th>名称</th>
            <th>描述</th>
            <th>单价</th>
            <th>库存/仓库</th>
            <th>已售数量</th>
            <th>创建日期</th>
            <th>修改日期</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="product in paginatedProducts" :key="product.productId">
            <td><img :src="getImagePath(product.productId)" class="product-image" /></td>
            <td>{{ product.name }}</td>
            <td>{{ truncateText(product.description, 50) }}</td>
            <td>{{ product.price }}</td>
            <td>{{ product.stock }}/{{ product.space }}</td>
            <td>{{ product.sold }}</td>
            <td>{{ formatDate(product.createDate) }}</td>
            <td>{{ formatDate(product.modifyDate) }}</td>
            <td>
              <button class="edit-button">编辑</button>
              <button class="delete-button" @click="deleteProduct(product.productId)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
      <div class="pagination-control">
        <button class="pagination-button" @click="changePage('product', currentProductPage - 1)" :disabled="currentProductPage <= 1">上一页</button>
        <button class="pagination-number" @click="changePage('product', n)" v-for="n in displayedProductPages" :key="n" :class="{ active: currentProductPage === n }">{{ n }}</button>
        <button class="pagination-button" @click="changePage('product', currentProductPage + 1)" :disabled="currentProductPage >= maxProductPage">下一页</button>
        <input class="pagination-input" type="number" v-model.number="currentProductPage" @change="changePage('product', currentProductPage)" />
      </div>
    </section>

    <!-- 订单管理部分 -->
    <section class="order-management">
      <h2>订单管理</h2>
      <div>
        <button @click="fetchOrders('all')">全部订单</button>
        <button @click="fetchOrders('price_desc')">按总价降序</button>
        <button @click="fetchOrders('price_asc')">按总价升序</button>
        <button @click="fetchOrders('date_desc')">按日期降序</button>
        <button @click="fetchOrders('date_asc')">按日期升序</button>
        <button @click="fetchOrders('sent')">已发货</button>
        <button @click="fetchOrders('unsent')">待发货</button>
      </div>
      <table class="order-table">
        <thead>
          <tr>
            <th>用户名</th>
            <th>日期</th>
            <th>总价</th>
            <th>状态</th>
            <th>商品详情</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="order in paginatedOrders" :key="order.id">
            <td>Admin</td>
            <td>{{ order.orderDate }}</td>
            <td>{{ order.totalPrice }}</td>
            <td>{{ order.orderStatus }}</td>
            <td>{{ orderDetails[order.orderId] }}</td>
            <td>
              <button @click="viewOrderDetails(order.orderId)" style="margin-top: 40px; height: 40px;">查看详情</button>
              <button class="delete-button" @click="deleteOrder(order.orderId)" style="margin-top: 40px; height: 40px;">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
      <div class="pagination-control">
        <button class="pagination-button" @click="changePage('order', currentOrderPage - 1)" :disabled="currentOrderPage <= 1">上一页</button>
        <button class="pagination-number" @click="changePage('order', n)" v-for="n in displayedOrderPages" :key="n" :class="{ active: currentOrderPage === n }">{{ n }}</button>
        <button class="pagination-button" @click="changePage('order', currentOrderPage + 1)" :disabled="currentOrderPage >= maxOrderPage">下一页</button>
        <input class="pagination-input" type="number" v-model.number="currentOrderPage" @change="changePage('order', currentOrderPage)" />
      </div>
    </section>

    <!-- 订单详情弹窗 -->
    <div v-if="isDetailsModalOpen" class="modal">
      <div class="modal-content">
        <span class="close" @click="isDetailsModalOpen = false">&times;</span>
        <h3>订单详情</h3>
        <p>{{ selectedOrderDetails }}</p>
      </div>
    </div>

  </div>
</template>
  
  <script>
  import api from "../api/api.js";

  export default {
    name: "AdminPage",
    data() {
      return {
        products: [],
        orders: [],
        orderDetails: {},
        showAddProductForm: false,
        newProduct: {
          name: '',
          description: '',
          price: 0,
          stock: 0
        },
        currentProductPage: 1,
        currentOrderPage: 1,
        itemsPerPage: 5,
        selectedOrderDetails: null, // 存储选中的订单详情
        isDetailsModalOpen: false,  // 控制详情弹窗的显示状态
      };
    },
    computed: {
      paginatedProducts() {
        const start = (this.currentProductPage - 1) * this.itemsPerPage;
        const end = start + this.itemsPerPage;
        return this.products.slice(start, end);
      },
      maxProductPage() {
        return Math.ceil(this.products.length / this.itemsPerPage);
      },
      paginatedOrders() {
        const start = (this.currentOrderPage - 1) * this.itemsPerPage;
        const end = start + this.itemsPerPage;
        return this.orders.slice(start, end);
      },
      maxOrderPage() {
        return Math.ceil(this.orders.length / this.itemsPerPage);
      },
      displayedProductPages() {
        const minPage = Math.max(this.currentProductPage - 2, 1);
        const maxPage = Math.min(this.currentProductPage + 2, this.maxProductPage);
        const pages = [];

        for (let i = minPage; i <= maxPage; i++) {
          pages.push(i);
        }

        return pages;
      },
      displayedOrderPages() {
        const minPage = Math.max(this.currentOrderPage - 2, 1);
        const maxPage = Math.min(this.currentOrderPage + 2, this.maxOrderPage);
        const pages = [];

        for (let i = minPage; i <= maxPage; i++) {
          pages.push(i);
        }

        return pages;
      },
    },
    methods: {
      getImagePath(number) {
        return require(`../assets/images/${number}.jpg`);
      },
      formatDate(dateString) {
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = (date.getMonth() + 1).toString().padStart(2, '0');
        const day = date.getDate().toString().padStart(2, '0');
        return `${year}-${month}-${day}`;
      },
      changePage(type, page) {
        if (type === 'product') {
          if (page >= 1 && page <= this.maxProductPage) {
            this.currentProductPage = page;
          }
        } else if (type === 'order') {
          if (page >= 1 && page <= this.maxOrderPage) {
            this.currentOrderPage = page;
            this.loadCurrentPageOrderDetails();
          }
        }
      },
      truncateText(text, length) {
        return text.length > length ? text.substring(0, length) + '...' : text;
      },

      // products

      async fetchProducts(sortType = 'rand') {
        try {
          let response;
          switch (sortType) {
            case 'rand':
              response = await api.getRandProducts();
              break;
            case 'price_desc':
              response = await api.getProductsSortedByPrice('desc');
              break;
            case 'price_asc':
              response = await api.getProductsSortedByPrice('asc');
              break;
            case 'sold_desc':
              response = await api.getProductsSortedBySold('desc');
              break;
            case 'sold_asc':
              response = await api.getProductsSortedBySold('asc');
              break;
            case 'create_desc':
              response = await api.getProductsSortedByCreateDate('desc');
              break;
            case 'create_asc':
              response = await api.getProductsSortedByCreateDate('asc');
              break;
            default:
              response = await api.getAllProducts();
          }
          this.products = response.data;
        } catch (error) {
          console.log(error);
        }
      },
      addProduct() {
        api.insertProduct(this.newProduct);
      },
      async deleteProduct(productId) {
        try {
          await api.deleteProduct(productId); // 假设这是API调用删除商品的方法
          this.products = this.products.filter(product => product.productId !== productId); // 从商品列表中移除
          this.fetchProducts('all'); // 重新加载商品，以便获取最新数据
        } catch (error) {
          console.error('Error deleting product:', error);
        }
      },

      async deleteOrder(orderId) {
        try {
          await api.deleteOrder(orderId); // 假设这是API调用删除订单的方法
          this.orders = this.orders.filter(order => order.orderId !== orderId); // 从订单列表中移除
          this.fetchOrders(); // 重新加载订单，以便获取最新数据
        } catch (error) {
          console.error('Error deleting order:', error);
        }
      },

      // orders

      async fetchOrders(sortType = 'all') {
        try {
          let response;
          switch (sortType) {
            case 'date_desc':
              response = await api.getOrdersSortedByDate('desc');
              break;
            case 'date_asc':
              response = await api.getOrdersSortedByDate('asc');
              break;
            case 'price_desc':
              response = await api.getOrdersSortedByPrice('desc');
              break;
            case 'price_asc':
              response = await api.getOrdersSortedByPrice('asc');
              break;
            case 'sent':
              response = await api.getSentOrders();
              break;
            case 'unsent':
              response = await api.getUnsentOrders();
              break;
            default:
              response = await api.getAllOrders();
          }
          this.orders = response.data;
          this.loadCurrentPageOrderDetails();
        } catch (error) {
          console.log(error);
        }
      },
      async viewOrderDetails(orderId) {
        try {
          const response = await api.getOrderItemsByOrderId(orderId);
          this.selectedOrderDetails = await this.truncateOrderItems(response.data, 1000);
          this.isDetailsModalOpen = true; // 打开弹窗
        } catch (error) {
          console.error('Error fetching order details:', error);
        }
      },
      async loadCurrentPageOrderDetails() {
        const currentPageOrders = this.paginatedOrders;
        for (const order of currentPageOrders) {
          const response = await api.getOrderItemsByOrderId(order.orderId);
          this.orderDetails[order.orderId] = await this.truncateOrderItems(response.data);
        }
      },
      async truncateOrderItems(items, length=50) {
        let itemDescriptions = [];

        for (const item of items) {
          try {
            const response = await api.getProductById(item.productId);
            let productName = response.data.name;
            itemDescriptions.push(`${productName} * ${item.quantity}`);
          } catch (error) {
            console.error('Error fetching product name:', error);
          }
        }
        let itemString = itemDescriptions.join(', ');
        return itemString.length > length ? itemString.substring(0, length) + '...' : itemString;
      },
    },
    created() {
      this.fetchProducts();
      this.fetchOrders();
    },
  };
  </script>
  
  <style >
  .admin-page {
    padding: 20px;
  }
  
/* 为按钮组添加边距 */
.admin-page .product-management div,
.admin-page .order-management div {
  margin-bottom: 20px;
  margin-left: 20px;
}

.admin-page .product-management button,
.admin-page .order-management button{
  margin-left: 20px;
}
  
  .add-product-form {
    margin-bottom: 20px;
  }
  
  .product-table {
    width: 100%;
    border-collapse: collapse;
    text-align: left;
  }
  
  .product-table th, .product-table td {
    border: 1px solid #ddd;
    padding: 8px;
  }
  
  .product-table th {
    background-color: #ffcc00;
    color: #d32f2f;
  }

  .product-image {
    width: 100px; /* 容器的80%宽度 */
    height: 100px;
    object-fit: cover; /* 拉伸并裁剪图片 */
    border-bottom: 1px solid #ffcc00;
  }

  /* 设置表格单元格的最小高度 */
.product-table td,
.order-table td {
  min-height: 120px; /* 设置最小高度，确保单元格不会塌陷 */
  /* 其他样式保持不变 */
}

/* 确保图片大小适当 */
.product-table .product-image {
  width: 100px; /* 图片宽度 */
  height: 100px; /* 图片高度，可以调整以适应您的需求 */
  object-fit: cover; /* 保持图片比例 */
}
  
.order-table {
  width: 100%;
  border-collapse: collapse;
  text-align: left;
}

.order-table th, .order-table td {
  border: 1px solid #ddd;
  padding: 8px;
}

.order-table th {
    background-color: #ffcc00;
    color: #d32f2f;
  }

/* 分页控制区域样式 */
.pagination-control {
  text-align: center;
  margin-top: 20px;
}

.pagination-button {
  padding: 5px 10px;
  margin: 0 5px;
  background-color: #ffff00;
  color: white;
  border: none;
  cursor: pointer;
}

.pagination-button:disabled {
  background-color: #ccc;
}

.pagination-number {
  padding: 5px 10px;
  margin: 0 5px;
  background-color: #f0f0f0;
  border: 1px solid #ddd;
  cursor: pointer;
}

.pagination-number.active {
  background-color: #ffff00;
  color: white;
}

.pagination-input {
  margin-left: 10px;
  padding: 5px;
  width: 50px;
}

/* 订单管理中按钮样式 */
.order-table td button {
  margin: 0 auto;
  display: block;
}

/* 商品和订单表格中操作列的样式 */
.product-table td:last-child,
.order-table td:last-child {
  display: flex;
  justify-content: space-around;
}

/* 编辑和删除按钮的样式 */
.edit-button {
  height: 40px;
  width: 80px;
  margin-top: 40px;
  background-color: #ffcc00; /* 蓝色背景 */
  color: #d32f2f; /* 白色文字 */
  border: none;
  padding: 5px 10px;
  cursor: pointer;
}

.delete-button {
  height: 40px;
  width: 80px;
  margin-top: 40px;
  background-color: #d32f2f; /* 蓝色背景 */
  color: #ffff00; /* 白色文字 */
  border: none;
  padding: 5px 10px;
  cursor: pointer;
}

.edit-button:hover {
  background-color: #d32f2f; /* 深蓝色背景 */
  color: white; /* 白色文字 */
}

.delete-button:hover {
  background-color: #ffcc00; /* 深蓝色背景 */
  color: white; /* 白色文字 */
}

/* 分页控制按钮样式调整 */
.pagination-button {
  background-color: #17a2b8; /* 亮蓝绿色 */
  /* 其他样式保持不变 */
}

.pagination-button:disabled {
  background-color: #ccc; /* 灰色背景 */
}

.pagination-number {
  background-color: #f8f9fa; /* 浅灰色背景 */
  /* 其他样式保持不变 */
}

.pagination-number.active {
  background-color: #28a745; /* 绿色背景 */
  /* 其他样式保持不变 */
}

.modal {
    position: fixed;
    z-index: 1;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    overflow: auto;
    background-color: rgba(0, 0, 0, 0.4);
  }

.modal-content {
  background-color: #fefefe;
  margin: 15% auto;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}
</style>