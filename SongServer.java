import static spark.Spark.*;
import java.sql.*;

public class SongServer {
    public static void main(String[] args) {
        // Use Render-provided PORT
        port(Integer.parseInt(System.getenv("PORT")));

        get("/", (req, res) -> {
            StringBuilder html = new StringBuilder();
            html.append("<html><head><meta charset='UTF-8'><title>Song List</title></head><body>");
            html.append("<h1>🎵 Songs in Your Database</h1>");

            try {
                // Read DB credentials from Render environment variables
                String url = System.getenv("DB_URL");
                String user = System.getenv("DB_USER");
                String password = System.getenv("DB_PASS");

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
