FROM java:8

WORKDIR /

ADD target/approval-flow-1.0-SNAPSHOT.jar approval.jar
ENV argum APPROVED

EXPOSE 8080

CMD java -jar approval.jar ${argum}
