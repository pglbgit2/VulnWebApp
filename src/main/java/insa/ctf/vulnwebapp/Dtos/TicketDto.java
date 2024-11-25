package insa.ctf.vulnwebapp.Dtos;

import insa.ctf.vulnwebapp.Entities.TicketEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketDto {
    private Long id;
    private String title;
    private String description;
    private List<String> filenames;
    private Long idCreator;
    private String creatorName;
    private List<CommentsDto> comments;

    public TicketDto(Long idTicket, String title, Long idCreator, String creatorName) {
        this.id = idTicket;
        this.title = title;
        this.idCreator = idCreator;
        this.creatorName = creatorName;
    }

    static public TicketDto toTicketDto(TicketEntity ticket, boolean fullInfo) {
        if(fullInfo){
            return new TicketDto(ticket.getIdTicket(), ticket.getTitle(), ticket.getDescription(), ticket.getFilenames(), ticket.getCreator().getId(), ticket.getCreator().getUsername(), CommentsDto.toCommentsDtos(ticket.getComments()));
        } else {
            return new TicketDto(ticket.getIdTicket(), ticket.getTitle(), ticket.getCreator().getId(), ticket.getCreator().getUsername());
        }
    }


    static public List<TicketDto> toTicketsDtos(List<TicketEntity> tickets, boolean fullInfo){
        ArrayList<TicketDto> ticketDtos = new ArrayList<>();
        for (TicketEntity ticket : tickets) {
            ticketDtos.add(toTicketDto(ticket, fullInfo));
        }
        return ticketDtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Same instance
        if (o == null || getClass() != o.getClass()) return false; // Same class and not null
        TicketDto that = (TicketDto) o;
        return Objects.equals(id, that.id); // Compare by id
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Generate hash based on id
    }
}
