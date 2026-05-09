package edu.esi.ds.esientradas.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import edu.esi.ds.esientradas.services.ReservasService;
import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/reservas")
@CrossOrigin(origins = "*")
public class ReservasController {

    @Autowired
    private ReservasService service;

    @PutMapping("/reservar")
    public String reservar(
    @RequestParam Long idEntrada,
    HttpSession session
) {

    return service.reservar(idEntrada, session);
}
}
