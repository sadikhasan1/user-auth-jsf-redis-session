# Use a base image with Payara Server
FROM payara/server-full:latest

# Set the working directory
WORKDIR /opt/payara/app

# Copy the application WAR file to the app directory
COPY authredis-1.0-SNAPSHOT.war /opt/payara/appserver/glassfish/domains/domain1/autodeploy/

# Download the PostgreSQL JDBC driver
COPY postgresql-42.7.3.jar /opt/payara/appserver/glassfish/domains/domain1/lib/

# Copy the password file
COPY passwordfile /opt/payara/app/passwordfile

# Expose the default HTTP port
EXPOSE 8080
EXPOSE 4848

# Environment variables for the database connection
ENV DB_HOST=db
ENV DB_PORT=5432
ENV DB_NAME=authtest
ENV DB_USER=dynamic
ENV DB_PASSWORD=dynamic

# Copy the entrypoint script
COPY entrypoint.sh /opt/payara/app/

# Make the entrypoint script executable
RUN chmod +x /opt/payara/app/entrypoint.sh

# Run the entrypoint script
ENTRYPOINT ["/opt/payara/app/entrypoint.sh"]
