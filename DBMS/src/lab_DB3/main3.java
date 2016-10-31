package lab_DB3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import lab1_BY1.PL0;

public class main3 {
	/*��1��create table						��8��create index
	��2��drop table						��9��drop index
	��3��alter table						��10��create view
	��4��insert							��11��drop view
	��5��delete							��12��create user
	��6��update							
	��7��select	
	�������ϻ������
	*/
	public main3() {
		//����������������\r\n��������������\r\n���ǵ����Ĵ���
		Scanner sc = new Scanner (System.in);
		String  console = sc.nextLine();
		while(!console.equals("#")){
			boolean isSuccess = excute(console);
			if (isSuccess){
				System.out.println("ִ�гɹ�");
				console = sc.nextLine();
			}	
			else {
				System.out.println("ִ�����ʧ��");
				console = sc.nextLine();
			}
		}
	}
	private boolean excute(String console) {
		String[] allConsole = console.split(" ");
		boolean isSuccess = false; 
		switch(allConsole[0]){
		case "create": 	{isSuccess = excuteCreate(allConsole,console); 	break;	}
		case "insert":	{isSuccess = excuteInsert(allConsole,console);	break;	}
		case "delete":	{isSuccess = excuteDelete(allConsole,console);	break;	}
		case "update":	{isSuccess = excuteUpdate(allConsole,console);	break;	}
		case "select":	{isSuccess = excuteSelect(allConsole,console);	break;	}
		case "alter":	{isSuccess = excuteAlter(allConsole,console);	break;	}
		case "drop":	{isSuccess = excuteDrop(allConsole,console);	break;	}
		case "createIndex":	{isSuccess = createIndex.createIndex(allConsole[2],allConsole[3]);	break;	}
		//����Ϊˢ���������ʹ��  ˢ����� �� ˢ���ļ�.txt
		case "ˢ��":	{isSuccess = executeFileName();	break;	}
		default : {isSuccess = false; System.out.println("������"); break;  }
		}
		return isSuccess;
	}

	private boolean excuteDrop(String[] allConsole, String console) {
		/*ʵ��ɾ����Ĺ��ܣ�drop table����
		drop S
		�������ֵ䣬�����ļ���ɾ��*/
		//���� �� ���иñ���������û��Ա�Ĳ���Ȩ��Ҳ��Ҫɾ��
		File fz = new File("src//lab_DB3//�����ֵ�//"+allConsole[1]+".txt");
		File fj = new File("src//lab_DB3//�����ļ�//"+allConsole[1]+".txt");
		if(tools.isHasIndexFile(allConsole[1])){
			//ɾ������
			ArrayList<File> asIndexsFile = tools.getFilesIndex(allConsole[1]);
			for(int i=0;i<asIndexsFile.size();i++)
				asIndexsFile.get(i).delete();
		}
		if(fz.delete() && fj.delete())
			return true;
		else
			return false;
	}
	
	private boolean executeFileName() {
		File executeFile = new File("src//lab_DB3//ˢ�����.txt");
		try(FileReader fr = new FileReader(executeFile);
				BufferedReader br = new BufferedReader(fr)){
			String console = new String ("");
			while ((console = br.readLine()) !=  null){
				excute(console);
			}
		}catch(Exception e){
			System.out.println("����������");
		}
		return true;
	}
	
