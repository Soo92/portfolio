package mainframe;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;


import javax.swing.ImageIcon;

import mainframe.MemberBean;
import mainframe.MemberMgr;

public class MainPanel extends MFrame implements ActionListener{
	
	Toolkit tk = Toolkit.getDefaultToolkit(); 	//�̹����� �ҷ����� ���� ��Ŷ

	Image buffImg;
	Graphics buffi;
	Image background = tk.getImage(this.getClass().getResource("/img/login.gif"));
	ImageIcon a = new ImageIcon(background);

	TextField idTx, pwTx;
	Label idl, pwl, msgl;
	Button logBtn, makeBtn, offBtn;
	MemberMgr mgr;
	Socket sock;
	BufferedReader in;
	PrintWriter out;
	String id;

	public MainPanel() {
		mgr = new MemberMgr();
		buffImg = createImage(a.getIconWidth(),a.getIconHeight());
		buffi = buffImg.getGraphics();
		setTitle("LOGIN");
		setSize(a.getIconWidth(), a.getIconHeight()+75);
					
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();		//�������� �����쿡 ǥ�ñ��� ��ġ�� �����ϱ� ����.
		int focus_X = (int)(screen.getWidth() / 2 - 400 / 2);
		int focus_Y = (int)(screen.getHeight() / 2 - 450 / 2);
		setLayout(null);
		setLocation(focus_X, focus_Y); //�������� ȭ�鿡 ��ġ

		idl = new Label("ID");
		pwl = new Label("PASS");
		idTx = new TextField("aaa");
		pwTx = new TextField("1234");
		logBtn = new Button("�α���");
		makeBtn = new Button("ȸ������");
		offBtn = new Button("��������");
		msgl = new Label("ID�� PASS�� �Է��ϼ���.");
		idl.setBounds(150, 180, 50, 20);
		idTx.setBounds(200, 180, 100, 20);
		pwl.setBounds(150, 210, 50, 20);
		pwTx.setBounds(200, 210, 100, 20);
		logBtn.setBounds(125, 250, 60, 30);
		makeBtn.setBounds(190, 250, 60, 30);
		offBtn.setBounds(255, 250, 60, 30);
		msgl.setBounds(155, 280, 150, 40);
		logBtn.addActionListener(this);
		makeBtn.addActionListener(this);
		offBtn.addActionListener(this);;
		add(idl);
		add(idTx);
		add(pwl);
		add(pwTx);
		add(logBtn);
		add(makeBtn);
		add(offBtn);
		add(msgl);

		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		try {
			buffi.drawImage(background, 0, 0, this);			
			buffi.setColor(Color.DARK_GRAY);
			buffi.setFont(new Font("����", Font.BOLD, 50));
			buffi.drawString("ȸ��", 170, 150);
		} catch (Exception e) {
		}
		g.drawImage(buffImg, 0, 0, this);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		MemberBean bean = new MemberBean();
			if (obj == logBtn) { 
				// �ߺ� üũ
				bean.setId(idTx.getText());
				bean.setPass(pwTx.getText());
				if(mgr.checkID(bean)) {
					msgl.setForeground(Color.red);
					msgl.setText("ID�� Ȯ�����ּ���.");
				}else if(mgr.checkPass(bean)){
					msgl.setForeground(Color.red);
					msgl.setText("PW�� Ȯ�����ּ���.");
				}else	{
					msgl.setForeground(Color.BLACK);
					msgl.setText(bean.getId()+"�� ȯ���մϴ�!");
					dispose();
					new Main();
				}
			}

			else if (obj == makeBtn) {
				bean.setId(idTx.getText());
				bean.setPass(pwTx.getText());
				if(mgr.checkID(bean)) {
					msgl.setForeground(Color.BLACK);
					msgl.setText(bean.getId()+"�� ȯ���մϴ�!");
					mgr.insertMember(bean);
				}else {
					msgl.setText("��� ���� ID�Դϴ�.");
				}
			}
	}		
	public void main() {
		new MainPanel();
	}
}