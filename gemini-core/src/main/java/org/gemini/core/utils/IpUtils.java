package org.gemini.core.utils;

import java.net.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe IP工具类
 * @date 2023/8/25 18:51
 */
public class IpUtils {

    /**
     * eth0 代表的是单个物理网卡接口，例如一张以太网卡（Ethernet Card）
     */
    private static final String NETWORK_CARD = "eth0";

    /**
     * bond0 代表的是虚拟的网络接口，它集成了多张物理网卡接口，实现了网络负载均衡和冗余备援。
     */
    private static final String NETWORK_CARD_BAND = "bond0";

    /**
     * 当前IP
     */
    public final static String CURRENT_IP = getLocalIP();

    /**
     * loopback 地址
     */
    public final static String LOOPBACK_ADDRESS="127.0.0.1";


    /**
     * 找到第一个非回环地址（非 loopback 地址）的 IPv4 地址,若找不到则返回loopback 地址
     * @return
     */
    private static String getLocalIP() {
        InetAddress result=null;
        try{
            int index=Integer.MAX_VALUE;
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while(interfaces.hasMoreElements()){
                NetworkInterface i1 = interfaces.nextElement();
                if(i1.isUp()) {
                    if (i1.getIndex() < index || result == null) {
                        index = i1.getIndex();
                    } else if (result != null) {
                        continue;
                    }
                }
                for(Enumeration<InetAddress> addrs=i1.getInetAddresses();addrs.hasMoreElements();){
                    InetAddress address = addrs.nextElement();
                    if(address instanceof Inet4Address
                            && !address.isLoopbackAddress()){
                        result=address;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(result!=null){
            return result.getHostAddress();
        }
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return LOOPBACK_ADDRESS;
    }

    /**
     * 获取本地主机的主机名
     * @return
     */
    public static String getLocalHostName() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostName();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取所有的主机地址
     * @return
     */
    public static Collection<InetAddress> getAllHostAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            Collection<InetAddress> addresses = new ArrayList<InetAddress>();

            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    addresses.add(inetAddress);
                }
            }

            return addresses;
        } catch (SocketException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


}
