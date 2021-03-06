package application.app.com.greenDao_test1;

import android.app.ActivityManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import application.app.com.greenDao_test1.daoEntity.DaoMaster;
import application.app.com.greenDao_test1.daoEntity.DaoSession;
import application.app.com.greenDao_test1.daoEntity.Datos;
import application.app.com.greenDao_test1.daoEntity.DatosDao;

/**
 * La clase {@link Operador} se utiliza para realizar las operaciones
 * de alta, baja, modificación y consulta de datos en la base de datos.
 * Esta clase tiene una relación de asociación simple con la clase {@link Datos},
 * los objetos de la clase Datos cumplen la funcion del DAO
 */
public class Operador {

    /**
     * {@link ActivityManager} para recuperar el contexto de la aplicación
     */
    private ActivityManager act;
    private DatosDao dao;

    /**
     * Constructor.
     * Recibe como parámetro un ActivitiManager
     * @param m {@link MainActivity}
     */
    public Operador(MainActivity m) {
        this.act = (ActivityManager) m.getSystemService(Context.ACTIVITY_SERVICE);

        // configuración de la base de datos
        String DB_NAME = "greendao_test.db";
        DaoMaster.DevOpenHelper masterHelper = new DaoMaster.DevOpenHelper(m.getApplication(), DB_NAME, null);

        SQLiteDatabase db = masterHelper.getWritableDatabase();
        DaoMaster master = new DaoMaster(db);
        DaoSession session = master.newSession();
        dao = session.getDatosDao();
    }

    /**
     * Realiza un análisis de la memoria utilizada por la aplicaci&oacute;n que es asignada por
     * ART (android runtime)
     * Devuelve un Map con los siguientes resultados:
     * <br>totMemAsig: la cantidad de memoria que ART asigna a la aplicaci&oacute;n (mb).
     * <br>memUsada: la cantidad de memoria utilizada por la aplicaci&oacute;n de la memoria asignada (mb).
     * <br>memLib: cantidad de memoria no utilizada de la memoria asignada a la aplicaci&oacute;n (mb).
     * <br>porcMemUsada:porcentaje de memoria utilizada de la parcela asignada.
     * <br>porcMemLib: porcentaje de memoria libre de la parcela asignada.
     * @return result {@link Map} Resultados
     */
    private Map<String,Double> getMemoryApp(){
        final Runtime rTime = Runtime.getRuntime();
        //double maxMemART = (double) rTime.maxMemory() / 1048576L;
        double totMemART =  (double) rTime.totalMemory() / 1048576L;
        double memUsada =  ( (double) rTime.totalMemory() - (double) rTime.freeMemory() ) / 1048576L; // 1048576L = (1024*1024)
        double memLib = (double) rTime.freeMemory() / 1048576L;
        double porcMemLib = ( memLib * 100 ) / totMemART;
        double porcMemUsada = ( memUsada * 100 ) / totMemART;
        DecimalFormat format = new DecimalFormat("#.##" );
        Map< String, Double > result = new HashMap<>();
        result.put("totMemAsig",Double.parseDouble( format.format( totMemART ) ) );
        result.put("memUsada",Double.parseDouble( format.format( memUsada ) ) );
        result.put("memLib",Double.parseDouble( format.format( memLib ) ) );
        result.put("porcMemLib",Double.parseDouble( format.format( porcMemLib ) ) );
        result.put("porcMemUsada",Double.parseDouble( format.format( porcMemUsada ) ) );
        return result;
    }

