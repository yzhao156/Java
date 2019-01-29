import java.util.Random;
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
    private static void setPlane0(){

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
        /** 
     * In this second method, we will select a plane at random.
     *  1) we select a line z = ax + by + c, with a, b and c 
     * randomly selected between -100 and +100 
     *  2) we add 5000 samples randomly selected on the plane
     * with x and y both randomly selected between 50 and 4000. 
     * For each sample we add a "noise" 
     * randomly selected between -20 and +20 (that is, for
     * each randomly selected x and y we add the sample 
     *[ (x,y), ax+by+c+noise).
     * where "noise" is randomly selected between -20 and 20
     *  4) we iterate gradient descent (find a number of iterations,
     * and a step alpha that seems to work, regularly printing
     * the target,  the current hypothesis and the current cost)
     */


    private static void randomPlane(){
        LinearRegression linearRegression = new LinearRegression(2,5000);
        Random generator = new java.util.Random();
        double a = generator.nextDouble()*200 - 100;
        double b = generator.nextDouble()*200 - 100;
        double c = generator.nextDouble()*200 - 100;
        double noise = generator.nextDouble()*40 - 20;
        for(int i=0; i<5000; i++){
            double[] temp = new double[2];
            double x1 = generator.nextDouble()*3950 + 50;
            double x2 = generator.nextDouble()*3950 + 50;
            temp[0] = x1;
            temp[1] = x2;
            double x3 = 0;
            x3 = a*x1 + b*x2 + c + noise;
            linearRegression.addSample(temp, x3);
        }
        for (int i=0; i<11; i++){//16->11
            System.out.println(i+" :  " +"Current hypothesis: "+linearRegression.currentHypothesis()+" Current cost: "+linearRegression.currentCost());
           
            linearRegression.gradientDescent(0.00000009, 2);//(0.00000009, 2)
        }
    }

    /** 
     * In this third method, we will follow the same approach
     * that the one followed in the method  randomPlane, but
     * this time we will have a variable number of dimensions,
     * specified by the parameter "dimension". We will
     * create 5000 samples of "dimension" dimension, where each
     * dimension will be ranmly selected between  -100 and +100,
     * and a randomly selected noise between -20 and +20 will be
     * added to the result.We will then iterate gradient descent 
     * (find a number of iterations,
     * and a step alpha that seems to work, regularly printing
     * the target,  the current hypothesis and the current cost)
     *
     * @param dimension the number of features
     */
    private static void randomDimension(int dimension){
        LinearRegression linearRegression = new LinearRegression(dimension,5000);
        Random generator = new java.util.Random();
        double[] t = new double[dimension+1]; 
        for (int i=0; i<dimension+1; i++){
            double temp = generator.nextDouble()*200 - 100;
            t[i] = temp;
        }
        for (int i=0; i<5000; i++){
        	double[] tempX = new double[dimension];
        	for(int j=0; j<dimension; j++){
        	double tempN = generator.nextDouble()*3950 + 50;
        	tempX[j] = tempN;
        	}
        	double tempR = t[0];
        	//cal r
        	for (int j=0; j<dimension; j++){
        		tempR += (t[j+1] * tempX[j]);
        	}	
        	double noise = generator.nextDouble()*40 - 20;
        	tempR += noise;
        	linearRegression.addSample(tempX, tempR);

        }
        for (int i=0; i<11; i++){//16->11

            System.out.println(i+" :  " +"Current hypothesis: "+linearRegression.currentHypothesis()+"\n"+"Current cost: "+linearRegression.currentCost());
           
            linearRegression.gradientDescent(0.000000004, 100);//(0.00000009, 10)
        }

    }


	public static void main(String[] args) {

        StudentInfo.display();

        System.out.println("randomDimension");
        randomDimension(50);

	}

}