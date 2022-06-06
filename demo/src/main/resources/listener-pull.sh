sudo pkill java
rm -r target/
rm -r /home/opc/SQL
scp -r opc@10.0.0.125:/home/opc/f1-game-listener/demo/src/main/java/Repository/SQL .
scp -r opc@10.0.0.125:~/f1-game-listener/demo/target .
source envs-api
nohup java -jar target/demo-1.jar &