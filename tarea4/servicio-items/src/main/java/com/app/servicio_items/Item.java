package com.app.servicio_items;

/**
 * Clase que representa un item de compra o pedido.
 */
public class Item {
    
    private Auto auto;          //Auto asociado al item
    private Integer cantidad;   //Cantidad solicitada.

    /**
     * Constructor vacío para la deserialización en JSON.
     */
    public Item() {}

    /**
     * Constructor para crear un item.
     * 
     * @param auto El auto asociado al item
     * @param cantidad La cantidad de unidades.
     */
    public Item(Auto auto, Integer cantidad) {
        this.auto = auto;
        this.cantidad = cantidad;
    }

    /**
     * Calcula el precio total de este item.
     * 
     * @return El precio calculado
     */
    public Double getTotal() {
        return auto.getPrecio() * cantidad.doubleValue();
    }

    /**
     * Getter del auto asociado a este item.
     * @return El objeto Auto del item
     */
    public Auto getAuto() { 
        return auto; 
    }
    
    /**
     * Setter del auto asociado a este item.
     * @param auto El nuevo auto para este item
     */
    public void setAuto(Auto auto) { 
        this.auto = auto; 
    }
    
    /**
     * Getter de la cantidad de unidades de este item.
     * @return La cantidad actual
     */
    public Integer getCantidad() { 
        return cantidad; 
    }
    
    /**
     * Establece la cantidad de unidades de este item.
     * @param cantidad La nueva cantidad (debe ser positiva)
     */
    public void setCantidad(Integer cantidad) { 
        this.cantidad = cantidad; 
    }

    /**
     * Representación en String del objeto Item.
     * 
     * @return Representación textual del item
     */
    @Override
    public String toString() {
        return "Item{" +
                "auto=" + (auto != null ? auto.getMarca() + " " + auto.getModelo() : "null") +
                ", cantidad=" + cantidad +
                ", total=" + (auto != null ? getTotal() : "N/A") +
                '}';
    }
}