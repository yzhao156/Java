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
     * Random generator 
     */
	private static java.util.Random generator = new java.util.Random();

		/** 
     * In this second method, we will select a line at random.
     * 	1) we select a line y = ax + b, with a randomly selected
     * between -100 and +100 and b randomly selected between 
     * -250 and +250
     * 	2) we add 500 samples randomly selected on the line
     * between -100 and +300. For each sample we add a "noise" 
     * randomly selected between -1000 and +1000 (that is, for
     * each randomly selected x, we add the sample (x, ax+b+noise).
     * where "noise" is randomly selected between -1000 and 1000
     *  3) We create an instance of Display
     *  4) we iterate gradient descent (find a number of iterations,
     * a number of updates to the instance of Display, and a 
     * step alpha that seems to work
     */
	


	public static void randomLine(){
		LinearRegression linearRegression = new LinearRegression(500);
		double a = generator.nextDouble()*200 - 100;
		double b = generator.nextDouble()*500 - 250;
		Display display = new Display(linearRegression);
		display.setTarget(a,b);//ab
		for (int i=0; i<500; i++){
			double x = generator.nextDouble()*400 - 100;
			double tempMin = a*x+b-1000;
			double tempMax = a*x+b+1000;
			double y = generator.nextDouble()*(tempMax-tempMin)+tempMin;
			linearRegression.addSample(x,y);
			//display.update();
			//linearRegression.gradientDescent(0.000000006, 200);
		}
		for (int i=0; i<51; i++){
			display.update();
			System.out.println("Aming for: "+a+" + "+b+"x_1");
			System.out.println("Current Hypothesis: "+linearRegression.currentHypothesis());
			System.out.println("Current Cost: "+linearRegression.currentCost());
			linearRegression.gradientDescent(0.0000016, 10);

			//display.update();

		}
	}



	public static void main(String[] args) {

	    StudentInfo.display();

		System.out.println("randomLine");
		randomLine();

	}

}