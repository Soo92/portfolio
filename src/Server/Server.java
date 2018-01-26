package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {

	Vector<ClientThread2> vc;
	ServerSocket server;
	
	public Server() {
		try {
			server = new ServerSocket(8002);
			vc = new Vector<ClientThread2>();
		} catch (Exception e) {
			System.err.println("Error in Server");
			e.printStackTrace();
			System.exit(1);//���������� ����
		}
		System.out.println("****************************");
		System.out.println("Ŭ���̾�Ʈ�� ��ٸ��� �ֽ��ϴ�.");
		System.out.println("****************************");
		try {
			while(true){	
				Socket sock = server.accept();
				ClientThread2 ct = new ClientThread2(sock);
				ct.start();
				vc.add(ct);
			}
		} catch (Exception e) {
			System.err.println("Error in Socket");
			e.printStackTrace();
		}
	}
	
	//���ӵ� ��� Ŭ���̾�Ʈ���� �޼��� ����
	public void sendAllMessage(String msg){
		for (int i = 0; i < vc.size(); i++) {
			ClientThread2 ct = vc.get(i);
			ct.sendMessage(msg);
		}
	}

	//���ӵ� ��� Ŭ���̾�Ʈ���� �޼��� ����
	public void sendAllLocation(String msg){
		for (int i = 0; i < vc.size(); i++) {
			ClientThread2 ct = vc.get(i);
			ct.sendLocation(msg);
		}
	}

	//Vector�� Client�� ����
	public void removeClient(ClientThread2 ct){
		vc.remove(ct);
	}
	
	class ClientThread2 extends Thread{
		
		Socket sock;
		BufferedReader in;
		PrintWriter out;
		String id = "�͸�";
		
		public ClientThread2(Socket sock) {
			try {
				this.sock = sock;
				in = new BufferedReader(
						new InputStreamReader(
								sock.getInputStream()));
				out = new PrintWriter(
						sock.getOutputStream(),true);
				System.out.println(sock+" ���ӵ�....");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			try {
				out.println("����Ͻ� ���̵� �Է��ϼ���.");
				while(true) {
					String line = in.readLine();
					System.out.println(line);
					if(line==null)
						break;
					else
						routine(line);
				}
			} catch (Exception e) {
				removeClient(this);
				sendAllMessage(ChatProtocol2.CHATLIST+
						":"+getIds());
				System.err.println(sock +"["+id+"] ��������.");
				//e.printStackTrace();
			}
		}
		
		//Client���� ���۵� Protocol�� �м� �޼ҵ�
		public void routine(String line){
			System.out.println("line : " + line);
			//CHATALL:������ �� �ɴϴ�. �Ӱ� ����������?
			int idx = line.indexOf(':');
			String cmd/*CHATALL*/ = line.substring(0, idx);
			String data/*������...*/ = line.substring(idx+1);
			if(cmd.equals(ChatProtocol2.ID)) {
				//ID:ȫ�浿
				if(data!=null&&data.length()>0) {
					id = data;
					//���ο� ������ �߰� �Ǿ��� ������ ����Ʈ ������
					sendAllMessage(ChatProtocol2.CHATLIST+
							":"+getIds());
					sendAllMessage(ChatProtocol2.CHATALL+
							":"+"["+id+"]���� �����Ͽ����ϴ�.");
				}
			}else if(cmd.equals(ChatProtocol2.CHAT)) {
				idx = data.indexOf(';');
				cmd = data.substring(0, idx);
				data = data.substring(idx+1);
				ClientThread2 ct = findClient(cmd);
				if(ct!=null) {
					ct.sendMessage(ChatProtocol2.CHAT+":"+
							"["+id+"(��)]"+data);
				}else {
					sendMessage(ChatProtocol2.CHAT+":"+
							"["+cmd+"] �����ڰ� �ƴմϴ�.");
				}
			}else if(cmd.equals(ChatProtocol2.MESSAGE)) {
				idx = data.indexOf(';');
				cmd = data.substring(0, idx);
				data = data.substring(idx+1);
				ClientThread2 ct = findClient(cmd);
				if(ct!=null) {
					ct.sendMessage(ChatProtocol2.MESSAGE+":"+
							 id +";"+data);
				}else {
					//���ڽſ��� ����
					sendMessage(ChatProtocol2.CHAT+":"+
							"["+cmd+"] �����ڰ� �ƴմϴ�.");
				}
			}else if(cmd.equals(ChatProtocol2.CHATALL)) {
				sendAllMessage(ChatProtocol2.CHATALL+
						":"+"["+id+"]"+ data);
			}else {sendAllLocation(ChatProtocol2.HEROSET+
					":"+"["+id+"]"+ data);}
		}//routine

		//�Ű������� ���� id������ ClientThread2�� ã�´�.
		public ClientThread2 findClient(String id){
			ClientThread2 ct = null;
			for (int i = 0; i < vc.size(); i++) {
				ct = vc.get(i);
				if(ct.id.equals(id))
					break;
			}
			return ct;
		}
		
		//���ӵ� ��� id�� ����(; ���� - ex)aaa;bbb;ȫ�浿;)
		public String getIds() {
			String ids = "";
			for (int i = 0; i < vc.size(); i++) {
				ClientThread2 ct = vc.get(i);
				ids+=ct.id+";";
			}
			return ids;
		}
	
		public void sendMessage(String msg){
			out.println(msg);
		}
		
		public void sendLocation(String Location){
			out.println(Location);
		}
	}
	
	public static void main(String[] args) {
		new Server();
	}
}