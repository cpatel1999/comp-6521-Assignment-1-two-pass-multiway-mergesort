import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("1. Create a random list of integers");
        System.out.println("2. Display the random list");
        System.out.println("3. Run 2PMMS\n");
        System.out.println("Enter your choice:");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                System.out.println("Enter the count for random number:");
                int count = sc.nextInt();
                RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();
                randomNumberGenerator.generate(count);
                System.out.println("Numbers have been generated and written in the text file.");
                break;
            case 2:
                break;
            case 3:
                System.out.println("Enter the RAM size");
                int memorySize = sc.nextInt();
                TwoPhaseMultiWayMergeSort sort = new TwoPhaseMultiWayMergeSort();
                sort.twoPhaseSort(memorySize);
                break;
        }
    }
}
