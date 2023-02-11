build:
	# https://docs.docker.com/build/building/multi-stage/
	DOCKER_BUILDKIT=0 docker-compose build
run:
	DOCKER_BUILDKIT=0 docker-compose up
stop:
	docker-compose down -v --remove-orphans
clean:
	docker rmi feign-client-compression-client feign-client-compression-server -f