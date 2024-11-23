package insa.ctf.vulnwebapp.Service;

import insa.ctf.vulnwebapp.Entities.AppUserEntity;
import insa.ctf.vulnwebapp.Entities.TicketCommentEntity;
import insa.ctf.vulnwebapp.Entities.TicketEntity;
import insa.ctf.vulnwebapp.Repositories.TicketCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private TicketCommentRepository ticketCommentRepository;

    public TicketCommentEntity addComment(AppUserEntity creator, String content, TicketEntity ticket){
        TicketCommentEntity ticketComment = new TicketCommentEntity();
        ticketComment.setContent(content);
        ticketComment.setTicket(ticket);
        ticketComment.setAuthor(creator);
        this.ticketCommentRepository.save(ticketComment);
        return ticketComment;
    }
}
