package org.siesta.controller;

import org.siesta.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Endpoint for registration/ cancelOfRegistration of repository
 */
@Controller
public class RepositoryRegisterContoroller {

    @Autowired
    private RegistrationService registrationService;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public Boolean registrationRepository(@RequestParam("connectUrl") String connectUrl,
                                          @RequestParam("name") String repoName){
        return registrationService.registration(connectUrl, repoName);
    }
    @RequestMapping(value = "/unregistration", method = RequestMethod.POST)
    public Boolean unrergister(@RequestParam("connectUrl")String connectUrl,@RequestParam("name") String repoName){
        return registrationService.cancelOfRegistration(connectUrl, repoName);
    }
}
