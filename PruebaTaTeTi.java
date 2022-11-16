import java.util.Scanner;

public class PruebaTaTeTi {

    public static void main(String[] args) {
       try {
        imprimirTablaIdiomas();
       } catch (Exception e) {
        System.out.println("Se ha ingresado un valor incorrecto\nCerrando...");
       }
    }

    public static void imprimirTablaIdiomas() {
        while (true) {
            System.out.println("Seleccione su idioma\n1.Ingles\n2.Español\n3.Portugues\n4.Salir");
            Scanner dato = new Scanner(System.in);
            int idioma = dato.nextInt();
            if (idioma > 0 && idioma < 4) {
                TaTeTi jugar = new TaTeTi(idioma);
                jugar.menu(jugar);
            } else if (idioma == 4) {
                break;
            } else {
                System.out.println("Solo puede ingresar numeros del 1 al 4\n");
            }

        }
    }

}
