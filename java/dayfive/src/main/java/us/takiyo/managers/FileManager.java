package us.takiyo.managers;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {
    String fileName;

    public FileManager(String fileName) {
        this.fileName = fileName;
        this.createFile();
    }

    private void createFile() {
        try {
            File file = new File(this.fileName);
            if (file.createNewFile()) {
                System.out.println("File created");
            }
        } catch (IOException ignored) {
        }
    }

    public void write(String[] content) {
        try {
            FileWriter file = new FileWriter(this.fileName);
            file.write(String.join("\n", content));
            file.close();
        } catch (IOException ignored) {
        }
    }

    public String[] read() {
        try {
            FileReader file = new FileReader(this.fileName);
            Scanner scanner = new Scanner(file);
            List<String> list = new ArrayList<>();

            while (scanner.hasNextLine()) {
                list.add(scanner.nextLine());
            }

            String[] data = new String[list.size()];
            list.toArray(data);
            return data;
        } catch (IOException ignored) {
            return null;
        }
    }
}
