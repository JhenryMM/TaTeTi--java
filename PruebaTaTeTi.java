import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.Date;
public class PruebaTaTeTi {
    
    public static void main(String[] args) {
    conectaDB d = new conectaDB("localhost:3306/tateti","root","");
    System.out.println("1.jugar\n2.Cambiar idomas\n3.ver estadisticas\n4.salir");
    Scanner l = new Scanner(System.in);
    int op1= l.nextInt();
    Date date=new Date();
    DateFormat hourdateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    LocalDateTime datetime = LocalDateTime.now();
    
    menu(op1,d,l);


    }

    public static void menu(int op1, conectaDB d,Scanner l) {
        String gano="perdio";
        switch(op1){
            case 1: 
            Date inicio= new Date();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            TaTeTi jugar= new TaTeTi();
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
            break;

            case 2: 
          
            d.imprimirTablaIdiomas();
            int idioma= l.nextInt();
            
            break;
            case 3: 
            d.imprimirEstadisticas();

            break;

            default:
            
            break;
    }
    }

}
