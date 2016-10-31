package lab_DB3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class tools {
	public static int getPrimaryIndex(String fileName) throws Exception{
		//�õ������ļ���λ�� 
		
		//ע��:��������ͬʱ��һ���ļ����в���ʱ����Ч~
		FileReader fr 
		= new FileReader(new File("src//lab_DB3//�����ֵ�//"+fileName+".txt"));
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
		//�õ������ļ���λ�� 
		
		//ע��:��������ͬʱ��һ���ļ����в���ʱ����Ч~
		try (FileReader fr = new FileReader(new File("src//lab_DB3//�����ֵ�//"+fileName+".txt"));
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
		//�ж��Ƿ�������ļ�
		File f = new File("src//lab_DB3//"+string+"//"+allConsole[i]+".txt");
		if(!f.exists()){
			System.out.println("�ļ�������");
			return false;
		}
		return true;
	}
	
	public static void writeFile(String filePath,String s,String type){
		//д���ļ�
		if(type.equals("write")){
			try(FileWriter fw = new FileWriter(new File(filePath)))
			{
				fw.write(s);
			}catch(Exception e){
				System.out.println("����������");
			}
		}
		if(type.equals("append")){
			try(FileWriter fw = new FileWriter(new File(filePath),true))
			{
				fw.append(s);
			}catch(Exception e){
				System.out.println("����������");
			}
		}
	}
	
	public static boolean isHasIndexFile(String fileName){
		//�ж�������Ƿ��������ļ� �� fileName�� S
		File f = new File("src//lab_DB3//�����ļ�");
		File[] indexFileNames = f.listFiles();
		boolean isHas = false;
		for(int i=0;i<indexFileNames.length ;i++)
			if(indexFileNames[i].getName().startsWith(fileName))
				isHas = true;
		return isHas;
	}
	
	public static ArrayList<String> getFileIndexs(String fileName){
		//�õ����������������ļ����֣��е��������� ���� AGE Height��
		File f = new File("src//lab_DB3//�����ļ�");
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
		//�õ����������������ļ����е��������� ���� AGE Height��
		File f = new File("src//lab_DB3//�����ļ�");
		File[] indexFileNames = f.listFiles();
		ArrayList<File> FileIndexs = new ArrayList<>();
		for(int i = 0 ;i<indexFileNames.length ;i++){
			if(indexFileNames[i].getName().startsWith(fileName))
				FileIndexs.add(indexFileNames[i]);
		}
		return FileIndexs;
	}
	
	
}	
