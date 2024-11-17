package insa.ctf.vulnwebapp.Repositories;

import insa.ctf.vulnwebapp.Entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
}
