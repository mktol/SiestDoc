package org.siesta.service;

import org.siesta.repository.RepoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class RegistrationService {

    @Autowired
    private ManageDocumentService manageDocumentService;
    @Autowired
    private HandleDocumentService handleDocumentService;

    @Autowired
    private RepoRepository repository;

    public Boolean registration(String connectUrl, String repoName) {
        //TODO add url and name validation

        SiestaConnector siestaConnector = new SiestaConnector();
        siestaConnector.setName(repoName);
        siestaConnector.setUrl(connectUrl);
        handleDocumentService.addConnector(siestaConnector);
/*        Repository newRepository = new Repository(connectUrl, repoName);
        repository.save(newRepository);*/
        RepoConnector repoConnector = new RepoConnector(connectUrl, repoName);
        return manageDocumentService.addConnector(repoConnector);

    }

    public Boolean cancelOfRegistration(String repoName) {
        return manageDocumentService.removeConnector(repoName);
    }

    public void checkConnection(){

    }


}