    /**
     * Realiza un análisis de la memoria del dispositivo.
     * Devuelve un Map con los siguientes resultados:
     * <br>memTotDevice: la cantidad total de memoria del dispositivo (mb).
     * <br>memLibDevice: la cantidad libre de memoria del dispositivo (mb).
     * <br>memUsadaDevice: la cantidad de memoria utilizada por todos los procesos en el dispositivo (mb).
     * <br>porcMemUsadaDevice: porcentaje de memoria utilizada por todos los procesos en el dispositivo.
     * <br>porcMemLibDevice: porcentaje de memoria libre en el dispositivo.
     * @return result {@link Map} Resultados
     */
    private Map< String, Double > getMemoryDevice(){
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = act;
        activityManager.getMemoryInfo(mi);
        double memTotDevice = (double) mi.totalMem / 0x100000L; // 0x100000L = (1024*1024)
        double availableMem = (double) mi.availMem / 0x100000L;
        double usedMem = ((double) mi.totalMem / 0x100000L) - ( (double) mi.availMem  / 0x100000L );
        double percentAvail = ( (double) mi.availMem * 100.0 ) / (double) mi.totalMem;
        double percetUsed = ( ( (double) mi.totalMem - (double) mi.availMem ) * 100.00) / (double) mi.totalMem;

        DecimalFormat format = new DecimalFormat("#.##");
        Map< String, Double > result = new HashMap<>();
        result.put("memTotDevice", Double.parseDouble( format.format( memTotDevice ) ) );
        result.put("memLibDevice", Double.parseDouble( format.format(availableMem ) ) );
        result.put("memUsadaDevice", Double.parseDouble( format.format( usedMem ) ) );
        result.put("porcMemUsadaDevice", Double.parseDouble( format.format( percetUsed ) ) );
        result.put("porcMemLibDevice", Double.parseDouble( format.format( percentAvail ) ) );

        return result;
    }


    /**
     * Se utiliza para generar un objeto de tipo {@link Datos} con valores de atributos aleatorios.
     * @return dt {@link Datos} Objeto de tipo {@link Datos} con valores de atributos aleatorios
     */
    private Datos genDatos(){
        Random r = new Random(System.currentTimeMillis());
        long x = r.nextLong();
        int i = 1+r.nextInt(1000000);
        double d = 1+r.nextDouble();
        String t = new BigInteger(130,r).toString(36);
        Date f  = new Date((x<0)?(x/6000000)*-1:x/6000000) ;
        boolean b = ( ( i%2 ) == 0 ) ? true : false;

        Datos dt = new Datos( i, d, t, f, b );
        return dt;
    }

    /**
     * Realiza inserciones emplenado valores aleatorios para cada registro.
     * Utiliza el método genDatos para generar los datos aleatorios.
     * Devuelve un map con resultados de los an&aacute;lisis de tiempo y memoria
     * <br>Para el tiempo:
     * <br>tiempo: duraci&oacute;n de la ejecuci&oacute;n de la operaci&oacute;n (ms).
     * <br>ultId: ultima id insertada en la db.
     * <br>Para la memoria:
     * <br>memAsigApp: la cantidad de memoria que ART asigna a la aplicaci&oacute;n (mb).
     * <br>memUsadaApp: la cantidad de memoria utilizada por la aplicaci&oacute;n de la memoria asignada (mb).
     * <br>porcMemUsadaApp: porcentaje de memoria utilizada de la parcela asignada.
     * <br>memTotDevice: la cantidad total de memoria del dispositivo (mb).
     * <br>memUsadaDevice: la cantidad de memoria utilizada por todos los procesos en el dispositivo (mb).
     * <br>porcMemUsadaDevice:  porcentaje de memoria utilizada por todos los procesos en el dispositivo.
     * @param c {@link Integer} cantidad de datos a insertar.
     * @return rstl {@link Map<String, String>} Resultados.
     */
    public Map<String,String> insertaAleatorio(Integer c){
        Map<String,Double> memApp = null;
        Map<String,Double> memDev = null;
        long id = -1;

        long ini = System.currentTimeMillis();
        for (int i = 0; i < c; i++) {
            if (i==(c-1)) {
                memApp = getMemoryApp();
                memDev = getMemoryDevice();
            }
            Datos d = genDatos();
            dao.insert(d);
            id = d.getId();
        }
        long fin = System.currentTimeMillis();

        Map<String,String> rslt = new HashMap<>();
        rslt.put("tiempo", (fin-ini) + "ms");
        rslt.put("ultId", id+"");
        rslt.put("memAsigApp", memApp.get("totMemAsig") + "mb");
        rslt.put("memUsadaApp", memApp.get("memUsada") + "mb");
        rslt.put("porcMemUsadaApp", memApp.get("porcMemUsada") + "%");
        rslt.put("memTotDevice", memDev.get("memTotDevice") + "mb");
        rslt.put("memUsadaDevice", memDev.get("memUsadaDevice") + "mb");
        rslt.put("porcMemUsadaDevice", memDev.get("porcMemUsadaDevice") + "%");

        return rslt;
    }

