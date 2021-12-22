mvn clean install package &
cd ./service-registry
sudo chmod -R 777 ./run-backend.sh
./run-backend.sh &
cd ../cloud-config-server
sudo chmod -R 777 ./run-backend.sh
./run-backend.sh &
cd ../cloud-gateway
sudo chmod -R 777 ./run-backend.sh
./run-backend.sh &
cd ../hystrix-dashboard
sudo chmod -R 777 ./run-backend.sh
./run-backend.sh &
cd ../auth-service
sudo chmod -R 777 ./run-backend.sh
./run-backend.sh &
cd ../user-service
sudo chmod -R 777 ./run-backend.sh
./run-backend.sh &
cd ../media-service
sudo chmod -R 777 ./run-backend.sh
./run-backend.sh &
cd ../post-service
sudo chmod -R 777 ./run-backend.sh
./run-backend.sh &
cd ../feed-service
sudo chmod -R 777 ./run-backend.sh
./run-backend.sh &
cd ../comment-service
sudo chmod -R 777 ./run-backend.sh
./run-backend.sh &
cd ../like-service
sudo chmod -R 777 ./run-backend.sh
./run-backend.sh &
cd ../destination-service
sudo chmod -R 777 ./run-backend.sh
./run-backend.sh &
cd ../search-service
sudo chmod -R 777 ./run-backend.sh
./run-backend.sh


