package com.se491.simacogo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.se491.simacogo.exceptions.IllegalMoveException;
import com.se491.simacogo.exceptions.IllegalPlayerException;
import com.se491.simacogo.model.*;
import com.se491.simacogo.search.*;


public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView arrow1;
    private ImageView arrow2;
    private ImageView arrow3;
    private ImageView arrow4;
    private ImageView arrow5;
    private ImageView arrow6;
    private ImageView arrow7;
    private ImageView arrow8;
    private ImageView arrow9;
    private TextView blackScore;
    private TextView whiteScore;
    private TextView player;


    private GridLayout table;
    private ImageView[][] board;
    private Board gameBoard;
    private Node aiRoot;
    private int depth;

    @Override
    protected void onStart() {
        super.onStart();
        Intent i = getIntent();
        Bundle b = i.getExtras();
        depth = b.getInt("numPlies");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //depth = 0;

        blackScore = (TextView) findViewById(R.id.blackScore);
        whiteScore = (TextView) findViewById(R.id.whiteScore);
        player = (TextView) findViewById(R.id.player);

        arrow1 = (ImageView) findViewById(R.id.arrow1);
        arrow2 = (ImageView) findViewById(R.id.arrow2);
        arrow3 = (ImageView) findViewById(R.id.arrow3);
        arrow4 = (ImageView) findViewById(R.id.arrow4);
        arrow5 = (ImageView) findViewById(R.id.arrow5);
        arrow6 = (ImageView) findViewById(R.id.arrow6);
        arrow7 = (ImageView) findViewById(R.id.arrow7);
        arrow8 = (ImageView) findViewById(R.id.arrow8);
        arrow9 = (ImageView) findViewById(R.id.arrow9);

        arrow1.setOnClickListener(this);
        arrow2.setOnClickListener(this);
        arrow3.setOnClickListener(this);
        arrow4.setOnClickListener(this);
        arrow5.setOnClickListener(this);
        arrow6.setOnClickListener(this);
        arrow7.setOnClickListener(this);
        arrow8.setOnClickListener(this);
        arrow9.setOnClickListener(this);

        board = new ImageView[9][10];
        table = (GridLayout) findViewById(R.id.grid);

        // create game board
        for(int i=0; i<9; i++){

            for(int j = 1; j<10; j++){
                ImageView v = new ImageView(this);
                v.setImageResource(R.drawable.graysquare);
                v.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                GridLayout.LayoutParams param =new GridLayout.LayoutParams();
                param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                param.width = GridLayout.LayoutParams.WRAP_CONTENT;
                param.setMargins(5,5,5,5);
                param.setGravity(Gravity.CENTER);
                param.columnSpec = GridLayout.spec(i);
                param.rowSpec = GridLayout.spec(j);
                v.setLayoutParams (param);
                table.addView(v);

                board[i][j] = v;

            }

            gameBoard = new Board();

        }
    }
    @Override
    public void onClick(View v) {
        try {


            switch (v.getId()) {
                case R.id.arrow1:
                    doDrop(Player.WHITE, 1);
                    break;
                case R.id.arrow2:
                    doDrop(Player.WHITE, 2);
                    break;
                case R.id.arrow3:
                    doDrop(Player.WHITE, 3);
                    break;
                case R.id.arrow4:
                    doDrop(Player.WHITE, 4);
                    break;
                case R.id.arrow5:
                    doDrop(Player.WHITE, 5);
                    break;
                case R.id.arrow6:
                    doDrop(Player.WHITE, 6);
                    break;
                case R.id.arrow7:
                    doDrop(Player.WHITE, 7);
                    break;
                case R.id.arrow8:
                    doDrop(Player.WHITE, 8);
                    break;
                case R.id.arrow9:
                    doDrop(Player.WHITE, 9);
                    break;
            }
            Pair<Integer, Integer> p = gameBoard.getLastMove();
            board[p.first][p.second + 1].setImageResource(R.drawable.whitesquare);
            player.setText(getResources().getString(R.string.computer));
            disableButtons();
            if (!gameBoard.isFull()) {

                aiRoot = new Node(NodeType.MAX, new Board(gameBoard));
                try {
                    int aiMove = AIMove(aiRoot, depth);
                    doDrop(Player.BLACK, aiMove);
                    Pair<Integer, Integer> p2 = gameBoard.getLastMove();
                    board[p2.first][p2.second + 1].setImageResource(R.drawable.blacksquare);
                    player.setText(getResources().getString(R.string.player));
                } catch (IllegalPlayerException e) {

                }
                whiteScore.setText(String.valueOf(gameBoard.getWhiteScore()));
                blackScore.setText(String.valueOf(gameBoard.getBlackScore()));

                enableButtons();
            } else {
                Intent gameOver = new Intent(GameActivity.this, GameOverActivity.class);
                gameOver.putExtra("whiteScore", gameBoard.getWhiteScore());
                gameOver.putExtra("blackScore", gameBoard.getBlackScore());
                startActivity(gameOver);
                finish();

            }



        }catch (IllegalMoveException e){
            Toast.makeText(this, "Invalid Move - Try again", Toast.LENGTH_SHORT).show();
        }catch (IllegalPlayerException e){

        }

    }


    public static int AIMove(Node root, int depth) throws IllegalPlayerException {
        MinMax mm = new MinMax(root,depth);
        return mm.getNextMove();

    }

    private void doDrop(Player p, int move) throws IllegalMoveException, IllegalPlayerException{

            gameBoard.drop(p,move);
    }

    private void disableButtons(){
        arrow1.setEnabled(false);
        arrow2.setEnabled(false);
        arrow3.setEnabled(false);
        arrow4.setEnabled(false);
        arrow5.setEnabled(false);
        arrow6.setEnabled(false);
        arrow7.setEnabled(false);
        arrow8.setEnabled(false);
        arrow9.setEnabled(false);
    }
    private void enableButtons(){
        arrow1.setEnabled(true);
        arrow2.setEnabled(true);
        arrow3.setEnabled(true);
        arrow4.setEnabled(true);
        arrow5.setEnabled(true);
        arrow6.setEnabled(true);
        arrow7.setEnabled(true);
        arrow8.setEnabled(true);
        arrow9.setEnabled(true);
    }
}
