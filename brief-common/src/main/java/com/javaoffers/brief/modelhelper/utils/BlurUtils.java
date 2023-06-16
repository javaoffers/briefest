package com.javaoffers.brief.modelhelper.utils;

import com.javaoffers.brief.modelhelper.anno.derive.Blur;
import com.javaoffers.brief.modelhelper.anno.derive.EmailBlur;
import com.javaoffers.brief.modelhelper.anno.derive.IdCardBlur;
import com.javaoffers.brief.modelhelper.anno.derive.PhoneNumBlur;
import com.javaoffers.brief.modelhelper.anno.derive.StringBlur;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * @author mingJie
 */
public class BlurUtils {

    static final Set<Class> blurCLASS = new HashSet<>();
    static SoftCache<Field, Boolean> isBlurField =  SoftCache.getInstance();
    static {
        blurCLASS.add(EmailBlur.class);
        blurCLASS.add(IdCardBlur.class);
        blurCLASS.add(PhoneNumBlur.class);
        blurCLASS.add(StringBlur.class);
    }

    public static boolean containsBlurAnno(Field field){
        Boolean isBlurField = BlurUtils.isBlurField.get(field);
        if(isBlurField != null){
            return isBlurField;
        }
        Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
        for(Annotation annotation : declaredAnnotations){
            if(isBlurAnno(annotation.annotationType())){
                BlurUtils.isBlurField.put(field, true);
                return true;
            }
        }
        BlurUtils.isBlurField.put(field, false);
        return false;
    }

    public static boolean isBlurAnno(Class clazz){
        return blurCLASS.contains(clazz);
    }

    public static String processBlurAnno(Annotation blurAnno, String orgValue){
        if(StringUtils.isBlank(orgValue)){
            return orgValue;
        }
        if(blurAnno instanceof EmailBlur){
            EmailBlur emailBlur = (EmailBlur) blurAnno;
            Blur blur = emailBlur.blur();
            double percent = emailBlur.percent();
            String blurTag = emailBlur.blurTag();
            String[] email = orgValue.split("@");
            String emailContent = email[0];
            String emilSuffix = email.length == 2 ? "@"+email[1] :
                    email.length == 1 ? "" : orgValue.substring(orgValue.indexOf("@"));
            orgValue = processBlurAnno(blur, percent, emailContent, blurTag) + emilSuffix;
        }
        if(blurAnno instanceof IdCardBlur){
            IdCardBlur idCardBlur = (IdCardBlur) blurAnno;
            Blur blur = idCardBlur.blur();
            String blurTag = idCardBlur.blurTag();
            double percent = idCardBlur.percent();
            orgValue = processBlurAnno(blur, percent, orgValue, blurTag);
        }
        if(blurAnno instanceof PhoneNumBlur){
            PhoneNumBlur phoneNumBlur = (PhoneNumBlur) blurAnno;
            Blur blur = phoneNumBlur.blur();
            String blurTag = phoneNumBlur.blurTag();
            double percent = phoneNumBlur.percent();
            if(orgValue.charAt(0) == '+' && orgValue.length() > 1){
                orgValue = "+"+processBlurAnno(blur, percent, orgValue.substring(1), blurTag);
            }else{
                orgValue = processBlurAnno(blur, percent, orgValue, blurTag);
            }
        }
        if(blurAnno instanceof StringBlur){
            StringBlur stringBlur = (StringBlur) blurAnno;
            Blur blur = stringBlur.blur();
            String blurTag = stringBlur.blurTag();
            double percent = stringBlur.percent();
            orgValue = processBlurAnno(blur, percent, orgValue, blurTag);
        }
        return orgValue;
    }

    private static String processBlurAnno(Blur blur, double percent, String orgValue,String blurTag){
        if(StringUtils.isBlank(orgValue)){
            return orgValue;
        }
        String value = orgValue;
        int len = orgValue.length();
        int blurStrLen = (int)Math.ceil(len * percent);
        StringBuilder appender = new StringBuilder();
        if(blurStrLen >= len){
            for(int i = 0 ; i < len; i++){
                appender.append(blurTag);
            }
            return appender.toString();
        }
        for(int i = 0 ; i< blurStrLen; i++){
            appender.append( blurTag );
        }
        int lefLen = 0;
        switch (blur){
            case POST_BLUR:
                lefLen = len - blurStrLen;
                value = value.substring(0, lefLen) + appender.toString();
                break;
            case FRONT_BLUR:
                value = appender.toString() + value.substring(blurStrLen, len );
                break;
            case MIDDLE_BLUR:
                lefLen = (len - blurStrLen) / 2;
                value = value.substring(0, lefLen) + appender.toString() + value.substring(blurStrLen + lefLen, len);
                break;

        }
        return value;
    }

}
