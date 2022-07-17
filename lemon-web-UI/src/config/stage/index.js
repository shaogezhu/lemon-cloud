import Utils from '@/lin/util/util'
import adminConfig from './admin'
import staticsConfig from './statics'
import pluginsConfig from './plugin'
import bannerConfig from './banner'
import categoryConfig from './category'
import gridCategoryConfig from './grid-categroy'
import specConfig from './spec'
import spuConfig from './spu'
import skuConfig from './sku'
import themeConfig from './theme'
import activityConfig from './activity'

// eslint-disable-next-line import/no-mutable-exports
let homeRouter = [
  {
    title: '日志管理',
    type: 'view',
    name: Symbol('log'),
    route: '/log',
    filePath: 'view/log/log.vue',
    inNav: true,
    icon: 'iconfont icon-rizhiguanli',
    order: 1,
    permission: ['查询所有日志'],
  },
  {
    title: '个人中心',
    type: 'view',
    name: Symbol('center'),
    route: '/center',
    filePath: 'view/center/center.vue',
    inNav: false,
    icon: 'iconfont icon-rizhiguanli',
  },
  {
    title: '404',
    type: 'view',
    name: Symbol('404'),
    route: '/404',
    filePath: 'view/error-page/404.vue',
    inNav: false,
    icon: 'iconfont icon-rizhiguanli',
  },
  staticsConfig,
  bannerConfig,
  categoryConfig,
  gridCategoryConfig,
  specConfig,
  spuConfig,
  skuConfig,
  themeConfig,
  activityConfig,
  adminConfig,
]

// 接入插件
const plugins = [...pluginsConfig]
filterPlugin(homeRouter)
homeRouter = homeRouter.concat(plugins)

// 处理顺序
homeRouter = Utils.sortByOrder(homeRouter)
deepReduceName(homeRouter)

export default homeRouter

/**
 * 筛除已经被添加的插件
 */
function filterPlugin(data) {
  if (plugins.length === 0) {
    return
  }
  if (Array.isArray(data)) {
    data.forEach(item => {
      filterPlugin(item)
    })
  } else {
    const findResult = plugins.findIndex(item => data === item)
    if (findResult >= 0) {
      plugins.splice(findResult, 1)
    }
    if (data.children) {
      filterPlugin(data.children)
    }
  }
}

/**
 * 使用 Symbol 处理 name 字段, 保证唯一性
 */
function deepReduceName(target) {
  if (Array.isArray(target)) {
    target.forEach(item => {
      if (typeof item !== 'object') {
        return
      }
      deepReduceName(item)
    })
    return
  }
  if (typeof target === 'object') {
    if (typeof target.name !== 'symbol') {
      target.name = target.name || Utils.getRandomStr()
      target.name = Symbol(target.name)
    }

    if (Array.isArray(target.children)) {
      target.children.forEach(item => {
        if (typeof item !== 'object') {
          return
        }
        deepReduceName(item)
      })
    }
  }
}
