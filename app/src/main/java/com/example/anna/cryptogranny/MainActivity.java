package com.example.anna.cryptogranny;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView curSolView;
    private Cryptogranny cryptogranny;
    private Character fromM;
    private LinearLayout puzzleLayout;
    private View keyboard;
    private Button clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        curSolView = (TextView) findViewById(R.id.curSolText);

        keyboard = findViewById(R.id.keyboard);
        for (View view: keyboard.getTouchables()){
            Button button = (Button) view;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button button = (Button) view;
                    handleClickOnN(button.getText().charAt(0));
                }
            });
        }
        clearButton = (Button) findViewById(R.id.clear);
        clearButton.setText("CLEAR ALL");
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClickClear();
            }
        });

        cryptogranny = new Cryptogranny("PUZZLE FOO");

        puzzleLayout = (LinearLayout) findViewById(R.id.puzzleLayout);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        );
        for(Character m: cryptogranny.getPuzzle().toCharArray()){
            Button puzzleButton = new Button(this);
            puzzleButton.setText(m.toString());
            puzzleButton.setBackgroundColor(0);
            puzzleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button button = (Button) view;
                    handleClickOnM(button.getText().charAt(0));
                }
            });
            puzzleLayout.addView(puzzleButton, p);
        }

        updatePuzzleState();
    }

    private void handleClickOnM(Character fromM) {
        clearPuzzleHighlight();
        if( this.fromM != null && this.fromM.equals(fromM)){
            resetM();
        } else {
            this.fromM = fromM;
            clearButton.setText("CLEAR");
            for (View view : puzzleLayout.getTouchables()) {
                Button button = (Button) view;
                if (button.getText().equals(fromM.toString())) {
                    button.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light, null));
                }
            }
        }
    }

    private void resetM() {
        this.fromM = null;
        clearButton.setText("CLEAR ALL");
    }

    private void handleClickClear() {
        if(fromM != null) {
            cryptogranny.clearM(fromM);
            resetM();
        } else {
            cryptogranny.resetPuzzle();
        }
        clearPuzzleHighlight();
        updatePuzzleState();
    }

    private void handleClickOnN(Character toN) {
        if(fromM != null) {
            cryptogranny.guess(fromM, toN);
            resetM();
            updatePuzzleState();
        }
        clearPuzzleHighlight();
    }

    private void clearPuzzleHighlight() {
        for (View view: puzzleLayout.getTouchables()) {
            Button button = (Button) view;
            button.setBackgroundColor(0);
        }
    }

    private void updatePuzzleState() {
        // Puzzle state
        Cryptogranny.PuzzleState puzzleState = cryptogranny.getPuzzleState();
        curSolView.setText(puzzleState.getCurSol());
        for (View view: keyboard.getTouchables()) {
            if(view.getId() == R.id.clear) continue;
            Button button = (Button) view;
            if (cryptogranny.isNUsed(button.getText().charAt(0))){
                button.setTextColor(getResources().getColor(android.R.color.white, null));
                button.getBackground().setTint(getResources().getColor(android.R.color.holo_blue_dark, null));
            } else{
                button.setTextColor(getResources().getColor(android.R.color.black, null));
                button.getBackground().setTint(getResources().getColor(android.R.color.holo_blue_bright, null));
            }
        }
    }
}
