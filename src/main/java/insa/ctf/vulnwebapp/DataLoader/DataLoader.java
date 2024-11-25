package insa.ctf.vulnwebapp.DataLoader;

import insa.ctf.vulnwebapp.Entities.*;
import insa.ctf.vulnwebapp.Repositories.AppUserRepository;
import insa.ctf.vulnwebapp.Repositories.CompanyRepository;
import insa.ctf.vulnwebapp.Repositories.TicketCommentRepository;
import insa.ctf.vulnwebapp.Repositories.TicketRepository;
import insa.ctf.vulnwebapp.Service.CommentService;
import insa.ctf.vulnwebapp.Service.CompanyService;
import insa.ctf.vulnwebapp.Service.TicketService;
import insa.ctf.vulnwebapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    CommentService  commentService;
    @Autowired
    TicketService ticketService;
    @Autowired
    UserService userService;
    @Autowired
    CompanyService companyService;

    //INSERT IGNORE INTO company_entity (id_company, company_name)
    //VALUES(1,'Anneau&Co');

    //INSERT IGNORE INTO app_user_entity (id, username, password, role, company_id_company)
    //VALUES(64, 'aragorn', 'GondorSecretKing', "supervisor", 1);


    //INSERT IGNORE INTO app_user_entity (id, username, password, role, company_id_company, supervisor_id)
    //VALUES(1, 'gimli', 'm1thril!', "user",1, 64);

    //INSERT IGNORE INTO app_user_entity (id, username, password, role, company_id_company, supervisor_id)
    //VALUES (2, 'legolas', 'WHAT_DO_YOUR_ELF_EYES_SEE?', "user",1, 64);

    //INSERT IGNORE INTO ticket_entity (id_ticket, title, description, creator_id)
    //VALUES (1, "Update problem", "The save option don't change anything", 1);

    //INSERT IGNORE INTO ticket_comment_entity (author_id, id_comment, ticket_id_ticket, content)
    //VALUES (2, 1, 1, "skill issue");













    @Override
    public void run(String... args) throws Exception {

        CompanyEntity ringco = this.companyService.addCompanyIfNotExists("Anneau&Co");
        CompanyEntity nazgulcorp = this.companyService.addCompanyIfNotExists("NazgulCorp");

        AppUserEntity aragorn = this.userService.addUserIfNotExists("aragorn", "GondorSecretKing", ringco, UserRole.supervisor, null);
        AppUserEntity gimli = this.userService.addUserIfNotExists("gimli", "m1thril!", ringco, UserRole.user, aragorn);
        AppUserEntity legolas = this.userService.addUserIfNotExists("legolas", "WHAT_DO_YOUR_ELF_EYES_SEE?", ringco, UserRole.user, aragorn);
        aragorn.setSubordinates(Arrays.asList(legolas, gimli));

        TicketEntity ticket1 = this.ticketService.addTicketIfNotExists("Update problem", "The save option don't change anything", gimli);
        if(ticket1 != null) {
            TicketCommentEntity comment1 = this.commentService.addComment(legolas, "Skill issue", ticket1);
        }
    }
}
