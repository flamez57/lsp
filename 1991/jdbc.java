import com.mysql.cj.jdbc.Driver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.Properties;

 /*
  * #编译
  * javac -encoding UTF-8 -cp E:\my_code\lsp\1991\work_lib\mysql-connector-java-8.0.11.jar jdbc.java
  *
  * #执行class文件需要加上“.;”代表从当前目录查找主类。否则会报“找不到或无法加载主类”的错误
  * java -cp .;E:\my_code\lsp\1991\work_lib\mysql-connector-java-8.0.11.jar jdbc
  */
// java获取连接的5种方式
public class jdbc {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        int connectIndex = 1;
        if (args.length > 0) {
            connectIndex = Integer.parseInt(args[0]);
        }
        Connection connection;
        switch (connectIndex) {
            case 1:
            connection = connect01();
            break;
            case 2:
            connection = connect02();
            break;
            case 3:
            connection = connect03();
            break;
            case 4:
            connection = connect04();
            break;
            case 5:
            connection = connect05();
            break;
            default:
            connection = connect01();
        }

        System.out.println("No."+connectIndex +":"+ connection);

        // 执行 SQL语句
        String sql1 = "CREATE TABLE news(id INT,content VARCHAR(32))";
        String sql2 = "INSERT INTO news VALUES (1,'居民健康'),(2,'商品健康'),(3,'大熊猫')";
        String sql3 = "UPDATE news SET content='湖北'WHERE id=1;";

        final Statement statement = connection.createStatement();
        final int row1 = statement.executeUpdate(sql1);//返回影响的行数
        final int row2 = statement.executeUpdate(sql2);//返回影响的行数
        final int row3 = statement.executeUpdate(sql3);
 
        // 验证是否执行成功
        if (row1 != 0) {
            System.out.println("建表执行成功");
        } else {
            System.out.println("建表执行失败");
        }
        if (row2 != 0) {
            System.out.println("插入数据执行成功");
        } else {
            System.out.println("插入数据执行失败");
        }
        if (row3 != 0) {
            System.out.println("修改数据执行成功");
        } else {
            System.out.println("修改数据执行失败");
        }
 
        // 关闭资源
        statement.close();
        connection.close();
    }

    // 方式一，直接导入第三方库驱动类
    public static Connection connect01() throws SQLException {
        // 获取驱动
        Driver driver = new Driver();
        // 获取连接
        String url = "jdbc:mysql://49.235.118.244:3307/flamez_test?serverTimezone=UTC&useSSL=false&useSer" +
                "verPrepStmts=true&characterEncoding=utf-8&useSSL=false";
        // 将用户名和密码放入到Properities对象中
        Properties properties = new Properties();
        properties.setProperty("user","root"); // 用户
        properties.setProperty("password","123456"); // 密码
        final Connection connection = driver.connect(url, properties);
        return connection;
    }

    // 方式二：使用反射加载Driver：动态加载，更加的灵活，减少依赖
    public static Connection connect02() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NoSuchMethodException, InvocationTargetException {
        // 获取Driver类的字节码文件对象
        final Class<?> clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        // 注意：在用字节码文件对象获取Driver对象时，直接newInstance被idea提示已经弃用
        final Constructor<?> Constructor = clazz.getDeclaredConstructor();
        final Driver driver = (Driver)Constructor.newInstance();
        String url = "jdbc:mysql://49.235.118.244:3307/flamez_test?serverTimezone=UTC&useSSL=false&useSer" +
                "verPrepStmts=true&characterEncoding=utf-8&useSSL=false";
        // 将用户名和密码放入到Properities对象中
        Properties properties = new Properties();
        properties.setProperty("user","root"); // 用户
        properties.setProperty("password","123456"); // 密码
        final Connection connect = driver.connect(url, properties);
        return connect;
    }

    // 方式三：使用DriverManager替换Driver
    public static Connection connect03() throws SQLException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
        // DriverManager类支持更好的获取连接的方法,可以直接将用户和密码作为参数，而不用存储到Properities
        final Class<?> clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        final Constructor<?> constructor = clazz.getDeclaredConstructor();
        final Driver driver =(Driver)constructor.newInstance();
        // 创建url和user和password
        String url = "jdbc:mysql://49.235.118.244:3307/flamez_test?serverTimezone=UTC&useSSL=false&useSer" +
                "verPrepStmts=true&characterEncoding=utf-8&useSSL=false";
        String user = "root";
        final String password = "123456";
        DriverManager.registerDriver(driver); // 注册Driver驱动
        final Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    // 方式四：加载Driver时自动完成注册（这种方式使用的最多，推荐使用）
    public static Connection connect04() throws ClassNotFoundException, SQLException {
        // 使用反射加载了Driver类
        // 在加载 Driver类时，完成注册
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://49.235.118.244:3307/flamez_test?serverTimezone=UTC&useSSL=false&useSer" +
                "verPrepStmts=true&characterEncoding=utf-8&useSSL=false";
        String user = "root";
        String password = "123456";
        final Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    // 方式五:进一步优化，将信息写入到配置文件
    public static Connection connect05() throws IOException, ClassNotFoundException, SQLException {
        // 通过Properties对象获取配置文件信息
        Properties properties = new Properties();
        File file=new File("mysql.properties");
        properties.load(new FileInputStream(file));// 此时已经将配置文件的信息读取到了Properties中
        //properties.load(new FileInputStream("src\\mysql.properties"));// 此时已经将配置文件的信息读取到了Properties中
        // 获取相关信息
        final String user = properties.getProperty("user"); // 用户
        final String password = properties.getProperty("password"); // 密码
        final String url = properties.getProperty("url"); // url
        final String driver = properties.getProperty("driver");
        Class.forName(driver); // 注册驱动
        final Connection connection = DriverManager.getConnection(url, user, password); // 获取连接
        return connection;
    }
}
 