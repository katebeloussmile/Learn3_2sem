class ThreadCacl extends Thread {

    long[][] InputArray;
    int startIndex;
    int endIndex;
    long rowSize;
    int ParallelRowSum = 0;

    public ThreadCacl(long[][] InputArray, int startIndex, int endIndex, long SIZE) { //конструктор класу, приймає дані для обчислень
        this.InputArray = InputArray;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.rowSize = SIZE;
    }

    public void getResult() {
        for (int i = startIndex; i < endIndex; i++) {
            for (int j = 0; j < rowSize; j++) {
                if (j != rowSize - 1) {
                    System.out.print(InputArray[i][j] + "\t");
                } else {
                    System.out.println(InputArray[i][j]);
                }
            }
        }
    }

    @Override
    public void run() { //обрахунки, що здійснюватимуться в зазначеному потоці
        for (int i = startIndex; i < endIndex; i++) {
            for (int j = 0; j < rowSize; j++) {
                if (j != rowSize - 1) {
                    ParallelRowSum += InputArray[i][j];
                } else {
                    ParallelRowSum += InputArray[i][j];
                    InputArray[i][i] = ParallelRowSum;
                    ParallelRowSum = 0;
                }
            }
        }
    }
}
