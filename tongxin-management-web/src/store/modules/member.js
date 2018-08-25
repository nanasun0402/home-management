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
        members: [],
        currentMember: null
    },
    ui: {
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
    getCurrentMember: state => state.data.currentMember,
    getMembers: state => state.data.members,
    getMemberPage: state => state.ui.page,
    getMemberPageSize: state => state.ui.page.size,
    getCurrerntMemberPage: state => state.ui.page.number + 1,
    getMemberTotalPage: state => state.ui.page.pages,
    getMemberDataCount: state => state.ui.page.total,
    getMemberFilter: state => state.ui.page.filter,
    hasMemberFilter: state => state.ui.page.filter.userName || state.ui.page.filter.mobile || state.ui.page.filter.sex || state.ui.page.filter.institution
}

const actions = {
    getMembersByPage({ commit, state }) {
        api.getMembersByPage(state.ui.page, response => {
            commit(types.SET_MEMBER_PAGE, { pages: response.data.pages, total: response.data.total })
            commit(types.SET_MEMBERS, { members: response.data.content })
        })
    },
    // getMembersByUserId({ commit }, { userId }) {
    //     api.getMembersByUserId(userId, response => {
    //         commit(types.SET_MEMBERS_OF_USER, { membersOfCurrentUser: response.data })
    //     })
    // },
    addMember({ commit, dispatch }, member) {
        api.addMember(member).then(res => {
            dispatch('getMembersByPage')
        })
    },
    editMember({ commit, dispatch }, member) {
        api.editMember(member).then(res => {
            dispatch('getMembersByPage')
        })
    },
    deleteMember({ commit, dispatch }, id) {
        api.deleteMember(id).then(res => {
            dispatch('getMembersByPage')
        })
    }
}

const mutations = {
    [types.SET_CURRENT_MEMBER](state, { currentMember }) {
        console.log('setAuth::', currentMember)
        state.data.currentMember = currentMember
    },
    [types.SET_MEMBERS](state, { members }) {
        state.data.members = members
    },
    [types.SET_MEMBER_FILTER](state, { filter }) {
        state.ui.page.filter = filter
    },
    [types.RESET_MEMBER_FILTER](state) {
        state.ui.page.filter = { ...filterInit }
    },
    [types.SET_MEMBER_PAGE](state, { pageSize, currentPage, pages, total }) {
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
