package exercise;

import java.util.Arrays;

// BEGIN
public class MaxThread extends Thread{

    private final int[] numbers;
    private int maxNumber;

    public MaxThread(int[] numbers) {
        this.numbers = numbers;
        this.setName("MaxThread");
    }

    @Override
    public void run() {
        maxNumber = Arrays.stream(numbers).max().getAsInt();
    }

    public int getMinNumber() {
        return maxNumber;
    }
}
// END
