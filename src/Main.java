import java.util.Scanner;

public class Main {

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the count for random number:");
        int count = sc.nextInt();

        RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();
        randomNumberGenerator.generate(count);
        System.out.println("Numbers have been generated and written in the text file.");

        System.out.println("Enter the RAM size");
        int memorySize = sc.nextInt();
        TwoPhaseMultiWayMergeSort sort = new TwoPhaseMultiWayMergeSort();
        sort.twoPhaseSort(memorySize);
    }
}
