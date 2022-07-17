import { post, get, put, _delete } from '@/lin/plugin/axios'

class Activity {
  static async addActivity(data) {
    const res = await post('/activity', data)
    return res
  }

  static async getActivity(id) {
    const res = await get(`/activity/${id}`)
    return res
  }

  static async getActivityDetail(id) {
    const res = await get(`/activity/${id}/detail`)
    return res
  }

  static async editActivity(id, data) {
    const res = await put(`/activity/${id}`, data)
    return res
  }

  static async deleteActivity(id) {
    const res = await _delete(`/activity/${id}`)
    return res
  }

  static async getActivities(page = 0, count = 10) {
    const res = await get('/activity/page', { page, count })
    return res
  }
}

export default Activity
