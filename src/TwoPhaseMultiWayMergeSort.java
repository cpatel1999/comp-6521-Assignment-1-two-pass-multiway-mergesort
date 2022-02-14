import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class TwoPhaseMultiWayMergeSort {
    public final QuickSort quickSort = new QuickSort();
    public Path phase1Resource = Paths.get("resources", "phase_1");
    public Path phase2Resource = Paths.get("resources", "phase_2");

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

    public void runPhase2(ArrayList<Path> list, int blockSize) {
        ArrayList<BufferedReader> inputBlockFiles = new ArrayList<>();
        ArrayList<String> corruptedFiles = new ArrayList<>();
        for (Path inputBlock : list) {
            try {
                inputBlockFiles.add(new BufferedReader(new FileReader(inputBlock.toFile()), blockSize /* bytes */));
            } catch (IOException ignored) {
                corruptedFiles.add(inputBlock.toString());
            }
        }

        int outputBlockIndex = 0;
        while (outputBlockIndex < inputBlockFiles.size()) {
            PriorityQueue<Integer> bufferedNumbers = new PriorityQueue<>(inputBlockFiles.size());
            for (BufferedReader bufferedReader : inputBlockFiles) {
                try {
                    String line;
                    if ((line = bufferedReader.readLine()) != null) {
                        bufferedNumbers.add(Integer.parseInt(line));
                    }
                } catch (IOException ignored) {
                }
            }

            String fileName = "merged-block-" + outputBlockIndex + ".txt";
            try (PrintWriter pw = new PrintWriter(new FileWriter(Paths.get(phase2Resource.toString(), fileName).toFile()))) {
                // Dump merged numbers to disk.
                while (!bufferedNumbers.isEmpty()) pw.println(bufferedNumbers.poll());
            } catch (IOException ignored) {
            }

            outputBlockIndex++;
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