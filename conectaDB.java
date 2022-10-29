import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import com.mysql.cj.xdevapi.PreparableStatement;

import java.sql.ResultSet;


public class conectaDB {
    public static void main(String[] args) {
        try {
            //Crear conexion
            Connection miConexion= DriverManager.getConnection("jdbc:mysql://localhost:3306/tateti","root","");
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
  
		
	
}
	

