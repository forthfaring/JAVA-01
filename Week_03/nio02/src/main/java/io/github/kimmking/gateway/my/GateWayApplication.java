package io.github.kimmking.gateway.my;

import java.util.Arrays;

/**
 * @Desc
 * @Author wfy
 * @Date 2021/2/22 10:40
 */
public class GateWayApplication {
    public static void main(String[] args) {
        String proxyServers = "http://localhost:8088,http://localhost:8089";
        int port = 9000;
        GateWayServer gateWayServer = new GateWayServer(port, Arrays.asList(proxyServers.split(",")));
        System.out.println("gateway starting...");
        try {
            gateWayServer.run();
        } catch (Exception e) {
            System.out.println("gateway start failed");
            e.printStackTrace();
        }
        System.out.println("gateway started, port:" + port);
    }
}
