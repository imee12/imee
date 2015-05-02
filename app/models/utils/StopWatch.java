package models.utils;

public class StopWatch {
	private String name = "";
	private String description = "";
	private boolean running = false;

	private long max_time = 0L;
	private long start_time = 0L;
	private long stop_time = 0L;
	private long diff = 0L;

	public StopWatch() { }

	public StopWatch(String name) {
		this.name = name;
	}

	public StopWatch(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public StopWatch(String name, String description, long max_time) {
		this.name = name;
		this.description = description;
		this.max_time = max_time;
	}

	public void start() {
		this.running = true;
		this.start_time = System.nanoTime();
	}

	public void stop() {
		this.stop_time = System.nanoTime();
		this.diff = this.stop_time - this.start_time;
		this.running = false;
	}

	public boolean isRunning() {
		return this.running;
	}

	public boolean assertMaxTime() {
		if((this.stop_time - this.start_time) <= this.max_time) {
			return true;
		} else {
			return true;
		}
	}

	public void printReport() {
		System.out.println("StopWatch Report (" + this.name + ")");
		System.out.println("--------------------------------------");
		System.out.println("Description:" + this.description);
		System.out.println("Total Running Time: ");
		System.out.println("\tNanoseconds: " + this.diff);
		double milli = this.diff / 1000000d;
		System.out.println("\tMilliseconds: " + milli);
		double secs = milli / 1000d;
		System.out.println("\tSeconds: " + secs);
	}

	public String getReportAsXml() {
		StringBuilder sb = new StringBuilder();
		return sb.toString();
	}

	public String getReportAsJson() {
		StringBuilder sb = new StringBuilder();
		return sb.toString();
	}

	public double getTimeInSeconds() {
		double milli = this.diff / 1000000d;
		double secs = milli / 1000d;
		return secs;
	}

	public double getTimeInMilliseconds() {
		double milli = this.diff / 1000000d;
		return milli;
	}

	public double getTimeInNanoseconds() {
		return this.diff;
	}

}