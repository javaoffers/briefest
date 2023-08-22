package com.javaoffers.base.modelhelper.sample.json;

import com.javaoffers.brief.modelhelper.anno.derive.JsonColumn;
import com.javaoffers.brief.modelhelper.utils.GsonUtils;
import org.junit.Test;

/**
 * 附属信息: {"nickName":"mingJie","age":30}. 用于测试json
 * @author mingJie
 */
public class ExtraInfo implements JsonColumn {
    private String nickName;
    private Integer age;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

//    @Test
//    public void t(){
//        ExtraInfo extraInfo = new ExtraInfo();
//        extraInfo.setAge(30);
//        extraInfo.setNickName("mingJie");
//        String s = GsonUtils.gson.toJson(extraInfo);
//        System.out.println(s);
//    }
}
