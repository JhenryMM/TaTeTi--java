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
    private Statement miStatement;
    private int idioma = 2; // idioma por defecto 2 (español) para mostrar la tabla de idiomas
    private String nombreJ;
    private boolean seregistro; // boolean para que solo se pueda pedir 1 vez el nombre al usuario que corre el programa
    private static String nombreJugador;
    private boolean nosalir = true;
    private static boolean ganoJugador; // si es true gano el jugador, si es false gano la computadora
    private static boolean empate = false;
    private static boolean notermino = true; // no termino quiere decir q la partida no termino asi q seguira pidiendo los movimientos al jugador

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
                        // condicion para que no termine el juego hasta que gane 1 con un try chatch por si el jugador ingresa un caracter que no sea numero
                        // en caso que si ingrese un caracter el juego reinicia la peticion de ingresar la colum y fila, no importa si esta en la fila
                        while (notermino) {
                            try {
                                notermino = jugar(notermino);
                            } catch (Exception e) {
                                System.out.println(d.mensajexIdioma(idioma, 14));
                            }
                        }
                        notermino = true;

                        if (ganoJugador && !empate) {
                            gano = 1;
                        } else if (!ganoJugador && !empate) {
                            gano = 0;
                        } else {
                            gano = 2;
                        }

                        Date fin = new Date();

                        d.GuardaPartida(format, inicio, fin, gano, nombreJugador, idioma);
                    } else if (op == 2) {
                        d.imprimirEstadisticas(idioma, null);
                    } else if (op == 3) {
                        d.imprimirEstadisticas(idioma, nombreJ);
                    } else if (op == 4) {

                        seregistro = true;
                        tabladeIdiomas();
                    } else if (op == 5) {
                        nosalir = false;
                        condicion = false;

                    } else {
                        System.out.println(d.mensajexIdioma(idioma, 31));
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

        System.out.println(d.mensajexIdioma(idioma, 12) + ": " + J1);
        System.out.println(d.mensajexIdioma(idioma, 13) + ": " + J2);
        System.out.println();

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

    public boolean jugar(boolean notermino) {
        boolean continuarPartida = true;
        while (continuarPartida) {
            if (chequeodeEmpate()) {
                continuarPartida = false;
                empate = true;
                notermino = false;
            } else {

                System.out.println(d.mensajexIdioma(idioma, 4) + " : " + J1);
                movimientoJugador();
                mostrarTablero();
                System.out.println();
                if (chequeodeEmpate()) {
                    continuarPartida = false;
                    empate = true;
                    notermino = false;
                } else {
                    movimientoComputadora();
                    if (continuarPartida = ganador()) {
                        System.out.println(d.mensajexIdioma(idioma, 3) + " : " + J2);
                    }
                }
            }
            if (!(ganador())) {
                anuncioDeGanador(ganoJugador);
                notermino = false;
            } else if (empate) {
                notermino = false;
                System.out.println(d.mensajexIdioma(idioma, 11));
            }
        }
        return notermino;
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
                        System.out.println();
                    }

                } else {
                    System.out.println(d.mensajexIdioma(idioma, 14));
                    System.out.println();
                }
            } else {
                System.out.println(d.mensajexIdioma(idioma, 14));
                System.out.println();
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

    public void anuncioDeGanador(boolean ganoJugador) {
        if (ganoJugador) {
            System.out.println(d.mensajexIdioma(idioma, 6));
        } else {
            System.out.println(d.mensajexIdioma(idioma, 5));
        }
    }

    public boolean ganador() {
        boolean ganador;

        if (tablero[0][0] == J1 && tablero[0][1] == J1 && tablero[0][2] == J1) {
            ganador = false;
            ganoJugador = true;

        } else if (tablero[1][0] == J1 && tablero[1][1] == J1 && tablero[1][2] == J1) {
            ganador = false;
            ganoJugador = true;

        } else if (tablero[2][0] == J1 && tablero[2][1] == J1 && tablero[2][2] == J1) {
            ganador = false;
            ganoJugador = true;

        } else if (tablero[0][0] == J1 && tablero[1][0] == J1 && tablero[2][0] == J1) {
            ganador = false;
            ganoJugador = true;

        } else if (tablero[0][1] == J1 && tablero[1][1] == J1 && tablero[2][1] == J1) {
            ganador = false;
            ganoJugador = true;

        } else if (tablero[0][2] == J1 && tablero[1][2] == J1 && tablero[2][2] == J1) {
            ganador = false;
            ganoJugador = true;

        } else if (tablero[0][0] == J1 && tablero[1][1] == J1 && tablero[2][2] == J1) {
            ganador = false;
            ganoJugador = true;

        } else if (tablero[2][0] == J1 && tablero[1][1] == J1 && tablero[0][2] == J1) {
            ganador = false;
            ganoJugador = true;

        } else if (tablero[0][0] == J2 && tablero[0][1] == J2 && tablero[0][2] == J2) {
            ganador = false;
            ganoJugador = false;

        } else if (tablero[1][0] == J2 && tablero[1][1] == J2 && tablero[1][2] == J2) {
            ganador = false;
            ganoJugador = false;

        } else if (tablero[2][0] == J2 && tablero[2][1] == J2 && tablero[2][2] == J2) {
            ganador = false;
            ganoJugador = false;

        } else if (tablero[0][0] == J2 && tablero[1][0] == J2 && tablero[2][0] == J2) {
            ganador = false;
            ganoJugador = false;

        } else if (tablero[0][1] == J2 && tablero[1][1] == J2 && tablero[2][1] == J2) {
            ganador = false;
            ganoJugador = false;

        } else if (tablero[0][2] == J2 && tablero[1][2] == J2 && tablero[2][2] == J2) {
            ganador = false;
            ganoJugador = false;

        } else if (tablero[0][0] == J2 && tablero[1][1] == J2 && tablero[2][2] == J2) {
            ganador = false;
            ganoJugador = false;

        } else {
            if (tablero[2][0] == J2 && tablero[1][1] == J2 && tablero[0][2] == J2) {
                ganador = false;

            } else {
                ganador = true;
            }
            ganoJugador = false;
        }

        return ganador;
    }

    public boolean chequeodeEmpate() {
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