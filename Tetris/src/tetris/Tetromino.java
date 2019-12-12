package tetris;

/**
 * 	�|�������O
 * @author Leslie Leung
 */
public class Tetromino {
	protected Cell[] cells;			//�Ϊ���}�C�x�s�|����
	protected Cell axis;			//����b
	protected Cell[] rotateCells;	//�ݭn���઺��l�����X
	
	/**
	 * 	��{�|�����f�ɰw���઺�t��k
	 * @param axis����b,�Hcells���U�Ь�0��cell������b
	 * @param rotateCells �ݭn���઺��l���X
	 */
	protected void counterclockwiseRotate(Cell axis, Cell[] rotateCells) {
		int oldX;	//�ΨӪ�ܶǶi�Ӫ�rotatecell�����
		int oldY;	//�ΨӪ�ܶǶi�Ӫ�rotatecell���a����
		int newX;	//�ΨӪ�ܶǶi�Ӫ�rotatecell"�����"�����
		int newY;	//�ΨӪ�ܶǶi�Ӫ�rotatecell"�����"�����
		
		for(int i = 0; i < 3; i ++) {
			oldX = rotateCells[i].getX();
			oldY = rotateCells[i].getY();
			
			newX = axis.getX() - axis.getY() + oldY;	//�s��Эp���k
			newY = axis.getY() + axis.getX() - oldX;	//�s�a���Эp���k
			
			rotateCells[i].setX(newX);					//���s�]�m�ؼЮ�l�����
			rotateCells[i].setY(newY);					//���s�]�m�ؼЮ�l���a����
		}
	}
	
	/**
	 * 	��{�|�������ɰw���઺�t��k
	 * @param axis����b,�Hcells���U�Ь�0��cell������b
	 * @param rotateCells �ݭn���઺��l���X
	 */
	protected void clockwiseRotate(Cell axis, Cell[] rotateCells) {
		int oldX;	//�ΨӪ�ܶǶi�Ӫ�rotatecell�����
		int oldY;	//�ΨӪ�ܶǶi�Ӫ�rotatecell���a����
		int newX;	//�ΨӪ�ܶǶi�Ӫ�rotatecell"�����"�����
		int newY;	//�ΨӪ�ܶǶi�Ӫ�rotatecell"�����"�����
		
		for(int i = 0; i < 3; i ++) {
			oldX = rotateCells[i].getX();
			oldY = rotateCells[i].getY();
			
			newX = axis.getX() - oldY + axis.getY();	//�s��Эp���k
			newY = axis.getY() + oldX - axis.getX();	//�s�a���Эp���k
			
			rotateCells[i].setX(newX);		//���s�]�m�ؼЮ�l�����
			rotateCells[i].setY(newY);		//���s�]�m�ؼЮ�l���a����
		}
	}
	
	/**
	 * 	��{�|�������n�U��(�۰ʤU��)
	 */
	protected void softDrop() {
		int oldY;	//�Y�Ӯ�l�U���e���a�y��
		int newY;	//�Y�Ӯ�l�U���᪺�a�y��
		
		//�Ӥ�����Ҧ��|�Ӯ�l�U��
		for(int i = 0; i < cells.length; i ++) {
			oldY = cells[i].getY();
			newY = oldY + 1;
			
			cells[i].setY(newY);
		}
	}
	
	/**
	 * 	��{�|�����������t��k
	 */
	protected void moveLeft() {
		int oldX;	//�Y�Ӯ�l�����e����y��
		int newX;	//�Y�Ӯ�l�����᪺��y��
		
		//�Ӥ�����Ҧ��|�Ӯ�l����
		for(int i = 0; i < cells.length; i ++) {
			oldX = cells[i].getX();
			newX = oldX - 1;
			
			cells[i].setX(newX);
		}
	}
	
	/**
	 * 	��{�|�����k�����t��k
	 */
	protected void moveRight() {
		int oldX;	//�Y�Ӯ�l�k���e����y��
		int newX;	//�Y�Ӯ�l�k���᪺��y��
		
		//�Ӥ�����Ҧ��|�Ӯ�l�k��
		for(int i = 0; i < cells.length; i ++) {
			oldX = cells[i].getX();
			newX = oldX + 1;
			
			cells[i].setX(newX);
		}
	}
	
	/**
	 * 	��^�|��������l���X
	 * @return Cell�����X
	 */
	protected Cell[] getCells() {
		return cells;
	}
	
	/**
	 * 	��o����b
	 * @return ����b
	 */
	protected Cell getAxis() {
		return axis;
	}
	
	/**
	 * 	����ݭn���઺�ؼЮ�l���X
	 * @return �ؼЮ�l���X
	 */
	protected Cell[] getRotateCells() {
		return rotateCells;
	}
	
	/**
	 * 	��cells[0]�]������b
	 */
	protected void setAxis() {
		axis = cells[0];
	}
	
	/**
	 * 	�s�ت��׬�3���}�C�ç�cells[1]~cells[3]�[��rotateCells��
	 */
	protected void setRotateCells() {
		rotateCells = new Cell[]{cells[1], cells[2], cells[3]};
	}
}
