package com.example.myapplication;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private TextView player1Score;
    private TextView player2Score;

    private boolean playerTurn=true;

    private int round=0;

    private int player1points=0;
    private int player2points=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player1Score = (TextView)findViewById(R.id.text_view_p1);
        player2Score = (TextView)findViewById(R.id.text_view_p2);
        for(int i=1;i<4;i++)
        {
            for(int j=1;j<4;j++)
            {
                String buttonID="button_"+i+"_"+j;
                int redID = getResources().getIdentifier(buttonID,"id",getPackageName());
                buttons[i-1][j-1]=findViewById(redID);
                buttons[i-1][j-1].setOnClickListener(this);
            }
        }

        Button buttonReset = (Button)findViewById(R.id.button_reset);
    }


    public void resetButton(View view){
        round=0;
        player1points=0;
        player2points=0;
        playerTurn=true;
        player1Score.setText("Player 1: "+player1points);
        player2Score.setText("Player 2: "+player2points);
        resetBoard();
    }


    @Override
    public void onClick(View v) {
        if(!((Button) v).getText().toString().equals(""))
        {
            return;
        }
        else
        {
            if(playerTurn)
            {
                ((Button) v).setText("X");
            }
            else
            {
                ((Button) v).setText("O");
            }
            playerTurn=!playerTurn;
            round++;

            if(checkForWin())
            {
                if(playerTurn==false)
                {
                    player1points++;
                    player1Score.setText("Player 1: "+player1points);
                    Toast.makeText(this, "Player 1 wins!!!", Toast.LENGTH_SHORT).show();
                    resetBoard();
                }
                else
                {
                    player2points++;
                    player2Score.setText("Player 2: "+player2points);
                    Toast.makeText(this, "Player 2 wins!!!", Toast.LENGTH_SHORT).show();
                    resetBoard();
                }
                round=0;
                playerTurn=true;
            }
            else
            {
                if(round==9){
                    round=0;
                    playerTurn=true;
                    Toast.makeText(this, "It was a draw", Toast.LENGTH_SHORT).show();
                    resetBoard();
                }
            }

        }

    }

    private boolean checkForWin(){
        String[][] field = new String[3][3];
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                field[i][j]=buttons[i][j].getText().toString();
            }
        }

        for(int i=0;i<3;i++)
        {
            if (field[i][0] == field[i][1] && field[i][1] == field[i][2] && !(field[i][0].equals(""))) {
                return true;
            }
        }
        for(int i=0;i<3;i++)
        {
            if (field[0][i] == field[1][i] && field[1][i] == field[2][i]&& !(field[0][i].equals(""))) {
                return true;
            }
        }
        if (field[0][0] == field[1][1] && field[1][1] == field[2][2]&& !(field[0][0].equals(""))) {
            return true;
        }
        if (field[2][0] == field[1][1] && field[1][1] == field[0][2]&& !(field[1][1].equals(""))) {
            return true;
        }
        return false;


    }

    private void resetBoard()
    {

        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                buttons[i][j].setText("");
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putInt("round",round);
        outState.putInt("player1Score",player1points);
        outState.putInt("player2Score",player2points);
        outState.putBoolean("playerTurn",playerTurn);

    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        round = savedInstanceState.getInt("round");
        player1points = savedInstanceState.getInt("player1Score");
        player2points = savedInstanceState.getInt("player2Score");
        playerTurn = savedInstanceState.getBoolean("playerTurn");
    }
}

