import java.io.*;
import java.net.*;
import java.util.Scanner;

public class chatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("192.168.43.50", 54321);
            System.out.println("Terhubung ke server.");

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Masukkan nama Anda: ");
            String clientName = scanner.nextLine();
            output.println(clientName); // Mengirim nama klien ke s erver

            String message; 
            Thread receiveThread = new Thread(() -> {
                try {
                    while (true) {
                        String serverResponse = input.readLine();
                        System.out.println(serverResponse);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiveThread.start();

            while (true) {
                System.out.print("Pesan: ");
                message = scanner.nextLine();
                output.println(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}