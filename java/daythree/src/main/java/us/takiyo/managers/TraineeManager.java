package us.takiyo.managers;

import us.takiyo.comparators.TraineeComparator;
import us.takiyo.interfaces.Trainee;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Vector;

public class TraineeManager {
    Vector<Trainee> trainees = new Vector<>();
    FileManager fileManager = new FileManager("trainees.txt");
    boolean synced = false;

    public TraineeManager() {
        this.sync();
    }

    public void save() {
        Vector<String> trainees = new Vector<>();
        for (Trainee t : this.trainees) {
            trainees.add(String.format("%s#%s#%s#%d", t.getTraineeCode(), t.getName(), t.getGender(), t.getScore()));
        }
        String[] formatted = new String[trainees.size()];
        trainees.toArray(formatted);
        this.fileManager.write(formatted);
    }

    public void sync() {
        if (!synced) {
            Vector<String> loadedTrainees = this.fileManager.read();
            for (String str : loadedTrainees) {
                String[] parsed = str.split("#");
                this.addTrainee(new Trainee(parsed[0], parsed[1], us.takiyo.enums.Trainee.Gender.valueOf(parsed[2]), Integer.parseInt(parsed[3])));

            }
        }
    }

    public Trainee addTrainee(Trainee trainee) {
        trainees.add(trainee);
        save();
        return trainee;
    }

    public Trainee removeTraineeByCode(String code) {
        Trainee trainee = this.getTraineeByCode(code);
        if (trainee != null)
            trainees.remove(trainee);
        save();
        return trainee;
    }

    public Vector<Trainee> getSortedTrainees() {
        this.trainees.sort(new TraineeComparator());
        return this.trainees;
    }

    public Trainee getTraineeByCode(String code) {
        for (Trainee trainee : trainees)
            if (Objects.equals(trainee.getTraineeCode().toLowerCase(), code.toLowerCase()))
                return trainee;
        return null;
    }
}
