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
 * �Xù������C�����������O
 * @author Leslie Leung
 */
public class TetrisPane extends JPanel {
	public static final int ROWS = 20;			//��ӹC���������X��
	public static final int COLUMNS = 16;		//��ӹC���������X�C
	
	/*���7�ؤ��P���*/
	public static final int I_SHAPED = 0;
	public static final int S_SHAPED = 1;
	public static final int T_SHAPED = 2;
	public static final int Z_SHAPED = 3;
	public static final int L_SHAPED = 4;
	public static final int O_SHAPED = 5;
	public static final int J_SHAPED = 6;
	
	public static final int KIND = 7;			//��ܥ|������7�Ӻ���
	public static final int INIT_SPEED = 1000;	//��ܪ�l���U���t��
	
	private static int randomNum = 0;			//��ܤw�ͦ����Xù������ƥ�
	
	private Random random;
	private Tetromino currentTetromino;			//��ܷ�e���|�Ӥ��
	private Cell[][] wall;						//�����,null��ܤ�����S������
	private Timer autoDrop;						//�������p�ɾ�
	private KeyControl keyListener;				//��L�ƥ��ť��
	
	/**
	 * �غc�l
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
	 * �H���ͦ��@�ӥ|����
	 */
	public void randomOne() {
		Tetromino tetromino = null;
		
		/*�H���ͦ�7�إ|�������䤤�@��*/
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
		currentTetromino = tetromino;	//�ثe���|����=����H���ͦ����|����
		randomNum ++;					//�w�ͦ����|������+1
	}
	
	/**
	 * �P�_���a�O�_��F
	 * @return true:��F 	false:�٨S��
	 */
	public boolean isGameOver() {
		int x, y;	//���o��e�Xù�������l��x,y�y��
		for(int i = 0; i < getCurrentCells().length; i ++) {
			x = getCurrentCells()[i].getX();
			y = getCurrentCells()[i].getY();
			
			if(isContain(x, y)) {//�ݨ��ͦ�����m�O�_�s�b�������.�����ܥN��Xù������L�k�U��=���F=��F
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * �C�ͦ��@�ӫXù�����,�q�L����TimerTask���ɶ����j�ӥ[�֤U���t��
	 * @return �ɶ����j
	 */
	public double interval() {
		return INIT_SPEED * Math.pow((double)39 / 38, 0 - randomNum);
	}
	
	/**
	 *	��^KeyControl class��instance
	 * @return KeyControl class instance
	 */
	public KeyControl getInnerInstanceOfKeyControl() {
		return keyListener;
	}
	
	/**
	 * 	�����_�����O �Ψӹ�@����۰ʤU��
	 * @author Leslie Leung
	 */
	private class DropExecution extends TimerTask {	
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			if(isGameOver()) {					//�p�G��F
				JOptionPane.showMessageDialog(null, "�A��F");
				autoDrop.cancel();
				removeKeyListener(keyListener);
				return;
			}
			
			if(!isReachBottomEdge()) {
				currentTetromino.softDrop();
			} else {
				landIntoWall();					//��Xù������K�[����W
				removeRows();					//�p�G����F,���R����
				randomOne();					//�A���W�s�ؤ@�ӫXù�����
				
				autoDrop.cancel();
				autoDrop = new Timer();
				autoDrop.schedule(new DropExecution(), (long)interval(), (long)interval());
			}
			
			repaint();
		}	
	}
	
	/**
	 * 	�N�Xù������K�[����W
	 */
	public void landIntoWall() {
		int x, y;	//�w�q�Xù��������ಾ�ʫ᪺x,y�y��
		
		for(int i = 0; i < getCurrentCells().length; i ++) {
			x = getCurrentCells()[i].getX();
			y = getCurrentCells()[i].getY();
			
			wall[y][x] = getCurrentCells()[i];	//�K�[����W
		}
	}
	
	/**
	 * 	�����_�����O,�Ψӹ�{��L�ƥ󱱨�
	 * @author Leslie Leung
	 */
	private class KeyControl extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:	//��������
					
					if(!isReachLeftEdge()) {//�����٨S�I�쥪��ɪ��ɭ�,��������
						currentTetromino.moveLeft();
						repaint();
					}					
					break;
					
				case KeyEvent.VK_RIGHT:	//���k����
					
					if(!isReachRightEdge()) {//�����٨S�I��k��ɪ��ɭ�,���k����
						currentTetromino.moveRight();
						repaint();
					}
					break;
				
				case KeyEvent.VK_DOWN:	//���U����
					
					if(!isReachBottomEdge()) {//�����٨S�I��U��ɪ��ɭ�,���U����
						currentTetromino.softDrop();
						repaint();
					}
					
					break;
					
				case KeyEvent.VK_SPACE:	//�����U���쩳
					
					hardDrop();			//�w�U��
					landIntoWall();		//�K�[����W
					removeRows();		//�p�G����,�R����
					
					//�A���s�ͦ��s�����
					randomOne();
					autoDrop.cancel();
					autoDrop = new Timer();
					autoDrop.schedule(new DropExecution(), (long)interval(), (long)interval());
					
					repaint();
					break;
					
				case KeyEvent.VK_D:		//���ɰw��
					//��������ᤣ�|�V�ɥB����O����,�i�H����
					if(!clockwiseRotateIsOutOfBounds() && !(currentTetromino instanceof OShaped)) {
						currentTetromino.clockwiseRotate(getAxis(), getRotateCells());
						repaint();
					}
					break;
					
				case KeyEvent.VK_A:		//�f�ɰw��
					//��������ᤣ�|�V�ɥB����O����,�i�H����
					if(!counterclockwiseRotateIsOutOfBounds() && !(currentTetromino instanceof OShaped)) {
						currentTetromino.counterclockwiseRotate(getAxis(), getRotateCells());
						repaint();
					}
					break;
			}
		}
	}
	
