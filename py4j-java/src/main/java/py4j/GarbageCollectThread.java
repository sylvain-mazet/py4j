package py4j;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

class GarbageCollectThread implements Runnable {

    public static final Logger PY4J_LOGGER = Logger.getLogger(GarbageCollectThread.class.getName());

    Gateway gateway;

    Long graceDelayMillis;

    public GarbageCollectThread(Long graceDelayMillis) {
        this.gateway = null;
        this.graceDelayMillis = graceDelayMillis;
    }

    public void setGateway(Gateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void run() {
        Date limit = Date.from(Instant.now().minusMillis(graceDelayMillis));

        PY4J_LOGGER.info("Garbage collecting unused java bindings");

        try {
            List<String> objectsToRemove = new ArrayList<String>();
            gateway.getBindings().forEach((id, boundObject) -> {
                if (boundObject.getLastAccess().before(limit)) {
                    if (id != Protocol.ENTRY_POINT_OBJECT_ID && id != Protocol.DEFAULT_JVM_OBJECT_ID
                            && id != Protocol.GATEWAY_SERVER_ID) {
                        objectsToRemove.add(id);
                    }
                }
            });
            objectsToRemove.forEach(gateway::deleteObject);
            PY4J_LOGGER.info(String.format("Collected %d objects from py4j", objectsToRemove.size()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