	private boolean excuteAlter(String[] allConsole, String console) {
		/*6��ʵ�������еĹ�ϵ��������ԵĹ��ܣ�alter table����add����
		alter table S add Address char
		7��ʵ�ִ����еĹ�ϵ��ɾ�����ԵĹ��ܣ�alter table����drop����
		alter table S drop Address char    ɾ��һ��ȫ�ǿ����ݵ�����
		alter table S drop AGE char    ɾ��һ����ȫ�ǿ����ݵ�����*/
		//�������ֵ䣬�����ļ�
		if(console.contains("add")){
			//��������
			String add_fz = allConsole[4]+" "+allConsole[5];
			String add_fj = allConsole[4];
			String fj = new String("");
			try(FileReader fr = new FileReader(new File("src//lab_DB3//�����ļ�//"+allConsole[2]+".txt"));
					BufferedReader br = new BufferedReader(fr)){
				fj += br.readLine();
				fj += add_fj;
				fj += "\r\n";
				String line = new String("");
				while ((line = br.readLine()) !=  null){
					fj += line +"\t"+ "\r\n";
				}
			}catch(Exception e ){
				System.out.println("����������");
				return false;
			}
			try(FileWriter fw_fz = new FileWriter(new File("src//lab_DB3//�����ֵ�//"+allConsole[2]+".txt"),true);
					FileWriter fw_fj = new FileWriter(new File("src//lab_DB3//�����ļ�//"+allConsole[2]+".txt"))
				){
				fw_fz.append(add_fz);
				fw_fj.append(fj);
			}catch(Exception e){
				System.out.println("����������");
				return false;
			}
			return true;
		}
		if(console.contains("drop")){
			//ɾ������
			String dropString_fz = new String("");
			String dropString_fj = new String("");
			String line = new String("");
			try(FileReader fr = new FileReader(new File("src//lab_DB3//�����ļ�//"+allConsole[2]+".txt"));
					BufferedReader br = new BufferedReader(fr)){
				int dropIndex = 0;
				String[] property = br.readLine().split("\t");
				for(int i =0;i<property.length;i++){
					if(property[i].equals(allConsole[4])){
						dropIndex = i;
					}else{
						dropString_fj += property[i];
						dropString_fj +="\t";
					}
				}
				dropString_fj += "\r\n";
				String dropInfo = new String("");
				while ((line = br.readLine()) !=  null){
					String[] allLine = line.split("\t");
					for(int i=0;i<allLine.length;i++){
						if(i != dropIndex)
							dropInfo+=allLine[i]+"\t";
					}
					dropString_fj += dropInfo ;
					dropString_fj += "\r\n";
					dropInfo = new String("");
				}
			}catch(Exception e ){
				System.out.println("����������");
				return false;
			}
			try(FileReader fr = new FileReader(new File("src//lab_DB3//�����ֵ�//"+allConsole[2]+".txt"));
					BufferedReader br = new BufferedReader(fr)){
				while ((line = br.readLine()) !=  null){
					if(!line.contains(allConsole[4]))
						dropString_fz += line + "\r\n";
				}
			}catch(Exception e ){
				System.out.println("����������");
				return false;
			}
			try(FileWriter fw_fz = new FileWriter(new File("src//lab_DB3//�����ֵ�//"+allConsole[2]+".txt"));
					FileWriter fw_fj = new FileWriter(new File("src//lab_DB3//�����ļ�//"+allConsole[2]+".txt"))
				){
				fw_fz.append(dropString_fz);
				fw_fj.append(dropString_fj);
			}catch(Exception e){
				System.out.println("����������");
				return false;
			}
			return true;
		}
		System.out.println("�﷨����");
		return false;
	}

	private boolean excuteSelect(String[] allConsole, String console) {
		/*ʵ����ʾ���ݿ�ṹ�����ݣ��Ա����ʽ��ʾ����select * from ��ϵ������
		select * from S
		*/
		String[] selectRs;
		if(console.contains("where"))
			//����������ѯ
			selectRs = new Selection().conditionSelection(allConsole, console);
		else
			//ֱ�Ӳ�ѯ
			selectRs = new Selection().directSelection(allConsole, console);
		if(selectRs == null )
			return false;
		else {
			for(int i =0;i<selectRs.length;i++)
				System.out.println(selectRs[i]);
			return true;
		}
	}

