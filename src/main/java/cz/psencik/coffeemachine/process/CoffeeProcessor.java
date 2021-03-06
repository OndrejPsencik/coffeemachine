package cz.psencik.coffeemachine.process;

import cz.psencik.coffeemachine.domain.entities.CoffeeOrderState;
import cz.psencik.coffeemachine.service.CoffeeOrderDaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoffeeProcessor {
    private static final Long COFFEE_PREPARATION_TIME = 20000L;
    private static final Logger log = LoggerFactory.getLogger(CoffeeProcessor.class);

    private Thread processingThread;
    private final Long machineId;
    private final CoffeeOrderDaoService coffeeOrderDaoService;

    private final Object waitObject = new Object();

    public CoffeeProcessor(Long machineId, CoffeeOrderDaoService coffeeOrderDaoService) {
        this.machineId = machineId;
        this.coffeeOrderDaoService = coffeeOrderDaoService;
    }

    public synchronized void start() {
        if(processingThread==null) {
            processingThread = new Thread(this::makeCoffees);
            processingThread.setDaemon(true);
            processingThread.start();
        }
    }

    synchronized public void stop() {
        processingThread.interrupt();
        processingThread = null;
    }

    public void notifyNewOrder() {
        synchronized (waitObject) {
            waitObject.notify();
        }
    }

    public void makeCoffees() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                var order = coffeeOrderDaoService.findFirstOrder(machineId);
                if (order != null) {
                    log.info("processing coffee order {}", order);
                    order.setCoffeeOrderState(CoffeeOrderState.PROCESSING);
                    coffeeOrderDaoService.saveCoffeeOrder(order);

                    Thread.sleep(COFFEE_PREPARATION_TIME);

                    order.setCoffeeOrderState(CoffeeOrderState.DONE);
                    coffeeOrderDaoService.saveCoffeeOrder(order);
                    log.info("finished coffee order {}", order);
                } else {
                    synchronized (waitObject) {
                        waitObject.wait();
                    }
                }
            }
        } catch (InterruptedException interruptedException) {
            //aborting processor
        }
    }
}
