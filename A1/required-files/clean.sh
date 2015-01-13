rm *.class getAccTypeClient.java getAccTypeServerStub.java
java -jar jrpcgen.jar getAccType.x
javac -classpath .:oncrpc.jar *.java

