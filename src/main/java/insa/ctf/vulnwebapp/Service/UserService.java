package insa.ctf.vulnwebapp.Service;

import insa.ctf.vulnwebapp.Dtos.UserDto;
import insa.ctf.vulnwebapp.Entities.AppUserEntity;
import insa.ctf.vulnwebapp.Entities.CompanyEntity;
import insa.ctf.vulnwebapp.Entities.UserRole;
import insa.ctf.vulnwebapp.Repositories.AppUserRepository;
import insa.ctf.vulnwebapp.Repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    TicketRepository ticketRepository;

    public AppUserEntity getUserByUsername(String username) {
        return this.appUserRepository.findByUsername(username);
    }


    public AppUserEntity getUserById(Long id) {
        return this.appUserRepository.findById(id).orElse(null);
    }

    public UserDto toDto(Long personId, String current) {
        AppUserEntity appUserEntity = this.appUserRepository.findById(personId).orElse(null);
        if (appUserEntity == null) {
            return null;
        }
        if(appUserEntity.getUsername().equals(current)){
            return new UserDto(appUserEntity, ticketRepository.getAllByComments(appUserEntity.getComments()), appUserEntity.getSupervisor());
        }
        AppUserEntity currentUser = appUserRepository.findByUsername(current);
        if(currentUser.getRole() == UserRole.administrator || (currentUser.getRole() == UserRole.technician)){
            return new UserDto(appUserEntity, ticketRepository.getAllByComments(appUserEntity.getComments()), appUserEntity.getSupervisor());
        } else {
            return new UserDto(appUserEntity.getUsername(), appUserEntity.getId());
        }
    }

    public AppUserEntity addUserIfNotExists(String userName, String password, CompanyEntity company, UserRole role, AppUserEntity supervisor) {
        AppUserEntity appUser = this.appUserRepository.findByUsername(userName);
        if (appUser == null) {
            appUser = new AppUserEntity();
            appUser.setUsername(userName);
            appUser.setPassword(password);
            appUser.setRole(role);
            appUser.setCompany(company);
            if(supervisor != null && supervisor.getRole() == UserRole.supervisor) {
                appUser.setSupervisor(supervisor);
            }
            this.appUserRepository.save(appUser);
        }
        return appUser;
    }
}
