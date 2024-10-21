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

    private void create() {
        try {
            File file = new File(this.filename);
            file.createNewFile();
        } catch (Exception ignored) {
        }
    }

    public void write(String[] data) {
        try {
            FileWriter file = new FileWriter(this.filename);
            file.write(String.join("\n", data));
            file.close();
        } catch (Exception ignored) {
        }
    }

    public void write(String data) {
        try {
            FileWriter file = new FileWriter(this.filename);
            file.write(data);
            file.close();
        } catch (Exception ignored) {
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

    public String readString() {
        try {
            FileReader file = new FileReader(this.filename);
            Scanner scanner = new Scanner(file);
            return scanner.nextLine();
        } catch (Exception ignored) {
            return null;
        }
    }
}