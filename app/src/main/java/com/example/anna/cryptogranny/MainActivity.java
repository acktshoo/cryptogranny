package com.example.anna.cryptogranny;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public final static String filename = "savedPuzzle";
    private Button continuePuzzleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newPuzzleButton = (Button) findViewById(R.id.newPuzzle);
        newPuzzleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleNewPuzzle();
            }
        });

        continuePuzzleButton = (Button) findViewById(R.id.continuePuzzle);
        continuePuzzleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleContinuePuzzle();
            }
        });
    }

    private void updateContinueButton() {
        File filesDir = this.getFilesDir();
        File file = new File(filesDir, filename);
        if (file.exists()) {
            continuePuzzleButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        updateContinueButton();
        super.onResume();
    }

    private void handleNewPuzzle() {
        Intent intent = new Intent(this, InputActivity.class);
        startActivity(intent);
    }

    private void handleContinuePuzzle() {
        Intent intent = new Intent(this, PuzzleActivity.class);
        startActivity(intent);
    }

}