	/**
	 * 	�����_�����O,I�������,�~�Ӧ�Tetromino���O
	 * @author Leslie Leung
	 */
	private class IShaped extends Tetromino {
		/**
		 * 	�غc�l
		 */
		public IShaped() {
			cells = new Cell[4];
			
			cells[1] = new Cell(3, 0, Color.cyan);
			cells[0] = new Cell(4, 0, Color.cyan);
			cells[2] = new Cell(5, 0, Color.cyan);
			cells[3] = new Cell(6, 0, Color.cyan);
			
			/*�]�m����b�M�n���઺��l*/
			setAxis();
			setRotateCells();
			
			repaint();
		}
	}
	
	/**
	 * 	�����_�����O,S�������,�~�Ӧ�Tetromino���O
	 * @author Leslie Leung
	 */
	private class SShaped extends Tetromino {
		/**
		 * 	�غc�l
		 */
		public SShaped() {
			cells = new Cell[4];
			
			cells[0] = new Cell(4, 0, Color.blue);
			cells[1] = new Cell(5, 0, Color.blue);
			cells[2] = new Cell(3, 1, Color.blue);
			cells[3] = new Cell(4, 1, Color.blue);
			
			/*�]�m����b�M�n���઺��l*/
			setAxis();
			setRotateCells();
			
			repaint();
		}
	}
	
	/**
	 * 	�����_�����O,T�������,�~�Ӧ�Tetromino���O
	 * @author Leslie Leung
	 */
	private class TShaped extends Tetromino {
		/**
		 * 	�غc�l
		 */
		public TShaped() {
			cells = new Cell[4];
			
			cells[1] = new Cell(3, 0, Color.green);
			cells[0] = new Cell(4, 0, Color.green);
			cells[2] = new Cell(5, 0, Color.green);
			cells[3] = new Cell(4, 1, Color.green);
			
			/*�]�m����b�M�n���઺��l*/
			setAxis();
			setRotateCells();
			
			repaint();
		}
	}
	
	/**
	 * 	�����_�����O,Z�������,�~�Ӧ�Tetromino���O
	 * @author Leslie Leung
	 */
	private class ZShaped extends Tetromino {
		/**
		 * 	�غc�l
		 */
		public ZShaped() {
			cells = new Cell[4];
			
			cells[1] = new Cell(3, 0, Color.orange);
			cells[2] = new Cell(4, 0, Color.orange);
			cells[0] = new Cell(4, 1, Color.orange);
			cells[3] = new Cell(5, 1, Color.orange);
			
			/*�]�m����b�M�n���઺��l*/
			setAxis();
			setRotateCells();
			
			repaint();
		}
	}
	
