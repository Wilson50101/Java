package tetris;

import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * �ج[���O
 * @author Leslie Leung
 */
public class TetrisFrame extends JFrame {
	private TetrisPane tp;	//�Xù������D�C������
	private JLabel mention;	//�C�������ܰT��
	
	/**
	 * �غc�l
	 */
	public TetrisFrame() {
		setSize(550, 600);				//�]�w�����j�p
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);	//�]�w�������ù�����
		setTitle("Tetris");				//�]�m���D��"Tetris"
		setResizable(false);			//�����\�����Y��
		setLayout(new FlowLayout());	//�]�w�����t�m
		
		tp = new TetrisPane();			//�s�س�������
		mention = new JLabel("��A��f�ɰw����A��D���ɰw����A����V�䱱��V�W�B�V�k�M�V�U���B�ʡA���Ů��䪽���U���쩳");
		add(mention);					//�N���ܰT���K�[��ج[
		add(tp);						//�N�C���D�������O�K�[��D�ج[��
		
		/*���U��L�ƥ�*/
		addKeyListener(tp.getInnerInstanceOfKeyControl());
		tp.addKeyListener(tp.getInnerInstanceOfKeyControl());
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new TetrisFrame();
	}
}
