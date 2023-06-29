文件所依赖的中间件服务
//todo 网盘链接 docker官网
1.加载镜像
docker pull redis
docker pull minio/minio
docker pull mysql
docker pull rabbitmq:management
2.运行容器
...

docker run --name Redis -p 6379:6379 -itd redis

docker run --name MinIO -p 9000:9000 -itd --env MINIO_ROOT_USER=root --env MINIO_ROOT_PASSWORD=12345678 --restart=always -p 9001:9001 minio/minio server /data --console-address ":9001"

docker run --name MySQL -p 3306:3306 --restart=always -e MYSQL_ROOT_PASSWORD=12345678 -itd  mysql

docker run --name RabbitMQ -p 5672:5672 -p 15672:15672 --restart=always  -itd rabbitmq:management