	/**
	 * 	�����_�����O,L�������,�~�Ӧ�Tetromino���O
	 * @author Leslie Leung
	 */
	private class LShaped extends Tetromino {
		/**
		 * 	�غc�l
		 */
		public LShaped() {
			cells = new Cell[4];
			
			cells[1] = new Cell(3, 0, Color.pink);
			cells[0] = new Cell(4, 0, Color.pink);
			cells[2] = new Cell(5, 0, Color.pink);
			cells[3] = new Cell(3, 1, Color.pink);
			
			/*�]�m����b�M�n���઺��l*/
			setAxis();
			setRotateCells();
			
			repaint();
		}
	}
	
	/**
	 * 	�����_�����O,O�������,�~�Ӧ�Tetromino���O
	 * @author Leslie Leung
	 */
	private class OShaped extends Tetromino {
		/**
		 * 	�غc�l
		 */
		public OShaped() {
			cells = new Cell[4];
			
			cells[0] = new Cell(4, 0, Color.red);
			cells[1] = new Cell(5, 0, Color.red);
			cells[2] = new Cell(4, 1, Color.red);
			cells[3] = new Cell(5, 1, Color.red);
			
			/*�]�m����b�M�n���઺��l*/
			setAxis();
			setRotateCells();
			
			repaint();
		}
	}
	
	/**
	 * 	�����_�����O,J�������,�~�Ӧ�Tetromino���O
	 * @author Leslie Leung
	 */
	private class JShaped extends Tetromino {
		/**
		 * 	�غc�l
		 */
		public JShaped() {
			cells = new Cell[4];
			
			cells[1] = new Cell(3, 0, Color.yellow);
			cells[0] = new Cell(4, 0, Color.yellow);
			cells[2] = new Cell(5, 0, Color.yellow);
			cells[3] = new Cell(5, 1, Color.yellow);
			
			/*�]�m����b�M�n���઺��l*/
			setAxis();
			setRotateCells();
			
			repaint();
		}
	}
	
	/**
	 * 	�R���Y�z��
	 */
	public void removeRows() {
		for(int i = 0; i < getCurrentCells().length; i ++) {
			removeRow(getCurrentCells()[i].getY());
		}
	}
	
	/**
	 * 	�������b
	 * @return ����b
	 */
	public Cell getAxis() {
		return currentTetromino.getAxis();
	}
	
	/**
	 * 	����ݭn���઺��l
	 * @return �ݭn���઺��l
	 */
	public Cell[] getRotateCells() {
		return currentTetromino.getRotateCells();
	}
	
	/**
	 * 	�����e�Xù��������Ҧ���l
	 * @return ��e�Xù��������Ҧ���l
	 */
	public Cell[] getCurrentCells() {
		return currentTetromino.getCells();
	}
	
