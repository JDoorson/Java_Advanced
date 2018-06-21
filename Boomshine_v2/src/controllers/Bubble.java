package controllers;

public class Bubble implements Runnable{
    private models.Bubble model;
    private boolean isActive;

    public Bubble(models.Bubble model) {
        this.model = model;
        this.isActive = true;

        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        while(this.isActive) {
            model.move();

            try {
                Thread.sleep(3);
            } catch(InterruptedException e) {
                // Nothing.
            }
        }
    }
}
