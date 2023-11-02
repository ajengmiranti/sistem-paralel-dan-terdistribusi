import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

class clientHandler implements Runnable {
    private static int numConnections;
    private int connectionId;
    private Socket link;
    private PrintWriter out;
    private String clientName; // Tambahkan variabel nama klien

    public clientHandler(Socket s) {
        connectionId = numConnections++;
        System.out.println("Melayani koneksi ke-" + connectionId);
        link = s;
        try {
            out = new PrintWriter(link.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void run() {
        BufferedReader in = null;

        try {
            in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            // Menerima nama dari klien
            clientName = in.readLine();
            System.out.println("Klien #" + connectionId + " diberi nama: " + clientName);

            String message = in.readLine();
            while (!message.equals("close")) {
                System.out.println("Pesan diterima dari " + clientName + ": " + message);
                TCPEchoServerThread.broadcastMessage(clientName + ": " + message); // Broadcast pesan ke semua klien
                message = in.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                link.close();
                System.out.println("Menutup koneksi, #" + connectionId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}