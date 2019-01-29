import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.*;


/**
 * The class <b>GameController</b> is the controller of the game. It is a listener
 * of the view, and has a method <b>play</b> which computes the next
 * step of the game, and  updates model and view.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */


public class GameController implements ActionListener {

    // ADD YOUR INSTANCE VARIABLES HERE
    private GameModel gameModel;
    private int width;
    private int height; 
    private int numberOfMines;
    private GameView gameView;
    

    /**
     * Constructor used for initializing the controller. It creates the game's view 
     * and the game's model instances
     * 
     * @param width
     *            the width of the board on which the game will be played
     * @param height
     *            the height of the board on which the game will be played
     * @param numberOfMines
     *            the number of mines hidden in the board
     */
    public GameController(int width, int height, int numberOfMines) {

    // ADD YOU CODE HERE
    	this.height = width;
    	this.width = height;
    	this.numberOfMines = numberOfMines;

    	
    	
        gameModel = new GameModel(this.width, this.height, this.numberOfMines);

        gameView = new GameView(gameModel, this);

        
   

        //System.out.println("Controller Constructor ok");
    }


    /**
     * Callback used when the user clicks a button (reset or quit)
     *
     * @param e
     *            the ActionEvent
     */

    public void actionPerformed(ActionEvent e) {
        
    // ADD YOU CODE HERE
        if (e.getSource() == gameView.reset) {
        	reset();
        } else if(e.getSource()==gameView.quit) {
        	System.exit(0);
        }
        for (int x=0; x<width; x++) {
           for (int y=0; y<height; y++){
               if (e.getSource()== gameView.dotButton[x][y]) {
                    if (!gameModel.get(x,y).hasBeenClicked()){
                        play(x,y);
                    }
        	   }
            }
        }
    }

    /**
     * resets the game
     */
    private void reset(){

    // ADD YOU CODE HERE
        gameModel.reset();
        gameView.update();
        gameView.repaint();
    }

    /**
     * <b>play</b> is the method called when the user clicks on a square.
     * If that square is not already clicked, then it applies the logic
     * of the game to uncover that square, and possibly end the game if
     * that square was mined, or possibly uncover some other squares. 
     * It then checks if the game
     * is finished, and if so, congratulates the player, showing the number of
     * moves, and gives to options: start a new game, or exit
     * @param width
     *            the selected column
     * @param heigth
     *            the selected line
     */
    private void play(int width, int heigth){

    // ADD YOU CODE HERE
    	if (!gameModel.getFlaged(width,heigth)){

    	
	        gameModel.get(width,heigth).click();
	        gameModel.get(width,heigth).uncover();
	        gameModel.step();
	        gameView.update();
	        gameView.repaint();

	        if (gameModel.get(width,heigth).isMined()) {
	            gameModel.uncoverAll();
	            gameView.update();
	            gameView.repaint();
	            Object[] options ={ "Quit", "Play Again" };  
	            int m = JOptionPane.showOptionDialog(null, "Aouch, you lost in "+gameModel.getNumberOfSteps()+" steps!\nWould you like to play again?", "Boom!",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);  
	            if (m==0) {
	                System.exit(0);
	            } else {
	                reset();
	            }
	        }

	        else if (gameModel.isFinished()) {
	            gameModel.uncoverAll();
	            gameView.update();
	            gameView.repaint();
	            Object[] options ={ "Quit", "Play Again" };  
	            int m = JOptionPane.showOptionDialog(null, "Congratulations, you won in "+gameModel.getNumberOfSteps()+" steps!\nWould you like to play again?", "Won",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);  
	            if (m==0) {
	                System.exit(0);
	            } else {
	                reset();
	            }
	        }

	        else if (gameModel.isBlank(width,heigth)) {
	            clearZone(gameModel.get(width,heigth));
	            gameView.update();
	            gameView.repaint();
	        }
	    }
    }

   /**
     * <b>clearZone</b> is the method that computes which new dots should be ``uncovered'' 
     * when a new square with no mine in its neighborood has been selected
     * @param initialDot
     *      the DotInfo object corresponding to the selected DotButton that
     * had zero neighbouring mines
     */


