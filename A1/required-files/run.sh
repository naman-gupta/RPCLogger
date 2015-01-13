sudo service portmap stop
sudo rpcbind -i -w
sudo service portmap start
java -classpath .:oncrpc.jar getAccTypeInterface
