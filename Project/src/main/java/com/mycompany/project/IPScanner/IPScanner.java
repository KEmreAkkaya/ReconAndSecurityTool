package com.mycompany.project.IPScanner;

import com.mycompany.project.FXController.MainController;
import java.io.IOException;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.net.util.SubnetUtils;

public class IPScanner {

    public static List<String> ipList = new ArrayList<>();

    public static List<String> getIpList() {
        return ipList;
    }

    public static void setIpList(List<String> ipList) {
        IPScanner.ipList = ipList;
    }

    public static void add(String str) {
        IPScanner.ipList.add(str);

    }

    public static void startIPScanner(String ipaddress, int mask) {

        scan(ipaddress, mask);
    }

    public static ConcurrentSkipListSet<String> scan(String ipaddress, int mask) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        ConcurrentSkipListSet<String> ipsSet = new ConcurrentSkipListSet();

        executorService.submit(new Runnable() {
            @Override
            public void run() {

                String subnet = ipaddress + "/" + mask;
                SubnetUtils utils = new SubnetUtils(subnet);
                String[] addresses = utils.getInfo().getAllAddresses();

                ipList.clear();

                for (int i = 0; i < addresses.length; i++) {

                    try {
                        InetAddress inAddress = InetAddress.getByName(addresses[i]);
                        if (inAddress.isReachable(10)) {

                            ipsSet.add(addresses[i]);
                            add(addresses[i]);

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }

        });

        try {
            executorService.shutdownNow();
            executorService.awaitTermination(60, TimeUnit.MINUTES);

        } catch (InterruptedException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ipsSet;
    }

}
