package insa.ctf.vulnwebapp.Controller;

import insa.ctf.vulnwebapp.Dtos.CommentsDto;
import insa.ctf.vulnwebapp.Dtos.TicketDto;
import insa.ctf.vulnwebapp.Entities.TicketEntity;
import insa.ctf.vulnwebapp.Service.Facade;
import insa.ctf.vulnwebapp.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping({"/ticket/", "/ticket"})
@SessionAttributes({"username"})
public class TicketController {

    @Autowired
    Facade facade;

    @Autowired
    TicketService ticketService;

    private static final String UPLOAD_DIR = "src/main/resources/static/";

    @GetMapping({"/", ""})
    public String getRoot(Model model) {
        return "redirect:/ticket/all";
    }


    @GetMapping("/{ticketId}")
    public String getTicketId(@PathVariable Long ticketId, Model model) {
        Logger.getLogger(getClass().getName()).log(Level.INFO, "User is getting: /ticket/"+ticketId);

        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        if (model.getAttribute("username") == null) {
            return "redirect:/login";
        }
        TicketEntity ticket = facade.getTicketIfAuthorized((String) model.getAttribute("username"), ticketId);
        if(ticket == null) {
            return "redirect:/errors/error-403";
        }
        model.addAttribute("ticket", TicketDto.toTicketDto(ticket, true));
        model.addAttribute("commentDto", new CommentsDto());
        model.addAttribute("fileNames", ticketService.getFilesOfTicket(ticketId));
        return "/tickets/oneTicket";
    }

    @PostMapping("{ticketId}/upload")
    public String uploadFile(@RequestParam("uploadfile") MultipartFile file, @PathVariable Long ticketId, Model model) {
        if (!file.isEmpty()) {
            try {
                // Generate a unique file name
                String fileName = file.getOriginalFilename();
                fileName = ensureUniqueFileName(fileName);

                // Save the file to the uploads directory
                Path path = Paths.get(UPLOAD_DIR, fileName);
                Files.copy(file.getInputStream(), path);
                this.ticketService.addFileToTicket(ticketId, fileName);
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("error", "Erreur lors du téléchargement du fichier");
            }
        }
        return "redirect:/ticket/"+ticketId;
    }


    @PostMapping("/{ticketId}/comment")
    public String postTicketComment(@PathVariable Long ticketId, CommentsDto comment, Model model) {
        if (model.getAttribute("username") == null) {
            return "redirect:/login";
        }
        comment.setAuthorName((String) model.getAttribute("username"));
        this.ticketService.commentTicket(ticketId, comment);
        return "redirect:/ticket/" + ticketId;
    }

    @GetMapping("/new")
    public String createTicketGet(Model model) {
        Logger.getLogger(getClass().getName()).log(Level.INFO, "User is getting: /ticket/new");

        if (model.getAttribute("username") == null) {
            return "redirect:/login";
        }
        model.addAttribute("ticketDto", new TicketDto());
        return "tickets/newTicket";
    }

    @PostMapping("/new")
    public String createTicketPost(Model model, TicketDto ticketDto) {
        if (model.getAttribute("username") == null) {
            return "redirect:/login";
        }
        this.ticketService.createTicket(ticketDto, (String)model.getAttribute("username"));
        return "redirect:/ticket/";
    }

    @GetMapping("/all")
    public String getAllTickets(Model model) {
        Logger.getLogger(getClass().getName()).log(Level.INFO, "User is getting: /ticket/all");

        if (model.getAttribute("username") == null) {
            return "redirect:/login";
        }
        List<TicketEntity> tickets = facade.getAllAuthorizedTickets((String) model.getAttribute("username"));

        List<TicketDto> ticketsDtos =  TicketDto.toTicketsDtos(tickets, false);
        model.addAttribute("tickets", ticketsDtos);
        return "/tickets/allTickets";
    }

    @GetMapping("/download/{filename}")
    @ResponseBody
    public byte[] downloadFile(@PathVariable String filename) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIR, filename);
        return Files.readAllBytes(filePath);
    }

    // Helper method to ensure the file name is unique by adding a number if needed
    private String ensureUniqueFileName(String fileName) {
        File dir = new File(UPLOAD_DIR);
        String baseName = fileName;
        String extension = "";

        // Check if file has an extension
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0) {
            baseName = fileName.substring(0, dotIndex);
            extension = fileName.substring(dotIndex); // include the file extension
        }

        int counter = 1;
        File file = new File(dir, fileName);
        // Check if file already exists, if so, append a number to the file name
        while (file.exists()) {
            fileName = baseName + counter + extension;
            file = new File(dir, fileName);
            counter++;
        }

        return fileName;
    }


}