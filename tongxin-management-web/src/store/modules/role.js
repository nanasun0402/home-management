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
        roles: [],
        currentRole: null,
        membersOfCurrentRole: null
    },
    ui: {
        showCertificateDialog: false,
        showMembersDialog: false,
        page: {
            size: 10, number: 0, total: 0, pages: 0,
            filter: { ...filterInit }
        }
    }
}

const getters = {
    getCurrentRole: state => state.data.currentRole,
    getRoles: state => state.data.roles,
    getMembersOfCurrentRole: state => state.data.membersOfCurrentRole,
    getRolePage: state => state.ui.page,
    getRolePageSize: state => state.ui.page.size,
    getCurrerntRolePage: state => (state.ui.page.number + 1),
    getRoleTotalPage: state => state.ui.page.pages,
    getRoleDataCount: state => state.ui.page.total,
    getRoleFilter: state => state.ui.page.filter,
    hasRoleFilter: state => (state.ui.page.filter.userName || state.ui.page.filter.mobile || state.ui.page.filter.sex || state.ui.page.filter.institution)
}

const actions = {
    getRolesByPage({ commit, state }) {
        api.getRolesByPage(state.ui.page, response => {
            commit(types.SET_ROLE_PAGE, { pages: response.data.pages, total: response.data.total })
            commit(types.SET_ROLES, { roles: response.data.content })
        })
    },
    getMembersByRoleId({ commit }, { roleId }) {
        api.getMembersByRoleId(roleId, response => {
            commit(types.SET_MEMBERS_OF_ROLE, { membersOfCurrentRole: response.data })
        })
    },
    addRole({ commit, dispatch }, role) {
        api.addRole(role).then(res => {
            dispatch('getRolesByPage');
        })
    },
    editRole({ commit, dispatch }, role) {
        api.editRole(role).then(res => {
            dispatch('getRolesByPage');
        })
    },
    deleteRole({ commit, dispatch }, id) {
        api.deleteRole(id).then(res => {
            dispatch('getRolesByPage');
        })
    }
}

const mutations = {
    [types.SET_CURRENT_ROLE](state, { currentRole }) {
        state.data.currentRole = currentRole
    },
    [types.SET_ROLES](state, { roles }) {
        state.data.roles = roles
    },
    [types.SET_ROLE_FILTER](state, { filter }) {
        state.ui.page.filter = filter
    },
    [types.RESET_ROLE_FILTER](state) {
        state.ui.page.filter = { ...filterInit }
    },
    [types.SET_MEMBERS_OF_ROLE](state, { membersOfCurrentRole }) {
        state.data.membersOfCurrentRole = membersOfCurrentRole
    },
    [types.SET_SHOW_CERTIFICATE_DIALOG](state, { showCertificateDialog }) {
        state.ui.showCertificateDialog = showCertificateDialog
    },
    [types.SET_SHOW_MEMBERS_DIALOG](state, { showMembersDialog }) {
        state.ui.showMembersDialog = showMembersDialog
    },
    [types.SET_ROLE_PAGE](state, { pageSize, currentPage, pages, total }) {
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
