package us.takiyo.utils;

import us.takiyo.interfaces.User;

import java.util.Comparator;

public class UserComparator implements Comparator<User> {
    @Override
    public int compare(User a, User b) {
        return a.Scores - b.Scores;
    }
}