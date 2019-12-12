package tetris;

import java.awt.Color;
import java.awt.Graphics;

/**
 * 網格類別
 * @author Leslie Leung
 */
public class Cell {
	//一個網格的大小
	public static final int CELL_SIZE = 25;	

	
	private Color nowcolor;//格子的顏色
	private int x;	//格子的x座標
	private int y;	//格子的y座標

	/**
	 * 建構子
	 * @param x x(橫)座標
	 * @param y y(縱)座標
	 * @param setcolor 想要設成的顏色
	 */
	public Cell(int x, int y, Color setcolor) {
		setNowcolor(setcolor);
		this.x = x;
		this.y = y;
	}

	/**
	 * 設定該格子的x座標
	 * @param newX 新的x座標
	 */
	public void setX(int newX) {
		x = newX;
	}

	/**
	 * 設定該格子的y座標
	 * @param newY 新的y座標
	 */
	public void setY(int newY) {
		y = newY;
	}

	/**
	 * 得到該cell的x座標
	 * @return x座標
	 */
	public int getX() {
		return x;
	}

	/**
	 * 得到該cell的y座標
	 * @return y座標
	 */
	public int getY() {
		return y;
	}

	/**
	 * 繪圖方法
	 * @param g Graphics引用
	 */
	public void paintCell(Graphics g) {
		g.setColor(nowcolor);
		g.fillRect(x * CELL_SIZE + 1, y * CELL_SIZE + 1, CELL_SIZE - 2, CELL_SIZE - 2);
	}
	/**
	 * 得到該cell的顏色
	 * @return 顏色
	 */
	public Color getNowcolor() {
		return nowcolor;
	}
	/**
	 * 設定該格子的顏色
	 * @param newcolor 新的顏色
	 */	
	public void setNowcolor(Color newcolor) {
		this.nowcolor = newcolor;
	}

}
