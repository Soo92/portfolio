package weapon;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;


//�⺻ �Ѿ��� �θ� Ŭ����
public class Weapon {
	Toolkit tk = Toolkit.getDefaultToolkit(); 	//�̹����� �ҷ����� ���� ��Ŷ
	
	//�Ѿ� ��ǥ
	protected Point bullet_Point;
	//�Ѿ� �ӵ�
	protected int bullet_Speed;
	//�Ѿ��� �Ŀ�
	protected int bullet_Power; 
	//���� �ӵ�
	protected int bullet_time;
	
	//�Ѿ��� �β�
	protected int weapon_Width;
	protected int weapon_Height;

	//�Ѿ��� ���ư� ����  true:�� false:��   
	protected boolean bullet_Side_LEFT_RIGHT;
	
	//�Ѿ� �浹 ������ ���� �ϱ� ����
	private boolean bullet_Remove_Bollean; 
	
	protected Image Weapon_pic;	
	protected ImageIcon Weapon_icon;
	
	//������ �ʱ�ȭ, �Ѿ� ��ǥ �ʱ�ȭ	
	Weapon(Point character_Point, boolean bullet_Side_LEFT_RIGHT) {
		Weapon_pic = tk.getImage(this.getClass().getResource("/img/M_bullet.gif"));
		Weapon_icon = new ImageIcon(this.getClass().getResource("/img/M_bullet.gif"));

		//�Ѿ� �� ���� ���ݸ� ���� �޴� ĳ������ x ���ݷ� ����
		//�Ѿ� �� ���� ���ݸ� ���� �޴� ĳ������ y ���ݷ� ����
		bullet_Point = new Point(character_Point.x, character_Point.y+30);
		
		weapon_Width = Weapon_icon.getIconWidth();
		weapon_Height = Weapon_icon.getIconHeight();
		
		//�Ѿ��� �⺻ �ӵ� 10���� ����
		bullet_Speed = 10;
		
		//�Ѿ��� �⺻ �Ŀ�
		bullet_Power = 100;
		bullet_time = 1;
		
		this.bullet_Side_LEFT_RIGHT = bullet_Side_LEFT_RIGHT;
		
		bullet_Remove_Bollean = false;
	}
	
	public Image get_Weapon_Img(){
		return Weapon_pic; 
	}

	//�Ѿ� �β� ��������
	public int get_Weapon_Width(){
		return weapon_Width;
	}
	public int get_Weapon_Height(){
		return weapon_Height;
	}
	
	
	//����� �Ѿ� 
	public void set_Remove_Bullet_Choice(){
		bullet_Remove_Bollean = true;
	}
	//����� �Ѿ� 
	public boolean get_Remove_Bullet_Choice(){
		return bullet_Remove_Bollean;
	}
	
	//�Ѿ��� ��ǥ�� ����
	public Point getPoint(){
		return bullet_Point;
	}
	
	//�Ѿ��� �¿��� ������ ����ssss
	public boolean get_Bullet_Side_LEFT_RIGHT(){
		return bullet_Side_LEFT_RIGHT;
	}
	
	public int get_Bullet_Power(){
		return bullet_Power;
	}
	
	public int get_Bullet_Time(){
		return bullet_time;
	}
	
}



