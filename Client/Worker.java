package Client;


public class Worker implements Runnable {

    public Worker(byte[] buffer) {
        // data that thread receives to process
    }
    // Open socket to another Node

    @Override
    public void run() {
        System.out.println("Worker activated");
        boolean work = true;
        while (work) {

        // when work is done change work to false

        }
    }
}
