import { post, get, put, _delete } from '@/lin/plugin/axios'

class Banner {
  static async addBanner(data) {
    const res = await post('/banner', data)
    return res
  }

  static async getBanner(id) {
    const res = await get(`/banner/${id}`)
    return res
  }

  static async editBanner(id, data) {
    const res = await put(`/banner/${id}`, data)
    return res
  }

  static async deleteBanner(id) {
    const res = await _delete(`/banner/${id}`)
    return res
  }

  static async getBanners(page = 0, count = 10) {
    const res = await get('/banner/page', {
      page,
      count,
    })
    return res
  }
}

export default Banner
