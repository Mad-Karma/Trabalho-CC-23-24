import java.io.*;
import java.nio.charset.StandardCharsets;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;

public class Server {

    private static final int PORT = 9090;
    private static volatile boolean isRunning = true;
    private static Map<String, List<String>> clientFilesMap = new HashMap<>();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server listening on port " + PORT);

            // Start a thread to handle the menu
            Thread menuThread = new Thread(Server::startMenu);
            menuThread.start();

            while (isRunning) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("[DEBUG] Client connected: " + clientSocket);

                // Start a new thread to handle the client
                executorService.execute(new ClientHandler(clientSocket, clientFilesMap));
            }

            executorService.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startMenu() {
        Scanner scanner = new Scanner(System.in);

        while (isRunning) {
            try {
                System.out.println("\nMenu:");
                System.out.println("1. Display client files");
                System.out.println("2. Exit\n");

                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1:
                        displayClientFiles();
                        break;
                    case 2:
                        System.out.println("Exiting the server.");
                        isRunning = false;
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        scanner.close();
    }

    private static void displayClientFiles() {
        System.out.println("\n\n#################\n# Client Files: #\n#################\n");
        for (Map.Entry<String, List<String>> entry : clientFilesMap.entrySet()) {
            System.out.println("Client: " + entry.getKey());
            System.out.println("Files: " + entry.getValue());
            System.out.println("--------");
        }
    }

    public static class ClientHandler implements Runnable {
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
}