    private void clearZone(DotInfo initialDot) {


    // ADD YOU CODE HERE
        GenericArrayStack<DotInfo> stack = new GenericArrayStack<DotInfo>(width*height);
        stack.push(initialDot);

        
        while(!stack.isEmpty()){
            DotInfo temp = stack.pop();
            if (((temp.getX()+1)<width)&&((temp.getY()+1)<height)) {
                if (gameModel.isCovered(temp.getX()+1,temp.getY()+1)) {
                    gameModel.get(temp.getX()+1,temp.getY()+1).uncover();
                    gameModel.get(temp.getX()+1,temp.getY()+1).click();
                    if (gameModel.isBlank(temp.getX()+1,temp.getY()+1)) {
                        stack.push(gameModel.get(temp.getX()+1,temp.getY()+1));
                    }
                }   
            }
            if (((temp.getX()+1)<width)&&((true))) {
                if (gameModel.isCovered(temp.getX()+1,temp.getY())) {
                    gameModel.get(temp.getX()+1,temp.getY()).uncover();
                    gameModel.get(temp.getX()+1,temp.getY()).click();
                    if (gameModel.isBlank(temp.getX()+1,temp.getY())) {
                        stack.push(gameModel.get(temp.getX()+1,temp.getY()));
                    }
                }
            }
            if (((temp.getX()+1)<width)&&((temp.getY()-1)>=0)) {
                if (gameModel.isCovered(temp.getX()+1,temp.getY()-1)) {
                    gameModel.get(temp.getX()+1,temp.getY()-1).uncover();
                    gameModel.get(temp.getX()+1,temp.getY()-1).click();
                    if (gameModel.isBlank(temp.getX()+1,temp.getY()-1)) {
                        stack.push(gameModel.get(temp.getX()+1,temp.getY()-1));
                    }
                }
            }
            if ((true)&&((temp.getY()+1)<height)) {
                if (gameModel.isCovered(temp.getX(),temp.getY()+1)) {
                    gameModel.get(temp.getX(),temp.getY()+1).uncover();
                    gameModel.get(temp.getX(),temp.getY()+1).click();
                    if (gameModel.isBlank(temp.getX(),temp.getY()+1)) {
                        stack.push(gameModel.get(temp.getX(),temp.getY()+1));
                    }
                }
            }
            if ((true)&&((temp.getY()-1)>=0)) {
                if (gameModel.isCovered(temp.getX(),temp.getY()-1)) {
                    gameModel.get(temp.getX(),temp.getY()-1).uncover();
                    gameModel.get(temp.getX(),temp.getY()-1).click();
                    if (gameModel.isBlank(temp.getX(),temp.getY()-1)) {
                        stack.push(gameModel.get(temp.getX(),temp.getY()-1));
                    }
                }
            }
            if (((temp.getX()-1)>=0)&&((temp.getY()+1)<height)) {
                if (gameModel.isCovered(temp.getX()-1,temp.getY()+1)) {
                    gameModel.get(temp.getX()-1,temp.getY()+1).uncover();
                    gameModel.get(temp.getX()-1,temp.getY()+1).click();
                    if (gameModel.isBlank(temp.getX()-1,temp.getY()+1)) {
                        stack.push(gameModel.get(temp.getX()-1,temp.getY()+1));
                    }
                }
            }
            if (((temp.getX()-1)>=0)){
                if (gameModel.isCovered(temp.getX()-1,temp.getY())) {
                    gameModel.get(temp.getX()-1,temp.getY()).uncover();
                    gameModel.get(temp.getX()-1,temp.getY()).click();
                    if (gameModel.isBlank(temp.getX()-1,temp.getY())) {
                        stack.push(gameModel.get(temp.getX()-1,temp.getY()));
                    }
                }
            }
            if (((temp.getX()-1)>=0)&&((temp.getY()-1)>=0)) {
                if (gameModel.isCovered(temp.getX()-1,temp.getY()-1)) {
                    gameModel.get(temp.getX()-1,temp.getY()-1).uncover();
                    gameModel.get(temp.getX()-1,temp.getY()-1).click();
                    if (gameModel.isBlank(temp.getX()-1,temp.getY()-1)) {
                        stack.push(gameModel.get(temp.getX()-1,temp.getY()-1));
                    }
                }
            }
        }
    }




}
