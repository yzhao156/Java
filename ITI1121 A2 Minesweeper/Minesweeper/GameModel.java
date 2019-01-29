import java.util.Random;

/**
 * The class <b>GameModel</b> holds the model, the state of the systems. 
 * It stores the following information:
 * - the state of all the ``dots'' on the board (mined or not, clicked
 * or not, number of neighbooring mines...)
 * - the size of the board
 * - the number of steps since the last reset
 *
 * The model provides all of this informations to the other classes trough 
 *  appropriate Getters. 
 * The controller can also update the model through Setters.
 * Finally, the model is also in charge of initializing the game
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */
public class GameModel {


     // ADD YOUR INSTANCE VARIABLES HERE
    private int[] tempX;
    private int[] tempY;
    private static boolean[][] flags;
    private DotInfo[][] points;
    private int widthOfGame;
    private int heigthOfGame;
    private int numberOfMines;
    private int numberOfSteps;

    /**
     * Constructor to initialize the model to a given size of board.
     * 
     * @param width
     *            the width of the board
     * 
     * @param heigth
     *            the heigth of the board
     * 
     * @param numberOfMines
     *            the number of mines to hide in the board
     */
    public GameModel(int width, int heigth, int numberOfMines) {
    // ADD YOU CODE HERE
        
        this.widthOfGame = width;
        this.heigthOfGame = heigth;
        this.numberOfMines= numberOfMines;
        this.numberOfSteps = 0;
        this.points = new DotInfo[width][heigth]; 
        this.flags = new boolean[width][heigth];       
        this.reset();


    }


 
    /**
     * Resets the model to (re)start a game. The previous game (if there is one)
     * is cleared up . 
     */

    public void reset(){

        
    // ADD YOU CODE HERE
        numberOfSteps = 0;
        for (int a=0; a<widthOfGame; a++) {
            for (int b=0; b<heigthOfGame; b++){
                points[a][b] = new DotInfo(a,b);
                flags[a][b] = false;
            }
        }

        int tempNum=0;
        double tdx, tdy;
        int tix, tiy;
        java.util.Random generator = new java.util.Random();

        while (tempNum<numberOfMines) {
            //initilize
            //generate random mines
            tdx = generator.nextDouble();
            tix = (int)(tdx*widthOfGame);
            tdy = generator.nextDouble();
            tiy = (int)(tdy*heigthOfGame);
            points[tix][tiy].setMined();
            //check mines
            tempNum = 0;
            for (int x=0; x<widthOfGame; x++) {
                for (int y=0; y<heigthOfGame; y++){
                    if (points[x][y].isMined()) {
                        tempNum ++;
                    }
                }
            }
        }
        // neighbormines
        for ( int i=0; i<widthOfGame; i++) {
            for (int j=0; j<heigthOfGame; j++){
                int tempMines = 0;
                //if mine set 10
                if (points[i][j].isMined()) {
                    points[i][j].setMined();
                }
                //check else 8 zone nbm
                if (i==0&&j==heigthOfGame-1) {
                    if (points[i+1][j].isMined()) {
                        tempMines ++;
                    }
                    if (points[i+1][j-1].isMined()) {
                        tempMines ++;
                    }
                    if (points[i][j-1].isMined()) {
                        tempMines ++;
                    }
                    points[i][j].setNeighbooringMines(tempMines);
                } else if (i==0&&j==0) {
                    if (points[i+1][j+1].isMined()) {
                        tempMines ++;
                    }
                    if (points[i+1][j].isMined()) {
                        tempMines ++;
                    }
                    if (points[i][j+1].isMined()) {
                        tempMines ++;
                    }
                    points[i][j].setNeighbooringMines(tempMines);
                    
                } else if (i==widthOfGame-1&&j==0) {
                        if (points[i][j+1].isMined()) {
                        tempMines ++;
                    }           
                    if (points[i-1][j+1].isMined()) {
                        tempMines ++;
                    }
                    if (points[i-1][j].isMined()) {
                        tempMines ++;
                    }
                    points[i][j].setNeighbooringMines(tempMines);
                } else if (i==widthOfGame-1&&j==heigthOfGame-1) {
                    if (points[i][j-1].isMined()) {
                        tempMines ++;
                    }
                    if (points[i-1][j].isMined()) {
                        tempMines ++;
                    }
                    if (points[i-1][j-1].isMined()) {
                        tempMines ++;
                    }
                    points[i][j].setNeighbooringMines(tempMines);
                } else if (i==0) {
                    if (points[i+1][j+1].isMined()) {
                        tempMines ++;
                    }
                    if (points[i+1][j].isMined()) {
                        tempMines ++;
                    }
                    if (points[i+1][j-1].isMined()) {
                        tempMines ++;
                    }
                    if (points[i][j+1].isMined()) {
                        tempMines ++;
                    }
                    if (points[i][j-1].isMined()) {
                        tempMines ++;
                    }
                    points[i][j].setNeighbooringMines(tempMines);
                } else if (j==0) {
                    if (points[i+1][j+1].isMined()) {
                        tempMines ++;
                    }
                    if (points[i+1][j].isMined()) {
                        tempMines ++;
                    }
                    
                    if (points[i][j+1].isMined()) {
                        tempMines ++;
                    }
                    
                    if (points[i-1][j+1].isMined()) {
                        tempMines ++;
                    }
                    if (points[i-1][j].isMined()) {
                        tempMines ++;
                    }
                    points[i][j].setNeighbooringMines(tempMines);
                    
                } else if (i==widthOfGame-1) {
                   
                    if (points[i][j+1].isMined()) {
                        tempMines ++;
                    }
                    if (points[i][j-1].isMined()) {
                        tempMines ++;
                    }
                    if (points[i-1][j+1].isMined()) {
                        tempMines ++;
                    }
                    if (points[i-1][j].isMined()) {
                        tempMines ++;
                    }
                    if (points[i-1][j-1].isMined()) {
                        tempMines ++;
                    }
                    points[i][j].setNeighbooringMines(tempMines);
                } else if (j==heigthOfGame-1) {
                    if (points[i+1][j].isMined()) {
                        tempMines ++;
                    }
                    if (points[i+1][j-1].isMined()) {
                        tempMines ++;
                    }
                    if (points[i][j-1].isMined()) {
                        tempMines ++;
                    }
                    if (points[i-1][j].isMined()) {
                        tempMines ++;
                    }
                    if (points[i-1][j-1].isMined()) {
                        tempMines ++;
                    }
                    points[i][j].setNeighbooringMines(tempMines);
                } else {



                if (points[i+1][j+1].isMined()) {
                    tempMines ++;
                }
                if (points[i+1][j].isMined()) {
                    tempMines ++;
                }
                if (points[i+1][j-1].isMined()) {
                    tempMines ++;
                }
                if (points[i][j+1].isMined()) {
                    tempMines ++;
                }
                if (points[i][j-1].isMined()) {
                    tempMines ++;
                }
                if (points[i-1][j+1].isMined()) {
                    tempMines ++;
                }
                if (points[i-1][j].isMined()) {
                    tempMines ++;
                }
                if (points[i-1][j-1].isMined()) {
                    tempMines ++;
                }
                
            }
                points[i][j].setNeighbooringMines(tempMines);
            }
        }
    }
        
        






