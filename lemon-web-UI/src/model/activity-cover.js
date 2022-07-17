import { post, get, put, _delete } from '@/lin/plugin/axios'

class ActivityCover {
  async addActivityCover(data) {
    const res = await post('/activity-cover', data)
    return res
  }

  async getActivityCover(id) {
    const res = await get(`/activity-cover/${id}`)
    return res
  }

  async editActivityCover(id, data) {
    const res = await put(`/activity-cover/${id}`, data)
    return res
  }

  async deleteActivityCover(id) {
    const res = await _delete(`/activity-cover/${id}`)
    return res
  }

  async getActivityCovers(page = 0, count = 10) {
    const res = await get('/activity-cover/page', { page, count })
    return res
  }
}

export default ActivityCover
