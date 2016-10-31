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
	/*（1）create table						（8）create index
	（2）drop table						（9）drop index
	（3）alter table						（10）create view
	（4）insert							（11）drop view
	（5）delete							（12）create user
	（6）update							
	（7）select	
	包含以上基本语句
	*/
	public main3() {
		//输入的语句后面如果加\r\n就是批处理，不带\r\n就是单语句的处理
		Scanner sc = new Scanner (System.in);
		String  console = sc.nextLine();
		while(!console.equals("#")){
			boolean isSuccess = excute(console);
			if (isSuccess){
				System.out.println("执行成功");
				console = sc.nextLine();
			}	
			else {
				System.out.println("执行语句失败");
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
		//以下为刷表命令：调试使用  刷入的是 ： 刷表文件.txt
		case "刷表":	{isSuccess = executeFileName();	break;	}
		default : {isSuccess = false; System.out.println("语句错误"); break;  }
		}
		return isSuccess;
	}

	private boolean excuteDrop(String[] allConsole, String console) {
		/*实现删除表的功能（drop table）。
		drop S
		将数据字典，数据文件都删除*/
		//后续 ： 还有该表的索引，用户对表的操作权限也都要删除
		File fz = new File("src//lab_DB3//数据字典//"+allConsole[1]+".txt");
		File fj = new File("src//lab_DB3//数据文件//"+allConsole[1]+".txt");
		if(tools.isHasIndexFile(allConsole[1])){
			//删除索引
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
		File executeFile = new File("src//lab_DB3//刷表操作.txt");
		try(FileReader fr = new FileReader(executeFile);
				BufferedReader br = new BufferedReader(fr)){
			String console = new String ("");
			while ((console = br.readLine()) !=  null){
				excute(console);
			}
		}catch(Exception e){
			System.out.println("读入流出错");
		}
		return true;
	}
	
	private boolean excuteAlter(String[] allConsole, String console) {
		/*6、实现在已有的关系中添加属性的功能（alter table……add）；
		alter table S add Address char
		7、实现从已有的关系中删除属性的功能（alter table……drop）；
		alter table S drop Address char    删除一个全是空数据的属性
		alter table S drop AGE char    删除一个不全是空数据的属性*/
		//改数据字典，数据文件
		if(console.contains("add")){
			//增加属性
			String add_fz = allConsole[4]+" "+allConsole[5];
			String add_fj = allConsole[4];
			String fj = new String("");
			try(FileReader fr = new FileReader(new File("src//lab_DB3//数据文件//"+allConsole[2]+".txt"));
					BufferedReader br = new BufferedReader(fr)){
				fj += br.readLine();
				fj += add_fj;
				fj += "\r\n";
				String line = new String("");
				while ((line = br.readLine()) !=  null){
					fj += line +"\t"+ "\r\n";
				}
			}catch(Exception e ){
				System.out.println("输入流错误");
				return false;
			}
			try(FileWriter fw_fz = new FileWriter(new File("src//lab_DB3//数据字典//"+allConsole[2]+".txt"),true);
					FileWriter fw_fj = new FileWriter(new File("src//lab_DB3//数据文件//"+allConsole[2]+".txt"))
				){
				fw_fz.append(add_fz);
				fw_fj.append(fj);
			}catch(Exception e){
				System.out.println("输入流错误");
				return false;
			}
			return true;
		}
		if(console.contains("drop")){
			//删除属性
			String dropString_fz = new String("");
			String dropString_fj = new String("");
			String line = new String("");
			try(FileReader fr = new FileReader(new File("src//lab_DB3//数据文件//"+allConsole[2]+".txt"));
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
				System.out.println("输入流错误");
				return false;
			}
			try(FileReader fr = new FileReader(new File("src//lab_DB3//数据字典//"+allConsole[2]+".txt"));
					BufferedReader br = new BufferedReader(fr)){
				while ((line = br.readLine()) !=  null){
					if(!line.contains(allConsole[4]))
						dropString_fz += line + "\r\n";
				}
			}catch(Exception e ){
				System.out.println("输入流错误");
				return false;
			}
			try(FileWriter fw_fz = new FileWriter(new File("src//lab_DB3//数据字典//"+allConsole[2]+".txt"));
					FileWriter fw_fj = new FileWriter(new File("src//lab_DB3//数据文件//"+allConsole[2]+".txt"))
				){
				fw_fz.append(dropString_fz);
				fw_fj.append(dropString_fj);
			}catch(Exception e){
				System.out.println("输入流错误");
				return false;
			}
			return true;
		}
		System.out.println("语法错误");
		return false;
	}

	private boolean excuteSelect(String[] allConsole, String console) {
		/*实现显示数据库结构和内容（以表格形式显示）（select * from 关系名）。
		select * from S
		*/
		String[] selectRs;
		if(console.contains("where"))
			//进行条件查询
			selectRs = new Selection().conditionSelection(allConsole, console);
		else
			//直接查询
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
		/*4、实现修改数据库记录的功能（x）。
		update S set SNAME = YU where AGE = 22   修改where后面的是一个
		update S set SNAME = YU where SEX = M  修改where后面的是三个
		update S set SNAME = GOU               修改该属性下面的所有数据
		*/
		if(console.contains("where")){
			return updateSome(allConsole,console);
		}else{
			//修改全部
			return updateAll(allConsole,console); 
		}
	}

	private boolean updateAll(String[] allConsole, String console) {
		String tmp = new String("");
		try(FileReader fr = new FileReader(new File("src//lab_DB3//数据文件//"+allConsole[1]+".txt"));
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
			System.out.println("流错误");
			return false;
		}
		try(FileWriter fw =
				new FileWriter(new File("src//lab_DB3//数据文件//"+allConsole[1]+".txt"))){
			fw.write(tmp);
		}catch(Exception e){
			System.out.println("流错误");
			return false;
		}
		if(tools.isHasIndexFile(allConsole[1]))
			createIndex.update(allConsole[1]);
		return true;
	}

	private boolean updateSome(String[] allConsole, String console) {
		String[] rs = new Selection().conditionSelection(allConsole, console);
		String tmp = new String("");
		try(FileReader fr = new FileReader(new File("src//lab_DB3//数据文件//"+allConsole[1]+".txt"));
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
			System.out.println("流错误");
			return false;
		}
		try(FileWriter fw =
				new FileWriter(new File("src//lab_DB3//数据文件//"+allConsole[1]+".txt"))){
			fw.write(tmp);
		}catch(Exception e){
			System.out.println("流错误");
			return false;
		}
		if(tools.isHasIndexFile(allConsole[1]))
			createIndex.update(allConsole[1]);
		return true;
	}
	private boolean excuteDelete(String[] allConsole, String console) {
		/*3、实现删除数据库记录的功能（delete）。
		delete from S where SNAME = WANG //选择删除1个记录(char)型
		delete from S where AGE = 18	 //选择删除1个记录(int)型
		delete from S where AGE = 19	 //选择删除多个记录
		delete from S  删除S的全部记录*/
		boolean isHasFile = tools.hasFile(allConsole,2,"数据文件");
		if(!isHasFile)
			return false;
		if(console.contains("where")){
			//部分删除
			String[] rs = new Selection().conditionSelection(allConsole, console);
			String tmp = new String("");
			try(FileReader fr = new FileReader(new File("src//lab_DB3//数据文件//"+allConsole[2]+".txt"));
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
				System.out.println("流错误");
				return false;
			}
			try(FileWriter fw =
					new FileWriter(new File("src//lab_DB3//数据文件//"+allConsole[2]+".txt"))){
				fw.write(tmp);
			}catch(Exception e){
				System.out.println("流错误");
				return false;
			}
			if(tools.isHasIndexFile(allConsole[2]))
				createIndex.delete(allConsole[2]);
			return true;
		}else{
			//全部删除
			String propertyLine = new String("");
			//由于不能同时 用两个流操控文件 ，只能分着打开了
			try(FileReader fr 
					= new FileReader(new File("src//lab_DB3//数据文件//"+allConsole[2]+".txt"));
				BufferedReader br = new BufferedReader(fr)){
				propertyLine = br.readLine();
			}catch(Exception e){
				System.out.println("流错误");
				return false;
			}
			try(FileWriter fw =
					new FileWriter(new File("src//lab_DB3//数据文件//"+allConsole[2]+".txt"))){
				fw.write(propertyLine + "\r\n");
			}catch(Exception e){
				System.out.println("流错误");
				return false;
			}
			if(tools.isHasIndexFile(allConsole[2]))
				createIndex.delete(allConsole[2]);
			return true;
		}
	}

	private boolean excuteInsert(String[] allConsole, String console) {
		/*实现输入数据库记录的功能（insert）。
		insert into S values(S1,WANG,20,M)
		insert into S values(S2,LIU,19,M)
		insert into S values(S3,CHEN,22,M)
		insert into S values(S4,WU,19,F)*/
		//1.判定是否有这个数据字典，数据文件（略）
		//2.判定主键是否有重叠 
		//3.判定数据类型是否一一匹配(简) 
		//insert into S values(S1,WANG,20,20)
		//insert into S values(S1,WANG,M,M)
		//4.根据主键顺序将其插入（有序文件）   直接插入（无序文件）
		//5.更改索引文件
		//3.都ok了,将其写入到数据文件当中
		CharSequence csConsole =  console.subSequence(0,console.length() - 1); 
		Pattern p1 = Pattern.compile("\\(");
		String[] ss1 = p1.split(csConsole);
		String[] ss2 = ss1[1].split(",");       //S1 WANG 20 M
		try{
			FileReader fr 
				= new FileReader(new File("src//lab_DB3//数据字典//"+allConsole[2]+".txt"));
			BufferedReader br = new BufferedReader(fr);
			boolean isInsertProperty23Right = isInsertPropertyRight(br,ss2,allConsole) ;
			br.close();
			fr.close();
			if(isInsertProperty23Right){
				//主键不重合且属性正确的情况下，根据主键顺序将其插入
				/*FileReader fr_shujuwenjian 
				= new FileReader(new File("src//lab_DB3//数据文件//"+allConsole[2]+".txt"));
				BufferedReader br_shujuwenjian = new BufferedReader(fr_shujuwenjian);
				insertOfSequence( br_shujuwenjian ,ss2,allConsole[2]);
				br_shujuwenjian.close();
				fr_shujuwenjian.close();*/
				FileWriter fw = new FileWriter(new File("src//lab_DB3//数据文件//"+allConsole[2]+".txt"),true);
				for(int i=0;i<ss2.length;i++){
					//将ss2中的数据都变成5字节存储
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
				System.out.println("插入的属性不正确（主键或者属性不正确）");
				return false;
			}
		}catch(Exception e){
			System.out.println("文件不存在或者流错误");
			return false;
		}
		return true;
	}

	/*private void insertOfSequence(BufferedReader br_shujuwenjian, String[] ss2,String fileName) throws Exception {
		//根据主键顺序将其插入
		String insertConsequence = new String("");
		int pIndex = getPrimaryIndex(fileName);
		int insertPrimaryDate = Integer.parseInt(ss2[pIndex]);
		String shujuShuxing = br_shujuwenjian.readLine(); //把第一行属性文件读过
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
		FileWriter fw = new FileWriter(new File("src//lab_DB3//数据文件//"+fileName+".txt"));
		fw.write(insertConsequence);
		fw.close();
	}*/
	
	private boolean isInsertPropertyRight(BufferedReader br, String[] ss2, String[] allConsole) throws IOException {
		//2.判定主键是否有重叠 
		//3.判定数据类型是否一一匹配(简) 
		boolean isRight = true;
		int checkIndex = 0;
		String lineString = new String("");
		int primarykeyIndex = 0 ;
		while ((lineString = br.readLine()) != null){
			if(lineString.contains("primary key")){
				//进行主键检索:看是否主键有重叠
				try(FileReader fr_s 
						= new FileReader(new File("src//lab_DB3//数据文件//"+allConsole[2]+".txt"));
					BufferedReader br_s = new BufferedReader(fr_s)){
					String lineString_date;
					br_s.readLine();      //越过第一行属性名开始读
					while ((lineString_date = br_s.readLine()) != null){
						String primarykeyString = lineString_date.split("\t")[primarykeyIndex].trim();
						String insertPrimarykeyString = ss2[primarykeyIndex];
						if(insertPrimarykeyString.equals(primarykeyString))
							isRight = false;
					}
				}catch(Exception e){
					System.out.println("插入主键属性有相同值");
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
		/*实现建立数据库表结构的功能（create table）。要求：
		（1）支持整型、字符型数据。
		（2）以文件形式保存基本表。（存储结构可自行设计）
		（3）建立相应的数据字典。
		例如:create table S {S# char primary key,SNAME char,AGE int,SEX char}
		*/
		//假定char int 在表中都是5字节 5字节的存储
		String fileName = allConsole[2];
		//建立数据字典文件
		File f_z = new File("src//lab_DB3//数据字典//"+fileName+".txt");
		//建立相应的数据文件
		File f_j = new File("src//lab_DB3//数据文件//"+fileName+".txt");
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
			System.out.println("输入流错误");
			return false;
		}
		return true;
	}

	public static void main(String[] args){
		//接下来的工作流程:
		//思考有序 无序问题
		// 		1.设计指令且实现:创建索引，删除索引(暂存)  
		//2.重写select
		//按照流程图：重写select大致步骤
		
		//要求：select 属性名列表 from 关系名列表【即表名】 where  (连接条件 where操作符 选择条件)
		//每个连接条件都具有一个isExecute 属性判定该连接条件是否执行过 初始isExecute=false
		//where操作符号:and , or
		//选择条件是指“属性名 选择操作符 常量”形式的条件
		//选择操作符号=、!=、<=、>=、<、>。

		//属性名列表 - 投影操作2
		//关系列名 - 是否支持索引
		//连接条件 - 连接操作3
		//选择条件 - 选择操作1
		
		//从单一关系开始检索,直到检索完毕：
		//1.是否有选择，有则进行；是否有投影，有则进行；
		//2.是否有连接条件且没有执行过,有则执行，执行后将isExecute = true;
		new main3();
	}
}
