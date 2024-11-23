package insa.ctf.vulnwebapp.Entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TicketEntity {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTicket;

    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;

    @ElementCollection
    List<String> filenames;

    @ManyToOne
    AppUserEntity creator;

    @OneToMany(mappedBy = "ticket")
    List<TicketCommentEntity> comments;
}
