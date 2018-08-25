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
        users: [],
        currentUser: null,
        membersOfCurrentUser: null
    },
    ui: {
        showCertificateDialog: false,
        showMembersDialog: false,
        page: {
            size: 10,
            number: 0,
            pages: 0,
            total: 0,
            filter: { ...filterInit }
        }
    }
}

const getters = {
    getCurrentUser: state => state.data.currentUser,
    getUsers: state => state.data.users,
    getMembersOfCurrentUser: state => state.data.membersOfCurrentUser,
    getUserPage: state => state.ui.page,
    getUserPageSize: state => state.ui.page.size,
    getCurrerntUserPage: state => state.ui.page.number + 1,
    getUserTotalPage: state => state.ui.page.pages,
    getUserDataCount: state => state.ui.page.total,
    getUserFilter: state => state.ui.page.filter,
    hasUserFilter: state => state.ui.page.filter.userName || state.ui.page.filter.mobile || state.ui.page.filter.sex || state.ui.page.filter.institution
}

const actions = {
    getUsersByPage({ commit, state }) {
        api.getUsersByPage(state.ui.page, response => {
            commit(types.SET_USER_PAGE, { pages: response.data.pages, total: response.data.total })
            commit(types.SET_USERS, { users: response.data.content })
        })
    },
    getMembersByUserId({ commit }, { userId }) {
        api.getMembersByUserId(userId, response => {
            commit(types.SET_MEMBERS_OF_USER, { membersOfCurrentUser: response.data })
        })
    },
    addUser({ commit, dispatch }, user) {
        api.addUser(user).then(res => {
            dispatch('getUsersByPage')
        })
    },
    editUser({ commit, dispatch }, user) {
        api.editUser(user).then(res => {
            dispatch('getUsersByPage')
        })
    },
    deleteUser({ commit, dispatch }, id) {
        api.deleteUser(id).then(res => {
            dispatch('getUsersByPage')
        })
    }
}

const mutations = {
    [types.SET_CURRENT_USER](state, { currentUser }) {
        console.log('setAuth::', currentUser)
        state.data.currentUser = currentUser
    },
    [types.SET_USERS](state, { users }) {
        state.data.users = users
    },
    [types.SET_USER_FILTER](state, { filter }) {
        state.ui.page.filter = filter
    },
    [types.RESET_USER_FILTER](state) {
        state.ui.page.filter = { ...filterInit }
    },
    [types.SET_MEMBERS_OF_USER](state, { membersOfCurrentUser }) {
        state.data.membersOfCurrentUser = membersOfCurrentUser
    },
    [types.SET_SHOW_CERTIFICATE_DIALOG](state, { showCertificateDialog }) {
        state.ui.showCertificateDialog = showCertificateDialog
    },
    [types.SET_SHOW_MEMBERS_DIALOG](state, { showMembersDialog }) {
        state.ui.showMembersDialog = showMembersDialog
    },
    [types.SET_USER_PAGE](state, { pageSize, currentPage, pages, total }) {
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
