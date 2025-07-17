get("/", (req, res) -> {
    StringBuilder html = new StringBuilder();
    html.append("<html><head><meta charset='UTF-8'><title>Song List</title></head><body>");
    html.append("<h1>🎵 Songs in Your Database</h1>");

    try {
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASS");

        Connection conn = DriverManager.getConnection(url, user, password);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT title FROM table1");

        html.append("<ul>");
        while (rs.next()) {
            String title = rs.getString("title");
            if (title != null && !title.trim().isEmpty()) {
                html.append("<li>").append(title).append("</li>");
            }
        }
        html.append("</ul>");

        rs.close();
        stmt.close();
        conn.close();
    } catch (Exception e) {
        html.append("<p>Error loading songs: ").append(e.getMessage()).append("</p>");
    }

    html.append("</body></html>");
    res.type("text/html");
    return html.toString();
});
