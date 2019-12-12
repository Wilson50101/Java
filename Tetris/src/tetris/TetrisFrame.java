package tetris;

import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 框架類別
 * @author Leslie Leung
 */
public class TetrisFrame extends JFrame {
	private TetrisPane tp;	//俄羅斯方塊主遊戲場景
	private JLabel mention;	//遊戲的提示訊息
	
	/**
	 * 建構子
	 */
	public TetrisFrame() {
		setSize(550, 600);				//設定視窗大小
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);	//設定視窗位於螢幕中間
		setTitle("Tetris");				//設置標題為"Tetris"
		setResizable(false);			//不允許視窗縮放
		setLayout(new FlowLayout());	//設定版面配置
		
		tp = new TetrisPane();			//新建場景物件
		mention = new JLabel("按A鍵逆時針旋轉，按D順時針旋轉，按方向鍵控制向上、向右和向下的運動，按空格鍵直接下落到底");
		add(mention);					//將提示訊息添加到框架
		add(tp);						//將遊戲主場景面板添加到主框架中
		
		/*註冊鍵盤事件*/
		addKeyListener(tp.getInnerInstanceOfKeyControl());
		tp.addKeyListener(tp.getInnerInstanceOfKeyControl());
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new TetrisFrame();
	}
}
