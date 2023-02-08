#!/bin/bash

jar_unzip(){
    # sdk install gradle
    cd server/build
    rm -rf native
    mkdir native
    cd native
    jar xvf ../libs/server-0.0.1-SNAPSHOT.jar
}

jar_native(){
    # sdk install java 22.3.1.r17-grl
    CP=BOOT-INF/classes:`find BOOT-INF/lib | tr '\n' ':'`
    MAINCLASS=com.example.demo.FakerApplication
    ARTIFACT=server
    GRAALVM_VERSION=`native-image --version`
    echo "[-->] Compiling Spring Boot App '$ARTIFACT' with $GRAALVM_VERSION"
    time native-image \
      --no-fallback \
      -J-Xmx4G \
      -H:Name=$ARTIFACT \
      -H:+ReportExceptionStackTraces \
      -Dspring.graal.remove-unused-autoconfig=true \
      -Dspring.graal.remove-yaml-support=true \
      -cp $CP $MAINCLASS;
}

./gradlew :server:build
jar_unzip
jar_native