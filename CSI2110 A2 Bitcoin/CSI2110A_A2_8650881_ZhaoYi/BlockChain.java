import java.util.*;
import java.io.*;  

public class BlockChain{

	private static String minerID = "yzhao156";

	private ArrayList<Block> arrayList;
	private String currentFileName;

	public BlockChain(){
		arrayList = new ArrayList<Block>();
	}	

	public BlockChain(ArrayList<Block> arrayList){
		this.arrayList = arrayList;
	}

	public static BlockChain fromFile(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		ArrayList<Block> arrayList = new ArrayList<Block>();
		String[] array = new String[7];

		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			String previousHash = "00000";
			// read in lines, until read null
			while ((tempString = reader.readLine()) != null) {
				if (line%7 != 0){
					array[(line%7)-1] = tempString;
				}else{
					array[6] = tempString;
					/*
					//for test
					for (int i=0; i<7; i++){
						System.out.println("index "+i+" = "+array[i]);
					}
					*/
					//System.out.println(array[4]);
					Transaction transaction = new Transaction (array[2], array[3], Integer.parseInt(array[4]));
					Block tempBlock = new Block(transaction, previousHash, array[6], Integer.parseInt(array[0]));
					tempBlock.setNonce(array[5]);
					long l = Long.parseLong(array[1]);
					try{
						tempBlock.settimestamp(l);
					} catch(Exception e){
						System.out.println(e);
					}
					arrayList.add(tempBlock);
					//System.out.println(tempBlock);
					previousHash = array[6];
				}
				line++;
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("error");
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
		return new BlockChain(arrayList);
	}

	/**
	*Add all blocks in arrayList to file.
	*Since a block can be added to arrayList only if the block is valid, no need to check validity again.
	*/

	public void toFileAuto(String fileName){// 
		try{
			Scanner scan = new Scanner(System.in);
			System.out.println("To file？('y' for yes, 'n' for no)");
			if (scan.hasNextLine()) {
				String flag = scan.nextLine();
				if (flag.equals("y")){
					fileName = fileName.substring(0,fileName.length()-4)+"_"+minerID+".txt";
					File file = new File(fileName);
					if(file.exists()){
						System.out.println("rewritting");
					}else{
						System.out.println("writting");
						file.createNewFile();
					}
					toFile(fileName); 
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public void toFile(String fileName){
		FileWriter fw = null;
		int line = 1;
		String tempString;
		try{
			fw = new FileWriter(fileName,false);
			for(Iterator it2 = arrayList.iterator();it2.hasNext();){
            	Block temp = (Block)it2.next();
            	if(line!=1)
            		fw.write("\n");
                fw.write(temp.getIndex()+"\n");
            	fw.write(temp.gettimestamp()+"\n");
            	fw.write(temp.getTransaction().getsender()+"\n");
            	fw.write(temp.getTransaction().getreceiver()+"\n");
            	fw.write(temp.getTransaction().getamount()+"\n");
            	fw.write(temp.getNonce()+"\n");
            	fw.write(temp.getHash());
            	line++;
        	}
			System.out.println(fileName+" save successful.");
		} catch (IOException e) {
			System.out.println("Save unsuccessful.");
			e.printStackTrace();
		} finally{
			try{
				if (fw!=null){
					fw.close();
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	public boolean validateBlockchain(){
		if(arrayList.size() == 0){
			return true;
		}
		String tempHash = "00000";
		int tempInt = 0;
		for(Iterator it2 = arrayList.iterator();it2.hasNext();){
			Block temp = (Block)it2.next();
            //check hash
            try{
	            if (!Sha1.hash(temp.toString()).substring(0,5).equals("00000")){
	            	System.out.println("1");
	            	return false; 
	            }
	            if(!Sha1.hash(temp.toString()).equals(temp.getHash())){
            		return false;
            	}
        	} catch (UnsupportedEncodingException e){
        		System.out.println(e);
        	}
        	//check previous hash
            if (temp.getPrevious()!=tempHash){
            	System.out.println("2");
            	return false;
            }
			//check index
            if (temp.getIndex()!=tempInt){
            	System.out.println("3");
            	return false;
            }
            //reset tempHash
            tempHash = temp.getHash();
            tempInt++;    
        }
        return true;
	}

	public int getBalance(String username){
		int tempBalance = 0;
		int amount;
		for(Iterator it = arrayList.iterator();it.hasNext();){
			Block temp = (Block)it.next();
			if (temp.getTransaction().getsender().equals(username)){
				tempBalance -= temp.getTransaction().getamount();
			} else if (temp.getTransaction().getreceiver().equals(username)) {
				tempBalance += temp.getTransaction().getamount();
			}
		}
		return tempBalance;
	}

	public boolean newtransaction(){//ask sender/reciver/amount if valid,then add block to arrayList
		String flag = "y";
		boolean valid = true;
		while (flag.equals("y")){
			String sender = "INVALID SENDER";
			String receiver = "INVALID REVEIVER";
			String amount = "INVALID AMOUNT";
			Scanner scan = new Scanner(System.in);
			if (validateBlockchain()){
				System.out.println("Please input sender");
				if (scan.hasNextLine()) {
		            sender = scan.nextLine();
	        	}
				System.out.println("Please input receiver");
				if (scan.hasNextLine()) {
		            receiver = scan.nextLine();
	        	}
				System.out.println("Please input amount");
				if (scan.hasNextLine()) {
		            amount = scan.nextLine();
	        	}
			
				try{
					System.out.println("------------------------");
					System.out.println("Amount:  "+Integer.valueOf(amount).intValue());
					System.out.println("Balance: "+(arrayList.size()==0?("since this is the creation, set as infinty"):(getBalance(sender))));
					System.out.println("------------------------");
					if ((arrayList.size()!=0)&&(Integer.valueOf(amount).intValue() > getBalance(sender))){
						System.out.println("the transaction filed, and the transaction does not count");
					} else {
						System.out.println("valid trasaction, processing");
						Transaction t = new Transaction(sender, receiver, Integer.valueOf(amount).intValue());
						String s;
						if(arrayList.size()==0){s="00000";}else{s = arrayList.get(arrayList.size()-1).getHash();}
						int i = arrayList.size();
						Block b = new Block(t, s, i);
						add(b);
					}
				} catch(Exception e){
					System.out.println(e);
				}
				//ask user if continue
				System.out.println("\nKeep transaction？('y' for yes, 'n' for no)");
				if (scan.hasNextLine()) {
				    flag = scan.nextLine();
				    if (!flag.equals("y")){
				        	flag = "n";
				    }
			    }
			} else {
				System.out.println("transaction filled, invilad block chain");
				valid = false;
				break;
			}
		}
		return valid;
	}

	public ArrayList<Block> getArrayList(){
		return arrayList;
	}

	public void add(Block block){
		if(validateBlockchain()){
			try{
				block.setNonce();//also set hash
			} catch (UnsupportedEncodingException e){
				System.out.println(e);
			}
			arrayList.add(block);
			System.out.println("add success.");
		} else {
			System.out.println("add failed, not valid blockchain");
		}
	}

	public String toString(){
        return arrayList.toString();
	}

	public static void main(String[] args){
		//another way to do this is by asking filename from user
		try{
	    	Scanner scan = new Scanner(System.in);
			System.out.println("Please input your test file as XXXX.txt");
			String file = "";
	    	if (scan.hasNextLine()) {
            	file = scan.nextLine();
	    	}
		    if (!new File(file).exists()){
		    	System.out.println("file does not exist, now exit");
		    	System.exit(0);
		    }
			/*
			newtransaction() method will ask for input and success only if staisfy the following conditions
			(1)valid sender/reviever/amount 
			(2)valid amount (sender have enough balence to send the amount of money)
			(3)valid blockChain(valid index, valid previous hash, valid hash(starts with 00000))
		 	*/
		    BlockChain a = new BlockChain();
			a = fromFile(file);
			if(a.newtransaction())//if valid blockchan, to file
				a.toFileAuto(file);
	    }catch(Exception e){
	    	System.out.println(e+"\ncannot find file");
		}

		/*
------------------------------------------------------------------------------------------------
followings are another way to implement this
------------------------------------------------------------------------------------------------
		*/
/*
		//get instance from BlockChain
		BlockChain bc = new BlockChain();
		//get information from file and save to the instance(bc)
		bc = bc.fromFile("bitcoinBank.txt");
		//then add block to the instance(bc)
		bc.newtransaction();
		bc.toFile("bitcoinBank.txt");
*/
		//another way to do this does not require user to input infromation
/*
		String sender = "robert";
		String receiver = "bitcoin";
		String amount = "10";
		String file = "bitcoinBank.txt";

		BlockChain b = new BlockChain();
		b = b.fromFile(file);
			
		if (b.validateBlockchain()){
			try{
				if ((b.getArrayList().size()!=0)&&(Integer.valueOf(amount).intValue() > b.getBalance(sender))){
					System.out.println("transaction failed");
				} else {
					Transaction t = new Transaction(sender, receiver, Integer.valueOf(amount).intValue());
					String s;
					if(b.getArrayList().size()==0){s="00000";}else{s = b.getArrayList().get(b.getArrayList().size()-1).getHash();}
					int i = b.getArrayList().size();
					Block block = new Block(t, s, i);
					b.add(block);
				}
			} catch(Exception e){
				System.out.println(e);
			}
		} else {
			System.out.println("transaction filled, invilad block chain");
		}
		
		b.toFile(file);

*/

/*
		//test construct
		BlockChain blockChain = new BlockChain();

		//test fromFile
		BlockChain blockChain2 = fromFile("bitcoinBank.txt");
		//blockChain2.toFile("BlockChain.txt"); //as clearBC.txt
		BlockChain blockChain3 = fromFile("bitcoinBank.txt");
		blockChain3.toString();
		//System.out.println(blockChain3.validateBlockchain());
		//System.out.println(blockChain3.getBalance("bitcoin"));

		//test existing file
		blockChain3.newtransaction();

		// test new file
		BlockChain blockChain4 = new BlockChain();
		blockChain4.newtransaction();
*/
	}

}