package insa.ctf.vulnwebapp.Service;

import insa.ctf.vulnwebapp.Dtos.AuthDto;
import insa.ctf.vulnwebapp.Entities.AppUserEntity;
import insa.ctf.vulnwebapp.Entities.UserRole;
import insa.ctf.vulnwebapp.Repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    AppUserRepository userRepository;

    public boolean checkLogin(AuthDto authDto) {
        AppUserEntity user = userRepository.findByUsername(authDto.getUsername());
        if(user == null){
            return false;
        }
        return user.getPassword().equals(authDto.getPassword());
    }



    public boolean canAccessUserProfile(Long targetId, String currentUsername){
        AppUserEntity user = userRepository.findByUsername(currentUsername);
        Optional<AppUserEntity> target = userRepository.findById(targetId);
        if(target.isPresent()){
            if(user.getRole() == UserRole.supervisor || user.getRole() == UserRole.user) {
                return false;
                //return user.getCompany().getName().equals(target.get().getCompany().getName());
            }
            return user.getRole() == UserRole.administrator || user.getRole() == UserRole.technician;
        } else {
            return false;
        }
    }

}