    public boolean getFlaged(int i, int j) {
        return flags[i][j];
    }

    public void setFlaged(int i, int j) {
        flags[i][j] = !flags[i][j];
    }

    

    /**
     * Getter method for the heigth of the game
     * 
     * @return the value of the attribute heigthOfGame
     */   
    public int getHeigth(){
        
    // ADD YOU CODE HERE
        return heigthOfGame;

    }

    /**
     * Getter method for the width of the game
     * 
     * @return the value of the attribute widthOfGame
     */   
    public int getWidth(){
        
    // ADD YOU CODE HERE
        return widthOfGame;

    }



    /**
     * returns true if the dot at location (i,j) is mined, false otherwise
    * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isMined(int i, int j){
        
    // ADD YOU CODE HERE
        return points[i][j].isMined();

    }

    /**
     * returns true if the dot  at location (i,j) has 
     * been clicked, false otherwise
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean hasBeenClicked(int i, int j){
        
    // ADD YOU CODE HERE
         return points[i][j].hasBeenClicked();

    }

  /**
     * returns true if the dot  at location (i,j) has zero mined 
     * neighboor, false otherwise
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isBlank(int i, int j){
        
    // ADD YOU CODE HERE
        return points[i][j].getNeighbooringMines() == 0;
    }
    /**
     * returns true if the dot is covered, false otherwise
    * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isCovered(int i, int j){
        
    // ADD YOU CODE HERE
        return points[i][j].isCovered();

    }

    /**
     * returns the number of neighbooring mines os the dot  
     * at location (i,j)
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the number of neighbooring mines at location (i,j)
     */   
    public int getNeighbooringMines(int i, int j){
        
    // ADD YOU CODE HERE
        

        return points[i][j].getNeighbooringMines();
        

    }


    /**
     * Sets the status of the dot at location (i,j) to uncovered
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */   
    public void uncover(int i, int j){
        
    // ADD YOU CODE HERE
        points[i][j].uncover();

    }

    /**
     * Sets the status of the dot at location (i,j) to clicked
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */   
    public void click(int i, int j){
        
    // ADD YOU CODE HERE
        points[i][j].click();

    }
     /**
     * Uncover all remaining covered dot
     */   
    public void uncoverAll(){
        
    // ADD YOU CODE HERE
        for (int x=0; x<widthOfGame; x++) {
            for (int y=0; y<heigthOfGame; y++){
                points[x][y].uncover();
            }
        }

    }

 

    /**
     * Getter method for the current number of steps
     * 
     * @return the current number of steps
     */   
    public int getNumberOfSteps(){
        
    // ADD YOU CODE HERE
        return numberOfSteps;

    }

  

    /**
     * Getter method for the model's dotInfo reference
     * at location (i,j)
     *
      * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     *
     * @return model[i][j]
     */   
    public DotInfo get(int i, int j) {
        
    // ADD YOU CODE HERE
        return points[i][j];

    }


   /**
     * The metod <b>step</b> updates the number of steps. It must be called 
     * once the model has been updated after the payer selected a new square.
     */
     public void step(){
        
    // ADD YOU CODE HERE
        numberOfSteps++;
    }
 
   /**
     * The metod <b>isFinished</b> returns true iff the game is finished, that
     * is, all the nonmined dots are uncovered.
     *
     * @return true if the game is finished, false otherwise
     */
    public boolean isFinished(){
        
    // ADD YOU CODE HERE
        for (int x=0; x<widthOfGame; x++) {
            for (int y=0; y<heigthOfGame; y++){
                if(points[x][y].isCovered()&&(!points[x][y].isMined())){
                    return false;
                }
            }
        }
        return true;


    }


   /**
     * Builds a String representation of the model
     *
     * @return String representation of the model
     */
    public String toString(){
        
    // ADD YOU CODE HERE
        return "numberOfSteps: "+numberOfSteps;

    }
}
