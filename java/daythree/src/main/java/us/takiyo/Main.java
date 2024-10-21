package us.takiyo;

import us.takiyo.interfaces.Trainee;
import us.takiyo.managers.TraineeManager;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Vector;

public class Main {
    TraineeManager traineeManager = new TraineeManager();
    Scanner scanner = new Scanner(System.in);
    String loggedInTrainee;


    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        int state = 0; // 0 = home; 1 = recsel; 2 = trainee; 3 = exit
        while (true) {
            this.clearTerminal();
            switch (state) {
                case 0: {
                    System.out.print(
                            "-- Main Menu --\n" +
                                    "1. Recsel Operations\n" +
                                    "2. Trainee Operations\n" +
                                    "3. Exit\n" +
                                    "Choose an option:\n> "
                    );
                    int choice = this.getInt();
                    if (choice == -1 || (choice < 1 && choice > 3)) {
                        this.sendError("Invalid choice");
                        continue;
                    }
                    state = choice;
                    break;
                }

                case 1: {
                    this.renderRecsel();
                    state = 0;
                    break;
                }

                case 2: {
                    int log = this.renderLogin();
                    if (log == 1) this.renderTrainee();
                    state = 0;
                    break;
                }

                case 3: {
                    return;
                }
            }
        }
    }

    private void renderRecsel() {
        int state = 0; // 0 = home; 1 = all trainee; 2 = edit trainee score; 3 = create trainee; 4 = back
        while (true) {
            clearTerminal();
            switch (state) {
                case 0: {
                    System.out.print(
                            """
                                    -- Recsel Menu --
                                    1. Show All Trainees
                                    2. Edit Trainee Score
                                    3. Create Trainee
                                    4. Back to Main Menu
                                    Choose your option:
                                    >\s"""
                    );
                    int choice = this.getInt();
                    if (choice == -1 || (choice < 1 && choice > 4)) {
                        this.sendError("Invalid choice");
                        continue;
                    }
                    if (choice == 4) {
                        return;
                    }
                    state = choice;
                    break;
                }

                case 1: {
                    System.out.println("-- Trainee's Score --");
                    System.out.printf("| %-10s | %-10s | %-25s | %-10s | %-10s |\n", "Index", "T Number", "Name", "Gender", "Score");
                    System.out.println("---------------------------------------------------------------------------");
                    Vector<Trainee> trainees = this.traineeManager.getSortedTrainees();

                    int index = 0;
                    for (Trainee t : trainees)
                        System.out.printf("| %-10s | %-10s | %-25s | %-10s | %-10s |\n", ++index, t.getTraineeCode(), t.getName(), t.getGender(), t.getScore());
                    System.out.println("---------------------------------------------------------------------------");
                    this.waitForEnter();
                    state = 0;
                    break;
                }

                case 2: {
                    System.out.println(" -- Edit Trainee's Score --");
                    String code;
                    int newScore;
                    Trainee trainee;
                    while (true) {
                        clearTerminal();
                        System.out.print("Enter the trainee's code\n> ");
                        code = this.scanner.nextLine();
                        trainee = this.traineeManager.getTraineeByCode(code);
                        System.out.print("Enter the new score\n> ");
                        newScore = this.getInt();
                        if (newScore == -1) {
                            this.sendError("Invalid point");
                            continue;
                        }
                        break;
                    }
                    if (trainee == null) {
                        this.sendError("Trainee does not exist");
                        continue;
                    }
                    int previous = trainee.getScore();
                    trainee.setScore(newScore);
                    System.out.printf("Score updated from %d to %d for %s (%s)\n", previous, newScore, trainee.getName(), trainee.getTraineeCode());
                    waitForEnter();
                    this.traineeManager.save();
                    state = 0;
                    break;
                }

                case 3: {
                    int st = 0; // 0 = trainee code; 1 = name; 2 = gender; 3 = init score; 4 = done; 5
                    Trainee trainee = new Trainee();
                    while (st != 5) {
                        clearTerminal();
                        System.out.println("-- Create Trainee --");
                        switch (st) {
                            case 0: {
                                System.out.print("Enter the trainee code [txxx where x = number]\n> ");
                                String code = scanner.nextLine();
                                if (!code.toLowerCase().startsWith("t")) {
                                    this.sendError("Invalid trainee code");
                                    continue;
                                }
                                int realCode = this.parseInt(code.substring(1, 4));
                                if (realCode > 999 || realCode < 100) {
                                    this.sendError("Invalid trainee code");
                                    continue;
                                }
                                trainee.setTraineeCode(code);
                                st++;
                                break;
                            }
                            case 1: {
                                System.out.print("Enter the trainee's name\n> ");
                                String name = scanner.nextLine();
                                if (name.length() > 10) {
                                    this.sendError("Invalid trainee name");
                                    continue;
                                }
                                trainee.setName(name);
                                st++;
                                break;
                            }

                            case 2: {
                                System.out.print("Enter the trainee's gender (Male/Female)\n> ");
                                String gender = scanner.nextLine();
                                gender = gender.toLowerCase();
                                if (Objects.equals(gender, "male"))
                                    trainee.setGender(us.takiyo.enums.Trainee.Gender.Male);
                                else if (Objects.equals(gender, "female"))
                                    trainee.setGender(us.takiyo.enums.Trainee.Gender.Female);
                                else {
                                    this.sendError("Invalid trainee gender");
                                    continue;
                                }
                                st++;
                                break;
                            }

                            case 3: {
                                System.out.print("Enter the trainee's score\n> ");
                                int score = this.getInt();
                                if (score < 0) {
                                    this.sendError("Invalid score");
                                    continue;
                                }
                                trainee.setScore(score);
                                st++;
                                break;
                            }

                            case 4: {
                                this.traineeManager.addTrainee(trainee);
                                System.out.printf("Added [%s] %s", trainee.getTraineeCode(), trainee.getName());
                                waitForEnter();
                                st++;
                                state = 0;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private int renderLogin() {
        clearTerminal();
        System.out.print("Enter your trainee code\n> ");
        String code = scanner.nextLine();
        Trainee trainee = this.traineeManager.getTraineeByCode(code);
        if (trainee == null) {
            this.sendError("Trainee does not exist");
            return 0;
        }
        loggedInTrainee = trainee.getTraineeCode();
        return 1;
    }

    private void renderTrainee() {
        int state = 0; // 0 = home; 1  = show profile; 2 = view leaderboard; 3 = logout
        while (true) {
            clearTerminal();
            switch (state) {
                case 0: {
                    System.out.print(
                            """
                                    -- Trainee Menu --
                                    1. Show Your Profile
                                    2. View Leaderboard
                                    3. Logout
                                    Choose an option
                                    >\s"""
                    );
                    int choice = this.getInt();
                    if (choice < 1 || choice > 3) {
                        this.sendError("Invalid choice");
                        continue;
                    }
                    state = choice;
                    break;
                }

                case 1: {
                    Trainee trainee = traineeManager.getTraineeByCode(loggedInTrainee);
                    System.out.printf(
                            """
                                    -- Your Profile [%s] --
                                    Name   : %s
                                    Gender : %s
                                    Score  : %s
                                    """, loggedInTrainee, trainee.getName(), trainee.getGender(), trainee.getScore());
                    waitForEnter();
                    state = 0;
                    break;
                }

                case 2: {
                    Vector<Trainee> trainees = traineeManager.getSortedTrainees();
                    System.out.println("-- Trainee's Leaderboard --");
                    System.out.printf("| %-10s | %-10s | %-25s | %-10s | %-10s |\n", "Index", "T Number", "Name", "Gender", "Score");
                    System.out.println("---------------------------------------------------------------------------");

                    int index = 0;
                    for (Trainee t : trainees)
                        System.out.printf("| %-10s | %-10s | %-25s | %-10s | %-10s |\n", ++index, t.getTraineeCode(), t.getName(), t.getGender(), t.getScore());
                    System.out.println("---------------------------------------------------------------------------");
                    this.waitForEnter();
                    state = 0;
                    break;
                }

                case 3: {
                    System.out.println("You're about to be logged out...");
                    this.loggedInTrainee = null;
                    return;
                }
            }
        }
    }

    private void sendError(String message) {
        System.out.println(message);
        this.waitForEnter();
    }

    private void waitForEnter() {
        try {
            System.out.println("Press ENTER to continue...");
            int a = System.in.read();
        } catch (IOException ignored) {
        }
    }

    public int parseInt(String any) {
        try {
            return Integer.parseInt(any);
        } catch (NumberFormatException ignored) {
            return 0;
        }
    }

    public int getInt() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void clearTerminal() {
        for (int i = 0; i < 50; i++)
            System.out.println();
    }
}