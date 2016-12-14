package org.siesta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Service for connection checking
 */
@Component
public class ConnectorChecker {

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);

    @Autowired
    private HandleDocumentService documentService;

    public void connectorsChecker(){
        CheckConnectorsTask connectorsTask = new CheckConnectorsTask(documentService.getConnectors());
        executorService.schedule(connectorsTask, 30, TimeUnit.SECONDS);
    }


}
