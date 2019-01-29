import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * The class <b>GameView</b> provides the current view of the entire Game. It extends
 * <b>JFrame</b> and lays out an instance of  <b>BoardView</b> (the actual game) and 
 * two instances of JButton. The action listener for the buttons is the controller.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */

public class GameView extends JFrame {

     // ADD YOUR INSTANCE VARIABLES HERE
    private GameModel gameModel;
    private GameController gameController;
    public DotButton[][] dotButton;
    public JButton reset;
    public JButton quit;
    private Label label;



    /**
     * Constructor used for initializing the Frame
     * 
     * @param gameModel
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */

    public GameView(GameModel gameModel, GameController gameController) {
        
    // ADD YOU CODE HERE
        //title
        super("MineSweeper");
        this.gameModel = gameModel;
        this.gameController = gameController;
        setSize(gameModel.getHeigth()*40,gameModel.getWidth()*40);
        setLocationRelativeTo(null);

        
        JPanel myPanel = new JPanel();
        JPanel mine = new JPanel();        

        Dimension dim = getToolkit().getScreenSize();
        int dimWidth = (int) (dim.width/-90);
        int dimHeight = (int) (dim.height/-64);
        mine.setLayout(new GridLayout(gameModel.getWidth(),gameModel.getHeigth(),dimWidth,dimHeight));
        
        reset = new JButton("Reset");
        quit = new JButton("Quit");
        label = new Label(gameModel.toString());
        label.setPreferredSize(new Dimension(150,30));

        myPanel.add(label);
        myPanel.add(reset);
        myPanel.add(quit);
        add(myPanel, BorderLayout.SOUTH);
        add(mine, BorderLayout.NORTH);
        reset.addActionListener(gameController);
        quit.addActionListener(gameController);
      
        dotButton = new DotButton[gameModel.getWidth()][gameModel.getHeigth()];
    
        for (int x=0; x<gameModel.getWidth(); x++) {
            for (int y=0; y<gameModel.getHeigth(); y++){
                    dotButton[x][y] = new DotButton(x,y,11);
                    final int row = x;
                    final int col = y;
                    dotButton[x][y].addActionListener(gameController);
                    dotButton[x][y].addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            gameModel.setFlaged(row, col);
                            update();
                            repaint();
                        }
                    }
                    
                });
                        mine.add((dotButton[x][y]));
            }
        }
    

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);



    }



    /**
     * update the status of the board's DotButton instances based 
     * on the current game model, then redraws the view
     */

    public void update(){
        
    // ADD YOU CODE HERE
        label.setText(gameModel.toString());
        for (int c=0; c<gameModel.getWidth(); c++) {
            for (int d=0; d<gameModel.getHeigth(); d++){
                dotButton[c][d].setIconNumber(getIcon(c,d));

            }
        }

    }

    /**
     * returns the icon value that must be used for a given dot 
     * in the game
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the icon to use for the dot at location (i,j)
     */   
    private int getIcon(int i, int j){
        
    // ADD YOU CODE HERE
        if (gameModel.get(i,j).isCovered()) {
            if (gameModel.getFlaged(i, j)) {
                        return 12;
                    } else {
                        return 11;
                    }
        
            } else if (gameModel.get(i,j).isMined()) {


                if (gameModel.get(i,j).hasBeenClicked()) {
                    return 10;

                } else{
                    return 9;
                }


            } else {
                return gameModel.getNeighbooringMines(i,j);
            }

    }


}
