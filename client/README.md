# Client

## Request


Redis

    curl -I "http://localhost:9090/demo/person?amount=999&delay=0&cacheManager=redisCacheManager"

http://localhost:8081/

hazelCastCacheManager

    curl -I "http://localhost:9090/demo/person?amount=999&delay=0&cacheManager=hazelCastCacheManager"

http://localhost:8080/clusters/dev/maps/person

simpleCacheManager

    curl -I "http://localhost:9090/demo/person?amount=999&delay=0&cacheManager=simpleCacheManager"

caffeineCacheManager

    curl -I "http://localhost:9090/demo/person?amount=999&delay=0&cacheManager=caffeineCacheManager"


noCacheManager

    curl -I "http://localhost:9090/demo/person?amount=999&delay=0&cacheManager=noCacheManager"
