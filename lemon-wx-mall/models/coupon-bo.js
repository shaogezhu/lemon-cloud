import {CouponType} from "../core/enum";
import {accMultiply, accSubtract} from "../utils/number";

class CouponBO {
    constructor(coupon) {
        this.type = coupon.type
        this.fullMoney = coupon.full_money
        this.rate = coupon.rate
        this.minus = coupon.minus
        this.id = coupon.id
        this.startTime = coupon.start_time
        this.endTime = coupon.end_time
        this.wholeStore = coupon.whole_store
        this.title = coupon.title
        this.satisfaction = false

        this.categoryIds = coupon.categories.map(category => {
            return category.id
        })
    }

    meetCondition(order) {
        let categoryTotalPrice;

        if (this.wholeStore) {
            // 全场券无视适用分类
            categoryTotalPrice = order.getTotalPrice()
        } else {
            categoryTotalPrice = order.getTotalPriceByCategoryIdList(this.categoryIds)
        }

        let satisfaction = false

        switch (this.type) {
            case CouponType.FULL_MINUS:
            case CouponType.FULL_OFF:
                satisfaction = this._fullTypeCouponIsOK(categoryTotalPrice)
                break
            case CouponType.NO_THRESHOLD_MINUS:
                satisfaction = true
                break
            default:
                break
        }
        this.satisfaction = satisfaction
        // return satisfaction
    }

    static getFinalPrice(orderPrice, couponObj) {
        if (couponObj.satisfaction === false) {
            throw new Error('优惠券不满足使用条件')
        }

        let finalPrice;

        switch (couponObj.type) {
            case CouponType.FULL_MINUS:
                return {
                    finalPrice: accSubtract(orderPrice, couponObj.minus),
                    discountMoney: couponObj.minus
                }
            case CouponType.FULL_OFF:
                const actualPrice = accMultiply(orderPrice, couponObj.rate)
                finalPrice = CouponBO.roundMoney(actualPrice)

                const discountMoney = accSubtract(orderPrice, finalPrice)

                return {
                    finalPrice,
                    discountMoney
                }

            case CouponType.NO_THRESHOLD_MINUS:
                finalPrice = accSubtract(orderPrice, couponObj.minus)
                finalPrice = finalPrice < 0 ? 0 : finalPrice
                return {
                    finalPrice,
                    discountMoney: couponObj.minus
                }
        }
    }

    static roundMoney(money) {
        const final = Math.ceil(money * 100) / 100
        return final
    }

    _fullTypeCouponIsOK(categoryTotalPrice) {
        if (categoryTotalPrice >= this.fullMoney) {
            return true
        }
        return false
    }
}

export {
    CouponBO
}