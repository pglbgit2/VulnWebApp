package insa.ctf.vulnwebapp.Dtos;

import insa.ctf.vulnwebapp.Entities.TicketEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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



    static public List<TicketDto> toTicketDtos(List<TicketEntity> tickets, boolean fullInfo){
        ArrayList<TicketDto> ticketDtos = new ArrayList<>();
        for (TicketEntity ticket : tickets) {
            if(fullInfo) {
                ticketDtos.add(new TicketDto(ticket.getIdTicket(), ticket.getTitle(), ticket.getDescription(), ticket.getFilenames(), ticket.getCreator().getId(), ticket.getCreator().getUsername(), CommentsDto.toCommentsDtos(ticket.getComments())));
            } else {
                ticketDtos.add(new TicketDto(ticket.getIdTicket(), ticket.getTitle(), ticket.getCreator().getId(), ticket.getCreator().getUsername()));
            }
        }
        return ticketDtos;
    }
}
