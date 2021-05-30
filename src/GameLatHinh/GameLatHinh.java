package GameLatHinh;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.event.AncestorListener;

public class GameLatHinh extends JFrame implements ActionListener{
	int count = 0, id, preX, preY, X, Y;
	int level = 0, hit = 0, h;
	int sizeX[] = { 2,  2,  2,  3,  4,  4,   4,   4,   5,  5};
	int sizeY[] = { 3,  4,  6,  6,  6,  7,   8,  10,  10,  12};
	int TIME[] =  {10, 20, 30, 50, 65, 80, 100, 120, 140, 150}; // mÃƒÂ nt 1 kÃ¡ÂºÂ¿t thÃƒÂºc sau 10 giÃƒÂ¢y
	int maxTime = 30, time = 0;
	boolean loss = false;
	int BOM, dem = 0;
	int maxXY = 100;
	int m = 2, n = 3;
	private JProgressBar progressTime;
	private JButton bt[][] = new JButton[maxXY][maxXY];
	private boolean tick[][] = new boolean[maxXY][maxXY];
	private int a[][] = new int[maxXY][maxXY];
	private int xFood, yFood;
	private JButton score_bt, highScore_bt, newGame_bt;
	private JPanel pn, pn2;
	Container cn;
	Timer timer, timer2;
	public GameLatHinh(int k, int score) {
		this.setTitle("Demo _ game lật hình");
		level = k;
		cn = init(k, score);
		timer = new Timer(240, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				open();
				timer.stop();
			}
		});
		
		timer2 = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				time ++;
				progressTime.setValue(maxTime - time);
				if (maxTime == time) {
					timer2.stop();
					String str1 = JOptionPane.showInputDialog("Hết thời gian\nĐiểm của bạn là: " + score + 
							"\n" + "Nhập tên (và trường) của bạn để lưu lại số điểm này nhé\n" +
							"(Tiếng Việt không dấu)");
					String sdt = "";
					String checkSdt = "^0[0-9]{9}";
					do {
						sdt = JOptionPane.showInputDialog("Nhập số điện thoại của bạn:\n(Dùng để trao quà)");
						if (!sdt.matches(checkSdt))
							JOptionPane.showMessageDialog(null, "Số điện thoại không đúng\n" + "Vui lòng nhập lại");
					} while(!sdt.matches(checkSdt));
					JOptionPane.showMessageDialog(null, "Đã lưu điểm");
					str1 = str1.replace(" ", "%20");
					try {
						URL url = new URL("https://haizukon.000webhostapp.com/HighScore/GameLatHinh?vkuName=" + str1 + "&score=" + score + "&sdt=" + sdt);
						URLConnection urlConnection = url.openConnection();
						HttpURLConnection connection = null;
						if(urlConnection instanceof HttpURLConnection) {
							connection = (HttpURLConnection) urlConnection;
						}
						BufferedReader in = new BufferedReader(
						new InputStreamReader(connection.getInputStream()));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					new GameLatHinh(0, 100);
				}
			}
		});
	}
	
	public void open() {
		if (id == a[X][Y]) {
			bt[preX][preY].setIcon(getIcon(-1));
			a[X][Y] = a[preX][preY] = 0;
			tick[X][Y] = tick[preX][preY] = false;
			bt[X][Y].setBorder(null);
			bt[preX][preY].setBorder(null);
			showMatrix();
			bt[X][Y].setIcon(getIcon(-1));
			score_bt.setText(String.valueOf(Integer.parseInt(score_bt.getText()) + 100));
			hit++;
			if (hit == m * n / 2) {
				timer.stop();
				timer2.stop();
				nextGame();
			}
		}else {
			bt[preX][preY].setIcon(getIcon(0));
			bt[X][Y].setIcon(getIcon(0));
			tick[preX][preY] = true;
			tick[X][Y] = true;
			score_bt.setText(String.valueOf(Integer.parseInt(score_bt.getText()) - 10));
		}
	}
	
	public Container init(int k, int score) {
		m = sizeX[k];
		n = sizeY[k];
		maxTime = TIME[k] * 10 ;
		time = 0;
		Container cn = this.getContentPane();
		pn = new JPanel();
		pn.setLayout(new GridLayout(m, n));
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++){
				bt[i][j] = new JButton();
				pn.add(bt[i][j]);
				bt[i][j].setActionCommand(i + " " + j);
				bt[i][j].addActionListener(this);
				bt[i][j].setBackground(Color.black);
				a[i][j] = (int) (Math.random() * 2 + 1);
				bt[i][j].setIcon(getIcon(0));
				tick[i][j] = true;
			}
		pn2 = new JPanel();
		pn2.setLayout(new FlowLayout());
		score_bt = new JButton(String.valueOf(score));
		score_bt.setFont(new Font("UTM Nokia", 1, 20));
		score_bt.setBackground(Color.white);
		
		highScore_bt = new JButton("High Score");
		highScore_bt.setFont(new Font("UTM Nokia", 1, 20));
		highScore_bt.setBackground(Color.white);
		highScore_bt.addActionListener(this);
		
		newGame_bt = new JButton("New Game");
		newGame_bt.setFont(new Font("UTM Nokia", 1, 20));
		newGame_bt.setBackground(Color.white);
		newGame_bt.addActionListener(this);
		
		JLabel score_lb = new JLabel("Score: ");
		score_lb.setFont(new Font("UTM Nokia", 1, 20));
		pn2.add(newGame_bt);
		pn2.add(score_lb);
		pn2.add(score_bt);
		pn2.add(highScore_bt);

		progressTime = new JProgressBar(0, maxTime);
		progressTime.setValue(maxTime);
		progressTime.setForeground(Color.orange);
		
		createMatrix();
		showMatrix();
		cn.add(pn);
		cn.add(progressTime, "North");
		cn.add(pn2, "South");
		this.setVisible(true);
		this.setSize(n * 120, m * 170 + 90);
		if (m == 2)
		this.setSize(500, m * 170 + 90);
		System.out.println(m);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setLocationRelativeTo(null);
		setResizable(false);
		return cn;
	}
	public void dispose() {
	    timer.stop();
	    timer2.stop();
	    super.dispose();
	}
	public void createMatrix() {
		int images = 50;
		int N = m * n;
		int b[] = new int [m * n + images];
		int c[] = new int [m * n + images];
		for (int i = 0; i < images; i++) {
			b[i] = i;
			c[i] = (int) (Math.random() * 1000000);
		}
		for (int i = 0; i < images - 1; i++)
			for (int j = i + 1; j < images; j++)
				if (c[i] > c[j]) {
					int tmp = b[i];
					b[i] = b[j];
					b[j] = tmp;
					tmp = c[i];
					c[i] = c[j];
					c[j] = tmp;
				}
		for (int i = N / 2; i < N; i++)
			b[i] = b[i - N/2];
		for (int i = 0; i < m * n; i++) {
			c[i] = (int) (Math.random() * 1000000);
		}
		for (int i = 0; i < N - 1; i++)
			for (int j = i + 1; j < N; j++)
				if (c[i] > c[j]) {
					int tmp = b[i];
					b[i] = b[j];
					b[j] = tmp;
					tmp = c[i];
					c[i] = c[j];
					c[j] = tmp;
				}
		N = 0;
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				a[i][j] = b[N++];
	}
	
	public void showMatrix() {
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				System.out.printf("%3d", a[i][j]);
			System.out.println();
		}
		System.out.println("-----------------");
		System.out.println();
	}
	
	private Icon getIcon(int index) {
		int width = 120, height = 170;
		Image image = new ImageIcon(getClass().getResource("icon/icon" + index + ".jpg")).getImage();
		Icon icon = new ImageIcon(image.getScaledInstance(width, height, image.SCALE_SMOOTH));
		return icon;
	}
	
	public void newGame() {
		this.dispose();
		new GameLatHinh(0, 100);
	}
	
	public void nextGame() {
		this.dispose();
		new GameLatHinh(level + 1, Integer.parseInt(score_bt.getText()) + (maxTime - time)/50);
	}
	
	public void showDialogNewGame(String message, String title) {
		int select = JOptionPane.showOptionDialog(null, message, title,
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				null, null);
		if (select == 0) {
			newGame();
		} else {
			System.exit(0);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (loss)
			this.dispose();
		timer2.start();
		if (e.getActionCommand().equals(highScore_bt.getText())) {
			new HighScore();
		} if (e.getActionCommand().equals(newGame_bt.getText())) {
			new GameLatHinh(0, 100);
			this.dispose();
		} else {
			int i, j;
			String s = e.getActionCommand();
			int k = s.indexOf(32);
			i = Integer.parseInt(s.substring(0, k));
			j = Integer.parseInt(s.substring (k + 1, s.length()));
			if (tick[i][j]) {
				tick[i][j] = false;
				if (count == 0) {
					bt[i][j].setIcon(getIcon(a[i][j]));
					id = a[i][j];
					preX = i;
					preY = j;
				}else {
					bt[i][j].setIcon(getIcon(a[i][j]));
					X = i; Y = j;
					timer.start(); 
				}
				count = 1 - count;
			}
		}
		// TODO Auto-generated method stub
	}
	public static void main(String[] args) {
		new GameLatHinh(0, 100);
	}
}
