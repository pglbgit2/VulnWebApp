package insa.ctf.vulnwebapp.Repositories;

import insa.ctf.vulnwebapp.Entities.TicketCommentEntity;
import insa.ctf.vulnwebapp.Entities.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
    List<TicketEntity> getAllByComments(List<TicketCommentEntity> comments);
}
