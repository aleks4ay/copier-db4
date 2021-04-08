package ua.aleks4ay.domain.model;

public class InvoiceDescription extends AbstractEntity<InvoiceDescription>{
    private String idInvoice;
    private String idTmc;
    private int position;
    private int quantity = 0;
    private double payment;

    public InvoiceDescription(String idInvoice, String idTmc, int position, int quantity, double payment) {
        this.idInvoice = idInvoice;
        this.idTmc = idTmc;
        this.position = position;
        this.quantity = quantity;
        this.payment = payment;
    }

    @Override
    public InvoiceDescription getEntity() {
        return this;
    }

    @Override
    public String getId() {
        return idInvoice + "-" + position;
    }

    public String getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(String idInvoice) {
        this.idInvoice = idInvoice;
    }

    public String getIdTmc() {
        return idTmc;
    }

    public void setIdTmc(String idTmc) {
        this.idTmc = idTmc;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public InvoiceDescription getInvoiceDescription() {
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InvoiceDescription that = (InvoiceDescription) o;

        return position == that.position
                && quantity == that.quantity
                && Double.compare(payment, that.payment) == 0
                && idInvoice.equals(that.idInvoice)
                && idTmc.equals(that.idTmc);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = idInvoice != null ? idInvoice.hashCode() : 0;
        result = 31 * result + (idTmc != null ? idTmc.hashCode() : 0);
        result = 31 * result + position;
        result = 31 * result + quantity;
        temp = Double.doubleToLongBits(payment);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String getDifferences(InvoiceDescription invDescr) {
        String result = "";
        if (! this.idInvoice.equals(invDescr.idInvoice) ) {
            result += "idInvoice ['" + invDescr.idInvoice + "' --> '" + this.idInvoice + "'] ";
        }
        if (! this.idTmc.equals(invDescr.idTmc) ) {
            result += "idTmc ['" + invDescr.idTmc + "' --> '" + this.idTmc + "'] ";
        }
        if (this.quantity != invDescr.quantity) {
            result += "quantity ['" + invDescr.quantity + "' --> '" + this.quantity + "'] ";
        }
        if (Double.compare(this.position, invDescr.position) != 0) {
            result += "position ['" + invDescr.position + "' --> '" + this.position + "'] ";
        }
        if (Double.compare(this.payment, invDescr.payment) != 0) {
            result += "payment ['" + invDescr.payment + "' --> '" + this.payment + "'] ";
        }
        return result;
    }

    @Override
    public String toString() {
        return "InvoiceDescription{" +
                "idInvoice='" + idInvoice + '\'' +
                ", idTmc='" + idTmc + '\'' +
                ", position=" + position +
                ", quantity=" + quantity +
                ", payment=" + payment +
                '}';
    }
}
