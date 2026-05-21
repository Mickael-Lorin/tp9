package fr.ekod.cda.ja.tp7.controller;



import fr.ekod.cda.ja.tp7.service.DirectorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DirectorWebController {

    private final DirectorService directorService;

    public DirectorWebController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping("/directors")
    public String directors(Model model){
        model.addAttribute("directors",
                directorService.findAll()
        );
        return "directors/list";

    }
}
