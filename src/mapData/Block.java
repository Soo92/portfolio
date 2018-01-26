package mapData;

import java.awt.Image;
import java.awt.Point;

public class Block {
	
	private Image block_image;
	
	//�ڽ��� ���� �»�� ����Ʈ
	private Point block_Point;
	
	//����
	private int width;
	
	//ĳ���Ϳ� ��ô ����
	private boolean contact;
	
	//����
	private int height;
	
	private String effect;
	
	
	//�»�� ����Ʈ�� ���� ���� �� ������ ����� �����Ѵ�.
	public Block(Point left_Top_Point, int width, int height) {
		
		//������ �Է¹��� �� ��� ���ݸ� �����´�.
		block_Point = new Point(left_Top_Point.x, left_Top_Point.y);

		//���̿� ���̸� �����´�.
		this.width = width;
		this.height = height;
		
		//���˿���
		contact = false;
		
	}
	
	//�������� ��ü�� ���� �ʴ� ������ private ���Ȼ��� ������
	
	public Block(Point left_Top_Point, int width, int height, String effect) {
		
		//������ �Է¹��� �� ��� ���ݸ� �����´�.
		block_Point = new Point(left_Top_Point.x, left_Top_Point.y);

		//���̿� ���̸� �����´�.
		this.width = width;
		this.height = height;
		
		this.effect = effect;
		//���˿���
		contact = false;
	}


	//�»�� ����Ʈ�� ���� ���̸� �����Ѵ�.
	public Point get_Left_Top_Point(){
		return block_Point;
	}
	
	//���̸� ��ȯ�Ѵ�.
	public int get_Width(){
		return width;
	}
	
	//���̸� ��ȯ�Ѵ�.
	public int get_Height(){
		return height;
	}
	
	public String get_effect(){
		return effect;
	}
	public void set_effect(String effectt){
	      effect= effectt;
	   }

	//���˽ÿ� true
	public void set_Contect_T(){
		contact = true;
	}
	
	public void set_Contect_F(){
		contact = false;
	}
	
	public boolean get_Set_Contect(){
		return contact;
	}

	public void set_block_Y_Point(int set_Y_Point){
		block_Point.y = set_Y_Point;
	}
	
	public void set_block_X_Point(int set_X_Point){
		block_Point.x = set_X_Point;
	   }
}


