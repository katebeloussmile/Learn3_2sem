public class ThreadSample {
    public static int SIZE = 10000;
    public static int NUMBER_THREADS = 40;

    //Output for demonstration
    public static void test_print(long[][] Array, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (j != SIZE - 1) {
                    System.out.print(Array[i][j] + "\t");
                } else {
                    System.out.println(Array[i][j]);
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long StartTime;
        long[][] InputArray = new long[SIZE][SIZE];
        int max_value = 5, min_value = 1;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                InputArray[i][j] = min_value + (int) (Math.random() * max_value);
        int SerialRowSumm = 0;
        StartTime = System.currentTimeMillis();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (j != SIZE - 1) {
                    SerialRowSumm += InputArray[i][j];
                } else {
                    SerialRowSumm += InputArray[i][j];
                    InputArray[i][i] = SerialRowSumm;
                    SerialRowSumm = 0;
                }
            }
        }
        System.out.print("Time in miliseconds for serial result: " + (System.currentTimeMillis() - StartTime) + "\n");
        System.out.print("-------------------------\n");
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                InputArray[i][j] = min_value + (int) (Math.random() * max_value);
        System.out.println();
        StartTime = System.currentTimeMillis();
        ThreadCacl ThreadArray[] = new ThreadCacl[NUMBER_THREADS];
        for (int i = 0; i < NUMBER_THREADS; i++) {
            ThreadArray[i] = new ThreadCacl(InputArray,
                    SIZE / NUMBER_THREADS * i,
                    i == (NUMBER_THREADS - 1) ? SIZE : SIZE / NUMBER_THREADS * (i + 1), SIZE); //тернарна умовна операція
            ThreadArray[i].start();
        }
        for (int i = 0; i < NUMBER_THREADS; i++) {
            ThreadArray[i].join();
        }
        System.out.print("Time in miliseconds for parallel result: " + (System.currentTimeMillis() - StartTime) + "\n");
        if (SIZE < 20) {
            for (int i = 0; i < NUMBER_THREADS; i++) {
                ThreadArray[i].getResult();
            }
        }
    }
}