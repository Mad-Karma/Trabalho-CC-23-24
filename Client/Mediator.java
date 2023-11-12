package Client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Mediator implements Runnable {
    private InputStream clientInput;

    public Mediator(InputStream clientInputStream) {
        this.clientInput = clientInputStream;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(100);
            System.out.println("Mediator is listening...");

            while (true) {
                byte[] buffer = new byte[1024];
                int bytesRead = clientInput.read(buffer);

                if (bytesRead != -1) {
                    String firstByte = new String(buffer, 0, 1, StandardCharsets.UTF_8);

                    // Debug
                    System.out.println("First Byte: " + firstByte);

                    //message from server
                    if (firstByte.equals("1")) {
                        String receivedData = new String(buffer, 2, bytesRead, StandardCharsets.UTF_8);
                        Thread.sleep(100);

                        System.out.println("Message from Server: " + receivedData);
                        } 
                        
                        //request for file
                        else if (firstByte.equals("2")) {
                        //creates worker thread
                        }

                    } 
                    else {
                        System.out.println("Invalid header format.");
                    }
                }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
