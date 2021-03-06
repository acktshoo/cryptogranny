package com.example.anna.cryptogranny;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wefika.flowlayout.FlowLayout;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class PuzzleActivity extends AppCompatActivity {

    private Cryptogranny cryptogranny;
    private Character fromM;
    private FlowLayout puzzleLayout;
    private View keyboard;
    private Button clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        try {
            FileInputStream fileInputStream = openFileInput(MainActivity.filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            cryptogranny = (Cryptogranny) objectInputStream.readObject();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(cryptogranny == null ) {
            Intent intent = getIntent();
            String puzzle = intent.getStringExtra(InputActivity.PUZZLE_TEXT);
            cryptogranny = new Cryptogranny(puzzle);
        }

        // Setup puzzle display
        puzzleLayout = (FlowLayout) findViewById(R.id.puzzleLayout);
        FlowLayout.LayoutParams p = new FlowLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        p.setMarginStart(getDp(8));
        p.setMarginEnd(getDp(8));
        for(String puzzleWord: cryptogranny.getPuzzleWords()) {
            LinearLayout word = new LinearLayout(this);
            for (Character m : puzzleWord.toCharArray()) {
                View letterColumn = getLayoutInflater().inflate(R.layout.letter_column, null);
                final TextView puzzleButton = letterColumn.findViewById(R.id.letterPuz);
                puzzleButton.setText(m.toString());
                letterColumn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleClickOnM(puzzleButton.getText().charAt(0));
                    }
                });
                word.addView(letterColumn);
            }
            puzzleLayout.addView(word, p);
        }

        // Setup Keyboard
        keyboard = findViewById(R.id.keyboard);
        for (View view: keyboard.getTouchables()){
            Button button = (Button) view;
            button.setBackground(button.getBackground().mutate());
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

        updatePuzzleState();
    }

    @Override
    protected void onPause() {
        try {
            FileOutputStream fileOutputStream = openFileOutput(MainActivity.filename, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(cryptogranny);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPause();
    }

    private int getDp(int i) {
        return (int) (getResources().getDisplayMetrics().density * i);
    }

    private void handleClickOnM(Character fromM) {
        clearPuzzleHighlight();
        if( this.fromM != null && this.fromM.equals(fromM)){
            resetM();
        } else {
            this.fromM = fromM;
            clearButton.setText("CLEAR");
            for (TextView button : getPuzzleButtons()) {
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
        for (TextView button: getPuzzleButtons()) {
            button.setBackgroundColor(getResources().getColor(android.R.color.transparent, null));
        }
    }

    private ArrayList<TextView> getPuzzleButtons() {
        ArrayList<TextView> buttons = new ArrayList<>();
        for (int i = 0; i < puzzleLayout.getChildCount(); i++){
            ViewGroup viewWord = (ViewGroup) puzzleLayout.getChildAt(i);
            for(int j = 0; j < viewWord.getChildCount(); j ++) {
                View view = viewWord.getChildAt(j);
                buttons.add((TextView) view.findViewById(R.id.letterPuz));
            }
        }
        return buttons;
    }

    private void updatePuzzleState() {
        // Update the solution text
        for (int i = 0; i < puzzleLayout.getChildCount(); i++){
            ViewGroup viewWord = (ViewGroup) puzzleLayout.getChildAt(i);
            for(int j = 0; j < viewWord.getChildCount(); j ++) {
                View view = viewWord.getChildAt(j);
                TextView puzzleButton = view.findViewById(R.id.letterPuz);
                TextView solText = view.findViewById(R.id.letterSol);
                solText.setText(cryptogranny.getN(puzzleButton.getText().charAt(0)).toString());
            }
        }

        // Reflect the leftover letters in keyboard
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
