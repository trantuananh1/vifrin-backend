mvn clean install package &
cd ./service-registry
./run-backend.sh &
cd ../cloud-config-server
./run-backend.sh &
cd ../cloud-gateway
./run-backend.sh &
cd ../hystrix-dashboard
./run-backend.sh &
cd ../auth-service
./run-backend.sh &
cd ../user-service
./run-backend.sh &
cd ../media-service
./run-backend.sh &
cd ../post-service
./run-backend.sh &
cd ../feed-service
./run-backend.sh &
cd ../comment-service
./run-backend.sh &
cd ../like-service
./run-backend.sh &
cd ../destination-service
./run-backend.sh &
cd ../search-service
./run-backend.sh


