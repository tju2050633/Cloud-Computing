import axios from 'axios';

const PRODUCTS_BASE_URL = 'http://localhost:8081/api/products';
const CARTS_BASE_URL = 'http://localhost:8081/api/carts';
const ORDERS_BASE_URL = 'http://localhost:8081/api/orders';

const api = {

  // Product API

  getAllProducts() {
    return axios.get(`${PRODUCTS_BASE_URL}/get`);
  },

  getRandProducts() {
    return axios.get(`${PRODUCTS_BASE_URL}/rand`);
  },

  getProductsSortedByPrice(type) {
    return axios.get(`${PRODUCTS_BASE_URL}/get/price/${type}`);
  },

  getProductsSortedBySold(type) {
    return axios.get(`${PRODUCTS_BASE_URL}/get/sold/${type}`);
  },

  getProductsSortedByCreateDate(type) {
    return axios.get(`${PRODUCTS_BASE_URL}/get/create/${type}`);
  },

  getProductById(productId) {
    return axios.get(`${PRODUCTS_BASE_URL}/${productId}`);
  },

  insertProduct(product) {
    return axios.post(`${PRODUCTS_BASE_URL}/add`, product);
  },

  updateProduct(product) {
    return axios.put(`${PRODUCTS_BASE_URL}/update`, product);
  },

  deleteProduct(productId) {
    return axios.delete(`${PRODUCTS_BASE_URL}/${productId}`);
  },

  // Cart API

  getEmptyCart() {
    return axios.get(`${CARTS_BASE_URL}/init`);
  },

  addProductToCart(cart, productId, quantity) {
    return axios.post(`${CARTS_BASE_URL}/add`, { cart, productId, quantity });
  },

  getCartContents(cart) {
    return axios.get(`${CARTS_BASE_URL}/get`, { data: cart });
  },

  decreaseProductQuantity(cart, productId, quantity) {
    return axios.post(`${CARTS_BASE_URL}/decrease`, { cart, productId, quantity });
  },

  removeProductFromCart(cart, productId) {
    return axios.post(`${CARTS_BASE_URL}/remove`, { cart, productId });
  },

  getTotalPrice(cart) {
    return axios.get(`${CARTS_BASE_URL}/total`, { data: cart });
  },

  // Order API

  createOrder(cart) {
    return axios.post(`${ORDERS_BASE_URL}`, cart);
  },

  getOrderById(orderId) {
    return axios.get(`${ORDERS_BASE_URL}/${orderId}`);
  },

  getAllOrders() {
    return axios.get(`${ORDERS_BASE_URL}/get`);
  },

  getOrdersSortedByDate(type) {
    return axios.get(`${ORDERS_BASE_URL}/get/date/${type}`);
  },

  getOrdersSortedByPrice(type) {
    return axios.get(`${ORDERS_BASE_URL}/get/total/${type}`);
  },

  getSentOrders() {
    return axios.get(`${ORDERS_BASE_URL}/get/sent`);
  },

  getUnsentOrders() {
    return axios.get(`${ORDERS_BASE_URL}/get/unsent`);
  },

  getOrderItemsByOrderId(orderId) {
    return axios.get(`${ORDERS_BASE_URL}/items/${orderId}`);
  },

  deleteOrder(orderId) {
    return axios.delete(`${ORDERS_BASE_URL}/${orderId}`);
  }

};

export default api;
