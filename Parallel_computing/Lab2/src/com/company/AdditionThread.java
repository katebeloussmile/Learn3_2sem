package com.company;

class AdditionThread implements Runnable{
    private CpuQueue q;
    private int a;
    private int b;
    private int generateNumber;
    public boolean isBusy = false;
    public AdditionThread(CpuQueue q, int a, int b, int gN){
        this.generateNumber = gN;
        this.q = q;
        this.a = a;
        this.b = b;
    }

    @Override public void run(){
        long generateDelay;
        for (int i = 0; i < generateNumber; i++) {
            int randMin = 10;
            int randMax = 40;
            generateDelay = randMin + (int) (Math.random() * randMax);
            try {
                Thread.sleep(generateDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                q.put(a + b);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Addition thread is done");
    }
}