	/**
	 * 	�P�_�Xù������O�_��F����
	 * @return true:��F  false:�S��F
	 */
	public boolean isReachBottomEdge() {
		int oldY, newY, oldX;			//�w�q��l��/�s��Y(�a����)�M�ª�X(��)���� 	�]����Ш쩳���N���|�ܤF
		
		for(int i = 0; i < getCurrentCells().length; i ++) {
			oldY = getCurrentCells()[i].getY();
			newY = oldY + 1;
			oldX = getCurrentCells()[i].getX();
			
			if(oldY == ROWS - 1) {		//��F���O����
				return true;
			}
			
			if(isContain(oldX, newY)) {	//�U�@�Ӧ�m�����
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 	�P�_�Xù������O�_��F�����(�]�A�O�_�W�X���O����ɩM�U�@�Ӧ�m�O�_�X�{�P��L����I��)
	 * @return true:��F  false:�S��F
	 */
	public boolean isReachLeftEdge() {
		int oldX, newX, oldY;		//�w�q��l��/�s�����,�ª��a����
		
		for(int i = 0; i < getCurrentCells().length; i ++) {
			oldX = getCurrentCells()[i].getX();
			newX = oldX - 1;
			oldY = getCurrentCells()[i].getY();
			
			if(oldX == 0 || isContain(newX, oldY)) {//��F�����
				return true;
			}
			
			if(isContain(newX, oldY)) {//���䦳���
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 	�P�_�Xù������O�_��F�k���(�]�A�O�_�W�X���O�k��ɩM�U�@�Ӧ�m�O�_�X�{�P��L����I��)
	 * @return true:��F  false:�S��F
	 */
	public boolean isReachRightEdge() {
		int oldX, newX, oldY;		//�w�q��l��/�s�����,�ª��a����
		
		for(int i = 0; i < getCurrentCells().length; i ++) {
			oldX = getCurrentCells()[i].getX();
			newX = oldX + 1;
			oldY = getCurrentCells()[i].getY();
			
			if(oldX == COLUMNS - 1 || isContain(newX, oldY)) {//��F�k���
				return true;
			}
			
			if(isContain(newX, oldY)) {//�k�䦳���
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 	�P�_������ɰw�����O�_�|�W�X���(�]�A�O�_�W�X���O��ɩM�U�@�Ӧ�m�O�_�X�{�P��L����I��)
	 * @return true:�W�X���  false:�S�W�X
	 */
	public boolean clockwiseRotateIsOutOfBounds() {
		int oldX;	//rotateCell�����
		int oldY;	//rotateCell���a����
		int newX;	//rotateCell����᪺���
		int newY;	//rotateCell����᪺�a����
		
		for(int i = 0; i < 3; i ++) {
			oldX = getRotateCells()[i].getX();
			oldY = getRotateCells()[i].getY();
			
			newX = getAxis().getX() - oldY + getAxis().getY();	//�s��Эp���k
			newY = getAxis().getY() + oldX - getAxis().getX();	//�s�a���Эp���k
			
			if(newX < 0 || newY < 0 || newX > COLUMNS - 1 || newY > ROWS - 1) {//�p�G�V��,return true
				return true;
			}
			
			if(isContain(newX, newY)) {//�p�G�V��,return true
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 	�P�_����f�ɰw�����O�_�|�W�X���(�]�A�O�_�W�X���O��ɩM�U�@�Ӧ�m�O�_�X�{�P��L����I��)
	 * @return true:�W�X���  false:�S�W�X
	 */
	public boolean counterclockwiseRotateIsOutOfBounds() {
		int oldX;	//rotateCell�����
		int oldY;	//rotateCell���a����
		int newX;	//rotateCell����᪺���
		int newY;	//rotateCell����᪺�a����
		
		for(int i = 0; i < 3; i ++) {
			oldX = getRotateCells()[i].getX();
			oldY = getRotateCells()[i].getY();
			
			newX = getAxis().getX() - getAxis().getY() + oldY;	//�s��Эp���k
			newY = getAxis().getY() + getAxis().getX() - oldX;	//�s�a���Эp���k
			
			if(newX < 0 || newY < 0 || newX > COLUMNS - 1 || newY > ROWS - 1) {//�p�G�V��,return true
				return true;
			}
			
			if(isContain(newX, newY)) {//�p�G�V��,return true
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 	�P�_�Y�Ӯ�l�O�_������s�b
	 * @param x ���
	 * @param y �a����
	 * @return true:�s�b��H 	false:���s�b�ﹳ
	 */
	public boolean isContain(int x, int y) {
		if(wall[y][x] == null) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 	��{�Xù��������w�U��
	 */
	public void hardDrop() {
		while(!isReachBottomEdge()) {
			currentTetromino.softDrop();
		}
	}
	
	/**
	 * 	�������
	 * @param i�檺index
	 */
	public void removeRow(int i) {
		int oldY, newY;	
		//�p�G�䤤�@�Ӥ���S����,return
		for(int j = 0; j < COLUMNS; j ++) {
			if(wall[i][j] == null) {
				return;
			}
		}
		
		//������ç�Ӧ�W����������U��
		for(int k = i; k >= 1; k --){
			System.arraycopy(wall[k - 1], 0, wall[k], 0, COLUMNS);
			
			for(int m = 0; m < COLUMNS; m ++) {
				if(wall[k][m] != null) {//��󤣬O�Ū���H,�n���]���a�y��
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
		
		/*�e��*/
		for(int i = 0; i < ROWS; i ++) {
			for(int j = 0; j < COLUMNS; j ++) {
				if(wall[i][j] == null) {//��Y�I������Ů�
					g.setColor(Color.WHITE);
					g.fillRect(j * Cell.CELL_SIZE + 1, i * Cell.CELL_SIZE + 1, Cell.CELL_SIZE - 2, Cell.CELL_SIZE - 2);
				} else {//���������Ů�
					wall[i][j].paintCell(g);
				}
			}
		}
		
		/*�e��e�Xù�����*/
		for(int i = 0; i < getCurrentCells().length; i ++) {
			getCurrentCells()[i].paintCell(g);
		}
		
	}
}
