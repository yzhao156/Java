public class Solution{

	private Cube[] cubes; 
	private Cube[] copies;
	private int counter;
	private int size;


	public Solution(Cube[] cubes){
		this.cubes = new Cube[cubes.length];
		for (int i=0; i<cubes.length; i++){
			this.cubes[i] = cubes[i].copy();
		}
		copies = new Cube[cubes.length];
		for (int i=0; i<cubes.length; i++){
			copies[i] = cubes[i].copy();
		}
		counter = 0;
		size = cubes.length;
		

	}

	public Solution(Solution other, Cube c){
		if(other.size()>=4){
			throw new IllegalStateException("size cannot exceed 4");
		}
		if(c==null){
			throw new NullPointerException("Cube cannot be null");
		}
		size = other.size()+1;
		this.cubes = new Cube[size];
		for (int i=0; i<other.size; i++){
			this.cubes[i] = other.getCube(i).copy();
		}
		cubes[size-1] = c.copy();
		counter = other.getNumberOfCalls();
	}

	public int size(){
		return size;
	}

	public Cube getCube(int pos){
		return cubes[pos];
	}

	public boolean isValid(){

		counter++;

		for (int i=0; i<size(); i++){
			for (int j=i+1; j<size(); j++){
				if(cubes[i].getFront() == cubes[j].getFront()){
					return false;
				}
				if(cubes[i].getRight() == cubes[j].getRight()){
					return false;
				}
				if(cubes[i].getBack() == cubes[j].getBack()){
					return false;
				}
				if(cubes[i].getLeft() == cubes[j].getLeft()){
					return false;
				}
			}
		}
		return true;
	}



	public boolean isValid(Cube next){
		if(size()>3){
			throw new IllegalStateException("size cannot exceed 4");
		}
		counter ++;

		boolean tempBoolean;
		Solution tempSolution = new Solution(this, next);
		return tempSolution.isValid();
	}
	
	public int getNumberOfCalls(){
		return counter;
	}

	public void resetNumberOfCalls(){
		counter = 0;
	}

	public String toString(){
		String temp = "\n";
		for (int i=0; i<size(); i++){
			temp += cubes[i];
			temp += "\n";
		}
		if(isValid()){
			temp += "The solution is valid\n";
		} else {
			temp += "The solution is invalid\n";
		}

		return temp;
	}

}














