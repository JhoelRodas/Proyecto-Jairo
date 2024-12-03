package bo.com.jvargas.veterinaria.negocio.ventas.impl;

import bo.com.jvargas.veterinaria.datos.model.Cliente;
import bo.com.jvargas.veterinaria.datos.model.dto.ClienteDto;
import bo.com.jvargas.veterinaria.datos.model.sistema.AuthRole;
import bo.com.jvargas.veterinaria.datos.model.sistema.AuthUser;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.UserDto;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.TipoProceso;
import bo.com.jvargas.veterinaria.datos.repository.ventas.ClienteRepository;
import bo.com.jvargas.veterinaria.datos.repository.sistema.AuthRoleRepository;
import bo.com.jvargas.veterinaria.negocio.admusuarios.BitacoraService;
import bo.com.jvargas.veterinaria.negocio.admusuarios.UserService;
import bo.com.jvargas.veterinaria.negocio.ventas.ClienteService;
import bo.com.jvargas.veterinaria.utils.OperationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service("ClienteService")
public class ClienteServicelmpl implements ClienteService {
    private final ClienteRepository clienteRepository;
    private final UserService userService;
    private final AuthRoleRepository authRoleRepository;
    private final BitacoraService bitacoraService;


    @Override
    public List<ClienteDto> lista() {
        return clienteRepository.listar();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void registrar(Cliente cliente) {
        //log.error("registrar cliente");
        AuthRole rolCliente = authRoleRepository.findByName("ROL_CLIENTE").orElseThrow(() -> new OperationException("No existe rol"));

        // Poniendo los datos del Cliente a su cuenta de  Usuario
        //cuando se crea un cliente tambien se le crea su Usuario
        UserDto userDto = new UserDto();
        userDto.setIdAuthRole(rolCliente.getId());
        userDto.setUsername(cliente.getCi());
        userDto.setName(cliente.getNombre());
        userDto.setLastname("");
        userDto.setEmail(cliente.getCorreo());
        userDto.setTelefono(cliente.getTelefono());

        AuthUser user = userService.createUser(userDto);
        cliente.setIdAuthUser(user);

        clienteRepository.save(cliente);


        bitacoraService.info(TipoProceso.GESTIONAR_CLIENTE, "Cliente registrado correctamente: {}", cliente.getCi());
    }

    @Override
    public void actualizar(Long id, Cliente cliente) {
        log.error("Actualizo");
        Cliente clienteBuscado = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        clienteBuscado.setCi(cliente.getCi());
        clienteBuscado.setExtension(cliente.getExtension());
        clienteBuscado.setNombre(cliente.getNombre());
        clienteBuscado.setTelefono(cliente.getTelefono());
        clienteBuscado.setCorreo(cliente.getCorreo());
        clienteBuscado.setDireccion(cliente.getDireccion());

        clienteRepository.save(clienteBuscado);
    }

    @Override
    public void eliminar(Long id) {
        Cliente cliente = getClientePorId(id);
        cliente.setDeleted(true);
        clienteRepository.save(cliente);
    }

    private Cliente getClientePorId(Long id) {
        Optional<Cliente> o = clienteRepository.findByIdAndDeletedFalse(id);

        if (o.isEmpty())
            throw new IllegalArgumentException("Cliente no encontrado");

        return o.get();
    }
}
