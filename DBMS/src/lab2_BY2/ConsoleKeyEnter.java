package lab2_BY2;

import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

public class ConsoleKeyEnter extends KeyAdapter{
	JTextArea jta;
	WordAnalyze wa;
	public ConsoleKeyEnter(JTextArea jta2) {
		// TODO Auto-generated constructor stub
		jta = jta2;
	}
	@Override
	public void keyPressed(KeyEvent e){
		if(e.getKeyChar()==''){
			Rectangle rec;
			try {
				rec = jta.modelToView(jta.getCaretPosition());
				int line = rec.y / rec.height + 1;
				String[] lineString = jta.getText().split("\n");
				String thisString = lineString[line-1];
				if(thisString.endsWith(">"))
					jta.setText(jta.getText()+">");
			}catch (BadLocationException e1) {
				System.out.println("��ȡ���λ�ô���");
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e){
		if(e.getKeyChar()=='\n'){
			wa = new WordAnalyze();
			Rectangle rec;
			try {
				rec = jta.modelToView(jta.getCaretPosition());
				int line = rec.y / rec.height + 1;
				String[] lineString = jta.getText().split("\n");
				String console = lineString[line-2].split(">>")[1];
				if(console.endsWith(";")){
					//�������һ���ַ��Ƿ���;(ִ������)
					String allConsole = "";
					//allConsole = ���ļ��ж�ȡ������ + ���һ�����������
					File file = new File("src//lab2//compilerTest.txt");//Text�ļ�
					BufferedReader br = new BufferedReader(new FileReader(file));//����һ��BufferedReader������ȡ�ļ�
					String s = null;
					while((s = br.readLine())!=null){//ʹ��readLine������һ�ζ�һ��
						allConsole += s;
						allConsole += " ";
					}
					allConsole += console;
					//��ȡ��compilerText.txt���
					FileWriter fw = new FileWriter(file);
					fw.write("");
					fw.flush();
					fw.close();
					//���дʷ�����
					wa.Analyze(allConsole);
					//���ʷ������Ľ����wordTest�ж������д��
					File fword = new File("src//lab2//wordTest.txt");
					br = new BufferedReader(new FileReader(fword));
					while((s = br.readLine())!=null){
						jta.append(s+"\n");
					}
					
					//jta.append("���ж������Ƿ�����\n");
					/*if(canExecute(allConsole)){
						//������ȷ,ִ������,���ʷ��������д�����
						//jta.append("SUCCESS----ִ�гɹ�");
					}else{
						//jta.append("ERROR----��������");
					}*/
					br.close();
				}else{
					//�������һ���ַ�������д���ļ�
					File f = new File("src//lab2//compilerTest.txt");
					FileWriter fw = new FileWriter(f,true);
					if(f.length() != 0)
						fw.write("\n"+console);
					else
						fw.write(console);
					fw.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			jta.append("\nSQL>>");
		}
	}
}
