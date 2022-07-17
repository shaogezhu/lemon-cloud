// pages/foot/foot.js
import {Spu} from "../../models/spu";
//import {themeBehavior} from "../../components/behaviors/theme-beh";

Page({  
//  behaviors: [themeBehavior],
    /**
     * 页面的初始数据
     */
  data: {
      spuList: null,
      topImg: "../../imgs/foot.png"
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: async function (options) {
    const _spuList = await Spu.getUserFoot()
      this.setData({
          spuList: _spuList
      })
  },

  
    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {
    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: async function () {
      // const _spuList = await Spu.getUserFoot()
      // this.setData({
      //     spuList: _spuList
      // })
    },

      onGoToSpu(event) {
        const pid = event.currentTarget.dataset.spuId
        wx.navigateTo({
            url: `/pages/detail/detail?pid=${pid}`
        })
      },

      onLoadImg(event) {
        // 440 750
        const {height, width} = event.detail
        this.setData({
            h: height,
            w: width,
        })
      }
})