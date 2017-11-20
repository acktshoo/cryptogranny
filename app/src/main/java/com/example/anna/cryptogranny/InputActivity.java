package com.example.anna.cryptogranny;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;

import static com.example.anna.cryptogranny.MainActivity.filename;

public class InputActivity extends AppCompatActivity {

    public final static String PUZZLE_TEXT = "puzzleText";
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        Button startPuzzleButton = (Button) findViewById(R.id.startPuzzle);
        startPuzzleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleStartPuzzle();
            }
        });

        editText = (EditText) findViewById(R.id.editText);
        editText.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
    }

    private void handleStartPuzzle() {
        Intent intent = new Intent(this, PuzzleActivity.class);
        String puzzleText = editText.getText().toString();
        intent.putExtra(PUZZLE_TEXT, puzzleText);
        File filesDir = this.getFilesDir();
        File file = new File(filesDir, filename);
        if (file.exists()) file.delete();
        startActivity(intent);
        finish();
    }

}
