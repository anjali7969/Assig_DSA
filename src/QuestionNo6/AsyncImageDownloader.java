package QuestionNo6;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncImageDownloader extends JFrame {

    private JTextField urlField;
    private JButton downloadButton;
    private JTextArea logArea;
    private JLabel imageLabel;
    private ExecutorService executorService;
    private boolean downloading;

    public AsyncImageDownloader() {
        setTitle("Image Downloader");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Header Panel
        JPanel headerPanel = new JPanel();
        JLabel headerLabel = new JLabel("Image Downloader");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(headerLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new BorderLayout());
        urlField = new JTextField();
        urlField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        ImageIcon downloadIcon = new ImageIcon("download.png");
        downloadButton = new JButton("Download", downloadIcon);
        downloadButton.setVerticalTextPosition(SwingConstants.CENTER);
        downloadButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startDownload();
            }
        });

        JPanel urlPanel = new JPanel(new BorderLayout());
        urlPanel.add(new JLabel("URL: "), BorderLayout.WEST);
        urlPanel.add(urlField, BorderLayout.CENTER);
        urlPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        inputPanel.add(urlPanel, BorderLayout.CENTER);
        inputPanel.add(downloadButton, BorderLayout.EAST);

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(imageLabel, BorderLayout.SOUTH);

        add(mainPanel);

        executorService = Executors.newFixedThreadPool(5);
    }

    private void startDownload() {
        String url = urlField.getText();
        if (url.isEmpty()) {
            logMessage("Please enter a URL.");
            return;
        }

        if (downloading) {
            logMessage("Already downloading. Please wait.");
            return;
        }

        downloading = true;
        logMessage("Downloading images from: " + url);

        executorService.execute(new ImageDownloadTask(url));
    }

    private void logMessage(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                logArea.append(message + "\n");
            }
        });
    }

    private class ImageDownloadTask implements Runnable {

        private String url;

        public ImageDownloadTask(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            try {
                URL imageUrl = new URL(url);
                InputStream inputStream = imageUrl.openStream();

                // Extract file name from URL
                String fileName = url.substring(url.lastIndexOf('/') + 1);
                // Replace invalid characters in file name
                fileName = fileName.replaceAll("[^a-zA-Z0-9.-]", "_"); // Replace invalid characters with underscores

                String desktopPath = System.getProperty("user.home") + "/Downloads/";
                String filePath = "C:\\Users\\Anjali\\Downloads\\" + fileName;


                FileOutputStream outputStream = new FileOutputStream(filePath);

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                inputStream.close();
                outputStream.close();

                logMessage("Image downloaded: " + fileName);

                // Display the downloaded image
                displayImage(filePath);
            } catch (IOException e) {
                logMessage("Error downloading image: " + e.getMessage());
            } finally {
                downloading = false;
                urlField.setText(""); // Clear the input box after download
            }
        }
    }

    private void displayImage(String filePath) {
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            if (image != null) {
                ImageIcon icon = new ImageIcon(image);
                imageLabel.setIcon(icon);
            } else {
                logMessage("Failed to load image: " + filePath);
            }
        } catch (IOException e) {
            logMessage("Error displaying image: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AsyncImageDownloader().setVisible(true);
            }
        });
    }
}
