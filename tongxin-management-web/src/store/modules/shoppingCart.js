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
        shoppingCarts: [],
        currentShoppingCart: null
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
    getShoppingCarts: state => state.data.shoppingCarts,
    getCurrentShoppingCart: state => state.data.currentShoppingCart,
    getShoppingCartPage: state => state.ui.page,
    getShoppingCartPageSize: state => state.ui.page.size,
    getCurrerntShoppingCartPage: state => state.ui.page.number + 1,
    getShoppingCartTotalPage: state => state.ui.page.pages,
    getShoppingCartDataCount: state => state.ui.page.total,
    getShoppingCartFilter: state => state.ui.page.filter,
    hasShoppingCartFilter: state => state.ui.page.filter.userName || state.ui.page.filter.mobile || state.ui.page.filter.sex || state.ui.page.filter.institution
}

const actions = {
    getShoppingCartsByPage({ commit, state }, page) {
        api.getShoppingCartsByPage(page ? page : state.ui.page, response => {
            console.log(1212121, response)
            commit(types.SET_SHOPPING_CART_PAGE, { pages: response.data.pages, total: response.data.total })
            commit(types.SET_SHOPPING_CARTS, { shoppingCarts: response.data.content })
        })
    },
    // addShoppingCart({ commit, dispatch }, shoppingCart) {
    //     api.addShoppingCart(shoppingCart).then(res => {
    //         commit(types.SET_CURRENT_SHOPPING_ID, res.data.id)
    //         dispatch('getShoppingsByPage')
    //     })
    // },
    editShoppingCart({ commit, dispatch }, shoppingCart) {
        api.editShoppingCart(shoppingCart).then(res => {
            dispatch('getShoppingCartsByPage')
        })
    },
    deleteShoppingCart({ commit, dispatch }, id) {
        api.deleteShoppingCart(id).then(res => {
            dispatch('getShoppingCartsByPage')
        })
    }
}

const mutations = {
    [types.SET_CURRENT_SHOPPING_CART](state, { currentShoppingCart }) {
        state.data.currentShoppingCart = currentShoppingCart
    },
    [types.SET_SHOPPING_CARTS](state, { shoppingCarts }) {
        state.data.shoppingCarts = shoppingCarts
    },
    [types.SET_SHOPPING_CART_FILTER](state, { filter }) {
        state.ui.page.filter = filter
    },
    [types.RESET_SHOPPING_CART_FILTER](state) {
        state.ui.page.filter = { ...filterInit }
    },
    [types.SET_SHOPPING_CART_PAGE](state, { pageSize, currentPage, pages, total }) {
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
