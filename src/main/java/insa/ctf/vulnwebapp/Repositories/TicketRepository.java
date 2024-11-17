package insa.ctf.vulnwebapp.Repositories;

import insa.ctf.vulnwebapp.Entities.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

}
