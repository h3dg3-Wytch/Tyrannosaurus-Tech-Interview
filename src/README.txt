This is a java implementation of the problem. It was created via java 1.8, and gradle.
It accepts JSON files that follow the hash schema, and then formats them to the schema B form.

Make sure your jdk version is at least 1.7
Install gradle: https://gradle.org/
Go to folder with build.gradle
Run: gradle build
java -jar build/libs/Ttech-1.0.jar schemafile.json