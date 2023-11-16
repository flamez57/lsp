import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

 /*
  * 图片下载
  * #编译
  * javac -encoding UTF-8 downloadPic.java
  *
  * #执行class文件
  * java downloadPic
  */
public class downloadPic {
    public static void main(String[] args) throws IOException {
        // downPic();
        download("https://img2.baidu.com/it/u=1814561676,2470063876&fm=253&fmt=auto&app=138&f=JPEG?w=750&h=500", "d:\\mnt\\", "sample.jpg");
    }

    /*
     * URL -->> Connect: 打开URL连接
     * Connect -->> InputStream: 获取输入流
     * InputStream -->> ImageIO: 将输入流转换为图片对象
     * ImageIO -->> Image: 读取图片数据
     * Image -->> File: 将图片保存到文件
     */
    public static void downPic()
    {
        // 源文件
        String imageUrl = "https://img2.baidu.com/it/u=1814561676,2470063876&fm=253&fmt=auto&app=138&f=JPEG?w=750&h=500";
        // 目标文件
        String destinationFile = "image.jpg";

        try {
            URL url = new URL(imageUrl);
            InputStream inputStream = url.openStream();
            OutputStream outputStream = new FileOutputStream(destinationFile);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();

            System.out.println("图片下载完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件下载到指定路径
     *
     * @param urlString 链接
     * @param savePath  保存路径
     * @param filename  文件名
     * @throws Exception
     */
    public static void download(String urlString, String savePath, String filename) throws IOException {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        //设置请求超时为20s
        con.setConnectTimeout(20 * 1000);
        //文件路径不存在 则创建
        File sf = new File(savePath);
        if (!sf.exists()) {
            sf.mkdirs();
        }
        //jdk 1.7 新特性自动关闭
        try (InputStream in = con.getInputStream();
             OutputStream out = new FileOutputStream(sf.getPath() + "\\" + filename)) {
            //创建缓冲区
            byte[] buff = new byte[1024];
            int n;
            // 开始读取
            while ((n = in.read(buff)) >= 0) {
                out.write(buff, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}