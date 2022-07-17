import { post, get, put, _delete } from '@/lin/plugin/axios'

class Theme {
  static async addTheme(data) {
    const res = await post('/theme', data)
    return res
  }

  static async addThemeSpu(data) {
    const res = await post('/theme/spu', data)
    return res
  }

  static async getTheme(id) {
    const res = await get(`/theme/${id}`)
    return res
  }

  static async editTheme(id, data) {
    const res = await put(`/theme/${id}`, data)
    return res
  }

  static async deleteTheme(id) {
    const res = await _delete(`/theme/${id}`)
    return res
  }

  static async getThemes(page = 0, count = 10) {
    const res = await get('/theme/page', { page, count })
    return res
  }

  static async getSpus(id) {
    const res = await get('/theme/spus', { id })
    return res
  }

  static async getSpuList(id) {
    const res = await get('/theme/spu/list', { id })
    return res
  }

  static async deleteSpu(id) {
    const res = await _delete(`/theme/spu/${id}`)
    return res
  }
}

export default Theme
