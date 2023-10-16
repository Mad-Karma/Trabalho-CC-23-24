import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final String SERVER_ADDRESS = "127.0.0.1"; // Server IP address
    private static final int SERVER_PORT = 9090; // Server port

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             OutputStream outputStream = socket.getOutputStream()) {

            System.out.println("Connected to server.");

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Enter a string to send (q to quit):");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("q")) {
                    break;
                }

                byte[] bytesToSend = input.getBytes(StandardCharsets.UTF_8);
                outputStream.write(bytesToSend);
                outputStream.flush();
            }

            System.out.println("Closing connection.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
