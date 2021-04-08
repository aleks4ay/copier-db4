package ua.aleks4ay.domain.model;

public class Invoice extends AbstractEntity<Invoice>{
    private String idDoc;
    private String idOrder;
    private double price;

    public Invoice(String idDoc, String idOrder, double price) {
        this.idDoc = idDoc;
        this.idOrder = idOrder;
        this.price = price;
    }

    @Override
    public Invoice getEntity() {
        return this;
    }

    @Override
    public String getId() {
        return idDoc;
    }

    public String getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(String idDoc) {
        this.idDoc = idDoc;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Invoice getInvoice() {
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Invoice that = (Invoice) obj;

        return idDoc.equals(that.idDoc)
                && idOrder.equals(that.idOrder)
                && Double.compare(price, that.getPrice()) == 0;
    }

    @Override
    public String getDifferences(Invoice invoice) {
        String result = "";
        if (! this.idDoc.equals(invoice.idDoc) ) {
            result += "idDoc [" + invoice.idDoc + "--> " + this.idDoc + "] ";
        }
        if (! this.idOrder.equals(invoice.idOrder) ) {
            result += "idOrder [" + invoice.idOrder + "--> " + this.idOrder + "] ";
        }
        if (Double.compare(this.price, invoice.price) != 0) {
            result += "price [" + invoice.price + "--> " + this.price + "] ";
        }
        return result;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "idDoc='" + idDoc + '\'' +
                ", idOrder='" + idOrder + '\'' +
                ", price=" + price +
                '}';
    }
}
