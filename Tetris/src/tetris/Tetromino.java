package tetris;

/**
 * 	四格方塊類別
 * @author Leslie Leung
 */
public class Tetromino {
	protected Cell[] cells;			//用物件陣列儲存四格方塊
	protected Cell axis;			//旋轉軸
	protected Cell[] rotateCells;	//需要旋轉的格子之集合
	
	/**
	 * 	實現四格方塊逆時針旋轉的演算法
	 * @param axis旋轉軸,以cells中下標為0的cell為旋轉軸
	 * @param rotateCells 需要旋轉的格子集合
	 */
	protected void counterclockwiseRotate(Cell axis, Cell[] rotateCells) {
		int oldX;	//用來表示傳進來的rotatecell的橫坐標
		int oldY;	//用來表示傳進來的rotatecell的縱坐標
		int newX;	//用來表示傳進來的rotatecell"旋轉後"的橫坐標
		int newY;	//用來表示傳進來的rotatecell"旋轉後"的橫坐標
		
		for(int i = 0; i < 3; i ++) {
			oldX = rotateCells[i].getX();
			oldY = rotateCells[i].getY();
			
			newX = axis.getX() - axis.getY() + oldY;	//新橫坐標計算算法
			newY = axis.getY() + axis.getX() - oldX;	//新縱坐標計算算法
			
			rotateCells[i].setX(newX);					//重新設置目標格子的橫坐標
			rotateCells[i].setY(newY);					//重新設置目標格子的縱坐標
		}
	}
	
	/**
	 * 	實現四格方塊順時針旋轉的演算法
	 * @param axis旋轉軸,以cells中下標為0的cell為旋轉軸
	 * @param rotateCells 需要旋轉的格子集合
	 */
	protected void clockwiseRotate(Cell axis, Cell[] rotateCells) {
		int oldX;	//用來表示傳進來的rotatecell的橫坐標
		int oldY;	//用來表示傳進來的rotatecell的縱坐標
		int newX;	//用來表示傳進來的rotatecell"旋轉後"的橫坐標
		int newY;	//用來表示傳進來的rotatecell"旋轉後"的橫坐標
		
		for(int i = 0; i < 3; i ++) {
			oldX = rotateCells[i].getX();
			oldY = rotateCells[i].getY();
			
			newX = axis.getX() - oldY + axis.getY();	//新橫坐標計算算法
			newY = axis.getY() + oldX - axis.getX();	//新縱坐標計算算法
			
			rotateCells[i].setX(newX);		//重新設置目標格子的橫坐標
			rotateCells[i].setY(newY);		//重新設置目標格子的縱坐標
		}
	}
	
	/**
	 * 	實現四格方塊的軟下落(自動下落)
	 */
	protected void softDrop() {
		int oldY;	//某個格子下落前的縱座標
		int newY;	//某個格子下落後的縱座標
		
		//該方塊的所有四個格子下移
		for(int i = 0; i < cells.length; i ++) {
			oldY = cells[i].getY();
			newY = oldY + 1;
			
			cells[i].setY(newY);
		}
	}
	
	/**
	 * 	實現四格方塊左移的演算法
	 */
	protected void moveLeft() {
		int oldX;	//某個格子左移前的橫座標
		int newX;	//某個格子左移後的橫座標
		
		//該方塊的所有四個格子左移
		for(int i = 0; i < cells.length; i ++) {
			oldX = cells[i].getX();
			newX = oldX - 1;
			
			cells[i].setX(newX);
		}
	}
	
	/**
	 * 	實現四格方塊右移的演算法
	 */
	protected void moveRight() {
		int oldX;	//某個格子右移前的橫座標
		int newX;	//某個格子右移後的橫座標
		
		//該方塊的所有四個格子右移
		for(int i = 0; i < cells.length; i ++) {
			oldX = cells[i].getX();
			newX = oldX + 1;
			
			cells[i].setX(newX);
		}
	}
	
	/**
	 * 	返回四格方塊的格子集合
	 * @return Cell的集合
	 */
	protected Cell[] getCells() {
		return cells;
	}
	
	/**
	 * 	獲得旋轉軸
	 * @return 旋轉軸
	 */
	protected Cell getAxis() {
		return axis;
	}
	
	/**
	 * 	獲取需要旋轉的目標格子集合
	 * @return 目標格子集合
	 */
	protected Cell[] getRotateCells() {
		return rotateCells;
	}
	
	/**
	 * 	把cells[0]設為旋轉軸
	 */
	protected void setAxis() {
		axis = cells[0];
	}
	
	/**
	 * 	新建長度為3的陣列並把cells[1]~cells[3]加到rotateCells中
	 */
	protected void setRotateCells() {
		rotateCells = new Cell[]{cells[1], cells[2], cells[3]};
	}
}
