package weapon;

import java.awt.Point;

import javax.swing.ImageIcon;

//����
public class FireBack extends Weapon{

      public FireBack(Point character_Point, boolean bullet_Side_LEFT_RIGHT) {
         
         super(character_Point, bullet_Side_LEFT_RIGHT);
         //�ǽ����� �⺻ ���ݷ�
         bullet_Power = 1;
         bullet_time = 1;
         bullet_Speed = 5;
         Weapon_pic = tk.getImage(this.getClass().getResource("/img/wave.gif"));
         Weapon_icon = new ImageIcon(this.getClass().getResource("/img/wave.gif"));
         weapon_Width=0;
         weapon_Height=0;
      }
      
      //������ ���ư��� ������ ��� ���� �Ǵ� ����
      public void pistol_Move(boolean direction){
         //���̸� ����, �����̸� ������ ���� �̵�
         int i=2;
         if(weapon_Width>80||weapon_Width<-80) i=0;
         bullet_Point.y-= i/2;
         weapon_Height+=i;
         if(direction){ //����
            bullet_Point.x -= bullet_Speed;
            weapon_Width-=i;
         }else{// ����
            bullet_Point.x += bullet_Speed;
            weapon_Width+=i;
         }
         
      }
      
   }