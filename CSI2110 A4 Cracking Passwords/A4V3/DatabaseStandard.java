import java.util.HashMap;
public class DatabaseStandard implements DatabaseInterface{
	final static int INIT = 525432;
	private int N;

	HashMap<String, String> hashMap;
	public DatabaseStandard(){
		hashMap = new HashMap<>(INIT);
		N = INIT;
	}

	public DatabaseStandard(int N){
		this.N = N;
		hashMap = new HashMap<>(N);
	}

	public String save(String plainPassword, String encryptedPassword) 
	 // Stores plainPassword and corresponding encryptedPassword in a map.
	 // if there was a value associated with this key, it is replaced, 
	 // and previous value returned; otherwise, null is returned
	 // The key is the encryptedPassword the value is the plainPassword
	{
		String temp = null;
		if (hashMap.containsKey(encryptedPassword)){
			temp = hashMap.get(encryptedPassword);
			hashMap.remove(encryptedPassword);
			hashMap.put(encryptedPassword, plainPassword);
		}else{
			hashMap.put(encryptedPassword, plainPassword);
		}
		return temp;
	}

	public String decrypt(String encryptedPassword)
	// returns plain password corresponding to encrypted password
	{
		String plainPassword;
		plainPassword = hashMap.get(encryptedPassword);
		if(plainPassword==null){
			plainPassword = "<NOT FIND>";
		}
		return plainPassword;
	}
	
    public int size()
    // returns the number of password pairs stored in the database
    {
		return hashMap.size();
    }
	
    public void printStatistics()
    // print statistics based on type of Database
	{
		System.out.println("*** DatabaseStandard Statistics ***"+"\nSize is " + size() + " passwords"+"\nInitial Number of Indexes when created " + N+"\n*** End DatabaseStandard Statistics ***");
	}
}