    /**
     * Realiza inserciones emplenado los mismos valores para todos los registros.
     * Devuelve un map con resultados de los an&aacute;lisis de tiempo y memoria
     * <br>Para el tiempo:
     * <br>tiempo: duraci&oacute;n de la ejecuci&oacute;n de la operaci&oacute;n (ms).
     * <br>ultId: ultima id insertada en la db.
     * <br>Para la memoria:
     * <br>memAsigApp: la cantidad de memoria que ART asigna a la aplicaci&oacute;n (mb).
     * <br>memUsadaApp: la cantidad de memoria utilizada por la aplicaci&oacute;n de la memoria asignada (mb).
     * <br>porcMemUsadaApp: porcentaje de memoria utilizada de la parcela asignada.
     * <br>memTotDevice: la cantidad total de memoria del dispositivo (mb).
     * <br>memUsadaDevice: la cantidad de memoria utilizada por todos los procesos en el dispositivo (mb).
     * <br>porcMemUsadaDevice:  porcentaje de memoria utilizada por todos los procesos en el dispositivo.
     * @param c {@link Integer} cantidad de datos a insertar.
     * @return rstl {@link Map<String, String>} Resultados.
     */
    public Map<String,String> inserta(Integer c){

        Map<String,Double> memApp = null;
        Map<String,Double> memDev = null;
        Datos d = genDatos();
        long id = -1;

        long ini = System.currentTimeMillis();
        for (int i = 0; i < c; i++) {
            if (i==(c-1)) {
                memApp = getMemoryApp();
                memDev = getMemoryDevice();
            }
            Datos datoIns= new Datos();
            datoIns.setInteger(d.getInteger());
            datoIns.setReal(d.getReal());
            datoIns.setText(d.getText());
            datoIns.setNumDate(d.getNumDate());
            datoIns.setNumBool(d.getNumBool());
            dao.insert(datoIns);
            id = datoIns.getId();
        }
        long fin = System.currentTimeMillis();

        Map<String,String> rslt = new HashMap<>();
        rslt.put("tiempo", (fin-ini) + "ms");
        rslt.put("ultId", id+"");
        rslt.put("memAsigApp", memApp.get("totMemAsig") + "mb");
        rslt.put("memUsadaApp", memApp.get("memUsada") + "mb");
        rslt.put("porcMemUsadaApp", memApp.get("porcMemUsada") + "%");
        rslt.put("memTotDevice", memDev.get("memTotDevice") + "mb");
        rslt.put("memUsadaDevice", memDev.get("memUsadaDevice") + "mb");
        rslt.put("porcMemUsadaDevice", memDev.get("porcMemUsadaDevice") + "%");

        return rslt;
    }

    /**
     * Realiza una consulta de todos los datos en la base de datos.
     * Devuelve un map con resultados de la consulta y del an&aacute;lisis de tiempo y memoria
     * <br>Para el tiempo:
     * <br>tiempo: duraci&oacute;n de la ejecuci&oacute;n de la operaci&oacute;n (ms).
     * <br>cantCon: cantidad de registros recuperados.
     * <br>Para la memoria:
     * <br>memAsigApp: la cantidad de memoria que ART asigna a la aplicaci&oacute;n (mb).
     * <br>memUsadaApp: la cantidad de memoria utilizada por la aplicaci&oacute;n de la memoria asignada (mb).
     * <br>porcMemUsadaApp: porcentaje de memoria utilizada de la parcela asignada.
     * <br>memTotDevice: la cantidad total de memoria del dispositivo (mb).
     * <br>memUsadaDevice: la cantidad de memoria utilizada por todos los procesos en el dispositivo (mb).
     * <br>porcMemUsadaDevice:  porcentaje de memoria utilizada por todos los procesos en el dispositivo.
     * <br>datos:  string con los registros recuperados.
     * @return rstl {@link Map<String, String>} Resultados.
     */
    public Map<String,String> consulta(){

        Map<String,Double> memApp = null;
        Map<String,Double> memDev = null;

        List<Datos> dts = null;
        long ini = 0, fin = 0;

        ini = System.currentTimeMillis();
        while ( dts == null ) {
            dts = dao.loadAll();
            memApp = getMemoryApp();
            memDev = getMemoryDevice();
        }
        fin = System.currentTimeMillis();

        StringBuilder stDts = new StringBuilder("");
        for ( Datos d: dts ) {
            stDts.append("{ id: ").append(d.getId())
                 .append(", integer: ").append(d.getInteger())
                 .append(", real: ").append(d.getReal())
                 .append(", date: ").append(d.getNumDate())
                 .append(", boolean: ").append(d.getNumBool())
                 .append(" }\n");
        }

        Map<String,String> rslt = new HashMap<>();
        rslt.put("tiempo", (fin-ini) + "ms");
        rslt.put("cantCon", dts.size()+"");
        rslt.put("memAsigApp", memApp.get("totMemAsig") + "mb");
        rslt.put("memUsadaApp", memApp.get("memUsada") + "mb");
        rslt.put("porcMemUsadaApp", memApp.get("porcMemUsada") + "%");
        rslt.put("memTotDevice", memDev.get("memTotDevice") + "mb");
        rslt.put("memUsadaDevice", memDev.get("memUsadaDevice") + "mb");
        rslt.put("porcMemUsadaDevice", memDev.get("porcMemUsadaDevice") + "%");
        rslt.put("datos", stDts.toString());

        return rslt;
    }

