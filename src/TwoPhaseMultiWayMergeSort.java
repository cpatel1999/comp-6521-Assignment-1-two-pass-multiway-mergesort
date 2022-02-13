import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class TwoPhaseMultiWayMergeSort {

    public QuickSort quickSort = new QuickSort();
    public String path = "docs/";
    public ArrayList<String> filesList = new ArrayList<>();
    public int totalBlocks;

    public void twoPhaseSort(int ramSize) {
        System.out.println("----------------------Phase 1--------------------");
        System.out.println("-------------------------------------------------");
        phase_1(ramSize);
        System.out.println("----------------------Phase 2--------------------");
        System.out.println("-------------------------------------------------");
        phase_2(ramSize);
    }

    public void phase_1(int ramSize) {

        boolean run = true;
        int recordCount = 0;
        int dataCount = 0;
        int currentBlock = 1;
        try (Scanner fileReader = new Scanner(new FileReader("docs/input.txt"))) {
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

                System.out.println("Block-" + currentBlock + " before sorting");
                display(numberList);
                numberList = quickSort.sort(numberList);
                System.out.println("Block-" + currentBlock + " after sorting");
                display(numberList);

                String outputFile = path + "block-" + currentBlock + ".txt";
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
            totalBlocks = currentBlock;
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("IO Error!");
            System.exit(0);
        }
    }

    public void phase_2(int ramSize) {
        mergeSort(filesList);
    }

    public void mergeSort(ArrayList<String> list) {
        String currentMergeFiles = null;
        ArrayList<String> mergedFiles = new ArrayList<>();
        int num_1;
        int num_2;
        int min;
        int iteration = 0;
        for(int i = 0; i < list.size(); i = i + 2) {
            currentMergeFiles = path + iteration + "-block-"+ i + "_" + (i+1) + ".txt";
//            System.out.println(i);
            try{
                Scanner scanner_1 = new Scanner(new FileReader(list.get(i)));
                Scanner scanner_2 = null;
                if(i+1 < list.size()) {
                    scanner_2 = new Scanner(new FileReader(list.get(i + 1)));
                }
                System.out.println(scanner_1);
                System.out.println(scanner_2);

                BufferedWriter writer = new BufferedWriter(new FileWriter(currentMergeFiles));
                if(scanner_2 != null) {
                    num_1 = scanner_1.nextInt();
                    num_2 = scanner_2.nextInt();
                    while (true) {
                        if (num_1 < num_2) {
                            min = num_1;
                            if (scanner_1.hasNext()) {
                                num_1 = scanner_1.nextInt();
                            } else {
                                writer.write(min + "\n");
                                writer.write(num_2 + "\n");
                                break;
                            }
                        } else {
                            min = num_2;
                            if (scanner_2.hasNext()) {
                                num_2 = scanner_2.nextInt();
                            } else {
                                writer.write(min + "\n");
                                writer.write(num_1 + "\n");
                                break;
                            }
                        }
                        writer.write(min + "\n");
                    }

                    while (scanner_1.hasNextLine()) {
                        writer.write(scanner_1.nextInt());
                    }
                    while (scanner_2.hasNextLine()) {
                        writer.write(scanner_2.nextInt());
                    }
                    scanner_2.close();
                }
//                else {
//                    while (scanner_1.hasNextLine()) {
//                        writer.write(scanner_1.nextInt());
//                    }
//                }
                mergedFiles.add(currentMergeFiles);
                scanner_1.close();
                writer.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found!");
            } catch (IOException e) {
                System.out.println("IO Error!");
            }
        }
        if(mergedFiles.size() > 1) {
            mergeSort(mergedFiles);
        }
    }



    public void display(LinkedList<Integer> list) {
        for(int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}