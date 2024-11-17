package insa.ctf.vulnwebapp.Entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TicketCommentEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Setter(AccessLevel.NONE)
    private Long idComment;

    private String content;

    @ManyToOne
    TicketEntity ticket;

    @ManyToOne
    AppUserEntity author;
}
