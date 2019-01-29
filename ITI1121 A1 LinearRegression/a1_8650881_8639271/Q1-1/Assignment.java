/*
 * The class  <b>Assignment</b> is used to
 * test our LinearRegression class. It uses the
 * provided class <b>Display</b> to show the results
 *
 * @author gvj (gvj@eecs.uottawa.ca)
 *
 */

public class Assignment {



	

	/** 
     * In this first method, we are simply using sample points that are
     * on a straight line, namely y = x;
     * In his method, 
     * 	1) we create an instance of LinearRegression.
     * 	2) we add 1,000 samples (from 0 to 999) from the line y=x
     *  3) We create an instance of Display
     *  4) we iterate gradient descent 5,000, updating the instance
     * of Display every 100 iteration, using a step alpha of 0.000000003
     */

	private static void setLine(){
		LinearRegression linearRegression = new LinearRegression(1000);
		for (int i=0; i<1000; i++){
			linearRegression.addSample(i,i);
		}
		
		Display display = new Display(linearRegression);
		for (int i=0; i<51; i++){
			System.out.println("Current hypothesis: "+linearRegression.currentHypothesis());
			System.out.println("Current cost: "+linearRegression.currentCost());
			display.update();
			linearRegression.gradientDescent(0.000000003, 100);

			
		}
		
		
		// your code goes there

	}



	public static void main(String[] args) {

	    StudentInfo.display();

		System.out.println("setLine");
		setLine();

	}

}