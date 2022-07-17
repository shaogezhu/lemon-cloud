import { post, get, put, _delete } from '@/lin/plugin/axios'

class Category {
  static async addCategory(data) {
    const res = await post('/category', data)
    return res
  }

  static async getCategory(id) {
    const res = await get(`/category/${id}`)
    return res
  }

  static async editCategory(id, data) {
    const res = await put(`/category/${id}`, data)
    return res
  }

  static async deleteCategory(id) {
    const res = await _delete(`/category/${id}`)
    return res
  }

  /**
   * 分页获取分类数据
   * @param page
   * @param count
   * @param root 1: 父类
   * @returns {Promise<*>}
   */
  static async getCategories(page = 0, count = 10, root = 1) {
    const res = await get('/category/page', {
      page,
      count,
      root,
    })
    return res
  }

  /**
   * 分页获取子分类数据
   * @param page
   * @param count
   * @param id 父分类id
   * @returns {Promise<*>}
   */
  static async getSubCategories(page = 0, count = 10, id = 1) {
    const res = await get('/category/sub-page', {
      page,
      count,
      id,
    })
    return res
  }

  static async getList() {
    const res = await get('/category/list')
    return res
  }
}

export default Category
