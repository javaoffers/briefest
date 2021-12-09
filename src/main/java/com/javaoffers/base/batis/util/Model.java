package com.javaoffers.base.batis.util;

/**
 * @Description:
 * @Auther: create by cmj on 2021/12/9 11:37
 */
public class Model<E> {
    private E model;
    private boolean status = false;//

    public E getModel() {
        return model;
    }

    public E getModelAndSetStatusIsTrue() {
        this.status = true;
        return model;
    }

    public void setModel(E model) {
        this.model = model;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Model(E model) {
        super();
        this.model = model;
    }

    public static <E> Model getModel(E model) {
        return new Model<E>(model);
    }
}
