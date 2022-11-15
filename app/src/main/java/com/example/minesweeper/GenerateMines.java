package com.example.minesweeper;

import java.util.Random;

public class GenerateMines {

    public static boolean verif = false;

    //sets all values in arr to 0, and then places mines randomly within that array (value = 1)
    public static boolean generate1(int numberBombs, int[][] arr)
    {
        for(int i = 0; i < arr.length; i++)
        {
            for(int j = 0; j < arr.length; j++)
            {
                arr[i][j] = 0;
            }
        }

        int bombsPlaced = 0; //to not let more bombs be placed then numberBombs
        while(bombsPlaced < numberBombs)
        {
            int x = new Random().nextInt(arr.length); //get random x coordinate within arr length
            int y = new Random().nextInt(arr.length); //get random y coordinate within arr length

            if(arr[x][y] == 0) //placing bomb at these random coordinates
            {
                arr[x][y] = 1;
                bombsPlaced += 1;
            }

        }
        verif = true;  //verif set to true so that we don't regenerate bombs every time we recall onDraw in SpecialView
        return verif;
    }

    //other function to simply generate arr all to 0
    public static void generate2(int[][] arr)
    {
        for(int i = 0; i< arr.length; i++)
        {
            for(int j = 0; j< arr.length; j++)
            {
                arr[i][j] = 0;
            }
        }
    }
}
