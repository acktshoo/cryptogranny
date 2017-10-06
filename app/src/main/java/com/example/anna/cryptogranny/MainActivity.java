package com.example.anna.cryptogranny;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView puzzleView = (TextView) findViewById(R.id.puzzleText);
        TextView curSolView = (TextView) findViewById(R.id.curSolText);
        Cryptogranny cryptogranny = new Cryptogranny("PUZZLE FOO");
        Cryptogranny.PuzzleState puzzleState = cryptogranny.getPuzzleState();
        puzzleView.setText(puzzleState.getPuzzle());
        curSolView.setText(puzzleState.getCurSol());
    }
}
