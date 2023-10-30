import java.io.*;
import java.nio.charset.StandardCharsets;
import java.net.Socket;
import java.util.*;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Map<String, List<String>> clientFilesMap;

    public ClientHandler(Socket clientSocket, Map<String, List<String>> clientFilesMap) {
        this.clientSocket = clientSocket;
        this.clientFilesMap = clientFilesMap;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                String receivedString = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);
                String[] parts = receivedString.split(";");

                if (parts.length < 3) {
                    System.err.println("Invalid header format.");
                    continue;
                }

                String requestType = parts[0];
                String ip = parts[1];
                String requestInfo = parts[2];

                if (requestType.equals("1")) {
                    List<String> files = new ArrayList<>();
                    String[] fileswithsize = requestInfo.split(":");
                    for (String file : fileswithsize) {
                        String[] filesName = file.split("!");
                        files.add(filesName[0]);
                    }
                    clientFilesMap.put(ip, files);
                    System.out.println("Received files for IP " + ip + ": " + files);
                }
                if (requestType.equals("2")) {
                    String requestInfoToFind = requestInfo; // Request info to find

                    for (Map.Entry<String, List<String>> entry : clientFilesMap.entrySet()) {
                        String clientIP = entry.getKey();
                        List<String> clientFiles = entry.getValue();

                        // Check if the client has the requested file
                        if (clientFiles.contains(requestInfoToFind)) {
                            String message = "\n Client IP: " + clientIP + " has the requested file: " + requestInfoToFind + "\n";
                            byte[] bytesToSend = message.getBytes(StandardCharsets.UTF_8);
                            outputStream.write(bytesToSend);
                            outputStream.flush();
                        } else {
                            String message = "\n There's no file with the name. \n";
                            byte[] bytesToSend = message.getBytes(StandardCharsets.UTF_8);
                            outputStream.write(bytesToSend);
                            outputStream.flush();
                        }
                    }
                }
                if (requestType.equals("3")) {

                }
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
