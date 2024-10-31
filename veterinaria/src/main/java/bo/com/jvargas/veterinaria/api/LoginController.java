package bo.com.jvargas.veterinaria.api;

import bo.com.jvargas.veterinaria.datos.model.dto.Login;
import bo.com.jvargas.veterinaria.negocio.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/login")
public class LoginController {
    private final LoginService loginService;

//    @PostMapping("/users")
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody Login login) {
        Login resp = loginService.login(login.getUsuario(), login.getPassword());
        if (resp == null) {
            return ResponseEntity.badRequest().body("Usuario o contrasenia inconrrectos");
        }
        return ResponseEntity.ok(resp);
    }
}
