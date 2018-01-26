package mainframe;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JFrame;

import character.Hero;
import enemy.Enemy;
import enemy.Walker;
import enemy.Walker2;
import mapData.Block;
import mapData.Stage;
import weapon.FireBack;
import weapon.Mine;
import weapon.Pistol;
import weapon.Weapon;

//���� ȭ�� ��������� Ŭ����
class Meet extends JFrame implements KeyListener, Runnable, ActionListener{
	int framew=700, frameh= 700; 	//ȭ�� ũ��
	int buffx=0, buffy=0, buffw=700, buffh=700;
	int cnt=0, chatt=0, bulletch=0;
	int stage_Num = 0; 	//���� ���� ��������
	String str="";
	Toolkit tk = Toolkit.getDefaultToolkit(); 	//�̹����� �ҷ����� ���� ��Ŷ
	Image menu1,menu2,menu3,menu4;
	Image buffImage;	//���� ���۸��� �̹���
	Graphics buffg;		
	Thread th,th2; 			//������ ����
	Hero mainCh;		//����� ����
	Enemy enemy;	//�⺻ ���� ����
	Stage stage;	//���������� �����Ѵ�
	boolean end_Stage;	//���� ���������� �Ѿ� �� ���ΰ�.
	boolean clear_Stage;	//���� ���������� �Ѿ� �� ���ΰ�.
	boolean attack,mine_attack;	//�������ΰ� �������� �ƴѰ�
	int weapon_Number;	//���� ��ü �� �� ��ȣ
	int option=50;
	int a=20,b=0,c=0,d=0;
	boolean jump;	//������ ������ ���ΰ� ? true �̸� �����Ѵ�.
	boolean resumed=false;
	boolean Hero_move=false;
	
	Socket sock;
	BufferedReader in;
	PrintWriter out;
	boolean flag = false;
	String listTitle = "*******�����ڸ��*******";
	String user="����";

	List list;
	TextArea ta;
	TextField tf;
	Panel pa;

	//���� �⺻������
	Weapon weapon; 
	Point pistol_Point;
	
	List Hero_List = new List(); 	//�ټ��� ����(��)�� ���� ��� ����Ʈ
	ArrayList bullet_List = new ArrayList<Weapon>(); 	//�ټ��� ����(��)�� ���� ��� ����Ʈ
	ArrayList enemy_List = new ArrayList<Enemy>();	//�ټ��� ������ ���� ��� ����Ʈ
	
	public Meet() {
		init();
		setTitle("Meet");		//�������� �̸��� ����
		setSize(framew, frameh); //�������� ũ��
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();		//�������� �����쿡 ǥ�ñ��� ��ġ�� �����ϱ� ����.

		Panel pa = new Panel();   											//ä�� �ǳ�
		list = new List();
		list.add(listTitle);

		pa.setBackground(Color.green);
		pa.setLayout(new BorderLayout());
		pa.add(ta = new TextArea(),BorderLayout.CENTER);
		pa.add(list, BorderLayout.EAST);
//		ta.setVisible(false);
		ta.setEditable(false);
		ta.setFocusable(false);
		list.setFocusable(false);
		pa.add(tf = new TextField(),BorderLayout.SOUTH);
		tf.setFocusable(true);
		tf.addActionListener(this);

		this.add(pa,BorderLayout.SOUTH);
		
		//�������� ����� ȭ�� ���߾ӿ� ��ġ ��Ű�� ���� ��ǥ ���� ���.
		int focus_X = (int)(screen.getWidth() / 2 - framew / 2);
		int focus_Y = (int)(screen.getHeight() / 2 - frameh / 2);
		
		setLocation(focus_X, focus_Y); //�������� ȭ�鿡 ��ġ
		setResizable(false);		   //�������� ũ�⸦ ���Ƿ� ������ϵ��� ����
		setVisible(true);			   //�������� ���� ���̰� ���	
	}

