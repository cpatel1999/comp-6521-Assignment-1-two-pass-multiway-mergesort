import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

public class TwoPhaseMultiWayMergeSort {

    public LinkedList<Integer> numberList = new LinkedList<>();
    QuickSort quickSort = new QuickSort();
    public void twoPhaseSort(int ramSize) {
        try (Scanner fileReader = new Scanner(new FileReader("docs/file1.txt"))) {
            while (fileReader.hasNextLine()) {
                int number = Integer.parseInt(fileReader.nextLine().trim());
                numberList.add(number);
            }

            phase_1(ramSize);
        }catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public void phase_1(int ramSize) {
        int currentBlockCount = 0;
        int numbersCount = numberList.size();
        int blockCount = (int) Math.ceil((double)numbersCount / (double)ramSize);
        System.out.println("Blocks count: " + blockCount);


    }
}