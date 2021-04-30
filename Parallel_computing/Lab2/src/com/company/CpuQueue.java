package com.company;
import java.util.LinkedList;
import java.util.Queue;

public class CpuQueue {

    private Queue<Integer> queue = new LinkedList<>();
    private int capacity;
    private int maxSize = 0;

    public CpuQueue(int capacity) {
        this.capacity = capacity;
        this.maxSize = this.capacity;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int GetSize(){
        return queue.size();
    }

    public synchronized void put(int element) throws InterruptedException {
        while(queue.size() == capacity) {
            System.out.println("Queue is FULL, waiting..");
            wait();
        }
        queue.add(element);

        if(queue.size()>maxSize)
            maxSize=queue.size();
        notify();
    }

    public synchronized Integer get() throws InterruptedException {
        while(queue.isEmpty()) {
           System.out.println("Queue is EMPTY, waiting..");
            wait();
        }
        Integer item = queue.remove();
        notify();
        return item;
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }
}