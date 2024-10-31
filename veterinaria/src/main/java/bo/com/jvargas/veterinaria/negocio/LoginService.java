package bo.com.jvargas.veterinaria.negocio;

import bo.com.jvargas.veterinaria.datos.model.dto.Login;

public interface LoginService {
    Login login(String usuario, String clave);
}
