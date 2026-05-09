package edu.esi.ds.esientradas.http;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import edu.esi.ds.esientradas.dao.EntradaDao;
import edu.esi.ds.esientradas.model.Entrada;
import edu.esi.ds.esientradas.services.PagosService;


@RestController
@RequestMapping("pagos")
@CrossOrigin(origins = "*")

public class PagosController {

   @Autowired
    private PagosService pagosService;

    @Autowired
    private EntradaDao entradaDao;

    @PostMapping("/preparar")
    public String prepararPago(@RequestBody Map<String, Object> infoPago) {

        Long entradaId = ((Number) infoPago.get("entradaId")).longValue();

        Entrada entrada = entradaDao.findById(entradaId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entrada no encontrada"));

        try {
            return pagosService.prepararPago(entrada.getPrecio());

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error preparando el pago: " + e.getMessage());
        }
    }
}
