package com.itheima;

import java.util.Random;

public class CodeUtil {
    public static String getCode() {
        String s = new String();
        String str = new String();
        Random r = new Random();
        for (int i = 0; i < 5; i++) {
            int temp = r.nextInt(58) + 65;  // 随机生成 65—122 的数(A—z)

            if ((temp >= 'A' && temp <= 'Z') || (temp >= 'a' && temp <= 'z')) {   // 判断随机数是不是(A-Z)||(a-z)

                str += (char) temp;         // 将当前随机数强制转化为字符类型并和字符串相加

            } else {            // 不满足条件，将当前的i再执行一次
                i--;
            }
        }
        int num = r.nextInt('0', '9');
        int TEMP = r.nextInt(0, str.length() - 1);
        char[] array = new char[str.length()];
        for (int i = 0; i < array.length; i++) {
            array[i] = str.charAt(i);
        }
        array[TEMP] = (char) num;
        String s11 = new String();
        for (int i = 0; i < array.length; i++) {
            s11 = s11 + array[i];
        }
        System.out.println("您的验证码为" + s11);
        return s11;     // 返回长度为5的随机字符串，字符串由随机的5个大小写字母组成
    }
}

