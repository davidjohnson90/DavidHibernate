package com.david.ORMappingSet;

public class Certificate {
    private int id;
    private String name;

    public Certificate() {
    }

    public Certificate(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (!this.getClass().equals(object.getClass())) return false;

        Certificate objectCertificate = (Certificate) object;
        if ((this.id == objectCertificate.getId()) && (this.name.equals(objectCertificate.getName()))) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int tmp = 0;
        tmp = (id + name).hashCode();
        return tmp;
    }
}
