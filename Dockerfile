# Force rebuild: July 17, 2025

# This Dockerfile uses explicit COPY to prevent .class leakage
# Use OpenJDK 17 as base image
FROM openjdk:17

# Set working directory inside the container
WORKDIR /app

# Copy only source and required JARs
COPY SongServer.java .
COPY *.jar .

# Compile the Java file using explicit classpath
RUN javac -cp "spark-core-2.9.4.jar:postgresql-42.7.3.jar:slf4j-api-1.7.36.jar:javax.servlet-api-3.1.0.jar:jetty-server-9.4.48.v20220622.jar:jetty-util-9.4.48.v20220622.jar:jetty-http-9.4.48.v20220622.jar:jetty-io-9.4.48.v20220622.jar:." SongServer.java

# Run the compiled application
CMD ["java", "-cp", "spark-core-2.9.4.jar:postgresql-42.7.3.jar:slf4j-api-1.7.36.jar:javax.servlet-api-3.1.0.jar:jetty-server-9.4.48.v20220622.jar:jetty-util-9.4.48.v20220622.jar:jetty-http-9.4.48.v20220622.jar:jetty-io-9.4.48.v20220622.jar:.", "SongServer"]
