package programacion;

import java.io.*;
import java.util.*;

/**
 * Unique class since it's a non object oriented project.
 * @author Ulises Ceca Tamurejo
 */

public class Programacion {
    
    public static Scanner sc = new Scanner (System.in);

    /**
     * Method used to show the options from the menu
     */
    
    public static void showOptions() {
        System.out.println("Choose an option:\n1. Load points file\n2. Play a "
                + "match\n3. Play all matches "
                + "\n4. Compute the total points of a team "
                + "\n5. Compute the points of all teams\n6. Compute the "
                + "team with the highest points \n7. Save \n8. Exit");
    }
    
    /**
     * Method to start the array with -1
     * @param matriz Is the array which has the points from the teams
     */
    
    public static void setSame (int [][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matriz[i][j] = -1;
            }
        }
    }

    /**
     * Method to read the file and fill the array with the points of the teams.
     * It also checks if the array of the file is correct and if the file exists
     * (Option 1)
     * @param matriz Is the array which will be filled with the points of teams
     */
    
    public static void readFile (int [][] matriz) {
        int value = 2;  //Value to display an error if there's any
        String file;
        System.out.println("Insert the name of the File to be read: ");
        do {
            file = sc.next();
            try {
                Scanner sc = new Scanner(new FileReader(file)); //To read
                while (sc.hasNext()) {  //Start reading the file
                    if (sc.nextInt() == matriz.length) {
                        value = 2;  //Dimension from file is the same
                    } else {
                        value = 1;  //Dimension is different
                    }
                    try {
                        for (int i = 0; i < matriz.length; i++) {
                            for(int j = 0; j < matriz[i].length; j++) {
                                    matriz[i][j] = sc.nextInt();
                            }
                        }
                    } catch (NoSuchElementException e) {
                    }
                }
            } catch (FileNotFoundException exception) {
                value = 0;  //File not found
                }
            switch (value) {    //To print what happened
                case 0:
                    System.err.println("The file " + file + " wasn't "
                            + "found");
                    break;

                case 1:
                    System.err.println("Dimension from file must be"
                            + " the same as the previously entered");
                    break;

                case 2:
                    System.out.println("File has been read");
            }
        } while (value != 2);
    }
    
    /**
     * Method to set the points from all matches (Option 3)
     * @param matriz Is the array with all the points and which is going to be
     * updated depending on the results from both matches.
     */
    
    public static void setScoreOneRound (int [][] matriz) {
        String points = "";
        int equipos = 0;    //Variable to display the teams properly
        int equipos2;   //Variable to display the teams properly
        for (int i = 0; i < matriz.length; i++) {
            equipos++;
            equipos2 = 0;   //Reset to 0 when all cols have been shown
            for (int j = 0; j < matriz[i].length; j++) {
                equipos2++;
                if (i != j) {
                    System.out.println("Set points for Team " + equipos + 
                            " against Team " + equipos2);
                    points = checkPoints(points);   //Check if points are ok
                    matriz[i][j] = Integer.parseInt(points);
                }
            }
        }
    }
     
    /**
     * Method to set the points from a single match between 2 teams (Option 2)
     * @param team1 Is one of the teams which is going to play
     * @param team2 Is the team which is going to play against Team 1
     * @param points Is the points from Team 1¡
     * @param matriz Is the array with all the points and which is going to be
     * updated depending on the results.
     */
    
    public static void setScoreOneMatch(int team1, int team2 ,int points, 
            int [][] matriz) {
        if (points == 3) {   //Set 3 points
            matriz[team1][team2] = 3;   
        } else if (points == 0) { //Set 0 points
            matriz[team1][team2] = 0;   
        } else if (points == 1) { //Set 1 points
            matriz[team1][team2] = 1;   
        }
    }
    
    /**
     * Method to check if the selected teams are the same or not
     * @param team1 First team selected
     * @param team2 Second team selected
     * @param max Existing teams
     * @return The second team if everything is correct
     */
    
    public static String sameTeams (String team1, String team2, 
            int max) {
        boolean same = true;    //For the loop
        do {
            team2 = teamExist(team2, max);  //Check if team exists and is number
            if (team1.equalsIgnoreCase(team2)) {
                same = true;    //Change to true if the teams are the same
                System.err.println("Select a different team");
            } else {
                same = false;   //Change to false if they're not the same
            }
        } while (same);
        return team2;
    }
    
    /**
     * Method to check if the introduced team by the user exists or not
     * @param team Is the introduced team
     * @param max Is the length of the array where the teams are
     * @return The introduced team if everything is ok
     */
    
    public static String teamExist (String team, int max) {
        boolean bucle = true;
        do {
            team = isNumeric(team); //First check if it's a number
            if ((Integer.parseInt(team) < 1) || (Integer.parseInt(team) > max)){
                bucle = false;  //Change to false if the team isn't in the range
                System.err.println("Select a valid team");
            } else {
                bucle = true;
            }
        } while (!bucle);
        return team;
    }
      
    /**
     * Method to compute the points from the desired team in the league 
     * (Option 4)
     * @param team1 Is we the team want to know the total points in the league
     * from
     * @param matriz Is the array with all the points, it's needed to compute
     * the ones from the team we want
     * @return A sum with all the points for that team in the league
     */
    
    public static int computePointsOne (int team1, int [][] matriz) {
        int suma;
        int vertical = 0;
        int horizontal = 0;
        for (int i = 0; i < matriz.length; i++) {
            for(int j = 0; j < matriz[i].length; j++) {
                if((matriz[i][j] == 1) && (i == team1)) {   //Check Rows
                    horizontal += 1;
                } else if ((matriz[i][j] == 3) && (i == team1)) {   //Check Rows
                    horizontal += 3;
                }
                if((matriz[i][j] == 1) && (j == team1)) {   //Check Columns
                    vertical += 1;
                } else if ((matriz[i][j] == 0) && (j == team1)) {   //Check Cols
                    vertical += 3;
                }
            }
        }
        suma = vertical + horizontal;   //We compute the sum
        return suma;
      }
      
    /**
     * Method used to compute the total points from all teams in the league
     * (Option 5)
     * @param matriz Is the array which contains all the points from all teams
     * @return An array with the sum of all teams
     */
    
    public static int [] computePointsAll (int [][] matriz){
        int [] suma = new int [matriz.length];  //Array to sum rows and cols
        for (int i = 0; i < suma.length; i++) {
            suma[i] = computePointsOne(i, matriz);  //We sum all teams' points
        }
        return suma;
      }
      
    /**
     * Method to compute the team with the highest points, if there's more than 
     * one with the highest points they'll be shown too. (Option 6)
     * @param matriz is the array which contains all points from all teams, we
     * need it to pass it to an existing method to compute the total points from
     * every team
     * @return An array with the teams that have the highest points
     */
    
    public static int [] maxScore (int [][] matriz) {
        int [] suma = new int [matriz.length];  //Array to keep all the points
        suma = computePointsAll(matriz);    //Call method to calculate all point
        int maximo = suma[0];   //Variable to know the maximum point
        int [] equipo = new int [matriz.length];
        for (int i = 1; i < suma.length; i++) {
            if (maximo <= suma[i]) {    //We check if next value is bigger or no
                maximo = suma[i];   //We store the highest value
            }
        }
        for (int i = 0; i < suma.length; i++) {
            if (suma[i] == maximo) {    //We search the highest values
                equipo[i] = i+1;    //We store the position+1, which is the team
            }
        }
        return equipo;
      }
    
    /**
     * Method to save a file with the results which are stored in an array 
     * (Option 7)
     * @param matriz is the array which contains the points which are going to
     * be saved in the file
     */
    
    public static void saveFile (int [][] matriz) {
        boolean value;  //For the loop
        String filesave;    //File name
        
        System.out.println("Write a name for the file including "
                + "its format (Ex: exported.txt)");
         do {
            filesave = sc.next();
            try {
                PrintWriter file = new PrintWriter(filesave);
                file.println(matriz.length);    //Print the dimension
                for (int i = 0; i < matriz.length; i++) {
                    for (int j = 0; j < matriz.length; j++) {
                        file.println(matriz[i][j]); //Print all values
                    }
                }
                file.close();
                value = true;
            } catch (IOException iox) {
                value = false;
            }
            if (value) {
                System.out.println("The file has been saved");
            } else {
                System.out.println("There was an error, try again");
            }
         } while (!value);
    }
    
    /**
     * Method to check if the introduced points are 0, 1 or 3
     * @param points Is the introduced points by the user
     * @return The introduced points if everything is ok
     */
    
    public static String checkPoints (String points) {
        boolean bucle = true;
        do {
            points = isNumeric(points); //Check if points are numeric
            if ((Integer.parseInt(points) != 3) //Check points
                    && (Integer.parseInt(points) != 0)
                    && (Integer.parseInt(points) != 1)) {
                bucle = false;
                System.err.print("Points must be 0, 1 or 3");
            } else {
                bucle = true;   //End loop
            }
        } while (!bucle);
        return points;
    }
    
    /**
     * Method to check if the selected option is valid
     * @param option Is the option the user has selected
     * @return The option if everything is right
     */
    
    public static String checkOption (String option) {
        boolean bucle;  //For the loop
        do {
            option = isNumeric(option); //Check if option is a number
            if ((Integer.parseInt(option) < 1)  //Check if value is valid
                    || (Integer.parseInt(option) > 8)) {
                System.err.println("Choose a valid option");
                bucle = false;
            } else {
                bucle = true;
            }
        } while (!bucle);
        return option;
    }
    
    /**
     * Method to check if the introduced data is a number
     * @param number is the data we're going to check
     * @return The introduced value if it's ok (is a number)
     */
    
    public static String isNumeric (String number) {
        boolean bucle = true;
        do {
            number = sc.next(); //Introduce value to be checked
            try {   
                Long.parseLong(number); //We parse the String to a number
                bucle = true;
            } catch (NumberFormatException nfe) {   //If it's not a number
                System.err.println("Enter a number");
                bucle = false;
            }
        } while (!bucle);
        return number;
    }
    
    /**
     * Main method to initialize the program
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        String option = "";  //Variable for the menu
        String team1 = "";   //Team 1 to be chosen
        String team2 = "";   //Team 2 to be chosen
        String points = ""; //Points for first match and team 1
        int equipos = 0;    //Variable to print the teams properly in option 5
        int dimension = Integer.parseInt(args[0]);  //We read by command line
        int [][] matriz = new int [dimension] [dimension];  //Array for league
        int [] suma = new int [dimension];  //Array to store the total sum
        int [] maximo = new int [dimension];    //Array to store the max point
        
        setSame(matriz);    //Set teams to -1 if they're the same
        do {    //To continuously execute the menu
            showOptions();  //We show the options
            option =  checkOption(option);  //Check if selected option is ok
            switch (Integer.parseInt(option)) {
                case 1:
                    readFile(matriz);   //Call method to read file
                    break;
                    
                case 2:
                    System.out.println("There are "+ matriz.length + 
                            " teams, choose the teams who will play\n"
                            + "Team 1: ");
                    team1 = teamExist(team1, matriz.length);    //Check if exist
                    
                    System.out.println("Team 2:");
                    team2 = sameTeams(team1, team2, matriz.length);    //Check if same as first team
                    
                    System.out.println("How many points did Team "+ team1
                        + " get?");
                    points = checkPoints(points);
                    
                    setScoreOneMatch(Integer.parseInt(team1)-1, //Call method
                            Integer.parseInt(team2)-1, 
                            Integer.parseInt(points), matriz);
                    
                    System.out.println("Results have been saved");
                    break;
                    
                case 3:
                    setScoreOneRound(matriz);   //Call method to fill array
                    break;
                    
                case 4:
                    System.out.println("There are "+ matriz.length + 
                            " teams, choose a team to calculate its "
                            + "total points:");
                    team1 = teamExist(team1, matriz.length);    //Check if exist
                    
                    System.out.println("Total points in the league for "
                            + "Team " + team1 + ": " 
                            + computePointsOne(Integer.parseInt(team1)-1, matriz));
                    break;
                    
                case 5:
                    suma = computePointsAll(matriz);    //Compute all points
                    for (int i = 0; i < suma.length; i++) { //Print all points
                        equipos++;
                        System.out.println("Team " + equipos + ": " + suma[i]);
                    }
                    equipos = 0;    //Restart the teams to be shown
                    break;
                    
                case 6:
                    maximo = maxScore(matriz);  //Compute max point
                    System.out.println("Teams with the highest points are: ");
                    for (int i = 0; i < maximo.length; i++) {   //Print
                        if(maximo[i] != 0) {
                            System.out.println("Team " + maximo[i]);
                        }
                    }
                    equipos = 0;    //Restart the teams to be shown
                    break;
                    
                case 7:
                    saveFile(matriz);   //Method to save the file
                    break;
            }
        } while (Integer.parseInt(option) != 8);
    }
}