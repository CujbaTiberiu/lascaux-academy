package it.corso.Runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import it.corso.Entities.Role;
import it.corso.Repositories.Role_repo;

@Component
public class Role_runner implements ApplicationRunner {
	@Autowired
	private Role_repo roleRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		setRoles();
	}

	private void setRoles() {
		if (!roleRepo.existsByCode("ROLE_ADMIN")) {
			Role admin = new Role("ROLE_ADMIN", "role_admin");
			roleRepo.save(admin);
		}

		if (!roleRepo.existsByCode("ROLE_USER")) {
			Role user = new Role("ROLE_USER", "role_user");
			roleRepo.save(user);
		}
	}

}
