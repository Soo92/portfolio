package character;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Hero extends JPanel{
   Toolkit tk = Toolkit.getDefaultToolkit();    //�̹����� �ҷ����� ���� ��Ŷ
   //����� �⺻ ��ǥ����
   private Point Hero_Point;
   
   private int x_Point;
   private int y_Point;
   
   //������� �β�
   private int hero_Height;
   private int hero_Width;
   private int move= 10;
   private int JumpSpeed =3;

   //����� ����(�ε巯�� ������ ǥ��)
   boolean x_Flag_Left;
   boolean y_Flag_Left;
   boolean x_Flag_Right;
   boolean y_Flag_Right;
   
   //������
   boolean jump_Hero;
   //������ �ö󰡴������� ���� �ɴ� �� ���� Ȯ�� true �̸� ������
   boolean jump_Hero_UP_DOWN;
   //���� ���۰� �Բ� �⺻ ĳ������ �������� Y�� üũ�ϱ����� �÷���
   boolean jump_Hero_Stop_Point;
   //���������� y��ǥ�� ������ ����
   int jump_Hero_Stop_Point_Y;
   
   protected String ImgUrl;
   protected boolean CrashBlock = false;
   protected Image Hero_pic;
   protected ImageIcon Hero_icon;

   //ĳ���Ͱ� �����ִ� ���� �����϶� true ���� �϶� false
   boolean face_Side_LEFT_RIGHT;
   
   //ĳ���� ������ �߷°��ӵ�
   int g;
   int gSum;
   int dgSum;
   
   
   //���� �����ٸ� ���� �����ٸ� �� ������� �����ؾ� �ٽ� ��� �ֵ��� ����
   int jump_Time_Schedule;
   
   
   //����� �⺻ ������
   public Hero() {
    ImgUrl = "img/dalza_run.gif";
  	Hero_pic = tk.getImage(this.getClass().getResource("/img/dalza_run.gif"));
	Hero_icon = new ImageIcon(this.getClass().getResource("/img/dalza_run.gif"));
      
      x_Point = 100;
      y_Point = 620;
      Hero_Point = new Point(x_Point,y_Point);

      //����� ��
      int a=100;
      hero_Width = Hero_icon.getIconWidth()/Hero_icon.getIconHeight()*a;
      hero_Height = a;
      
      x_Flag_Left = false;
      x_Flag_Right = false;
      y_Flag_Left = false;
      y_Flag_Right = false;
      
      face_Side_LEFT_RIGHT = false; //ĳ���� ������ ���ÿ� ���� ����
      
      jump_Hero = false;//����Ű ������ ���� �ϴ� ��ɾ� ����
      jump_Hero_UP_DOWN = true; //�ö󰡴���
      jump_Hero_Stop_Point = true; //true �϶� �ѹ� ����
      
      g = 1;//�߷°��ӵ�
      gSum = 15;
      dgSum = 0;//���� ���ӵ�
      
      //���� �����ٸ� 0 ���� �ʱ�ȭ
      jump_Time_Schedule = 0;
   }
   
   public String get_Hero_ImgUrl(){
      return ImgUrl; 
   }

   public Image get_Hero_Img(){
      return Hero_pic; 
   }
   
   public void set_Hero_Image(String img) {
		Hero_pic = tk.getImage(this.getClass().getResource(img));
		Hero_icon = new ImageIcon(Hero_pic);
	}
   
   //����� �� ��ȯ
   public int get_Hero_Width(){
      return hero_Width; 
   }
   //����� ���� ��ȯ
   public int get_Hero_Height(){
      return hero_Height;
   }
   
   //����� �˹� ������ ������ �ǰ�������. �������� �˹�
   public void left_Knock_Back(){
      x_Point -= 10;
   }
   //�������� �˹�
   public void right_Knock_Back(){
      x_Point += 10;
   }
   
   public void move(){  //synchronized �ش� �Լ��� �۵��ϴ� ���� ����ȭ�� �����Ѵ�.
      //�¿� ������ ��Ʈ��
	   if(x_Flag_Left){
			Hero_pic = tk.getImage(this.getClass().getResource("/img/dalza_run.gif"));
			Hero_icon = new ImageIcon(this.getClass().getResource("/img/dalza_run.gif"));
			x_Point -= move;
		}
		if(x_Flag_Right){
			Hero_pic = tk.getImage(this.getClass().getResource("/img/dalza_run.gif"));
			Hero_icon = new ImageIcon(this.getClass().getResource("/img/dalza_run.gif"));
			x_Point += move;
		}
		if(!x_Flag_Left&&!x_Flag_Right) {
			//System.out.println("a");
			Hero_pic = tk.getImage(this.getClass().getResource("/img/dalza_normal.gif"));
			Hero_icon = new ImageIcon(this.getClass().getResource("/img/dalza_normal.gif"));
		}
   }
   
   //ĳ���� ���� ����
   public void jump_Move(){ //ĳ������ Y�� ���� �����ͼ� Y��� ���� �����ų� �۾��������� ���ҽ�Ű�� Y�� ���� ���� �Ǵ� �߰��� ���� ������ ���� ���� ������ ��ġ��Ų��.
      
      //jump_Time_Schedule �� ���� �� �̻��϶� ���� ����
      jump_Time_Schedule++;
      
      
      if(jump_Hero){
         //���� ���� �����ϱ�
         if(jump_Hero_Stop_Point){
            jump_Hero_Stop_Point = false;
         }
         
         //gSum�� Ư�� ���ڿ� ���������� ���� ����
          if(gSum == 0){
             jump_Hero_UP_DOWN = !jump_Hero_UP_DOWN;
          }
          
          //���� ���� ����
          if(gSum >= 15){
             gSum = 15;
          }
          
          //�ö󰡴���
          if(jump_Hero_UP_DOWN){
             gSum -= g;
             y_Point -= gSum+JumpSpeed;
            Hero_pic = tk.getImage(this.getClass().getResource("/img/dalza_jump.gif"));
			Hero_icon = new ImageIcon(this.getClass().getResource("/img/dalza_jump.gif"));
			
          }else{ //����������
             gSum += g;
             y_Point += gSum;
             Hero_pic = tk.getImage(this.getClass().getResource("/img/dalza_run.gif"));
			 Hero_icon = new ImageIcon(this.getClass().getResource("/img/dalza_run.gif"));
          }
      }
   }
   
   //������ ���� ������ �����ش�.
   public void jump_Move_Stop(int y_Stop){
      jump_Hero_Stop_Point_Y = y_Stop; //�� ó�� ĳ���Ͱ� ������ ������ Y��ǥ
      //������ �����Ǿ�� �� ���� //������ �ٽ� �����ϵ��� �ʱ�ȭ
       if(y_Point >= jump_Hero_Stop_Point_Y){         //�ʱⰪ���� ���� y �����Ͱ� ĳ���ͺ��� �Ʒ��̸� ����
          y_Point = jump_Hero_Stop_Point_Y;      
          jump_Hero = false;                     //Jump_Move �żҵ� ������ �������� �Ѵ�. ���� ����
          jump_Hero_Stop_Point = true;            //���� ������ �ٽ� ���� Y �����͸� ������ �ֵ��� �ʱ�ȭ
          jump_Hero_UP_DOWN = true;    //�߷� ���ӵ� ���� ���� �ϴ� �Ҹ�
          g = 1;
          gSum = 15;
       }
   }
   
   //���� ��� ���� ������ ������ �������� �Ѵ�.
   public void auto_Jump_Down(){
      //�����ϴ� ������ �ƴϸ鼭 ĳ���Ͱ� ���� �´������ ������
      if(!jump_Hero){ //jump_Hero = ���� ���϶�
         if(dgSum>=15)dgSum = 15;
          y_Point += dgSum;
          dgSum += g;
          //System.out.println(dgSum);
          jump_Hero_UP_DOWN = false; //���� �����ʰ� �������� �߿��� ������ �� �ֵ��� ��
      }else dgSum = 0;
   }
   
   //�Ӹ��� ���������� �Ʒ��� ����߸���.
   public void auto_Jump_Down_Head(int minus_Num){
       y_Point += minus_Num+1;
   }
   
   //�������� ���鰡����
   public void stop_Move_Right(int x_Temp){
      //x_Point = x_Temp;
      //���� �� �� �ε����� ���������� ���� �Ұ���
      x_Flag_Right = false;
      //�������� -5 ��ŭ�� �о���� ������ �ȵ���
      x_Point -= 10;
      //y_Point �� �ణ�� ���� ������ �߰��Ͽ���, ������ �ε����� ���� ���� �� �ֱ⶧���� �̼��ϰ� �����̵��� ��
     // y_Point -= 5;
      
   }
   
   //�������� ���鰡����
   public void stop_Move_Leftt(int y_Temp){
         //x_Point = x_Temp;
         //���� �� �� �ε����� ���������� ���� �Ұ���
         x_Flag_Left = false;
         //���������� + 5 ��ŭ�� �о���� ������ �ȵ���
         x_Point += 10;
         //y_Point �� �ణ�� ���� ������ �߰��Ͽ���, ������ �ε����� ���� ���� �� �ֱ⶧���� �̼��ϰ� �����̵��� ��
         //y_Point -= 5;
   }
   
   //ĳ���Ͱ� ������ �����ʰ� ���������� �߷� ���ӵ��� �ʱ�ȭ �Ѵ�.
   public void set_dgSum_Zero(){
      dgSum=0;
   }
   
   //���ϰ� �浹�� �ٷ� �Ʒ��� ���������� �ϴ� �Լ�
   public void set_Jump_Hero_UP_DOWN(){
      dgSum=0;
      jump_Hero_UP_DOWN = false;
   }
   
   //���� Ű�� �������� ���� �� �����ϵ��� ������
   public void set_Hero_Jumping(){
      if(jump_Time_Schedule >= 15){   
         if(!jump_Hero){ //�������϶��� ������ �Ұ�����
         jump_Hero = true;
         //���� ������ �ʱ�ȭ
         jump_Time_Schedule = 0;
         }      
      }
   }
   
   //ĳ���Ͱ� ���� ������ �ƴ��� ��ȯ�Ѵ�
   public boolean get_Jump_State(){
      return jump_Hero_UP_DOWN; //true = �ö󰡴���
   }
   
   public void set_CrashBlock(Boolean CrashBlockin){
	      CrashBlock=CrashBlockin; //true = �ö󰡴���
	}
   public void get_CrashBlock(Boolean CrashBlockin){
	      CrashBlock=CrashBlockin; //true = �ö󰡴���
	}
   //���ΰ� x �� ����
   public int get_Hero_X_Point(){
      return x_Point;
   }
   //���ΰ� y �� ����
   public int get_Hero_Y_Point(){
      return y_Point;
   }

   public Point get_Hero_Point(){
      Hero_Point = new Point(x_Point,y_Point);
      return Hero_Point;
   }
   
   public Boolean get_Jump_Hero(){
	      return jump_Hero;
	   }

   //ĳ���Ͱ� ó�ٺ��� �ִ� ���� ����
   public boolean get_Face_Side_LFET_RIGHT(){
      return face_Side_LEFT_RIGHT;
   }
   
   //���ΰ� ������ �����ϸ� ������� ������Ų��
   public void set_Hero_Y_Point(int set_Y_Point){
      y_Point = set_Y_Point;
   }
   
   public void set_Hero_X_Point(int set_X_Point){
      x_Point = set_X_Point;
   }

   //���ΰ� �����̵�
   public void set_Hero_X_Left(){
      x_Flag_Left = true;
      face_Side_LEFT_RIGHT = true; //���� ����
   }
   
   //���ΰ� ���� �̵�
   public void set_Hero_X_Right(){
      x_Flag_Right = true;
      face_Side_LEFT_RIGHT = false; //���� ����
   }
   //���ΰ� ����
   public void set_Hero_X_Left_Stop(){
      x_Flag_Left = false;
   }
   //���ΰ� ����
   public void set_Hero_X_Right_Stop(){
      x_Flag_Right = false;
   }
   public void set_Hero_Jump_Speed(int JumpS){
	     JumpSpeed = JumpS; 
	   }
   public void set_Hero_Move_Velocity(int velocity){
       move = velocity;
}
}