import java.util.Scanner;
public class TaTeTi {
    private char [][] tablero;
    private final char J1= 'X';
    private final char J2= '0';
    private char jugador= J1;
    
    void mostrarTablero(){
        System.out.println("Ficha de jugador : X \n");
        System.out.println("Ficha de Maquina : 0\n");

        for (int i = 0; i < tablero.length; i++) {
    
            for (int j = 0; j < tablero.length; j++) {
                System.out.print("|" + tablero[i][j]);
    
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
    
    void jugar(){
        boolean gano= false;
         while( !gano || !Empate()){
           
            jugador=J1;
            System.out.print("turno del jugador " + jugador);
            System.out.println(" ");
            movimientoJugador();
            mostrarTablero(); 
            if(haGanado(jugador)|| Empate()){
                gano= true;
                break;
            }
            jugador=J2;
            System.out.print("turno del jugador " + jugador);
            System.out.println(" ");
            movimientoComputadora();
            mostrarTablero();
            if(haGanado(jugador)|| Empate()){
                gano= true;
                break;
            }
         }
         if(haGanado(jugador)){
            mensaje(jugador);
         }
         
         if (!haGanado(jugador) && Empate()) {
            System.out.println("Empate!");
        }
        System.out.println("\nGracias por jugar!\n");
        }
    void movimientoJugador() {
        Scanner movimiento = new Scanner(System.in);
        int fila;
        int columna;
        boolean condicion = true;
        while (condicion) {
            System.out.println("ingrese la columna");
            columna = movimiento.nextInt();
            if (columna <= 3 && columna > 0) {
                System.out.println("ingrese la fila");
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
                        System.out.println("El espacio ya fue ocupado");
                    }
    
                } else {
                    System.out.println("El numero ingresado es incorrecto, debe ser mayor a 1 y menor a 3 ");
                }
            } else {
                System.out.println("El numero ingresado es incorrecto, debe ser mayor a 1 y menor a 3");
            }
        }
    }
    
    void movimientoComputadora(){
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
    
    public void mensaje(char jugador){
        System.out.println("ha ganado el jugador "+ jugador);
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