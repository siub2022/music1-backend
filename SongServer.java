import static spark.Spark.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SongServer {
    public static void main(String[] args) {
        port(4567);

        get("/", (req, res) -> {
            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html>");
            html.append("<html lang=\"en\">");
            html.append("<head>");
            html.append("<meta charset=\"UTF-8\">");
            html.append("<title>Song List</title>");
            html.append("</head>");
            html.append("<body>");
            html.append("<h1>List of Songs in db1</h1>");
            html.append("<ul>");

            try {
                Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/db1", "postgres", "1234"
                );
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT title FROM songs");

                while (rs.next()) {
                    String title = rs.getString("title");
                    html.append("<li>").append(title).append("</li>");
                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (Exception e) {
                html.append("</ul>");
                html.append("<p><strong>Error loading songs:</strong> ").append(e.getMessage()).append("</p>");
                html.append("</body></html>");
                res.status(500);
                return html.toString();
            }

            html.append("</ul>");
            html.append("</body>");
            html.append("</html>");

            res.type("text/html");
            return html.toString();
        });
    }
}
