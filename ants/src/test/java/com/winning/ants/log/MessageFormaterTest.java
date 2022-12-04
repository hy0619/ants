package com.winning.ants.log;

import org.junit.jupiter.api.Test;

public class MessageFormaterTest {

    @Test
    public void arrayFormatTest(){
        FormattingTuple formattingTuple = MessageFormatter.arrayFormat("记录：{} , 记录：{} ,记录：{} "
                , new Object[]{"1"  , new Exception("异常1") , new Exception("异常2") , new Exception("异常3")});

        System.out.println(formattingTuple.getMessage());
        System.out.println(formattingTuple.getThrowable());
    }
}
