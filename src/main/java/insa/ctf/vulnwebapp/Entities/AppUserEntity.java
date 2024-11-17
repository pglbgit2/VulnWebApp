package insa.ctf.vulnwebapp.Entities;


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

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    @OneToMany(mappedBy="creator")
    private List<TicketEntity> tickets;

    @OneToMany(mappedBy="author")
    private List<TicketCommentEntity> comments;

    @ManyToOne
    private AppUserEntity supervisor;

    @OneToMany(mappedBy = "supervisor")
    private List<AppUserEntity> supervisors;

    @ManyToOne
    private CompanyEntity company;
}
