package com.javaoffers.base.modelhelper.sample.model;

import com.javaoffers.brief.modelhelper.anno.BaseModel;
import com.javaoffers.brief.modelhelper.anno.BaseUnique;
import com.javaoffers.brief.modelhelper.core.KeyGenerate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @Description:
 * @Auther: create by cmj on 2021/12/13 13:03
 */
@BaseModel
@Data
@Builder
@AllArgsConstructor
public class UserOrder {

    @BaseUnique("id")
    private int orderId;
    /**
     * 测试自动生成唯一key
     */
    @BaseUnique(keyGenerate = KeyGenerate.SNOWFLAKE)
    private Long orderMark;

    private String orderName;
    private String orderMoney;

    private String userId;

    private String createTime;

    private int isDel;

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public UserOrder() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
