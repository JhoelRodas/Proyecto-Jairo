package bo.com.jvargas.veterinaria.negocio.compra.impl;

import bo.com.jvargas.veterinaria.datos.model.Producto;
import bo.com.jvargas.veterinaria.datos.model.Proveedor;
import bo.com.jvargas.veterinaria.datos.model.dto.ProveedorDto;
import bo.com.jvargas.veterinaria.datos.repository.compra.ProveedorRepository;
import bo.com.jvargas.veterinaria.negocio.compra.ProveedorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service("ProveedorService")

public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository proveedorRepository;

    @Override
    public List<ProveedorDto> listar(){
        return proveedorRepository.lista();
    }

    @Override
    public void registrar(Proveedor proveedor){
        proveedorRepository.save(proveedor);
    }

    @Override
    @Transactional
    public void actualizar(Long id , Proveedor proveedor){
        Proveedor proveedorActualizado = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        proveedorActualizado.setNombre(proveedor.getNombre());
        proveedorActualizado.setTelefono(proveedor.getTelefono());
        proveedorActualizado.setCorreo(proveedor.getCorreo());
        proveedorActualizado.setDireccion(proveedor.getDireccion());
        proveedorActualizado.setEncargado(proveedor.getEncargado());
        proveedorRepository.save(proveedorActualizado);
    }

    @Override
    public void eliminar(Long id){
        Proveedor proveedorBuscado = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        proveedorBuscado.setDeleted(true);
        proveedorRepository.save(proveedorBuscado);
    }

}
