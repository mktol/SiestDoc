package org.siesta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class RegistrationService {

    @Autowired
    private HandleDocumentService handleDocumentService;



    public Boolean registration(String connectUrl, String repoName) {

        if(!validateName(repoName)){
            return false;
        }
        SiestaConnector siestaConnector = new SiestaConnector();
        siestaConnector.setName(repoName);
        siestaConnector.setUrl(connectUrl);
        return handleDocumentService.addConnector(siestaConnector);

    }

    private boolean validateName(String name){
        return !handleDocumentService.getConnectors().containsKey(name);
    }

}
