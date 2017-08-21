package com.se491.simacogo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GameOverActivity extends AppCompatActivity {

    private TextView winner;
    private TextView blackScore;
    private TextView whiteScore;
    private Button playAgainBtn;
    private Button exitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        winner = (TextView) findViewById(R.id.winner);
        whiteScore = (TextView) findViewById(R.id.whiteScore);
        blackScore = (TextView) findViewById(R.id.blackScore);

        playAgainBtn = (Button) findViewById(R.id.playAgainBtn);
        exitBtn = (Button) findViewById(R.id.exitBtn);

        playAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent i = getIntent();
        Bundle b = i.getExtras();
        int black = b.getInt("blackScore");
        blackScore.setText(String.valueOf(black));
        int white = b.getInt("whiteScore");
        whiteScore.setText(String.valueOf(white));


        if(black > white){
            winner.setText(R.string.lose);
        }else{
            winner.setText(R.string.youwin);
        }



    }
}
