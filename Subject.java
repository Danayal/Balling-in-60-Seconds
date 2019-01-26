public interface Subject {
	// Standard Subject interface
	void registerObserver(Observer o);

	void removeObsever(Observer o);

	void notifyObservers();

}
