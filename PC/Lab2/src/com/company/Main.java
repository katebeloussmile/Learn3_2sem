package com.company;

class Main{
    static final int num_runs = 17;
    static final int a = 5;
    static final int capacity = 4;
    static final int b = 5;
    public static void main(String []args){
        CpuQueue q1 = new CpuQueue(capacity);
        CpuQueue q2 = new CpuQueue(capacity);
        SquareThread st = new SquareThread(q2,a,num_runs);
        AdditionThread at = new AdditionThread(q1, a,b,num_runs);
        FirstCpu fCpu = new FirstCpu(q1,q2,at,st, num_runs);
        SecondCpu sCpu = new SecondCpu(q1,q2, num_runs);
        new Thread(st).start();
        new Thread(at).start();
        try{
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(fCpu).start();
        new Thread(sCpu).start();

    }
}