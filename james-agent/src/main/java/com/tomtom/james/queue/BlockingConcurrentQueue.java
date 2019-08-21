package com.tomtom.james.queue;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

public class BlockingConcurrentQueue<E> extends ConcurrentLinkedQueue<E> implements BlockingQueue<E> {


    final int capacity;
    int estimatedSize = 0;


    public BlockingConcurrentQueue(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void put(E e) throws InterruptedException {
        if(capacity > estimatedSize){
            estimatedSize++;
            offer(e);
        }
    }

    @Override
    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        if(capacity > estimatedSize){
            estimatedSize++;
            return offer(e);
        }
        return false;
    }

    @Override
    public E take() throws InterruptedException {
            estimatedSize--;
            return this.poll();
    }

    @Override
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        estimatedSize--;
        return poll();
    }

    @Override
    public int remainingCapacity() {
        if(estimatedSize<0){
            estimatedSize = 0;
        }
        return capacity-estimatedSize;
    }

    @Override
    public int drainTo(Collection<? super E> c) {
        int count = 0;
        while(!isEmpty()){
            E poll = this.poll();
            if(poll != null){
                c.add(poll);
                count++;
            }
        }
        return count;
    }

    @Override
    public int drainTo(Collection<? super E> c, int maxElements) {
        int count = 0;
        while(!isEmpty() && count <= maxElements){
            E poll = this.poll();
            if(poll != null){
                c.add(poll);
                count++;
            }
        }
        return count;
    }
}
