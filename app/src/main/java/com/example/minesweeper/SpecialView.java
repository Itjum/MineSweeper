package com.example.minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class SpecialView extends View {

    Rect square;
    Paint rectPaint;
    TextPaint minePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

    //number of bombs and cells, chosen here
    static int noRect = 10;
    static int noBombs = 20;
    static int cellsMarked = 0;

    static boolean mVerif = false;

    //boolean to verify if we are flagging or uncovering
    static boolean flagMode = false;

    //if mine has been uncovered or not, InGame will be false if one was
    static boolean InGame = true;

    //arrays used to know if cell has been revealed, flagged or untouched, or where the mines are
    public static int[][] revealedBoard = new int[noRect][noRect];
    public static int[][] mineBoard = new int[noRect][noRect];


    //Set up view, constructors
    public SpecialView(Context context)
    {
        super(context);
        init(null, 0);

    }

    public SpecialView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0);
    }

    public SpecialView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs,defStyle);
    }

    public void init(AttributeSet attrs, int defStyle)
    {
        setBackgroundColor(Color.WHITE);
    }

    //Function used to draw on canvas
    @Override
    protected void onDraw(Canvas canvas)
    {
        //Change on TextView the number of flagged cells
        MainActivity.Text2.setText("Flags: "+cellsMarked);

        //generate mines if haven't been generated yet
        if(mVerif == false)
        {
            mVerif = GenerateMines.generate1(noBombs, mineBoard);
            GenerateMines.generate2(revealedBoard);
        }

        //set up canvas and number of squares
        super.onDraw(canvas);
        int contentwidth = getWidth();
        int rectBound = contentwidth/noRect;

        //setting initial colors of paint
        rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectPaint.setColor(Color.BLACK);
        minePaint.setColor(Color.BLACK);
        minePaint.setTextSize(77);
        minePaint.setTypeface(Typeface.create("Arial", Typeface.BOLD));
        square = new Rect(10, 10, rectBound -10, rectBound-10);

        for(int i = 0; i <= noRect-1; i++)
        {
            canvas.save();
            for(int j = 0; j <= noRect-1; j++)
            {
                if(revealedBoard[i][j] == 1 && mineBoard[i][j] == 1)
                {
                    //change paint color for mines
                    rectPaint.setColor(Color.RED);

                }
                else if(revealedBoard[i][j] == 1){
                    //color for regular revealed squares
                    rectPaint.setColor(Color.GRAY);
                }
                else if(revealedBoard[i][j] == -1)
                {
                    //color for flagged squares
                    rectPaint.setColor(Color.YELLOW);
                }
                else
                {
                    //color for none revealed squares
                    rectPaint.setColor(Color.BLACK);
                }
                canvas.save();
                canvas.translate(i* rectBound, j*rectBound); //setting location to draw the M
                canvas.drawRect(square,rectPaint);
                if(mineBoard[i][j] == 1 && revealedBoard[i][j] == 1)
                {
                    //draw "M" on mine locations after drawing squares so that we can actually see the M
                    canvas.drawText("M", rectBound/2-33, rectBound/2+23, minePaint); //had to slightly change the coordinates
                }
                canvas.restore();
            }

        }

    }

    public boolean onTouchEvent(MotionEvent event)
    {
        int x = (int) event.getX(); //get coordinates
        int y = (int) event.getY();
        int contentWidth = getWidth();
        int rectBound = contentWidth/revealedBoard.length;
        x = x/rectBound;
        y = y/rectBound;
        if(InGame == true)
        {
            if(y < revealedBoard.length) //if clicked towards the cell mines
            {
                if(flagMode == true) //means we are currently placing flags on the minefield
                {
                    if(revealedBoard[x][y] == 0 && cellsMarked < noBombs) //must not be able to flag more cells then number of bombs in game
                    {
                        revealedBoard[x][y] = -1; //-1 used to indicate if cell is flagged
                        cellsMarked ++; //increase number of cells marked
                    }
                    else if(revealedBoard[x][y] == -1)
                    {
                        revealedBoard[x][y] = 0; //set cell/square back to uncovered
                        cellsMarked --; //decrease
                    }
                }
                else{
                    if(mineBoard[x][y] == 1 && revealedBoard[x][y] == 0) //if clicked mine then game over
                    {
                        InGame = false; //Game Over
                    }
                    if(revealedBoard[x][y] == 0) //if board not revealed yet then state that it is that when onDraw is recalled, the color of square will be changed to gray
                    {
                        revealedBoard[x][y] = 1;
                    }
                }
                invalidate(); //calls onDraw function
            }
        }
        return super.onTouchEvent(event);
    }

}
