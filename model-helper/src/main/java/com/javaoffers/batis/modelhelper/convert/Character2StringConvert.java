package com.javaoffers.batis.modelhelper.convert;

public class Character2StringConvert extends AbstractConver<Character,String> {
    @Override
    public String convert(Character character) {
        return character.toString();
    }
}
