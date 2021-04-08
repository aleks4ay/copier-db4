package ua.aleks4ay.domain.model;

import ua.aleks4ay.domain.tools.DateConverter;

import java.sql.Timestamp;

public class Journal extends AbstractEntity<Journal>{

    private String idDoc;
    private String docNumber;
    private Timestamp dateCreate;

    public Journal(String idDoc, String docNumber, Timestamp dateCreate) {
        this.idDoc = idDoc;
        this.docNumber = docNumber;
        this.dateCreate = dateCreate;
    }

    @Override
    public Journal getEntity() {
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

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Journal journal = (Journal) o;

        return getIdDoc().equals(journal.getIdDoc())
                && getDocNumber().equals(journal.getDocNumber())
                && this.getDateCreate().getTime() == journal.getDateCreate().getTime();
    }

    @Override
    public int hashCode() {
        int result = getIdDoc().hashCode();
        result = 31 * result + getDocNumber().hashCode();
        result = 31 * result + getDateCreate().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Journal{" +
                "idDoc='" + idDoc + '\'' +
                ", docNumber='" + docNumber + '\'' +
                ", dateCreate=" + dateCreate +
                '}';
    }

    @Override
    public String getDifferences(Journal journal) {
        String result = "";
        if (! this.idDoc.equals(journal.getId()) ) {
            result += "iddoc ['" + journal.getId() + "' --> '" + this.idDoc + "'] ";
        }
        if (! this.docNumber.equals( journal.getDocNumber())) {
            result += "docNumber ['" + journal.getDocNumber() + "' --> '" + this.docNumber + "'] ";
        }
        if (this.dateCreate.getTime() != journal.dateCreate.getTime()) {
            result += "dateCreate [" + DateConverter.dateToString(journal.dateCreate.getTime()) + "--> "
                    + DateConverter.dateToString(this.dateCreate.getTime()) + "] ";
        }
        return result;
    }
}
