package com.shoaib.documentsearch;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocumentSearcher {

    int searchOption;

    Path path1 = Paths.get("documents/french_armed_forces.txt");
    Path path2 = Paths.get("documents/hitchhikers.txt");
    Path path3 = Paths.get("documents/warp_drive.txt");

    Path[] paths = {path1, path2, path3};

    String file1 = new String(Files.readAllBytes(path1));
    String file2 = new String(Files.readAllBytes(path2));
    String file3 = new String(Files.readAllBytes(path3));

    String[] files = {file1, file2, file3};

    HashMap<String, HashMap<String, Integer>> fileIndexedData = new HashMap<String, HashMap<String, Integer>>();

    public DocumentSearcher() throws IOException {
    }

    public static void main(String args[]) {

        System.out.print("Enter the Search Term: \t");
        String searchTerm = new Scanner(System.in).nextLine();

        System.out.print("Search Method:1) String Match 2) Regular Expression 3) Indexed:");
        int searchOption = new Scanner(System.in).nextInt();

        try {
            DocumentSearcher documentSearcher = new DocumentSearcher();
            // preprocess the files for indexed search
            if (searchOption == 3) {
                for (int i = 0; i < 3; i++) {
                    documentSearcher.preProcessFile(documentSearcher.files[i], documentSearcher.paths[i].getFileName().toString());
                }
            }
            documentSearcher.search(searchOption, searchTerm);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void search(int searchOption, String searchTerm) throws IOException {

        int[] counts = new int[3];

        switch (searchOption) {
            case 1 -> {
                for (int i = 0; i < 3; i++) {
                    counts[i] = simpleSearch(searchTerm, files[i]);
                }
                printResult(counts);
            }
            case 2 -> {
                for (int i = 0; i < 3; i++) {
                    counts[i] = regExSearch(searchTerm, files[i]);
                }
                printResult(counts);
            }
            case 3 -> {
                for (int i = 0; i < 3; i++) {
                    counts[i] = indexedSearch(searchTerm, paths[i].getFileName());
                }
                printResult(counts);
            }
            default -> System.out.println("Invalid Selection");
        }
    }



    int simpleSearch(String searchTerm, String file) {
        int count = 0;
        String[] words = file.split("[\\s]+");
        for (String word : words) {
            if ((word.toLowerCase()).contains(searchTerm.toLowerCase())) {
                count++;
            }
        }
        return count;
    }

    int regExSearch(String searchTerm, String file) {
        int count = 0;
        Pattern pattern = Pattern.compile(searchTerm, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(file);
        while (matcher.find()) {
            count++;
        }
        return count;
    }


    int indexedSearch(String searchTerm, Path fileName) {
        int count = 0;
        HashMap<String, Integer> indexedData = fileIndexedData.get(fileName.toString());
        for (String key : indexedData.keySet()) {
            if ((key.toLowerCase()).contains(searchTerm.toLowerCase()))
                count = count + indexedData.get(key);
        }
        return count;
    }

    void preProcessFile(String file, String fileName) throws IOException {
        // map contains the word as key and number of occurrences in value
        HashMap<String, Integer> indexedData = new HashMap<String, Integer>();
        String[] words = file.split("[\\s]");
        for (String word : words) {
            if (indexedData.containsKey(word)) {
                indexedData.put(word.toLowerCase(), indexedData.get(word.toLowerCase()) + 1);
            } else {
                indexedData.put(word.toLowerCase(), 1);
            }
        }
        fileIndexedData.put(fileName, indexedData);
    }

    private void printResult(int[] counts) {
        // sort the results based on relevance
        int n = counts.length;
        int temp = 0;
        String tempString;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (counts[j - 1] < counts[j]) {

                    temp = counts[j - 1];
                    counts[j - 1] = counts[j];
                    counts[j] = temp;

                    tempString = files[j - 1];
                    files[j - 1] = files[j];
                    files[j] = tempString;
                }
            }
        }
        System.out.println("Search Results:");
        for (int i = 0; i < 3; i++)
            System.out.println(paths[i].getFileName() + " has " + counts[i] + " matches");
    }

}
