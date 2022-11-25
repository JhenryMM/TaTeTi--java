import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TaTeTi {
    private char[][] tablero;
    private final char J1 = 'X';
    private final char J2 = '0';
    private char jugador = J1;
    private final conectaDB d = new conectaDB("localhost:3306/tateti", "root", "admin1");
    Statement miStatement;
    int idioma = 2; // idioma por defecto 2 (español) para mostrar la tabla de idiomas
    String nombreJ;
    boolean seregistro; // boolean para que solo se pueda pedir 1 vez el nombre al usuario que corre el programa
    static String nombreJugador;
    boolean nosalir = true;

    //Imprime la tabla de idiomas para que el usuario escojan en que idioma jugar
    public void tabladeIdiomas() {
        while (nosalir) {
            System.out.println(d.mensajexIdioma(idioma, 33) + ": ");
            System.out.println("1.Ingles\n2.Español\n3.Portugues");
            Scanner dato = new Scanner(System.in);
            //try catch para evitar la excepcion en caso de que el usuario ingrese una letra en lugar de un numero
            try {
                int miIdioma = dato.nextInt();
                if (miIdioma > 0 && miIdioma < 4) {
                    idioma = miIdioma;
                    menu();

                } else {
                    System.out.println(d.mensajexIdioma(idioma, 14));
                    System.out.println();
                }
            } catch (InputMismatchException e) {
                System.out.println(d.mensajexIdioma(idioma, 14));
                System.out.println();
            }
        }
    }

    public void menu() {
        int op;
        try {
            Connection miConexion = DriverManager.getConnection("jdbc:mysql://" + d.getConexion(), d.getUsuario(), d.getPassword());
            miStatement = miConexion.createStatement();

            //se verifica que no se haya registrado el nombre del jugador
            if (!seregistro) {
                // ingresa el nombre del jugador
                System.out.println(d.mensajexIdioma(idioma, 2));
                Scanner n = new Scanner(System.in);
                nombreJugador = n.nextLine();
                nombreJ = nombreJugador;
                //Se guarda el jugador en la base de datos al comienzo
                String instruccionSQL = "INSERT INTO jugador(nombre) VALUES ('" + nombreJ + "')";
                miStatement.executeUpdate(instruccionSQL);
            }

            int gano;
            boolean condicion = true;
            while (condicion) {
                // menu del juego
                System.out.println("          " + d.mensajexIdioma(idioma, 1) + "          ");
                System.out.println("1." + d.mensajexIdioma(idioma, 8));
                System.out.println("2." + d.mensajexIdioma(idioma, 9));
                System.out.println("3." + d.mensajexIdioma(idioma, 10));
                System.out.println("4." + d.mensajexIdioma(idioma, 32));
                System.out.println("5." + d.mensajexIdioma(idioma, 28));
                Scanner l = new Scanner(System.in);
                try {
                    op = l.nextInt();
                    if (op == 1) {
                        Date inicio = new Date();
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        crearYllenarTablero();
                        mostrarTablero();
                        jugar();

                        if (haGanado('X')) {
                            gano = 1;
                        } else if (haGanado('0')) {
                            gano = 0;
                        } else {
                            gano = 2;
                        }

                        Date fin = new Date();

                        d.GuardaPartida(format, inicio, fin, gano, nombreJugador, idioma);
                    }
                    if (op == 2) {
                        d.imprimirEstadisticas(idioma, null);
                    }
                    if (op == 3) {
                        d.imprimirEstadisticas(idioma, nombreJ);
                    }
                    if (op == 4) {

                        seregistro = true;
                        tabladeIdiomas();
                    }
                    if (op == 5) {
                        nosalir = false;
                        condicion = false;

                    } else {
                        System.out.println(d.mensajexIdioma(idioma, 31));
                        System.out.println();
                    }
                } catch (InputMismatchException e) {
                    System.out.println(d.mensajexIdioma(idioma, 31));
                    System.out.println();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public void mostrarTablero() {
        System.out.println(d.mensajexIdioma(idioma, 12));
        System.out.println(d.mensajexIdioma(idioma, 13));

        for (char[] chars : tablero) {

            for (int j = 0; j < tablero.length; j++) {
                System.out.print("|" + chars[j]);

            }
            System.out.println("|");
        }
        System.out.println(" ");
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

    public void jugar() {
        boolean gano = false;
        while (true) {
            if (gano) {
                Empate();
            }

            jugador = J1;
            System.out.println(d.mensajexIdioma(idioma, 4) + " : " + jugador);
            System.out.println(" ");
            movimientoJugador();
            mostrarTablero();
            if (haGanado(jugador) || Empate()) {
                gano = true;
                break;
            }
            jugador = J2;
            System.out.println(d.mensajexIdioma(idioma, 3) + " : " + jugador);
            System.out.println(" ");
            movimientoComputadora();
            mostrarTablero();
            if (haGanado(jugador) || Empate()) {
                gano = false;
                break;
            }
        }
        if (haGanado(jugador)) {
            mensaje(jugador);
        }

        if (!haGanado(jugador) && Empate()) {
            System.out.println(d.mensajexIdioma(idioma, 11));
        }
        System.out.println(d.mensajexIdioma(idioma, 7));

    }

    public void movimientoJugador() {
        Scanner movimiento = new Scanner(System.in);
        int fila;
        int columna;
        boolean condicion = true;
        while (condicion) {
            System.out.println(d.mensajexIdioma(idioma, 15));
            columna = movimiento.nextInt();
            if (columna <= 3 && columna > 0) {
                System.out.println(d.mensajexIdioma(idioma, 16));
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
                        System.out.println(d.mensajexIdioma(idioma, 17));
                    }

                } else {
                    System.out.println(d.mensajexIdioma(idioma, 14));
                }
            } else {
                System.out.println(d.mensajexIdioma(idioma, 14));
            }
        }
    }

    public void movimientoComputadora() {
        boolean condicion = true;
        while (condicion) {
            int fila = (int) (Math.random() * (3 - 1 + 1));
            int columna = (int) (Math.random() * (3 - 1 + 1));

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

    public void mensaje(char jugador) {
        if (jugador == 'X') {
            System.out.println(d.mensajexIdioma(idioma, 6));
        } else {
            System.out.println(d.mensajexIdioma(idioma, 5));
        }
    }

    public boolean haGanado(char jugador) {
        // horizontales
        if (tablero[0][0] == jugador && tablero[0][1] == jugador && tablero[0][2] == jugador) {

            return true;
        } else if (tablero[1][0] == jugador && tablero[1][1] == jugador && tablero[1][2] == jugador) {

            return true;
        } else if (tablero[2][0] == jugador && tablero[2][1] == jugador && tablero[2][2] == jugador) {

            return true;
        }

        //verticales
        else if (tablero[0][0] == jugador && tablero[1][0] == jugador && tablero[2][0] == jugador) {

            return true;
        } else if (tablero[0][1] == jugador && tablero[1][1] == jugador && tablero[2][1] == jugador) {

            return true;
        } else if (tablero[0][2] == jugador && tablero[1][2] == jugador && tablero[2][2] == jugador) {

            return true;
        }

        //diagonales
        else if (tablero[0][0] == jugador && tablero[1][1] == jugador && tablero[2][2] == jugador) {

            return true;
        } else return tablero[0][2] == jugador && tablero[1][1] == jugador && tablero[2][0] == jugador;

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