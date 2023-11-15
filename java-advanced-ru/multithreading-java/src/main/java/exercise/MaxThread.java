package exercise;

import java.util.Arrays;

// BEGIN
public class MaxThread extends Thread {

    private int[] numbers;
    private int max = 0;

    @Override
    public void run() {
        max = Arrays.stream(numbers).max().getAsInt();
    }

    public MaxThread(int[] numbers) {
        this.numbers = numbers;
    }

    public int getMax() {
        return max;
    }
}
// END
