import java.util.ArrayList;

public class CommuncationSubject implements Subject {

	private ArrayList observers;
	private byte byteOne, byteTwo;
	private Player p;

	public CommuncationSubject(Player p) {
		observers = new ArrayList();
		this.p = p;
	}

	@Override
	public void registerObserver(Observer o) {
		// TODO Auto-generated method stub
		observers.add(o);
	}

	@Override
	public void removeObsever(Observer o) {
		// TODO Auto-generated method stub
		observers.remove(observers.indexOf(o));
	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		for (int i = 0; i < observers.size(); i++) {
			Observer observer = (Observer) observers.get(i);
			observer.update(byteOne, byteTwo);
		}
	}

	public void getData(byte b1, byte b2) {
		this.byteOne = b1;
		this.byteTwo = b2;
		notifyObservers();

	}

}
