/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SERVER;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
/**
 *
 * @author User
 */
public class Server {
    
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private JTextArea messageArea;
    
    
    public Server() {
        // Membuat antarmuka pengguna server
        JFrame frame = new JFrame("Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Panel utama
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Area pesan
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel status server
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel statusLabel = new JLabel("Status: Menunggu koneksi...");
        statusPanel.add(statusLabel);
        mainPanel.add(statusPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);

        try {
            // Membuat server socket
            serverSocket = new ServerSocket(9999);
            statusLabel.setText("Status: Menunggu koneksi...");

            // Menerima koneksi dari klien
            clientSocket = serverSocket.accept();
            statusLabel.setText("Status: Koneksi dari klien diterima.");

            // Membuat input dan output stream
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Menerima dan membalas pesan dari klien
            String message;
            while ((message = in.readLine()) != null) {
                showMessage("Pesan dari klien: " + message);

                // Membalas pesan ke klien
                String response = JOptionPane.showInputDialog("Masukkan pesan untuk klien:");
                out.println(response);

                // Menampilkan pesan yang dikirim di antarmuka pengguna
                showMessage("Pesan terkirim ke klien: " + response);
            }

            // Menutup koneksi dengan klien
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showMessage(final String text) {
        // Menampilkan pesan di antarmuka pengguna
        SwingUtilities.invokeLater(() -> {
            messageArea.append(text + "\n");
        });
    }

    public static void main(String[] args) {
        new Server();
    }
}
    