import static spark.Spark.*;
import java.sql.*;
import java.util.Properties;
import java.io.FileInputStream;

public class SongServer {
    public static void main(String[] args) {
        port(4567); // Browser will access http://localhost:4567

        get("/", (req, res) -> {
            StringBuilder html = new StringBuilder();
            html.append("<html><head><meta charset='UTF-8'><title>Song List</title></head><body>");
            html.append("<h1>🎵 Songs in Your Database</h1>");

            try {
                Properties props = new Properties();
                props.load(new FileInputStream("dbconfig.properties"));
                String url = props.getProperty("db.url");
                String user = props.getProperty("db.username");
                String password = props.getProperty("db.password");

                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT title FROM table1");

                html.append("<ul>");
                while (rs.next()) {
                    html.append("<li>").append(rs.getString("title")).append("</li>");
                }
                html.append("</ul>");

                rs.close();
                stmt.close();
                conn.close();
            } catch (Exception e) {
                html.append("<p>Error: ").append(e.getMessage()).append("</p>");
            }

            html.append("</body></html>");
            res.type("text/html");
            return html.toString();
        });
    }
}
