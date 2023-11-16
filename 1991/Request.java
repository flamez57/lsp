import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

 /*
  * 多线程图片下载
  * #编译
  * javac -encoding UTF-8 Request.java
  *
  * #执行class文件
  * java Request
  */
public class Request {
  
    public static void main(String[] args) {
        postRequest("");
    }

    public static InputStream postRequest(String urlStr) {
        // 1. 创建HTTP连接
        // 我们可以使用java.net包中的HttpURLConnection类来创建HTTP连接。通过调用openConnection()方法可以获取到一个HttpURLConnection对象。
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // 2. 设置请求方法
        // 将HTTP请求方法设置为POST，可以使用setRequestMethod()方法来实现。
        connection.setRequestMethod("POST");

        // 3. 设置请求头
        // 在发送POST请求时，需要设置请求头中的Content-Type和Content-Length。
        // Content-Type表示请求体的类型，通常为application/x-www-form-urlencoded或application/json。
        // Content-Length表示请求体的长度，可以通过getBytes()方法获取请求体的字节数，并将其作为Content-Length的值。
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        String requestBody = "param1=value1&param2=value2";
        connection.setRequestProperty("Content-Length", String.valueOf(requestBody.getBytes().length));

        // 4. 设置请求体
        // 将需要发送的数据写入请求体。请求体可以是一个字符串，也可以是一个字节数组。
        connection.setDoOutput(true);
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(requestBody);
        outputStream.flush();
        outputStream.close();

        // 5. 发送请求
        // 调用connect()方法将请求发送给服务器。
        connection.connect();

        // 6. 接收响应
        // 通过getResponseCode()方法可以获取到服务器返回的响应状态码。
        int responseCode = connection.getResponseCode();

        // 7. 处理响应
        // 根据响应状态码来判断请求是否成功，并根据需要进行后续处理。
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // 请求成功
            InputStream inputStream = connection.getInputStream();
            // 处理输入流中的数据
            return inputStream;
        } else {
            // 请求失败
            InputStream errorStream = connection.getErrorStream();
            // 处理错误流中的数据
            return errorStream;
        }
    }
}