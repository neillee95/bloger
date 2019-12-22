FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY build/dependency/BOOT-INF/lib /app/lib
COPY build/dependency/META-INF /app/META-INF
COPY build/dependency/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","me.lee.bloger.BlogerApplicationKt","--spring.profiles.active=compose"]