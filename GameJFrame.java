package com.itheima;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {
    //创建二位数组，用来管理数据
    int[][] data = new int[4][4];
    //表示空白方块在二维数组中的位置
    int x = 0;
    int y = 0;
    int step = 0;
    Random r = new Random();


    int[][] standardData = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}};

    String path = "D:\\computing\\PuzzleGame\\image\\girl\\girl3\\";

    //子类也表示窗体
    //此类即为游戏的主界面
    public GameJFrame() {
        initJframe();
        initMenu();
        //初始化数据
        initData();
        //初始化图片
        initImage();
        //可视化
        this.setVisible(true);
    }



    private void initData() { //将一个一维数组中的数字0-15打乱顺序
        //然后在按照4个一组的方式添加到二位数组当中
        int[] tempArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        Random r = new Random();
        for (int i = 0; i < tempArr.length; i++) {
            int index = r.nextInt(0, tempArr.length);
            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;
        }

        for (int i = 0; i < tempArr.length; i++) {
            if (tempArr[i] == 0) {
                x = i / 4;
                y = i % 4;}
                data[i / 4][i % 4] = tempArr[i];
            }
        }


    private void initImage() {
        //清空已经存在的所有图片
        this.getContentPane().removeAll();
        //胜利结局
        if (victory()){
           JLabel winJLabel = new JLabel(new ImageIcon("D:\\computing\\PuzzleGame\\image\\win.png"));
           winJLabel.setBounds(203,283,197,73);
           this.getContentPane().add(winJLabel);
        }
        JLabel stepCount = new JLabel("步数"+step);
        stepCount.setBounds(50,20,100,20);
        this.getContentPane().add(stepCount);


        //先加载的图片在上方，后加载的图片在下方。
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                //此为绝对路劲（从盘符开始）
                //相对路径不需从盘符开始，只需要表明项目内的上级文件名即可。
                JLabel jLabel = new JLabel(new ImageIcon(path + data[i][j] + ".jpg"));
                this.add(jLabel);
                jLabel.setBounds(105 * j + 83, 105 * i + 134, 105, 105);
                //给图片添加边框
                jLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
                this.getContentPane().add(jLabel);
            }
        }
        JLabel backGround = new JLabel(new ImageIcon("D:\\computing\\PuzzleGame\\image\\background.png"));
        backGround.setBounds(40, 40, 508, 560);
        this.getContentPane().add(backGround);
        this.getContentPane().repaint();

    }
    //将条目放在成员位置。
    JMenuItem replayItem = new JMenuItem("重新游戏");
    JMenuItem reLoginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");

    JMenuItem AccountItem = new JMenuItem("公众号");
    JMenuItem girl = new JMenuItem("女孩");
    JMenuItem animal = new JMenuItem("动物");
    JMenuItem sports = new JMenuItem("运动");


    private void initMenu() {
        //初始化菜单。
        JMenuBar JMenuBar = new JMenuBar();
        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("关于我们");

        JMenu changePics = new JMenu("更换照片");


        //将每个选择的条目添加到选项之中
        functionJMenu.add(replayItem);
        functionJMenu.add(reLoginItem);
        functionJMenu.add(closeItem);

        functionJMenu.add(changePics);

        changePics.add(girl);
        changePics.add(animal);
        changePics.add(sports);

        aboutJMenu.add(AccountItem);
        //给条目绑定事件
        replayItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);
        AccountItem.addActionListener(this);
        girl.addActionListener(this);
        animal.addActionListener(this);
        sports.addActionListener(this);

        JMenuBar.add(functionJMenu);
        JMenuBar.add(aboutJMenu);

        this.setJMenuBar(JMenuBar);
    }

    private void initJframe() {
        //设置界面的宽高
        this.setSize(603, 680);
        //设置界面的标题
        this.setTitle("川哥的拼图游戏 v1.0");
        //上层显示
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置游戏关闭模式
        //windowConstants可以显示int数据类型代表了什么结果
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //取消默认的居中方式。，只有取消了才会按照xy轴的形式添加组件。
        this.setLayout(null);
        //给整个界面添加键盘监听事件。
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==66){
            this.getContentPane().removeAll();
            JLabel fullPic = new JLabel(new ImageIcon(path+"all.jpg"));
            fullPic.setBounds(83,134,420,420);
            this.getContentPane().add(fullPic);
            JLabel backGround = new JLabel(new ImageIcon("D:\\computing\\PuzzleGame\\image\\background.png"));
            backGround.setBounds(40, 40, 508, 560);
            this.getContentPane().add(backGround);
            this.getContentPane().repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //判断游戏是否顺利
        if(victory()){
            return;
        }
        int code = e.getKeyCode();
        //w 87 s 83 a 65 d 68
        if (code == 65){
            System.out.println("向左移动");
            if(y==0){
                return;
            }
            data[x][y] = data[x][y-1];
            data[x][y-1] = 0;
            y--;
            step++;
            initImage();
        }else if(code == 87){
            System.out.println("向上移动");
            if(x==0){
                return;
            }
            data[x][y] = data[x-1][y];
            data[x-1][y] = 0;
            x--;
            step++;
            initImage();
        }else if(code == 68){
            System.out.println("向右移动");
            if(y==3){
                return;
            }
            data[x][y] = data[x][y+1];
            data[x][y+1] = 0;
            y++;
            step++;
            initImage();
        }else if(code == 83){
            System.out.println("向下移动");
            if(x==3){
                return;
            }
            data[x][y] = data[x+1][y];
            data[x+1][y] = 0;
            x++;
            step++;
            initImage();
            //按v作弊
        }else if (e.getKeyCode()==66){
            initImage();
        }else if (e.getKeyCode()==86){
            data = new int[][]{
                    {1,2,3,4},
                    {5,6,7,8},
                    {9,10,11,12},
                    {13,14,15,0},
            };
            x = 3;
            y = 3;
            initImage();
            }
        //v = 86
        }


        public Boolean victory(){
            for (int i = 0; i < data.length; i++) {
                for (int i1 = 0; i1 < data[i].length; i1++) {
                    if (data[i][i1]!=standardData[i][i1]){
                       return false;
                    }
                }
            }
            return true;
        }
    int index = 0;
    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == replayItem){
            System.out.println("重新游戏");
            step = 0;
            initData();
            initImage();
        }else if(obj == reLoginItem){
            this.setVisible(false);
            new LoginJFrame();
        }else if(obj == closeItem){
            System.out.println("关闭游戏");
            System.exit(0);
        }else if(obj == AccountItem){
            System.out.println("公众号");
            JDialog jDialog = new JDialog();
            JLabel jLabel = new JLabel(new ImageIcon("D:\\computing\\PuzzleGame\\image\\Snipaste_2023-02-10_14-43-54.png"));
            jLabel.setBounds(0,0,258,258);
            jDialog.getContentPane().add(jLabel);
            jDialog.setSize(344,344);
            jDialog.setAlwaysOnTop(true);
            jDialog.setLocationRelativeTo(null);
            jDialog.setModal(true);
            jDialog.setVisible(true);
        }else if (obj==girl){
            index = r.nextInt(1,13);
            path ="D:\\computing\\PuzzleGame\\image\\girl\\girl"+index+"\\";
            step = 0;
            initData();
            initImage();
        }else if (obj==animal){
            index = r.nextInt(1,8);
            path ="D:\\computing\\PuzzleGame\\image\\animal\\animal"+index+"\\";
            step = 0;
            initData();
            initImage();

        }else if (obj==sports){
            index = r.nextInt(1,10);
            path ="D:\\computing\\PuzzleGame\\image\\sport\\sport"+index+"\\";
            step = 0;
            initData();
            initImage();
        }

    }
}

