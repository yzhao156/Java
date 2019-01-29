public class DatabaseMine implements DatabaseInterface{



	public class Entry{
		String key;
		String value;
		public Entry(String key, String value){
			this.key = key;
			this.value = value;
		}
	    public String getKey() {
	        return key;
	    }

	    public String getValue() {
	        return value;
	    }

	    public String setValue(String value) {
	        String temp = this.value;
	        this.value = value;
	        return temp;
	    }
	}


	int N; // this is a prime number that gives the number of addresses
// these constructors must create your hash tables with enough positions N 
// to hold the entries you will insert; you may experiment with primes N
	private int size, probes, collisions;
	// private static int DEFAULT = 1000001;
	private static int DEFAULT = 300001;
	private Entry[] array;

	public DatabaseMine(){
		collisions = 0;
		N = DEFAULT;
		size = 0;
		array = new Entry[DEFAULT];
	}

	public DatabaseMine(int length){
		collisions = 0;
		N = length;
		size = 0;
		array = new Entry[N];
	}

	public int hashFunction(String key) {
		int address = key.hashCode()%N;
		return (address>=0)?address:(address+N);
	}

	public String save(String plainPassword, String encryptedPassword){

		boolean isDisplacements = false;
		probes++;

		if(size>N){
			 throw new IllegalStateException("Out of range!");
		}
  
    	int hash = hashFunction(encryptedPassword);
    	Entry entry = array[hash];
    	while(entry!=null&&!entry.getKey().equals(encryptedPassword)){
    		hash = (hash+1)%N;
    		entry=array[hash];		
    		isDisplacements = true;
    		probes++;
    	}

    	if (isDisplacements){
    		collisions++;//15470
    	}

    	String temp = null;

    	if(array[hash] == null){
    		array[hash] = new Entry(encryptedPassword,plainPassword);
    		size++;
    	}else{
    		if(encryptedPassword.equals(array[hash].getKey())){
    			temp = array[hash].getValue();
    			array[hash]= new Entry(encryptedPassword,plainPassword);
    		}else{
    			array[hash]= new Entry(encryptedPassword,plainPassword);
    			size++;
    		}
    	}
    	
    	return temp;
    } 

	public String decrypt(String encryptedPassword){
		int hash=hashFunction(encryptedPassword);
		Entry entry=array[hash];
		String temp;
		String checkKey = entry.getKey();

		while(!checkKey.equals(encryptedPassword)){
			hash = (hash+1)%N;
			probes ++;
			entry=array[hash];
			checkKey = entry.getKey();
		}
		temp = entry.getValue();
		return temp;
	}

	
    public int size() 
    // returns the number of password pairs stored in the database
    {
    	return size;
    }
	

    public void printStatistics(){
    	System.out.println("Size is "+ size + " passwords\n"+"Number of Indexes is "+N+"\nLoad Factor is "+ (double)size/N+"\nAverage Number of Probes is " + (double)probes/size+"\nNumber of displacements (due to collisions) is " + collisions);
	}
}