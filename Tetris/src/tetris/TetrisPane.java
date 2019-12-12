package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 俄羅斯方塊遊戲場景的類別
 * @author Leslie Leung
 */
public class TetrisPane extends JPanel {
	public static final int ROWS = 20;			//整個遊戲場景有幾行
	public static final int COLUMNS = 16;		//整個遊戲場景有幾列
	
	/*表示7種不同方塊*/
	public static final int I_SHAPED = 0;
	public static final int S_SHAPED = 1;
	public static final int T_SHAPED = 2;
	public static final int Z_SHAPED = 3;
	public static final int L_SHAPED = 4;
	public static final int O_SHAPED = 5;
	public static final int J_SHAPED = 6;
	
	public static final int KIND = 7;			//表示四格方塊有7個種類
	public static final int INIT_SPEED = 1000;	//表示初始的下落速度
	
	private static int randomNum = 0;			//表示已生成的俄羅斯方塊數目
	
	private Random random;
	private Tetromino currentTetromino;			//表示當前的四個方塊
	private Cell[][] wall;						//表示牆,null表示方塊內沒有物件
	private Timer autoDrop;						//降落的計時器
	private KeyControl keyListener;				//鍵盤事件監聽器
	
	/**
	 * 建構子
	 */
	public TetrisPane() {
		setPreferredSize(new Dimension(COLUMNS * Cell.CELL_SIZE, ROWS * Cell.CELL_SIZE));
		
		random = new Random();
		wall = new Cell[ROWS][COLUMNS];
		autoDrop = new Timer();
		keyListener = new KeyControl();
		
		randomOne();
		
		autoDrop.schedule(new DropExecution(), (long)interval(), (long)interval());
	}
	
	/**
	 * 隨機生成一個四格方塊
	 */
	public void randomOne() {
		Tetromino tetromino = null;
		
		/*隨機生成7種四格方塊的其中一種*/
		switch(random.nextInt(KIND)) {
			case I_SHAPED: 
				tetromino = new IShaped();
				break;
			case S_SHAPED: 
				tetromino = new SShaped();
			   	break;
			case T_SHAPED: 
				tetromino = new TShaped();
			    break;
			case Z_SHAPED: 
				tetromino = new ZShaped();
			    break;
			case L_SHAPED: 
				tetromino = new LShaped();
			    break;
			case O_SHAPED: 
				tetromino = new OShaped();
			    break;
			case J_SHAPED: 
				tetromino = new JShaped();
			    break;
		}
		currentTetromino = tetromino;	//目前的四格方塊=剛剛隨機生成的四格方塊
		randomNum ++;					//已生成的四格方塊數+1
	}
	
