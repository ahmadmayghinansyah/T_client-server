/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
/**
 *
 * @author Ahmad Mayghinansyah
 */
public class Client {
    
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private JTextArea messageArea;
    
    public Client() {
        // Membuat antarmuka pengguna klien
        JFrame frame = new JFrame("Client");
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

        // Panel input pesan
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JTextField textField = new JTextField(25);
        textField.addActionListener(e -> {
            sendMessage(textField.getText());
            textField.setText("");
        });
        inputPanel.add(textField);
        JButton sendButton = new JButton("Kirim");
        sendButton.addActionListener(e -> {
            sendMessage(textField.getText());
            textField.setText("");
        });
        inputPanel.add(sendButton);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);

        try {
            // Menghubungkan dengan server
            socket = new Socket("localhost", 9999);

            // Membuat input dan output stream
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Menerima dan menampilkan pesan dari server
            String message;
            while ((message = in.readLine()) != null) {
                showMessage("Pesan dari server: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        // Mengirim pesan ke server
        out.println(message);
        // Menampilkan pesan yang dikirim di antarmuka pengguna
        showMessage("Pesan terkirim: " + message);
    }

    private void showMessage(final String text) {
        // Menampilkan pesan di antarmuka pengguna
        SwingUtilities.invokeLater(() -> {
            messageArea.append(text + "\n");
        });
    }

    public static void main(String[] args) {
        new Client();
    }
}
    