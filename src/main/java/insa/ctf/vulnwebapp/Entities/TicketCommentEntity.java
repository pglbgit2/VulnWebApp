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

    @Column(name="content")
    private String content;

    @ManyToOne
    TicketEntity ticket;

    @ManyToOne
    AppUserEntity author;

    public TicketCommentEntity(String content, TicketEntity ticket, AppUserEntity author) {
        this.content = content;
        this.ticket = ticket;
        this.author = author;
    }
}
