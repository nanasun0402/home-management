import { api } from "@/api";
import * as types from "@/store/mutation-types";

const filterInit = {
    userName: "",
    mobile: "",
    sex: "",
    institution: ""
};

const state = {
    data: {
        goods: [],
        currentGoods: null,
        currentGoodsId: null
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
};

const getters = {
    getGoods: state => state.data.goods,
    getCurrentGoods: state => state.data.currentGoods,
    getCurrentGoodsId: state => state.data.currentGoodsId,
    getGoodsPage: state => state.ui.page,
    getGoodsPageSize: state => state.ui.page.size,
    getCurrerntGoodsPage: state => state.ui.page.number + 1,
    getGoodsTotalPage: state => state.ui.page.total,
    getGoodsDataCount: state => state.ui.page.pages,
    getGoodsFilter: state => state.ui.page.filter,
    getGoodsImages: state =>
        state.data.currentGoods && state.data.currentGoods.images
            ? state.data.currentGoods.images
            : [],
    getGoodsDetailImages: state =>
        state.data.currentGoods && state.data.currentGoods.detailImages
            ? state.data.currentGoods.detailImages
            : [],
    getGoodsSizes: state =>
        state.data.currentGoods && state.data.currentGoods.sizes
            ? state.data.currentGoods.sizes
            : [],
    getGoodsColors: state =>
        state.data.currentGoods && state.data.currentGoods.colors
            ? state.data.currentGoods.colors
            : [],
    getSizeColors: state =>
        state.data.currentGoods &&
        state.data.currentGoods.sizes &&
        state.data.currentGoods.sizes.colors
            ? state.data.currentGoods.sizes.colors
            : []
};

const actions = {
    getGoodsByPage({ commit, state }, page) {
        api.getGoodsByPage(page ? page : state.ui.page, response => {
            commit(types.SET_GOODS_PAGE, {
                pages: response.data.pages,
                total: response.data.total
            });
            commit(types.SET_GOODS, { goods: response.data.content });
        });
    },
    getGoodsById({ commit, state }, id) {
        commit(types.SET_CURRENT_GOODS_ID, id);
        if (id) {
            api.getGoodsById(id).then(res => {
                commit(types.SET_CURRENT_GOODS, res.data);
            });
        }
    },
    addGoods({ commit, dispatch }, goods) {
        api.addGoods(goods).then(res => {
            commit(types.SET_CURRENT_GOODS_ID, res.data.id);
            dispatch("getGoodsByPage");
        });
    },
    editGoods({ commit, dispatch }, goods) {
        api.editGoods(goods).then(res => {
            commit(types.SET_CURRENT_GOODS, res.data);
            commit(types.SET_CURRENT_GOODS_ID, res.data.id);
            dispatch("getGoodsByPage");
        });
    },
    deleteGoods({ commit, dispatch }, id) {
        api.deleteGoods(id).then(res => {
            dispatch("getGoodsByPage");
        });
    }
};

const mutations = {
    [types.SET_GOODS](state, { goods }) {
        state.data.goods = goods;
    },
    [types.SET_CURRENT_GOODS](state, goods) {
        state.data.currentGoods = goods;
    },
    [types.SET_CURRENT_GOODS_ID](state, id) {
        state.data.currentGoodsId = id;
    },
    [types.SET_GOODS_FILTER](state, { filter }) {
        state.ui.page.filter = filter;
    },
    [types.RESET_GOODS_FILTER](state) {
        state.ui.page.filter = { ...filterInit };
    },
    [types.SET_GOODS_PAGE](state, { pageSize, currentPage, pages, total }) {
        if (pageSize) {
            state.ui.page.size = pageSize;
        }
        if (currentPage) {
            state.ui.page.number = currentPage - 1;
        }
        if (pages) {
            state.ui.page.pages = total;
        }
        if (total) {
            state.ui.page.total = pages;
        }
    }
};

export default {
    state,
    getters,
    actions,
    mutations
};
