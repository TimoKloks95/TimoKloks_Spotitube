//package nl.han.oose.timokloks.util;
//
//
//import java.io.IOException;
//import java.io.Reader;
//import java.nio.file.Files;
//import java.util.Properties;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//
//public class PropertyReader {
//
//    static Properties properties = new Properties();
//    static Path PropertyFile = Paths.get("spotitube.properties");
//    static String connectionUrl, dbUser, dbPass, mysqlDriver;
//
//
//    public static void makeConnection() {
//        try {
//            Reader PropReader = Files.newBufferedReader(PropertyFile);
//            properties.load(PropReader);
//
//            connectionUrl = properties.getProperty("CONNECTION_URL");
//            dbUser = properties.getProperty("DB_USER");
//            dbPass = properties.getProperty("DB_PASS");
//            mysqlDriver = properties.getProperty("MYSQL_JDBC_DRIVER");
//        }
//            catch(IOException e) {
//                    System.out.println("Error.");
//        }
//        }
//
//        public static String getConnectionUrl() {
//            return connectionUrl;
//        }
//
//        public static String getDbUser() {
//        return dbUser;
//        }
//
//        public static String getDbPass() {
//        return dbPass;
//        }
//
//        public static String getMysqlDriver() {
//        return mysqlDriver;
//        }
//}
