package de.ls5.wt2;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class DBIdentified {

    private long id;

    @Id
    @GeneratedValue
    public long getId() {
        return this.id;
    }

    public void setId(final long id) {
        this.id = id;
    }

}