	private boolean excuteUpdate(String[] allConsole, String console) {
		/*4��ʵ���޸����ݿ��¼�Ĺ��ܣ�x����
		update S set SNAME = YU where AGE = 22   �޸�where�������һ��
		update S set SNAME = YU where SEX = M  �޸�where�����������
		update S set SNAME = GOU               �޸ĸ������������������
		*/
		if(console.contains("where")){
			return updateSome(allConsole,console);
		}else{
			//�޸�ȫ��
			return updateAll(allConsole,console); 
		}
	}

	private boolean updateAll(String[] allConsole, String console) {
		String tmp = new String("");
		try(FileReader fr = new FileReader(new File("src//lab_DB3//�����ļ�//"+allConsole[1]+".txt"));
				BufferedReader br = new BufferedReader(fr)){
			String line = new String("");
			String updateInfo = new String("");
			int upIndex = 0;
			String[] property = br.readLine().split("\t");
			System.out.println("allConsole[3]"+allConsole[3]);
			for(int i =0;i<property.length;i++){
				System.out.println("property[i]"+property[i]);
				tmp+=property[i];
				tmp +="\t";
				if(property[i].trim().equals(allConsole[3])){
					upIndex = i;
				}
			}
			tmp+="\r\n";
			while ((line = br.readLine()) !=  null){
				String[] allLine = line.split("\t");
				int allLineLength = allLine.length;
				if(line.endsWith("\t\t"))
					allLineLength++;
				for(int i=0;i<allLineLength;i++){
					if(i == upIndex)
						updateInfo+=allConsole[5];
					else
						updateInfo+=allLine[i];
					updateInfo+="\t";
				}
				tmp+=updateInfo;
				tmp+="\r\n";
				updateInfo = new String("");
			}
		}
		catch(Exception e){
			System.out.println("������");
			return false;
		}
		try(FileWriter fw =
				new FileWriter(new File("src//lab_DB3//�����ļ�//"+allConsole[1]+".txt"))){
			fw.write(tmp);
		}catch(Exception e){
			System.out.println("������");
			return false;
		}
		if(tools.isHasIndexFile(allConsole[1]))
			createIndex.update(allConsole[1]);
		return true;
	}

