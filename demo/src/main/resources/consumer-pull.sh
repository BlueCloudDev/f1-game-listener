sudo pkill java
scp -r 10.0.0.125:/home/opc/f1-game-listener/demo/target .
source envs
nohup java -jar target/demo-1.jar &