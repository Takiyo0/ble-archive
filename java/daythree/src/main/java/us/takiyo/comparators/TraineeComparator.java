package us.takiyo.comparators;

import us.takiyo.interfaces.Trainee;

import java.util.Comparator;

public class TraineeComparator implements Comparator<Trainee> {
    public int compare(Trainee t1, Trainee t2) {
        return t2.getScore() - t1.getScore();
    }
}