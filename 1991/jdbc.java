
 
 import com.mysql.cj.jdbc.Driver;
 
 import java.lang.reflect.Constructor;
 import java.lang.reflect.InvocationTargetException;
 import java.sql.Connection;
 import java.sql.SQLException;
 import java.util.Properties;
 
 /*
  * #编译
javac -encoding UTF-8 -cp E:\my_code\lsp\1991\work_lib\mysql-connector-java-8.0.11.jar jdbc.java
#执行class文件需要加上“.;”代表从当前目录查找主类。否则会报“找不到或无法加载主类”的错误
java -cp .;E:\my_code\lsp\1991\work_lib\mysql-connector-java-8.0.11.jar jdbc
  */
//java获取连接的5种方式
public class jdbc {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        connect01();
        connect02();
 
    }
    //方式一，直接导入第三方库驱动类
    public static void connect01() throws SQLException {
 
        //获取驱动
        Driver driver = new Driver();
        //获取连接
        String url = "jdbc:mysql://49.235.118.244:3307/flamez_test?serverTimezone=UTC&useSSL=false&useSer" +
                "verPrepStmts=true&characterEncoding=utf-8&useSSL=false";
        //将用户名和密码放入到Properities对象中
        Properties properties = new Properties();
        properties.setProperty("user","root");//用户
        properties.setProperty("password","123456");//密码
        final Connection connect = driver.connect(url, properties);
        System.out.println(connect);
    }
    //方式二：使用反射加载Driver：动态加载，更加的灵活，减少依赖
    public static void connect02() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NoSuchMethodException, InvocationTargetException {
        //获取Driver类的字节码文件对象
        final Class<?> clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        //注意：在用字节码文件对象获取Driver对象时，直接newInstance被idea提示已经弃用
        final Constructor<?> Constructor = clazz.getDeclaredConstructor();
        final Driver driver = (Driver)Constructor.newInstance();
        String url = "jdbc:mysql://49.235.118.244:3307/flamez_test?serverTimezone=UTC&useSSL=false&useSer" +
                "verPrepStmts=true&characterEncoding=utf-8&useSSL=false";
        //将用户名和密码放入到Properities对象中
        Properties properties = new Properties();
        properties.setProperty("user","root");//用户
        properties.setProperty("password","123456");//密码
        final Connection connect = driver.connect(url, properties);
        System.out.println(connect);
 
    }

    //方式三：使用DriverManager替换Driver
    /*public static void connect03() throws SQLException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
        //DriverManager类支持更好的获取连接的方法,可以直接将用户和密码作为参数，而不用存储到Properities
        final Class<?> clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        final Constructor<?> constructor = clazz.getDeclaredConstructor();
        final Driver driver =(Driver)constructor.newInstance();
        //创建url和user和password
        String url = "jdbc:mysql://49.235.118.244:3307/flamez_test?serverTimezone=UTC&useSSL=false&useSer" +
                "verPrepStmts=true&characterEncoding=utf-8&useSSL=false";
        String user = "root";
        final String password = "123456";
      DriverManager.registerDriver(driver);//注册Driver驱动
        final Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
 
    }

    //方式四：加载Driver时自动完成注册（这种方式使用的最多，推荐使用）
    public static void connect04() throws ClassNotFoundException, SQLException {
        //使用反射加载了Driver类
        //在加载 Driver类时，完成注册
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://49.235.118.244:3307/flamez_test?serverTimezone=UTC&useSSL=false&useSer" +
                "verPrepStmts=true&characterEncoding=utf-8&useSSL=false";
        String user = "root";
        String password="123456";
        final Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
 
    }*/
}
 