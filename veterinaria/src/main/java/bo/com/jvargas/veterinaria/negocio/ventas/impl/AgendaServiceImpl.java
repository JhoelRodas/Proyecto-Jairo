package bo.com.jvargas.veterinaria.negocio.ventas.impl;

import bo.com.jvargas.veterinaria.datos.model.Agenda;
import bo.com.jvargas.veterinaria.datos.model.Cliente;
import bo.com.jvargas.veterinaria.datos.model.dto.AgendaDto;
import bo.com.jvargas.veterinaria.datos.repository.ventas.ClienteRepository;
import bo.com.jvargas.veterinaria.datos.repository.ventas.AgendaRepository;
import bo.com.jvargas.veterinaria.negocio.ventas.ClienteService;
import bo.com.jvargas.veterinaria.negocio.ventas.AgendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author GERSON
 */

@RequiredArgsConstructor
@Service("AgendaController")
public class AgendaServiceImpl implements AgendaService {

    private final AgendaRepository agendaRepository;
    private final ClienteRepository clienteRepository;
    private final ClienteService clienteService;

    @Override
    @Transactional(readOnly = true)
    public List<AgendaDto> listar() {
        return agendaRepository.findAllByDeletedFalse().stream()
                .map(AgendaDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void registrar(AgendaDto agenda) {
        Agenda agendaAGuardar = AgendaDto.toEntity(agenda);
        Cliente cliente = getCliente(agenda);

        agendaAGuardar.setIdCliente(cliente);
        agendaRepository.save(agendaAGuardar);
    }

    private Cliente getCliente(AgendaDto agendaNueva) {
        String ciCliente = agendaNueva.getCi();
        Optional<Cliente> o = clienteRepository
                .findByCiAndDeletedFalse(ciCliente);

        if (o.isPresent())
            return o.get();

        Cliente cliente = new Cliente(null,
                agendaNueva.getCi(), agendaNueva.getExtension(),
                agendaNueva.getCliente(), agendaNueva.getTelefono(),
                null, null, null);
        clienteService.registrar(cliente);
        return clienteRepository.findByCiAndDeletedFalse(ciCliente)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Error al registrar al cliente"
                ));
    }

    @Override
    @Transactional
    public void actualizar(Long id, AgendaDto agendaNueva) {
        Agenda agendaActual = getAgenda(id);
        actualizarDatos(agendaActual, agendaNueva);
        agendaRepository.save(agendaActual);
    }

    private Agenda getAgenda(Long id) {
        Optional<Agenda> o = agendaRepository.findByIdAndDeletedFalse(id);
        if (o.isEmpty())
            throw new IllegalArgumentException("Agenda con ID " + id +
                    " no existe");

        return o.get();
    }

    private void actualizarDatos(Agenda agendaActual, AgendaDto agendaNueva) {
        agendaActual.setFecha(agendaNueva.getFecha());
        agendaActual.setHora(agendaNueva.getHora());
        agendaActual.setDescripcion(agendaNueva.getDescripcion());
        agendaActual.setEstado(agendaNueva.getEstado());
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Agenda agendaBuscado = getAgenda(id);
        agendaBuscado.setDeleted(true);
        agendaRepository.save(agendaBuscado);
    }
}
