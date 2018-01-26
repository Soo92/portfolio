package weapon;

import java.awt.Point;

import javax.swing.ImageIcon;

//����
public class Pistol extends Weapon{

		public Pistol(Point character_Point, boolean bullet_Side_LEFT_RIGHT) {
			
			super(character_Point, bullet_Side_LEFT_RIGHT);
			
			//�ǽ����� �⺻ ���ݷ�
			bullet_Power = 1;
			bullet_time = 1;
			bullet_Speed = 10;
			Weapon_pic = tk.getImage(this.getClass().getResource("/img/M_bullet.gif"));
			Weapon_icon = new ImageIcon(this.getClass().getResource("/img/M_bullet.gif"));		}
		
		//������ ���ư��� ������ ��� ���� �Ǵ� ����
		public void pistol_Move(boolean direction){
			//���̸� ����, �����̸� ������ ���� �̵�
			if(direction){ //����
				bullet_Point.x -= bullet_Speed;
			}else{// ����
				bullet_Point.x += bullet_Speed;
			}
		}
	}
