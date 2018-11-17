
public interface Subject {

	void registerObserver(Observer o);

	void removeObsever(Observer o);

	void notifyObservers();
	
}
