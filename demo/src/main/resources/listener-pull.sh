sudo pkill java
rm -r target/
scp -r opc@10.0.0.125:~/f1-game-listener/demo/target .
source envs
nohup java -jar target/demo-1.jar &
source envs-brian
nohup java -jar target/demo-1.jar &
source envs-david
nohup java -jar target/demo-1.jar &
source envs-wojciech
nohup java -jar target/demo-1.jar &