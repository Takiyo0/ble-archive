package us.takiyo.interfaces;

public class User {
    public String Username;
    public int Moves;
    public int Scores;

    public User(String username, int moves, int scores) {
        this.Username = username;
        this.Moves = moves;
        this.Scores = scores;
    }
}
