package bo.com.jvargas.veterinaria.datos.model.sistema.enums;

public enum ApplicationType {
    WEB("WEB"),
    MOVIL("MOVIL");
    private String tipo;
    ApplicationType(String tipo){
        this.tipo = tipo;
    }
}
