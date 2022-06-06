sudo pkill java
rm -r /home/opc/SQL
scp -r 141.147.102.85:/home/opc/f1-game-listener/demo/src/main/java/Repository/SQL .
scp -r 10.0.0.125:/home/opc/f1-game-listener/demo/target .
source envs
nohup java -jar target/demo-1.jar &