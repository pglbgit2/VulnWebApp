package insa.ctf.vulnwebapp.Repositories;

import insa.ctf.vulnwebapp.Entities.TicketCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketCommentRepository extends JpaRepository<TicketCommentEntity, Long> {
}
