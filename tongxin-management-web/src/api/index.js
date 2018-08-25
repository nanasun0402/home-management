import Vue from 'vue'
import router from '@/router'
import VueResource from 'vue-resource'
import { Message, MessageBox, Loading } from 'element-ui'
import { setCookie } from 'tiny-cookie'
import * as types from '@/store/mutation-types'
import JacksonParser from 'jackson-parser'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(VueResource)
export function decodeJson(json) {
    return json ? JacksonParser.decode(json, { key: '@id' }) : json
}

export const LoginUser = {
    currentUser: null,
    get: () => {
        if (!this.currentUser) {
            let item = window.localStorage.getItem('currentUser')
            this.currentUser = item && item !== 'undefined' && item != 'null' ? JSON.parse(item) : null
        }
        return this.currentUser
    },
    save: currentUser => {
        this.currentUser = currentUser
        window.localStorage.setItem('currentUser', currentUser ? JSON.stringify(currentUser) : null)
        if (currentUser) {
            setCookie(types.X_MG_AUTH_TOKEN, currentUser.token[types.X_MG_AUTH_TOKEN])
        }
    },
    isAuthenticated: () => {
        if (!this.currentUser) {
            let item = window.localStorage.getItem('currentUser')
            this.currentUser = item && item !== 'undefined' && item != 'null' ? JSON.parse(item) : null
        }
        return this.currentUser && this.currentUser.token
    },
    isRole(role) {
        if (!this.isAuthenticated()) {
            return false
        }
        let user = this.get().user
        if (user && user.username === types.ADMIN) {
            return true
        } else if (user && user.authorities) {
            for (let authority of user.authorities) {
                if (role === authority.key) {
                    return true
                }
            }
        }
        return false
    }
}

export const deepClone = obj => {
    return JSON.parse(JSON.stringify(obj))
}

Vue.http.interceptors.push(function(request, next) {
    console.log('Vue-resource >>', request)
    let loadingInstance = null
    const closeLoading = () => {
        if (loadingInstance) {
            loadingInstance.close()
            loadingInstance = null
        }
    }
    const responseHandler = response => {
        closeLoading()
        if (!response.ok) {
            if (response.body && response.body.message) {
                if (request.method === 'DELETE' && response.body.message.indexOf('SQL') >= 0) {
                    Message.error('删除错误，数据可能被引用，不能被删除')
                } else {
                    Message.error(response.body.message)
                }
            } else {
                Message.error('操作错误')
            }
            console.log(response.statusText, response.bodyText)
        } else if (request.method === 'PUT') {
            console.log(request)
            Message.success('保存成功')
        } else if (request.method === 'DELETE') {
            Message.success('删除成功')
        }
        console.log('Vue-resource <<', response)
    }

    if (LoginUser.isAuthenticated()) {
        request.headers.set(types.X_MG_AUTH_TOKEN, LoginUser.get().token[types.X_MG_AUTH_TOKEN])
    }
    if (request.method === 'POST') {
        loadingInstance = Loading.service({ fullscreen: true, text: '玩命加载中 ...', target: '#loadingTarget' })
        setTimeout(() => closeLoading(), 20000)
        next(responseHandler)
    } else if (request.method === 'DELETE') {
        MessageBox.confirm('确定要删除么？')
            .then(() => {
                next(responseHandler)
            })
            .catch(() => console.log('cancel deleting'))
    } else {
        next(responseHandler)
    }
    // continue to next interceptor
})

class Api {
    // admin
    login(username, password, cb) {
        Vue.http.post('/api/user/login', { username, password }).then(response => {
            LoginUser.save(response.data)
            cb ? cb(response) : undefined
        })
    }
    getUsersByPage(page, cb) {
        Vue.http.post('/api/user', page).then(response => (cb ? cb(response) : undefined))
    }

    addUser(user) {
        return Vue.http.put('/api/user', user)
    }

    editUser(user) {
        return Vue.http.put('/api/user', user)
    }

    deleteUser(id) {
        return Vue.http.delete('/api/user/' + id)
    }

    // member
    // memberLogin(username, password, cb) {
    //     Vue.http.post('/api/member/login', { username, password }).then(response => {
    //         LoginUser.save(response.data)
    //         cb ? cb(response) : undefined
    //     })
    // }
    getMembersByPage(page, cb) {
        Vue.http.post('/api/member', page).then(response => (cb ? cb(response) : undefined))
    }

    addMember(member) {
        return Vue.http.put('/api/member', member)
    }

    editMember(member) {
        return Vue.http.put('/api/member', member)
    }

    deleteMember(id) {
        return Vue.http.delete('/api/member/' + id)
    }

    //role
    getRoles(cb) {
        Vue.http.get('/api/role').then(response => (cb ? cb(response) : undefined), error => console.log('Error: ', error))
    }

    getRolesByPage(page, cb) {
        Vue.http.post('/api/role', page).then(response => (cb ? cb(response) : undefined))
    }

    addRole(role) {
        return Vue.http.put('/api/role', role)
    }

    editRole(role) {
        return Vue.http.put('/api/role', role)
    }

    deleteRole(id) {
        return Vue.http.delete('/api/role/' + id)
    }

    // goods
    getGoodsByPage(page, cb) {
        Vue.http.post('/api/goods', page).then(response => (cb ? cb(response) : undefined))
    }

    addGoods(goods) {
        return Vue.http.put('/api/goods', goods)
    }

    editGoods(goods) {
        return Vue.http.put('/api/goods', goods)
    }

    deleteGoods(id) {
        return Vue.http.delete('/api/goods/' + id)
    }

    getGoodsById(id) {
        return Vue.http.get('/api/goods/' + id)
    }

    // shoppingCart
    getShoppingCartsByPage(page, cb) {
        Vue.http.post('/api/shoppingCart', page).then(response => (cb ? cb(response) : undefined))
    }

    addShoppingCart(shoppingCart) {
        return Vue.http.put('/api/shoppingCart', shoppingCart)
    }

    editShoppingCart(shoppingCart) {
        return Vue.http.put('/api/shoppingCart', shoppingCart)
    }

    deleteShoppingCart(id) {
        return Vue.http.delete('/api/shoppingCart/' + id)
    }

    // order
    getOrdersByPage(page, cb) {
        Vue.http.post('/api/order', page).then(response => (cb ? cb(response) : undefined))
    }

    addOrder(order) {
        return Vue.http.put('/api/order', order)
    }

    editOrder(order) {
        return Vue.http.put('/api/order', order)
    }

    deleteOrder(id) {
        return Vue.http.delete('/api/order/' + id)
    }

    // comment
    getCommentByOrderIdAndMemberId(orderId, memberId, cb) {
        Vue.http.get(`/api/comment?orderId=${orderId}&memberId=${memberId}`).then(response => (cb ? cb(response) : undefined))
    }

    deleteComment(id) {
        return Vue.http.delete('/api/comment/' + id)
    }

    //const
    getConstants(cb) {
        Vue.http.get('/api/const').then(response => (cb ? cb(response) : undefined))
    }

    getConstantsGroup(cb) {
        Vue.http.get('/api/const/group').then(response => (cb ? cb(response) : undefined))
    }
}

export const api = new Api()
