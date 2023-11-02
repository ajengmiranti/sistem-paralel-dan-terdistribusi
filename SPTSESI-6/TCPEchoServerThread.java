import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class TCPEchoServerThread {
    private static ServerSocket servSock;
    private static final int PORT = 54321;
    private static List<clientHandler> clientList = new ArrayList<>(); // Daftar koneksi klien

    public TCPEchoServerThread() {
    }

    public void start() {
        try {
            servSock = new ServerSocket(PORT, 0, InetAddress.getByName("192.168.43.50"));

            while (true) {
                Socket clientSocket = servSock.accept();
                clientHandler newClient = new clientHandler(clientSocket);
                clientList.add(newClient); // Tambahkan koneksi klien ke daftar
                Thread clientThread = new Thread(newClient);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Menutup koneksi....");
                servSock.close();
            } catch (IOException e) {
                System.out.println("Tidak dapat memustukan koneksi");
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    // Method untuk broadcast pesan ke semua klien yang terhubung
    public static void broadcastMessage(String message) {
        for (clientHandler client : clientList) {
            client.sendMessage(message);
        }
    }

    public static void main(String[] args) {
        TCPEchoServerThread es = new TCPEchoServerThread();
        System.out.println("Server telah berjalan di komputer ini pada port " + PORT);
        es.start();
    }
}