    /**
     * Actualiza los todos los registros de la base utilizando valores aleatorios para cada registro.
     * Emplea el método genDatos para generar los valores aleatorios.
     * Devuelve un map con resultados de los an&aacute;lisis de tiempo y memoria
     * <br>Para el tiempo:
     * <br>tiempo: duraci&oacute;n de la ejecuci&oacute;n de la operaci&oacute;n (ms).
     * <br>ultId: ultima id insertada en la db.
     * <br>Para la memoria:
     * <br>memAsigApp: la cantidad de memoria que ART asigna a la aplicaci&oacute;n (mb).
     * <br>memUsadaApp: la cantidad de memoria utilizada por la aplicaci&oacute;n de la memoria asignada (mb).
     * <br>porcMemUsadaApp: porcentaje de memoria utilizada de la parcela asignada.
     * <br>memTotDevice: la cantidad total de memoria del dispositivo (mb).
     * <br>memUsadaDevice: la cantidad de memoria utilizada por todos los procesos en el dispositivo (mb).
     * <br>porcMemUsadaDevice:  porcentaje de memoria utilizada por todos los procesos en el dispositivo.
     * @return rstl {@link Map<String, String>} Resultados.
     */
    public Map< String, String > actualizaAleatorio(){
        List<Datos> datos = null;
        Map<String,Double> memApp = null;
        Map<String,Double> memDev = null;
        datos = dao.loadAll();
        int n = datos.size();
        long up = -1;

        long ini = System.currentTimeMillis();
        for ( int i = 0 ; i < n; i++) {
            if ( i == ( n - 1) ) {
                memApp = getMemoryApp();
                memDev = getMemoryDevice();
            }
            Datos newD = genDatos();
            Datos oldD = datos.get( i );
            oldD.setInteger( newD.getInteger() ); oldD.setReal( newD.getReal() );
            oldD.setText( newD.getText() ); oldD.setNumDate( newD.getNumDate() );
            oldD.setNumBool( newD.getNumBool() );
            dao.update(oldD);
            up = oldD.getId();
        }
        long fin = System.currentTimeMillis();

        Map<String,String> rslt = new HashMap<>();
        rslt.put("tiempo", (fin-ini) + "ms");
        rslt.put("ultId", up+"");
        rslt.put("memAsigApp", memApp.get("totMemAsig") + "mb");
        rslt.put("memUsadaApp", memApp.get("memUsada") + "mb");
        rslt.put("porcMemUsadaApp", memApp.get("porcMemUsada") + "%");
        rslt.put("memTotDevice", memDev.get("memTotDevice") + "mb");
        rslt.put("memUsadaDevice", memDev.get("memUsadaDevice") + "mb");
        rslt.put("porcMemUsadaDevice", memDev.get("porcMemUsadaDevice") + "%");

        return rslt;
    }

