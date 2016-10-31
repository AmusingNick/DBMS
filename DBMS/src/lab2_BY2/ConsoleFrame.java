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
	JFrame jf = new JFrame("ģ�������н���");
	JTextArea jta = new JTextArea(20,50);
	void init() throws IOException{
		jta.setBackground(Color.black);
		jta.setForeground(Color.white);
		jta.setFont(new Font(Font.DIALOG,Font.BOLD,20));
		jta.setText("SQLģ����\n"
				+ "2016.9   -why set\n"
				+ "��������5��ͷ\n"
				+ "�������4��ͷ\n"
				+ "�����3��ͷ\n"
				+ "��ʷ����������ɵĳ����ͱ�ʶ����000��ͷ\n"
				+ "\n"
				+ "\n"
				+ "SQL>>");
		jta.addKeyListener(new ConsoleKeyEnter(jta));
		jf.add(new JScrollPane(jta));
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.pack();
		//���Ȱ�compilerTest���
		FileWriter fw = new FileWriter(new File("src//lab2//compilerTest.txt"));
		fw.write("");
		fw.flush();
		fw.close();
	}
	public static void main(String[] args) throws IOException {
		new ConsoleFrame().init();
	}
}
