/**
 * The class  <b>Assignment</b> is used to
 * test our LinearRegression class. 
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
     * In this first method, we are simply using sample points that are
     * on a straight plane. We will use the plane z= x + 2x.
     * In his method, 
     * 	1) we create an instance of LinearRegression.
     * 	2) we add 2,000 samples from the plane z= x + 2x as follows:
     * 		add the sample [(i, 2i), 5i] for 0<=i<=999
     * 		add the sample [(2i, i), 4i] for 0<=i<=999
     *  3) we iterate gradient descent 10,000, printing out the
     * current hypothesis and the current cost every 1,000 
     * iterations, using a step alpha of 0.000000003
     */
    private static void setPlane(){

		// your code goes there
        LinearRegression linearRegression = new LinearRegression(2,2000);
        for (int i=0; i<1000; i++){
            double[] temp1 = new double[2];
            temp1[0] = i;
            temp1[1] = 2*i;
            double[] temp2 = new double[2];
            temp2[0] = 2*i;
            temp2[1] = i;
            linearRegression.addSample(temp1,5*i);
            linearRegression.addSample(temp2,4*i);
        }
        for (int i=0; i<11; i++){
            System.out.println("Current hypothesis: "+linearRegression.currentHypothesis()+" Current cost: "+linearRegression.currentCost());
           
            linearRegression.gradientDescent(0.000000003, 1000);

        }


	}




	public static void main(String[] args) {

		StudentInfo.display();

		System.out.println("setPlane");
		setPlane();


	}

}