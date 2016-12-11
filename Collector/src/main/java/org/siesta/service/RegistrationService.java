package org.siesta.service;

import org.siesta.model.Repository;
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
    private RepoRepository repository;
    public Boolean registration(String connectUrl, String repoName) {
        //TODO add url and name validation

        Repository newRepository = new Repository(connectUrl, repoName);
        repository.save(newRepository);
        RepoConnector repoConnector = new RepoConnector();
        repoConnector.setRepoName(repoName);
        repoConnector.setRepoName(connectUrl);
        return null;
    }

    public Boolean cancelOfRegistration(String connectUrl, String repoName) {


        return null;

    }


}
