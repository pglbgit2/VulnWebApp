package insa.ctf.vulnwebapp.Dtos;

import insa.ctf.vulnwebapp.Entities.AppUserEntity;
import insa.ctf.vulnwebapp.Entities.TicketEntity;
import insa.ctf.vulnwebapp.Entities.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private UserRole role;
    private Set<TicketDto> relatedTickets;
    private String supervisorName;
    private Long supervisorId;
    private List<UserDto> subordinates;


    public UserDto(AppUserEntity user, List<TicketEntity> allByComments, AppUserEntity supervisor) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.relatedTickets = new HashSet<>();
        this.relatedTickets.addAll(TicketDto.toTicketsDtos(user.getTickets(), false));
        this.relatedTickets.addAll(TicketDto.toTicketsDtos(allByComments, false));
        if(supervisor != null) {
            this.supervisorName = supervisor.getUsername();
            this.supervisorId = supervisor.getId();
        } else {
            this.supervisorId = null;
            this.supervisorName = null;
        }
        List<AppUserEntity> subordinates = user.getSubordinates();
        if(subordinates != null && !subordinates.isEmpty()) {
            this.subordinates = new ArrayList<>();
            for(AppUserEntity subordinate : subordinates) {
                this.subordinates.add(new UserDto(subordinate.getUsername(), subordinate.getId()));
            }
        }
    }


    public UserDto(String username, Long id) {
        this.username = username;
        this.id = id;
        this.supervisorId = null;
        this.supervisorName = null;
    }
}
