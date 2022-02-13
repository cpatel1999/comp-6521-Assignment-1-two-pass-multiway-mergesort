import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Generator class to generate the random numbers.
 */
public class RandomNumberGenerator {

    /**
     * Generates the given numbers of random numbers in specific range.
     *
     * @param count count to generate random numbers.
     */
    public void generate(int count) {
        FileWriter writer = null;
        BufferedWriter bufferedWriter = null;
        int randomNumber;
        try {
            writer = new FileWriter("docs/input.txt");
            bufferedWriter = new BufferedWriter(writer);
            Random random = new Random();
            for(int i = 0; i < count; i++) {
                randomNumber = random.nextInt(0, count * 5);
                bufferedWriter.write(randomNumber + "\n");
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}