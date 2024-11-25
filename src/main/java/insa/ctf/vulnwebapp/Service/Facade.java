package insa.ctf.vulnwebapp.Service;

import insa.ctf.vulnwebapp.Entities.AppUserEntity;
import insa.ctf.vulnwebapp.Entities.TicketEntity;
import insa.ctf.vulnwebapp.Entities.UserRole;
import insa.ctf.vulnwebapp.Repositories.AppUserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Facade {
    @Autowired
    TicketService ticketService;
    @Autowired
    AppUserRepository appUserRepository;

    public List<TicketEntity> getAllAuthorizedTickets(String username) {
        AppUserEntity appUser = appUserRepository.findByUsername(username);
        if(appUser.getRole() == UserRole.administrator || appUser.getRole() == UserRole.technician){
            return this.ticketService.getAllTickets();
        } else {
            return this.ticketService.getAllTicketsFromSameCompany(appUser.getCompany());
        }
    }

    public TicketEntity getTicketIfAuthorized(String username, Long ticketId) {
        AppUserEntity appUser = appUserRepository.findByUsername(username);
        TicketEntity ticket = ticketService.getTicketById(ticketId);
        if(appUser.getRole() == UserRole.administrator || appUser.getRole() == UserRole.technician || ticket.getCreator().getCompany() == appUser.getCompany()){
            return ticket;
        } else {
            return null;
        }
    }
}
