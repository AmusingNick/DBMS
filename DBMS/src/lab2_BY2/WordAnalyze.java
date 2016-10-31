package lab2_BY2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lab1_BY1.PL0;

public class WordAnalyze {
	FileWriter fw;
	ArrayList biaoAl ;
	ArrayList changAl ;
	int biaoCount = 0;
	int changCount = 0;
	boolean Analyze(String console) throws IOException{
		fw = new FileWriter(new File("src//lab2//wordTest.txt"));
		fw.write("CLASS"+"\t"+"VALUE"+"\n");
		//将分析结果写入文件当中
		biaoAl = new ArrayList<String>();
		changAl = new ArrayList<String>();
		//为变量名表及常数表 建立空间
		int indexRear = 0; 
		int indexFront =0;
		Pattern p = Pattern.compile("\\s+");
		Matcher m = p.matcher(console);
		console = m.replaceAll(" ");
		console = console.trim();
		while(indexRear < console.length()){
			Character nowChar = console.charAt(indexRear); //当前检索的字符
			String frontString = new String("");
			frontString = console.substring(indexFront,indexRear);//判断之前的串
			//遇到等于号就和前面的字符组合一起，进行判断~~
			

			
			if(nowChar == ' ' || isYunORJie(nowChar.toString())){
				if(!frontString.equals(" ")){
					//System.out.println(PL0.GETSYM(frontString));
					outStreamFile(frontString,fw);
				}
				if(nowChar != ' '){
					//System.out.println(PL0.GETSYM(nowChar.toString()));
					outStreamFile(nowChar.toString(),fw);
				}
				indexFront = ++indexRear;
				indexRear++;
			}else{
				if(isYunORJie(frontString)){
					//System.out.println(PL0.GETSYM(frontString));
					outStreamFile(frontString,fw);
					indexFront++;
				}
				if(frontString.equals(" ")){
					indexFront++;
				}
				indexRear++;
			}
		}
		if(indexRear - 1 !=  console.length()){
			//最后一个没输出,将最后一个输出
			//System.out.println(PL0.GETSYM(console.substring(console.length() - 1)));	
			outStreamFile(console.substring(console.length() - 1),fw);
		}
		fw.flush();
		fw.close();
		return true;
	}
	private void outStreamFile(String frontString,FileWriter fw) throws IOException {


		String stringType = PL0.GETSYM(frontString);
		if(stringType.equals("标识符")){
			String biaoAbout = new String("");
			biaoCount++;
			biaoAbout = "000"+biaoCount + "\t" + frontString;
			biaoAl.add(biaoAbout);// 加入标识符表中
			
			fw.append(biaoAbout+"\n");
		}else if(stringType.equals("整数") || stringType.equals("浮点数")){
			String changAbout = new String("");
			changCount++;
			changAbout = "000"+changCount + "\t" + frontString;
			biaoAl.add(changAbout);// 加入常量表中
			
			fw.append(changAbout+"\n");
		}else if(stringType.equals("default")){
			fw.append(frontString+"(default)"+"\n");
		}else{
			fw.append(stringType+"\n");
		}
	}
	private boolean isYun(String allYun) {
		for(int i=0;i<PL0.yun.length;i++)
			if(PL0.yun[i][0].equals(allYun)){
				return true;
			}
		return false;
	}
	private boolean isYunORJie(String nowChar) {	
		for(int i=0;i<PL0.yun.length;i++)
			if(PL0.yun[i][0].equals(nowChar)){
				return true;
			}
		for(int i=0;i<PL0.jie.length;i++)
			if(PL0.jie[i][0].equals(nowChar)){
				return true;
			}
		return false;
	}
	public static void main(String[] args) throws IOException {
		String s1 = new String("create table Su(SW CHAR(82), SNAME VARCHAR(15),AGE CHAR(2123.2), SEX CHAR(123);");
		String s2 = new String("insert into S8g values('S1','WANG','20' ,'M');");
		String s3 = new String("select SW,SNAME from S,SC where SC.CW='C2' and S.SW = SC.SW;");
		String s4 = new String("update SC from GRADE = GRADE * 1.1;");
		String s5 = new String("delete from C where CGRADE >= 7;");
		String s6 = new String("alter table S  add address VARCHAR(20);");
		String s7 = new String("create view student(SW,SNAME,CNAME,GRADE) as select SW,SNAME,CNAME,GRADE from S,SC,C where S.SEX = 'M' and SC.CW = C.CW  and S.SW = SC.SW;");
		String s8 = new String("GRANT * ON S,SC,C TO others;");
		new WordAnalyze().Analyze(s2);
		
	}
}
