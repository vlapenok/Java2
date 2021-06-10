package lesson5;

public class MainApp {
    static final int SIZE = 10_000_000;
    static final int HALF = SIZE / 2;

    public static void main(String[] args) throws InterruptedException {
        methodOne(); // У меня примерно 6400 - 6600
        methodTwo(); // У меня примерно 2300 - 2400
    }

    public static void methodOne() {
        float[] arr = new float[SIZE];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1.0f;
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println(System.currentTimeMillis() - startTime);
    }

    public static void methodTwo() throws InterruptedException {
        float[] srcArray = new float[SIZE];
        float[] firstHalfArray = new float[HALF];
        float[] secondHalfArray = new float[HALF];
        for (int i = 0; i < srcArray.length; i++) {
            srcArray[i] = i + 8035205.122058f;
        }
        long startTime = System.currentTimeMillis();
        Thread thread1 = new Thread(() -> {
            System.arraycopy(srcArray, 0, firstHalfArray, 0, HALF);
            for (int i = 0; i < firstHalfArray.length; i++) {
                firstHalfArray[i] = (float)(srcArray[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        Thread thread2 = new Thread(() -> {
           System.arraycopy(srcArray, HALF, secondHalfArray, 0, secondHalfArray.length);
            for (int i = 0; i < secondHalfArray.length; i++) {
                secondHalfArray[i] = (float)(srcArray[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        float[] destArray = new float[firstHalfArray.length + secondHalfArray.length];
        System.arraycopy(firstHalfArray, 0, destArray, 0, firstHalfArray.length);
        System.arraycopy(secondHalfArray, 0, destArray, HALF, secondHalfArray.length);

        System.out.println(System.currentTimeMillis() - startTime);
    }
}