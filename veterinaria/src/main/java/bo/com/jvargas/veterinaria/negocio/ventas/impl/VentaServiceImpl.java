package bo.com.jvargas.veterinaria.negocio.ventas.impl;

import bo.com.jvargas.veterinaria.datos.model.Recibo;
import bo.com.jvargas.veterinaria.datos.repository.ventas.ReciboRepository;
import bo.com.jvargas.veterinaria.negocio.ventas.VentaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service("VentaService")
public class VentaServiceImpl implements VentaService {
    private final ReciboRepository reciboRepository;


    @Override
    public void agregar(Recibo recibo) {

        reciboRepository.save(recibo);
    }
}