	@Override							// ä��â �Է� ++ Ű���� event comma ��ǲ ����
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==tf) {
			chatt=cnt;
			tf.setFocusable(true);
			if("".equals(tf.getText())) return;
			str = tf.getText();
			if(filterMgr(str)) {
				new MDialog(this,"���","�Է��Ͻ� ��¥�� �������Դϴ�.");
				return;
			}
			if(!flag/*���̵� �϶�*/) {
				user=str;
				sendMessage(ChatProtocol.ID+":"+str);
				setTitle(getTitle()+"----"+str+"�� �ݰ����ϴ�.");
				ta.setText(" ");
				tf.setText("");
				flag=true;
			}else/*�Ϲ�ä��*/ {
				int i = list.getSelectedIndex();
				if(i==-1||i==0) { //��üä��
					sendMessage(ChatProtocol.CHATALL+":"+str);
				}else { // �ӼӸ� ä��
					String id = list.getSelectedItem();
					sendMessage(ChatProtocol.CHAT+":"+
							id+";"+str);
				}
				tf.setText("");
				tf.requestFocus();
			}
		}
	}
	
	private void init(){
		//���α׷��� ���������� �����ϵ��� ����� �ݴϴ�.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menu1 = tk.getImage(this.getClass().getResource("/img/menu1.png"));
		menu2 = tk.getImage(this.getClass().getResource("/img/menu2.png"));
		menu3 = tk.getImage(this.getClass().getResource("/img/menu3.png"));
		menu4 = tk.getImage(this.getClass().getResource("/img/menu4.png"));
		//���ΰ� ����
		mainCh = new Hero();
		
		//�������� true �� �Ǹ� ���� ���������� �Ѿ
		end_Stage = true;
		
		addKeyListener(this); //Ű���� �̺�Ʈ ����
		th = new Thread(this); 	  //������ ����
		th.start(); 		  //������ ����
		
		//�������� 1 ����
		stage = new Stage();
		
		attack = false; //���� ���� ���� 
		weapon_Number = 1;//������ ����, �⺻ 1 �ǽ���
		jump = false; //���� ���¼���
	}
	
	public void paint(Graphics g){
		//������۸� ���� ũ�⸦ ȭ�� ũ��� ���� ����
		buffImage = createImage(buffw,buffh*2);
		//������ �׷��� ��ü ���
		buffg = buffImage.getGraphics();
		if(!resumed) update(g);
		else update1(g);
	}
	
	private void update1(Graphics g) {
		//�Ͻ�����
		if(resumed) {
			g.clearRect(230, 180, 250, 300);
			g.drawImage(menu1, 250-a/5, 200-a/2, 200+a/4, 50+a, this);
			g.drawImage(menu2, 250-b/5, 260-b/2, 200+b/4, 50+b, this);
			g.drawImage(menu3, 250-c/5, 320-c/2, 200+c/4, 50+c, this);
			g.drawImage(menu4, 250-d/5, 380-d/2, 200+d/4, 50+d, this);
		}
	}
	
	public void update(Graphics g){
		//������ �׷��� �׸��� �����´�.
		draw();
		//�Ѿ��� �׸���.
		draw_Bullet();
		//������ �׸���.
		draw_Enemy();
		//���������� �׸���.
		draw_Stage();
		//ȭ�鿡 ���ۿ� �׸� �׸��� ������ �׸���
		g.clearRect(0, 0, framew, frameh);
		if(mainCh.get_Hero_X_Point()<=framew/2) {                                 //ĳ���Ͱ� ȭ�� ���ʺ� ��ġ
			g.drawImage(buffImage, -10,buffh/2-mainCh.get_Hero_Y_Point(), this);
		}
		else if(mainCh.get_Hero_X_Point()>=buffw-framew/2) {						//ĳ���Ͱ� ȭ�� �����ʺ� ��ġ
			g.drawImage(buffImage, framew-buffw,buffh/2-mainCh.get_Hero_Y_Point(), this);
		}
		else {																		//������ ��Ȳ�϶�
			g.drawImage(buffImage, framew/2-mainCh.get_Hero_X_Point(),buffh/2-mainCh.get_Hero_Y_Point(), this);
		}
		
		g.drawImage(buffImage, 0, 0, 200, 200, this);
		
		//Ŭ����
		if(clear_Stage) {
			g.drawString("CLEAR", framew/2, frameh/2);
		}
		
		//����
		if((mainCh.get_Hero_X_Point() < (buffx) || 
			mainCh.get_Hero_X_Point() > (buffx+buffw)) ||
			mainCh.get_Hero_Y_Point() > (buffy+buffh)*1.5){
				mainCh=new Hero();
				mainCh.set_Hero_Y_Point(stage.get_Block().get(0).get_Left_Top_Point().y-mainCh.get_Hero_Height());
		}
	}
	
	//������ �׸����� �׸� �κ�
	public void draw(){
		//�����ӿ� ����� png �̹����� �׷��ֽ��ϴ�.
		//buffg.drawImage(hero_Png, mainCh.get_Hero_X_Point(), mainCh.get_Hero_Y_Point(), this);
		if(mainCh.get_Hero_X_Point()<=framew/2) {                                 //ĳ���Ͱ� ȭ�� ���ʺ� ��ġ
			buffg.drawImage(tk.getImage(this.getClass().getResource("/img/stage"+stage_Num+".gif")), 0, mainCh.get_Hero_Y_Point()-buffh/2, buffw, buffh, this);
			buffg.drawRect( 0, mainCh.get_Hero_Y_Point()-buffh/2, buffw, buffh);
		}
		else if(mainCh.get_Hero_X_Point()>=buffw-framew/2) {						//ĳ���Ͱ� ȭ�� �����ʺ� ��ġ
			buffg.drawRect(buffw-framew, mainCh.get_Hero_Y_Point()-buffh/2, buffw, buffh);
		}
		else {																		//������ ��Ȳ�϶�
			buffg.drawImage(tk.getImage(this.getClass().getResource("/img/stage"+stage_Num+".gif")), mainCh.get_Hero_X_Point()-framew/2, mainCh.get_Hero_Y_Point()-buffh/2, buffw, buffh, this);
			buffg.drawRect(mainCh.get_Hero_X_Point()-framew/2, mainCh.get_Hero_Y_Point()-buffh/2, buffw, buffh);
		}
		
		if(mainCh.get_Face_Side_LFET_RIGHT()) {
			buffg.drawImage(mainCh.get_Hero_Img(), mainCh.get_Hero_X_Point()+mainCh.get_Hero_Width(),   mainCh.get_Hero_Y_Point(), -mainCh.get_Hero_Width(), mainCh.get_Hero_Height(),this);
			buffg.drawRect(mainCh.get_Hero_X_Point()+mainCh.get_Hero_Width(),   mainCh.get_Hero_Y_Point(), -mainCh.get_Hero_Width(), mainCh.get_Hero_Height()); //�簢������ �ϴ� ��ü
		}
		else {
			buffg.drawImage(mainCh.get_Hero_Img(), mainCh.get_Hero_X_Point(),   mainCh.get_Hero_Y_Point(), mainCh.get_Hero_Width(), mainCh.get_Hero_Height(),this);
			buffg.drawRect(mainCh.get_Hero_X_Point(), mainCh.get_Hero_Y_Point(), mainCh.get_Hero_Width(), mainCh.get_Hero_Height()); //�簢������ �ϴ� ��ü
		}
		for (int i = 0; i < Hero_List.getItemCount(); i++) {
			String[] a = Hero_List.getItem(i).split(",");
			int UserX = Integer.parseInt(a[0]);
			int UserY = Integer.parseInt(a[1]);
			int UserW = Integer.parseInt(a[2]);
			int UserH = Integer.parseInt(a[3]);
			Boolean UserDirection = a[4].equals("true");
			Boolean UserJump = a[5].equals("true");
			Image UserImage = tk.getImage(a[6]);
			if(UserDirection) {
				buffg.drawImage(UserImage, UserX+UserW, UserY, -UserW, UserH, this);
				buffg.drawRect(UserX+UserW, UserY, -UserW, UserH); //�簢������ �ϴ� ��ü
			}
			else {
				buffg.drawImage(UserImage, UserX, UserY, UserW, UserH, this);
				buffg.drawRect(UserX, UserY, UserW, UserH); //�簢������ �ϴ� ��ü
			}
		}
		
		if((cnt-chatt)<=100&&chatt!=0) {
			if((cnt-chatt)<10)
			this.requestFocus();
			buffg.drawString(str,mainCh.get_Hero_X_Point()+20, mainCh.get_Hero_Y_Point()-30);
		}
	}
	
	//�������� �� �� �׸� (���)
	public void draw_Stage(){
		//���� ���������� �Ѿ
		if(end_Stage){
			stage_Num++; //���� ���������� �Ѿ //�� ��ġ �ʱ�ȭ 1��������
			//���������ѹ��� �ѹ� �ݿ��ؼ� ���������� �����.
			this.setTitle("MEET");
			mainCh = new Hero();
			stage.map_meet(stage_Num);
			//�⺻ ���� ��Ŀ ����
			enemy_Process(stage_Num);
			mainCh.set_Hero_Y_Point(stage.get_Block().get(0).get_Left_Top_Point().y-mainCh.get_Hero_Height());
			end_Stage = false;
		}
				
		//������ ���������� ����� �׷����� 
		int temp = 0;
		for(int i=0; i<stage.get_Block().size(); i++){
			buffg.setColor(Color.DARK_GRAY);
			buffg.fillRect(stage.get_Block().get(i).get_Left_Top_Point().x,
					stage.get_Block().get(i).get_Left_Top_Point().y,
					stage.get_Block().get(i).get_Width(), 
					stage.get_Block().get(i).get_Height());
			//�浹 �Լ� ȣ�� 1�̸� ���� ĳ����
			crash_Decide_Block(mainCh, stage.get_Block().get(i));
			//buff�̹��� ũ������
			if(buffw<stage.get_Block().get(i).get_Left_Top_Point().x+stage.get_Block().get(i).get_Width())
				buffw=stage.get_Block().get(i).get_Left_Top_Point().x+stage.get_Block().get(i).get_Width()+10;
			if(buffx>stage.get_Block().get(i).get_Left_Top_Point().x)
				buffx=stage.get_Block().get(i).get_Left_Top_Point().x-10;
			if(buffh<stage.get_Block().get(i).get_Left_Top_Point().y+stage.get_Block().get(i).get_Height())
				buffh=stage.get_Block().get(i).get_Left_Top_Point().y+stage.get_Block().get(i).get_Height()+10;
			if(buffy>stage.get_Block().get(i).get_Left_Top_Point().y)
				buffy=stage.get_Block().get(i).get_Left_Top_Point().y-10;
			//ĳ���Ͱ� ��� ������ ��� ���� ������ �߶����̴�.
			if(!stage.get_Block().get(i).get_Set_Contect()){
				temp++;
			}
			//���� ���ڸ�ŭ false �̸� ������ ��� �������� 
			if(temp == stage.get_Block().size()){
				mainCh.auto_Jump_Down();
			}
		}
		for(int i=0; i<stage.get_Item().size(); i++){
			buffg.setColor(Color.LIGHT_GRAY);
			buffg.fillRect(stage.get_Item().get(i).get_Left_Top_Point().x,
					stage.get_Item().get(i).get_Left_Top_Point().y,
					stage.get_Item().get(i).get_Width(), 
					stage.get_Item().get(i).get_Height());
			//�浹 �Լ� ȣ�� 1�̸� ���� ĳ����
			crash_Decide_Item(mainCh, stage.get_Item().get(i));
			//buff�̹��� ũ������
			if(buffw<stage.get_Item().get(i).get_Left_Top_Point().x+stage.get_Item().get(i).get_Width())
				buffw=stage.get_Item().get(i).get_Left_Top_Point().x+stage.get_Item().get(i).get_Width()+10;
			if(buffx>stage.get_Item().get(i).get_Left_Top_Point().x)
				buffx=stage.get_Item().get(i).get_Left_Top_Point().x-10;
			if(buffh<stage.get_Item().get(i).get_Left_Top_Point().y+stage.get_Item().get(i).get_Height())
				buffh=stage.get_Item().get(i).get_Left_Top_Point().y+stage.get_Item().get(i).get_Height()+10;
			if(buffy>stage.get_Item().get(i).get_Left_Top_Point().y)
				buffy=stage.get_Item().get(i).get_Left_Top_Point().y-10;
			//ĳ���Ͱ� ��� ������ ��� ���� ������ �߶����̴�.
		}
	}
	
	public void draw_Bullet(){	//�Ѿ��� �׸��� �Լ�
		for(int i=0; i<bullet_List.size(); i++){		//�Ѿ� ���� ��ŭ �ݺ��ϸ� �׸���.
			weapon = (Weapon) bullet_List.get(i);
			buffg.drawRect(weapon.getPoint().x,  weapon.getPoint().y, weapon.get_Weapon_Width(), weapon.get_Weapon_Height()); //�簢������ �ϴ� ��ü
			buffg.drawImage(weapon.get_Weapon_Img(),weapon.getPoint().x,  weapon.getPoint().y, weapon.get_Weapon_Width(), weapon.get_Weapon_Height(),this); //�簢������ �ϴ� ��ü
			if(weapon instanceof Pistol){ //�Ҹ� ����Ʈ�� ������ �ǽ���� ���� �����ϴٸ�.
				((Pistol) weapon).pistol_Move( weapon.get_Bullet_Side_LEFT_RIGHT() );				//�ǽ��� �Ѿ� ������ �� ���⼺�� ������ ���ư���.
			}

			if(weapon instanceof Mine){ //�Ҹ� ����Ʈ�� ������ ���ڷ� ���� �����ϴٸ�.
				((Mine) weapon).pistol_Move( weapon.get_Bullet_Side_LEFT_RIGHT() );				//�ǽ��� �Ѿ� ������ �� ���⼺�� ������ ���ư���.
			}
			
			//�Ѿ˰� ������ �浹���� �Լ� ȣ�� 
			for(int j=0; j<enemy_List.size(); j++){
			enemy = (Enemy) enemy_List.get(j);
			crash_Decide_Enemy(weapon, enemy, enemy.get_Move_Site());
			}
			//�Ѿ� ���� �Լ� ȣ��
			remove_Bullet(weapon, i);
		}
	}
	
	//�浹 ���� �Լ�, �浹�� 2�� �ٿ����Ʈ�� ���Ѵ�. 1�� �� ū �簢�� 2���� ������ �簢���� �������Ѵ� �ϴ� 1�� �ٿ�� ������ �����ϵ��� �Ѵ�.
	//private int object_Width; //������ ������ �����ϱ� ���� ���� ����
	//private int object_Height; //������ ������ �����ϱ� ���� ���� ����
	
	private boolean jump_Up_Lock_Temp = false; //�ö󰡴� ���߿��� ������ ���� �Ұ���
	private int auto_Jump_Down_Head_Flag = 0; //�Ӹ� ���� ���� ����
	//�浹 üũ �ʰ� ���� ĳ���� ĳ���� x,y
	public void crash_Decide_Block(Hero hero, Block block){ //what_Object 1 �϶� ���� ĳ���� �浹
		//�ö󰡴����϶� ���� �Ұ�
		if(!mainCh.get_Jump_State()){
			jump_Up_Lock_Temp = true;
		}
			if((hero.get_Hero_X_Point()+hero.get_Hero_Width()) < (block.get_Left_Top_Point().x ) || 
					hero.get_Hero_X_Point() > (block.get_Left_Top_Point().x+block.get_Width()) ||
					(hero.get_Hero_Y_Point()+hero.get_Hero_Height()) < block.get_Left_Top_Point().y ||
					hero.get_Hero_Y_Point() > (block.get_Left_Top_Point().y+block.get_Height())){
				//ĳ���Ͱ� ���� ���� ������ false ���� ��� ���� �������� ���������� 
				block.set_Contect_F();
			}else {
				//ĳ���Ͱ� ���� ������ true
				block.set_Contect_T();
				//ĳ���Ͱ� ������ �����ʰ� ���������� �߷� ���ӵ��� �ʱ�ȭ �Ѵ�.
				mainCh.set_dgSum_Zero();
				//ĳ������ �Ӹ��� ���� �ٴڿ� �������
				if(hero.get_Hero_Y_Point() >= block.get_Left_Top_Point().y + block.get_Height() - 20){
//					System.out.println("�Ӹ��� �ٴ� �ε���");
					//ĳ���Ͱ� ���� �ε����� �ٷ� �Ʒ������� ������
					mainCh.set_Jump_Hero_UP_DOWN();
					//������ ���ϰ� �߶��Ҷ� �������� ���� �ʵ��� �ؾ��Ѵ�.
					//�� ���� ���̸� ���ؼ� ����.
					auto_Jump_Down_Head_Flag++;
					if(auto_Jump_Down_Head_Flag >= 2){
					mainCh.auto_Jump_Down_Head(block.get_Left_Top_Point().y + block.get_Height() - hero.get_Hero_Y_Point());
					}
				}else 
				//ĳ������ �ϴ��� ������ ����� �������� ���� ���� �˷��ش�.
				if(hero.get_Hero_Y_Point()+hero.get_Hero_Height()  <=  block.get_Left_Top_Point().y + 20){
					//System.out.println("���� ��� ����");
				//������ �߻��ϸ� �������� ������ �߻������� �������� ������ �߻��Ͽ����� ������ ���ش�.
				auto_Jump_Down_Head_Flag = 0;
				//ĳ���Ͱ� �����ߴٰ� �Ʒ��� �������� ���߿��� �� ���� �ö� ���� �ֵ��� ���� && ���Ͻÿ� ����� ���� �ֵ��� ����
				if(jump_Up_Lock_Temp){
					//������ ������� ������ ���� ��Ű������ �Լ�
					mainCh.set_Hero_Y_Point(block.get_Left_Top_Point().y-hero.get_Hero_Height());
					//ĳ���Ͱ� �ٽ� ��ġ �ؾ� �� �� �������� && ���� ���� �ƴҶ��� �ʱ�ȭ �Ҽ� �ֵ��� �ؾ��Ѵ�.
					mainCh.jump_Move_Stop(mainCh.get_Hero_Y_Point());
					jump_Up_Lock_Temp = false;
				}
					//���� ���������� �������ٰ� ��� ���� if ���� ������ ���� ���� �� ���´�.
				}//ĳ���� ������ ���� ������ �ھ�����
				else if(hero.get_Hero_X_Point() + hero.get_Hero_Width() <= block.get_Left_Top_Point().x + 25){
					System.out.println("���� ���� �� �ε���");
					//���������� ���� ���ϵ��� ���ƾ���
					mainCh.stop_Move_Right(hero.get_Hero_X_Point());
				}
				//ĳ���� ������ ���� ������ �ھ�����
				else if(hero.get_Hero_X_Point() >= block.get_Left_Top_Point().x + block.get_Width() - 25){
					System.out.println("���� ������ �� �ε���");
					//�������� ���� ���ϵ��� ���ƾ���
					mainCh.stop_Move_Leftt(hero.get_Hero_X_Point());
				}
				else {
					block.set_Contect_F();//ĳ���Ͱ� ���� ���� ������ false ���� ��� ���� �������� ���������� 
				}
			}
		}
	//������ ����� �浹 �˻� �������ٰ� �¹������Ѵ�.
	
	public void crash_Decide_Enemy_Block(Block block, Enemy enemy){
		//�������� �۵�
		if(enemy.get_Down_Start()){
			//xpoint = ���� x ��ǥ
			if((block.get_Left_Top_Point().x + block.get_Width()) <= (enemy.get_enemy_Point().x ) || 
					block.get_Left_Top_Point().x >= (enemy.get_enemy_Point().x+enemy.get_Enemy_Width()) ||
					(block.get_Left_Top_Point().y + block.get_Height()) <= enemy.get_enemy_Point().y ||
					block.get_Left_Top_Point().y-1 >= (enemy.get_enemy_Point().y+enemy.get_Enemy_Height())){
			//enemy.get_Enemy_Exit_Yoint(1000);
		}else{
			System.out.println("���� ���� ��� ����");
			//��� ������ ������ ��ġ�� �����Ѵ�, ������ ������� ������Ų��.
			enemy.get_Enemy_Exit_Yoint(block.get_Left_Top_Point().y  - enemy.get_Enemy_Height() );
			enemy.init_Bound_Site(block.get_Left_Top_Point().x, (block.get_Width() + block.get_Left_Top_Point().x), block.get_Left_Top_Point().y - block.get_Height());
			
			//���� �˰��� ���� ���ž��Ѵ�.
			enemy.init_Range_Site(enemy.get_enemy_Point().x, enemy.get_enemy_Point().y);
		}
		}
	}
	
	public void crash_Decide_Item(Hero hero, Block item){
		if((hero.get_Hero_X_Point()+hero.get_Hero_Width()) < (item.get_Left_Top_Point().x ) || 
				hero.get_Hero_X_Point() > (item.get_Left_Top_Point().x+item.get_Width()) ||
				(hero.get_Hero_Y_Point()+hero.get_Hero_Height()) < item.get_Left_Top_Point().y ||
				hero.get_Hero_Y_Point() > (item.get_Left_Top_Point().y+item.get_Height())){
			item.set_Contect_F();
		}else {
			item.set_Contect_T();
			if(item.get_effect()=="next") {
				clear_Stage=false;
				end_Stage=true;
			}
			if(item.get_effect()=="finish") {
				clear_Stage=true;
				end_Stage=true;
			}
		}
	}
	
	//�浹 üũ �Լ� ĳ���Ϳ� �Ѿ� �� 
	public void crash_Decide_Enemy(Hero hero, Enemy enemy, boolean get_Site){ //get_Site = Ž�������� �������� ��������
		//what_Object = 1 �̸� ĳ����, 2 �̸� �Ѿ�
				if(get_Site){ //������ Ž���Ҷ�. �簢���� ������ ĳ������ �������� �����ϱ� ������ �˹� ���ÿ� ĳ������ ���� ��ŭ ��� ������ x ���� ����(width)�� �����־�Ѵ�. 
					//System.out.println("���� �̵� ĳ���� ��ġ : " + enemy.get_enemy_Point().x + ", ĳ���� ���� �þ� : " + (enemy.get_enemy_Point().x - enemy.get_Range_Site_Width()));
					if((hero.get_Hero_X_Point()+hero.get_Hero_Width()) < (enemy.get_Range_Site_Width_Left_Point() - enemy.get_Range_Site_Width_Right_Point()+hero.get_Hero_Width()) || 
							hero.get_Hero_X_Point() > (enemy.get_Range_Site_Width_Left_Point() - enemy.get_Range_Site_Width_Right_Point()+hero.get_Hero_Width()+enemy.get_Range_Site_Width_Right_Point()) ||
							(hero.get_Hero_Y_Point()+hero.get_Hero_Height()) < enemy.get_range_Site_Height_Top_Point() ||
							hero.get_Hero_Y_Point() > (enemy.get_range_Site_Height_Top_Point()+enemy.get_range_Site_Height_Bottom_Point())){
						enemy.set_Not_Find_Hero(); //ĳ���͸� ã�� ��������.
					}else {
						//System.out.println("�浹 ����");
						enemy.set_Find_Hero(mainCh.get_Hero_X_Point()); //ĳ���͸� ã������
						//ĳ���Ϳ� ������ ���� �������. ��������
						if((hero.get_Hero_X_Point()+hero.get_Hero_Width()) < (enemy.get_enemy_Point().x - enemy.get_Enemy_Width()) || 
								hero.get_Hero_X_Point() > (enemy.get_enemy_Point().x - enemy.get_Enemy_Width()+enemy.get_Enemy_Width()) ||
								(hero.get_Hero_Y_Point()+hero.get_Hero_Height()) < enemy.get_enemy_Point().y ||
								hero.get_Hero_Y_Point() > (enemy.get_enemy_Point().y+enemy.get_Enemy_Height())){
						}else{
							//�������� �˹�
							hero.left_Knock_Back();
						}
					}
				}else { //���� Ž���Ҷ� ��� 
					if((hero.get_Hero_X_Point()+hero.get_Hero_Width()) < (enemy.get_Range_Site_Width_Left_Point() ) || 
							hero.get_Hero_X_Point() > (enemy.get_Range_Site_Width_Left_Point()+enemy.get_Range_Site_Width_Right_Point()) ||
							(hero.get_Hero_Y_Point()+hero.get_Hero_Height()) < enemy.get_range_Site_Height_Top_Point() ||
							hero.get_Hero_Y_Point() > (enemy.get_range_Site_Height_Top_Point()+enemy.get_range_Site_Height_Bottom_Point())){
						enemy.set_Not_Find_Hero(); //ĳ���͸� ã�� ��������.
					}else {
						//System.out.println("�浹 ����");
						enemy.set_Find_Hero(mainCh.get_Hero_X_Point()); //ĳ���͸� ã������
						//���� ��� ������ �������.
						if((hero.get_Hero_X_Point()+hero.get_Hero_Width()) < (enemy.get_enemy_Point().x ) || 
								hero.get_Hero_X_Point() > (enemy.get_enemy_Point().x + enemy.get_Enemy_Width()) ||
								(hero.get_Hero_Y_Point()+hero.get_Hero_Height()) < enemy.get_enemy_Point().y ||
								hero.get_Hero_Y_Point() > (enemy.get_enemy_Point().y+enemy.get_Enemy_Height())){
						}else {
							//�������� �˹�
							hero.right_Knock_Back();
						}
					}
				}
	}
	//�浹 üũ �Լ� ĳ���Ϳ� �Ѿ� �� 
	public void crash_Decide_Enemy(Weapon weapon, Enemy enemy, boolean get_Site){ //get_Site = Ž�������� �������� ��������
			if((weapon.getPoint().x+weapon.get_Weapon_Width()) < (enemy.get_enemy_Point().x ) || 
					weapon.getPoint().x > (enemy.get_enemy_Point().x+enemy.get_Enemy_Width()) ||
					(weapon.getPoint().y+weapon.get_Weapon_Height()) < enemy.get_enemy_Point().y ||
					weapon.getPoint().y > (enemy.get_enemy_Point().y+enemy.get_Enemy_Height())){
			}else {
				//System.out.println("�浹 ����");
				//�ǰݽ� ������ �������� ���ҽ�Ų��.
				enemy.enemy_HP_Down(weapon.get_Bullet_Power());
							//�ǰݵ� ���� �������� 0 �� �Ǹ� �����Ѵ�.
				if(enemy.get_Enemy_HP() <= 0 || 
				(enemy.get_enemy_Point().x < (buffx-10) || 
				enemy.get_enemy_Point().x+enemy.get_Enemy_Width() > (buffx+buffw+10)) ||
				enemy.get_enemy_Point().y+enemy.get_Enemy_Height()>buffh-10){
					enemy_List.remove(enemy);
				}
				//�ǰݵ� ���͸� �ڽ��� �������� �޷������� �ؾ��Ѵ�. �� ���͸� ���� ���·� �����ؾ��Ѵ�.
				if(enemy.get_enemy_Point().x >= mainCh.get_Hero_X_Point()){
					enemy.set_Move_Site(true);
					//���� �˹�ȿ��
					enemy.knockback(true);
				}
				//�ǰݵ� ���͸� �ڽ��� �������� �޷������� �ؾ��Ѵ�. �� ���͸� ���� ���·� �����ؾ��Ѵ�.
				if(enemy.get_enemy_Point().x <= mainCh.get_Hero_X_Point()){
					enemy.set_Move_Site(false);
					//���� �˹�ȿ��
					enemy.knockback(false);
				}
				//�˹� �ϴٰ� ���Ǻ��� �Ÿ��� �Ѿ�ԵǸ� �߶� ����
				if(enemy.get_enemy_Point().x >= enemy.get_Right_Bound_Site() ||
					enemy.get_enemy_Point().x + 30 <= enemy.get_Left_Bound_Site()){ //�������� �������� �������� ��������
					enemy.set_Down_Start_True();
				}
				weapon.set_Remove_Bullet_Choice(); //�浹 �Ǹ� �Ѿ��� ���¸� ���� ���·�
		}
	}
	//������ �׸��� �Լ�
	public void draw_Enemy(){
		//������ ���� �ݺ��Ͽ� �׸���
		for(int i=0; i<enemy_List.size(); i++){
			enemy = (Enemy) enemy_List.get(i);
			buffg.drawRect(enemy.get_enemy_Point().x,  enemy.get_enemy_Point().y, enemy.get_Enemy_Width(), enemy.get_Enemy_Height()); //�� ������ �� ��ġ, ũ��

			//�� ����
			if(enemy instanceof Walker){ //���ʹ��� ��Ŀ�� ��ü�� �ִٸ� �׷���
				//�¿���ȯ
				if(enemy.get_Move_Site())
					buffg.drawImage(enemy.get_Enemy_Img(), enemy.get_enemy_Point().x, enemy.get_enemy_Point().y, this);
				else
					buffg.drawImage(enemy.get_Enemy_Img(), enemy.get_enemy_Point().x + enemy.get_Enemy_Width(), enemy.get_enemy_Point().y,-enemy.get_Enemy_Width(),enemy.get_Enemy_Height(), this);
				buffg.setColor(Color.RED);
				buffg.fillRect(enemy.get_enemy_Point().x,  enemy.get_enemy_Point().y-10, enemy.get_Enemy_Width()*enemy.get_Enemy_HP()/10, 5); //�� ������ �� ��ġ, ũ��
				buffg.setColor(Color.BLACK);
			}
			if(enemy instanceof Walker2){ //���ʹ��� ��Ŀ�� ��ü�� �ִٸ� �׷���
				//�¿���ȯ
				if(enemy.get_Move_Site())
					buffg.drawImage(enemy.get_Enemy_Img(), enemy.get_enemy_Point().x + enemy.get_Enemy_Width(), enemy.get_enemy_Point().y,-enemy.get_Enemy_Width(),enemy.get_Enemy_Height(), this);
				else
					buffg.drawImage(enemy.get_Enemy_Img(), enemy.get_enemy_Point().x, enemy.get_enemy_Point().y, this);
				buffg.drawRect(enemy.get_enemy_Point().x,  enemy.get_enemy_Point().y, ((Walker2) enemy).get_Enemy_Width(),  ((Walker2) enemy).get_Enemy_Height()); //�簢������ �ϴ� ��ü
				buffg.setColor(Color.RED);
				buffg.fillRect(enemy.get_enemy_Point().x,  enemy.get_enemy_Point().y-10, enemy.get_Enemy_Width()*enemy.get_Enemy_HP()/10, 5); //�� ������ �� ��ġ, ũ��
				buffg.setColor(Color.BLACK);
			}
			
			//������ �����̴� �Լ� ȣ�� -> ��Ƽ������� ���� ������ ���ÿ� ���� Ŭ���� ������ �ڵ�����
			//ĳ���Ϳ� ���� �浹���� �Լ� ȣ��
			crash_Decide_Enemy(mainCh, enemy, enemy.get_Move_Site());
			//���ϰ� �����ϰ� �⵿���� ���� ���������������� �ϸ� �Ǳ� �Ѵ�.
			for(int j=0; j< stage.get_Block().size(); j++ ){
			crash_Decide_Enemy_Block(stage.get_Block().get(j), enemy);
			}
			//Ž�� ���� �׸���
			if(enemy.get_Move_Site()){ //�������� ����
					buffg.drawRect(enemy.get_Range_Site_Width_Left_Point() - enemy.get_Range_Site_Width_Right_Point(),  enemy.get_range_Site_Height_Top_Point(),
							enemy.get_Range_Site_Width_Right_Point(), enemy.get_range_Site_Height_Bottom_Point());	
			}else 
			if(!enemy.get_Move_Site()){ //�������� ����
					buffg.drawRect(enemy.get_Range_Site_Width_Left_Point(),  enemy.get_range_Site_Height_Top_Point(),
					enemy.get_Range_Site_Width_Right_Point(), enemy.get_range_Site_Height_Bottom_Point());
			}
			enemy.set_Hero_Information(mainCh);
		}
	}
	
	//���������� ���۵ɶ����� ���� ���ڸ� �Ķ���ͷ� �ް� �����ϰ� �ؾ� �� �� �ϴ�.
	public void enemy_Process(int stage_Num){
		if(stage_Num == 1){ //1�������� �϶� ���� ��ġ
			enemy_List.clear();
			enemy = new Walker(stage.get_Block().get(0).get_Left_Top_Point().x, 
					stage.get_Block().get(0).get_Left_Top_Point().x+stage.get_Block().get(0).get_Width()-600, 
					stage.get_Block().get(0).get_Left_Top_Point().y - 70); //20, width �� ��� ���� ����, height �� ���Ͱ� ��� �ִ� ���� ����
			enemy_List.add(enemy);
			enemy = new Walker2(stage.get_Block().get(0).get_Left_Top_Point().x, 
					stage.get_Block().get(0).get_Left_Top_Point().x+stage.get_Block().get(0).get_Width(), 
					stage.get_Block().get(0).get_Left_Top_Point().y - 70); //20, width �� ��� ���� ����, height �� ���Ͱ� ��� �ִ� ���� ����
			enemy_List.add(enemy);
			enemy = new Walker(stage.get_Block().get(1).get_Left_Top_Point().x, 
					stage.get_Block().get(1).get_Left_Top_Point().x + stage.get_Block().get(1).get_Width(), 
					stage.get_Block().get(1).get_Left_Top_Point().y - 70); //20, width �� ��� ���� ����, height �� ���Ͱ� ��� �ִ� ���� ����
			enemy_List.add(enemy);
			enemy = new Walker(stage.get_Block().get(2).get_Left_Top_Point().x, 
					stage.get_Block().get(2).get_Left_Top_Point().x + stage.get_Block().get(2).get_Width(), 
					stage.get_Block().get(2).get_Left_Top_Point().y - 70); //20, width �� ��� ���� ����, height �� ���Ͱ� ��� �ִ� ���� ����
			enemy_List.add(enemy);
			////////////////////////////////////////////////////////�� �ʿ� 1����������  ��Ŀ �κ� �߰�
		}
	}
	
	public void enemy_move_stop() {
		for(int i=0; i<enemy_List.size(); i++){
			enemy = (Enemy) enemy_List.get(i);
			enemy.enemy_stop(resumed);
		}
	}
	
	//�Ѿ��� �߻����ΰ� �ƴѰ� �Ѿ��� �����ϴ� �Լ�
	public void bullet_Process(){
		if(attack){ //���϶� �Ѿ��� �����Ѵ�.
			if(weapon_Number == 1 && (cnt-bulletch)>5){ //�����϶�
				pistol_Point = new Point(mainCh.get_Hero_X_Point(),mainCh.get_Hero_Y_Point()); //ĳ������ ���ݸ� �˾ƿͼ� ��ġ ������ ����
				weapon = new FireBack(pistol_Point, mainCh.get_Face_Side_LFET_RIGHT()); //ĳ������ ��ǥ ������ ������ �����Ѵ�.
				bullet_List.add(weapon); //������ ������ �Ҹ� ����Ʈ�� �ִ´� �� �����z Weapon Ŭ������ �Ǿ��ִ�.
				bulletch=cnt;
			}
			if(weapon_Number == 2 && (cnt-bulletch)>20){ //����2�϶�
				pistol_Point = new Point(mainCh.get_Hero_X_Point(),mainCh.get_Hero_Y_Point()); //ĳ������ ���ݸ� �˾ƿͼ� ��ġ ������ ����
				weapon = new Pistol(pistol_Point, mainCh.get_Face_Side_LFET_RIGHT()); //ĳ������ ��ǥ ������ ������ �����Ѵ�.
				bullet_List.add(weapon); //������ ������ �Ҹ� ����Ʈ�� �ִ´� �� �����z Weapon Ŭ������ �Ǿ��ִ�.
				bulletch=cnt;
			}
		}
		if(mine_attack){ //���϶� �Ѿ��� �����Ѵ�.
			if((cnt-bulletch)>50){ 
				pistol_Point = new Point(mainCh.get_Hero_X_Point(),mainCh.get_Hero_Y_Point()); //ĳ������ ���ݸ� �˾ƿͼ� ��ġ ������ ����
				weapon = new Mine(pistol_Point, mainCh.get_Face_Side_LFET_RIGHT()); //ĳ������ ��ǥ ������ ������ �����Ѵ�.
				bullet_List.add(weapon); //������ ������ �Ҹ� ����Ʈ�� �ִ´� �� �����z Weapon Ŭ������ �Ǿ��ִ�.
				bulletch=cnt;
				mine_attack = false;
			}
		}
	}
	
	//�Ѿ� ���� �Լ�
	public void remove_Bullet(Weapon weapon, int i){
		//x�࿡�� ȭ�� ������ �������� ����
		if(weapon.getPoint().x > buffw-30 || weapon.getPoint().x < 10 
				|| weapon.get_Remove_Bullet_Choice()){
			bullet_List.remove(i);
		}
		if(end_Stage) {
			bullet_List.clear();
		}
	}
	
	public void sendMessage(String msg) {
		out.println(msg);
	}
	
	public void sendLocation(Hero mainCh) {
		out.println(ChatProtocol.HEROSET+":"+
				mainCh.get_Hero_X_Point()+","+
				mainCh.get_Hero_Y_Point()+","+
				mainCh.get_Hero_Width()+","+
				mainCh.get_Hero_Height()+","+
				mainCh.get_Face_Side_LFET_RIGHT()+","+
				mainCh.get_Jump_State()+","+
				mainCh.get_Hero_ImgUrl());
	}

	public void routine(String line) {
		int idx = line.indexOf(':');
		String cmd = line.substring(0, idx);
		String data = line.substring(idx+1);
		if(cmd.equals(ChatProtocol.CHATLIST)) {
			//data - aaa;bbb;ccc;
			list.removeAll();
			list.add(listTitle);
			StringTokenizer st = 
					new StringTokenizer(data, ";");
			while(st.hasMoreTokens()) {
				String a = st.nextToken();
				list.add(a);
				Hero_List.add("0,0,0,0,false,true,"+this.getClass().getResource("/img/char2.gif"));
			}
		}else if(cmd.equals(ChatProtocol.CHAT)||
				cmd.equals(ChatProtocol.CHATALL)) {
			ta.append(data+"\n");
		}else if(cmd.equals(ChatProtocol.MESSAGE)) {
			idx = data.indexOf(';');
			cmd = data.substring(0, idx);
			data = data.substring(idx+1);
			new Message("FROM", cmd, data);
		}else if(cmd.equals(ChatProtocol.HEROSET)){
			idx = data.indexOf("[");
			int idx2 = data.indexOf("]");
			String User = data.substring(idx+1,idx2);
			String User_Move = data.substring(idx2+1);
			if(!User.equals(user))
			for (int i = 0; i < list.getItemCount()-1; i++) {
				String Item = list.getItem(i+1);
				if(Item.equals(User)) {
					Hero_List.replaceItem(User_Move, i);
				}
			}else if(mainCh.get_Jump_Hero()||Hero_move)sendLocation(mainCh);
		}
	}//routine
	
	public boolean filterMgr(String msg){
		boolean flag = false;//false�̸� ������ �ƴ�
		String str[] = {"�ٺ�","������","����","�ڹ�","java"};
		//���� ȣȣ ����
		StringTokenizer st = new StringTokenizer(msg);
		String msgs[] = new String[st.countTokens()];
		for (int i = 0; i < msgs.length; i++) {
			msgs[i] = st.nextToken();
		}
		for (int i = 0; i < str.length; i++) {
			if(flag) break;
			for (int j = 0; j < msgs.length; j++) {
				if(str[i].equals(msgs[j])){
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	class Message extends Frame implements ActionListener {

		Button send, close;
		TextField name;
		TextArea ta;
		String mode;// to/from
		String id;

		public Message(String mode) {
			setTitle("����������");
			this.mode = mode;
			id = list.getSelectedItem();
			layset("");
			validate();
		}
		public Message(String mode, String id, String msg) {
			setTitle("�����б�");
			this.mode = mode;
			this.id = id;
			layset(msg);
			validate();
		}
		public void layset(String msg) {
			 addWindowListener(new WindowAdapter() {
				   public void windowClosing(WindowEvent e) {
				    dispose();
				   }
			});
			Panel p1 = new Panel();
			p1.add(new Label(mode, Label.CENTER));
			name = new TextField(id, 20);
			p1.add(name);
			add(BorderLayout.NORTH, p1);
			
			ta = new TextArea("");
			add(BorderLayout.CENTER, ta);
			ta.setText(msg);
			Panel p2 = new Panel();
			if (mode.equals("TO:")) {
				p2.add(send = new Button("send"));
				send.addActionListener(this);
			}
			p2.add(close = new Button("close"));
			close.addActionListener(this);
			add(BorderLayout.SOUTH, p2);
			
			setBounds(200, 200, 250, 250);
			setVisible(true);
		}

		public void actionPerformed(ActionEvent e) {

			if(e.getSource()==send){
				sendMessage(ChatProtocol.MESSAGE+
						":"+id+";"+ ta.getText());
			}
			setVisible(false);
			dispose();
		}
	}

	class MDialog extends Dialog implements ActionListener{
		
		Button ok;
		Meet ct2;
		
		public MDialog(Meet ct2,String title, String msg) {
			super(ct2, title, true);
			this.ct2 = ct2;
			 //////////////////////////////////////////////////////////////////////////////////////////
			   addWindowListener(new WindowAdapter() {
			    public void windowClosing(WindowEvent e) {
			     dispose();
			    }
			   });
			   /////////////////////////////////////////////////////////////////////////////////////////
			   setLayout(new GridLayout(2,1));
			   Label label = new Label(msg, Label.CENTER);
			   add(label);
			   add(ok = new Button("Ȯ��"));
			   ok.addActionListener(this);
			   layset();
			   setVisible(true);
			   validate();
		}
		
		public void layset() {
			int x = ct2.getX();
			int y = ct2.getY();
			int w = ct2.getWidth();
			int h = ct2.getHeight();
			int w1 = 150;
			int h1 = 100;
			setBounds(x + w / 2 - w1 / 2, 
					y + h / 2 - h1 / 2, 200, 100);
		}

		public void actionPerformed(ActionEvent e) {
			tf.setText("");
			dispose();
		}
	}	
	//implement Runnable�� ���� ������ ������ 
	@Override
	public void run() {
		try{
			sock = new Socket("192.168.0.50",8002);
			in = new BufferedReader(
					new InputStreamReader(
							sock.getInputStream()));
			out = new PrintWriter(
					sock.getOutputStream(),true);
			ta.append(in.readLine()+"\n");
			while(true){
				String line = in.readLine();
//				System.out.println(line);
				routine(line);
				mainCh.move();//ĳ������ �������� �׻� üũ�Ѵ�.
				bullet_Process();//�Ѿ� ���� �Լ� ȣ��
				//���� ���� �żҵ�
				if(jump){
					mainCh.set_Hero_Jumping();
				}
				//ĳ���Ͱ� ����ִ� ������ ���̰� �Ǿ���Ѵ�.
				mainCh.jump_Move();
				repaint(); //ȭ���� ������ �ٽ� �׸���as
				Thread.sleep(20); //20milli sec �� ������ ������
				cnt++;
			}
		}catch (Exception e) {
			e.printStackTrace();
			ta.append("���� �Ѱ� �ٽ�"+"\n");
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////
	//Ű �ڵ鷯
	@Override
	//Ű���尡 ���������� �̺�Ʈ ó���ϴ� ��
	public void keyPressed(KeyEvent e) {
		Hero_move=true;
		sendLocation(mainCh);
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP :
			if(resumed) {
				if(b==20) {
					b=0;
					a=20;
				}
				if(c==20) {
					c=0;
					b=20;
				}
				if(d==20) {
					d=0;
					c=20;
				}
			}
			break;
		case KeyEvent.VK_DOWN :
			if(resumed) {
				if(c==20) {
					c=0;
					d=20;
				}
				if(b==20) {
					b=0;
					c=20;
				}
				if(a==20) {
					a=0;
					b=20;
				}
			}
			break;
		case KeyEvent.VK_LEFT :
			mainCh.set_Hero_X_Left();
			break;
		case KeyEvent.VK_RIGHT :
			mainCh.set_Hero_X_Right();
			break;
		case KeyEvent.VK_A :
			//�������϶� �Ѿ��� �����Ѵ�.
			attack = true;
			break;
		case KeyEvent.VK_S :
			//���� �����Ѵ�.
			jump = true;
			break;
		case KeyEvent.VK_D :
			//Į��..
			mine_attack = true;
			break;
		case KeyEvent.VK_ESCAPE:
			resumed=!resumed;
			enemy_move_stop();
			break;
		}
	}

	@Override
	//Ű���尡 �������ٰ� ���������� �̺�Ʈ ó���ϴ� ��
	public void keyReleased(KeyEvent e) {
		Hero_move=false;
		sendLocation(mainCh);
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP :
			break;
		case KeyEvent.VK_DOWN :
			break;
		case KeyEvent.VK_LEFT :
			mainCh.set_Hero_X_Left_Stop();
			break;
		case KeyEvent.VK_RIGHT :
			mainCh.set_Hero_X_Right_Stop();
			break;
		case KeyEvent.VK_A :
			//�Ѿ˻����� �����Ѵ�..
			attack = false;
			break;
		case KeyEvent.VK_S :
			//���� ����
			jump = false;
			break;
		case KeyEvent.VK_COMMA :
			//���� ����
			ta.setVisible(!ta.isVisible());
			this.revalidate();
			break;
		case KeyEvent.VK_ENTER:
			if(resumed) {
				if(a==20) {
					resumed=!resumed;
					enemy_move_stop();
				}
				if(b==20) {
				}
				if(c==20) {
				}
				if(d==20) {
					dispose();
					Main menu = new Main();
				}
			}
			else {
				tf.requestFocus();
				break;
			}
		}
	}

	@Override
	//Ű���尡 Ÿ���� �� �� �̺�Ʈ ó���ϴ� ��
	public void keyTyped(KeyEvent e) {
		sendLocation(mainCh);
	}
	///////////////////////////////////////////////////////////////////////////////////////////
}