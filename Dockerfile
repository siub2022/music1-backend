# Use OpenJDK 17 for compilation and runtime
FROM openjdk:17

# Set working directory inside container
WORKDIR /app

# Copy all files into the container
COPY . .

# Compile Java source with required JARs in classpath
RUN javac -cp "spark-core-2.9.4.jar:postgresql-42.7.3.jar:slf4j-api-1.7.36.jar:javax.servlet-api-3.1.0.jar:jetty-server-9.4.48.v20220622.jar:jetty-util-9.4.48.v20220622.jar:jetty-http-9.4.48.v20220622.jar:jetty-io-9.4.48.v20220622.jar:." SongServer.java

# Run the app using correct classpath and entrypoint
CMD ["java", "-cp", "spark-core-2.9.4.jar:postgresql-42.7.3.jar:slf4j-api-1.7.36.jar:javax.servlet-api-3.1.0.jar:jetty-server-9.4.48.v20220622.jar:jetty-util-9.4.48.v20220622.jar:jetty-http-9.4.48.v20220622.jar:jetty-io-9.4.48.v20220622.jar:.", "SongServer"]
