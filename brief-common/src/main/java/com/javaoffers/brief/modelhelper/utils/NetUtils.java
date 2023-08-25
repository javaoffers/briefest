package com.javaoffers.brief.modelhelper.utils;

import com.javaoffers.brief.modelhelper.exception.BaseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * @author mingJie
 */
public class NetUtils {

    /**
     * 获取本机的内网ip地址
     *
     * @return
     */
    public static String getInnetIp() {
        try {
            String localip = null;// 本地IP，如果没有配置外网IP则返回它
            String netip = null;// 外网IP
            Enumeration<NetworkInterface> netInterfaces;
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            boolean finded = false;// 是否找到外网IP
            while (netInterfaces.hasMoreElements() && !finded) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> address = ni.getInetAddresses();
                while (address.hasMoreElements()) {
                    ip = address.nextElement();
                    if (!ip.isSiteLocalAddress()
                            && !ip.isLoopbackAddress()
                            && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                        netip = ip.getHostAddress();
                        finded = true;
                        break;
                    } else if (ip.isSiteLocalAddress()
                            && !ip.isLoopbackAddress()
                            && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                        localip = ip.getHostAddress();
                    }
                }
            }
            if (netip != null && !"".equals(netip)) {
                return netip;
            } else {
                return localip;
            }
        }catch (Exception e){
            throw new BaseException("get ip error");
        }

    }

}
