
package com.mycompany.project.Interfaces;

import java.util.concurrent.ConcurrentSkipListSet;


public interface IPScannerInterface {

    public ConcurrentSkipListSet<String> getIPScanner(String ip, int mask);
}
