package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tombe on 24/11/2015.
 */
public class FileReader {

    private String[] lines = new String[6];

    private List<IFileReaderListener> listeners;

    public FileReader() {

        listeners = new ArrayList<>();

    }

    public void readFile(File file) {
        BufferedReader bufferedReader = null;

        try {
            String currentLine;
            int counter = 0;
            bufferedReader = new BufferedReader(new java.io.FileReader(file));

            while ((currentLine = bufferedReader.readLine()) != null) {
                lines[counter] = currentLine;

                counter++;
            }

            for (String s : lines) {
                System.out.println(s);
            }

            for (IFileReaderListener listener : listeners) {
                listener.FileReaded(lines);
            }

        } catch (IOException e) {

        }

    }

    public void addListener(IFileReaderListener fileReaderListener) {
        listeners.add(fileReaderListener);
    }

}
