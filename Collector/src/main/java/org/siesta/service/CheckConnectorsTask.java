package org.siesta.service;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by user on 13.12.2016.
 */
public class CheckConnectorsTask implements Runnable {

    private List<SiestaConnector> connectorList;
    public CheckConnectorsTask(List<SiestaConnector> connectorList) {
        this.connectorList = connectorList;
    }

    @Override
    public void run() {
        for (SiestaConnector connector : connectorList) {
            if(!connector.isConnectionALive()){
                connectorList.remove(connector);
            }
        }
    }
}
