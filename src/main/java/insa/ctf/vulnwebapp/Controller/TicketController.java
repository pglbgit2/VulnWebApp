package insa.ctf.vulnwebapp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"/ticket/", "/ticket"})
@SessionAttributes({"username"})
public class TicketController {
    @GetMapping("/{ticketId}")
    public String getTicketId(@PathVariable Long ticketId, Model model) {
        return "errors/error-404";
        //return "/home/fullticket";
    }


    @PostMapping("/{ticketId}")
    public String postTicketId(@PathVariable Long ticketId, Model model) {
        return "errors/error-404";
        //return "/home/fullticket";
    }

    @GetMapping("/all")
    public String getAllTickets(@PathVariable Long ticketId, Model model) {
        return "errors/error-404";
        //return "/home/fullticket";
    }


}