import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Scanner;
import java.sql.ResultSet;


public class conectaDB {
    private String conexion;

    private String usuario;
    private String password;

    public conectaDB(String conexion, String usuario, String password) {
        this.conexion = conexion;
        this.usuario = usuario;
        this.password = password;
    }

    public String getConexion() {
        return conexion;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }

    public void imprimirEstadisticas(int idioma) {

        try {
           
          //Crear conexion
            Connection miConexion = DriverManager.getConnection("jdbc:mysql://" + this.conexion, this.usuario, this.password);
            Statement miStatement = miConexion.createStatement();
            
          //obtener registros

          //mensaje estadisticas
          ResultSet miResultSet = miStatement.executeQuery("SELECT descripcion from mensajexidioma where id_Mensaje=18 and id_idioma =" + idioma + "");
          while (miResultSet.next()) {
            System.out.println(miResultSet.getString("descripcion"));
          }
          
          miResultSet= miStatement.executeQuery("SELECT descripcion from mensajexidioma where id_Mensaje=18 and id_idioma =" + idioma + "");
          
         miResultSet = miStatement.executeQuery("SELECT * FROM registrodepartida rp INNER JOIN jugador j ON rp.idjugador = j.idjugador");

      
        while (miResultSet.next()) {
            System.out.println(miResultSet.getString("inicioDePartida")  + miResultSet.getString("FinDePartida") + " " + miResultSet.getString("nombre") + " " + miResultSet.getString("Gano"));
            
        }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void GuardaPartida(DateFormat date, Date inicio, Date fin, int ganador, String nombre, int idioma) {
       // System.out.println("Estas guardando tus partidas...");
       

        try {
            //Crear conexion
            Connection miConexion = DriverManager.getConnection("jdbc:mysql://" + this.conexion, this.usuario, this.password);

            Statement miStatement = miConexion.createStatement();

            ResultSet miResultSet = miStatement.executeQuery("SELECT descripcion from mensajexidioma where id_Mensaje=22 and id_idioma = " + idioma + " ");


            while (miResultSet.next()) {
                System.out.println(miResultSet.getString("descripcion"));
            }

            String instruccionSQL = "INSERT INTO jugador(nombre) VALUES ('" + nombre + "')";
            miStatement.executeUpdate(instruccionSQL);

            PreparedStatement ps = miConexion.prepareStatement("SELECT*FROM jugador WHERE nombre = '" + nombre + "'");
            ResultSet jugador = ps.executeQuery();
            int idjugador = 0;

            while (jugador.next()) {
                idjugador = jugador.getInt("idjugador");
            }

            int dato = idjugador;
            String instruccionSQLB = "INSERT INTO registrodepartida(inicioDePartida,FinDePartida,gano,idjugador) VALUES ('" +
                    date.format(inicio) + "','" + date.format(fin) + "','" + ganador + "','" + dato + "')";
            miStatement.executeUpdate(instruccionSQLB);

            miResultSet = miStatement.executeQuery("SELECT descripcion from mensajexidioma where id_Mensaje=24 and id_idioma = " + idioma + " ");

            while (miResultSet.next()) {
                System.out.println(miResultSet.getString("descripcion"));
            }

            //System.out.println("Se registro correctamente");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
	

