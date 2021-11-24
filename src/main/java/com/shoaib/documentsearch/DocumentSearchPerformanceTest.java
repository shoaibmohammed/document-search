package com.shoaib.documentsearch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class DocumentSearchPerformanceTest extends DocumentSearcher{

    String[] searchTypes = {"String Match", "Regular Expression", "Indexed"};

    private static final int TEST_ITERATIONS = 2000000;

    String wordBank = new String(Files.readAllBytes(Paths.get("dictionary/dictionary.txt")));

    String[] words = wordBank.split("[\\n]+");

    public DocumentSearchPerformanceTest() throws IOException {
        super();
    }

    public static void main(String[] args) {
        try {
            DocumentSearchPerformanceTest test = new DocumentSearchPerformanceTest();
            test.performTest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void performTest() throws IOException {
        long[] starttime = new long[3];
        long[] endtime = new long[3];

        for (int i = 0; i < 3; i++) {
            starttime[i] = System.currentTimeMillis();
             searchOption = i + 1;
            if (searchOption == 3) {
                for (int k = 0; k < 3; k++) {
                    preProcessFile(files[k], paths[k].getFileName().toString());
                }
            }
            for (int j = 0; j < TEST_ITERATIONS; j++) {
                search(searchOption, randomWord());
            }
            endtime[i] = System.currentTimeMillis();
        }

        System.out.println("\nResults of performance test with " + TEST_ITERATIONS + " iterations");
        for (int i = 0; i < 3; i++) {
            System.out.println("Time taken to run " + searchTypes[i] + " search is : " + (endtime[i] - starttime[i]) + " ms");
        }
    }

    String randomWord() {
        Random random = new Random();
        int randomIdx = random.nextInt(words.length);
        return words[randomIdx];
    }
}
