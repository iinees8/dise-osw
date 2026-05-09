package edu.esi.ds.esientradas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import edu.esi.ds.esientradas.dao.EntradaDao;
import edu.esi.ds.esientradas.dao.TokenDao;
import edu.esi.ds.esientradas.model.Entrada;
import edu.esi.ds.esientradas.model.Estado;
import edu.esi.ds.esientradas.model.Token;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class ReservasService {

    @Autowired
    private EntradaDao dao;

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private UsuariosService usuariosService;

    @Autowired
    private PagosService pagosService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ColaService colaService;

    

    @Transactional
public String reservar(Long idEntrada, HttpSession session) {
    
    Entrada entrada = dao.findById(idEntrada)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Entrada no encontrada"
        ));

    // 🔒 comprobar disponible
    if(entrada.getEstado() != Estado.DISPONIBLE) {

        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Entrada no disponible"
        );
    }

    // 🔥 crear token
    Token token = new Token();

    token.setSessionId(session.getId());

    token.setEntrada(entrada);

    tokenDao.save(token);

    // 🔥 cambiar estado
    entrada.setEstado(Estado.RESERVADA);

    dao.save(entrada);

    // 🔥 devolver token
    return token.getValor();
}

    @Transactional
public String comprar(String tokenEntrada, String tokenUsuario) {

    Token token = tokenDao.findByValor(tokenEntrada);

    if(token == null){

        throw new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Token no encontrado"
        );
    }

    long ahora = System.currentTimeMillis();

    if(ahora - token.getHora() > 300000) {

        Entrada entrada = token.getEntrada();

        entrada.setEstado(Estado.DISPONIBLE);

        dao.save(entrada);

        tokenDao.delete(token);

        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Reserva caducada"
        );
    }

    // 🔐 VALIDAR LOGIN
    String usuario = usuariosService.checkToken(tokenUsuario);

    if(usuario == null){

        throw new ResponseStatusException(
            HttpStatus.UNAUTHORIZED,
            "Debes iniciar sesión"
        );
    }

    // 🔥 vender entrada
    Entrada entrada = token.getEntrada();

    entrada.setEstado(Estado.VENDIDA);

    dao.save(entrada);

    tokenDao.delete(token);

    return "Compra realizada";
}

    @Async
    public void liberarEntradaSiNoSeCompra(Long idEntrada) {

        try {
            Thread.sleep(120000); // 2 minutos
        } catch (InterruptedException e) {}

        Entrada entrada = dao.findById(idEntrada).orElse(null);

        if (entrada != null && entrada.getEstado() == Estado.RESERVADA) {
            entrada.setEstado(Estado.DISPONIBLE);
            dao.save(entrada);
            System.out.println("⏰ Entrada liberada automáticamente");
        }
    }

}
