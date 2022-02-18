import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

class Element {
    public int value;
    public int fileIndex;

    public Element(int value, int fileIndex) {
        this.value = value;
        this.fileIndex = fileIndex;
    }
}

public class TwoPhaseMultiWayMergeSort {
    private final QuickSort quickSort = new QuickSort();
    private Path phase1Resource = Paths.get("resources", "phase_1");
    private Path phase2Resource = Paths.get("resources", "phase_2");
    private int totalTuples = 0;

    public void twoPhaseSort(int bufferSize) {
        System.out.println("----------------------Phase 1--------------------");
        System.out.println("-------------------------------------------------");
        ArrayList<Path> fileList = runPhase1(bufferSize);
        System.out.println("----------------------Phase 2--------------------");
        System.out.println("-------------------------------------------------");
        runPhase2(fileList, bufferSize);
    }

    public ArrayList<Path> runPhase1(int bufferSize) {
        ArrayList<Path> referenceFilePaths = new ArrayList<>();
        int dataCount = 0;
        int currentBlock = 0;
        try (Scanner fileReader = new Scanner(new FileReader("resources/input.txt"))) {
            while (fileReader.hasNextLine()) {
                LinkedList<Integer> numberList = new LinkedList<>();
                while (fileReader.hasNextLine()) {
                    int number = Integer.parseInt(fileReader.nextLine().trim());
                    numberList.add(number);
                    ++dataCount;
                    ++totalTuples;
                    if (dataCount == bufferSize) {
                        dataCount = 0;
                        break;
                    }
                }

                display(numberList, "Block-" + (currentBlock + 1) + " before sorting");
                quickSort.sort(numberList); /* In place sorting */
                System.out.println();
                display(numberList, "Block-" + (currentBlock + 1) + " after sorting");

                Path outputFile = Paths.get(phase1Resource.toString(), "block-" + currentBlock + ".txt");
                try (PrintWriter printWriter = new PrintWriter(new FileWriter(outputFile.toFile()))) {
                    for (Integer num : numberList) {
                        printWriter.println(num);
                    }
                }

                referenceFilePaths.add(outputFile);
                currentBlock++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("IO Error!");
            System.exit(0);
        }

        return referenceFilePaths;
    }

    public void runPhase2(ArrayList<Path> list, int bufferSize) {
        ArrayList<BufferedReader> inputBlockFiles = new ArrayList<>();
        ArrayList<String> corruptedFiles = new ArrayList<>();
        for (Path inputBlock : list) {
            try {
                inputBlockFiles.add(new BufferedReader(new FileReader(inputBlock.toFile()), 4 /* bytes */));
            } catch (IOException ignored) {
                corruptedFiles.add(inputBlock.toString());
            }
        }

        int numOfTuples = 2 * bufferSize;
        int passCounter = 0;
        int processedTuples = 0;

        System.out.println();
        System.out.println("=============================================");
        System.out.println("Pass-" + (passCounter + 1));
        System.out.println("=============================================");

        String fileName = "merged-sorted.txt";
        try (PrintWriter pw = new PrintWriter(new FileWriter(Paths.get(phase2Resource.toString(), fileName).toFile()))) {
            PriorityQueue<Element> bufferedNumbers = new PriorityQueue<>(inputBlockFiles.size(), (a, b) -> a.value - b.value);
            for (int i = 0; i < inputBlockFiles.size(); i++) {
                BufferedReader bufferedReader = inputBlockFiles.get(i);
                try {
                    String line;
                    if ((line = bufferedReader.readLine()) != null) {
                        bufferedNumbers.add(new Element(Integer.parseInt(line), i));
                    }
                } catch (IOException ignored) {
                }
            }

            // Dump merged numbers to disk.
            while (!bufferedNumbers.isEmpty()) {
                Element minElement = bufferedNumbers.poll();

                pw.println(minElement.value);
                System.out.println(minElement.value);

                processedTuples++;
                if (numOfTuples == processedTuples) {
                    numOfTuples *= 2;
                    processedTuples = 0;
                    passCounter++;

                    // New pass
                    System.out.println();
                    System.out.println("=============================================");
                    System.out.println("Pass-" + (passCounter + 1));
                    System.out.println("=============================================");
                }

                String line;
                if ((line = inputBlockFiles.get(minElement.fileIndex).readLine()) != null) {
                    bufferedNumbers.add(new Element(Integer.parseInt(line), minElement.fileIndex));
                }
            }
        } catch (IOException ignored) {
        }

        if (corruptedFiles.size() > 0) {
            // Corrupted files
            System.out.println("Corrupted files:");
            for (String corruptedFile : corruptedFiles)
                System.out.println(corruptedFile);
        }
    }

    public void display(LinkedList<Integer> list, String title) {
        if (!title.isEmpty()) {
            System.out.println(title);
        }
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }
}