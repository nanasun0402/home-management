import Vue from 'vue'
import Vuex from 'vuex'
import { api } from '@/api'
import * as types from '@/store/mutation-types'
import user from '@/store/modules/user'
import role from '@/store/modules/role'
import goods from '@/store/modules/goods'
import member from '@/store/modules/member'
import shoppingCart from '@/store/modules/shoppingCart'
import order from '@/store/modules/order'

Vue.use(Vuex)

const state = {
    data: {
        constants: {},
        constantsGroup: {},
        regions: [],
        regionMapByName: new Map()
    },
    ui: {
        isLoading: false,
        regionOptions: []
    }
}

const getters = {
    isLoading: state => state.ui.isLoading,
    getConstants: state => state.data.constants,
    getConstantsGroup: state => state.data.constantsGroup,
    getRegionOptions: state => state.ui.regionOptions.length > 0 ? state.ui.regionOptions : [{ value: undefined, label: "加载中 ..." }],
    getRegionMapByName: state => state.data.regionMapByName
}

const actions = {
    getConstants({ commit }) {
        api.getConstants(response => {
            commit(types.SET_CONSTANTS, { constants: response.data })
        })
    },
    getConstantsGroup({ commit }) {
        api.getConstantsGroup(response => {
            commit(types.SET_CONSTANTS_GROUP, { constantsGroup: response.data })
        })
    },
    getRegions({ commit }) {
        api.getRegions(response => {
            commit(types.SET_REGIONS, { regions: response.data })
        })
    }
}

const mutations = {
    [types.SET_CONSTANTS](state, { constants }) {
        state.data.constants = constants
        //alert(state.data.constants.group.type_office)
    },
    [types.SET_CONSTANTS_GROUP](state, { constantsGroup }) {
        state.data.constantsGroup = constantsGroup
    },
    [types.SET_REGIONS](state, { regions }) {
        state.data.regions = regions
        let result = normalizeRegionToOptions(regions);
        state.ui.regionOptions = result.regtionOptions
        state.data.regionMapByName = result.regionMapByName
        console.log("region options", state.ui.regionOptions)
    }
}

function normalizeRegionToOptions(regions) {
    let regionOptions = [];
    let regionMapByName = new Map()
    regions.forEach((province) => {
        let root = {
            value: province.name,
            label: province.name
        }
        regionMapByName.set(province.name, {
            owner: province
        })
        regionOptions.push(root)
        let cityRegions = province.subRegions
        let cityMap = regionMapByName.get(province.name)
        if (cityRegions) {
            cityRegions.forEach(city => {
                let cityOption = {
                    value: city.name,
                    label: city.name
                }
                if (!root.children) {
                    root.children = []
                }
                if (!cityMap.children) {
                    cityMap.children = new Map()
                }
                root.children.push(cityOption)
                cityMap.children.set(city.name, {
                    owner: city
                })
                let countyRegions = city.subRegions
                let countyMap = cityMap.children.get(city.name)
                if (countyRegions) {
                    countyRegions.forEach(county => {
                        let countyOption = {
                            value: county.name,
                            label: county.name
                        }
                        if (!city.children) {
                            city.children = []
                        }
                        if (!countyMap.children) {
                            countyMap.children = new Map()
                        }
                        city.children.push(countyOption)
                        countyMap.children.set(county.name, {
                            owner: county
                        })
                    })
                }
            })
        }
    })
    return {
        regtionOptions: regionOptions,
        regionMapByName: regionMapByName
    }
}

export default new Vuex.Store({
    state,
    getters,
    actions,
    mutations,
    modules: {
        user,
        role,
        goods,
        member,
        shoppingCart,
        order
    }
})
