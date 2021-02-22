package io.github.kimmking.gateway.my;

import io.github.kimmking.gateway.router.HttpEndpointRouter;

import java.util.List;
import java.util.Random;

/**
 * @Desc
 * @Author wfy
 * @Date 2021/2/22 16:27
 */
public class MyRandomHttpEndpointRouter implements HttpEndpointRouter {

    @Override
    public String route(List<String> endpoints) {
        int size = endpoints.size();
        Random random = new Random(System.currentTimeMillis());
        return endpoints.get(random.nextInt(size));
    }
}
