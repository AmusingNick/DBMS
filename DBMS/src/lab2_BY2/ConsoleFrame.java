package lab2_BY2;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ConsoleFrame {
	JFrame jf = new JFrame("模拟命令行界面");
	JTextArea jta = new JTextArea(20,50);
	void init() throws IOException{
		jta.setBackground(Color.black);
		jta.setForeground(Color.white);
		jta.setFont(new Font(Font.DIALOG,Font.BOLD,20));
		jta.setText("SQL模拟器\n"
				+ "2016.9   -why set\n"
				+ "保留字以5开头\n"
				+ "运算符以4开头\n"
				+ "界符以3开头\n"
				+ "随词法分析中生成的常量和标识符以000开头\n"
				+ "\n"
				+ "\n"
				+ "SQL>>");
		jta.addKeyListener(new ConsoleKeyEnter(jta));
		jf.add(new JScrollPane(jta));
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.pack();
		//事先把compilerTest清空
		FileWriter fw = new FileWriter(new File("src//lab2//compilerTest.txt"));
		fw.write("");
		fw.flush();
		fw.close();
	}
	public static void main(String[] args) throws IOException {
		new ConsoleFrame().init();
	}
}
