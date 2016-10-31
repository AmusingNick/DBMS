package lab1_BY1;

import java.util.Scanner;



public class PL0 {
	//保留字以5开头，运算符以4开头，界符以3开头，随词法分析中生成的常量和标识符以000开头
	static public String[][] word = {{"create","5001"},
						{"table","5002"},
						{"drop","5003"},
						{"alter","5004"},
						{"insert","5005"},
						{"delete","5006"},
						{"update","5007"},
						{"select","5008"},
						{"index","5009"},
						{"view","50010"},
						{"user","50011"},
						{"grant","50012"},
						{"revoke","50013"},
						{"where","50024"},
						{"from","50025"},
						{"primaryKey","50026"},
						{"int","50028"},
						{"char","50029"},
						{"varchar","50032"},
						{"values","50040"},
						
						{"CREATE","50011"},
						{"TABLE","50012"},
						{"DROP","50013"},
						{"ALTER","50014"},
						{"INSERT","50015"},
						{"DELETE","50016"},
						{"UPDATE","50017"},
						{"SELECT","50018"},
						{"INDEX","50019"},
						{"VIEW","500110"},
						{"USER","500111"},
						{"GRANT","500112"},
						{"REVOKE","500113"},
						{"WHERE","500124"},
						{"FROM","500125"},
						{"PRIMARYKEY","500126"},
						{"INT","500128"},
						{"CHAR","500129"},
						{"VARCHAR","500132"},
						{"VALUES","500140"}};
	static public String[][] yun = {{"and","40014"},
						{"or","40015"},
						{"AND","400114"},
						{"OR","400115"},
						{"=","40016"},
						{"+","40051"},
						{"-","40052"},
						{"*","40053"},
						{"/","40054"},
						
						/*{"+=","55"},
						{"-=","56"},
						{"*=","57"},
						{"/=","58"},
						{"<>","17"},
						{"<=","18"},
						{">=","19"},*/
						
						{"<","40020"},
						{">","40031"}};//and、or、=、≠、≤、≥、<、>
	static public String[][] jie = {{"(","30021"},
						{")","30022"},
						{";","30023"},
						{"'","30027"},
						{",","30030"}};
	public static String GETSYM(String SYM){
		// class value "shuxing"
		char[] plAll = SYM.toCharArray();
		Character[] plAllC = new Character[plAll.length];
		for(int i=0;i<plAll.length; i++){
			plAllC[i] = plAll[i];
		}
		for(int i=0;i<word.length;i++)
			if(word[i][0].equals(SYM)){
				return word[i][1] +"\t"+ "NULL" +"\t" +SYM;
			}
		for(int i=0;i<jie.length;i++)
			if(jie[i][0].equals(SYM)){
				return jie[i][1] +"\t"+ "NULL" +"\t" +SYM;
			}
		for(int i=0;i<yun.length;i++)
			if(yun[i][0].equals(SYM)){
				return yun[i][1] +"\t"+ "NULL" +"\t" +SYM;
			}
		if(isBiao(plAllC)){
			return "标识符";
		}
		if(isZheng(plAllC)){
			return "整数";
		}
		if(isXiao(plAllC)){
			return "浮点数";
		}
		return "default";
	}
	static private boolean isXiao(Character[] plAllC) {
		boolean flagXiao = true;
		int countDian = 0;
		if(plAllC[0] == '-'){
			for(int i=1;i<plAllC.length; i++){
				if(!Character.isDigit(plAllC[i])
						&& plAllC[i] != '.'){
					return false;
				}
				if(plAllC[i] == '.')
					countDian++;
				if(countDian > 1)
					return false;
			}
			return flagXiao;
		}else {
			for(int i=0;i<plAllC.length; i++){
				if(!Character.isDigit(plAllC[i])
						&& plAllC[i] != '.'){
					return false;
				}
				if(plAllC[i] == '.')
					countDian++;
				if(countDian > 1)
					return false;
			}
			return flagXiao;
		}
	}
	static private boolean isZheng(Character[] plAllC) {
		boolean flagDigit = true;
		if(plAllC[0] == '-'){
			for(int i=1;i<plAllC.length; i++){
				if(!Character.isDigit(plAllC[i])){
					flagDigit = false;
				}
			}
			return flagDigit;
		}else{
			for(int i=0;i<plAllC.length; i++){
				if(!Character.isDigit(plAllC[i])){
					flagDigit = false;
				}
			}
			return flagDigit;
		}
	}
	static public boolean isBiao(Character[] plAllC) {
		boolean flagStringLetter = true;
		if(!Character.isLetter(plAllC[0])){
			return false;
		}
		int countDian = 0;
		for(int i=1;i<plAllC.length; i++){
			if(plAllC[i] == '.')
				countDian++;
			if(( !Character.isLetterOrDigit(plAllC[i]) && plAllC[i] != '.')
					|| countDian >=2 ){
				flagStringLetter = false;
			}
		}
		if(plAllC[plAllC.length - 1] == '.')
			return false;
		return flagStringLetter;
	}
	public static void main(String[] args) {
		String s = new String("");
		Scanner sc = new Scanner(System.in);
		s = sc.next();
		System.out.println(new PL0().GETSYM(s));
	}
}

