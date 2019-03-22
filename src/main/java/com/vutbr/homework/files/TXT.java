package com.vutbr.homework.files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class TXT {
    public void writeToFile(String stringToWrite, String fileName) {
        File file = new File(fileName);
        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(stringToWrite);
            bw.newLine();
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public String createDir() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date date = new Date();
        String formattedDate = dateFormat.format(date);
        new File("./resources/" + formattedDate).mkdirs();
        return formattedDate;
    }
}
