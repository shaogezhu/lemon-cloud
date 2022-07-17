const staticsRouter = {
  route: null,
  name: null,
  title: 'C端数据',
  type: 'folder', // 类型: folder, tab, view
  icon: 'el-icon-s-data',
  filePath: 'view/statics/', // 文件路径
  order: null,
  inNav: true,
  children: [
    {
      title: '订单列表',
      type: 'view',
      name: 'orderList',
      route: '/statics/order/list',
      filePath: 'view/statics/order-list.vue',
      inNav: true,
      icon: '',
    },
    {
      title: 'C端用户列表',
      type: 'view',
      name: 'thirdUserList',
      route: '/statics/third_user/list',
      filePath: 'view/statics/third-user-list.vue',
      inNav: true,
      icon: '',
    },
  ],
}

export default staticsRouter
