Build the docker image from root via the following command:
docker build -f prod/Dockerfile .

When running a new container:
map host-port:docker-port as 8080:8080
set env var DB_PASSWORD=your.password.here