	private boolean updateSome(String[] allConsole, String console) {
		String[] rs = new Selection().conditionSelection(allConsole, console);
		String tmp = new String("");
		try(FileReader fr = new FileReader(new File("src//lab_DB3//�����ļ�//"+allConsole[1]+".txt"));
				BufferedReader br = new BufferedReader(fr)){
			String line = new String("");
			boolean isUpdate = false;
			String updateInfo = new String("");
			int upIndex = 0;
			String[] property = br.readLine().split("\t");
			for(int i =0;i<property.length;i++){
				tmp+=property[i];
				tmp +="\t";
				if(property[i].equals(allConsole[3])){
					upIndex = i;
				}
			}
			tmp+="\r\n";
			int updateIndex = 0;
			while ((line = br.readLine()) !=  null){
				for(int i = updateIndex ;i < rs.length; i++)
					if(rs[i].equals(line)){
						isUpdate = true;
						updateIndex++ ;
					}
				if(!isUpdate){
					tmp+=line;
					tmp+="\r\n";
				}else{
					String[] allLine = line.split("\t");
					for(int i=0;i<allLine.length;i++){
						if(i == upIndex)
							updateInfo+=allConsole[5];
						else
							updateInfo+=allLine[i];
						updateInfo+="\t";
					}
					tmp+=updateInfo;
					tmp+="\r\n";
					updateInfo = new String("");
				}
				isUpdate = false;
			}
		}
		catch(Exception e){
			System.out.println("������");
			return false;
		}
		try(FileWriter fw =
				new FileWriter(new File("src//lab_DB3//�����ļ�//"+allConsole[1]+".txt"))){
			fw.write(tmp);
		}catch(Exception e){
			System.out.println("������");
			return false;
		}
		if(tools.isHasIndexFile(allConsole[1]))
			createIndex.update(allConsole[1]);
		return true;
	}
	private boolean excuteDelete(String[] allConsole, String console) {
		/*3��ʵ��ɾ�����ݿ��¼�Ĺ��ܣ�delete����
		delete from S where SNAME = WANG //ѡ��ɾ��1����¼(char)��
		delete from S where AGE = 18	 //ѡ��ɾ��1����¼(int)��
		delete from S where AGE = 19	 //ѡ��ɾ�������¼
		delete from S  ɾ��S��ȫ����¼*/
		boolean isHasFile = tools.hasFile(allConsole,2,"�����ļ�");
		if(!isHasFile)
			return false;
		if(console.contains("where")){
			//����ɾ��
			String[] rs = new Selection().conditionSelection(allConsole, console);
			String tmp = new String("");
			try(FileReader fr = new FileReader(new File("src//lab_DB3//�����ļ�//"+allConsole[2]+".txt"));
					BufferedReader br = new BufferedReader(fr)){
				String line = new String("");
				boolean isDele = false;
				int delIndex = 0;
				while ((line = br.readLine()) !=  null){
					for(int i = delIndex ;i < rs.length; i++)
						if(rs[i].equals(line)){
							isDele = true;
							delIndex++;
						}
					if(!isDele){
						tmp+=line;
						tmp+="\r\n";
					}
					isDele = false;
				}
			}
			catch(Exception e){
				System.out.println("������");
				return false;
			}
			try(FileWriter fw =
					new FileWriter(new File("src//lab_DB3//�����ļ�//"+allConsole[2]+".txt"))){
				fw.write(tmp);
			}catch(Exception e){
				System.out.println("������");
				return false;
			}
			if(tools.isHasIndexFile(allConsole[2]))
				createIndex.delete(allConsole[2]);
			return true;
		}else{
			//ȫ��ɾ��
			String propertyLine = new String("");
			//���ڲ���ͬʱ ���������ٿ��ļ� ��ֻ�ܷ��Ŵ���
			try(FileReader fr 
					= new FileReader(new File("src//lab_DB3//�����ļ�//"+allConsole[2]+".txt"));
				BufferedReader br = new BufferedReader(fr)){
				propertyLine = br.readLine();
			}catch(Exception e){
				System.out.println("������");
				return false;
			}
			try(FileWriter fw =
					new FileWriter(new File("src//lab_DB3//�����ļ�//"+allConsole[2]+".txt"))){
				fw.write(propertyLine + "\r\n");
			}catch(Exception e){
				System.out.println("������");
				return false;
			}
			if(tools.isHasIndexFile(allConsole[2]))
				createIndex.delete(allConsole[2]);
			return true;
		}
	}

	private boolean excuteInsert(String[] allConsole, String console) {
		/*ʵ���������ݿ��¼�Ĺ��ܣ�insert����
		insert into S values(S1,WANG,20,M)
		insert into S values(S2,LIU,19,M)
		insert into S values(S3,CHEN,22,M)
		insert into S values(S4,WU,19,F)*/
		//1.�ж��Ƿ�����������ֵ䣬�����ļ����ԣ�
		//2.�ж������Ƿ����ص� 
		//3.�ж����������Ƿ�һһƥ��(��) 
		//insert into S values(S1,WANG,20,20)
		//insert into S values(S1,WANG,M,M)
		//4.��������˳������루�����ļ���   ֱ�Ӳ��루�����ļ���
		//5.���������ļ�
		//3.��ok��,����д�뵽�����ļ�����
		CharSequence csConsole =  console.subSequence(0,console.length() - 1); 
		Pattern p1 = Pattern.compile("\\(");
		String[] ss1 = p1.split(csConsole);
		String[] ss2 = ss1[1].split(",");       //S1 WANG 20 M
		try{
			FileReader fr 
				= new FileReader(new File("src//lab_DB3//�����ֵ�//"+allConsole[2]+".txt"));
			BufferedReader br = new BufferedReader(fr);
			boolean isInsertProperty23Right = isInsertPropertyRight(br,ss2,allConsole) ;
			br.close();
			fr.close();
			if(isInsertProperty23Right){
				//�������غ���������ȷ������£���������˳�������
				/*FileReader fr_shujuwenjian 
				= new FileReader(new File("src//lab_DB3//�����ļ�//"+allConsole[2]+".txt"));
				BufferedReader br_shujuwenjian = new BufferedReader(fr_shujuwenjian);
				insertOfSequence( br_shujuwenjian ,ss2,allConsole[2]);
				br_shujuwenjian.close();
				fr_shujuwenjian.close();*/
				FileWriter fw = new FileWriter(new File("src//lab_DB3//�����ļ�//"+allConsole[2]+".txt"),true);
				for(int i=0;i<ss2.length;i++){
					//��ss2�е����ݶ����5�ֽڴ洢
					int ss2ibyte = ss2[i].length();
					int j = 5 - ss2ibyte;
					fw.append(ss2[i]);
					for(int k = 0 ;k<j;k++)
						fw.append(" ");
					fw.append("\t");
				}
				fw.append("\r\n");
				fw.close();
				if(tools.isHasIndexFile(allConsole[2]))
					createIndex.insert(ss2,allConsole[2]);
			}else{
				System.out.println("��������Բ���ȷ�������������Բ���ȷ��");
				return false;
			}
		}catch(Exception e){
			System.out.println("�ļ������ڻ���������");
			return false;
		}
		return true;
	}

