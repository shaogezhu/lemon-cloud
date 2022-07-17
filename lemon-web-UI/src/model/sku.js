import { post, get, put, _delete } from '@/lin/plugin/axios'

class Sku {
  static async addSku(data) {
    const res = await post('/sku', data)
    return res
  }

  static async getSku(id) {
    const res = await get(`/sku/${id}`)
    return res
  }

  static async getSkuDetail(id) {
    const res = await get(`/sku/${id}/detail`)
    return res
  }

  static async editSku(id, data) {
    const res = await put(`/sku/${id}`, data)
    return res
  }

  static async deleteSku(id) {
    const res = await _delete(`/sku/${id}`)
    return res
  }

  static async getSkus(page = 0, count = 10) {
    const res = await get('/sku/page', { page, count })
    return res
  }

  static async getBySpuId(spuId) {
    const res = await get(`/sku/by/spu/${spuId}`)
    return res
  }

  static async getSpecValueId(skuId, keyId) {
    const res = await get('/sku/spec-value-id', { key_id: keyId, sku_id: skuId })
    return res
  }
}

export default Sku
