// pages/my/my.js
import {Coupon} from "../../models/coupon";
import {promisic} from "../../utils/util";
import {AuthAddress, CouponStatus} from "../../core/enum";

Page({

    /**
     * 页面的初始数据
     */
    data: {
        couponCount: 0
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: async function (options) {
        const coupons = await Coupon.getMyCoupons(CouponStatus.AVAILABLE)
        this.setData({
            couponCount: coupons.length
        })
    },

    onGotoMyCoupon(event) {
        wx.navigateTo({
            url: "/pages/my-coupon/my-coupon"
        })
    },

    onGotoMyOrder(event) {
        wx.navigateTo({
            url: "/pages/my-order/my-order?key=0"
        })
    },

    onGotoMyFoot(event) {
      wx.navigateTo({
        url: `/pages/foot/foot?tname=t-1`
      })
  },

    onGotoLinUI() {
        wx.navigateToMiniProgram({
            appId:'Lin-UI AppID'
        })
    },

    async onMgrAddress(event) {
        const authStatus = await this.hasAuthorizedAddress()
        if (authStatus === AuthAddress.DENY) {
            this.setData({
                showDialog: true
            })
            return
        }
        this.openAddress()
    },

    async hasAuthorizedAddress() {
        const setting = await promisic(wx.getSetting)();
        const addressSetting = setting.authSetting['scope.address']
        if (addressSetting === undefined) {
            return AuthAddress.NOT_AUTH
        }
        if (addressSetting === false) {
            return AuthAddress.DENY
        }
        if (addressSetting === true) {
            return AuthAddress.AUTHORIZED
        }
    },

    async openAddress() {
        let res;
        res = await promisic(wx.chooseAddress)();
    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {

    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function () {

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function () {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function () {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {

    }
})