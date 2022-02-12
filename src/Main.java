import java.util.Scanner;

public class Main {

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the count for random number:");
        int count = sc.nextInt();

        RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();
        randomNumberGenerator.generate(count);
    }
}
