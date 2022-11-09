import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
public class TaTeTi {
    private char [][] tablero;
    private final char J1= 'X';
    private final char J2= '0';
    private char jugador= J1;
    private conectaDB d = new conectaDB("localhost:3306/tateti","root","admin1");
    ResultSet miResulSet;
    Statement miStatement;
    int idioma;

    public TaTeTi (int idioma){
        this.idioma=idioma;
    }
    public void menu(TaTeTi jugar) {
        try {
            Connection miConexion = DriverManager.getConnection("jdbc:mysql://"+d.getConexion(),d.getUsuario(),d.getPassword());
            miStatement = miConexion.createStatement();
            miResulSet = miStatement.executeQuery("SELECT descripcion from mensajexidioma where (id_Mensaje=8 OR id_Mensaje=9 OR id_Mensaje=10) and  id_idioma ="+idioma+" ");
            int contador=1;
            while (miResulSet.next()){
                System.out.println(contador + "." + miResulSet.getString("descripcion"));
                contador++;
            }
            String gano;
            boolean condicion=true;
            while (condicion){
                Scanner l = new Scanner(System.in);
                int op= l.nextInt();
                if(op == 1){
                    Date inicio= new Date();
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    jugar.crearYllenarTablero();
                    jugar.mostrarTablero();
                    jugar.jugar();

                    if(jugar.haGanado('X')){
                        gano= "gano";
                    }else if(jugar.haGanado('0')){
                        gano= "perdio";
                    }else{
                        gano= "empato";
                    }

                    Date fin= new Date();

                    d.GuardaPartida(format, inicio, fin, gano);
                }
                if (op==2){
                   d.imprimirEstadisticas();
                }
                if (op==3){
                    condicion=false;
                } else {
                    miResulSet = miStatement.executeQuery("SELECT descripcion from mensajexidioma where id_Mensaje=14 and id_idioma = "+idioma+" ");
                    while (miResulSet.next()){
                        System.out.println(miResulSet.getString("descripcion"));
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
     public void mostrarTablero(){
         try {
             miResulSet = miStatement.executeQuery("SELECT descripcion from mensajexidioma where id_Mensaje=12 and id_idioma = "+idioma+" ");
             while (miResulSet.next()){
                 System.out.println(miResulSet.getString("descripcion") + " : X");
             }
             miResulSet = miStatement.executeQuery("SELECT descripcion from mensajexidioma where id_Mensaje=13 and id_idioma = "+idioma+" ");
             while (miResulSet.next()){
                 System.out.println(miResulSet.getString("descripcion") + " : 0");
             }

             for (int i = 0; i < tablero.length; i++) {

                 for (int j = 0; j < tablero.length; j++) {
                     System.out.print("|" + tablero[i][j]);

                 }
                 System.out.println("|");
             }
             System.out.println(" ");
         } catch (SQLException throwables) {
             throwables.printStackTrace();
         }
    }
    
    public char[][] crearYllenarTablero() {
        tablero = new char[3][3];
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                tablero[i][j] = '-';
            }
        }
        return tablero;
    }
    
    public void jugar(){
        try {
            boolean gano= false;
            while( !gano || !Empate()){

                jugador=J1;
                miResulSet = miStatement.executeQuery("SELECT descripcion from mensajexidioma where id_Mensaje=4 and id_idioma = "+idioma+" ");
                while (miResulSet.next()){
                    System.out.println(miResulSet.getString("descripcion") + jugador);
                }
                System.out.println(" ");
                movimientoJugador();
                mostrarTablero();
                if(haGanado(jugador)|| Empate()){
                    gano= true;
                    break;
                }
                jugador=J2;
                miResulSet = miStatement.executeQuery("SELECT descripcion from mensajexidioma where id_Mensaje=5 and id_idioma = "+idioma+" ");
                while (miResulSet.next()){
                    System.out.println(miResulSet.getString("descripcion") + jugador);
                }
                System.out.println(" ");
                movimientoComputadora();
                mostrarTablero();
                if(haGanado(jugador)|| Empate()){
                    gano= false;
                    break;
                }
            }
            if(haGanado(jugador)){
                mensaje(gano);
            }

            if (!haGanado(jugador) && Empate()) {
                miResulSet = miStatement.executeQuery("SELECT descripcion from mensajexidioma where id_Mensaje=11 and id_idioma = "+idioma+" ");
                while (miResulSet.next()){
                    System.out.println(miResulSet.getString("descripcion"));
                }
            }
            miResulSet = miStatement.executeQuery("SELECT descripcion from mensajexidioma where id_Mensaje=7 and id_idioma = "+idioma+" ");
            while (miResulSet.next()){
                System.out.println(miResulSet.getString("descripcion"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void movimientoJugador() {
        try {
        Scanner movimiento = new Scanner(System.in);
        int fila;
        int columna;
        boolean condicion = true;
        while (condicion) {
            miResulSet = miStatement.executeQuery("SELECT descripcion from mensajexidioma where id_Mensaje=15 and id_idioma = "+idioma+" ");
            while (miResulSet.next()){
                System.out.println(miResulSet.getString("descripcion"));
            }
            columna = movimiento.nextInt();
            if (columna <= 3 && columna > 0) {
                miResulSet = miStatement.executeQuery("SELECT descripcion from mensajexidioma where id_Mensaje=16 and id_idioma = "+idioma+" ");
                while (miResulSet.next()){
                    System.out.println(miResulSet.getString("descripcion"));
                }
                fila = movimiento.nextInt();
                if (fila <= 3 && fila > 0) {
                    fila = fila - 1;
                    columna = columna - 1;
                    boolean vacio = false;
                    for (int i = 0; i < tablero.length; i++) {
                        for (int j = 0; j < tablero.length; j++) {
                            if (tablero[i][j] == tablero[fila][columna]) {
                                if (tablero[i][j] == '-') {
                                    vacio = true;
                                }
                            }
                        }
                    }
                    if (vacio) {
                        for (int i = 0; i < tablero.length; i++) {
                            for (int j = 0; j < tablero.length; j++) {
                                if (tablero[i][j] == tablero[fila][columna]) {
                                    tablero[fila][columna] = J1;
                                }
                            }
                        }
                        condicion = false;
                    } else {
                        miResulSet = miStatement.executeQuery("SELECT descripcion from mensajexidioma where id_Mensaje=17 and id_idioma = "+idioma+" ");
                        while (miResulSet.next()){
                            System.out.println(miResulSet.getString("descripcion"));
                        }
                    }

                } else {
                    miResulSet = miStatement.executeQuery("SELECT descripcion from mensajexidioma where id_Mensaje=14 and id_idioma = "+idioma+" ");
                    while (miResulSet.next()){
                        System.out.println(miResulSet.getString("descripcion"));
                    }
                }
            } else {
                miResulSet = miStatement.executeQuery("SELECT descripcion from mensajexidioma where id_Mensaje=14 and id_idioma = "+idioma+" ");
                while (miResulSet.next()){
                    System.out.println(miResulSet.getString("descripcion"));
                }
            }
        }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    public void movimientoComputadora(){
        boolean condicion = true;
        while (condicion) {
            int fila = (int) (Math.random() * (3 - 1 + 1));
            int columna = (int) (Math.random() * (3 - 1 + 1) );
          
            boolean vacio = false;
            for (int i = 0; i < tablero.length; i++) {
                for (int j = 0; j < tablero.length; j++) {
                    if (tablero[i][j] == tablero[fila][columna]) {
                        if (tablero[i][j] == '-') {
                            vacio = true;
                        }
                    }
                }
            }
            if (vacio) {
                for (int i = 0; i < tablero.length; i++) {
                    for (int j = 0; j < tablero.length; j++) {
                        if (tablero[i][j] == tablero[fila][columna]) {
                            tablero[fila][columna] = J2;
                        }
                    }
                }
                condicion = false;
            }
        }
    }
    
    public void mensaje(boolean gano){
        try {
            if (gano){
                miResulSet = miStatement.executeQuery("SELECT descripcion from mensajexidioma where id_Mensaje=6 and id_idioma ="+idioma+"");
            } else {
                miResulSet = miStatement.executeQuery("SELECT descripcion from mensajexidioma where id_Mensaje=5 and id_idioma ="+idioma+"");
            }
            while (miResulSet.next()){
                System.out.println("descripcion");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean haGanado(char jugador ){
        // horizontales 
        if (tablero[0][0]== jugador && tablero[0][1]== jugador && tablero[0][2]== jugador) {
          
            return true;
        }
        else if (tablero[1][0]== jugador && tablero[1][1]== jugador && tablero[1][2]== jugador) {
           
            return true;
        }
       else if (tablero[2][0]== jugador && tablero[2][1]== jugador && tablero[2][2]== jugador) {
       
            return true;
        }
    
        //verticales
       else if (tablero[0][0]== jugador && tablero[1][0]== jugador && tablero[2][0]== jugador) {
       
            return true;
        }
        else if (tablero[0][1]== jugador && tablero[1][1]== jugador&& tablero[2][1]== jugador) {
           
            return true;
        }
       else if (tablero[0][2]== jugador && tablero[1][2]== jugador && tablero[2][2]== jugador) {
       
            return true;
        }
    
        //diagonales 
       else if (tablero[0][0]== jugador && tablero[1][1]== jugador && tablero[2][2]== jugador) {
       
            return true;
        }
       else if (tablero[0][2]== jugador && tablero[1][1]== jugador && tablero[2][0]== jugador) {
            return true;
        }
    else{
        return false;
    }
    
    }
    
    public boolean Empate() {
        int contador = 0;
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                if (tablero[i][j] == '-') {
                    contador++;
                }
            }
    
        }
        return contador == 0;
    }
    
    }