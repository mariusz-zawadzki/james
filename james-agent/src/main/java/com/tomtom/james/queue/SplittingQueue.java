package com.tomtom.james.queue;

import com.google.common.util.concurrent.ForwardingBlockingQueue;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SplittingQueue<E> extends ForwardingBlockingQueue<E> {

    ArrayBlockingQueue<E>[] queues;
    private int fragments;

    public SplittingQueue(int fragments, int singleSize, boolean fair) {
        queues = new ArrayBlockingQueue[fragments];
        this.fragments = fragments;
        for (int i = 0; i < fragments; i++) {
            queues[i] = new ArrayBlockingQueue<E>(singleSize, fair);
        }
    }

    int currentCounter = 0;
    private ArrayBlockingQueue<E> getQueue(){
        return queues[nextQueue()];

    }

    private int nextQueue() {
        return currentCounter++%fragments;
    }

    @Override
    protected BlockingQueue<E> delegate() {
        return getQueue();
    }

    @Override
    public boolean isEmpty() {
        return Arrays.stream(queues).allMatch(Queue::isEmpty);
    }

    @Override
    public int size() {
        return Arrays.stream(queues).mapToInt(Queue::size).sum();
    }

}
