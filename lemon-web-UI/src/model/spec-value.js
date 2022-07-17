import { post, get, put, _delete } from '../lin/plugin/axios'

class SpecValue {
  static async addSpecValue(data) {
    const res = await post('/spec-value', data)
    return res
  }

  static async getSpecValue(id) {
    const res = await get(`/spec-value/${id}`)
    return res
  }

  static async editSpecValue(id, data) {
    const res = await put(`/spec-value/${id}`, data)
    return res
  }

  static async deleteSpecValue(id) {
    const res = await _delete(`/spec-value/${id}`)
    return res
  }

  static async getSpecValues(page = 0, count = 10) {
    const res = await get('/spec-value/page', {
      page,
      count,
    })
    return res
  }

  static async getBySpecKeyId(keyId) {
    const res = await get(`/spec-value/by/spec-key/${keyId}`)
    return res
  }
}
export default SpecValue
