import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;


public class conectaDB {
    public static void main(String[] args) {
        try {
            //Crear conexion
            Connection miConexion= DriverManager.getConnection("jdbc:mysql://localhost:3306/tateti","root","");
            Statement miStatement= miConexion.createStatement();
            ResultSet miResultSet = miStatement.executeQuery("SELECT * FROM idioma" );
        

            while(miResultSet.next()){
                System.out.println(miResultSet.getString("idIdioma")+" "+ miResultSet.getString("descripcion"));
            }


        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
  
		
	
}
	

