package consume.api.sqlConn;

import consume.api.entity.Metrobus;
import consume.api.util.ReadPropertyFile;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Properties;

/**
 * Clase que gestiona conexiones a la base de datos
 * @author BrahianVT
 * */
public class SqlConnection {
    Properties config;
    ReadPropertyFile read = new ReadPropertyFile();
    Connection conn = null;
    // Crea un conexion a la base de datoe
    public void openConnection(){
        Properties auxConfig = getSqlProperties();

        try {
            Class.forName(auxConfig.getProperty("datasource.driver-class-name"));
            System.out.println("Driver Loaded");

            conn = DriverManager.getConnection(auxConfig.getProperty("datasource.url"),
                    auxConfig.getProperty("datasource.username"),auxConfig.getProperty("datasource.password"));

            System.out.println("Connected to MySql DataBase ...");
        } catch (SQLException  | ClassNotFoundException e ) {
            e.printStackTrace();
        }
    }

    // Metodo que cierra una conexion con base de datos
    public void disconnect(){
        System.out.println("Disconnect from database..");
        if(conn != null){
            try{
                conn.close();
                conn = null;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    // checa si un registro ya esta guardado en la db
    public boolean checkRecordId(String recordId){
        try{
            Statement statement = conn.createStatement();
            String query = "select id_record from metrobus_info where id_record ='"+recordId+"'";
            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()) {
                System.out.println("Already stored in the database so skip this dataset");
                return true;
            }
            System.out.println("Storing  in database ...");
            return false;
        }catch(SQLException ex){
            ex.printStackTrace(); return  false;
        }
    }

    // inserta un registro en la db
    public void insertBusInfo(Metrobus bus){
        String queryInsert = "INSERT INTO metrobus_info(id_record, id_vehicle, date_updated, longitude , latitude" +
                ", county) VALUES (";
        queryInsert+= "'" + bus.getRecordId() + "', " + "'"+bus.getVehicleId()+"', " + "'" + bus.getDateUpdated() + "', "
                + bus.getLongitude() + ", " + bus.getLatitude()+",'" +bus.getAlcaldia() +"')";
        System.out.println(queryInsert);
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(queryInsert);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    // optiene propiedades para crear la conexion
    public Properties getSqlProperties(){
        return (config != null)?config:read.getProperties();
    }

}
