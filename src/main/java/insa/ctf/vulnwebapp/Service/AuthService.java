package insa.ctf.vulnwebapp.Service;

import insa.ctf.vulnwebapp.Dtos.AuthDto;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public boolean checkLogin(AuthDto authDto) {
        return authDto.getUsername().equals("admin") && authDto.getPassword().equals("admin");
    }
}
