package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private Button button;
    static TextView Text1;
    static TextView Text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing button
        button = (Button) findViewById(R.id.button1);

        //when button clicked, reset minesweeper
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                reset(SpecialView.mineBoard);       //puts two used arrays, all values back to 0
                reset(SpecialView.revealedBoard);
                SpecialView.InGame = true;          //back to true so that we can play the game again
                SpecialView.mVerif = false;         //so that we can regenerate a random mine field
                SpecialView.flagMode = false;       //set flag mode off for user
                SpecialView.cellsMarked = 0;        //number of cells marked must go back to 0
                startActivity(new Intent(MainActivity.this, MainActivity.class)); //restarts activity#
            }
        });

        //initializing button
        button = (Button) findViewById(R.id.button2);

        //when button, clicked switch between flag mode and uncover mode
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(SpecialView.flagMode == false)
                    SpecialView.flagMode = true;
                else SpecialView.flagMode = false;
            }
        });

        //Show the number of mines
        Text1 = (TextView)findViewById(R.id.NoMines);
        Text1.setText("Mines: " + String.valueOf(SpecialView.noBombs));

        ///Set Text2 view as the one used for the flags
        Text2 = (TextView)findViewById(R.id.NoFlags);
    }

    void reset(int[][] arr)
    {
        arr = new int[SpecialView.noRect][SpecialView.noRect];  //resets to new array with no values
    }

}