	/**
	 * 判斷玩家是否輸了
	 * @return true:輸了 	false:還沒輸
	 */
	public boolean isGameOver() {
		int x, y;	//取得當前俄羅斯方塊格子的x,y座標
		for(int i = 0; i < getCurrentCells().length; i ++) {
			x = getCurrentCells()[i].getX();
			y = getCurrentCells()[i].getY();
			
			if(isContain(x, y)) {//看其剛生成的位置是否存在方塊物件.有的話代表俄羅斯方塊無法下降=滿了=輸了
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 每生成一個俄羅斯方塊,通過改變TimerTask的時間間隔來加快下落速度
	 * @return 時間間隔
	 */
	public double interval() {
		return INIT_SPEED * Math.pow((double)39 / 38, 0 - randomNum);
	}
	
	/**
	 *	返回KeyControl class的instance
	 * @return KeyControl class instance
	 */
	public KeyControl getInnerInstanceOfKeyControl() {
		return keyListener;
	}
	
	/**
	 * 	內部巢狀類別 用來實作方塊自動下降
	 * @author Leslie Leung
	 */
	private class DropExecution extends TimerTask {	
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			if(isGameOver()) {					//如果輸了
				JOptionPane.showMessageDialog(null, "你輸了");
				autoDrop.cancel();
				removeKeyListener(keyListener);
				return;
			}
			
			if(!isReachBottomEdge()) {
				currentTetromino.softDrop();
			} else {
				landIntoWall();					//把俄羅斯方塊添加到牆上
				removeRows();					//如果滿行了,先刪除行
				randomOne();					//再馬上新建一個俄羅斯方塊
				
				autoDrop.cancel();
				autoDrop = new Timer();
				autoDrop.schedule(new DropExecution(), (long)interval(), (long)interval());
			}
			
			repaint();
		}	
	}
	
	/**
	 * 	將俄羅斯方塊添加到牆上
	 */
	public void landIntoWall() {
		int x, y;	//定義俄羅斯方塊不能移動後的x,y座標
		
		for(int i = 0; i < getCurrentCells().length; i ++) {
			x = getCurrentCells()[i].getX();
			y = getCurrentCells()[i].getY();
			
			wall[y][x] = getCurrentCells()[i];	//添加到牆上
		}
	}
	
	/**
	 * 	內部巢狀類別,用來實現鍵盤事件控制
	 * @author Leslie Leung
	 */
	private class KeyControl extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:	//往左移動
					
					if(!isReachLeftEdge()) {//當方塊還沒碰到左邊界的時候,往左移動
						currentTetromino.moveLeft();
						repaint();
					}					
					break;
					
				case KeyEvent.VK_RIGHT:	//往右移動
					
					if(!isReachRightEdge()) {//當方塊還沒碰到右邊界的時候,往右移動
						currentTetromino.moveRight();
						repaint();
					}
					break;
				
				case KeyEvent.VK_DOWN:	//往下移動
					
					if(!isReachBottomEdge()) {//當方塊還沒碰到下邊界的時候,往下移動
						currentTetromino.softDrop();
						repaint();
					}
					
					break;
					
				case KeyEvent.VK_SPACE:	//直接下落到底
					
					hardDrop();			//硬下落
					landIntoWall();		//添加到牆上
					removeRows();		//如果滿行,刪除行
					
					//再重新生成新的方塊
					randomOne();
					autoDrop.cancel();
					autoDrop = new Timer();
					autoDrop.schedule(new DropExecution(), (long)interval(), (long)interval());
					
					repaint();
					break;
					
				case KeyEvent.VK_D:		//順時針轉
					//當方塊旋轉後不會越界且不為O型時,可以旋轉
					if(!clockwiseRotateIsOutOfBounds() && !(currentTetromino instanceof OShaped)) {
						currentTetromino.clockwiseRotate(getAxis(), getRotateCells());
						repaint();
					}
					break;
					
				case KeyEvent.VK_A:		//逆時針轉
					//當方塊旋轉後不會越界且不為O型時,可以旋轉
					if(!counterclockwiseRotateIsOutOfBounds() && !(currentTetromino instanceof OShaped)) {
						currentTetromino.counterclockwiseRotate(getAxis(), getRotateCells());
						repaint();
					}
					break;
			}
		}
	}
	
	/**
	 * 	內部巢狀類別,I型的方塊,繼承自Tetromino類別
	 * @author Leslie Leung
	 */
	private class IShaped extends Tetromino {
		/**
		 * 	建構子
		 */
		public IShaped() {
			cells = new Cell[4];
			
			cells[1] = new Cell(3, 0, Color.cyan);
			cells[0] = new Cell(4, 0, Color.cyan);
			cells[2] = new Cell(5, 0, Color.cyan);
			cells[3] = new Cell(6, 0, Color.cyan);
			
			/*設置旋轉軸和要旋轉的格子*/
			setAxis();
			setRotateCells();
			
			repaint();
		}
	}
	
	/**
	 * 	內部巢狀類別,S型的方塊,繼承自Tetromino類別
	 * @author Leslie Leung
	 */
	private class SShaped extends Tetromino {
		/**
		 * 	建構子
		 */
		public SShaped() {
			cells = new Cell[4];
			
			cells[0] = new Cell(4, 0, Color.blue);
			cells[1] = new Cell(5, 0, Color.blue);
			cells[2] = new Cell(3, 1, Color.blue);
			cells[3] = new Cell(4, 1, Color.blue);
			
			/*設置旋轉軸和要旋轉的格子*/
			setAxis();
			setRotateCells();
			
			repaint();
		}
	}
	
	/**
	 * 	內部巢狀類別,T型的方塊,繼承自Tetromino類別
	 * @author Leslie Leung
	 */
	private class TShaped extends Tetromino {
		/**
		 * 	建構子
		 */
		public TShaped() {
			cells = new Cell[4];
			
			cells[1] = new Cell(3, 0, Color.green);
			cells[0] = new Cell(4, 0, Color.green);
			cells[2] = new Cell(5, 0, Color.green);
			cells[3] = new Cell(4, 1, Color.green);
			
			/*設置旋轉軸和要旋轉的格子*/
			setAxis();
			setRotateCells();
			
			repaint();
		}
	}
	
	/**
	 * 	內部巢狀類別,Z型的方塊,繼承自Tetromino類別
	 * @author Leslie Leung
	 */
	private class ZShaped extends Tetromino {
		/**
		 * 	建構子
		 */
		public ZShaped() {
			cells = new Cell[4];
			
			cells[1] = new Cell(3, 0, Color.orange);
			cells[2] = new Cell(4, 0, Color.orange);
			cells[0] = new Cell(4, 1, Color.orange);
			cells[3] = new Cell(5, 1, Color.orange);
			
			/*設置旋轉軸和要旋轉的格子*/
			setAxis();
			setRotateCells();
			
			repaint();
		}
	}
	
	/**
	 * 	內部巢狀類別,L型的方塊,繼承自Tetromino類別
	 * @author Leslie Leung
	 */
	private class LShaped extends Tetromino {
		/**
		 * 	建構子
		 */
		public LShaped() {
			cells = new Cell[4];
			
			cells[1] = new Cell(3, 0, Color.pink);
			cells[0] = new Cell(4, 0, Color.pink);
			cells[2] = new Cell(5, 0, Color.pink);
			cells[3] = new Cell(3, 1, Color.pink);
			
			/*設置旋轉軸和要旋轉的格子*/
			setAxis();
			setRotateCells();
			
			repaint();
		}
	}
	
	/**
	 * 	內部巢狀類別,O型的方塊,繼承自Tetromino類別
	 * @author Leslie Leung
	 */
	private class OShaped extends Tetromino {
		/**
		 * 	建構子
		 */
		public OShaped() {
			cells = new Cell[4];
			
			cells[0] = new Cell(4, 0, Color.red);
			cells[1] = new Cell(5, 0, Color.red);
			cells[2] = new Cell(4, 1, Color.red);
			cells[3] = new Cell(5, 1, Color.red);
			
			/*設置旋轉軸和要旋轉的格子*/
			setAxis();
			setRotateCells();
			
			repaint();
		}
	}
	
	/**
	 * 	內部巢狀類別,J型的方塊,繼承自Tetromino類別
	 * @author Leslie Leung
	 */
	private class JShaped extends Tetromino {
		/**
		 * 	建構子
		 */
		public JShaped() {
			cells = new Cell[4];
			
			cells[1] = new Cell(3, 0, Color.yellow);
			cells[0] = new Cell(4, 0, Color.yellow);
			cells[2] = new Cell(5, 0, Color.yellow);
			cells[3] = new Cell(5, 1, Color.yellow);
			
			/*設置旋轉軸和要旋轉的格子*/
			setAxis();
			setRotateCells();
			
			repaint();
		}
	}
	
	/**
	 * 	刪除若干行
	 */
	public void removeRows() {
		for(int i = 0; i < getCurrentCells().length; i ++) {
			removeRow(getCurrentCells()[i].getY());
		}
	}
	
	/**
	 * 	獲取旋轉軸
	 * @return 旋轉軸
	 */
	public Cell getAxis() {
		return currentTetromino.getAxis();
	}
	
	/**
	 * 	獲取需要旋轉的格子
	 * @return 需要旋轉的格子
	 */
	public Cell[] getRotateCells() {
		return currentTetromino.getRotateCells();
	}
	
	/**
	 * 	獲取當前俄羅斯方塊的所有格子
	 * @return 當前俄羅斯方塊的所有格子
	 */
	public Cell[] getCurrentCells() {
		return currentTetromino.getCells();
	}
	
	/**
	 * 	判斷俄羅斯方塊是否到達底部
	 * @return true:到達  false:沒到達
	 */
	public boolean isReachBottomEdge() {
		int oldY, newY, oldX;			//定義格子舊/新的Y(縱坐標)和舊的X(橫)坐標 	因為橫坐標到底部就不會變了
		
		for(int i = 0; i < getCurrentCells().length; i ++) {
			oldY = getCurrentCells()[i].getY();
			newY = oldY + 1;
			oldX = getCurrentCells()[i].getX();
			
			if(oldY == ROWS - 1) {		//到達面板底部
				return true;
			}
			
			if(isContain(oldX, newY)) {	//下一個位置有方塊
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 	判斷俄羅斯方塊是否到達左邊界(包括是否超出面板左邊界和下一個位置是否出現與其他方塊碰撞)
	 * @return true:到達  false:沒到達
	 */
	public boolean isReachLeftEdge() {
		int oldX, newX, oldY;		//定義格子舊/新的橫坐標,舊的縱坐標
		
		for(int i = 0; i < getCurrentCells().length; i ++) {
			oldX = getCurrentCells()[i].getX();
			newX = oldX - 1;
			oldY = getCurrentCells()[i].getY();
			
			if(oldX == 0 || isContain(newX, oldY)) {//到達左邊界
				return true;
			}
			
			if(isContain(newX, oldY)) {//左邊有方塊
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 	判斷俄羅斯方塊是否到達右邊界(包括是否超出面板右邊界和下一個位置是否出現與其他方塊碰撞)
	 * @return true:到達  false:沒到達
	 */
	public boolean isReachRightEdge() {
		int oldX, newX, oldY;		//定義格子舊/新的橫坐標,舊的縱坐標
		
		for(int i = 0; i < getCurrentCells().length; i ++) {
			oldX = getCurrentCells()[i].getX();
			newX = oldX + 1;
			oldY = getCurrentCells()[i].getY();
			
			if(oldX == COLUMNS - 1 || isContain(newX, oldY)) {//到達右邊界
				return true;
			}
			
			if(isContain(newX, oldY)) {//右邊有方塊
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 	判斷方塊順時針旋轉後是否會超出邊界(包括是否超出面板邊界和下一個位置是否出現與其他方塊碰撞)
	 * @return true:超出邊界  false:沒超出
	 */
	public boolean clockwiseRotateIsOutOfBounds() {
		int oldX;	//rotateCell的橫坐標
		int oldY;	//rotateCell的縱坐標
		int newX;	//rotateCell旋轉後的橫坐標
		int newY;	//rotateCell旋轉後的縱坐標
		
		for(int i = 0; i < 3; i ++) {
			oldX = getRotateCells()[i].getX();
			oldY = getRotateCells()[i].getY();
			
			newX = getAxis().getX() - oldY + getAxis().getY();	//新橫坐標計算算法
			newY = getAxis().getY() + oldX - getAxis().getX();	//新縱坐標計算算法
			
			if(newX < 0 || newY < 0 || newX > COLUMNS - 1 || newY > ROWS - 1) {//如果越界,return true
				return true;
			}
			
			if(isContain(newX, newY)) {//如果越界,return true
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 	判斷方塊逆時針旋轉後是否會超出邊界(包括是否超出面板邊界和下一個位置是否出現與其他方塊碰撞)
	 * @return true:超出邊界  false:沒超出
	 */
	public boolean counterclockwiseRotateIsOutOfBounds() {
		int oldX;	//rotateCell的橫坐標
		int oldY;	//rotateCell的縱坐標
		int newX;	//rotateCell旋轉後的橫坐標
		int newY;	//rotateCell旋轉後的縱坐標
		
		for(int i = 0; i < 3; i ++) {
			oldX = getRotateCells()[i].getX();
			oldY = getRotateCells()[i].getY();
			
			newX = getAxis().getX() - getAxis().getY() + oldY;	//新橫坐標計算算法
			newY = getAxis().getY() + getAxis().getX() - oldX;	//新縱坐標計算算法
			
			if(newX < 0 || newY < 0 || newX > COLUMNS - 1 || newY > ROWS - 1) {//如果越界,return true
				return true;
			}
			
			if(isContain(newX, newY)) {//如果越界,return true
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 	判斷某個格子是否有方塊存在
	 * @param x 橫坐標
	 * @param y 縱坐標
	 * @return true:存在對象 	false:不存在對像
	 */
	public boolean isContain(int x, int y) {
		if(wall[y][x] == null) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 	實現俄羅斯方塊的硬下落
	 */
	public void hardDrop() {
		while(!isReachBottomEdge()) {
			currentTetromino.softDrop();
		}
	}
	
	/**
	 * 	消除單行
	 * @param i行的index
	 */
	public void removeRow(int i) {
		int oldY, newY;	
		//如果其中一個方塊沒有填滿,return
		for(int j = 0; j < COLUMNS; j ++) {
			if(wall[i][j] == null) {
				return;
			}
		}
		
		//消除行並把該行上面的方塊往下移
		for(int k = i; k >= 1; k --){
			System.arraycopy(wall[k - 1], 0, wall[k], 0, COLUMNS);
			
			for(int m = 0; m < COLUMNS; m ++) {
				if(wall[k][m] != null) {//對於不是空的對象,要重設其縱座標
					oldY = wall[k][m].getY();
					newY = oldY + 1;
					wall[k][m].setY(newY);				
				}
			}
			
		}
		Arrays.fill(wall[0], null);
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getBounds().width, getBounds().height);
		
		/*畫牆*/
		for(int i = 0; i < ROWS; i ++) {
			for(int j = 0; j < COLUMNS; j ++) {
				if(wall[i][j] == null) {//當某點方塊為空時
					g.setColor(Color.WHITE);
					g.fillRect(j * Cell.CELL_SIZE + 1, i * Cell.CELL_SIZE + 1, Cell.CELL_SIZE - 2, Cell.CELL_SIZE - 2);
				} else {//當方塊不為空時
					wall[i][j].paintCell(g);
				}
			}
		}
		
		/*畫當前俄羅斯方塊*/
		for(int i = 0; i < getCurrentCells().length; i ++) {
			getCurrentCells()[i].paintCell(g);
		}
		
	}
}
