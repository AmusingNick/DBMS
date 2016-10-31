package lab_DB3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class tools {
	public static int getPrimaryIndex(String fileName) throws Exception{
		//得到主键的检索位置 
		
		//注意:当两个流同时对一个文件进行操作时，无效~
		FileReader fr 
		= new FileReader(new File("src//lab_DB3//数据字典//"+fileName+".txt"));
		BufferedReader br = new BufferedReader(fr);
		String lineString = new String("");
		int primaryIndex = 0 ;
		while ((lineString = br.readLine()) != null){
			if(lineString.contains("primary key"))
				break;
			else
				primaryIndex++;
		}
		return primaryIndex;
	}
	
	public static int getIndex(String fileName,String key) {
		//得到主键的检索位置 
		
		//注意:当两个流同时对一个文件进行操作时，无效~
		try (FileReader fr = new FileReader(new File("src//lab_DB3//数据字典//"+fileName+".txt"));
				BufferedReader br = new BufferedReader(fr)){
			String lineString = new String("");
			int primaryIndex = 0 ;
			while ((lineString = br.readLine()) != null){
				if(lineString.contains(key))
					break;
				else
					primaryIndex++;
			}
			return primaryIndex;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public static boolean hasFile(String[] allConsole, int i, String string) {
		//判断是否有这个文件
		File f = new File("src//lab_DB3//"+string+"//"+allConsole[i]+".txt");
		if(!f.exists()){
			System.out.println("文件不存在");
			return false;
		}
		return true;
	}
	
	public static void writeFile(String filePath,String s,String type){
		//写入文件
		if(type.equals("write")){
			try(FileWriter fw = new FileWriter(new File(filePath)))
			{
				fw.write(s);
			}catch(Exception e){
				System.out.println("输入流错误");
			}
		}
		if(type.equals("append")){
			try(FileWriter fw = new FileWriter(new File(filePath),true))
			{
				fw.append(s);
			}catch(Exception e){
				System.out.println("输入流错误");
			}
		}
	}
	
	public static boolean isHasIndexFile(String fileName){
		//判断这个表是否有索引文件 ： fileName如 S
		File f = new File("src//lab_DB3//索引文件");
		File[] indexFileNames = f.listFiles();
		boolean isHas = false;
		for(int i=0;i<indexFileNames.length ;i++)
			if(indexFileNames[i].getName().startsWith(fileName))
				isHas = true;
		return isHas;
	}
	
	public static ArrayList<String> getFileIndexs(String fileName){
		//得到这个表的所有索引文件名字：中的索引列名 ：如 AGE Height等
		File f = new File("src//lab_DB3//索引文件");
		File[] indexFileNames = f.listFiles();
		ArrayList<String> FileIndexs = new ArrayList<>();
		for(int i = 0 ;i<indexFileNames.length ;i++){
			if(indexFileNames[i].getName().startsWith(fileName)){
				String indexName = indexFileNames[i].getName()
						.split("_")[1];
				FileIndexs.add(indexName);
			}
		}
		return FileIndexs;
	}
	
	public static ArrayList<File> getFilesIndex(String fileName){
		//得到这个表的所有索引文件：中的索引列名 ：如 AGE Height等
		File f = new File("src//lab_DB3//索引文件");
		File[] indexFileNames = f.listFiles();
		ArrayList<File> FileIndexs = new ArrayList<>();
		for(int i = 0 ;i<indexFileNames.length ;i++){
			if(indexFileNames[i].getName().startsWith(fileName))
				FileIndexs.add(indexFileNames[i]);
		}
		return FileIndexs;
	}
	
	
}	
