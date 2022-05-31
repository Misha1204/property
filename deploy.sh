#docker build -t catalogue-application .
#docker save -o catalogue-application.tar catalogue-application
scp docker-compose.yaml root@164.92.128.111:/app/
scp setup.sql root@164.92.128.111:/app/
scp catalogue-application.tar root@164.92.128.111:/app/
scp production.properties root@164.92.128.111:/app/
#ssh root@164.92.128.111 "docker load -i /app/catalogue-application.tar"