    /**
     * Actualiza los todos los registros de la base de datos utilizando los mismos valores para todos los registros.
     * Devuelve un map con resultados de los an&aacute;lisis de tiempo y memoria
     * <br>Para el tiempo:
     * <br>tiempo: duraci&oacute;n de la ejecuci&oacute;n de la operaci&oacute;n (ms).
     * <br>ultId: ultima id insertada en la db.
     * <br>Para la memoria:
     * <br>memAsigApp: la cantidad de memoria que ART asigna a la aplicaci&oacute;n (mb).
     * <br>memUsadaApp: la cantidad de memoria utilizada por la aplicaci&oacute;n de la memoria asignada (mb).
     * <br>porcMemUsadaApp: porcentaje de memoria utilizada de la parcela asignada.
     * <br>memTotDevice: la cantidad total de memoria del dispositivo (mb).
     * <br>memUsadaDevice: la cantidad de memoria utilizada por todos los procesos en el dispositivo (mb).
     * <br>porcMemUsadaDevice:  porcentaje de memoria utilizada por todos los procesos en el dispositivo.
     * @return rstl {@link Map<String, String>} Resultados.
     */
    public Map< String, String > actualiza(){

        List<Datos> datos = null;
        Map<String,Double> memApp = null;
        Map<String,Double> memDev = null;
        datos = dao.loadAll();
        long up = -1;
        int n = datos.size();
        Datos d = genDatos();

        long ini = System.currentTimeMillis();
        for ( int i = 0 ; i < n; i++) {
            if ( i == ( n - 1 ) ) {
                memApp = getMemoryApp();
                memDev = getMemoryDevice();
            }

            Datos oldD = datos.get( i );
            oldD.setInteger(d.getInteger());
            oldD.setReal(d.getReal());
            oldD.setText(d.getText());
            oldD.setNumDate(d.getNumDate());
            oldD.setNumBool(d.getNumBool());
            dao.update(oldD);
            up = oldD.getId();
        }
        long fin = System.currentTimeMillis();

        Map<String,String> rslt = new HashMap<>();
        rslt.put("tiempo", (fin-ini) + "ms");
        rslt.put("ultId", up+"");
        rslt.put("memAsigApp", memApp.get("totMemAsig") + "mb");
        rslt.put("memUsadaApp", memApp.get("memUsada") + "mb");
        rslt.put("porcMemUsadaApp", memApp.get("porcMemUsada") + "%");
        rslt.put("memTotDevice", memDev.get("memTotDevice") + "mb");
        rslt.put("memUsadaDevice", memDev.get("memUsadaDevice") + "mb");
        rslt.put("porcMemUsadaDevice", memDev.get("porcMemUsadaDevice") + "%");

        return rslt;
    }

    /**
     * Elimina todos los datos de la db.
     * Devuelve un map con resultados de los an&aacute;lisis de tiempo y memoria
     * <br>Para el tiempo:
     * <br>tiempo: duraci&oacute;n de la ejecuci&oacute;n de la operaci&oacute;n (ms).
     * <br>cantElim: Cantidad de registros eliminados.
     * <br>Para la memoria:
     * <br>memAsigApp: la cantidad de memoria que ART asigna a la aplicaci&oacute;n (mb).
     * <br>memUsadaApp: la cantidad de memoria utilizada por la aplicaci&oacute;n de la memoria asignada (mb).
     * <br>porcMemUsadaApp: porcentaje de memoria utilizada de la parcela asignada.
     * <br>memTotDevice: la cantidad total de memoria del dispositivo (mb).
     * <br>memUsadaDevice: la cantidad de memoria utilizada por todos los procesos en el dispositivo (mb).
     * <br>porcMemUsadaDevice:  porcentaje de memoria utilizada por todos los procesos en el dispositivo.
     * @return rstl {@link Map<String, String>} Resultados.
     */
    public Map< String, String > elimina(){
        Map<String, String> rslt = new HashMap<>();
        Map<String, Double> memApp = null;
        Map<String, Double> memDev = null;
        long ini = 0, fin = 0;
        long c = getCant();
        boolean x = true;
        ini = System.currentTimeMillis();
        while ( x ) {
            dao.deleteAll();
            memApp = getMemoryApp();
            memDev = getMemoryDevice();
            x = false;
        }
        fin = System.currentTimeMillis();

        rslt.put("tiempo", ( fin -ini) + "ms");
        rslt.put("cantElim", c + "");
        rslt.put("memAsigApp", memApp.get("totMemAsig") + "mb");
        rslt.put("memUsadaApp", memApp.get("memUsada") + "mb");
        rslt.put("porcMemUsadaApp", memApp.get("porcMemUsada") + "%");
        rslt.put("memTotDevice", memDev.get("memTotDevice") + "mb");
        rslt.put("memUsadaDevice", memDev.get("memUsadaDevice") + "mb");
        rslt.put("porcMemUsadaDevice", memDev.get("porcMemUsadaDevice") + "%");

        return rslt;
    }

    /**
     * Recuper la cantidad total de regitros almacenados en la base de datos.
     * @return long Cantidad de registros en la base de datos.
     */
    public long getCant(){
        return dao.count();
    }

}
