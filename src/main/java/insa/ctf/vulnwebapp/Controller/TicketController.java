package insa.ctf.vulnwebapp.Controller;

import insa.ctf.vulnwebapp.Dtos.CommentsDto;
import insa.ctf.vulnwebapp.Dtos.TicketDto;
import insa.ctf.vulnwebapp.Entities.TicketEntity;
import insa.ctf.vulnwebapp.Service.Facade;
import insa.ctf.vulnwebapp.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping({"/ticket/", "/ticket"})
@SessionAttributes({"username"})
public class TicketController {

    @Autowired
    Facade facade;

    @Autowired
    TicketService ticketService;

    @GetMapping({"/", ""})
    public String getRoot(Model model) {
        return "redirect:/ticket/all";
    }


    @GetMapping("/{ticketId}")
    public String getTicketId(@PathVariable Long ticketId, Model model) {
        Logger.getLogger(getClass().getName()).log(Level.INFO, "User is getting: /ticket/"+ticketId);

        if (model.getAttribute("username") == null) {
            return "redirect:/login";
        }
        TicketEntity ticket = facade.getTicketIfAuthorized((String) model.getAttribute("username"), ticketId);
        model.addAttribute("ticket", TicketDto.toTicketDto(ticket, true));
        model.addAttribute("commentDto", new CommentsDto());
        return "/tickets/oneTicket";
    }


    @PostMapping("/{ticketId}/comment")
    public String postTicketComment(@PathVariable Long ticketId, CommentsDto comment, Model model) {
        if (model.getAttribute("username") == null) {
            return "redirect:/login";
        }
        comment.setAuthorName((String) model.getAttribute("username"));
        this.ticketService.commentTicket(ticketId, comment);
        return "redirect:/ticket/" + ticketId;
    }

    @GetMapping("/new")
    public String createTicketGet(Model model) {
        Logger.getLogger(getClass().getName()).log(Level.INFO, "User is getting: /ticket/new");

        if (model.getAttribute("username") == null) {
            return "redirect:/login";
        }
        model.addAttribute("ticketDto", new TicketDto());
        return "tickets/newTicket";
    }

    @PostMapping("/new")
    public String createTicketPost(Model model, TicketDto ticketDto) {
        if (model.getAttribute("username") == null) {
            return "redirect:/login";
        }
        this.ticketService.createTicket(ticketDto, (String)model.getAttribute("username"));
        return "redirect:/ticket/";
    }

    @GetMapping("/all")
    public String getAllTickets(Model model) {
        Logger.getLogger(getClass().getName()).log(Level.INFO, "User is getting: /ticket/all");

        if (model.getAttribute("username") == null) {
            return "redirect:/login";
        }
        List<TicketEntity> tickets = facade.getAllAuthorizedTickets((String) model.getAttribute("username"));

        List<TicketDto> ticketsDtos =  TicketDto.toTicketsDtos(tickets, false);
        model.addAttribute("tickets", ticketsDtos);
        return "/tickets/allTickets";
    }


}