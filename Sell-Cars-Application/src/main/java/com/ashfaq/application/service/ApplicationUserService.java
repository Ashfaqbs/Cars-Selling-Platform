package com.ashfaq.application.service;

import java.util.List;

import com.ashfaq.application.model.ApplicationUser;

public interface ApplicationUserService {

    ApplicationUser saveApplicationUser(ApplicationUser applicationUser);

    ApplicationUser updateApplicationUser(Long id, ApplicationUser applicationUser);

    ApplicationUser getApplicationUserById(Long id);

    List<ApplicationUser> getAllApplicationUsers();

    void deleteApplicationUser(Long id);

    ApplicationUser findByApplicationUsername(String applicationUser);
}
