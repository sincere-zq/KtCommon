package com.zhixing.entity.bean;

import java.io.Serializable;

/**
 * create by 曾强 on 2020/3/12
 */
public class VisitorInfoBean implements Serializable {
    private String visitorName;
    private String orderDate;
    private String orderPerson;
    private String visitorMobile;

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderPerson() {
        return orderPerson;
    }

    public void setOrderPerson(String orderPerson) {
        this.orderPerson = orderPerson;
    }

    public String getVisitorMobile() {
        return visitorMobile;
    }

    public void setVisitorMobile(String visitorMobile) {
        this.visitorMobile = visitorMobile;
    }
}
