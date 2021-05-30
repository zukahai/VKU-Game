package snakeGame;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.net.*;
import java.util.Scanner;
import java.io.*;


public class snakeGame extends JFrame implements ActionListener, KeyListener {
	boolean loss = false;
	int maxXY = 100;
	int m = 20, n = 35;
	int start = 0;
	Color background_cl[] = {Color.gray, Color.LIGHT_GRAY, Color.darkGray, Color.green};
	int convertX[] = {-1, 0, 1, 0};
	int convertY[] = {0, 1, 0, -1};
	int speed[] = {700, 550, 400, 250, 100, 60, 40, 20, 10, 5};
	private JButton bt[][] = new JButton[maxXY][maxXY];
	private JComboBox lv = new JComboBox();
	private int a[][] = new int[maxXY][maxXY];
	private int xSnake[] = new int[maxXY * maxXY];
	private int ySnake[] = new int[maxXY * maxXY];
	private int xFood, yFood;
	private int sizeSnake = 0;
	private int direction = 2;
	private JButton newGame_bt, score_bt, high_bt;
	private int stepScore = 1;
	private JPanel pn, pn2;
	Container cn;
	Timer timer;
	public snakeGame(String s, int k) {
		super(s);
		stepScore = (k + 1) * (k + 1);
		cn = init(k);
		timer = new Timer(speed[k], new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runSnake(direction);
			}
		});
	}
	public Container init(int k) {
		Container cn = this.getContentPane();
		pn = new JPanel();
		pn.setLayout(new GridLayout(m,n));
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++){
				bt[i][j] = new JButton();
				pn.add(bt[i][j]);
				bt[i][j].setActionCommand(i + " " + j);
				bt[i][j].addActionListener(this);
				bt[i][j].addKeyListener(this);
				bt[i][j].setBorder(null);
				a[i][j] = 0;
			}
		
		pn2 = new JPanel();
		pn2.setLayout(new FlowLayout());
		
		newGame_bt = new JButton("New Game");
		newGame_bt.addActionListener(this);
		newGame_bt.addKeyListener(this);
		newGame_bt.setFont(new Font("UTM Micra", 1, 15));
		newGame_bt.setBackground(Color.white);
		
		score_bt = new JButton("0");
		score_bt.addActionListener(this);
		score_bt.addKeyListener(this);
		score_bt.setFont(new Font("UTM Micra", 1, 15));
		score_bt.setBackground(Color.white);
		
		high_bt = new JButton("High Score");
		high_bt.addActionListener(this);
		high_bt.addKeyListener(this);
		high_bt.setFont(new Font("UTM Micra", 1, 15));
		high_bt.setBackground(Color.white);
		
		for (int i = 1; i <= speed.length; i++)
			lv.addItem("Mức độ " + i);
		lv.setSelectedIndex(k);
		lv.addKeyListener(this);
		lv.setFont(new Font("UTM Micra", 1, 15));
		lv.setBackground(Color.white);
		
		pn2.add(newGame_bt);
		pn2.add(lv);
		pn2.add(score_bt);
		pn2.add(high_bt);
		
		a[m / 2][n / 2 - 1] = 1;
		a[m / 2][n / 2] = 1;
		a[m / 2][n / 2 + 1] = 2;
		xSnake[0] = m / 2;
		ySnake[0] = n / 2 - 1;
		xSnake[1] = m / 2;
		ySnake[1] = n / 2;
		xSnake[2] = m / 2;
		ySnake[2] = n / 2 + 1;
		sizeSnake = 3;
		
		creatFood();
		updateColor();
		cn.add(pn);
		cn.add(pn2, "South");
		this.setVisible(true);
		this.setSize(n * 30, m * 30);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		setResizable(false);
		return cn;
	}
	public void updateColor() {
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				bt[i][j].setBackground(background_cl[a[i][j]]);
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
	public void runSnake(int k) {
		a[xSnake[sizeSnake - 1]][ySnake[sizeSnake - 1]] = 1;
		xSnake[sizeSnake] = xSnake[sizeSnake - 1] + convertX[k - 1];
		ySnake[sizeSnake] = ySnake[sizeSnake - 1] + convertY[k - 1];
		
		if (xSnake[sizeSnake] < 0)
			xSnake[sizeSnake] = m - 1;
		if (xSnake[sizeSnake] == m)
			xSnake[sizeSnake] = 0;
		if (ySnake[sizeSnake] < 0)
			ySnake[sizeSnake] = n - 1;
		if (ySnake[sizeSnake] == n)
			ySnake[sizeSnake] = 0;
		
		if (a[xSnake[sizeSnake]][ySnake[sizeSnake]] == 1) {
			timer.stop();
			String str = JOptionPane.showInputDialog("Điểm của bạn là: " + score_bt.getText() + "\n" + "Nhập tên (và trường) của bạn để lưu lại số điểm này nhé\n" +
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
//			System.out.println("http://localhost/vku/HighScore/snakeGame?vkuName=" + str + "&score=" + score_bt.getText());
			try {
				URL url = new URL("https://haizukon.000webhostapp.com/HighScore/SnakeGame?vkuName=" + str + "&score=" + score_bt.getText() + "&sdt=" + sdt);
				URLConnection urlConnection = url.openConnection();
				HttpURLConnection connection = null;
				if(urlConnection instanceof HttpURLConnection) {
					connection = (HttpURLConnection) urlConnection;
				}
				BufferedReader in = new BufferedReader(
				new InputStreamReader(connection.getInputStream()));
//				System.out.println(url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			loss = true;
			return;
		}
		a[xSnake[start]][ySnake[start]] = 0;
		if (xFood == xSnake[sizeSnake] && yFood == ySnake[sizeSnake]) {
			a[xSnake[start]][ySnake[start]] = 1;
			start--;
			creatFood();
			score_bt.setText(String.valueOf(Integer.parseInt(score_bt.getText()) + stepScore));
		}
		a[xSnake[sizeSnake]][ySnake[sizeSnake]] = 2;
		start++;
		sizeSnake++;
		updateColor();
		for (int i = start; i < sizeSnake; i++) {
			xSnake[i - start] = xSnake[i];
			ySnake[i - start] = ySnake[i];
		}
		sizeSnake -= start;
		start = 0;
	}
	
	public void creatFood() {
		int k = 0;
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				if (a[i][j] == 0)
					k++;
		int h = (int) ((k - 1) *  Math.random() + 1);
		k = 0;
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				if (a[i][j] == 0) {
					k++;
					if (k == h) {
						xFood = i;
						yFood = j;
						a[i][j] = 3;
						return;
					}
				}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (!loss) {
			// TODO Auto-generated method stub
			if (e.getKeyCode() == e.VK_UP && direction != 3) {
				direction = 1;
				timer.start();
			}
			if (e.getKeyCode() == e.VK_RIGHT && direction != 4) {
				direction = 2;
				timer.start();
			}
			if (e.getKeyCode() == e.VK_DOWN && direction != 1) {
				direction = 3;
				timer.start();
			}
			if (e.getKeyCode() == e.VK_LEFT && direction != 2) {
				direction = 4;
				timer.start();
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand() == newGame_bt.getText()) {
			new snakeGame("CodeLearn.io - Game Rắn Săn Mồi", lv.getSelectedIndex());
			this.dispose();
		}
		if (e.getActionCommand() == high_bt.getText()) {
			new HighScore();
		}
		
	}
	public static void main(String[] args) {
		snakeGame sn = new snakeGame("CodeLearn.io - Game Rắn Săn Mồi", 4);
	}
}