package com.example.sandeepkumar.tictac;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 0 = yellow player , 1 = red player.
    int activePlayer = 0;
    boolean gameIsActive =true;
    // 2 = Unplayed
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    //3 rows, 3 coloums and 2 Diagonals are Winning Positions.
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};
    LinearLayout linearLayout;
    Button playAgainButton;

    @SuppressLint("SetTextI18n")
    public void dropIn(View view){

        TextView playerText = findViewById(R.id.playerTextView);

        ImageView counter = (ImageView)view;                    //We want the view which is pass in method
        //therefore We dont need to find the view by its id like in others methods to find button id like.

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameState[tappedCounter] == 2 && gameIsActive){
            gameState[tappedCounter] = activePlayer;
            counter.setTranslationX(-1000f);                        //Setting the Coins Invisible first

            if(activePlayer ==0) {
                playerText.setText("Player Turn: Red");
                counter.setImageResource(R.drawable.yellow);        //First Trun Play by Yellow Player
                activePlayer =1;                                    //Changing the Player to Red
            } else{
                playerText.setText("Player Turn: Yellow");
                counter.setImageResource(R.drawable.red);           //Red Players Trun
                activePlayer =0;                                    //Changing Back to Yellow Player
            }

            //Making the coins visible on screen
            counter.animate()                                       //Animate the Coin
                    .translationXBy(1000f)                          //Moving the coin to poistion by 1000 pixels
                    .setDuration(300);                              //Time taken to make visible the coin.

            for (int[] winningPosition : winningPositions){

                if(gameState[winningPosition[0]] == gameState[winningPosition[1]]
                        && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                        && gameState[winningPosition[0]] != 2){
                    //One Player is won
                    gameIsActive =false;

                    String winner = "Red";

                    playAgainButton = findViewById(R.id.playAgainButton);
                    playAgainButton.setVisibility(View.VISIBLE);

                    if(gameState[winningPosition[0]]== 0) {
                        winner = "Yellow";
                    }

                    playerText.setText("");

                    TextView winnerMessage = findViewById(R.id.winnerMessage);
                    winnerMessage.setText(winner +" has won");

                    linearLayout = findViewById(R.id.linearLayout);
                    linearLayout.setVisibility(View.VISIBLE);

                } else{
                    boolean gameIsOver = true;
                    for(int counterState : gameState){
                        if(counterState == 2)
                            gameIsOver = false;
                    }
                    if(gameIsOver){
                        TextView winnerMessage = findViewById(R.id.winnerMessage);
                        winnerMessage.setText("It's a draw");
                        playerText.setText("");
                        linearLayout = findViewById(R.id.linearLayout);
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void playAgain(View view){

        activePlayer = 0;
        gameIsActive =true;
        TextView playerText = findViewById(R.id.playerTextView);
        playerText.setText("Player 1: Yellow\nPlayer 2: Red");
        linearLayout = findViewById(R.id.linearLayout);
        linearLayout.setVisibility(View.INVISIBLE);

        android.support.v7.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);
        for(int i = 0 ; i< gridLayout.getChildCount(); i++){
            ((ImageView)gridLayout.getChildAt(i)).setImageResource(0);
        }

        for(int i = 0 ; i < gameState.length ; i++){
            gameState[i] = 2;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}