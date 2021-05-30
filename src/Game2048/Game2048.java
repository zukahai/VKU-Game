package Game2048;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import javax.swing.*;

public class Game2048 extends JFrame implements ActionListener,KeyListener{
	Color cl[] = new Color[20];
	int i,j,q,w,k;
	private Container cn;
	private JPanel pn1,pn2,pn3;
	private JTextField tf1,tf2;
	private JLabel lb1,lb2;
	private JButton b[][] = new JButton[6][6],newgame,quitgame;
	private JButton highScore_bt;
	public Game2048 (String high) {
		super("VKU - Game 2048");
		cn = this.getContentPane();
		lb1 = new JLabel("");
		lb2 = new JLabel("YOUR SCORE:");
		lb1.setForeground(Color.red);
		lb2.setForeground(Color.blue);
		tf1 = new JTextField(high);
		tf2 = new JTextField("000");
		tf1.setEditable(false);
		tf2.setEditable(false);
		
		highScore_bt = new JButton("High Score");
		highScore_bt.addActionListener(this);
		
		pn1 = new JPanel();
		pn1.setLayout(new GridLayout(2,2));
		pn1.add(lb1);
		pn1.add(highScore_bt);
		pn1.add(lb2);
		pn1.add(tf2);
		pn2 = new JPanel();
		pn2.setLayout(new GridLayout(4,4));
		for (i=0;i<=5;i++)
			for (j=0;j<=5;j++) {
				b[i][j] = new JButton("");
				b[i][j].addActionListener(this);
				b[i][j].addKeyListener(this);
				b[i][j].setActionCommand(i+" "+j);
				b[i][j].setBackground(Color.LIGHT_GRAY);
				b[i][j].setFont(new Font("Arial",Font.PLAIN,31));
			}
		for (i=1;i<=4;i++)
			for (j=1;j<=4;j++)
				pn2.add(b[i][j]);
		i = (int) (3*Math.random()+1);
		j = (int) (3*Math.random()+1);
		q = (int) (3*Math.random()+1);
		w = (int) (3*Math.random()+1);
		while (i==q&&j==w) {
			q = (int) (3*Math.random()+1);
			w = (int) (3*Math.random()+1);
		}
		b[i][j].setText("2");
		b[i][j].setBackground(Color.white);
		b[q][w].setText("2");
		b[q][w].setBackground(Color.white);
		newgame = new JButton("New Game");
		newgame.addActionListener(this);
		newgame.addKeyListener(this);
		newgame.setBackground(Color.green);
		quitgame = new JButton("Quit Game");
		quitgame.addActionListener(this);
		quitgame.setBackground(Color.GREEN);
		pn3 = new JPanel();
		pn3.setLayout(new FlowLayout());
		pn3.add(newgame);
		pn3.add(quitgame);
		cn.add(pn1, "North");
		cn.add(pn2);
		cn.add(pn3,"South");
		addKeyListener(this);
		this.setVisible(true);
		this.setSize(500,574);
		this.setLocationRelativeTo(null);
		cn.addKeyListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand()=="Quit Game") System.exit(0);
		else if (e.getActionCommand().equals(highScore_bt.getText())) {
			new HighScore();
		}
		else if (e.getActionCommand()=="New Game") {
			new Game2048("000");
			this.dispose();
		} else {
			String s = e.getActionCommand();
			int k = s.indexOf(32);
			i = Integer.parseInt(s.substring(0, k));
			j = Integer.parseInt(s.substring(k+1,s.length()));
		}
	}
	public void keyPressed(KeyEvent e) {
		cl[0]= Color.WHITE;
		cl[1]= Color.pink;
		cl[2]= Color.yellow;
		cl[3]= Color.GREEN;
		cl[4]= Color.magenta;
		cl[5]= Color.orange;
		cl[6]= Color.CYAN;
		cl[7]=Color.RED;
		if (e.getKeyCode()==KeyEvent.VK_DOWN) {
			boolean kt = false;
			for (i=1;i<=3;i++)
				for (j=1;j<=4;j++) {
					if (b[i][j].getText()!=""&&b[i+1][j].getText()=="") kt = true;
					if (b[i][j].getText()!="" && b[i+1][j].getText()!="" && Integer.parseInt(b[i][j].getText())==Integer.parseInt(b[i+1][j].getText())) kt = true;
				}
			for (j=1;j<=4;j++) {
				k=4;
				for (i=4;i>=1;i--) {
					if (b[i][j].getText()!="") {
						b[k][j].setText(b[i][j].getText()); 
						int o = Integer.parseInt(b[k][j].getText()),index=0;
						while (o!=2) {
							o/=2;
							index++;
						}
						if (index == 8) index++;
						b[k][j].setBackground(cl[index%8]);
						k--;
					}
				}
				for (i = k;i>=1;i--) {
					b[i][j].setText("");
					b[i][j].setBackground(Color.LIGHT_GRAY);
				}
			}
			for (j=1;j<=4;j++) {
				k=4;
				while (k>=2 && b[k][j].getText()!="" && b[k-1][j].getText()!="") {
					if (Integer.parseInt(b[k][j].getText())==Integer.parseInt(b[k-1][j].getText())) {
						b[k][j].setText(String.valueOf(2*Integer.parseInt(b[k][j].getText())));
						int o = Integer.parseInt(b[k][j].getText()),index=0;
						tf2.setText(String.valueOf(Integer.parseInt(tf2.getText())+o));
						while (o!=2) {
							o/=2;
							index++;
						}
						if (index == 8) index++;
						b[k][j].setBackground(cl[index%8]);
						for (i=k-1;i>=1;i--) {
							b[i][j].setText(b[i-1][j].getText());
							b[i][j].setBackground(b[i-1][j].getBackground());
						}
					}
					k--;
				}
			}
			if (kt) {
				k = (int) Math.round((2*Math.random()));
				if (k==0) {
					for (j=1;j<=4;j++) 
						if (b[1][j].getText()=="") break;
				}
				else
					if (k==1) {
						for (j=4;j>=1;j--) 
							if (b[1][j].getText()=="") break;	
					}
					else {
						for (j=2;j<=4;j++) 
							if (b[1][j].getText()=="") break;
						if (j==5)
						for (j=3;j>=1;j--) 
							if (b[1][j].getText()=="") break;
					}
				if (Math.random()<0.07) {
					b[1][j].setText("4");
					b[1][j].setBackground(Color.pink);
				}
				else
				{
					b[1][j].setText("2");
					b[1][j].setBackground(Color.white);
				}
				if (Math.random()<0.4&&b[2][j].getText()=="") {
					b[2][j].setText(b[1][j].getText());
					b[2][j].setBackground(b[1][j].getBackground());
					b[1][j].setText("");
					b[1][j].setBackground(Color.LIGHT_GRAY);
				}
			}
		}
		
		if (e.getKeyCode()==KeyEvent.VK_UP) {
			boolean kt = false;
			for (i=2;i<=4;i++)
				for (j=1;j<=4;j++) {
					if (b[i][j].getText()!=""&&b[i-1][j].getText()=="") kt = true;
					if (b[i][j].getText()!="" && b[i-1][j].getText()!="" && Integer.parseInt(b[i][j].getText())==Integer.parseInt(b[i-1][j].getText())) kt = true;
				}
			for (j=1;j<=4;j++) {
				k=1;
				for (i=1;i<=4;i++) {
					if (b[i][j].getText()!="") {
						int o = Integer.parseInt(b[i][j].getText()),index=0;
						while (o!=2) {
							o/=2;
							index++;
						}
						if (index == 8) index++;
						b[k][j].setText(b[i][j].getText());
						b[k][j].setBackground(cl[index%8]);
						k++;
					}
				}
				for (i = k;i<=4;i++) {
					b[i][j].setText("");
					b[i][j].setBackground(Color.LIGHT_GRAY);
				}
			}
			for (j=1;j<=4;j++) {
				k=1;
				while (k<=3 && b[k][j].getText()!="" && b[k+1][j].getText()!="") {
					if (Integer.parseInt(b[k][j].getText())==Integer.parseInt(b[k+1][j].getText())) {
						b[k][j].setText(String.valueOf(2*Integer.parseInt(b[k][j].getText())));
						int o = Integer.parseInt(b[k][j].getText()),index=0;
						tf2.setText(String.valueOf(Integer.parseInt(tf2.getText())+o));
						while (o!=2) {
							o/=2;
							index++;
						}
						if (index == 8) index++;
						b[k][j].setBackground(cl[index%8]);
						for (i=k+1;i<=4;i++) {
							b[i][j].setText(b[i+1][j].getText());
							b[i][j].setBackground(b[i+1][j].getBackground());
						}
					}
					k++;
				}
			}
			if (kt) {
				k = (int) Math.round((2*Math.random()));
				if (k==0) {
					for (j=1;j<=4;j++) 
						if (b[4][j].getText()=="") break;
				}
				else
					if (k==1) {
						for (j=4;j>=1;j--) 
							if (b[4][j].getText()=="") break;	
					}
					else {
						for (j=2;j<=4;j++) 
							if (b[4][j].getText()=="") break;
						if (j==5)
						for (j=3;j>=1;j--) 
							if (b[4][j].getText()=="") break;
					}
				if (Math.random()<0.07) {
					b[4][j].setText("4");
					b[4][j].setBackground(Color.pink);
				}
				else
				{
					b[4][j].setText("2");
					b[4][j].setBackground(Color.white);
				}
				if (Math.random()<0.4&&b[3][j].getText()=="") {
					b[3][j].setText(b[1][j].getText());
					b[3][j].setBackground(b[1][j].getBackground());
					b[4][j].setText("");
					b[4][j].setBackground(Color.LIGHT_GRAY);
				}
			}
		}
		if (e.getKeyCode()==KeyEvent.VK_LEFT) {
			boolean kt = false;
			for (i=1;i<=4;i++)
				for (j=2;j<=4;j++) {
					if (b[i][j].getText()!="" && b[i][j-1].getText()=="") kt = true;
					if (b[i][j].getText()!="" && b[i][j-1].getText()!="" && Integer.parseInt(b[i][j].getText())==Integer.parseInt(b[i][j-1].getText())) kt = true;
				}
			for (i=1;i<=4;i++) {
				k=1;
				for (j=1;j<=4;j++) {
					if (b[i][j].getText()!="") {
						b[i][k].setText(b[i][j].getText());
						int o = Integer.parseInt(b[i][j].getText()),index=0;
						while (o!=2) {
							o/=2;
							index++;
						}
						if (index == 8) index++;
						b[i][k].setBackground(cl[index%8]);
						k++;
					}
				}
				for (j = k;j<=4;j++) {
					b[i][j].setText("");
					b[i][j].setBackground(Color.LIGHT_GRAY);
				}
			}
			for (i=1;i<=4;i++) {
				k=1;
				while (k<=3 && b[i][k].getText()!="" && b[i][k+1].getText()!="") {
					if (Integer.parseInt(b[i][k].getText())==Integer.parseInt(b[i][k+1].getText())) {
						b[i][k].setText(String.valueOf(2*Integer.parseInt(b[i][k].getText())));
						int o = Integer.parseInt(b[i][k].getText()),index=0;
						tf2.setText(String.valueOf(Integer.parseInt(tf2.getText())+o));
						while (o!=2) {
							o/=2;
							index++;
						}
						if (index == 8) index++;
						b[i][k].setBackground(cl[index%8]);
						for (j=k+1;j<=4;j++) {
							b[i][j].setText(b[i][j+1].getText());
							b[i][j].setBackground(b[i][j+1].getBackground());
						}
					}
					k++;
				}
			}
			if (kt) {
				k = (int) Math.round((2*Math.random()));
				if (k==0) {
					for (i=1;i<=4;i++) 
						if (b[i][4].getText()=="") break;
				}
				else
					if (k==1) {
						for (i=4;i>=1;i--) 
							if (b[i][4].getText()=="") break;	
					}
					else {
						for (i=2;i<=4;i++) 
							if (b[i][4].getText()=="") break;
						if (i==5)
						for (i=3;i>=1;i--) 
							if (b[i][4].getText()=="") break;
					}
				if (Math.random()<0.07) {
					b[i][4].setText("4");
					b[i][4].setBackground(Color.pink);
				}
				else
				{
					b[i][4].setText("2");
					b[i][4].setBackground(Color.white);
				}
				if (Math.random()<0.4&&b[i][3].getText()=="") {
					b[i][3].setText(b[i][4].getText());
					b[i][3].setBackground(b[i][4].getBackground());
					b[i][4].setText("");
					b[i][4].setBackground(Color.LIGHT_GRAY);
				}
			}
		}
		if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
			boolean kt = false;
			for (i=1;i<=4;i++)
				for (j=1;j<=3;j++) {
					if (b[i][j].getText()!="" && b[i][j+1].getText()=="") kt = true;
					if (b[i][j].getText()!="" && b[i][j+1].getText()!="" && Integer.parseInt(b[i][j].getText())==Integer.parseInt(b[i][j+1].getText())) kt = true;
				}
			for (i=1;i<=4;i++) {
				k=4;
				for (j=4;j>=1;j--) {
					if (b[i][j].getText()!="") {
						b[i][k].setText(b[i][j].getText());
						int o = Integer.parseInt(b[i][k].getText()),index=0;
						while (o!=2) {
							o/=2;
							index++;
						}
						if (index == 8) index++;
						b[i][k].setBackground(cl[index%8]);
						k--;
					}
				}
				for (j = k;j>=1;j--) {
					b[i][j].setText("");
					b[i][j].setBackground(Color.LIGHT_GRAY);
				}
			}
			for (i=1;i<=4;i++) {
				k=4;
				while (k>=2 && b[i][k].getText()!="" && b[i][k-1].getText()!="") {
					if (Integer.parseInt(b[i][k].getText())==Integer.parseInt(b[i][k-1].getText())) {
						b[i][k].setText(String.valueOf(2*Integer.parseInt(b[i][k].getText())));
						int o = Integer.parseInt(b[i][k].getText()),index=0;
						tf2.setText(String.valueOf(Integer.parseInt(tf2.getText())+o));
						while (o!=2) {
							o/=2;
							index++;
						}
						if (index == 8) index++;
						b[i][k].setBackground(cl[index%8]);
						for (j=k-1;j>=1;j--) {
							b[i][j].setText(b[i][j-1].getText());
							b[i][j].setBackground(b[i][j-1].getBackground());
						}
					}
					k--;
				}
			}
			if (kt) {
				k = (int) Math.round((2*Math.random()));
				if (k==0) {
					for (i=1;i<=4;i++) 
						if (b[i][1].getText()=="") break;
				}
				else
					if (k==1) {
						for (i=4;i>=1;i--) 
							if (b[i][1].getText()=="") break;	
					}
					else {
						for (i=2;i<=4;i++) 
							if (b[i][1].getText()=="") break;
						if (i==5)
						for (i=3;i>=1;i--) 
							if (b[i][1].getText()=="") break;
					}
				if (Math.random()<0.07) {
					b[i][1].setText("4");
					b[i][1].setBackground(Color.pink);
				}
				else
				{
					b[i][1].setText("2");
					b[i][1].setBackground(Color.white);
				}
				if (Math.random()<0.4&&b[i][2].getText()=="") {
					b[i][2].setText(b[i][4].getText());
					b[i][2].setBackground(b[i][4].getBackground());
					b[i][1].setText("");
					b[i][1].setBackground(Color.LIGHT_GRAY);
				}

			}
		}
		boolean lose = true;
		for (i=1;i<=4;i++) 
		for (j=1;j<=4;j++)
			if (b[i][j].getText()=="") lose = false;
		if (lose)
		for (i=1;i<=4;i++)
			for (j=1;j<=3;j++)
				if (Integer.parseInt(b[i][j].getText())==Integer.parseInt(b[i][j+1].getText())) lose =false;
		if (lose)
		for (i=1;i<=3;i++)
			for (j=1;j<=4;j++)
				if (Integer.parseInt(b[i][j].getText())==Integer.parseInt(b[i+1][j].getText())) lose =false;
		if (lose) {
			lb1.setText("GAME");
			lb2.setText("OVER");
			tf2.setBackground(Color.GREEN);
			for (i=1;i<=4;i++)
				for (j=1;j<=4;j++)
					b[i][j].setEnabled(false);
			String str = JOptionPane.showInputDialog("Điểm của bạn là: " + tf2.getText() + "\n" + "Nhập tên (và trường) của bạn để lưu lại số điểm này nhé\n" +
					"(Tiếng Việt không dấu)");
			String sdt = "";
			String checkSdt = "^0[0-9]{9}";
			do {
				sdt = JOptionPane.showInputDialog("Nhập số điện thoại của bạn:\n(Dùng để trao quà)");
				if (!sdt.matches(checkSdt))
					JOptionPane.showMessageDialog(null, "Số điện thoại không đúng\n" + "Vui lòng nhập lại");
			} while(!sdt.matches(checkSdt));
			JOptionPane.showMessageDialog(null, "Đã lưu điểm");
			str = str.replace(" ", "%20");
//					System.out.println("http://localhost/vku/HighScore/snakeGame?vkuName=" + str + "&score=" + score_bt.getText());
			try {
				URL url = new URL("https://haizukon.000webhostapp.com/HighScore/Game2048?vkuName=" + str + "&score=" + tf2.getText() + "&sdt=" + sdt);
				URLConnection urlConnection = url.openConnection();
				HttpURLConnection connection = null;
				if(urlConnection instanceof HttpURLConnection) {
					connection = (HttpURLConnection) urlConnection;
				}
				BufferedReader in = new BufferedReader(
				new InputStreamReader(connection.getInputStream()));
//						System.out.println(url);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
		if (Integer.parseInt(tf2.getText())>Integer.parseInt(tf1.getText())) tf1.setText(tf2.getText());
	}
	
	public void keyReleased(KeyEvent e) {
		
	}
	public void keyTyped(KeyEvent e) {
		
	}
	
	public static void main(String[] args) {
		new Game2048("000");
	}
}