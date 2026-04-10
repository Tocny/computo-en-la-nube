package com.app.servicio_items;

/**
 * Clase que representa la entidad Auto en el sistema.
 * Esta clase modelo contiene los atributos básicos de un vehículo.
 */
public class Auto {
    
    // ATRIBUTOS
    private Long id;            // Identificador único del auto
    private String marca;       // Marca del vehículo
    private String modelo;      // Modelo específico del vehículo
    private String anio;        // Año de fabricación
    private Double precio;      // Precio del vehículo

    /**
     * Constructor vacio.
     */
    public Auto() {
    }

    /**
     * Constructor para crear una instancia de Auto.
     * 
     * @param id Identificador único del auto
     * @param marca Marca del vehículo
     * @param modelo Modelo específico del vehículo
     * @param anio Año de fabricación
     * @param precio Precio del vehículo
     */
    public Auto(Long id, String marca, String modelo, String anio, Double precio) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precio = precio;
    }

    /**
     * Getter del ID único del auto.
     * @return id del auto
     */
    public Long getId() { 
        return id; 
    }
    
    /**
     * Setter del ID único del auto.
     * @param id nuevo id para el auto
     */
    public void setId(Long id) { 
        this.id = id; 
    }

    /**
     * Obtiene la marca del auto.
     * @return marca del auto
     */
    public String getMarca() { 
        return marca; 
    }
    
    /**
     * Establece la marca del auto.
     * @param marca nueva marca para el auto
     */
    public void setMarca(String marca) { 
        this.marca = marca; 
    }

    /**
     * Getter del modelo del auto.
     * @return modelo del auto
     */
    public String getModelo() { 
        return modelo; 
    }
    
    /**
     * Setter del modelo del auto.
     * @param modelo nuevo modelo para el auto
     */
    public void setModelo(String modelo) { 
        this.modelo = modelo; 
    }

    /**
     * Getter del año del auto.
     * @return año del auto como String
     */
    public String getAnio() { 
        return anio; 
    }
    
    /**
     * Setter del año del auto.
     * @param anio nuevo año para el auto
     */
    public void setAnio(String anio) { 
        this.anio = anio; 
    }

    /**
     * Getter del precio del auto.
     * @return precio del auto
     */
    public Double getPrecio() { 
        return precio; 
    }
    
    /**
     * Setter del precio del auto.
     * @param precio nuevo precio para el auto
     */
    public void setPrecio(Double precio) { 
        this.precio = precio; 
    }

    /**
     * Representación en String del objeto Auto.
     * @return representación textual del auto
     */
    @Override
    public String toString() {
        return "Auto{" +
                "id=" + id +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", anio='" + anio + '\'' +
                ", precio=" + precio +
                '}';
    }

}