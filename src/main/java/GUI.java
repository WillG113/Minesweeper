import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static java.awt.BorderLayout.*;

public class GUI extends JFrame {
    private JButton restartButton;
    private final JLabel winLabel = new JLabel("");
    private Board gameBoard;
    private JButton[][] buttonArray;
    private int x;
    private int y;


    public GUI(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Board getGameBoard() {
        return gameBoard;
    }

    public JButton[][] getButtonArray() {
        return buttonArray;
    }

    public JLabel getWinLabel() { return winLabel; }

    public void startGame() {
            gameBoard = new Board(x, y);
            gameBoard.createBoard();

            Container panel = getContentPane();

            Container buttonPane = new Container();
            buttonPane.setLayout(new GridLayout(x, y));

            Container labelContainer = new Container();
            labelContainer.setLayout(new BorderLayout());

            JLabel label= new JLabel("Minesweeper");
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setFont(new Font("Serif", Font.BOLD, 30));
            labelContainer.add(label, PAGE_START);

            winLabel.setText("");
            winLabel.setHorizontalAlignment(JLabel.CENTER);
            labelContainer.add(winLabel, PAGE_END);

            panel.add(labelContainer, PAGE_START);

            restartButton = new JButton("Restart");
            restartButton.addActionListener(e -> {
                resetGame();
                startGame();
            });

            buttonArray = new JButton[gameBoard.getX()][gameBoard.getX()];

            for (int i = 0; i < gameBoard.getX(); i++) {
                for (int j = 0; j < gameBoard.getX(); j++) {
                    buttonArray[i][j] = new JButton(".");
                }
            }

            int j = 0;
            int k = 0;

            for (int i = 0; i < (gameBoard.getX() * gameBoard.getX()); i++) {
                int l = j;
                int m = k;
                JButton button = buttonArray[l][m];
                button.setBackground(Color.BLACK);
                button.setForeground(Color.WHITE);

                button.addMouseListener(new MouseListener() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        //NOT NEEDED
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                        if (SwingUtilities.isLeftMouseButton(e)) {
                            hit(button, l, m, gameBoard, buttonArray);
                        }

                        if (SwingUtilities.isRightMouseButton(e)) {
                            flag(button, l, m);
                        }

                        if (SwingUtilities.isMiddleMouseButton(e)) {
                            bulkHit(l, m, buttonArray, gameBoard);
                        }

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        //NOT NEEDED
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        //NOT NEEDED
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        //NOT NEEDED
                    }
                });
                buttonPane.add(button);

                k++;
                if (k == gameBoard.getX()) {
                    k = 0;
                    j++;
                }
            }

            panel.add(buttonPane, javax.swing.SwingConstants.CENTER);
            panel.add(restartButton, PAGE_END);
            restartButton.setVisible(false);
        }

        public void resetGame(){
            Container panel = getContentPane();
            panel.removeAll();
        }


        public void hit(JButton button, int l, int m, Board gameBoard, JButton[][] buttonArray){
            button.setBackground(Color.GRAY);
            button.setForeground(Color.BLACK);

            gameBoard.hit(l, m, false);
            if (gameBoard.getBoard()[l][m].checkBomb()) {
                button.setBackground(Color.RED);
                button.setForeground(Color.BLACK);
            } else if (gameBoard.getBoard()[l][m].getTouching() == 0) {
                button.setText("");
                recursion(l, m, buttonArray, gameBoard);
            } else {
                button.setText(Integer.toString(gameBoard.getBoard()[l][m].getTouching()));
            }
            checkVictory(buttonArray, gameBoard);
        }

        public void flag(JButton button, int l, int m) {
            gameBoard.setFlag(gameBoard.getBoard()[l][m]);
            if (!gameBoard.getBoard()[l][m].isClear()) {
                if (gameBoard.getBoard()[l][m].isFlagged()) {
                    button.setBackground(Color.YELLOW);
                    button.setForeground(Color.BLACK);
                    button.setText("F");
                } else {
                    button.setBackground(Color.BLACK);
                    button.setForeground(Color.WHITE);
                    button.setText(".");
                }
                checkVictory(buttonArray, gameBoard);
            }
        }

        public void recursion(int l, int m, JButton[][] buttonArray, Board test){
            for(int i = -1; i <= 1; i++) {
                for(int j = -1; j <= 1; j++) {
                    if(l+j >= 0 && m+i < test.getX() && m+i >= 0 && j+l < test.getX()) {
                        if(!buttonArray[l+j][m+i].getText().equals("") && test.getBoard()[l+j][m+i].getTouching() == 0 && !test.getBoard()[l+j][m+i].checkBomb()  && !test.getBoard()[l+j][m+i].isFlagged()) {
                            buttonArray[l + j][m + i].setText("");
                            buttonArray[l + j][m + i].setBackground(Color.GRAY);
                            buttonArray[l + j][m + i].setForeground(Color.BLACK);
                            //buttonArray[l + j][m + i].setEnabled(false);
                            recursion(l + j, m + i, buttonArray, test);
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

        public void flagAllBombs(JButton[][] buttonArray, Board gameBoard) {
            for(int i = 0; i < gameBoard.getX(); i++) {
                for(int j = 0; j < gameBoard.getX(); j++) {
                    if(gameBoard.getBoard()[i][j].checkBomb()) {
                        buttonArray[i][j].setBackground(Color.RED);
                        buttonArray[i][j].setForeground(Color.BLACK);
                    }
                }
            }
        }

        public void checkVictory(JButton[][] buttonArray, Board gameBoard) {
            gameBoard.checkVictory();
            if(gameBoard.getGameOver()){
                if(gameBoard.victoryState() == 1) {
                    winLabel.setText("YOU WON! YOU FLAGGED THE MINES");
                } else if (gameBoard.victoryState() == 2) {
                    winLabel.setText("YOU WON! YOU CLEARED ALL THE TILES");
                } else {
                    gameBoard.flagAllBombs();
                    flagAllBombs(buttonArray, gameBoard);
                    winLabel.setText("YOU LOST!");
                }
                restartButton.setVisible(true);
            }
        }


        public void bulkHit(int l, int m, JButton[][] buttonArray, Board gameBoard){
            for(int i = -1; i <= 1; i++) {
                for(int j = -1; j <= 1; j++) {
                    if(l+j >= 0 && l+j < gameBoard.getX() && m+i >= 0 && m+i < gameBoard.getX()) {
                        if(!buttonArray[l+j][m+i].getText().equals("F") && !gameBoard.getBoard()[l+j][m+i].isClear()) {
                            hit(buttonArray[l+j][m+i], l+j, m+i, gameBoard, buttonArray);
                        }

                        checkVictory(buttonArray, gameBoard);
                    }
                }
            }
        }
    }


