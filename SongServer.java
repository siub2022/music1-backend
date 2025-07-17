import static spark.Spark.*;
import java.sql.*;

public class SongServer {
    public static void main(String[] args) {
        // Use Render-provided PORT
        port(Integer.parseInt(System.getenv("PORT")));

        get("/", (req, res) -> {
            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Song List</title></head><body>");
            html.append("<h1>🎵 Songs in Your Database</h1>");

            html.append("<ul>");
            try (
                Connection conn = DriverManager.getConnection(
                    System.getenv("DB_URL"),
                    System.getenv("DB_USER"),
                    System.getenv("DB_PASS")
                );
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT title FROM table1")
            ) {
                while (rs.next()) {
                    String title = rs.getString("title");
                    if (title != null && !title.trim().isEmpty()) {
                        html.append("<li>").append(title).append("</li>");
                    }
                }
            } catch (Exception e) {
                html.append("</ul>"); // Close list even if error
                html.append("<p style='color:red;'>Error loading songs: ").append(e.getMessage()).append("</p>");
            }

            html.append("</body></html>");
            res.type("text/html");
            return html.toString();
        });
    }
}