	/*private void insertOfSequence(BufferedReader br_shujuwenjian, String[] ss2,String fileName) throws Exception {
		//��������˳�������
		String insertConsequence = new String("");
		int pIndex = getPrimaryIndex(fileName);
		int insertPrimaryDate = Integer.parseInt(ss2[pIndex]);
		String shujuShuxing = br_shujuwenjian.readLine(); //�ѵ�һ�������ļ�����
		insertConsequence += shujuShuxing +"\r\n"; 
		String lineDate = new String("");
		int linePrimaryDate = 0;
		boolean isWrite = false;
		while((lineDate = br_shujuwenjian.readLine()) != null){
			linePrimaryDate = Integer.parseInt(lineDate.split("\t")[pIndex]);
			if(insertPrimaryDate > linePrimaryDate)
				insertConsequence += lineDate + "\r\n";
			if(insertPrimaryDate < linePrimaryDate && isWrite)
				insertConsequence += lineDate + "\r\n";
			if(insertPrimaryDate < linePrimaryDate && !isWrite){
				for(int i=0; i< ss2.length;i++)
					insertConsequence += ss2[i] + "\t";
				insertConsequence += "\r\n";
				insertConsequence += lineDate + "\r\n";
				isWrite = true;
			}
		}
		if(!isWrite){
			for(int i=0; i< ss2.length;i++)
				insertConsequence += ss2[i] + "\t";
			insertConsequence += "\r\n";
		}
		FileWriter fw = new FileWriter(new File("src//lab_DB3//�����ļ�//"+fileName+".txt"));
		fw.write(insertConsequence);
		fw.close();
	}*/
	
