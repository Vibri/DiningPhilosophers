package cz.vibri.concurrency.diningphylosophers;

import java.util.Random;

public class Philosopher implements Runnable {

	private int id;
	private Chopstick left;
	private Chopstick right;
	private Random random;
	private int eatingCounter;

	private volatile boolean isFull = false;

	public void run() {

		try {
			while (!isFull) {

				think();

				if (left.pickUp(this, State.LEFT)) {
					if (right.pickUp(this, State.RIGHT)) {
						eat();
						right.putDown(this, State.RIGHT);
					}
					left.putDown(this, State.LEFT);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void think() throws InterruptedException {
		System.out.println(this + " is thinking ...");
		Thread.sleep(random.nextInt(1000));
	}

	private void eat() throws InterruptedException {
		System.out.println(this + " is eating ...");
		this.eatingCounter++;
		Thread.sleep(random.nextInt(1000));
	}

	public Philosopher(int id, Chopstick left, Chopstick right) {
		this.id = id;
		this.left = left;
		this.right = right;
		this.random = new Random();
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}

	@Override
	public String toString() {
		return "Philosopher " + id;
	}

	public int getCounter() {
		return eatingCounter;
	}
}
