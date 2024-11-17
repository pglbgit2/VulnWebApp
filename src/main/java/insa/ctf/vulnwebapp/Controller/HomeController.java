package insa.ctf.vulnwebapp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping({"/", "/home"})
@SessionAttributes({"username"})
public class HomeController {
    @GetMapping({"","/home"})
    public String home(Model model) {
        Logger.getLogger(getClass().getName()).log(Level.INFO, "User is getting: /home");

        if(model.getAttribute("username") == null){
            return "redirect:/login";
        }
        return "/home/homepage";
    }

}
