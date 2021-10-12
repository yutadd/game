package yeah;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Game extends Thread implements MouseListener,KeyListener{
	static JFrame frame =null;
	static JPanel panel=null;
	static int score=0;
	static boolean sagyou=false;
	static ImageIcon bg_sb=null;
	static JLabel sb;
	static JLabel sb_sc=null;
	static int[] line= {0,0,0,0};
	static JTextArea ziki=null;
	static HashMap<JTextArea,Vector> ziki_class=new HashMap<JTextArea,Vector>();
	static boolean isClicking=false;
	static HashMap<JTextArea,Vector> area=new HashMap<JTextArea,Vector>();

	public static void main(String[] args) {
		init();
		System.out.println("inited");
		Thread th=new Thread(new Game());
		th.run();
		try {
			th.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void init() {
		bg_sb=new ImageIcon("scoreboard_t.png");
		frame=new JFrame("いぇあ");
		frame.setUndecorated(true);
		frame.setBounds(0,0,Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height);
		panel=new JPanel() {
			@Override
			public void paint(Graphics g) {
				// TODO 自動生成されたメソッド・スタブ
				panel.getGraphics().drawLine(line[0],line[1],line[2],line[3]);
				super.paint(g);
			}
		};
		panel.setBounds(0,0,Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height);
		panel.setLayout(null);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		panel.addMouseListener(new Game());
		sb=new JLabel();
		sb.setBounds(1400,0,550,100);
		sb.setLayout(null);
		sb.setIcon(bg_sb);
		sb_sc=new JLabel();
		sb_sc.setBounds(100,0,550,100);
		sb_sc.setText("yeah!");
		sb_sc.setOpaque(false);
		sb_sc.setFont(new Font("メイリオ",Font.PLAIN,50));
		panel.add(sb);
		sb.add(sb_sc);
		ziki=new JTextArea();
		ziki.setText("〇");
		ziki.setFont(new Font("メイリオ",Font.PLAIN,100));
		ziki.setForeground(java.awt.Color.red);
		ziki.setSize(120,110);
		ziki.setOpaque(false);
		ziki.setEditable(false);
		frame.addKeyListener(new Game());
		ziki_class.put(ziki,new Vector(0,0));
	}



	public void run() {
		Random rd=new Random();
		panel.add(ziki);
		ArrayList<Entry<JTextArea, Vector>> rm=new ArrayList<>();
		while(true){
			try {
				if(frame.isActive()) {
					line[0]	= (int)panel.getMousePosition().getX();
					line[1]=(int)panel.getMousePosition().getY();
					line[2]=(int) ziki.getLocation().getX()+50;
					line[3]=(int) ziki.getLocation().getY()+50;
				}
			}catch(Exception e) {
				//e.printStackTrace();
			}
			sb_sc.setText("SCORE : "+String.valueOf(score));
			panel.getGraphics().setColor(new Color(0,0,0));
			//panel.getGraphics().drawLine((int)panel.getMousePosition().getX(), (int)panel.getMousePosition().getY(), (int)ziki.getLocation().getX()+50,(int) ziki.getLocation().getY()+50);
			try {
				ziki_class.get(ziki).x=((int)(panel.getMousePosition().getX()-ziki.getLocation().getX()))/10;
				ziki_class.get(ziki).y=((int)((panel.getMousePosition().getY()+100)-ziki.getLocation().getY()))/10;
			}catch(Exception e) {}
			JTextArea a=new JTextArea();
			a.setText("〇");
			a.setOpaque(false);
			a.setFont(new Font("メイリオ",Font.PLAIN,50));
			a.setLocation(rd.nextInt(1920),0);
			a.setSize(50,80);
			a.setEditable(false);
			a.setForeground(new Color(50,50,50));
			panel.add(a);
			area.put(a,new Vector(0,rd.nextInt(5)+5));
			if(sagyou)continue;
			for(Entry<JTextArea, Vector> b:area.entrySet()) {
				if(sagyou)break;
				if((b.getKey().getX()-40<ziki.getX())&&(ziki.getX()<b.getKey().getX()+40)) {//X_check
					if((b.getKey().getY()-40<ziki.getY())&&(ziki.getY()<b.getKey().getY()+40)) {//Y_check
						score++;
					}
				}
				if(b.getKey().getLocation().y>1080) {
					rm.add(b);

				}
				b.getKey().setLocation(b.getKey().getX()+b.getValue().x,b.getKey().getY()+b.getValue().y);
			}
			for(Entry<JTextArea, Vector> aa:rm) {
				area.remove(aa.getKey(),aa.getValue());
				panel.remove(aa.getKey());
			}

			for(Entry<JTextArea, Vector> e:ziki_class.entrySet()) {
				e.getKey().setLocation(e.getKey().getX()+e.getValue().x,e.getKey().getY()+e.getValue().y);
			}

			panel.repaint();
			try {
				Thread.sleep(20);
			}catch(Exception e) {

			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
	}
	@Override
	public void mousePressed(MouseEvent e) {

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("key");
		ArrayList<Entry<JTextArea, Vector>> rm=new ArrayList<>();
		sagyou=true;
		for(Entry<JTextArea, Vector> b:area.entrySet()) {
			if((b.getKey().getX()-300<ziki.getX())&&(ziki.getX()<b.getKey().getX()+300)) {//X_check
				if((b.getKey().getY()-300<ziki.getY())&&(ziki.getY()<b.getKey().getY()+300)) {//Y_check
					rm.add(b);
				}
			}
		}
		for(Entry<JTextArea,Vector> aa:rm) {
			area.remove(aa.getKey(),aa.getValue());
			panel.remove(aa.getKey());
		}
		sagyou=false;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
