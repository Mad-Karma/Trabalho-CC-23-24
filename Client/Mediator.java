package Client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

                    // message from server
                    if (firstByte.equals("1")) {
                        String receivedData = new String(buffer, 2, bytesRead, StandardCharsets.UTF_8);
                        Thread.sleep(100);

                        System.out.println("Message from Server: " + receivedData);
                    }

                    // receive the file list from the server
                    else if (firstByte.equals("2")) {
                        String receivedData = new String(buffer, 2, bytesRead, StandardCharsets.UTF_8);

                        System.out.println(receivedData);

                        // Parse blocks to update the list of people with blocks
                        String[] blocks = receivedData.split("\\|");
                        Map<String, List<String>> clientsWithBlocks = new HashMap<>();

                        for (String block : blocks) {
                            String[] blockInfo = block.split("/");
                            if (blockInfo.length == 2) {
                                String blockNumber = blockInfo[0];
                                String ipAddress = blockInfo[1]; // Extracting IP address

                                // Store blocks associated with IP addresses
                                if (!clientsWithBlocks.containsKey(ipAddress)) {
                                    clientsWithBlocks.put(ipAddress, new ArrayList<>());
                                }
                                clientsWithBlocks.get(ipAddress).add(blockNumber);
                            }
                        }
                        // Now clientsWithBlocks contains the IP addresses and their associated blocks
                        System.out.println("Blocks Information Updated: " + clientsWithBlocks);

                        // Ask for node to send block
                    }

                    //
                    else if (firstByte.equals("3")) {

                    }

                } else {
                    System.out.println("Invalid header format.");
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
