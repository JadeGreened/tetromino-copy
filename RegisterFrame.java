package com.itheima;

import javax.swing.*;

public class RegisterFrame extends JFrame {
    public RegisterFrame(){
        this.setSize(488,500);
        //设置界面的标题
        this.setTitle("注册");
        //上层显示
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置游戏关闭模式
        //windowConstants可以显示int数据类型代表了什么结果
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //可视化
        this.setVisible(true);
    }
}
