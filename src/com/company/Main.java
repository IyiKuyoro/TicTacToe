package com.company;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static char[][] grid = new char[3][3];
    private static int counter = 0;
    private static int comMoves = 0;

    public static void main(String[] args) {
	    System.out.println("This is a new TikTacToe Game.");
	    InitializeGrid();
	    DrawGird();
	    StartGame();
    }

    private static void InitializeGrid() {
        // Set all the positions to empty
        for(int r = 0; r <= 2; r++){
            for (int c = 0; c <= 2; c++){
                grid[r][c] = ' ';
            }
        }
    }

    private static void StartGame() {
        boolean gameOver = false;

        while (!gameOver) {
            boolean success = XPlays();     //Attempt to get user input.
            //if user input is wrong, loop till correct.
            while (!success) {
                success = XPlays();
            }
            DrawGird();
            gameOver = GameStatus();

            if (!gameOver) {
                ComputerPlays();
                DrawGird();
                gameOver = GameStatus();
            }
        }
        EndGame();
    }

    private static void EndGame() {
        if(Win() == 'X')
            System.out.println(("Congratulations! You have won the game."));
        else if (Win() == 'N')
            System.out.println("Game was a tie.");
        else if (Win() == 'O')
            System.out.println("I win you lose!");
    }

    private static boolean GameStatus() {
        boolean gameOver = false;

        //Check if all the positions have been filled of someone has won.
        if(counter == 9 || (Win() != 'N')){
            gameOver = true;
        }

        return gameOver;
    }

    private static char Win() {
        char won = 'N';

        //check if the game has been won Horizontally
        for(int row = 0; row < 3; row++) {
            if ((grid[row][0] == grid[row][1]) && (grid[row][1] == grid[row][2]) && (grid[row][0] != ' ')) {
                if (grid[row][0] == 'X') {
                    won = 'X';
                    return won;
                }
                else {
                    won = 'O';
                    return won;
                }
            }
        }

        //check if the game has been won vertically
        for(int col = 0; col < 3; col++){
            if (grid[0][col] == grid[1][col] && grid[1][col] == grid[2][col] && grid[0][col] != ' '){
                if (grid[0][col] == 'X'){
                    won = 'X';
                    return won;
                }
                else {
                    won = 'O';
                    return won;
                }
            }
        }

        //check if the game has been won diagonally
        if(grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2] && grid[1][1] != ' '){
            if (grid[0][0] == 'X'){
                won = 'X';
                return won;
            }
            else {
                won = 'O';
                return won;
            }
        }
        if(grid[2][0] == grid[1][1] && grid[1][1] == grid[0][2] && grid[2][0] != ' '){
            if (grid[2][0] == 'X'){
                won = 'X';
                return won;
            }
            else {
                won = 'O';
                return won;
            }
        }

        return won;     //The game has not been won 'N' is returned.
    }

    private static boolean XPlays() {
        System.out.println("Where will you like to play? Format = row|column");
        Scanner reader = new Scanner((System.in));
        char[] position = reader.next().toCharArray();
        System.out.println(position[0]);
        try {
            //Check if the format is correct.
            if(position.length != 3 || position[1] != '|')
                throw new Exception("Wrong format! The format is row|column.\n");

            //Check if selected position is out of range
            if(position[0] > '3' || position[0] < '1' || position[2] > '3' || position[2] < '1')
                throw new Exception(("Index out of range! The index of rows and columns is between 1 and 3.\n"));

            //Check is position has already been filled
            if(grid[position[0] - 49][position[2] - 49] != ' ')
                throw new Exception("Invalid Position. This position has been taken.\n");

            grid[position[0] - 49][position[2] - 49] = 'X';

            counter++;
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    private static void ComputerPlays() {
        Random rnd = new Random();

        if (comMoves == 0){
            //first move must be in the middle of on any of for corners

            //if human played on the edge, play in the center.
            if(grid[0][0] == 'X' || grid[2][0] == 'X' || grid[0][2] == 'X' || grid[2][2] == 'X'){
                grid[1][1] = 'O';
                counter++;
                comMoves++;
                return;
            }
            else if( grid[1][1] == 'X') {    //Else if human played in the center, play on the edge
                //Get row number
                int row = rnd.nextInt(3);
                while (row < 0 || row > 2 || row == 1) {
                    row = rnd.nextInt(3);
                }

                //Get col number
                int col = rnd.nextInt(3);
                while ((col < 0 || col > 2 || col == 1)) {
                    col = rnd.nextInt(3);
                }

                if (grid[row][col] == ' ') {
                    grid[row][col] = 'O';
                    counter++;
                    comMoves++;
                    return;
                }
            }
        }
        else{   //Not the first move then track player. If about to win block else advance
            for(int i = 0; i < 3; i++) {
                //Check if player is about to win game horizontally
                if (grid[i][0] == grid[i][1] && grid[i][0] == 'X' ) {
                    if(grid[i][2] == ' '){
                        grid[i][2] = 'O';
                        counter++;
                        comMoves++;
                        return;
                    }
                }
                else if (grid[i][1] == grid[i][2] && grid[i][1] == 'x'){
                    if(grid[i][0] == ' '){
                        grid[i][0] = 'O';
                        counter++;
                        comMoves++;
                        return;
                    }
                }
                else if (grid[i][0] == grid[i][2] && grid[i][0] == 'x'){
                    if(grid[i][1] == ' '){
                        grid[i][1] = 'O';
                        counter++;
                        comMoves++;
                        return;
                    }
                }

                //Check if player is about to win vertically
                if (grid[0][i] == grid[1][i] && grid[0][i] == 'X') {
                    if(grid[2][i] == ' '){
                        grid[2][i] = 'O';
                        counter++;
                        comMoves++;
                        return;
                    }
                }
                else if (grid[1][i] == grid[2][i] && grid[1][i] == 'X'){
                    if(grid[0][i] == ' '){
                        grid[0][i] = 'O';
                        counter++;
                        comMoves++;
                        return;
                    }
                }
                else if (grid[0][i] == grid[2][i] && grid[0][i] == 'X'){
                    if(grid[1][i] == ' '){
                        grid[1][i] = 'O';
                        counter++;
                        comMoves++;
                        return;
                    }
                }
            }

            //Check if player is about to win diagonally
            if(grid[0][0] == grid[1][1] && grid[0][0] == 'X'){
                if (grid[2][2] == ' '){
                    grid[2][2] = 'O';
                    counter++;
                    comMoves++;
                    return;
                }
            }
            else if(grid[1][1] == grid[2][2] && grid[1][1] == 'X'){
                if (grid[0][0] == ' ') {
                    grid[0][0] = 'O';
                    counter++;
                    comMoves++;
                    return;
                }
            }
            else if(grid[0][0] == grid[2][2] && grid[0][0] == 'X'){
                if (grid[1][1] == ' '){
                    grid[1][1] = 'O';
                    counter++;
                    comMoves++;
                    return;
                }
            }
            if(grid[2][0] == grid[1][1] && grid[2][0] == 'X'){
                if (grid[0][2] == ' '){
                    grid[0][2] = 'O';
                    counter++;
                    comMoves++;
                    return;
                }
            }
            else if (grid[1][1] == grid[0][2] && grid[1][1] == 'X'){
                if (grid[2][0] == ' '){
                    grid[2][0] = 'O';
                    counter++;
                    comMoves++;
                    return;
                }
            }
            else if (grid[2][0] == grid[0][2] && grid[2][0] == 'X'){
                if (grid[1][1] == ' '){
                    grid[1][1] = 'O';
                    counter++;
                    comMoves++;
                    return;
                }
            }
        }

        //play randomly
        boolean success = false;
        do {
            //Get row number
            int row = rnd.nextInt(3);
            while (row < 0 || row > 2) {
                row = rnd.nextInt(3);
            }

            //Get col number
            int col = rnd.nextInt(3);
            while ((col < 0 || col > 2)) {
                col = rnd.nextInt(3);
            }

            if (grid[row][col] == ' ') {
                grid[row][col] = 'O';
                counter++;
                comMoves++;
                success = true;
            }
        }while(!success);
    }

    private static void DrawGird() {
        for(int r = 0; r <= 2; r++){
            System.out.println(" " + grid[r][0] + " | " + grid[r][1] + " | " + grid[r][2] + " ");
            if (r != 2)
                System.out.println("------------");
        }
        System.out.println();
        System.out.println();
    }
}
