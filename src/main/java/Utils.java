import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Utils {

    public static void uploadFile(String targetUrl, File file, String id) {
        HttpURLConnection httpUrlConnection = null;
        String boundary = "===" + System.currentTimeMillis() + "===";
        String LINE_FEED = "\r\n";

        try {
            // Add the id parameter to the target URL
            if (!targetUrl.contains("?")) {
                targetUrl += "?";
            } else {
                targetUrl += "&";
            }
            targetUrl += "id=" + id;

            URL url = new URL(targetUrl);
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            OutputStream outputStream = httpUrlConnection.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true);

            // Add file part
            String fileName = file.getName();
            writer.append("--").append(boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(fileName).append("\"")
                    .append(LINE_FEED);
            writer.append("Content-Type: ").append(java.nio.file.Files.probeContentType(file.toPath()))
                    .append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();

            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();

            writer.append(LINE_FEED).flush();
            writer.append("--").append(boundary).append("--").append(LINE_FEED);
            writer.close();

            int responseCode = httpUrlConnection.getResponseCode();
            System.out.println("Response code: " + responseCode);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (httpUrlConnection != null) {
                httpUrlConnection.disconnect();
            }
        }
    }

}
