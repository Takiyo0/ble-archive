package us.takiyo.managers;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Vector;

public class FileManager {
    String filename;

    public FileManager(String filename) {
        this.filename = filename;
        this.create();
    }

    private boolean create() {
        try {
            File file = new File(this.filename);
            if (file.createNewFile()) {
                return true;
            }
            return false;
        } catch (Exception ignored) {
            return false;
        }
    }

    public boolean write(String[] data) {
        try {
            FileWriter file = new FileWriter(this.filename);
            file.write(String.join("\n", data));
            file.close();
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public Vector<String> read() {
        Vector<String> d = new Vector<>();
        try {
            FileReader file = new FileReader(this.filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                d.add(data);
            }

            return d;
        } catch (Exception ignored) {
            return d;
        }
    }
}
