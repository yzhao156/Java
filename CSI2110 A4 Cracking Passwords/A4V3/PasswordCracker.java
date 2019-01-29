import java.util.ArrayList;
import java.util.Stack;
import java.io.*; 

public class PasswordCracker{

	private ArrayList<String> arrayList;
	private DatabaseStandard ds;
	private ArrayList<String> temp; 

	private static String CURRENTYEAR = "2018";

	public PasswordCracker(){
		arrayList = new ArrayList<>();
		ds = new DatabaseStandard();
		temp = new ArrayList<>();
	}

	public void getCommanPasswords(String fileName){
		
		File file = new File(fileName);
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// read in lines, until read null
			while ((tempString = reader.readLine()) != null) {
				//**for test
				// System.out.println(tempString);
				// arrayList.add(tempString);
			}

			//##3 test
			arrayList.add("banana");
			// arrayList.add("E");
			// arrayList.add("E");

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void createDatabase(ArrayList<String> commonPasswords, DatabaseInterface database)
	//This class receives the original passwords in the array list, 
	//and is responsible for creating more passwords with augmented rules 
	//and insert everything on the given database, which is originally empty.
	{	
		ArrayList<String> clone = new ArrayList<>();
		try{
			for (String str : commonPasswords){
				// System.out.println("\n\n\n\n");


				temp.clear();
				temp.add(str);
				database.save(str,Sha1.hash(str));
				System.out.println(str);
				
				//rule 1:
				clone.clear();
				for (String tempString : temp){
					clone.add(tempString);

				}
				System.out.println(clone);
				for(String s : clone){
					if(rule1(s)!=null){
						temp.add(rule1(s));
						database.save(rule1(s),Sha1.hash(rule1(s)));

					}
				}
				
				//rule 2:
				clone.clear();
				for (String tempString : temp){
					clone.add(tempString);
				}
				System.out.println(clone);
				for(String s : clone){
					if(rule2(s)!=null){
						temp.add(rule2(s));
						database.save(rule2(s),Sha1.hash(rule2(s)));
					}
				}
				//rule 3:
				clone.clear();
				for (String tempString : temp){
					clone.add(tempString);
				}
				System.out.println(clone);
				for(String s : clone){
					ArrayList tempAL = rule3(s);
					if(tempAL!=null){
						for(Object o : tempAL){
							String string = (String)o;
							temp.add(string);
							database.save(string,Sha1.hash(string));
						}
					}
				}
				//reule 4:
				clone.clear();
				for (String tempString : temp){
					clone.add(tempString);
				}
				System.out.println(clone);
				for(String s : clone){
					ArrayList tempAL = rule4(s);
					if(tempAL!=null){
						for(Object o : tempAL){
							String string = (String)o;
							temp.add(string);
							database.save(string,Sha1.hash(string));
						}
					}
				}
				//rule 5:
				clone.clear();
				for (String tempString : temp){
					clone.add(tempString);
				}
				System.out.println(clone);
				for(String s : clone){
					ArrayList tempAL = rule5(s);
					if(tempAL!=null){
						for(Object o : tempAL){
							String string = (String)o;
							temp.add(string);
							database.save(string,Sha1.hash(string));
						}
					}
				}
				// System.out.println(temp.size());
				

				// System.out.println("\n\n");


			}
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
	}

	public String rule1(String str){
		if(str.length()<1){
			return null;
		}

		if(Character.isLowerCase(str.charAt(0))){
				String firstChar,elseChar;
				firstChar = str.substring(0,1);
				elseChar = str.substring(1,str.length());
				firstChar = firstChar.toUpperCase();
				return firstChar + elseChar;
		}else{
			return null;
		}
	}

	public String rule2(String str){
		return str+CURRENTYEAR;
	}

	public ArrayList<String> rule3(String str){

		ArrayList<String> result = new ArrayList<>();
		ArrayList<char[]> al = new ArrayList<>();
		ArrayList<char[]> alClone = new ArrayList<>();
		Stack<Integer> stack = new Stack<>();
		char[] inChar = str.toCharArray();

		al.add(inChar);


		//create stack
		for (int i=0; i<str.length(); i++){
				if(inChar[i]=='a'){
					stack.push(i);
				}
		}

		while(!stack.empty()){
			int tempInt = stack.pop();

			//clone to alClone
			alClone.clear();
			for (char[] c : al){
				alClone.add(c);
			}

			for(char[] c : al){
				String tempString = String.valueOf(c);
				char[] tempChar = tempString.toCharArray();
				tempChar[tempInt] = '@';
				alClone.add(tempChar);
			}

			//clone to al
			al.clear();
			for (char[] c : alClone){
				al.add(c);
			}

		}


		for (char[] c : al){
			result.add(String.valueOf(c));
		}

		if(result.size()==1){
			return null;
		}else{
			return result;
		}

	}

	public ArrayList<String> rule4(String str){
		ArrayList<String> result = new ArrayList<>();
		ArrayList<char[]> al = new ArrayList<>();
		ArrayList<char[]> alClone = new ArrayList<>();
		Stack<Integer> stack = new Stack<>();
		char[] inChar = str.toCharArray();

		al.add(inChar);


		//create stack
		for (int i=0; i<str.length(); i++){
				if(inChar[i]=='e'){
					stack.push(i);
				}
		}

		while(!stack.empty()){
			int tempInt = stack.pop();

			//clone to alClone
			alClone.clear();
			for (char[] c : al){
				alClone.add(c);
			}

			for(char[] c : al){
				String tempString = String.valueOf(c);
				char[] tempChar = tempString.toCharArray();
				tempChar[tempInt] = '3';
				alClone.add(tempChar);
			}

			//clone to al
			al.clear();
			for (char[] c : alClone){
				al.add(c);
			}

		}


		for (char[] c : al){
			result.add(String.valueOf(c));
		}

		if(result.size()==1){
			return null;
		}else{
			return result;
		}
	}

	public ArrayList<String> rule5(String str){
		ArrayList<String> result = new ArrayList<>();
		ArrayList<char[]> al = new ArrayList<>();
		ArrayList<char[]> alClone = new ArrayList<>();
		Stack<Integer> stack = new Stack<>();
		char[] inChar = str.toCharArray();

		al.add(inChar);


		//create stack
		for (int i=0; i<str.length(); i++){
				if(inChar[i]=='i'){
					stack.push(i);
				}
		}

		while(!stack.empty()){
			int tempInt = stack.pop();

			//clone to alClone
			alClone.clear();
			for (char[] c : al){
				alClone.add(c);
			}

			for(char[] c : al){
				String tempString = String.valueOf(c);
				char[] tempChar = tempString.toCharArray();
				tempChar[tempInt] = '1';
				alClone.add(tempChar);
			}

			//clone to al
			al.clear();
			for (char[] c : alClone){
				al.add(c);
			}

		}


		for (char[] c : al){
			result.add(String.valueOf(c));
		}

		if(result.size()==1){
			return null;
		}else{
			return result;
		}
	}

	public void testRule1(){
		System.out.println("\n\nTestRule1");

		System.out.println("test1\nShould be Dragon");
		System.out.println(rule1("dragon"));

		System.out.println("\ntest2\nShould be null");
		System.out.println(rule1("*7w"));

		System.out.println("\ntest3\nShould be null");
		System.out.println(rule1("*7w"));

	}

	public void testRule2(){
		System.out.println("\n\nTestRule2");

		System.out.println("test1\nShould be dragon2018");
		System.out.println(rule2("dragon"));

		System.out.println("\ntest2\nShould be *7w2018");
		System.out.println(rule2("*7w"));

	}

	public void testRule3(){
		System.out.println("\n\nTestRule3");

		System.out.println("test1\nShould be dr@gon");
		System.out.println(rule3("dragon"));

		System.out.println("\ntest2\nShould be null");
		System.out.println(rule3("*7w"));

	}

	public void testRule4(){
		System.out.println("\n\nTestRule4");

		System.out.println("test1\nShould be bas3ball");
		System.out.println(rule4("baseball"));

		System.out.println("\ntest2\nShould be null");
		System.out.println(rule4("*7w"));

	}

	public void testRule5(){
		System.out.println("\n\nTestRule5");

		System.out.println("test1\nShould be m1chael");
		System.out.println(rule5("michael"));

		System.out.println("\ntest2\nShould be null");
		System.out.println(rule5("*7w"));

	}

	public String crackPassword(String encryptedPassword, DatabaseInterface database) { 
	//uses database to crack encrypted password, returning the original password
		String result;
		result = database.decrypt(encryptedPassword);
		if (result == null){
			return "";
		}else{
			return result;
		}
	}

	public void test(){
		getCommanPasswords("commonPwd10K.txt");
		createDatabase(arrayList,ds);
		System.out.println("Size: "+arrayList.size());
	}


	

	public static void main(String[] args){

		PasswordCracker pc = new PasswordCracker();
		 // Set<Entry<String, String>> sets = hashMap.entrySet();  
   //      for(Entry<String, String> entry : sets) {  
   //          System.out.print(entry.getKey() + ", ");  
   //          System.out.println(entry.getValue());  
		pc.test();
		// System.out.println(pc.ds.size());
		// pc.testRule1();//pass
		// pc.testRule2();//pass
		// pc.testRule3();//pass
		// pc.testRule4();//pass
		// pc.testRule5();//pass
		// pc.rule3("11aass");


	/*	//-----------------
		PasswordCracker testCracker=new PasswordCracker(); 

		DatabaseStandard database1=new DatabaseStandard(); 
		ArrayList<String> commonPass=new ArrayList<String>(); 
		commonPass.add("ana");
		// commonPass.add("password"); 
		// commonPass.add("12345678"); 
		// commonPass.add("brady"); 
		testCracker.createDatabase(commonPass,database1); 
		database1.printStatistics();
		// String code=new String("F35D55B3ACF667911A679B44918F5D88B2400823"); 
		// String discoverPassword=testCracker.crackPassword(code,database1); 
		// System.out.println("Decrypt: "+code+ " Password: "+discoverPassword+";");
		//-----------------
*/

	// try{
	// 		System.out.println(Sha1.hash("brady"));
			
	// 	}catch(UnsupportedEncodingException e){
	// 		e.printStackTrace();
	// 	}

	}
}