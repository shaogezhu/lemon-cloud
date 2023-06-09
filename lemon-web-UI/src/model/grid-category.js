import { post, get, put, _delete } from '@/lin/plugin/axios'

class GridCategory {
  static async addGridCategory(data) {
    const res = await post('/grid-category', data)
    return res
  }

  static async getGridCategory(id) {
    const res = await get(`/grid-category/${id}`)
    return res
  }

  static async editGridCategory(id, data) {
    const res = await put(`/grid-category/${id}`, data)
    return res
  }

  static async deleteGridCategory(id) {
    const res = await _delete(`/grid-category/${id}`)
    return res
  }

  static async getGridCategories() {
    const res = await get('/grid-category/list')
    return res
  }
}

export default GridCategory
