package ua.aleks4ay.domain.model;

public class Tmc extends AbstractEntity<Tmc>{

    private String id;
    private String idParent;
    private String art;
    private String code;
    private String descr;
    private int isFolder;
    private String descrAll;
    private String type;
    private int sizeA;
    private int sizeB;
    private int sizeC;

    public Tmc() {
    }

    public Tmc(String id) {
        this.id = id;
    }

    public Tmc(String id, String idParent, String art, String code, String descr, int isFolder, String descrAll,
               String type, int sizeA, int sizeB, int sizeC) {
        this.id = id;
        this.idParent = idParent;
        this.art = art;
        this.code = code;
        this.descr = descr;
        this.isFolder = isFolder;
        this.descrAll = descrAll;
        this.type = type;
        this.sizeA = sizeA;
        this.sizeB = sizeB;
        this.sizeC = sizeC;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdParent() {
        return idParent;
    }

    public void setIdParent(String idParent) {
        this.idParent = idParent;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public int getIsFolder() {
        return isFolder;
    }

    public void setIsFolder(int isFolder) {
        this.isFolder = isFolder;
    }

    public String getDescrAll() {
        return descrAll;
    }

    public void setDescrAll(String descrAll) {
        this.descrAll = descrAll;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Tmc getTmc() {
        return this;
    }

    @Override
    public Tmc getEntity() {
        return this;
    }

    @Override
    public String toString() {
        return "Tmc{" +
                "id='" + id + '\'' +
                ", idParent='" + idParent + '\'' +
                ", art='" + art + '\'' +
                ", code='" + code + '\'' +
                ", descr='" + descr + '\'' +
                ", isFolder=" + isFolder +
                ", descrAll='" + descrAll + '\'' +
                ", type='" + type + '\'' +
                ", sizeA=" + sizeA +
                ", sizeB=" + sizeB +
                ", sizeC=" + sizeC +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Tmc that = (Tmc) obj;

        return id.equals(that.getId())
                && idParent.equals(that.getIdParent())
                && art.equals(that.getArt())
                && code.equals(that.getCode())
                && descr.equals(that.getDescr())
                && isFolder == that.getIsFolder()
                && sizeA == that.getSizeA()
                && sizeB == that.getSizeB()
                && sizeC == that.getSizeC()
                && descrAll.equals(that.getDescrAll())
                && type.equals(that.getType());
    }

    @Override
    public String getDifferences(Tmc tmc) {
        String result = "";
        if (! this.id.equals(tmc.id) ) {
            result += "id [" + tmc.id + "--> " + this.id + "] ";
        }
        if (! this.idParent.equals(tmc.idParent) ) {
            result += "idParent [" + tmc.idParent + "--> " + this.idParent + "] ";
        }
        if (! this.art.equals(tmc.art) ) {
            result += "art [" + tmc.art + "--> " + this.art + "] ";
        }
        if (! this.code.equals(tmc.code) ) {
            result += "vendor code [" + tmc.code + "--> " + this.code + "] ";
        }
        if (! this.descr.equals(tmc.descr) ) {
            result += "description [" + tmc.descr + "--> " + this.descr + "] ";
        }
        if (this.isFolder != tmc.isFolder) {
            result += "is_folder [" + tmc.isFolder + "--> " + this.isFolder + "] ";
        }
        if (! this.descrAll.equals(tmc.descrAll) ) {
            result += "descrAll [" + tmc.descrAll + "--> " + this.descrAll + "] ";
        }
        if (this.sizeA != tmc.sizeA) {
            result += "sizeA [" + tmc.sizeA + "--> " + this.sizeA + "] ";
        }
        if (this.sizeB != tmc.sizeB) {
            result += "sizeB [" + tmc.sizeB + "--> " + this.sizeB + "] ";
        }
        if (this.sizeC != tmc.sizeC) {
            result += "sizeC [" + tmc.sizeC + "--> " + this.sizeC + "] ";
        }
        if (! this.type.equals(tmc.type) ) {
            result += "type [" + tmc.type + "--> " + this.type + "] ";
        }
        return result;
    }
}
