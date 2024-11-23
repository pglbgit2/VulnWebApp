package insa.ctf.vulnwebapp.Dtos;

import insa.ctf.vulnwebapp.Entities.TicketCommentEntity;
import insa.ctf.vulnwebapp.Entities.TicketEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentsDto {
    private Long idComment;
    private String content;
    private String authorName;
    private Long authorId;

    public static List<CommentsDto> toCommentsDtos(List<TicketCommentEntity> comments) {
        ArrayList<CommentsDto> commentsDtos = new ArrayList<>();
        for (TicketCommentEntity comment : comments) {
            commentsDtos.add(new CommentsDto(comment.getIdComment(), comment.getContent(), comment.getAuthor().getUsername(), comment.getAuthor().getId()));
        }
        return commentsDtos;
    }
}
