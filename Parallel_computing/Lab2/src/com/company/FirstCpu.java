package com.company;

class FirstCpu implements Runnable{
    private CpuQueue q1;
    private CpuQueue q2;
    private AdditionThread additionThread;
    private SquareThread squareThread;
    private int gN;
    private int num_processed;
    public FirstCpu(CpuQueue q1, CpuQueue q2, AdditionThread at, SquareThread st, int gN){
        this.q1 = q1;
        this.q2 = q2;
        this.additionThread = at;
        this.squareThread = st;
        this.gN = gN;
    }

    public void run(){
        long processingTime;
        for(int i = 0; i < gN; i++) {
            int randMin=20;
            int randMax=80;
            int x, y;
            processingTime = randMin + (int) (Math.random() * randMax);
            try {
                if(q1.GetSize() != 0){
                    x = q1.get();
                    this.num_processed ++;
                }
                if(q2.GetSize() != 0){
                    y = q2.get();
                    this.num_processed ++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(processingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(String.format("first cpu is done, " +
                "total threads: %d, " +
                "percentage out of total: %.2f", this.num_processed,
                (float)this.num_processed * 100 / (this.gN * 2)));
    }
}