package ch.makery.address.util;

import ch.makery.address.model.Graph;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WorkWithFiles {

    public void fileCreateAndAdd(String name, Graph graph) {
        try {
            FileWriter writer = new FileWriter(name + ".txt");

            writer.write(String.valueOf(graph.getMatrixAdjancy()));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void readFromFile(String name,Graph graph) {
        try (FileReader reader = new FileReader(name)) {
            // читаем посимвольно
            int c;
            while ((c = reader.read()) != -1) {
                if (Character.getNumericValue((char) c) != -1)

                    System.out.print(Character.getNumericValue((char) c));


            }
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
    }
}
