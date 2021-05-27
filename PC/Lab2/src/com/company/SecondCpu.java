package com.company;

class SecondCpu implements Runnable{
    CpuQueue q1;
    CpuQueue q2;
    int gN;
    private int num_processed = 0;
    SecondCpu(CpuQueue q1, CpuQueue q2, int gN){
        this.q1 = q1;
        this.q2 = q2;
        this.gN = gN;
    }

    public void run(){
        long processingTime;
        for(int i = 0; i < gN; i++) {
            int randMin=20;
            int randMax=80;
            processingTime = randMin + (int) (Math.random() * randMax);
            try {
                if(q2.getMaxSize() == q2.GetSize()){
                    q2.get();
                    this.num_processed ++;
                }
                if(q1.getMaxSize() == q1.GetSize()){
                    q1.get();
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
        System.out.println(String.format("second cpu is done, " +
                        "total threads: %d, " +
                        "percentage out of total: %.2f", this.num_processed,
                (float)this.num_processed * 100 / (this.gN * 2)));
    }
}