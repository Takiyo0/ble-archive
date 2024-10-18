package us.takiyo.interfaces;

import us.takiyo.enums.Trainee.Gender;

public class Trainee {
    private String name;
    private String traineeCode;
    private Gender gender;
    private int score;

    public Trainee() {

    }

    public Trainee(String traineeCode, String name, Gender gender, int score) {
        this.traineeCode = traineeCode;
        this.name = name;
        this.gender = gender;
        this.score = score;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTraineeCode() {
        return traineeCode;
    }

    public void setTraineeCode(String traineeCode) {
        this.traineeCode = traineeCode;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

