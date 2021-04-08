package ua.aleks4ay.domain.model;

import ua.aleks4ay.domain.tools.DateConverter;

import java.sql.Timestamp;

public class Order extends AbstractEntity<Order>{
    private String idDoc;
    private String idClient;
    private String idManager;
    private int durationTime;
    private Timestamp dateToFactory;
    private double price;

    public Order(String idDoc, String idClient, String idManager, int durationTime, Timestamp dateToFactory, double price) {
        this.idDoc = idDoc;
        this.idClient = idClient;
        this.idManager = idManager;
        this.durationTime = durationTime;
        this.dateToFactory = dateToFactory;
        this.price = price;
    }

    public String getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(String idDoc) {
        this.idDoc = idDoc;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getIdManager() {
        return idManager;
    }

    public void setIdManager(String idManager) {
        this.idManager = idManager;
    }

    public int getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(int durationTime) {
        this.durationTime = durationTime;
    }

    public Timestamp getDateToFactory() {
        return dateToFactory;
    }

    public void setDateToFactory(Timestamp dateToFactory) {
        this.dateToFactory = dateToFactory;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Order getOrder() {
        return this;
    }

    @Override
    public String toString() {
        return "Order{" +
                ", idDoc='" + idDoc + '\'' +
                ", idClient='" + idClient + '\'' +
                ", idManager='" + idManager + '\'' +
                ", durationTime=" + durationTime +
                ", dateToFactory=" + dateToFactory +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Order that = (Order) obj;

        return idDoc.equalsIgnoreCase(that.getIdDoc())
                && idClient.equalsIgnoreCase(that.getIdClient())
                && idManager.equalsIgnoreCase(that.getIdManager())
                && durationTime == that.getDurationTime()
                && price == that.getPrice()
                && dateToFactory == null
                    ? that.getDateToFactory() == null
                    : that.getDateToFactory() !=null && dateToFactory.getTime() == that.getDateToFactory().getTime();
    }

    @Override
    public String getDifferences(Order order) {
        String result = "";
        if (! this.idDoc.equals(order.idDoc) ) {
            result += "idDoc [" + order.idDoc + "--> " + this.idDoc + "] ";
        }
        if (! this.idClient.equals(order.idClient) ) {
            result += "idClient [" + order.idClient + "--> " + this.idClient + "] ";
        }
        if (! this.idManager.equals(order.idManager) ) {
            result += "idManager [" + order.idManager + "--> " + this.idManager + "] ";
        }
        if (this.durationTime != order.durationTime) {
            result += "durationTime [" + order.durationTime + "--> " + this.durationTime + "] ";
        }
        if (this.dateToFactory.getTime() != order.dateToFactory.getTime()) {
            result += "dateToFactory [" + DateConverter.dateToString(order.dateToFactory.getTime()) + "--> "
                    + DateConverter.dateToString(this.dateToFactory.getTime()) + "] ";
        }
        if (Double.compare(this.price, order.price) != 0) {
            result += "price [" + order.price + "--> " + this.price + "] ";
        }
        return result;
    }

    @Override
    public String getId() {
        return idDoc;
    }

    @Override
    public Order getEntity() {
        return this;
    }
}
