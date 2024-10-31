package bo.com.jvargas.veterinaria.datos.model.sistema.enums;

public enum ResourceType {
    item("item"),
    subheading("subheading");
    private final String type;
    ResourceType(String type){
        this.type = type;
    }
}
