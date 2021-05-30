package main;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.awt.Image;

import Game2048.Game2048;
import GameLatHinh.GameLatHinh;
import GameLytoDifferentColor.GameLytoDifferentColor;
import GameSapXepSo.GameSapXepSo;
import GameTicTacTo.GameTicTacToe;
import Sudoku.Sudoku;
import gameCaro.CaRo;
import gameCoTrangDen.gameCoTrangDen;
import gameDoMin.gameDoMin;
import snakeGame.snakeGame;

public class Main extends JFrame implements ActionListener {
	String s[] = {"Game 2048",
			"Game rắn săn mồi",
			"Game sắp xếp số",
			"Game TicTacToe",
			"Game Lyto Different Color",
			"Game lật hình",
			"Game dò mìn",
			"Game cờ trắng đen",
			"Game Caro"
		};
	JPanel pn, pn2;
	JButton bt[] = new JButton[9];
	Container cn;
	public Main(String s) {
		super(s);
		cn = init();
	}
	public Container init() {
		Container cn = this.getContentPane();
		pn = new JPanel();
		pn.setLayout(new GridLayout(3, 3));
		for (int i = 0; i < s.length; i++) {
			bt[i] = new JButton();
			bt[i].setActionCommand(String.valueOf(i));
			bt[i].setBackground(Color.white);
			bt[i].setFont(new Font("s", 1, 20));
			pn.add(bt[i]);
			bt[i].addActionListener(this);
			bt[i].setIcon(getIcon(i + 1));
		}
		cn.add(pn);
		this.setVisible(true);
		this.setSize(900, 1000);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		return cn;
	}
		
	private Icon getIcon(int index) {
		int width = 200, height = 200;
		Image image = new ImageIcon(getClass().getResource("Icon/icon_" + index + ".png")).getImage();
		Icon icon = new ImageIcon(image.getScaledInstance(width, height, image.SCALE_SMOOTH));
		return icon;
	}	
		
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int k = Integer.parseInt(e.getActionCommand());
		switch (k) {
			case 5:
				new Sudoku(0).timer.start();
				break;
			case 0:
				new Game2048("000");
				break;
			case 1:
				new snakeGame("Demo - Game Rắn Săn Mồi", 4);
				break;
			case 2:
				String name = JOptionPane.showInputDialog(null, "Nhập tên của bạn\n" + "(Tiếng việt không dấu)", "");
				String sdt = "";
				String checkSdt = "^0[0-9]{9}";
				do {
					sdt = JOptionPane.showInputDialog("Nhập số điện thoại của bạn:\n(Dùng để trao quà)");
					if (!sdt.matches(checkSdt))
						JOptionPane.showMessageDialog(null, "Số điện thoại không đúng\n" + "Vui lòng nhập lại");
				} while(!sdt.matches(checkSdt));
				new GameSapXepSo("Demo - Game Sắp Xếp Số - Level: 1","3", name, sdt);
				break;
			case 3:
				GameTicTacToe k1 =new GameTicTacToe("CodeLearn - Game TicTacToe", 0, 0);
				if (Math.random() >= 0.5) {
					k1.timer.start();
					k1.icon = "O";
				}
				else
					k1.icon = "X";
				break;
			case 4:
				GameLytoDifferentColor k11 = new GameLytoDifferentColor(0, 0, "30:00", 1);
				k11.timer.start();
				break;
			case 6:
				new GameLatHinh(0, 100);
				break;
			case 8:
				new gameDoMin("Demo - game dò mìn", 0);
				break;
			case 7:
				new gameCoTrangDen("Demo - Game cờ trắng đen");
				break;
			default:
				System.exit(0);
		}
	}
	public static void main(String[] args) {
		new Main("VKU Game");
	}
}