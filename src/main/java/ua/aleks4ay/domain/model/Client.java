package ua.aleks4ay.domain.model;

public class Client extends AbstractEntity<Client> {

    private String id;
    private String name;

    public Client() {
    }

    public Client(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Client getEntity() {
        return this;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Client that = (Client) obj;

        return id.equals(that.getId())
                && name.equals(that.getName());
    }

    public Client getClient() {
        return this;
    }

    @Override
    public String getDifferences(Client client) {
        String result = "";
        if (! this.id.equals(client.getId()) ) {
            result += "id ['" + client.getId() + "' --> '" + this.id + "'] ";
        }
        if (! this.name.equals(client.getName()) ) {
            result += "name ['" + client.getName() + "' --> '" + this.name + "'] ";
        }
        return result;
    }
}
