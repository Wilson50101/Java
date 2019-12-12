package tetris;

import java.awt.Color;
import java.awt.Graphics;

/**
 * �������O
 * @author Leslie Leung
 */
public class Cell {
	//�@�Ӻ��檺�j�p
	public static final int CELL_SIZE = 25;	

	
	private Color nowcolor;//��l���C��
	private int x;	//��l��x�y��
	private int y;	//��l��y�y��

	/**
	 * �غc�l
	 * @param x x(��)�y��
	 * @param y y(�a)�y��
	 * @param setcolor �Q�n�]�����C��
	 */
	public Cell(int x, int y, Color setcolor) {
		setNowcolor(setcolor);
		this.x = x;
		this.y = y;
	}

	/**
	 * �]�w�Ӯ�l��x�y��
	 * @param newX �s��x�y��
	 */
	public void setX(int newX) {
		x = newX;
	}

	/**
	 * �]�w�Ӯ�l��y�y��
	 * @param newY �s��y�y��
	 */
	public void setY(int newY) {
		y = newY;
	}

	/**
	 * �o���cell��x�y��
	 * @return x�y��
	 */
	public int getX() {
		return x;
	}

	/**
	 * �o���cell��y�y��
	 * @return y�y��
	 */
	public int getY() {
		return y;
	}

	/**
	 * ø�Ϥ�k
	 * @param g Graphics�ޥ�
	 */
	public void paintCell(Graphics g) {
		g.setColor(nowcolor);
		g.fillRect(x * CELL_SIZE + 1, y * CELL_SIZE + 1, CELL_SIZE - 2, CELL_SIZE - 2);
	}
	/**
	 * �o���cell���C��
	 * @return �C��
	 */
	public Color getNowcolor() {
		return nowcolor;
	}
	/**
	 * �]�w�Ӯ�l���C��
	 * @param newcolor �s���C��
	 */	
	public void setNowcolor(Color newcolor) {
		this.nowcolor = newcolor;
	}

}
