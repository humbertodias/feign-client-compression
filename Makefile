build:
	# https://docs.docker.com/build/building/multi-stage/
	DOCKER_BUILDKIT=0 docker-compose build
run:
	DOCKER_BUILDKIT=0 docker-compose up