<template>
  <div class="container">
    <div class="header">
      <div class="title">Banner详情</div>
      <span @click="onGoBack"><i class="iconfont icon-fanhui" /> 返回</span>
    </div>
    <el-divider />
    <el-form :model="from" class="form-container">
      <el-form-item label="名称" label-width="100px">
        <el-input v-model="from.name" size="medium"></el-input>
      </el-form-item>
      <el-form-item label="标题" label-width="100px">
        <el-input v-model="from.title" size="medium"></el-input>
      </el-form-item>
      <el-form-item label="Banner描述" label-width="100px">
        <el-input v-model="from.description" size="medium"></el-input>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import Banner from '@/model/banner'

export default {
  data() {
    return {
      from: {},
    }
  },
  props: {
    bannerId: {
      type: Number,
    },
  },
  async created() {
    const detail = await this.getBannerDetail()
    console.log(detail)
    this.from = detail
  },
  methods: {
    onGoBack() {
      this.$emit('detail-close')
    },

    async getBannerDetail() {
      const res = await Banner.getBannerDetail(this.bannerId)
      return res
    },
  },
}
</script>

<style lang="scss" scoped>
.container {
  padding: 20px 30px 0 30px;

  .title {
    font-size: 16px;
    font-weight: 500;
    // margin-bottom: 20px;
    color: $theme;
  }

  .header {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    color: $theme;
    cursor: pointer;
  }

  .form-container {
    width: 600px;
  }
}
</style>
