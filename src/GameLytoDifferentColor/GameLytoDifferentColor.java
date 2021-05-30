package GameLytoDifferentColor;

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

public class GameLytoDifferentColor extends JFrame implements ActionListener{
	int maxXY = 100;
	int n = 1, A = 0, B = 0, W = 500, H = 550;
	private JButton bt[][] = new JButton[maxXY][maxXY];
	private boolean tick[][] = new boolean[maxXY][maxXY];
	private int a[][] = new int[maxXY][maxXY];
	private int xFood, yFood;
	boolean lose = false;
	int numberLv[] = {1, 2, 2, 3, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 11, 12, 12, 12, 12, 13, 13, 13, 1000000};
	private JButton lv_bt, highScore_bt;
	private JPanel pn, pn2;
	private JLabel time_lb;
	Container cn;
	public Timer timer;
	public GameLytoDifferentColor(int k, int score, String s, int lv) {
		this.setTitle("CodeLearn - Game Lyto Different Color");
		cn = init(k, score, s, lv);
		timer = new Timer(10, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				time_lb.setText(next(time_lb, -1));
			}
		});
	}
	
	public Container init(int k, int h, String s, int lv) {
		Container cn = this.getContentPane();
		A = k; B = h;
		n = A + 2;
		pn = new JPanel();
		pn.setLayout(new GridLayout(n, n));
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++){
				bt[i][j] = new JButton();
				pn.add(bt[i][j]);
				bt[i][j].setActionCommand(i + " " + j);
				bt[i][j].addActionListener(this);
				bt[i][j].setBackground(Color.black);
				bt[i][j].setBorder(null);
				a[i][j] = 1;
				tick[i][j] = true;
			}
		createMatrix();
		setIconMatrix();
//		showMatrix();
		pn2 = new JPanel();
		pn2.setLayout(new FlowLayout());
		lv_bt = new JButton(String.valueOf(lv));
		lv_bt.setFont(new Font("UTM Nokia", 1, 15));
		lv_bt.setBackground(Color.white);
		lv_bt.addActionListener(this);
		
		highScore_bt = new JButton("High Score");
		highScore_bt.setFont(new Font("UTM Nokia", 1, 15));
		highScore_bt.setBackground(Color.white);
		highScore_bt.addActionListener(this);
		
		time_lb = new JLabel(s);
		time_lb.setFont(new Font("", 1, 20));
		pn2.add(lv_bt);
		pn2.add(time_lb);
		pn2.add(highScore_bt);
		cn.add(pn);
		cn.add(pn2, "North");
		this.setVisible(true);
		this.setSize(W, H);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		setResizable(false);
		return cn;
	}
	
	public void createMatrix() {
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				a[i][j] = 0;
		a[(int) (Math.random() * (n - 1) + 0.5)][(int) (Math.random() * (n - 1) + 0.5)] = 1;
	}
	
	public void setIconMatrix() {
		int Nicon = 14;
		int k = (int) (Math.random() * (Nicon - 1) + 0.5 + 1);
		int h = (int) ((Math.random() + 0.5) + 1);
		Icon ic[] = new Icon[2];
		ic[0] = getIcon(k, h, (W - 100) / (A + 2));
		ic[1] = getIcon(k, 3 - h, (W - 100) / (A + 2));
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				bt[i][j].setIcon(ic[a[i][j]]);
	}
	
	public void showMatrix() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				System.out.printf("%3d", a[i][j]);
			System.out.println();
		}
		System.out.println("-----------------");
		System.out.println();
	}
	
	private Icon getIcon(int index, int index2, int size) {
		int width = size, height = size;
		Image image = new ImageIcon(getClass().getResource("/GameLytoDifferentColor/image/image" + index + "_" + index2 + ".jpg")).getImage();
		Icon icon = new ImageIcon(image.getScaledInstance(width, height, image.SCALE_SMOOTH));
		return icon;
	}
	
	public void nextGame() {
		if (B == numberLv[A]) {
			A++;
			B = 0;
		}
		else
			B++;
		timer.stop();
		this.dispose();
		GameLytoDifferentColor k = new GameLytoDifferentColor(A, B, time_lb.getText(), Integer.parseInt(lv_bt.getText()) + 1);
		k.timer.start();
	}

	public String next(JLabel lb, int k) {
		String str[] = lb.getText().split(":");
		int tt = Integer.parseInt(str[1]);
		int s = Integer.parseInt(str[0]);
		String kq = "";
		int sum = tt + s * 100 + k;
		if (sum <= 0) {
			timer.stop();
			time_lb.setText("0:00");
			if ( Integer.parseInt(lv_bt.getText()) > 1) {
//				lose = true;
				String str1 = JOptionPane.showInputDialog("Hết thời gian\nĐiểm của bạn là: " + Integer.parseInt(lv_bt.getText()) + 
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
					URL url = new URL("https://haizukon.000webhostapp.com/HighScore/GameLytoDifferentColor?vkuName=" + str1 + "&score=" + Integer.parseInt(lv_bt.getText()) + "&sdt=" + sdt);
					URLConnection urlConnection = url.openConnection();
					HttpURLConnection connection = null;
					if(urlConnection instanceof HttpURLConnection) {
						connection = (HttpURLConnection) urlConnection;
					}
					BufferedReader in = new BufferedReader(
					new InputStreamReader(connection.getInputStream()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					bt[i][j].disable();
			
			lv_bt.setText("New Game");
		}
		if (sum % 100 > 9)
			kq = ":" + sum % 100 + kq;
		else
			kq = ":0" + sum % 100 + kq;
		sum /= 100;
		kq = sum + kq;
//		System.out.println(kq);
		return kq;
	}
	
	public void showDialogNewGame(String message, String title) {
		int select = JOptionPane.showOptionDialog(null, message, title,
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				null, null);
		if (select == 0) {
			this.dispose();
			GameLytoDifferentColor k = new GameLytoDifferentColor(0, 0, "30:00", 1);
			k.timer.start();
		} else {
			System.exit(0);
		}
	}
	
	public void dispose() {
	    timer.stop();
	    super.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (lose) {
			this.dispose();
		}
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals(highScore_bt.getText())) {
			new HighScore();
		} else if (e.getActionCommand().equals(lv_bt.getText())) {
			timer.stop();
			GameLytoDifferentColor k = new GameLytoDifferentColor(0, 0, "30:00", 1);
			k.timer.start();
		} else {
			int i, j;
			String s = e.getActionCommand();
			int k = s.indexOf(32);
			i = Integer.parseInt(s.substring(0, k));
			j = Integer.parseInt(s.substring (k + 1, s.length()));
			if (a[i][j] == 1) {
				time_lb.setText(next(time_lb, 100));
				nextGame();
			}
			else {
				time_lb.setText(next(time_lb, -200));
			}
		}
	}
	
	public static void main(String[] args) {
		GameLytoDifferentColor k = new GameLytoDifferentColor(0, 0, "30:00", 1);
		k.timer.start();
	}
}
