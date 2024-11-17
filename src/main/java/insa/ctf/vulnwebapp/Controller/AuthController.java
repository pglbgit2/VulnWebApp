package insa.ctf.vulnwebapp.Controller;

import insa.ctf.vulnwebapp.Dtos.AuthDto;
import insa.ctf.vulnwebapp.Service.AuthService;
import insa.ctf.vulnwebapp.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("")
@SessionAttributes({"username"})
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @GetMapping({"/login"})
    public String login(Model model) {
        Logger.getLogger(getClass().getName()).log(Level.INFO, "User is getting /login");
        if(model.getAttribute("username") != null) {
            return "redirect:/home";
        }
        model.addAttribute("authDto", new AuthDto());
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout(Model model, SessionStatus sessionStatus) {
        Logger.getLogger(getClass().getName()).log(Level.INFO, "User is posting /logout");
        sessionStatus.setComplete();
        model.addAttribute("authDto", new AuthDto());
        model.addAttribute("username", null);
        return "redirect:/login";
    }



    @PostMapping("/login")
    public String register(AuthDto authDto, Model model) {
        Logger.getLogger(getClass().getName()).log(Level.INFO, "User is posting: /login");
        if(!authService.checkLogin(authDto)){
            model.addAttribute("error", "Bad Username Or Password");
            return "auth/login";
        }
        model.addAttribute("username", authDto.getUsername());
        return "redirect:/home";
    }
}
