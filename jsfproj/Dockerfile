# Use a base image with Payara Server
FROM payara/server-full:latest
USER payara
# Set the working directory
WORKDIR /opt/payara/app

# Copy the application WAR file to the app directory
COPY authredis-1.0-SNAPSHOT.war /opt/payara/appserver/glassfish/domains/domain1/autodeploy/
#COPY authredis-1.0-SNAPSHOT.war $DEPLOY_DIR

# Copy the PostgreSQL JDBC driver
COPY postgresql-42.7.3.jar /opt/payara/appserver/glassfish/domains/domain1/lib/

# Copy preboot and postboot command files
COPY pre-boot-commands.asadmin $PREBOOT_COMMANDS
COPY post-boot-commands.asadmin $POSTBOOT_COMMANDS

# Change ownership of the commands file
USER root
RUN chown payara $PREBOOT_COMMANDS && chown payara $POSTBOOT_COMMANDS

# Switch to the payara user
USER payara

# Expose the default HTTP and admin ports
EXPOSE 8080
EXPOSE 4848
