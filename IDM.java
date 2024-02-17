import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class DownloadManager {
    private static final int BUFFER_SIZE = 4096;
    private static final String DOWNLOADS_FOLDER = "D:\\alok\\";

    private List<DownloadTask> downloadQueue;

    public DownloadManager() {
        downloadQueue = new ArrayList<>(); 
    }

    public void addDownload(String url, String fileName) {
        DownloadTask task = new DownloadTask(url, fileName);
        downloadQueue.add(task);
    }

    public void startDownloads() {
        for (DownloadTask task : downloadQueue) {
            new Thread(task).start();
        }
    }

    private static class DownloadTask implements Runnable {
        private String url;
        private String fileName;

        public DownloadTask(String url, String fileName) {
            this.url = url;
            this.fileName = fileName;
        }

        @Override
        public void run() {
            try {
                URL fileUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) fileUrl.openConnection();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    int fileSize = connection.getContentLength();

                    try (InputStream inputStream = connection.getInputStream();
                         FileOutputStream outputStream = new FileOutputStream(DOWNLOADS_FOLDER + fileName)) {

                        byte[] buffer = new byte[BUFFER_SIZE];
                        int bytesRead;
                        int totalBytesRead = 0;

                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                            totalBytesRead += bytesRead;
                            System.out.print("\rDownloading " + fileName + ": " +
                                    totalBytesRead + " / " + fileSize + " bytes");
                        }

                        System.out.println("\nDownload complete: " + fileName);

                    } catch (IOException e) {
                        System.err.println("Error while downloading " + fileName);
                        e.printStackTrace();
                    }

                } else {
                    System.err.println("Failed to connect to the server. Response code: " + responseCode);
                }

            } catch (IOException e) {
                System.err.println("Error while connecting to the server");
                e.printStackTrace();
            }
            System.out.println("File saved at: " + new File(DOWNLOADS_FOLDER + fileName).getAbsolutePath());
        }
    }

    public static void main(String[] args) {
        DownloadManager downloadManager = new DownloadManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Download");
            System.out.println("2. Start Downloads");
            System.out.println("3. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter URL: ");
                    String url = scanner.next();
                    System.out.print("Enter file name: ");
                    String fileName = scanner.next();
                    downloadManager.addDownload(url, fileName);
                    break;
                case 2:
                    downloadManager.startDownloads();
                    break;
                case 3:
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
