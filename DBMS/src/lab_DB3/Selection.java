package lab_DB3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Selection {
	//查询工具类:主要是select语句
	//返回空为出错
	public static String[] directSelection(String[] allConsole,String console){
		//select * from S
		File f = new File("src//lab_DB3//数据文件//"+allConsole[3]+".txt");
		if(!f.exists()){
			System.out.println("文件不存在");
			return null; 
		}
		try{
			FileReader fr_s = new FileReader(f);
			BufferedReader br_s = new BufferedReader(fr_s);
			String lineString_date; 
			String[] selects = new String[1000];
			
			
			int selectIndex =0;
			while ((lineString_date = br_s.readLine()) != null){
				selects[selectIndex++] = lineString_date; 
			}
			br_s.close();
			fr_s.close();
			String[] reallySelect = getReallyS(selects);	
			return reallySelect;
		}catch(Exception e){
			System.out.println("文件流出错");
			return null;
		}
	}
	
	private static String[] getReallyS(String[] selects) {
		int counts = 0;
		for (int i=0;i<selects.length;i++)
			if(selects[i] != null)
				counts ++;
			else
				break;
		String[] reallySelect = new String[counts];
		for (int i=0;i<selects.length;i++)
			if(selects[i] != null)
				reallySelect[i] = selects[i];
			else
				break;
		return reallySelect;
	}

	public static String[] conditionSelection(String[] allConsole,String console){
		//select * from S where SNAME = LIU
		//select * from S where AGE = 22
		String[] relation = console.split("where")[1]
				.split("=");
		try(FileReader fr_s 
				= new FileReader(new File("src//lab_DB3//数据文件//S"+".txt"));
			BufferedReader br_s = new BufferedReader(fr_s)){
			int conditionIndex = 0;
			String oneLine = br_s.readLine();
			String[] allCondition = oneLine.split("\t");
			for(int i=0;i<allCondition.length ; i++){
				if(allCondition[i].trim().equals(relation[0].replaceAll(" ", "")))
					conditionIndex = i;
			}
			String line = new String("");
			String[] rs = new String[1000];
			int selectIndex =0;
			while( (line = br_s.readLine() ) != null){
				String[] allLine = line.split("\t");
				if(allLine[conditionIndex].trim().equals(relation[1].replaceAll(" ", "")))
					rs[selectIndex++] = line;
			}
			String[] reallySelect = getReallyS(rs);	
			return reallySelect;		
		}catch(Exception e){
			System.out.println("流出错");
			return null;
		}
	}
}
