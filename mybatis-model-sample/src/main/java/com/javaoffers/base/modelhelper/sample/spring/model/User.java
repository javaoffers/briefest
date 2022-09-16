package com.javaoffers.base.modelhelper.sample.spring.model;

import com.javaoffers.batis.modelhelper.anno.BaseModel;
import com.javaoffers.batis.modelhelper.anno.BaseUnique;
import com.javaoffers.batis.modelhelper.anno.ColName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@BaseModel
@Data
@Builder
@AllArgsConstructor
public class User {

    /**
     * You can use id to do count(id). Use this object to get the result of count.
     */
    @ColName("id")
    private Long countId;

    @BaseUnique
    private Long id;

    private String name;

    private String birthday;

    private String createTime;

    private List<UserOrder> orders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public List<UserOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<UserOrder> orders) {
        this.orders = orders;
    }

    public User() {
    }

}
