package consume.api.entity;


import java.math.BigDecimal;

/**
 * Clase qie se usa para guardar la informacion relevante del servicio consumido
 * @author Brahian VT
 * */
public class Metrobus {
    private String recordId;
    private String vehicleId;
    private String dateUpdated;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String alcaldia;

    public Metrobus() { }

    public Metrobus(String recordId, String vehicleId, String dateUpdated, BigDecimal longitude, BigDecimal latitude, String alcaldia) {
        this.recordId = recordId;
        this.vehicleId = vehicleId;
        this.dateUpdated = dateUpdated;
        this.longitude = longitude;
        this.latitude = latitude;
        this.alcaldia = alcaldia;
    }

    public String getRecordId() {
        return recordId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public String getAlcaldia() {
        return alcaldia;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public void setAlcaldia(String alcaldia) {
        this.alcaldia = alcaldia;
    }

    @Override
    public String toString() {
        return "Metrobus{" +
                "recordId='" + recordId + '\'' +
                ", vehicleId='" + vehicleId + '\'' +
                ", dateUpdated='" + dateUpdated + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", alcaldia='" + alcaldia + '\'' +
                '}';
    }
}
