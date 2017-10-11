package com.example.anna.cryptogranny;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView puzzleView;
    private TextView curSolView;
    private Cryptogranny cryptogranny;
    private Character fromM = 'P';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        puzzleView = (TextView) findViewById(R.id.puzzleText);
        curSolView = (TextView) findViewById(R.id.curSolText);

        View keyboard = findViewById(R.id.keyboard);
        for (View view: keyboard.getTouchables()){
            Button button = (Button) view;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button button = (Button) view;
                    handleClickOnM(button.getText().charAt(0));
                }
            });
        }
        Button clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClickClear();
            }
        });

        cryptogranny = new Cryptogranny("PUZZLE FOO");
        puzzleView.setText(cryptogranny.getPuzzle());
        
        updatePuzzleState();
    }

    private void handleClickClear() {
        cryptogranny.clearM(fromM);
        updatePuzzleState();
    }

    private void handleClickOnM(Character toN) {
        cryptogranny.guess(fromM,toN);
        updatePuzzleState();
    }

    private void updatePuzzleState() {
        // Puzzle state
        Cryptogranny.PuzzleState puzzleState = cryptogranny.getPuzzleState();
        curSolView.setText(puzzleState.getCurSol());
    }
}
