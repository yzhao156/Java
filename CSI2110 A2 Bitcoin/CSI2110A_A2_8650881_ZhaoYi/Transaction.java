public class Transaction {

	private String sender; 
	private String receiver; 
	private int amount;

	public Transaction(String sender,String receiver, int amount){
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;	
	}

	public String getsender(){
		return sender;
	}

	public String getreceiver(){
		return receiver;
	}

	public int getamount(){
		return amount;
	}

	public String toString() {
		return sender + ":" + receiver + "=" + amount; 
	}
}