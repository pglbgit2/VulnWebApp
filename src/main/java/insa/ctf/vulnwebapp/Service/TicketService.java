package insa.ctf.vulnwebapp.Service;

import insa.ctf.vulnwebapp.Dtos.CommentsDto;
import insa.ctf.vulnwebapp.Dtos.TicketDto;
import insa.ctf.vulnwebapp.Entities.*;
import insa.ctf.vulnwebapp.Repositories.AppUserRepository;
import insa.ctf.vulnwebapp.Repositories.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private CommentService commentService;

    public TicketEntity getTicketById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public List<TicketEntity> getAllTickets() {
        return ticketRepository.findAll();
    }

    public List<TicketEntity> getAllTicketsFromSameCompany(CompanyEntity company) {
        return this.ticketRepository.findAllByCreator_Company(company);
    }

    public TicketEntity addTicket(String title, String problem, AppUserEntity creator){
        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setTitle(title);
        ticketEntity.setDescription(problem);
        ticketEntity.setCreator(creator);
        this.ticketRepository.save(ticketEntity);
        return ticketEntity;
    }

    public void commentTicket(Long ticketId, CommentsDto comment) {
        Optional<TicketEntity> ticket = this.ticketRepository.findById(ticketId);
        if(ticket.isPresent()) {
            TicketEntity ticketEntity = ticket.get();
            TicketCommentEntity commentEntity = this.commentService.addComment(this.appUserRepository.findByUsername(comment.getAuthorName()), comment.getContent(), ticketEntity);
            ticketEntity.getComments().add(commentEntity);
        }
    }

    public void createTicket(TicketDto ticketDto, String author) {
        this.addTicket(ticketDto.getTitle(), ticketDto.getDescription(), this.appUserRepository.findByUsername(author));
    }

    public TicketEntity addTicketIfNotExists(String title, String description, AppUserEntity gimli) {
        if(this.ticketRepository.findAllByTitleEqualsIgnoreCase(title).isEmpty()) {
            return this.addTicket(title, description, gimli);
        }
        return null;
    }

    public List<String> getFilesOfTicket(Long ticketId) {
        Optional<TicketEntity> ticketsOpt =  this.ticketRepository.findById(ticketId);
        if(ticketsOpt.isPresent()) {
           return ticketsOpt.get().getFilenames();
        } else {
            return new ArrayList<>();
        }
    }

    @Transactional
    public void addFileToTicket(Long ticketId, String fileName) {
        Optional<TicketEntity> ticketsOpt =  this.ticketRepository.findById(ticketId);
        System.out.println("in addFile");
        ticketsOpt.ifPresent(ticketEntity -> {ticketEntity.getFilenames().add(fileName); System.out.println("AJOUT FICHIER");});
    }
}
