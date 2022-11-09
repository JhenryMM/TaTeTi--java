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

    public void imprimirEstadisticas() {
        System.out.println("Estas son tus Estadisticas");
        try {

//            //Crear conexion
            Connection miConexion = DriverManager.getConnection("jdbc:mysql://" + this.conexion, this.usuario, this.password);
            Statement miStatement = miConexion.createStatement();
//            //obtener registros
            ResultSet miResultSet = miStatement.executeQuery("SELECT * FROM registrodepartida rp INNER JOIN jugador j ON rp.idjugador = j.idjugador ");

            while (miResultSet.next()) {
                System.out.println("La partida inicio " + miResultSet.getString("inicioDePartida") + " y finalizo el " + miResultSet.getString("FinDePartida") + " " + miResultSet.getString("nombre") + " " + miResultSet.getString("Gano") + " contra la maquina");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void GuardaPartida(DateFormat date, Date inicio, Date fin, String ganador) {
        System.out.println("Estas guardando tus partidas...");
        System.out.println("A continuacion ingresa tu nombre");
        Scanner l = new Scanner(System.in);

        String nombre = l.nextLine();

        try {
            //Crear conexion
            Connection miConexion = DriverManager.getConnection("jdbc:mysql://" + this.conexion, this.usuario, this.password);
            Statement miStatement = miConexion.createStatement();
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
            System.out.println("Se registro correctamente");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
	

