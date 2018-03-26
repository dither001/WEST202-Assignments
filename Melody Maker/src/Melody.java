import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;


public class Melody {
	// fields
	private Queue<Note> song;
	
	// constructors
	public Melody(Queue<Note> song) {
		this.song = song;
	}
	
	// methods
	public double getTotalDuration() {
		Queue<Note> temp = new LinkedList<Note>();
		double duration = 0;
		int counter = song.size();
		boolean repeat = false;
		boolean loop = false;
		
		while (counter > 0) {
			Note current = song.remove();
			
			if (loop) {
				while (!temp.isEmpty()) {
					duration += temp.remove().getDuration();
				}
				loop = false;
			}
			
			if (current.isRepeat()) {
				if (!repeat) {
					repeat = true;
				} else {
					repeat = false;
					loop = true;
				}
				duration += current.getDuration();
				temp.add(current);
				--counter;
			} else if (repeat) {
				duration += current.getDuration();
				temp.add(current);
				--counter;
			} else {
				duration += current.getDuration();
				--counter;
			}
			
			song.add(current);
		}
		
		return duration;
	}
	
	public void changeTempo(double tempo) {
		int counter = song.size();
		double duration = 1.0;
		
		while (counter > 0) {
			duration = song.peek().getDuration();
			song.peek().setDuration(duration * tempo);
			song.add(song.remove());
			--counter;
		}
	}
	
	public void reverse() {
		Stack<Note> reverse = new Stack<Note>();
		
		while (song.peek() != null) {
			reverse.push(song.poll());
		}
		while (reverse.empty() != true) {
			song.add(reverse.pop());
		}
	}
	
	public void append(Melody other) {
		int counter = other.song.size();
		
		while (counter > 0) {
			song.add(other.song.peek());
			other.song.add(other.song.remove());
			--counter;
		}
	}
	
	public void play() {
		Queue<Note> temp = new LinkedList<Note>();
		int counter = song.size();
		boolean repeat = false;
		boolean loop = false;
		
		while (counter > 0) {
			Note current = song.remove();
			
			if (loop) {
				while (!temp.isEmpty()) {
					temp.remove().play();
				}
				loop = false;
			}
			
			if (current.isRepeat()) {
				if (!repeat) {
					repeat = true;
				} else {
					repeat = false;
					loop = true;
				}
				current.play();
				temp.add(current);
				--counter;
			} else if (repeat) {
				current.play();
				temp.add(current);
				--counter;
			} else {
				current.play();
				--counter;
			}
			
			song.add(current);
		}
	}
	
	@Override
	public String toString() {
		int counter = song.size();
		String string = "";
		
		while (counter > 0) {
			string += String.format("%s%n", song.peek());
			song.add(song.remove());
			--counter;
		}
		
		return string;
	}
}