	private boolean isInsertPropertyRight(BufferedReader br, String[] ss2, String[] allConsole) throws IOException {
		//2.�ж������Ƿ����ص� 
		//3.�ж����������Ƿ�һһƥ��(��) 
		boolean isRight = true;
		int checkIndex = 0;
		String lineString = new String("");
		int primarykeyIndex = 0 ;
		while ((lineString = br.readLine()) != null){
			if(lineString.contains("primary key")){
				//������������:���Ƿ��������ص�
				try(FileReader fr_s 
						= new FileReader(new File("src//lab_DB3//�����ļ�//"+allConsole[2]+".txt"));
					BufferedReader br_s = new BufferedReader(fr_s)){
					String lineString_date;
					br_s.readLine();      //Խ����һ����������ʼ��
					while ((lineString_date = br_s.readLine()) != null){
						String primarykeyString = lineString_date.split("\t")[primarykeyIndex].trim();
						String insertPrimarykeyString = ss2[primarykeyIndex];
						if(insertPrimarykeyString.equals(primarykeyString))
							isRight = false;
					}
				}catch(Exception e){
					System.out.println("����������������ֵͬ");
					return false;
				}
			}
			primarykeyIndex++;
			String checkType = lineString.split(" ")[1];
			if(checkType.equals("char")){
				Character[] cs = new Character[ss2[checkIndex].length()];
				for(int i = 0 ; i< ss2[checkIndex].length(); i++)
					cs[i] = ss2[checkIndex].charAt(i);
				if(!(new PL0().isBiao(cs))){
					isRight = false;
					break;
				}
			}
			if(checkType.equals("int")){
				for(int i = 0; i<ss2[checkIndex].length();i++){
					Character c = ss2[checkIndex].charAt(i);
					if(!Character.isDigit(c)){
						isRight = false;
						break;
					}
				}
			}
			checkIndex++;
		}
		return isRight;
	}

	private boolean excuteCreate(String[] allConsole, String console) {
		/*ʵ�ֽ������ݿ��ṹ�Ĺ��ܣ�create table����Ҫ��
		��1��֧�����͡��ַ������ݡ�
		��2�����ļ���ʽ������������洢�ṹ��������ƣ�
		��3��������Ӧ�������ֵ䡣
		����:create table S {S# char primary key,SNAME char,AGE int,SEX char}
		*/
		//�ٶ�char int �ڱ��ж���5�ֽ� 5�ֽڵĴ洢
		String fileName = allConsole[2];
		//���������ֵ��ļ�
		File f_z = new File("src//lab_DB3//�����ֵ�//"+fileName+".txt");
		//������Ӧ�������ļ�
		File f_j = new File("src//lab_DB3//�����ļ�//"+fileName+".txt");
		CharSequence csConsole =  console.subSequence(0,console.length() - 1); 
		Pattern p1 = Pattern.compile("\\{");
		String[] ss1 = p1.split(csConsole);
		Pattern p2 = Pattern.compile(",");
		String[] ss2 = p2.split(ss1[1]); 
		try(FileWriter fw_fz = new FileWriter(f_z);
			FileWriter fw_fj = new FileWriter(f_j)
		){
			for(int i=0;i<ss2.length;i++){
				fw_fz.append(ss2[i]+" ");
				fw_fz.append("\r\n");
				String[] everyWrite = ss2[i].split(" ");
				int everyWriteibyte = everyWrite[0].length();
				int j = 5 - everyWriteibyte;
				fw_fj.append(everyWrite[0]);
				for(int k = 0 ;k<j;k++)
					fw_fj.append(" ");
				fw_fj.append("\t");
			}
			fw_fj.append("\r\n");
		}catch(Exception e){
			System.out.println("����������");
			return false;
		}
		return true;
	}

	public static void main(String[] args){
		//�������Ĺ�������:
		//˼������ ��������
		// 		1.���ָ����ʵ��:����������ɾ������(�ݴ�)  
		//2.��дselect
		//��������ͼ����дselect���²���
		
		//Ҫ��select �������б� from ��ϵ���б��������� where  (�������� where������ ѡ������)
		//ÿ����������������һ��isExecute �����ж������������Ƿ�ִ�й� ��ʼisExecute=false
		//where��������:and , or
		//ѡ��������ָ�������� ѡ������� ��������ʽ������
		//ѡ���������=��!=��<=��>=��<��>��

		//�������б� - ͶӰ����2
		//��ϵ���� - �Ƿ�֧������
		//�������� - ���Ӳ���3
		//ѡ������ - ѡ�����1
		
		//�ӵ�һ��ϵ��ʼ����,ֱ��������ϣ�
		//1.�Ƿ���ѡ��������У��Ƿ���ͶӰ��������У�
		//2.�Ƿ�������������û��ִ�й�,����ִ�У�ִ�к�isExecute = true;
		new main3();
	}
}
