package ua.aleks4ay.domain.model;

public class Worker extends AbstractEntity<Worker> {

    private String id;
    private String name;

    public Worker() {
    }

    public Worker(String id, String name) {
        this.id = id;
        this.name = name;
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
        return "Worker{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Worker that = (Worker) obj;

        return id.equals(that.getId())
                && name.equals(that.getName());
    }

    public Worker getWorker() {
        return this;
    }

    @Override
    public Worker getEntity() {
        return this;
    }

    @Override
    public String getDifferences(Worker worker) {
        String result = "";
        if (! this.id.equals(worker.getId()) ) {
            result += "id ['" + worker.getId() + "' --> '" + this.id + "'] ";
        }
        if (! this.name.equals(worker.getName())) {
            result += "name ['" + worker.getName() + "' --> '" + this.name + "'] ";
        }
        return result;
    }
}
