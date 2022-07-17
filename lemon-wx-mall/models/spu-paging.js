import {Paging} from "../utils/paging";

class SpuPaging{
    static getLatestPaging() {
        return new Paging({
            url:`spu/latest`
        },20)
    }

  static getByCategoryPaging(cid, isRoot) {
    return new Paging({
      url: `spu/by/category/${cid}?is_root=${isRoot}`
    })
  }
}

export {
    SpuPaging
}