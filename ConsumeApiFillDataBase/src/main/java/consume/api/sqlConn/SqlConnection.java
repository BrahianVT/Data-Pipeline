package consume.api.sqlConn;

import consume.api.entity.Metrobus;
import consume.api.util.ReadPropertyFile;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Properties;

/**
 * @author BrahianVT
 * */
public class SqlConnection {
    Properties config;
    ReadPropertyFile read = new ReadPropertyFile();
    Connection conn = null;
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


    public void insertBusInfo2(Metrobus bus){
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

    public void insertBusInfo(){
        Metrobus bus = new Metrobus();
        bus.setAlcaldia("Coyoacán");
        bus.setDateUpdated("2020-05-21 20:00:03");
        bus.setLatitude(new BigDecimal("19.292600631713867"));
        bus.setLongitude(new BigDecimal("-99.17749786376953"));
        bus.setVehicleId("177");
        bus.setRecordId("0c94473d65d2185d7efc65b89d57413eda8ebfcc");

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

    public Properties getSqlProperties(){
        return (config != null)?config:read.getProperties();
    }

    public static void main(String[] args) {
        // Sql conn
        SqlConnection test = new SqlConnection();
        test.openConnection();
        test.checkRecordId("0c94473d65d2185d7efc65b89d57413eda8ebfcc");
        //test.insertBusInfo();

        test.disconnect();
    }
}
