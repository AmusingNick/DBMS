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
				System.out.println("获取光标位置错误");
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
					//检索最后一个字符是否是;(执行命令)
					String allConsole = "";
					//allConsole = 从文件中读取的命令 + 最后一行输入的命令
					File file = new File("src//lab2//compilerTest.txt");//Text文件
					BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
					String s = null;
					while((s = br.readLine())!=null){//使用readLine方法，一次读一行
						allConsole += s;
						allConsole += " ";
					}
					allConsole += console;
					//读取后将compilerText.txt清空
					FileWriter fw = new FileWriter(file);
					fw.write("");
					fw.flush();
					fw.close();
					//进行词法分析
					wa.Analyze(allConsole);
					//将词法分析的结果从wordTest中读入后再写入
					File fword = new File("src//lab2//wordTest.txt");
					br = new BufferedReader(new FileReader(fword));
					while((s = br.readLine())!=null){
						jta.append(s+"\n");
					}
					
					//jta.append("将判断命令是否有误\n");
					/*if(canExecute(allConsole)){
						//命令正确,执行命令,将词法分析结果写入界面
						//jta.append("SUCCESS----执行成功");
					}else{
						//jta.append("ERROR----命令有误");
					}*/
					br.close();
				}else{
					//不是最后一个字符，将其写入文件
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
