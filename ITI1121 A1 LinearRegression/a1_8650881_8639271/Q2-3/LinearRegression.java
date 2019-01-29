/**
 * The class  <b>LinearRegression</b> implements gradient
 * descent for multiple variables
 *
 * @author gvj (gvj@eecs.uottawa.ca)
 *
 */

public class LinearRegression{


	/** 
     * Number of features (usually "n" in litterature) 
     */
	private int nbreOfFeatures;

	/** 
     * Number of samples (usually "m" in litterature) 
     */
	private int nbreOfSamples;


	/** 
     * the nbreOfFeatures X nbreOfSamples Matrix of samples
     */
	private double[][] samplesMatrix;//(x1,x2)

	/** 
     * the nbreOfSamples Matrix of samples target values
     */
	private double[] samplesValues;//y

	/** 
     * the nbreOfFeatures Matrix theta of current hypthesis function
     */
	private double[] theta;


	/** 
     * number of samples received so far
     */
	private int currentNbreOfSamples;

	/** 
     * a place holder for theta during descent calculation
     */
	private double[] tempTheta;


	/** 
     * counts how many iterations have been performed
     */
	private int iteration;


	/** 
     * Object's contructor. The constructor initializes the instance
     * variables. The starting hypthesis is theta[i]=0.0 for all i
     * 
     * @param n the number of features that we will have
     * @param m the number of samples that we will have
	 *
     */
 	public LinearRegression(int n, int m){

		// your code goes there
		iteration = 0;
		currentNbreOfSamples = 0;
		theta = new double[n+1];
		tempTheta = new double[n+1];

		nbreOfFeatures = n+1;
		nbreOfSamples = m;
		samplesMatrix = new double [0] [n+1];


	}

	/** 
     * Add a new sample to samplesMatrix and samplesValues
     * 
     * @param x the vectors of samples
     * @param y the coresponding expected value
     *
	 */
	public void addSample(double[] x, double y){

		// your code goes there
		double[][] tempSamplesMatrix = new double[currentNbreOfSamples+1][nbreOfFeatures];
		double[] tempSamplesValues = new double [currentNbreOfSamples+1];
		for (int i=0; i<currentNbreOfSamples; i++){
			tempSamplesMatrix[i] = samplesMatrix[i];
			tempSamplesValues[i] = samplesValues[i];
		}
		//change x;
		double[] tempX = new double[x.length+1];
		for (int i=0; i<x.length; i++){
			tempX[i+1] = x[i];
		}
		tempX[0] = 1;
		tempSamplesValues[currentNbreOfSamples] = y;
		tempSamplesMatrix[currentNbreOfSamples] = tempX;		
		samplesValues = tempSamplesValues;
		samplesMatrix = tempSamplesMatrix;
		currentNbreOfSamples++;



	}

	/** 
     * Returns the current hypothesis for the value of the input
     * @param x the input vector for which we want the current hypothesis
     * 
	 * @return h(x)
	 */

	private double hypothesis(double[] x){

		// your code goes there
		double tempHypothesis = 0;
		for (int i=0; i<nbreOfFeatures; i++){
			tempHypothesis += ((theta[i])*x[i]);
		}
		return tempHypothesis;


	}

	/** 
     * Returns a string representation of hypthesis function
     * 
	 * @return the string "theta0 x_0 + theta1 x_1 + .. thetan x_n"
	 */

	public String currentHypothesis(){

		// your code goes there
		String tempString = "";
		tempString += theta[0]+" +";
		for (int i=1; i<nbreOfFeatures-1; i++){
			tempString += (theta[i]+"x_"+i+" +");
		}
		tempString += theta[nbreOfFeatures-1] + "x_"+(nbreOfFeatures-1);
		
		return tempString;

	}

	/** 
     * Returns the current cost
     * 
	 * @return the current value of the cost function
	 */

	public double currentCost(){

		// your code goes there
		double temp = 0;
		for (int i=0; i<currentNbreOfSamples; i++){
			temp += (hypothesis(samplesMatrix[i])-samplesValues[i])*(hypothesis(samplesMatrix[i])-samplesValues[i]);
		}
		return temp/currentNbreOfSamples;

	}

	/** 
     * runs several iterations of the gradient descent algorithm
     * 
     * @param alpha the learning rate
     *
     * @param numberOfSteps how many iteration of the algorithm to run
     */

	public void gradientDescent(double alpha, int numberOfSteps) {


		// your code goes there
		nbreOfFeatures = samplesMatrix[0].length;
		for (int i=0; i<numberOfSteps; i++){
			for (int j=0; j<nbreOfFeatures; j++){// I
				double temp = 0;
				for (int k=0; k<nbreOfSamples; k++){
					temp += (hypothesis(samplesMatrix[k])-samplesValues[k])*samplesMatrix[k][j];
				}
				tempTheta[j] -= 2*alpha*temp/nbreOfSamples;

			}
			for (int swap=0;swap<theta.length;swap++){
				theta[swap]=tempTheta[swap];
			}
		iteration += 1;
		}

	}


	/** 
     * Getter for theta
     *
	 * @return theta
	 */

	public double[] getTheta(){

		// your code goes there
		return theta;

	}

	/** 
     * Getter for iteration
     *
	 * @return iteration
	 */

	public int getIteration(){

		// your code goes there
		return iteration;

	}
}