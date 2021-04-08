package ua.aleks4ay.domain.model;

public class Description extends AbstractEntity<Description>{

    private String idDoc;
    private int position;
    private String idTmc;
    private int quantity;
    private String descrSecond;
    private int sizeA;
    private int sizeB;
    private int sizeC;
    private String embodiment;
    private Double price;

    @Override
    public Description getEntity() {
        return this;
    }

    @Override
    public String getId() {
        return idDoc + "-" + position;
    }

    public Description(String idDoc, int position, String idTmc, int quantity, String descrSecond,
                       int sizeA, int sizeB, int sizeC, String embodiment, Double price) {
        this.idDoc = idDoc;
        this.position = position;
        this.idTmc = idTmc;
        this.quantity = quantity;
        this.descrSecond = descrSecond;
        this.sizeA = sizeA;
        this.sizeB = sizeB;
        this.sizeC = sizeC;
        this.embodiment = embodiment;
        this.price = price;
    }

    public String getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(String idDoc) {
        this.idDoc = idDoc;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getIdTmc() {
        return idTmc;
    }

    public void setIdTmc(String idTmc) {
        this.idTmc = idTmc;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescrSecond() {
        return descrSecond;
    }

    public void setDescrSecond(String descrSecond) {
        this.descrSecond = descrSecond;
    }

    public int getSizeA() {
        return sizeA;
    }

    public void setSizeA(int sizeA) {
        this.sizeA = sizeA;
    }

    public int getSizeB() {
        return sizeB;
    }

    public void setSizeB(int sizeB) {
        this.sizeB = sizeB;
    }

    public int getSizeC() {
        return sizeC;
    }

    public void setSizeC(int sizeC) {
        this.sizeC = sizeC;
    }

    public String getEmbodiment() {
        return embodiment;
    }

    public void setEmbodiment(String embodiment) {
        this.embodiment = embodiment;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Description getDescription() {
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Description that = (Description) obj;

        return idDoc.equals(that.getIdDoc())
                && position == that.getPosition()
                && idTmc.equals(that.getIdTmc())
                && quantity == that.getQuantity()
                && descrSecond.equals(that.getDescrSecond())
                && sizeA == that.getSizeA()
                && sizeB == that.getSizeB()
                && sizeC == that.getSizeC()
                && Double.compare(price, that.getPrice()) == 0
                && embodiment.equals(that.getEmbodiment());
    }

    @Override
    public String getDifferences(Description description) {
        String result = "";
        if (! this.idDoc.equals(description.idDoc) ) {
            result += "idDoc ['" + description.idDoc + "' --> '" + this.idDoc + "'] ";
        }
        if (this.position != description.position) {
            result += "position ['" + description.position + "' --> '" + this.position + "'] ";
        }
        if (! this.idTmc.equals(description.idTmc) ) {
            result += "idTmc ['" + description.idTmc + "' --> '" + this.idTmc + "'] ";
        }
        if (this.quantity != description.quantity) {
            result += "quantity ['" + description.quantity + "' --> '" + this.quantity + "'] ";
        }
        if (! this.descrSecond.equals(description.descrSecond) ) {
            result += "descrSecond ['" + description.descrSecond + "' --> '" + this.descrSecond + "'] ";
        }
        if (this.sizeA != description.sizeA) {
            result += "sizeA ['" + description.sizeA + "' --> '" + this.sizeA + "'] ";
        }
        if (this.sizeB != description.sizeB) {
            result += "sizeB ['" + description.sizeB + "' --> '" + this.sizeB + "'] ";
        }
        if (this.sizeC != description.sizeC) {
            result += "sizeC ['" + description.sizeC + "' --> '" + this.sizeC + "'] ";
        }
        if (! this.embodiment.equals(description.embodiment) ) {
            result += "embodiment ['" + description.embodiment + "' --> '" + this.embodiment + "'] ";
        }
        return result;
    }

    @Override
    public String toString() {
        return "Description{" +
                "idDoc='" + idDoc + '\'' +
                ", position=" + position +
                ", idTmc='" + idTmc + '\'' +
                ", quantity=" + quantity +
                ", descrSecond='" + descrSecond + '\'' +
                ", sizeA=" + sizeA +
                ", sizeB=" + sizeB +
                ", sizeC=" + sizeC +
                ", embodiment='" + embodiment + '\'' +
                ", price=" + price +
                '}';
    }
}
