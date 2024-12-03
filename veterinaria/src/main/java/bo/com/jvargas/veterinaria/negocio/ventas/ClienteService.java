package bo.com.jvargas.veterinaria.negocio.ventas;

import bo.com.jvargas.veterinaria.datos.model.Cliente;
import bo.com.jvargas.veterinaria.datos.model.dto.ClienteDto;

import java.util.List;

public interface ClienteService {
    List<ClienteDto> lista();
    void registrar(Cliente cliente);
    void actualizar(Long id, Cliente cliente);
    void eliminar(Long id);
}
