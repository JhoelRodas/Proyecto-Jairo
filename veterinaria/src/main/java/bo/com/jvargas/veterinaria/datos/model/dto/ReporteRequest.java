package bo.com.jvargas.veterinaria.datos.model.dto;

import java.util.List;
import java.util.Map;

public class ReporteRequest {
    private List<String> columnas;
    private Map<String, Object> filtros;
    private Orden orden;
    private String formato;

    // Getters y Setters

    public List<String> getColumnas() {
        return columnas;
    }

    public void setColumnas(List<String> columnas) {
        this.columnas = columnas;
    }

    public Map<String, Object> getFiltros() {
        return filtros;
    }

    public void setFiltros(Map<String, Object> filtros) {
        this.filtros = filtros;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    // Clase interna para Orden
    public static class Orden {
        private String columna;
        private String direccion;

        public String getColumna() {
            return columna;
        }

        public void setColumna(String columna) {
            this.columna = columna;
        }

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }
    }
}
