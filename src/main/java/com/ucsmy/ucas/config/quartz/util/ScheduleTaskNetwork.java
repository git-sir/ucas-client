package com.ucsmy.ucas.config.quartz.util;


import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class ScheduleTaskNetwork {

    private final static ScheduleTaskNetwork instance = new ScheduleTaskNetwork();
    private Set<String> ips = new HashSet<>();
    private static org.slf4j.Logger log = LoggerFactory.getLogger(ScheduleTaskNetwork.class);


    /**
     * 暴露的接口，可以允许获得已经复制过的 IP Set
     *
     * @return
     */
    public static Set<String> getIPs() {
        return new HashSet<>(instance.ips);
    }

    /**
     * 暴露的接口，可以直接判断传入的 ip 是否在本地 ip 中
     *
     * @param ip
     * @return
     */
    public static boolean isExist(String ip) {
        return instance.ips.contains(ip);
    }

    /**
     * 私有化构造函数，不能显示创建
     */
    private ScheduleTaskNetwork() {
        this.initiaNetworkInterfacce();
    }


    private void initiaNetworkInterfacce() {

        try {
            this.procceed();
        } catch (Exception ex) {
            log.error("ScheduleTaskNetwork",ex);
        }

    }

    private void procceed() throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        NetworkInterface networkInterface ;
        Enumeration<InetAddress> inetAddresses ;
        InetAddress inetAddress ;

        while (networkInterfaces.hasMoreElements()) {
            networkInterface = networkInterfaces.nextElement();
            inetAddresses = networkInterface.getInetAddresses();

            while (inetAddresses.hasMoreElements()) {
                inetAddress = inetAddresses.nextElement();
                ips.add(inetAddress.getHostAddress());
            }
        }
    }

}
