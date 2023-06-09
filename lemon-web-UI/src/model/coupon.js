import { post, get, put, _delete } from '@/lin/plugin/axios'

class Coupon {
  static types = ['满减券', '折扣券', '无门槛券', '满金额折扣券']

  static async addCoupon(data) {
    const res = await post('/coupon', data)
    return res
  }

  static async getCoupon(id) {
    const res = await get(`/coupon/${id}`)
    return res
  }

  static async editCoupon(id, data) {
    const res = await put(`/coupon/${id}`, data)
    return res
  }

  static async deleteCoupon(id) {
    const res = await _delete(`/coupon/${id}`)
    return res
  }

  static async addCouponTemplate(data) {
    const res = await post('/coupon/template', data)
    return res
  }

  static async editCouponTemplate(id, data) {
    const res = await put(`/coupon/template/${id}`, data)
    return res
  }

  static async getCouponTemplate(id) {
    const res = await get(`/coupon/template/${id}`)
    return res
  }

  static async deleteCouponTemplate(id) {
    const res = await _delete(`/coupon/template/${id}`)
    return res
  }

  static async getCouponTemplates() {
    const res = await get('/coupon/templates')
    return res
  }

  static async getListByActivityId(id) {
    const res = await get(`/coupon/list?id=${id}`)
    return res
  }
}

export default Coupon
