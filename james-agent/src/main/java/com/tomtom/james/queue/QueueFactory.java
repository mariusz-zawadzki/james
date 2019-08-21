package com.tomtom.james.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class QueueFactory {

    public static <E> BlockingQueue<E> getJobQueue(int jobQueueSize, int asyncJobQueueFragments) {
        if(asyncJobQueueFragments <0){
            return new BlockingConcurrentQueue<>(jobQueueSize);
        }
        else if(asyncJobQueueFragments>1){
            return new SplittingQueue<>(asyncJobQueueFragments, jobQueueSize, true);
        }
        else
        {
            return new ArrayBlockingQueue<>(jobQueueSize, true);
        }
    }
}

