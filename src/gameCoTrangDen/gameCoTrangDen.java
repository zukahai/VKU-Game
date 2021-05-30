package gameCoTrangDen;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

public class gameCoTrangDen extends JFrame implements ActionListener {
	boolean win = false;
	private Color bom_cl = Color.red;
	private Color background_cl = Color.gray;
	private Color background[] = {Color.black, Color.green};
	private Color background_2_cl = Color.green;
	private int m = 10, n = 10, count = 0;
	private int a[][] = new int[m][n];
	private JButton bt[][] = new JButton[m][n];
	private JButton count1, count2;
	private JPanel pn, pn2;
	Container cn;
	Timer timer;
	public gameCoTrangDen(String s) {
		super(s);
		cn = init();
		timer = new Timer(350, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!win) {
					auto();
					update();
					timer.stop();
				}
			}
		});
	}
	public Container init() {
		Container cn = this.getContentPane();
		pn = new JPanel();
		pn.setLayout(new GridLayout(m, n));
		for (int i = 0; i < m ; i++)
			for (int j = 0; j < n; j++){
				bt[i][j] = new JButton("   ");
				a[i][j] = 2;
			}
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
			{
				bt[i][j] = new JButton("   ");
				pn.add(bt[i][j]);
				bt[i][j].setActionCommand(i + " " + j);
				bt[i][j].setBackground(background_cl);
				bt[i][j].addActionListener(this);
			}
		for (int i = m / 2 - 1; i <= m / 2; i++)
			for (int j = n / 2 - 1; j <= n / 2; j++) {
				bt[i][j].setBackground(background[(i + j) % 2]);
				a[i][j] = (i + j) % 2;
			}
		count1 = new JButton("2");
		count1.setBackground(background[0]);
		count1.setFont(new Font("Arial", 1, 20));
		count1.setForeground(Color.white);
		count2 = new JButton("2");
		count2.setBackground(background[1]);
		count2.setFont(new Font("Arial", 1, 20));
		count2.setForeground(Color.white);
		pn2 = new JPanel();
		pn2.setLayout(new FlowLayout());
		pn2.add(count1);
		pn2.add(count2);
		cn.add(pn);
		cn.add(pn2, "South");
		this.setVisible(true);
		this.setSize(m * 50, n * 50);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		return cn;
	}
	int[] creatBoder() {
		int arr[] = new int[2];
		arr[0] = arr[1] = -1;
		int k = 0;
		for (int i = 0; i < m; i++) {
			if (a[i][0] == 2)
				k++;
			if (a[i][n - 1] == 2)
				k++;
		}
		for (int j = 0; j < n; j++) {
			if (a[0][j] == 2)
				k++;
			if (a[m - 1][j] == 2)
				k++;
		}
		int h = (int) (Math.random() * (k - 1)) + 1;
		k = 0;
		for (int i = 0; i < m; i++) {
			if (a[i][0] == 2) {
				k++;
				if (h == k) {
					arr[0] = i;
					arr[1] = 0;
				}
			}
			if (a[i][n - 1] == 2) {
				k++;
				if (h == k) {
					arr[0] = i;
					arr[1] = n - 1;
				}
			}
		}
		for (int j = 0; j < n; j++) {
			if (a[0][j] == 2) {
				k++;
				if (h == k) {
					arr[0] = 0;
					arr[1] = j;
				}
			}
			if (a[m - 1][j] == 2) {
				k++;
				if (h == k) {
					arr[0] = m - 1;
					arr[1] = j;
				}
			}
		}
		return arr;
	}
	int[] creatPoint() {
		int arr[] = new int[2];
		int k = 0;
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				if (a[i][j] == 2)
					k++;
		int h = (int) (Math.random() * (k - 1)) + 1;
		k = 0;
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				if (a[i][j] == 2) {
					k++;
					if (k == h) {
						arr[0] = i;
						arr[1] = j;
					}
				}
		return arr;
	}
	public void addPoint(int i, int j) {
		if (a[i][j] == 2) {
			a[i][j] = count;
			bt[i][j].setBackground(background[count]);
		}
	}
	public void update() {
		int k1 = 0, k2 = 0;
		for (int i = 0; i < m; i ++)
			for (int j = 0; j <n; j++) {
				if (a[i][j] == 0)
					k1++;
				if (a[i][j] == 1)
					k2++;
			}
		count1.setText(String.valueOf(k1));
		count2.setText(String.valueOf(k2));
		count = 1 - count;
		checkWin();
	}
	public void solve(int x, int y) {
		addPoint(x, y);
		int i, j, h;
		i = x; j = y + 1;
		while (j < n && a[i][j] == 1 - count)
			j++;
		if (j < n && a[i][j] == count)
			for (int k = y + 1; k < j; k++) {
				a[i][k] = count;
				bt[i][k].setBackground(background[count]);
			}
		i = x; j = y - 1;
		while (j >= 0 && a[i][j] == 1 - count)
			j--;
		if (j >= 0 && a[i][j] == count)
			for (int k = j + 1; k < y; k++) {
				a[i][k] = count;
				bt[i][k].setBackground(background[count]);
			}
		
		i = x + 1; j = y;
		while (i < m && a[i][j] == 1 - count)
			i++;
		if (i < m && a[i][j] == count)
			for (int k = x + 1; k < i; k++) {
				a[k][j] = count;
				bt[k][j].setBackground(background[count]);
			}
		i = x - 1; j = y;
		while (i >= 0 && a[i][j] == 1 - count)
			i--;
		if (i >= 0 && a[i][j] == count)
			for (int k = i + 1; k < x; k++) {
				a[k][j] = count;
				bt[k][j].setBackground(background[count]);
			}
		
		i = x + 1; j = y + 1;
		while (i < m && j < n && a[i][j] == 1 - count) {
			i++;
			j++;
		}
		h = y + 1;
		if (i < m && j < n && a[i][j] == count)
			for (int k = x + 1; k < i; k++, h++) {
				a[k][h] = count;
				bt[k][h].setBackground(background[count]);
			}
		i = x - 1; j = y - 1;
		while (i >= 0 && j >= 0 && a[i][j] == 1 - count) {
			i--;
			j--;
		}
		h = j + 1;
		if (i >= 0 && j >= 0 && a[i][j] == count)
			for (int k = i + 1; k < x; k++, h++) {
				a[k][h] = count;
				bt[k][h].setBackground(background[count]);
			}
		
		i = x + 1; j = y - 1;
		while (i < m && j >= 0 && a[i][j] == 1 - count) {
			i++;
			j--;
		}
		h = j + 1;
		if (i < m && j >= 0 && a[i][j] == count)
			for (int k = i - 1; h < y; k--, h++) {
				a[k][h] = count;
				bt[k][h].setBackground(background[count]);
			}
		i = x - 1; j = y + 1;
		while (i >= 0 && j < n && a[i][j] == 1 - count) {
			i--;
			j++;
		}
		h = y + 1;
		if (i >= 0 && j < n && a[i][j] == count)
			for (int k = x - 1; h < j; k--, h++) {
				a[k][h] = count;
				bt[k][h].setBackground(background[count]);
			}	
	}
	public int countSolve(int x, int y) {
		int dem = 0;
		int i, j, h;
		i = x; j = y + 1;
		while (j < n && a[i][j] == 1 - count)
			j++;
		if (j < n && a[i][j] == count)
			for (int k = y + 1; k < j; k++) {
				dem++;
			}
		i = x; j = y - 1;
		while (j >= 0 && a[i][j] == 1 - count)
			j--;
		if (j >= 0 && a[i][j] == count)
			for (int k = j + 1; k < y; k++) {
				dem++;
			}
		
		i = x + 1; j = y;
		while (i < m && a[i][j] == 1 - count)
			i++;
		if (i < m && a[i][j] == count)
			for (int k = x + 1; k < i; k++) {
				dem++;
			}
		i = x - 1; j = y;
		while (i >= 0 && a[i][j] == 1 - count)
			i--;
		if (i >= 0 && a[i][j] == count)
			for (int k = i + 1; k < x; k++) {
				dem++;
			}
		
		i = x + 1; j = y + 1;
		while (i < m && j < n && a[i][j] == 1 - count) {
			i++;
			j++;
		}
		h = y + 1;
		if (i < m && j < n && a[i][j] == count)
			for (int k = x + 1; k < i; k++, h++) {
				dem++;
			}
		i = x - 1; j = y - 1;
		while (i >= 0 && j >= 0 && a[i][j] == 1 - count) {
			i--;
			j--;
		}
		h = j + 1;
		if (i >= 0 && j >= 0 && a[i][j] == count)
			for (int k = i + 1; k < x; k++, h++) {
				dem++;
			}
		
		i = x + 1; j = y - 1;
		while (i < m && j >= 0 && a[i][j] == 1 - count) {
			i++;
			j--;
		}
		h = j + 1;
		if (i < m && j >= 0 && a[i][j] == count)
			for (int k = i - 1; h < y; k--, h++) {
				dem++;
			}
		i = x - 1; j = y + 1;
		while (i >= 0 && j < n && a[i][j] == 1 - count) {
			i--;
			j++;
		}
		h = y + 1;
		if (i >= 0 && j < n && a[i][j] == count)
			for (int k = x - 1; h < j; k--, h++) {
				dem++;
			}	
		return dem;
	}
	public void auto() {
		int  arr[] = {-1, -1};
		if (Math.random() > 0.7)
			arr = creatBoder();
		if (arr[0] == -1) arr = creatPoint();
		int k = arr[0];
		int h = arr[1];
		int M = 0;
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
			if (a[i][j] == 2){
				int temp = countSolve(i, j);
				if (temp > M) {
					M = temp;
					k = i;
					h = j;
				}
			}
		if (M > 0) {
			solve(k, h);
		}
		else {
			solve(k, h);
		}
	}
	public void checkWin() {
		boolean kt = true;
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				if (a[i][j] == 2)
					kt = false;
		if (kt) {
			win = true;
			timer.stop();
			String s = "Ã„ï¿½en";
			if (Integer.parseInt(count2.getText()) > Integer.parseInt(count1.getText()))
				s = "Xanh";
			JOptionPane.showMessageDialog(null, s + " Ã„â€˜ÃƒÂ£ chiÃ¡ÂºÂ¿n thÃ¡ÂºÂ¯ng!");
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!win) {
			// TODO Auto-generated method stub
			int i = 0, j = 0;
			String s = e.getActionCommand();
			int k = s.indexOf(32);
			i = Integer.parseInt(s.substring(0, k));
			j = Integer.parseInt(s.substring (k + 1, s.length()));
			if (a[i][j] == 2) {
				solve(i, j);
				update();
				timer.start();
			}
		}
	}
	public static void main(String[] args) {
		new gameCoTrangDen("Demo _ game cờ trắng đen");
	}
}