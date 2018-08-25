import { api } from '@/api'
import * as types from '@/store/mutation-types'
const filterInit = {
    userName: '',
    mobile: '',
    sex: '',
    institution: ''
}

const state = {
    data: {
        orders: [],
        currentOrder: null,
        currentComment: null
    },
    ui: {
        page: {
            size: 10,
            number: 0,
            total: 0,
            pages: 0,
            filter: { ...filterInit }
        }
    }
}

const getters = {
    getOrders: state => state.data.orders,
    getCurrentOrder: state => state.data.currentOrder,
    getCurrentComment: state => state.data.currentComment,
    getOrderPage: state => state.ui.page,
    getOrderPageSize: state => state.ui.page.size,
    getCurrerntOrderPage: state => state.ui.page.number + 1,
    getOrderTotalPage: state => state.ui.page.pages,
    getOrderDataCount: state => state.ui.page.total,
    getOrderFilter: state => state.ui.page.filter,
    hasOrderFilter: state => state.ui.page.filter.userName || state.ui.page.filter.mobile || state.ui.page.filter.sex || state.ui.page.filter.institution
}

const actions = {
    getOrdersByPage({ commit, state }, page) {
        api.getOrdersByPage(page ? page : state.ui.page, response => {
            console.log(1212121, response)
            commit(types.SET_ORDER_PAGE, { pages: response.data.pages, total: response.data.total })
            commit(types.SET_ORDERS, { orders: response.data.content })
        })
    },
    // addShoppingCart({ commit, dispatch }, shoppingCart) {
    //     api.addShoppingCart(shoppingCart).then(res => {
    //         commit(types.SET_CURRENT_SHOPPING_ID, res.data.id)
    //         dispatch('getShoppingsByPage')
    //     })
    // },
    editOrder({ commit, dispatch }, order) {
        api.editOrder(order).then(res => {
            dispatch('getOrdersByPage')
        })
    },
    deleteOrder({ commit, dispatch }, id) {
        api.deleteOrder(id).then(res => {
            dispatch('getOrdersByPage')
        })
    },
    deleteComment({ commit, dispatch }, id) {
        api.deleteComment(id).then(res => {
            dispatch('getOrdersByPage')
        })
    },
    getCommentByOrderIdAndMemberId({ commit }, { orderId, memberId}) {
        api.getCommentByOrderIdAndMemberId(orderId, memberId, response => {
            commit(types.SET_CURRENT_COMMENT, {currentComment: response.data})
        })
    }
}

const mutations = {
    [types.SET_CURRENT_ORDER](state, { currentOrder }) {
        state.data.currentOrder = currentOrder
    },
    [types.SET_CURRENT_COMMENT](state, { currentComment }) {
        state.data.currentComment = currentComment
    },
    [types.SET_ORDERS](state, { orders }) {
        state.data.orders = orders
    },
    [types.SET_ORDER_FILTER](state, { filter }) {
        state.ui.page.filter = filter
    },
    [types.RESET_ORDER_FILTER](state) {
        state.ui.page.filter = { ...filterInit }
    },
    [types.SET_ORDER_PAGE](state, { pageSize, currentPage, pages, total }) {
        if (pageSize) {
            state.ui.page.size = pageSize
        }
        if (currentPage) {
            state.ui.page.number = currentPage - 1
        }
        if (pages) {
            state.ui.page.pages = pages
        }
        if (total) {
            state.ui.page.total = total
        }
    }
}

export default {
    state,
    getters,
    actions,
    mutations
}
