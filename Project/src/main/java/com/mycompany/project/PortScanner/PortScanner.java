package com.mycompany.project.PortScanner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class PortScanner {

    public static List<String> portList = new ArrayList<>();

    public static List<String> getportList() {
        return portList;
    }

    public static void setportList(List<String> ipList) {
        PortScanner.portList = ipList;
    }

    public static void add(String str) {
        PortScanner.portList.add(str);

    }

    public static void TCPPortScanner(String ipaddress, int startPort, int endPort) throws InterruptedException, ExecutionException {

        portList.clear();

        final ExecutorService executorService = Executors.newFixedThreadPool(20);
        final String ip = ipaddress;
        final int timeout = 200;
        final List<Future<ScanResult>> futures = new ArrayList<>();

        for (int port = startPort; port <= endPort; port++) {

            futures.add(portIsOpen(executorService, ip, port, timeout));

        }
        executorService.awaitTermination(200L, TimeUnit.MILLISECONDS);
        int openPorts = 0;
        for (final Future<ScanResult> f : futures) {
            if (f.get().isOpen()) {
                openPorts++;

                portList.add("> " + f.get().getPort() + " is open on " + ip + " / Port is probably " + PortScannerDescription.getPortDescription(f.get().getPort()));

            }
        }

    }

    public static Future<ScanResult> portIsOpen(final ExecutorService es, final String ip, final int port,
            final int timeout) {
        return es.submit(() -> {
            try {
                try ( Socket socket = new Socket()) {
                    socket.connect(new InetSocketAddress(ip, port), timeout);
                }
                return new ScanResult(port, true);
            } catch (IOException ex) {
                return new ScanResult(port, false);
            }
        });
    }

    public static class ScanResult {

        private int port;

        private boolean isOpen;

        public ScanResult(int port, boolean isOpen) {
            super();
            this.port = port;
            this.isOpen = isOpen;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public boolean isOpen() {
            return isOpen;
        }

        public void setOpen(boolean isOpen) {
            this.isOpen = isOpen;
        }

    }
}
