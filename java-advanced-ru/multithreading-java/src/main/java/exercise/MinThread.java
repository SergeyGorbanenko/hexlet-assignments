package exercise;

import java.util.Arrays;

// BEGIN
public class MinThread extends Thread {

    private int[] numbers;
    private int min = 0;

    @Override
    public void run() {
        min = Arrays.stream(numbers).min().getAsInt();
    }

    public MinThread(int[] numbers) {
        this.numbers = numbers;
    }

    public int getMin() {
        return min;
    }
}
// END
