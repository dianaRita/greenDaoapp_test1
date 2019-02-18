package application.app.com.greenDao_test1.daoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Entidad que sirve como modelo para la persistencia de los datos.
 */
@Entity
public class Datos {

    @Id(autoincrement = true)
    private Long id;

    /**
     * Atributo que representa la clase de datos para números enteros
     */
    private Integer integer;

    /**
     * Atributo que representa la clase de datos para números reales
     */
    private Double real;

    /**
     * Atributo que representa la clase de datos para cadena textos
     */
    private String text;

    /**
     * Atributo que representa la clase de datos para fechas
     */
    private Date numDate;

    /**
     * Atributo que representa la clase de datos para buleanos
     */
    private Boolean numBool;


    @Generated(hash = 474959832)
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

    @Generated(hash = 1776851259)
    public Datos(Long id, Integer integer, Double real, String text, Date numDate,
            Boolean numBool) {
        this.id = id;
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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
