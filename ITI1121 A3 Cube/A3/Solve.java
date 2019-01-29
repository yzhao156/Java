public class Solve{

	public static Queue<Solution> generateAndTest(){

		Cube a = new Cube(new Color []{ Color .BLUE, Color .GREEN, Color .WHITE, Color .GREEN, Color .BLUE, Color .RED});
		Cube b = new Cube(new Color []{ Color .WHITE, Color .GREEN, Color .BLUE, Color .WHITE, Color .RED, Color .RED});
		Cube c = new Cube(new Color []{ Color .GREEN, Color .WHITE, Color .RED, Color .BLUE, Color .RED, Color .RED});
		Cube d = new Cube(new Color []{ Color .BLUE, Color .RED, Color .GREEN, Color .GREEN, Color .WHITE, Color .WHITE});
		Cube[] cube = new Cube[]{a,b,c,d};
		int counter = 0;
		LinkedQueue<Solution> queue = new LinkedQueue<Solution>();

		while (a.hasNext()) {
			a.next();
			while (b.hasNext()) {
				b.next();
				while (c.hasNext()) {
					c.next();
					while (d.hasNext()) {
						d.next();
						Solution tempSolution = new Solution(cube);
						counter ++;
						if (tempSolution.isValid()){
							queue.enqueue(tempSolution);
						}
					}
					d.reset();
				}
				c.reset();
			}
			b.reset();
		}

		//System.out.println(queue);
		System.out.println("Number of calls: "+counter);
		return queue;
	}

	
	public static Queue<Solution> breadthFirstSearch (){

		Cube a = new Cube(new Color []{ Color .BLUE, Color .GREEN, Color .WHITE, Color .GREEN, Color .BLUE, Color .RED});
		Cube b = new Cube(new Color []{ Color .WHITE, Color .GREEN, Color .BLUE, Color .WHITE, Color .RED, Color .RED});
		Cube c = new Cube(new Color []{ Color .GREEN, Color .WHITE, Color .RED, Color .BLUE, Color .RED, Color .RED});
		Cube d = new Cube(new Color []{ Color .BLUE, Color .RED, Color .GREEN, Color .GREEN, Color .WHITE, Color .WHITE});
		Cube[] cube = new Cube[1];
		int counter = 0;
		int tempCounter = 0;
		LinkedQueue<Solution> open = new LinkedQueue<Solution>();
		LinkedQueue<Solution> solution = new LinkedQueue<Solution>();
		while (a.hasNext()) {
			a.next();
			cube[0] = a.copy();
			open.enqueue(new Solution(cube));
		}

		while(!open.isEmpty()){

			Solution current = open.dequeue();

			if (current.size() == 1){
				while (b.hasNext()) {
					b.next();
					counter++;
					if (current.isValid(b)){
						open.enqueue(new Solution(current, b.copy()));	
					}
				}
				b.reset();
			}
			if (current.size() == 2){
				while (c.hasNext()) {
					c.next();
					counter++;
					if (current.isValid(c)){
						open.enqueue(new Solution(current, c.copy()));	
					}
				}
				c.reset();
			}
			if (current.size() == 3){
				while (d.hasNext()) {
					d.next();
					counter++;
					if (current.isValid(d)){
						solution.enqueue(new Solution(current, d.copy()));
					}
				}
				d.reset();
			}	
		}

		//System.out.println(solution);
		System.out.println("Number of calls: " + counter);		
		return solution;
	}


	public static void main(String[] args){
		StudentInfo.display();
		long start , stop ;
		System.out.println( "generateAndTest:" ) ;
		start = System. currentTimeMillis (); // could also use nanoTime
		
		generateAndTest ();
		
		stop = System.currentTimeMillis();
		System.out.println("Elapsed time: " + (stop-start) + " milliseconds");
		System.out.println ("breadthFirstSearch:" ) ; start = System.currentTimeMillis();
		
		breadthFirstSearch ();
		
		stop = System.currentTimeMillis();
		System.out.println("Elapsed time: " + (stop-start) + " milliseconds");
	}
}
