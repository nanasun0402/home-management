import Vue from 'vue'
import Router from 'vue-router'
import 'element-ui/lib/theme-chalk/index.css'

import {
    Pagination,
    Dialog,
    Dropdown,
    DropdownMenu,
    DropdownItem,
    Menu,
    Submenu,
    MenuItem,
    MenuItemGroup,
    Input,
    InputNumber,
    Radio,
    RadioGroup,
    RadioButton,
    Checkbox,
    CheckboxGroup,
    Cascader,
    Switch,
    Select,
    Option,
    OptionGroup,
    Button,
    ButtonGroup,
    Table,
    TableColumn,
    Breadcrumb,
    BreadcrumbItem,
    Form,
    FormItem,
    Tabs,
    TabPane,
    Icon,
    Row,
    Col,
    Upload,
    Progress,
    Loading,
    MessageBox,
    Message,
    DatePicker,
    Card
} from 'element-ui'

Vue.use(DatePicker)
Vue.use(Card)
Vue.use(Pagination)
Vue.use(Dialog)
Vue.use(Dropdown)
Vue.use(DropdownMenu)
Vue.use(DropdownItem)
Vue.use(Menu)
Vue.use(Submenu)
Vue.use(MenuItem)
Vue.use(MenuItemGroup)
Vue.use(Input)
Vue.use(InputNumber)
Vue.use(Radio)
Vue.use(RadioGroup)
Vue.use(RadioButton)
Vue.use(Checkbox)
Vue.use(CheckboxGroup)
Vue.use(Cascader)
Vue.use(Switch)
Vue.use(Select)
Vue.use(Option)
Vue.use(OptionGroup)
Vue.use(Button)
Vue.use(ButtonGroup)
Vue.use(Table)
Vue.use(TableColumn)
Vue.use(Breadcrumb)
Vue.use(BreadcrumbItem)
Vue.use(Form)
Vue.use(FormItem)
Vue.use(Tabs)
Vue.use(TabPane)
Vue.use(Row)
Vue.use(Col)
Vue.use(Upload)
Vue.use(Progress)
Vue.use(Loading.directive)

Vue.prototype.$loading = Loading.service
Vue.prototype.$msgbox = MessageBox
Vue.prototype.$alert = MessageBox.alert
Vue.prototype.$confirm = MessageBox.confirm
Vue.prototype.$prompt = MessageBox.prompt
Vue.prototype.$message = Message

Vue.use(Router)

window.$ = require('jquery');

import Viewer from "v-viewer";
Vue.use(Viewer)

import { LoginUser } from '@/api'
import Login from '@/components/Login'
import Home from '@/components/Home'
import Root from '@/components/Root'
import Hello from '@/components/Hello'
import UserManagement from '@/components/user/UserManagement'
import GoodsManagement from '@/components/goods/GoodsManagement'
import AddGoodsManagement from '@/components/goods/add/AddGoodsManagement'
import MemberManagement from '@/components/member/MemberManagement'
import GoodsImages from '@/components/goods/add/GoodsImages'
import GoodsDetailImages from '@/components/goods/add/GoodsDetailImages'
import GoodsSizes from '@/components/goods/size/GoodsSizes'
import ShoppingCartManagement from '@/components/shoppingCart/ShoppingCartManagement'
import OrderManagement from '@/components/order/OrderManagement'

const router = new Router({
    routes: [
        {
            path: '/login',
            name: 'login',
            component: Login
        },
        {
            path: '/home',
            redirect: '/'
        },
        {
            path: '/',
            name: '',
            component: Home,
            children: [{
                path: '',
                component: Hello
            }, {
                path: 'user',
                name: 'user',
                component: UserManagement
            }, {
                path: 'goods',
                name: 'goods',
                component: GoodsManagement
            }, {
                path: 'addgoods',
                name: 'addgoods',
                component: AddGoodsManagement
            }, {
                path: 'goodsImages',
                name: 'goodsImages',
                component: GoodsImages
            }, {
                path: 'goodsDetailImages',
                name: 'goodsDetailImages',
                component: GoodsDetailImages
            }
            , {
                path: 'goodsSizes',
                name: 'goodsSizes',
                component: GoodsSizes
            }, {
                path: 'member',
                name: 'member',
                component: MemberManagement
            }, {
                path: 'shoppingCart',
                name: 'shoppingCart',
                component: ShoppingCartManagement
            }, {
                path: 'order',
                name: 'order',
                component: OrderManagement
            }]
        }
    ]
})

export default router

