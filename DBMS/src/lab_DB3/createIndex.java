package lab_DB3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class createIndex {
	//����������
	//���:createIndex on S ����   (ֻ������������)
	static boolean createIndex(String fileName,String coluName){
		File f = new File("src//lab_DB3//�����ļ�//"+fileName+".txt");
		try(FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr)){
			br.readLine();
			int priIndex = tools.getIndex(fileName, coluName);
			//������������
			String lineRecord = new String ("");
			int[][] indexs = new int[20][2];
			for(int i = 0;i < 20 ;i++)
				indexs[i][0] = i;
			int i = 0 ;
			while ((lineRecord = br.readLine()) !=  null){
				String[] allLineRecord = lineRecord.split("\t");
				indexs[i++][1] = Integer.parseInt(allLineRecord[priIndex].trim());			
			}
			String indexsString = new String("");
			int indexCount = 0 ;
			for(i=0;i<indexs.length;i++){
				if(indexs[i][1] != 0){
					indexsString += String.valueOf(indexs[i][0])
							+"\t"+String.valueOf(indexs[i][1]) +"\t"+"\r\n";
					indexCount++;
				}
			}
			tools.writeFile("src//lab_DB3//�����ļ�//"+fileName+"_"+coluName+"_"+indexCount+".txt", indexsString, "write");
			//�����ļ���_����������_������¼�� 
		}catch(Exception e){
			System.out.println("����������");
		}
		return true;
	}

	static boolean insert(String[] record,String fileName){
		//record  S1 WANG 20 M
		//insert into S values(78,why1,1,M,176)
		File f = new File("src//lab_DB3//�����ļ�");
		File[] indexFileNames = f.listFiles();
		boolean insert = false;
		for(int i = 0 ;i<indexFileNames.length ;i++){
			if(indexFileNames[i].getName().startsWith(fileName)){
				String indexName = indexFileNames[i].getName()
						.split("_")[1];    //Height AGE
				String indexLineCount = indexFileNames[i].getName()
						.split("_")[2];
				int indexofRecord = tools.getIndex(fileName, indexName);
				String insertIndexRecord = indexLineCount.substring(0,indexLineCount.length() -4 )
						+ "\t" + record[indexofRecord]+"\t"+"\r\n";
				tools.writeFile("src//lab_DB3//�����ļ�//"+indexFileNames[i].getName(), insertIndexRecord, "append");
				//���²����������ļ���
				int countLine = Integer.parseInt(
						indexLineCount.substring(0,indexLineCount.length() -4 )) + 1; 
				String newFileName = fileName + "_" + indexName + "_" + 
								String.valueOf(countLine) + ".txt";
				updateIndexFileName(indexFileNames[i],newFileName);
				insert = true;
			}
		}
		return insert;
	}

	static boolean delete(String fileName){
		//��д:ֱ�����½�������
		ArrayList<String> asIndexs = tools.getFileIndexs(fileName);
		ArrayList<File> asIndexsFile = tools.getFilesIndex(fileName);
		for(int i=0;i<asIndexsFile.size();i++)
			asIndexsFile.get(i).delete();
		for(int i=0;i<asIndexs.size();i++)
			createIndex(fileName,asIndexs.get(i));
		return true;
	}
	static boolean update(String fileName){
		//��д:ֱ�����½�������
		ArrayList<String> asIndexs = tools.getFileIndexs(fileName);
		ArrayList<File> asIndexsFile = tools.getFilesIndex(fileName);
		for(int i=0;i<asIndexsFile.size();i++)
			asIndexsFile.get(i).delete();
		for(int i=0;i<asIndexs.size();i++)
			createIndex(fileName,asIndexs.get(i));
		return true;
	}
	
	private static boolean updateIndexFileName(File changeF,String newName) {
		//i:+1 ���� -1 ���� 
         String c=changeF.getParent(); 
         File mm=new File("src//lab_DB3//�����ļ�//"+newName);
         if(changeF.renameTo(mm))   
        	 return true;
         else
        	 return false;
	}
}
