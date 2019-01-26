public abstract class Sound {
	//This is the class that implements strategy design pattern so the the
	//"SoundBehaviour" can be changed on the fly
	//In our code, the class SoundObserver will extend this
	SoundBehaviour sb;
	abstract void setBehaviour(SoundBehaviour sb);
	abstract void play();
	abstract void stop();
}
