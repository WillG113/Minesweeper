import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static java.awt.BorderLayout.*;

public class GUI extends JFrame {
    static int rows = 2;
    static int cols = 3;
    JButton restartButton;
    JLabel label2 = new JLabel("");
    boolean restart;




        public GUI(int x, int y) {

            startGame(x, y);

        }

        public void startGame(int x, int y) {
            Board test = new Board(x, y);
            test.createBoard();
            //test.printBoard();

            Container panel = getContentPane();

            Container buttonPane = new Container();
            buttonPane.setLayout(new GridLayout(x, y));

            Container labelContainer = new Container();
            labelContainer.setLayout(new BorderLayout());

            JLabel label= new JLabel("Minesweeper");
            label.setHorizontalAlignment(JLabel.CENTER);
            labelContainer.add(label, PAGE_START);

            label2.setText("");
            label2.setHorizontalAlignment(JLabel.CENTER);
            labelContainer.add(label2, PAGE_END);

            panel.add(labelContainer, PAGE_START);

            restartButton = new JButton("Restart");
            restartButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    resetGame();
                    startGame(x,y);
                }
            });


            JButton[][] buttonArray = new JButton[test.getX()][test.getX()];

            for (int i = 0; i < test.getX(); i++) {
                for (int j = 0; j < test.getX(); j++) {
                    buttonArray[i][j] = new JButton(".");
                }
            }

            int j = 0;
            int k = 0;

            for (int i = 0; i < (test.getX() * test.getX()); i++) {
                int l = j;
                int m = k;
                JButton button = buttonArray[l][m];
                button.setBackground(Color.BLACK);
                button.setForeground(Color.WHITE);

                button.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                    }



                    @Override
                    public void mousePressed(MouseEvent e) {

                        if (SwingUtilities.isLeftMouseButton(e)) {
                            hit(button, l, m, test, buttonArray);
                        }

                        if (SwingUtilities.isRightMouseButton(e)) {
                            test.setFlag(test.getBoard()[l][m]);
                            if (!test.getBoard()[l][m].isClear()) {
                                if (test.getBoard()[l][m].isFlagged()) {
                                    button.setBackground(Color.YELLOW);
                                    button.setForeground(Color.WHITE);
                                    button.setText("F");
                                } else {
                                    button.setBackground(Color.BLACK);
                                    button.setForeground(Color.WHITE);
                                    button.setText(".");
                                }
                                //test.printBoard();
                                checkVictory(buttonArray, test);
                            }
                        }

                        if (SwingUtilities.isMiddleMouseButton(e)) {
                            //test.bulk(test.getBoard()[l][m].getX(), test.getBoard()[l][m].getX());
                            bulkhit(l, m, buttonArray, test);
                        }

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
                buttonPane.add(button);

                k++;
                if (k == test.getX()) {
                    k = 0;
                    j++;
                }

            }

            panel.add(buttonPane, CENTER);
            panel.add(restartButton, PAGE_END);
            restartButton.setVisible(false);
        }

        public void resetGame(){
            Container panel = getContentPane();
            panel.removeAll();
        }

    // TODO Add some restart check

    public void hit(JButton button, int l, int m, Board test, JButton[][] buttonArray){
        button.setBackground(Color.GRAY);
        button.setForeground(Color.BLACK);
        //button.setEnabled(false);
        test.hit(l, m, false);
        if (test.getBoard()[l][m].checkBomb()) {
            button.setBackground(Color.RED);
            button.setForeground(Color.BLACK);
        } else if (test.getBoard()[l][m].getTouching() == 0) {
            button.setText("");
            recursion(l, m, test.getX(), buttonArray, test);
        } else {
            button.setText(Integer.toString(test.getBoard()[l][m].getTouching()));
        }
        //test.printBoard();
        checkVictory(buttonArray, test);
    }

        public void recursion(int l, int m, int x, JButton[][] buttonArray, Board test){
            for(int i = -1; i <= 1; i++) {
                for(int j = -1; j <= 1; j++) {
                    if(l+j >= 0 && m+i < test.getX() && m+i >= 0 && j+l < test.getX()) {
                        if(!buttonArray[l+j][m+i].getText().equals("") && test.getBoard()[l+j][m+i].getTouching() == 0 && !test.getBoard()[l+j][m+i].checkBomb()  && !test.getBoard()[l+j][m+i].isFlagged()) {
                            buttonArray[l + j][m + i].setText("");
                            buttonArray[l + j][m + i].setBackground(Color.GRAY);
                            buttonArray[l + j][m + i].setForeground(Color.BLACK);
                            //buttonArray[l + j][m + i].setEnabled(false);
                            recursion(l + j, m + i, x, buttonArray, test);
                        } else if (!buttonArray[l+j][m+i].getText().equals("") && test.getBoard()[l+j][m+i].getTouching() > 0 && !test.getBoard()[l+j][m+i].checkBomb() && !test.getBoard()[l+j][m+i].isFlagged()) {
                            buttonArray[l + j][m + i].setBackground(Color.GRAY);
                            buttonArray[l + j][m + i].setForeground(Color.BLACK);
                            //buttonArray[l + j][m + i].setEnabled(false);
                            buttonArray[l + j][m + i].setText(Integer.toString(test.getBoard()[l+j][m+i].getTouching()));
                        }
                    }
                }
            }
        }

        public void flagAllBombs(JButton[][] buttonArray, Board test) {
            for(int i = 0; i < test.getX(); i++) {
                for(int j = 0; j < test.getX(); j++) {
                    if(test.getBoard()[i][j].checkBomb()) {
                        buttonArray[i][j].setBackground(Color.RED);
                        buttonArray[i][j].setForeground(Color.BLACK);
                    }
                }
            }
        }

        public void checkVictory(JButton[][] buttonArray, Board test) {
            test.checkVictory();
            if(test.getGameOver()){
                if(test.victoryState() == 1) {
                    label2.setText("YOU WON! YOU FLAGGED THE MINES");
                } else if (test.victoryState() == 2) {
                    label2.setText("YOU WON! YOU CLEARED ALL THE TILES");
                } else {
                    test.flagAllBombs();
                    flagAllBombs(buttonArray, test);
                    label2.setText("YOU LOST!");
                }
                restartButton.setVisible(true);
            }
        }


        public void bulkhit(int l, int m, JButton[][] buttonArray, Board test){
            for(int i = -1; i <= 1; i++) {
                for(int j = -1; j <= 1; j++) {
                    if(l+j >= 0 && l+j < test.getX() && m+i >= 0 && m+i < test.getX()) {
                        if(!buttonArray[l+j][m+i].getText().equals("F") && !test.getBoard()[l+j][m+i].isClear()) {
                            hit(buttonArray[l+j][m+i], l+j, m+i, test, buttonArray);
                        }
                        //test.printBoard();

                        checkVictory(buttonArray, test);
                    }
                }
            }
        }
    }


