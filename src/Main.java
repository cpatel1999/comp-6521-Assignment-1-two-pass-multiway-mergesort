import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int flag = 0;
        Scanner sc = new Scanner(System.in);

        while(flag == 0) {
            System.out.println("\n1. Create a random list of integers");
            System.out.println("2. Display the random list");
            System.out.println("3. Run 2PMMS");
            System.out.println("4. Exit\n");
            System.out.println("Enter your choice:");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter the count for random number:");
                    int count = sc.nextInt();
                    RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();
                    randomNumberGenerator.generate(count);
                    System.out.println("Numbers have been generated and written in the text file.\n");
                    break;

                case 2:
                    System.out.println("Random numbers are generated and stored in the file");
                    try (Scanner fileReader = new Scanner(new FileReader("resources/input.txt"))) {
                        while (fileReader.hasNextLine()) {
                            System.out.println(fileReader.nextLine());
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found");
                    }
                    break;

                case 3:
                    System.out.println("Enter the RAM size");
                    int memorySize = sc.nextInt();
                    TwoPhaseMultiWayMergeSort sort = new TwoPhaseMultiWayMergeSort();
                    sort.twoPhaseSort(memorySize);
                    break;

                case 4:
                    flag = 1;
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
