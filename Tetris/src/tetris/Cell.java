package tetris;

import java.awt.Color;
import java.awt.Graphics;

/**
 * ������
 * @author Leslie Leung
 */
public class Cell {
	public static final int CELL_SIZE = 25;		//һ������Ĵ�С

	/* ���ӵ�������ɫ  */
	private Color nowcolor;
	private int x;	//������
	private int y;	//������

	/**
	 * ���췽��
	 * @param x ������
	 * @param y ������
	 * @param style ���ӵ���ʽ��ͨ����ɫ��ָ��
	 */
	public Cell(int x, int y, Color setcolor) {
		setNowcolor(setcolor);
		this.x = x;
		this.y = y;
	}

	/**
	 * ���øø��ӵĺ�����
	 * @param newX �µĺ�����
	 */
	public void setX(int newX) {
		x = newX;
	}

	/**
	 * ���øø��ӵ�������
	 * @param newY �µ�������
	 */
	public void setY(int newY) {
		y = newY;
	}

	/**
	 * ��ȡ��Cell�ĺ�����
	 * @return ������
	 */
	public int getX() {
		return x;
	}

	/**
	 * ��ȡ��Cell��������
	 * @return ������
	 */
	public int getY() {
		return y;
	}

	/**
	 * ��ͼ����
	 * @param g Graphics����
	 */
	public void paintCell(Graphics g) {
		g.setColor(nowcolor);
		g.fillRect(x * CELL_SIZE + 1, y * CELL_SIZE + 1, CELL_SIZE - 2, CELL_SIZE - 2);
	}

	public Color getNowcolor() {
		return nowcolor;
	}

	public void setNowcolor(Color nowcolor) {
		this.nowcolor = nowcolor;
	}

}
