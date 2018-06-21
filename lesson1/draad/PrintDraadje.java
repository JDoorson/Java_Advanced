package lesson1.draad;

public class PrintDraadje implements Runnable {

	private String s;
	private int teller;
	private volatile boolean doorgaan;
	Thread thread;

	public PrintDraadje(String s) {
		this.s = s;
		thread = new Thread(this);
		doorgaan = true;
		thread.start();
	}

	public synchronized void pleaseStop() {
		doorgaan = false;
	}

	@Override
	public void run() {

		while (doorgaan) {
			System.out.print(s);
			addOne();
			try {
				Thread.sleep(10);
			} catch (InterruptedException ie) { 	} // do nothing
		}
		System.out.println("\n" + Thread.currentThread().getName() + " - Teller: " + teller);
	}

	private synchronized void addOne() {
		teller++;
	}
}
