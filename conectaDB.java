import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.sql.ResultSet;
import java.time.LocalDateTime;
public class conectaDB {
    private String  conexion;
    private String  usuario;
    private String  password;
    public conectaDB(String conexion, String usuario, String password){
        this.conexion=conexion;
        this.usuario=usuario;
        this.password=password;
    }
    
    public void imprimirTablaIdiomas(){
        System.out.println("Seleccione su idioma");
        try {
            //Crear conexion
            Connection miConexion= DriverManager.getConnection("jdbc:mysql://"+this.conexion,this.usuario,this.password);
            Statement miStatement= miConexion.createStatement();
            //obtener registros
            ResultSet miResultSet = miStatement.executeQuery("SELECT * FROM idioma" );
            //update registros
            //miStatement.exceuteUpdate(instruccionSQL);
            
         

            while(miResultSet.next()){
                System.out.println(miResultSet.getString("idIdioma")+" "+ miResultSet.getString("descripcion"));
            }


        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void imprimirEstadisticas(){
        System.out.println("Estas son tus Estadisticas");
        try {
            //Crear conexion
            Connection miConexion= DriverManager.getConnection("jdbc:mysql://"+this.conexion,this.usuario,this.password);
            Statement miStatement= miConexion.createStatement();
            //obtener registros
            ResultSet miResultSet = miStatement.executeQuery("SELECT * FROM registrodepartida" );
            //update registros
            //miStatement.exceuteUpdate(instruccionSQL);
           
           
            


            while(miResultSet.next()){
               
                System.out.println("La partida inicio "+miResultSet.getString("inicioDePartida")+ " y finalizo el "+miResultSet.getString("FinDePartida")+" y el jugador "+ miResultSet.getString("nombreJugador")+" "+miResultSet.getString("Gano")+" contra la maquina");
            }


        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void GuardaPartida(DateFormat date, Date inicio, Date fin, String ganador){
        System.out.println("Estas guardando tus partidas...");
        System.out.println("A continuacion ingresa tu nombre");
        Scanner l = new Scanner(System.in);
     
        String nombre=l.nextLine();
     
        try {
            //Crear conexion
            Connection miConexion= DriverManager.getConnection("jdbc:mysql://"+this.conexion,this.usuario,this.password);
            Statement miStatement= miConexion.createStatement();
           
            //miStatement.exceuteUpdate(instruccionSQL);
            String instruccionSQL= "INSERT INTO registrodepartida(inicioDePartida,FinDePartida,gano,nombreJugador) VALUES ('"+date.format(inicio)+"','"+date.format(fin)+"','"+ganador+"','"+nombre+"')";
            miStatement.executeUpdate(instruccionSQL);
            
       

            System.out.println("Se registro correctamente");


        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

		
	
}
	

