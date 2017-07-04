FROM java:openjdk-8-jdk-alpine

VOLUME /tmp

ADD entrypoint.sh entrypoint.sh
ADD config/ /config
ADD *.jar app.jar
RUN sh -c 'touch /app.jar'
RUN chmod +x entrypoint.sh
EXPOSE 8082
CMD ["./entrypoint.sh"]