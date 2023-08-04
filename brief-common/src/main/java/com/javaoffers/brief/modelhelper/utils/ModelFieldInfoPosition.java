package com.javaoffers.brief.modelhelper.utils;

/**
 * 待有标记位
 * @author mingJie
 */
public class ModelFieldInfoPosition {
    //从1开始, 如果 -1 表示此字段是一个Model类型
    private int position;
    private ModelFieldInfo modelFieldInfo;

    public ModelFieldInfoPosition(int position, ModelFieldInfo modelFieldInfo) {
        this.position = position;
        this.modelFieldInfo = modelFieldInfo;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ModelFieldInfo getModelFieldInfo() {
        return modelFieldInfo;
    }

    public void setModelFieldInfo(ModelFieldInfo modelFieldInfo) {
        this.modelFieldInfo = modelFieldInfo;
    }

    public boolean isModelClass(){
        return this.modelFieldInfo.isModelClass();
    }
}
