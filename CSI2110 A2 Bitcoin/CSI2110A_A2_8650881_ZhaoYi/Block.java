import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Random;

public class Block {
	private int index; // the index of the block in the list 
	private java.sql.Timestamp timestamp; // time at which transaction
	// has been processed 
	private Transaction transaction; // the transaction object
	private String nonce; // random string (for proof of work) 
	private String previousHash; // previous hash (set to "" in first block) 
	private String hash; // hash of the block (hash of string obtained from previous variables via toString() method)

	public Block(Transaction transaction, String previousHash, int index) {
		this.transaction = transaction;
		this.previousHash = previousHash;
		this.index = index;
		timestamp = new Timestamp(System.currentTimeMillis());
	}

	public Block(Transaction transaction, String previousHash, String hash, int index) {
		this.transaction = transaction;
		this.previousHash = previousHash;
		this.hash = hash;
		this.index = index;
		timestamp = new Timestamp(System.currentTimeMillis());
	}

	public void settimestamp(long time){
		timestamp = new Timestamp(time);  
	}

	public void setNonce(String nonce){
		this.nonce = nonce;
	}
	
	public String Nonce() throws UnsupportedEncodingException{
		String temp;
		String last = "";
		int index = 0;
		int length = 1;
		char valueAtIndex;
		int trail = 0;

		for(int i=1; i<19; i++){ //max length=19 if does't find return null
			index = 0;
			temp="";
			for (int j=0; j<i; j++){
				temp+="!";	// add a new digit
			}
			while(index != -1){ 
				last = temp.substring(temp.length()-1, temp.length());	
				index = i-1;//point to the last index
				setNonce(temp);
				//System.out.println(temp);//for test
				if(Sha1.hash(this.toString()).substring(0,5).equals("00000")){
					System.out.println("trail: "+trail);
					return temp;//if valid return
				}
				valueAtIndex = temp.charAt(index);
				while(valueAtIndex == ('~')){// if last digit is ~, change it to ! and index--(pointer shift left until the index is not~)
					trail++;
					temp = temp.substring(0, index)+'!'+temp.substring(index+1, temp.length());
					if(index == 0){
						temp = temp.substring(0, index)+((char)(((int)temp.charAt(index))+1))+temp.substring(index+1, temp.length());
						index--;
						break;
					}
					if (index != 0){
						index--;
						valueAtIndex = temp.charAt(index);//check the pointer index == ~
					}else{
						 break;
					}
				} 
				if((index!=-1)) {
					if (temp.charAt(index) != ('~')){//normal case, add "1" to the current digit
						temp = temp.substring(0, index)+((char)(((int)temp.charAt(index))+1))+temp.substring(index+1, temp.length());
					}
				}
			}
		}
		return null;
	}

	public void setNonce() throws UnsupportedEncodingException {
		nonce = Nonce();
		hash = Sha1.hash(this.toString());
	}
	
	public int getIndex(){
		return index;
	}

	public long gettimestamp(){
		return timestamp.getTime();
	}

	public Transaction getTransaction(){
		return transaction;
	}

	public String getPrevious(){
		return previousHash;
	}

	public String getNonce(){
		return nonce;
	}

	public String getHash(){
		return hash;
	}

	public String toString() {
		return timestamp.toString() + ":" + transaction.toString()+ "." + nonce+ previousHash;
	} 
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		//create instances
		Transaction transaction = new Transaction("bitcoin","satoshi",50);
		Block block = new Block(transaction, "00000", 0);
		block.settimestamp(Long.valueOf("1231477200000"));
		block.setNonce("XNm.c@@*X\\4Ff*=9GB2");
		long endTime = System.currentTimeMillis ();
		System.out.println("block: "+block);			
		System.out.println("Sha1 block: "+Sha1.hash(block.toString()));	
		System.out.println("test finished1");

		transaction = new Transaction("satoshi","lucia",25);
		block = new Block(transaction, "00000613d1aec0be473e97e50e2a9e9f9f3fd73c", 1);
		block.settimestamp(Long.valueOf("1536150600000"));
		block.setNonce("ZI4b]Cg+g2Tr`fn4EB");
		System.out.println("block: "+block);			
		System.out.println("Sha1 block: "+Sha1.hash(block.toString()));	
		System.out.println("test finished2");








	}
}