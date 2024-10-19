package us.takiyo.controller;

import us.takiyo.extensions.TakiyoList;
import us.takiyo.managers.EncryptionManager;

import java.util.Objects;

public class Player {
    private String username;
    private int score = 0;
    private int floor = 0;
    private final String hashedPassword;
    private Character character;
    private TakiyoList<Skill> skills = new TakiyoList<>();

    public Player(String username, String hashedPassword) {
        this.username = username;
        System.out.println(hashedPassword + " from player");
        this.hashedPassword = hashedPassword;
    }

    public boolean comparePassword(String password) {
        String hashed = EncryptionManager.Encrypt(password);
        System.out.printf("Comparing (%s) %s and %s", password, hashed, hashedPassword);
        return Objects.equals(hashedPassword, hashed);
    }

    public void save() {

    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getFloor() {
        return floor;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public TakiyoList<Skill> getSkills() {
        return skills;
    }

    public void addSkill(Skill skill) {
        skills.add(skill);
    }
}
