package com.mawujun.exception.example;


import java.util.ResourceBundle;

import com.mawujun.exception.ExceptionCode;
import com.mawujun.exception.ValidationCode;


public class SystemExceptionExample3 {
    
    public static void main(String[] args) {
        System.out.println(getUserText(ValidationCode.VALUE_TOO_SHORT));
    }
    //这个类是当系统跑出异常后，进行异常拦截和处理的类的例子，比如spring mvc的异常处理
	//这样返回到客户端的错误信息就会比较人性化了
    public static String getUserText(ExceptionCode errorCode) {
        if (errorCode == null) {
            return null;
        }
        String key = errorCode.getClass().getSimpleName() + "__" + errorCode;
        ResourceBundle bundle = ResourceBundle.getBundle("com.mawujun.exception.example.exceptions");
        return bundle.getString(key);
    }

}
