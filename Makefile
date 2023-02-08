build:
	# https://docs.docker.com/build/building/multi-stage/
	DOCKER_BUILDKIT=0 docker-compose build --no-cache
rm:
	docker rmi feign-client-compression-client feign-client-compression-server -f
up:
	DOCKER_BUILDKIT=0 docker-compose up
down:
	docker-compose down -v --remove-orphans