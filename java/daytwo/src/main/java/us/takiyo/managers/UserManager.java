package us.takiyo.managers;

import us.takiyo.interfaces.User;
import us.takiyo.utils.UserComparator;
import us.takiyo.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class UserManager {
    List<User> users = new ArrayList<>();
    FileManager fileManager = new FileManager("users.txt");
    boolean synced = false;

    public UserManager() {
        this.sync();
    }

    private void sync() {
        Vector<String> data = this.fileManager.read();
        for (String d : data) {
            String[] yes = d.split("#");
            this.addUser(new User(yes[0], Utils.convertToInt(yes[1], 0), Utils.convertToInt(yes[2], 0)));
        }
    }

    public void save() {
        ArrayList<String> data = new ArrayList<>();
        for (User user : this.users) {
            data.add(String.format("%s#%d#%d", user.Username, user.Moves, user.Scores));
        }
        String[] str = new String[data.size()];
        str = data.toArray(str);
        this.fileManager.write(str);
    }

    public void addUser(User newUser) {
        this.users.add(newUser);
        this.save();
    }

    public List<User> GetSortedUsers() {
        this.users.sort(new UserComparator());
        return this.users;
    }

    public User GetUser(String username) {
        for (User user : this.users) {
            if (Objects.equals(user.Username, username)) {
                return user;
            }
        }
        return null;
    }
}