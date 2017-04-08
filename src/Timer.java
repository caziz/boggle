import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Timer implements Runnable {
	static int sec = 0;
	static int min = 0;
	
	private JFrame theFrame;
	public Timer(JFrame theFrame) {
		this.theFrame=theFrame;
		sec = Login.time % 60;
		min = Login.time / 60;
	}
    public void run() {
    	timerMethod();
    }
    public void timerMethod() {
    	long time = System.currentTimeMillis();
		while(time + 1000 > System.currentTimeMillis()) {}
		if(sec==-1) {
			sec=59;
			min--;
		}
		String tenth = "";
		if(sec<10){
			tenth = "0";
		}
		if(min>-1){
		BoggleGame.setTime(min+":"+tenth + sec);
		sec--;
		timerMethod();
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Your " + Login.time / 60 + " minutes and " + Login.time % 60 + " seconds are up!\nYour score is " + BoggleGame.getScore() + ".\nPress OK to quit.");
			((BoggleGame) theFrame).dispose();
		}
    }

}