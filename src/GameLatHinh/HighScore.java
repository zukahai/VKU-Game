package GameLatHinh;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class HighScore extends JFrame implements ActionListener{
	Container cn;
	DefaultTableModel model;
	JTable tb;
	JScrollPane jsc;
	public HighScore () {
		super("Game lật Hình - VKU");
		cn = init();
	}
	Container init() {
		Container cn = this.getContentPane();
		Vector vT = new Vector();
		Vector vD = writeScore();		
		try {	
			vT.clear();
			vT.add("Thứ tự");
			vT.add("Tên");
			vT.add("Số điện thoại");
			vT.add("Điểm số");
			
		}catch (Exception e) {
			
		}
		
		model = new DefaultTableModel(vD, vT);
		tb = new JTable(model);
		tb.setAutoResizeMode(1);
		jsc = new JScrollPane(tb);
		cn.add(jsc);
		
		this.setVisible(true);
		this.setSize(1000, 600);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		return cn;
	}
	public Vector writeScore() {
		Vector vD = new Vector();
		try {
			URL url = new URL("https://haizukon.000webhostapp.com/HighScore/GameLatHinh/vku.txt");
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection connection = null;
			if(urlConnection instanceof HttpURLConnection) {
				connection = (HttpURLConnection) urlConnection;
			}
			BufferedReader in = new BufferedReader(
			new InputStreamReader(connection.getInputStream()));
			String urlString = "";
			String current;
			while((current = in.readLine()) != null) {
				urlString += current;
			}
//			System.out.println(urlString);
			String str[] = urlString.split("@#@", -1);
//			System.out.println(str[0]);
			for (int i = 0; i < str.length - 1; i += 3) {
				Vector vo = new Vector();
				vo.add(i / 2 + 1);
				vo.add(str[i]);
				vo.add(str[i + 1].substring(0, 4) + "***" + str[i + 1].substring(7));
				vo.add(str[i + 2]);
				vD.add(vo);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return vD;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new HighScore();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
