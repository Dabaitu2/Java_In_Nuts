package MemoryManageAndGC;

public class MultiThread {
    public static void main(String[] args) {
        Thread t = new Thread(() -> System.out.println("Hello Thread"));
        t.start();
    }
}
