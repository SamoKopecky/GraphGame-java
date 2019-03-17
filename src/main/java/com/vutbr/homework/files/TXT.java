package com.vutbr.homework.files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TXT {
    public static void writeToFile(String stringToWrite, String fileName) {
        File file = new File(fileName);
        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(stringToWrite);
            bw.newLine();
            bw.flush();
            bw.close();
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.getStackTrace();
            System.out.println("can't write to file");
        }
    }

    public static String createDir() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date date = new Date();
        String formattedDate = dateFormat.format(date);
        new File("./resources/" + formattedDate).mkdirs();
        return formattedDate;
    }
}
