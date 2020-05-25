FROM openjdk:8-jre-alpine3.9
MAINTAINER pumasemj@hotmail.com


ADD java-version-cron /temp/java-version-cron

COPY target/filldataBase.jar /jar/
RUN cat /temp/java-version-cron >> /etc/crontabs/root
RUN rm /temp/java-version-cron

RUN touch /var/log/cron.log

CMD crond 2>&1  >/dev/null && tail -f /var/log/cron.log









