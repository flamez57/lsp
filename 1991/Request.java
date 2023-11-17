import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

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
        // https://news.qq.com/newsweather/showWeather.js
        try {
            InputStream inputStream = postRequest("https://api.shunliandongli.com/v2/front/goods/detail?goods_id=535005");// http://www.weather.com.cn/data/sk/101010100.html
            printInputStream(inputStream);
            //printInputStream2(inputStream);
            //printInputStream3(inputStream);
            //printInputStream4(inputStream);
        /*
         * sd 湿度
sm 未知
temp 温度
tempF 温度，华氏
time 时间
wd 风向
ws 风速 */
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        // } catch (ProtocolException e) {
        //     System.out.println(e.getMessage());
        //     e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
       
        
    }

    public static InputStream postRequest(String urlStr) throws MalformedURLException, IOException, ProtocolException {
        // 1. 创建HTTP连接
        // 我们可以使用java.net包中的HttpURLConnection类来创建HTTP连接。通过调用openConnection()方法可以获取到一个HttpURLConnection对象。
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // 2. 设置请求方法
        // 将HTTP请求方法设置为POST，可以使用setRequestMethod()方法来实现。
        connection.setRequestMethod("GET");

        // 3. 设置请求头
        // 在发送POST请求时，需要设置请求头中的Content-Type和Content-Length。
        // Content-Type表示请求体的类型，通常为application/x-www-form-urlencoded或application/json。
        // Content-Length表示请求体的长度，可以通过getBytes()方法获取请求体的字节数，并将其作为Content-Length的值。
        connection.setRequestProperty("Content-Type", "application/json");
        String requestBody = "goods_id=535005"; //param1=value1&param2=value2
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

    /*
     * 获取输入流 InputStream inputStream
     */
    public static void printInputStream(InputStream inputStream) throws IOException {
        // 使用BufferedReader逐行读取并打印
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(new String(line.getBytes("GBK"), "UTF-8"));
        }
        reader.close();
        System.out.println(response);
    }

    public static void printInputStream2(InputStream inputStream) throws IOException {
        // 2.使用Scanner逐行读取并打印
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(new String(line.getBytes("GBK"), "UTF-8"));
        }
    }

    public static void printInputStream3(InputStream inputStream) throws IOException {
        // 3.使用ByteArrayOutputStream将InputStrean转换为字符串并打印:
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
            System.out.println(result.toString("UTF-8"));// 将结果转换为字符串并打印
        }
    }

    public static void printInputStream4(InputStream inputStream) throws IOException {
        // 使用Files工具类将InputStream写入文件并打印
        Path outputPath = Paths.get("output.txt"); // 设置输出文件路径
        Files.copy(inputStream, outputPath, StandardCopyOption.REPLACE_EXISTING);
        System.out.println(Files.readAllLines(outputPath));// 读取输出文件并打印
    }

}