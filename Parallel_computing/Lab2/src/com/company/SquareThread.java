package com.company;

class SquareThread implements Runnable{
    private CpuQueue q;
    private int a;
    private int generateNumber;
    public SquareThread(CpuQueue q, int a, int gN){
        this.q = q;
        this.a = a;
        this.generateNumber = gN;
    }

    @Override public void run(){
        long generateDelay;
        for (int i = 0; i < generateNumber; i++) {
            int randMin=10;
            int randMax=40;
            generateDelay = randMin + (int) (Math.random() * randMax);
            try {
                Thread.sleep(generateDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
               q.put(a * a);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}