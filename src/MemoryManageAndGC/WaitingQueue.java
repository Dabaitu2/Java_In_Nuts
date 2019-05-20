package MemoryManageAndGC;

import java.util.LinkedList;

/**
 * <h2>这个类用于实现一个多线程安全存取的队列</h2>
 *
 * @author tomokokawase
 * @version 1.0
 * @param <E>
 */
public class WaitingQueue<E> {
    /**
     * 保存类中的队列
     */
    private LinkedList<E> q = new LinkedList<>();

    /**
     * 入队列
     *
     * @see #pop
     * @param o 推入的元素
     */
    public synchronized void push(E o) {
        q.add(o);
        this.notifyAll();
    }

    /**
     * 出队列
     *
     * @param thread 标志当前队列的字符串序列
     * @return <E>
     */
    public synchronized E pop(String thread) {
        while (q.size() == 0) {
            System.out.printf("%s: 没有数据, 线程等待中%n", thread);
            try {
                this.wait();
            } catch (InterruptedException ignore) {
            }
        }
        System.out.printf("%s: 获取到数据", thread);
        return q.remove();
    }

    public static void main(String[] args) {
        WaitingQueue<Integer> waitingQueue = new WaitingQueue<>();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                waitingQueue.push(i);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(waitingQueue.pop("thread2"));
            }
        });
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(waitingQueue.pop("thread3"));
            }
        });
        t1.start();
        t2.start();
        t3.start();
    }
}
