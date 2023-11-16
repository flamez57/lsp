import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

 /*
  * 多线程图片下载
  * #编译
  * javac -encoding UTF-8 moreThreadDownloadPic.java
  *
  * #执行class文件
  * java moreThreadDownloadPic
  */
public class moreThreadDownloadPic extends Thread {
    private String url; //网络地址
    private String name;//保存文件名
 
    public moreThreadDownloadPic(String url, String name){
        this.url = url;
        this.name = name;
    }
 
    @Override
    public void run() {
        try {
            download(url, "d:\\mnt\\", name);
            System.out.println("下载的文件名为" + name);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
    }

    public static void main(String[] args) throws IOException {

        moreThreadDownloadPic testThread2 = new moreThreadDownloadPic("https://img-blog.csdnimg.cn/ecc554ff1bfb4feb9b1a4a4444c7cf8c.png", "2.png");
        moreThreadDownloadPic testThread3 = new moreThreadDownloadPic("https://img-blog.csdnimg.cn/142817d142c64243a01169f67eacc154.png", "3.png");
        moreThreadDownloadPic testThread4 = new moreThreadDownloadPic("https://img-blog.csdnimg.cn/997601f9eea0409fac770af5a2863c57.png", "4.png");
 
        testThread2.start();
        testThread3.start();
        testThread4.start();
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