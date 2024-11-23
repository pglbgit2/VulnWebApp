package insa.ctf.vulnwebapp.Service;

import insa.ctf.vulnwebapp.Entities.AppUserEntity;
import insa.ctf.vulnwebapp.Entities.TicketEntity;
import insa.ctf.vulnwebapp.Repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public TicketEntity addTicket(String title, String problem, AppUserEntity creator){
        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setTitle(title);
        ticketEntity.setDescription(problem);
        ticketEntity.setCreator(creator);
        this.ticketRepository.save(ticketEntity);
        return ticketEntity;
    }
}
