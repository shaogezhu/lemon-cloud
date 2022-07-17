import {getSlashYMD} from "../../utils/date";

class CouponData {
    startTime
    endTime
    status

    constructor(coupon, status) {
        Object.assign(this, coupon)
        this.startTime = getSlashYMD(coupon.start_time)
        this.endTime = getSlashYMD(coupon.end_time)
        this.status = status
    }
}

export {
    CouponData
}