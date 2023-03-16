package com.javaoffers.base.modelhelper.sample.spring.blur;

import com.javaoffers.base.modelhelper.sample.utils.LOGUtils;
import com.javaoffers.batis.modelhelper.anno.derive.Blur;
import com.javaoffers.batis.modelhelper.anno.derive.EmailBlur;
import com.javaoffers.batis.modelhelper.anno.derive.IdCardBlur;
import com.javaoffers.batis.modelhelper.anno.derive.PhoneNumBlur;
import com.javaoffers.batis.modelhelper.anno.derive.StringBlur;
import com.javaoffers.batis.modelhelper.core.ConvertRegisterSelectorDelegate;
import lombok.Data;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * @author mingJie
 */
public class BlurSample {

    ConvertRegisterSelectorDelegate delegate =  ConvertRegisterSelectorDelegate.convert;

    @Test
    public void testBlurSample() throws Exception{
        BlurModel blurModel = new BlurModel();
        Field email = blurModel.getClass().getDeclaredField("email");
        Field email2 = blurModel.getClass().getDeclaredField("email2");
        Field phoneNum = blurModel.getClass().getDeclaredField("phoneNum");
        Field phoneNum2 = blurModel.getClass().getDeclaredField("phoneNum2");
        Field idCard = blurModel.getClass().getDeclaredField("idCard");
        Field strName = blurModel.getClass().getDeclaredField("strName");
        Field empty = blurModel.getClass().getDeclaredField("empty");

        String s = delegate.converterObject(String.class, "12345678@outlook.com", email);
        LOGUtils.printLog(s); //12***678@outlook.com
        s = delegate.converterObject(String.class, "123456789@outlook.com", email);
        LOGUtils.printLog(s);//123***789@outlook.com

        s = delegate.converterObject(String.class, "12345678@outlook.com", email2);
        LOGUtils.printLog(s); //12345***@outlook.com
        s = delegate.converterObject(String.class, "123456789@outlook.com", email2);
        LOGUtils.printLog(s);//123456***@outlook.com

        s = delegate.converterObject(String.class, "12345678", email); //没有@后缀的
        LOGUtils.printLog(s); //12***678
        s = delegate.converterObject(String.class, "123456789", email);
        LOGUtils.printLog(s);//123***789

        s = delegate.converterObject(String.class, "12345678", phoneNum);
        LOGUtils.printLog(s); //12***678
        s = delegate.converterObject(String.class, "123456789", phoneNum);
        LOGUtils.printLog(s);//123***789

        s = delegate.converterObject(String.class, "12345678", phoneNum2);
        LOGUtils.printLog(s); //12345***
        s = delegate.converterObject(String.class, "123456789", phoneNum2);
        LOGUtils.printLog(s);//123456***
        s = delegate.converterObject(String.class, "+86123456789", phoneNum2);
        LOGUtils.printLog(s);//+8612345****


        s = delegate.converterObject(String.class, "123123123456789", idCard);
        LOGUtils.printLog(s);// ?????3123456789

        s = delegate.converterObject(String.class, "12", idCard);
        LOGUtils.printLog(s);// ?2

        s = delegate.converterObject(String.class, "12123123123465", strName);
        LOGUtils.printLog(s);// 121*******3465

        s = delegate.converterObject(String.class, "123", strName);
        LOGUtils.printLog(s);// **3
        s = delegate.converterObject(String.class, "1234", strName);
        LOGUtils.printLog(s);// 1**4

        s = delegate.converterObject(String.class, "1234", empty);
        LOGUtils.printLog(s);// 1234

    }

    @Data
    static class BlurModel{

        @EmailBlur
        private String email;

        @EmailBlur(blur = Blur.POST_BLUR)
        private String email2;

        @PhoneNumBlur
        private String phoneNum;

        @PhoneNumBlur(blur = Blur.POST_BLUR)
        private String phoneNum2;

        @IdCardBlur(blur = Blur.FRONT_BLUR, blurTag = "?")
        private String idCard;

        @StringBlur(percent = 0.5)
        private String strName;

        private String empty;
    }
}
