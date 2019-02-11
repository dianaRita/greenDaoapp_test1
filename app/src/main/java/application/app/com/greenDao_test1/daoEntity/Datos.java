package application.app.com.greenDao_test1.daoEntity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * Entidad que sirve como modelo para la persistencia de los datos.
 */
@Table(name = "datos", id = "_id")
public class Datos extends Model {
    /**
     * Atributo que representa la clase de datos para números enteros
     */
    @Column(name = "integer")
    private Integer integer;

    /**
     * Atributo que representa la clase de datos para números reales
     */
    @Column(name = "real")
    private Double real;

    /**
     * Atributo que representa la clase de datos para cadena textos
     */
    @Column(name = "text")
    private String text;

    /**
     * Atributo que representa la clase de datos para fechas
     */
    @Column(name = "numDate")
    private Date numDate;

    /**
     * Atributo que representa la clase de datos para buleanos
     */
    @Column(name = "numBool")
    private Boolean numBool;


    /**
     * Constructor por defecto
     */
    public Datos() {
    }

    /**
     * Constructor por parámetros. Debe recibir todos los atributos de la clase.
     * @param integer
     * @param real
     * @param text
     * @param numDate
     * @param numBool
     */
    public Datos(Integer integer, Double real, String text, Date numDate, Boolean numBool) {
        this.integer = integer;
        this.real = real;
        this.text = text;
        this.numDate = numDate;
        this.numBool = numBool;
    }

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public Double getReal() {
        return real;
    }

    public void setReal(Double real) {
        this.real = real;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getNumDate() {
        return numDate;
    }

    public void setNumDate(Date numDate) {
        this.numDate = numDate;
    }

    public Boolean getNumBool() {
        return numBool;
    }

    public void setNumBool(Boolean numBool) {
        this.numBool = numBool;
    }

    @Override
    public String toString() {
        return  integer +
                " " + real +
                " " + text + '\'' +
                " " + numDate +
                " " + numBool ;
    }
}
