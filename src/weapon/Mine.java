package weapon;

import java.awt.Point;

import javax.swing.ImageIcon;

//����
public class Mine extends Weapon{

		public Mine(Point character_Point, boolean bullet_Side_LEFT_RIGHT) {
			
			super(character_Point, bullet_Side_LEFT_RIGHT);

			Weapon_pic = tk.getImage(this.getClass().getResource("/img/shit.gif"));
			Weapon_icon = new ImageIcon(this.getClass().getResource("/img/shit.gif"));
			
			//�ǽ����� �⺻ ���ݷ�
			bullet_Power = 20;
			weapon_Height =70;
			weapon_Width =70;
			bullet_time = 5;
			
			bullet_Point = new Point(character_Point.x+35, character_Point.y+43);
			
		}
		
		//������ ���ư��� ������ ��� ���� �Ǵ� ����
		public void pistol_Move(boolean direction){
			//���̸� ����, �����̸� ������ ���� �̵�
			int a = bullet_Point.x;
			if(direction){ //����
				a -= 10;
			}else{// ����
				a += 10;
			}
			
		}
		
	}
