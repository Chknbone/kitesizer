/* KiteSizeDB.java
 * Written by: CB
 * 
 * Program is being write initially for output to the console. Main method will live here. 
 * After getting console working, may tackle it in Spring or something similar.
 */

package com.palarran.kitesizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console {

    public static void main(String[] args) {

        // initialize KiteSizeDB databases
        Databases prodDB = new Databases();
        prodDB.bootstrapWeightDB();
        prodDB.bootstrapSpeedDB();
        prodDB.bootstrapKiteSizeDB();

        // initialize Console
        
        boolean programRunning = true;
        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
        String userName = null;
        int userWeightPounds = 0;
        int windSpeedKnots = 0;
        Size kiteSize = null;

        while (programRunning) {

            //ask for users name and add it to DB
            System.out.println("Enter your name: ");

            try {
                userName = consoleInput.readLine();
            } catch (IOException e) {
                System.out.println("Names only, no numbers. Try again.");
            }

            boolean nameResult = prodDB.addUser(userName);

            if (nameResult) {
                System.out.println("Welcome back " + userName); //user name exist in DB
            } else {
                System.out.println("Welcome " + userName); // user name does not exist in DB
            }

            // ask for user weight and add to DB
            System.out.print(
                    "This App will determine what size kite you should us based on your weight and the wind speed you provide.");
            System.out.println("\nFrom the following list: ");

            for (Weight data : prodDB.getWeight()) {
                System.out.println(data);
            }

            System.out.println("Enter the weight closet to your weight. Prices Right rules apply");

            try {
                userWeightPounds = Integer.parseInt(consoleInput.readLine());
            } catch (NumberFormatException e) {
                System.out.println("Weights provided must be integers");
            } catch (IOException e) {
                e.printStackTrace();
            }

            //show list of available wind speeds and ask  for input
            System.out.println("\nFrom the following list of wind Speeds: ");

            for (WindSpeed data : prodDB.getWindSpeed()) {
                System.out.println(data);
            }

            System.out.println(
                    "Enter the wind speed closet to the wind speed you are gonna kite in. Prices Right rules apply here as well");
            try {
                windSpeedKnots = Integer.parseInt(consoleInput.readLine());
            } catch (NumberFormatException e) {
                System.out.println("Wind speeds provided must be integers");
            } catch (IOException e) {
                e.printStackTrace();
            }

            //choosing kite size
            for (Size data : prodDB.getKites()) {
                kiteSize = data;
            }

            // combine weight and wind and show suggested kite size
            String kiteInfo = prodDB.addKiteSize(userName, userWeightPounds, windSpeedKnots, kiteSize);
            System.out.println("\nMaths done. Here are the details: ");
            System.out.println(kiteInfo + "\n");
        }
    }
}