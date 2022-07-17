import {Spu} from "../../models/spu";
import {CouponCenterType, ShoppingWay} from "../../core/enum";
import {SaleExplain} from "../../models/sale-explain";
import {getWindowHeightRpx} from "../../utils/system";
import {Cart} from "../../models/cart";
import {CartItem} from "../../models/cart-item";
import {Coupon} from "../../models/coupon";

Page({

    data: {
        showRealm: false,
        cartItemCount: 0
    },

    onLoad: async function (options) {
        const pid = options.pid
        const spu = await Spu.getDetail(pid)
        const coupons = await Coupon.getTop2CouponsByCategory(spu.category_id)

        const explain = await SaleExplain.getFixed()
        const windowHeight = await getWindowHeightRpx()
        const h = windowHeight - 100

        this.setData({
            spu,
            explain,
            h,
            coupons
        })
        this.updateCartItemCount()
    },

    onGoToCouponCenter(event) {
        const type = CouponCenterType.SPU_CATEGORY
        const cid = this.data.spu.category_id
        wx.navigateTo({
            url: `/pages/coupon/coupon?cid=${cid}&type=${type}`
        })
    },

    onAddToCart(event) {
        this.setData({
            showRealm: true,
            orderWay: ShoppingWay.CART
        })
    },

    onBuy(event) {
        this.setData({
            showRealm: true,
            orderWay: ShoppingWay.BUY
        })
    },

    onShopping(event) {
        const chosenSku = event.detail.sku
        const skuCount = event.detail.skuCount

        if (event.detail.orderWay === ShoppingWay.CART) {
            const cart = new Cart()
            const cartItem = new CartItem(chosenSku, skuCount)
            cart.addItem(cartItem)
            this.updateCartItemCount()
        }

        if(event.detail.orderWay === ShoppingWay.BUY){
            wx.navigateTo({
                url:`/pages/order/order?sku_id=${chosenSku.id}&count=${skuCount}&way=${ShoppingWay.BUY}`
            })
        }
    },

    updateCartItemCount() {
        const cart = new Cart()
        this.setData({
            cartItemCount: cart.getCartItemCount(),
            showRealm: false
        })
    },

    onGotoHome(event) {
        wx.switchTab({
            url: '/pages/home/home'
        })
    },

    onGotoCart(event) {
        wx.switchTab({
            url: '/pages/cart/cart'
        })
    },

    onSpecChange(event) {
        this.setData({
            specs: event.detail
        })
    },

    onReady: function () {

    },


    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {

    }
})