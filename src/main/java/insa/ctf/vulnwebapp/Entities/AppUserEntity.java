package insa.ctf.vulnwebapp.Entities;


import insa.ctf.vulnwebapp.Dtos.UserDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class AppUserEntity {
    @Id
    @Setter(AccessLevel.NONE) @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name="username")
    private String username;

    @Column(name="password")
    private String password;

    @OneToMany(mappedBy="creator")
    private List<TicketEntity> tickets;

    @OneToMany(mappedBy="author")
    private List<TicketCommentEntity> comments;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name="role")
    private UserRole role;

    @ManyToOne
    private AppUserEntity supervisor;

    @OneToMany(mappedBy = "supervisor")
    private List<AppUserEntity> subordinates;

    @ManyToOne
    private CompanyEntity company;
}
