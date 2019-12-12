package tetris;

import java.awt.Color;
import java.awt.Graphics;

/**
 * 网格类
 * @author Leslie Leung
 */
public class Cell {
	public static final int CELL_SIZE = 25;		//一个网格的大小

	/* 格子的所有颜色  */
	private Color nowcolor;
	private int x;	//横坐标
	private int y;	//纵坐标

	/**
	 * 构造方法
	 * @param x 横坐标
	 * @param y 纵坐标
	 * @param style 格子的样式，通过颜色来指定
	 */
	public Cell(int x, int y, Color setcolor) {
		setNowcolor(setcolor);
		this.x = x;
		this.y = y;
	}

	/**
	 * 设置该格子的横坐标
	 * @param newX 新的横坐标
	 */
	public void setX(int newX) {
		x = newX;
	}

	/**
	 * 设置该格子的纵坐标
	 * @param newY 新的纵坐标
	 */
	public void setY(int newY) {
		y = newY;
	}

	/**
	 * 获取该Cell的横坐标
	 * @return 横坐标
	 */
	public int getX() {
		return x;
	}

	/**
	 * 获取该Cell的纵坐标
	 * @return 纵坐标
	 */
	public int getY() {
		return y;
	}

	/**
	 * 绘图方法
	 * @param g Graphics引用
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
