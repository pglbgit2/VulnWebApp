package insa.ctf.vulnwebapp.DataLoader;

import insa.ctf.vulnwebapp.Entities.*;
import insa.ctf.vulnwebapp.Repositories.AppUserRepository;
import insa.ctf.vulnwebapp.Repositories.CompanyRepository;
import insa.ctf.vulnwebapp.Repositories.TicketCommentRepository;
import insa.ctf.vulnwebapp.Repositories.TicketRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    private final AppUserRepository appUserRepository;
    private final CompanyRepository companyRepository;
    private final TicketRepository  ticketRepository;
    private final TicketCommentRepository ticketCommentRepository;

    public DataLoader(AppUserRepository appUserRepository, CompanyRepository companyRepository, TicketRepository ticketRepository, TicketCommentRepository ticketCommentRepository) {
        this.appUserRepository = appUserRepository;
        this.companyRepository = companyRepository;
        this.ticketRepository = ticketRepository;
        this.ticketCommentRepository = ticketCommentRepository;
    }

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


    public CompanyEntity addCompanyIfNotExists(String companyName) {
        CompanyEntity company = this.companyRepository.findByCompanyNameEqualsIgnoreCase(companyName);
        if (company == null) {
            company = new CompanyEntity();
            company.setCompanyName(companyName);
            this.companyRepository.save(company);
        }
        return company;
    }

    public AppUserEntity addUserIfNotExists(String userName, String password, CompanyEntity company, UserRole role, AppUserEntity supervisor) {
        AppUserEntity appUser = this.appUserRepository.findByUsername(userName);
        if (appUser == null) {
            appUser = new AppUserEntity();
            appUser.setUsername(userName);
            appUser.setPassword(password);
            appUser.setRole(role);
            appUser.setCompany(company);
            if(supervisor != null && supervisor.getRole() == UserRole.supervisor) {
                appUser.setSupervisor(supervisor);
            }
            this.appUserRepository.save(appUser);
        }
        return appUser;
    }

    public TicketCommentEntity addComment(AppUserEntity creator, String content, TicketEntity ticket){
        TicketCommentEntity ticketComment = new TicketCommentEntity();
        ticketComment.setContent(content);
        ticketComment.setTicket(ticket);
        ticketComment.setAuthor(creator);
        this.ticketCommentRepository.save(ticketComment);
        return ticketComment;
    }

    public TicketEntity addTicket(String title, String problem, AppUserEntity creator){
        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setTitle(title);
        ticketEntity.setDescription(problem);
        ticketEntity.setCreator(creator);
        this.ticketRepository.save(ticketEntity);
        return ticketEntity;
    }




    @Override
    public void run(String... args) throws Exception {

        CompanyEntity ringco = this.addCompanyIfNotExists("Anneau&Co");
        CompanyEntity nazgulcorp = this.addCompanyIfNotExists("NazgulCorp");

        AppUserEntity aragorn = this.addUserIfNotExists("aragorn", "GondorSecretKing", ringco, UserRole.supervisor, null);
        AppUserEntity gimli = this.addUserIfNotExists("gimli", "m1thril!", ringco, UserRole.user, aragorn);
        AppUserEntity legolas = this.addUserIfNotExists("legolas", "WHAT_DO_YOUR_ELF_EYES_SEE?", ringco, UserRole.user, aragorn);


        TicketEntity ticket1 = this.addTicket("Update problem", "The save option don't change anything", gimli);
        TicketCommentEntity comment1 = this.addComment(legolas, "Skill issue", ticket1);

    }
}
