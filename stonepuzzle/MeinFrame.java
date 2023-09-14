package stonepuzzle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class MeinFrame extends JFrame implements KeyListener {
    //生成一个二维数组，以便随机图片
    int[][]data ={
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,0}
    };
    int[][] win={
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,0}
    };
    int row;          //0号元素行坐标位置-- 定义为成员变量
    int column;       //0号元素列坐标位置-- 定义为成员变量
    int count;       //计数器变量

    public MeinFrame(){
        //窗体对象调用键盘监听方法
        this.addKeyListener(this);
        initFrame();    //窗体初始化
        initData();     //初始化随机数组
        paintView();   //绘制游戏界面
        setVisible(true);   //最后一步: 设置窗口可见
    }

    //初始化数据（打乱二维数组）
    public  void initData(){
        Random r = new Random();
        //遍历二维数组data，获取到每一个元素
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                int randomX=r.nextInt(4);
                int randomY=r.nextInt(4);
                //data[i][j] 和 data[randomX][randomY] 进行交换-- 使用一个中间容器，进行变量交换
                int temp = data[i][j];
                data[i][j]= data[randomX][randomY];
                data[randomX][randomY]= temp;
            }
        }

        //嵌套循环: 查找二维数组 data 中第一个值为0的元素的位置，将行数索引存储在变量 row 中，列数索引存储在变量 column 中
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] == 0) {
                    row = i;               //把i赋值给成员变量row
                    column = j;            //把j赋值给成员变量column
                }
            }
        }

    }

    //窗体初始化
    public void initFrame(){
        //设置JEditorPane
        JEditorPane jep = new JEditorPane();
        jep.setEditable(false);	// 如果不设置则无法和超链接交互
        jep.setContentType("text/html;charset=utf-8");		// 设置编码类型

        // 异步加载页面
        SwingUtilities.invokeLater(() -> {
            try {
                jep.setPage("https://linda-de.github.io/JFrame_Stonepuzzle_Spiel/");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        setSize(514,595);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Stone Puzzle Spiel V1.0");
        setAlwaysOnTop(true);           //窗体保持在最上层
        setLocationRelativeTo(null);    //设置游戏窗体运行时居于屏幕正中
        setLayout(null);                //取消默认布局
        add(jep);
    }

    //绘制游戏界面
    public void paintView(){
        getContentPane().removeAll();             //清空游戏界面里面所有已经有的内容
        //加载胜利图片资源，添加到窗体中
        if(victory()){
            JLabel winLabel = new JLabel(new ImageIcon("C:\\Users\\Student\\Desktop\\javasepro\\Adavance-Codes\\stonepuzzle\\src\\stonepuzzle\\images\\win.png"));
            winLabel.setBounds(124,230,266,88);
            getContentPane().add(winLabel);
        }

        //设置重新开始按钮
        JButton btn = new JButton("Neustart");
        btn.setBounds(350,20,100,20);
        getContentPane().add(btn);
        btn.setFocusable(false);
        btn.addActionListener(e -> {
            count=0;
            initData();
            paintView();
        });

        //统计游戏一共用了多少步
        JLabel scoreLabel = new JLabel("Schritte: "+count);
        scoreLabel.setBounds(50,20,100,20);
        getContentPane().add(scoreLabel);

        //加载图片资源--代码优化-- 1,嵌套循环+ 二维数组嵌套循环
        for (int i = 0; i <4 ; i++) {
            for (int j = 0; j <4 ; j++) {
                JLabel imageLabel = new JLabel(new ImageIcon("C:\\Users\\Student\\Desktop\\javasepro\\Adavance-Codes\\stonepuzzle\\src\\stonepuzzle\\images\\"+data[j][i]+".png"));
                imageLabel.setBounds(50+100*i,90+100*j,100,100);
                getContentPane().add(imageLabel);
            }
        }

        //添加背景大图
        JLabel background = new JLabel(new ImageIcon("C:\\Users\\Student\\Desktop\\javasepro\\Adavance-Codes\\stonepuzzle\\src\\stonepuzzle\\images\\background.png"));
        background.setBounds(26,30,450,484);
        getContentPane().add(background);

        getContentPane().repaint();         //通知面板进行1次刷新
    }

    //判断游戏是否胜利
    public boolean victory(){
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                if(data[i][j]!= win[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode= e.getKeyCode();
        move(keyCode);
        paintView();                          //每一次移动之后，都重新绘制游戏界面
    }

    //此方法用于处理移动业务
    private void move(int keyCode) {

        if(victory()){                   //当游戏结束时，不再允许移动方块了
            return;
        }

        if(keyCode ==39){                       //向左移动-- 空白块和右侧的数据交换
            if(column==3){
                return;
            }
            int temp = data[row][column];
            data[row][column] = data[row][column+1];
            data[row][column+1] = temp;
            column++;
            count++;
        }else if (keyCode ==40){                //向上移动-- 空白块和下侧的数据交换
            if(row==3){
                return;
            }
            int temp = data[row][column];
            data[row][column]=data[row+1][column];
            data[row+1][column] = temp;
            row++;
            count++;
        }else if (keyCode ==37){                //向右移动-- 空白块和左侧的数据交换
            if(column==0){
                return;
            }
            int temp =data[row][column];
            data[row][column]=data[row][column-1];
            data[row][column-1]=temp;
            column--;
            count++;
        }else if (keyCode ==38){                //向下移动-- 空白块和上侧的数据交换
            if(row==0){
                return;
            }
            int temp =data[row][column];
            data[row][column]=data[row-1][column];
            data[row-1][column]=temp;
            row--;
            count++;
        }else if (keyCode==90){              //触发作弊器-- 按键90为y
            data = new int[][]{
                    {1,2,3,4},
                    {5,6,7,8},
                    {9,10,11,12},
                    {13,14,15,0}
            };
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

}
