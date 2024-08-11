package com.ashfaq.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ashfaq.application.model.ApplicationUser;
import com.ashfaq.application.repository.ApplicationUserRepository;

@Service
public class ApplicationUserServiceImpl implements ApplicationUserService{

	private final ApplicationUserRepository applicationUserRepository;

	public ApplicationUserServiceImpl(ApplicationUserRepository applicationUserRepository) {
		this.applicationUserRepository = applicationUserRepository;
	}

	@Override
	public ApplicationUser saveApplicationUser(ApplicationUser applicationUser) {
		try {
			return applicationUserRepository.save(applicationUser);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ApplicationUser updateApplicationUser(Long id, ApplicationUser applicationUser) {
		ApplicationUser existingApplicationUser = applicationUserRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("ApplicationUser not found with id: " + id));

		existingApplicationUser.setUsername(applicationUser.getUsername());
		existingApplicationUser.setEmail(applicationUser.getEmail());
		existingApplicationUser.setPassword(applicationUser.getPassword());
		existingApplicationUser.setRole(applicationUser.getRole());
		
		return applicationUserRepository.save(existingApplicationUser);
	}

	@Override
	public ApplicationUser getApplicationUserById(Long id) {
		return applicationUserRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("ApplicationUser not found with id: " + id));
	}

	@Override
	public List<ApplicationUser> getAllApplicationUsers() {
		return applicationUserRepository.findAll();
	}

	@Override
	public void deleteApplicationUser(Long id) {
		applicationUserRepository.deleteById(id);
	}

	@Override
	public ApplicationUser findByApplicationUsername(String applicationUsername) {
		return applicationUserRepository.findByUsername(applicationUsername)
				.orElseThrow(() -> new RuntimeException(
						"ApplicationUser not found with ApplicationUsername: " + applicationUsername));
	}

}
