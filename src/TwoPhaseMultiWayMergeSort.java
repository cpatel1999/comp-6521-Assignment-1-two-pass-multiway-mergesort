import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class TwoPhaseMultiWayMergeSort {

    public QuickSort quickSort = new QuickSort();
    public String path = "docs/";
    public ArrayList<String> filesList = new ArrayList<>();

    public void twoPhaseSort(int ramSize) {
        phase_1(ramSize);
    }

    public void phase_1(int ramSize) {

        boolean run = true;
        int recordCount = 0;
        int dataCount = 0;
        int currentBlock = 1;
        try (Scanner fileReader = new Scanner(new FileReader("docs/file1.txt"))) {
            while (run) {

                LinkedList<Integer> numberList = new LinkedList<>();
                while (fileReader.hasNextLine()) {

                    int number = Integer.parseInt(fileReader.nextLine().trim());
                    numberList.add(number);
                    recordCount++;
                    ++dataCount;
                    if (dataCount == ramSize) {
                        dataCount = 0;
                        break;
                    }
                }
                numberList = quickSort.sort(numberList);

                String outputFile = path + "block-" + currentBlock;
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
                for (int i = 0; i < numberList.size(); i++) {
                    writer.write(numberList.get(i) + "\n");
                }
                writer.close();

                filesList.add(outputFile);

                if (!fileReader.hasNextLine()) {
                    break;
                }
                currentBlock++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("IO Error!");
            System.exit(0);
        }
    }
}


//        int currentBlockCount = 0;
//        int numbersCount = numberList.size();
//        int blockCount = (int) Math.ceil((double)numbersCount / (double)ramSize);
//        System.out.println("Blocks count: " + blockCount);
//        int[] singleBlock = new int[ramSize];
//
//        for(int i = 0; i < numbersCount; i++) {
//            if(currentBlockCount < blockCount) {
//                singleBlock
//            }
//        }