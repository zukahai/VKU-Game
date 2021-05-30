package GameTicTacTo;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

public class GameTicTacToe extends JFrame implements ActionListener {
	boolean win = false;
	public String icon;
	int point1 = 0, point2 = 0;
	int start = 0;
	String str = "";
	int count;
	int countH[] = {0, 0};
	String text[] = {"X", "O"};
	private Color background_cl = Color.white;
	private Color number_cl[] = {Color.red, Color.blue};
	private JButton bt[] = new JButton[9];
	private JButton point1_bt, point2_bt;
	private JLabel luot;
	private int a[] = new int[9];
	private JButton newGame_bt;
	private JPanel pn0, pn, pn2;
	Container cn;
	public Timer timer;
	public GameTicTacToe(String s, int Point1, int Point2) {
		super(s);
		point1 = Point1;
		point2 = Point2;
		cn = init();
		timer = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!win) {
					addPoint(autoPoint());
					timer.stop();
				}
			}
		});
	}
	
	public Container init() {
		Container cn = this.getContentPane();
		pn0 = new JPanel();
		pn0.setLayout(new FlowLayout());
		luot = new JLabel("Lượt của X");
		luot.setFont(new Font("UTM Micra", 1, 20));
		pn0.add(luot);
		pn = new JPanel();
		pn.setLayout(new GridLayout(3, 3));
		for (int i = 0; i <9; i++) {
			bt[i] = new JButton(" ");
			pn.add(bt[i]);
			bt[i].setActionCommand(String.valueOf(i));
			bt[i].setBackground(background_cl);
			bt[i].addActionListener(this);
			bt[i].setFont(new Font("Antique", 1, 120));
			a[i] = 2;
		}
	
		pn2 = new JPanel();
		pn2.setLayout(new FlowLayout());
		
		newGame_bt = new JButton("New game");
		newGame_bt.setForeground(Color.green);
		newGame_bt.addActionListener(this);
		newGame_bt.setFont(new Font("UTM Swiss 721 Black Condensed", 1, 18));
		newGame_bt.setActionCommand("-1");
		
		point1_bt = new JButton(String.valueOf(point1));
		point1_bt.setFont(new Font("UTM Nokia", 1, 25));
		
		point2_bt = new JButton(String.valueOf(point2));
		point2_bt.setFont(new Font("UTM Nokia", 1, 25));
		
		pn2.add(point1_bt);
		pn2.add(newGame_bt);
		pn2.add(point2_bt);
		
		cn.add(pn0, "North");
		cn.add(pn);
		cn.add(pn2, "South");
		this.setVisible(true);
		this.setSize(500, 570);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		count = countH[0] = countH[1] = 0;
		return cn;
	}
	
	public int autoPoint() {
		int k = checkPoint1();
		if (k != -1)
			return k;
		else {
			k = checkPoint2();
			if (k != -1) 
				return k;
			else
				return creatPointRandom();
		}
	}
	
	public void updateMatrix() {
		for (int i = 0; i < 9; i++)
			if (bt[i].getText() == "X")
				a[i] = 0;
			else if (bt[i].getText() == "O")
				a[i] = 1;
			else
				a[i] = 2;
	}
	
	public int checkWin() {
		updateMatrix();
		if (a[0] != 2 && a[0] == a[1] && a[1] == a[2])
			return a[0];
		if (a[3] != 2 && a[3] == a[4] && a[4] == a[5])
			return a[3];
		if (a[6] != 2 && a[6] == a[7] && a[7] == a[8])
			return a[6];
		
		if (a[0] != 2 && a[0] == a[3] && a[3] == a[6])
			return a[0];
		if (a[1] != 2 && a[1] == a[4] && a[4] == a[7])
			return a[1];
		if (a[2] != 2 && a[2] == a[5] && a[5] == a[8])
			return a[2];
		
		if (a[0] != 2 && a[0] == a[4] && a[4] == a[8])
			return a[0];
		if (a[2] != 2 && a[2] == a[4] && a[4] == a[6])
			return a[2];
		for (int i = 0; i < 9; i++)
			if (a[i] == 2)
				return -1;
		return 2;
	}
	
	public int checkPoint1() {
		for (int i = 0; i < 9; i++)
			if (a[i] == 2) {
				a[i] = count;
				bt[i].setText(text[count]);
				if (checkWin() != -1) {
					a[i] = 2;
					bt[i].setText(" ");
					return i;
				}
				a[i] = 2;
				bt[i].setText(" ");
			}
		return -1;
	}
	
	public int checkPoint2() {
		for (int i = 0; i < 9; i++)
			if (a[i] == 2) {
				a[i] = 1 - count;
				bt[i].setText(text[1 - count]);
				if (checkWin() != -1) {
					a[i] = 2;
					bt[i].setText(" ");
					return i;
				}
				a[i] = 2;
				bt[i].setText(" ");
			}
		return -1;
	}
	
	public void addPoint(int k) {	
		if (!win) {
			if (a[k] == 2) {
				a[k] = count;
				bt[k].setForeground(number_cl[count]);
				bt[k].setText(text[count]);
				countH[count]++;
				count = 1 - count;
			}
			if (count == 0)
				luot.setText("Lượt của X");
			else
				luot.setText("Lượt của O");
			if (checkWin() != -1) {
				String mess = "";
				int cw = checkWin();
				if (cw == 2) {
					mess = "Hòa!";
					point1++;
					point2++;
				}
				else {
					if (icon != text[count]) {
						mess = "Bạn đã chiến thắng!";
						point1 += 10;
					}
					else {
						mess = "Bạn đã thua!";
						point2 += 10;
					}
				}
				win = true;
				JOptionPane.showMessageDialog(null, mess);
			}
		}
	}
	
	public int creatPointRandom() {
		if (a[4] == 2)
			return 4;
		int k = 0;
		k += (a[0] == 2) ? 1 : 0;
		k += (a[2] == 2) ? 1 : 0;
		k += (a[6] == 2) ? 1 : 0;
		k += (a[8] == 2) ? 1 : 0;
		if (k > 0) {
			int h = (int) ((k - 1) * Math.random() + 1);
			k = 0;
			k += (a[0] == 2) ? 1 : 0;
			if (k == h)
				return 0;
			k += (a[2] == 2) ? 1 : 0;
			if (k == h)
				return 2;
			k += (a[6] == 2) ? 1 : 0;
			if (k == h)
				return 6;
			k += (a[8] == 2) ? 1 : 0;
			if (k == h)
				return 8;
		}
		for (int i = 0; i < 9; i++)
			if (a[i] == 2)
				k++;
		int h = (int) ((k - 1) * Math.random() + 1);
		k = 0;
		for (int i = 0; i < 9; i++)
			if (a[i] == 2) {
				k++;
				if (k == h)
					return i;
			}
		return 0;
	}
	
	public void showMatrix() {
		System.out.println(a[0] + " " + a[1] + " " + a[2]);
		System.out.println(a[3] + " " + a[4] + " " + a[5]);
		System.out.println(a[6] + " " + a[7] + " " + a[8]);
		if (count == 0)
		System.out.println("---------------------------");
		System.out.println();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == newGame_bt.getActionCommand()) {
			GameTicTacToe k = new GameTicTacToe("CodeLearn - GameTicTacToe", point1, point2);
			if (Math.random() >= 0.5) {
				k.timer.start();
				k.icon = "O";
			}
			else 
				k.icon = "X";
			this.dispose();
		}
		else if (!win) {
			int k = Integer.parseInt(e.getActionCommand());
			if (a[k] == 2) {
				addPoint(k);
				timer.start();
			}
		}
	}
	
	public static void main(String[] args) {
		GameTicTacToe k =new GameTicTacToe("CodeLearn - Game TicTacToe", 0, 0);
		if (Math.random() >= 0.5) {
			k.timer.start();
			k.icon = "O";
		}
		else
			k.icon = "X";
	}
}