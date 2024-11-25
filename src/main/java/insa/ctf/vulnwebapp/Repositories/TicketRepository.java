package insa.ctf.vulnwebapp.Repositories;

import insa.ctf.vulnwebapp.Entities.CompanyEntity;
import insa.ctf.vulnwebapp.Entities.TicketCommentEntity;
import insa.ctf.vulnwebapp.Entities.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
    @Query("SELECT t FROM TicketEntity t JOIN t.comments c WHERE c.idComment IN :commentIds")
    List<TicketEntity> getAllByCommentsIds(@Param("commentIds") List<Long> commentIds);

    List<TicketEntity> findAllByCreator_Company(CompanyEntity company);

    List<TicketEntity> findAllByTitleEqualsIgnoreCase(String title);
}
