#!/usr/bin/env bash
javac -d target/classes -cp "target/dependency/rsyntaxtextarea-3.1.3.jar:target/dependency/commons-io-2.7.jar:target/dependency/autocomplete-3.1.2.jar" ./src/main/java/ru/jf17/ide/*.java
