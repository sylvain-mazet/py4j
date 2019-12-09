package py4j;

import java.time.Instant;
import java.util.Date;

public class BoundObject {

    private final Object object;

    final Date creation;

    private Date lastAccess;

    public BoundObject(Object object) {
        this.object = object;
        this.creation = Date.from(Instant.now());
        this.lastAccess = Date.from(Instant.now());
    }

    public String getObjectSimpleClassName() {
        return object.getClass().getSimpleName();
    }

    public Object getObject() {
        this.lastAccess = Date.from(Instant.now());
        return object;
    }

    public Date getCreation() {
        return creation;
    }

    public Date getLastAccess() {
        return lastAccess;
    }

}
