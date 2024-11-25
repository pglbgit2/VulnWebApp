package insa.ctf.vulnwebapp.Repositories;

import insa.ctf.vulnwebapp.Entities.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUserEntity, Long> {
    AppUserEntity findByUsername(String username);
    List<AppUserEntity> findAllBySupervisor_Id(Long id);
}
