package insa.ctf.vulnwebapp.Repositories;

import insa.ctf.vulnwebapp.Entities.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUserEntity, Long> {
    AppUserEntity findByUsername(String username);
}
