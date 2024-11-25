package insa.ctf.vulnwebapp.Controller;

import insa.ctf.vulnwebapp.Dtos.UserDto;
import insa.ctf.vulnwebapp.Entities.AppUserEntity;
import insa.ctf.vulnwebapp.Service.AuthService;
import insa.ctf.vulnwebapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping({"/", "/home", ""})
@SessionAttributes({"username"})
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @GetMapping({"","/home"})
    public String home(Model model) {
        Logger.getLogger(getClass().getName()).log(Level.INFO, "User is getting: /home");

        if(model.getAttribute("username") == null){
            return "redirect:/login";
        }
        return "/home/homepage";
    }

    @GetMapping("/profil")
    public String toProfil(Model model) {
        Logger.getLogger(getClass().getName()).log(Level.INFO, "User is getting: /profil");
        if(model.getAttribute("username") == null){
            return "redirect:/login";
        }
        return "redirect:/profil/"+this.userService.getUserByUsername((String) model.getAttribute("username")).getId().toString();
    }

    @GetMapping("/profil/{personId}")
    public String getUserProfil(@PathVariable Long personId, Model model) {
        Logger.getLogger(getClass().getName()).log(Level.INFO, "User is getting: /profil/id");

        if (model.getAttribute("username") == null) {
            return "redirect:/login";
        }
        if (!this.authService.canAccessUserProfile(personId, (String) model.getAttribute("username"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
        }
        model.addAttribute("userInfo", this.userService.toDto(personId, (String) model.getAttribute(("username"))));
        return "/home/profil";
    }

    @GetMapping("/profil/{personId}/update")
    public String getUpdateUserProfil(@PathVariable Long personId, Model model) {
        Logger.getLogger(getClass().getName()).log(Level.INFO, "User is getting: /profil/id/update");

        if (model.getAttribute("username") == null) {
            return "redirect:/login";
        }
        if (!this.authService.canAccessUserProfile(personId, (String) model.getAttribute("username"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
        }
        UserDto userdto = this.userService.toDto(personId, (String) model.getAttribute(("username")));
        userdto.setPassword(this.userService.getUserById(personId).getPassword());
        model.addAttribute("userInfo", userdto);
        return "/home/updateProfile";
    }

    @PostMapping("/profil/{personId}/update")
    public String postUpdateUserProfil(@PathVariable Long personId, UserDto updateDto, Model model) {
        Logger.getLogger(getClass().getName()).log(Level.INFO, "User is posting: /profil/id/update");

        if (model.getAttribute("username") == null) {
            return "redirect:/login";
        }
        if (!this.authService.canAccessUserProfile(personId, (String) model.getAttribute("username")) || !Objects.equals(this.userService.getUserByUsername((String) model.getAttribute("username")).getId(), personId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
        }
        //TODO: update user with updateDto
        UserDto userdto = this.userService.toDto(personId, (String) model.getAttribute(("username")));
        userdto.setPassword(this.userService.getUserById(personId).getPassword());
        model.addAttribute("userInfo", userdto);
        return "/home/updateProfile";
    }

}
