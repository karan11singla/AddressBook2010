

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;
import java.applet.AudioClip;
import javax.swing.JFrame;
import javax.swing.JOptionPane;




public class Snake extends JFrame implements Runnable {

	Vector<Piece> snake = new Vector<Piece>();
	Piece food;
	Piece head;
	Thread thread;
	Rectangle bounds;

	static AudioClip eat;

	static {
		ClassLoader cl = Snake.class.getClassLoader();
		eat = java.applet.Applet.newAudioClip(cl.getResource("sounds/eat.wav"));
	}

	int score;
	int direction;
	int prevDirection;

	final int UP =		0;
	final int DOWN =	1;
	final int LEFT =	2;
	final int RIGHT =	3;

	int pieceSize = 10;

	int speed = 3;

	int fieldWidth = 30;
	int fieldHeight = 30;

	boolean inited = false;

	public Snake() {

		super("Snake");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);

		init();

		Insets in = getInsets();

		setSize(in.left + in.right + bounds.width + 20, in.top + in.bottom + bounds.height + 20);

		start();
	}

	private void init() {

		prevDirection = RIGHT;

		direction = RIGHT;

		snake = new Vector<Piece>();

		food = null;

		Insets in = getInsets();

		bounds = new Rectangle();
		bounds.x = in.left + 10;
		bounds.y = in.top + 10;
		bounds.width = fieldWidth * pieceSize;
		bounds.height = fieldHeight * pieceSize;

		int nPieces = 10;

		for (int i = nPieces; --i >= 0;)
			snake.addElement(new Piece(i * nPieces + bounds.x, pieceSize * nPieces + bounds.y, pieceSize));
		head = snake.firstElement();

		addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_UP:
						direction = UP; break;
					case KeyEvent.VK_DOWN:
						direction = DOWN; break;
					case KeyEvent.VK_LEFT:
						direction = LEFT; break;
					case KeyEvent.VK_RIGHT:
						direction = RIGHT; break;
				}
			}
		});

		score = 0;

		inited = true;
	}

	public Vector<Piece> getSpaces() {
		int total = fieldHeight * fieldWidth;
		Vector<Piece> spaces = new Vector<Piece>(total);

		for (int i = 0; i < total; i++) {
			int x = i % fieldHeight * pieceSize + bounds.x;
			int y = i / fieldWidth * pieceSize + bounds.y;
			Piece sq = new Piece(x, y, pieceSize);
			if (snake.indexOf(sq) == -1)
				spaces.addElement(sq);
		}
		return spaces;
	}



	public void paint(Graphics g) {
		super.paint(g);
		onPaint();
	}

	private void onPaint() {
		Graphics g = getGraphics();
		if (!inited) {
			return;
		}

		g.setColor(Color.black);

		g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);

		g.setColor(Color.gray);

		if (food == null) feed(g);
		else food.fill(g);

		Piece newHead = new Piece(head.x, head.y, pieceSize);
		switch (direction) {
			case UP:
				if (prevDirection != DOWN) newHead.y -= pieceSize;
				else {
					direction = prevDirection;
					newHead.y += pieceSize;
				}
				break;
			case LEFT:
				if (prevDirection != RIGHT) newHead.x -= pieceSize;
				else {
					direction = prevDirection;
					newHead.x += pieceSize;
				}
				break;
			case DOWN:
				if (prevDirection != UP) newHead.y += pieceSize;
				else {
					direction = prevDirection;
					newHead.y -= pieceSize;
				}
				break;
			case RIGHT:
				if (prevDirection != LEFT) newHead.x += pieceSize;
				else {
					direction = prevDirection;
					newHead.x -= pieceSize;
				}
				break;
		}
		if (newHead.equals(food)) {
			head = food;
			feed(g);
			eat.stop();
			eat.play();
			score += speed;
		} else {
			Piece tail = snake.elementAt(snake.size()-1);
			tail.clear(g);
			snake.removeElementAt(snake.size()-1);

			head = newHead;
			if (checkHit(head)) {
				stop();
				int b = JOptionPane.showConfirmDialog(this, "oh! Baita tu khalas\n your Score: " + score +
													"\n phir panga lainay ka?", "Snake", JOptionPane.YES_NO_OPTION);
				if (b == JOptionPane.YES_OPTION) {
					clear(g);
					init();
					start();
				} else	System.exit(0);
				return;
			}
		}
		prevDirection = direction;
		snake.insertElementAt(head, 0);

		for (int i = 0; i < snake.size(); i++) {
			snake.elementAt(i).fill(g);
		}
	}

	private boolean checkHit(Piece square) {

		for (Piece s: snake)
			if (s.equals(square))
				return true;

		if (square.y < bounds.y || square.y >= (bounds.height + bounds.y) ||
			square.x < bounds.x || square.x >= (bounds.width + bounds.x))
				return true;

		return false;
	}

	private void feed(Graphics g) {
		Vector<Piece> spaces = getSpaces();
		if (spaces.size() > 0) {
			food = spaces.elementAt((int)(Math.random() * spaces.size()));
			food.fill(g);
		} else {
			stop();
			JOptionPane.showMessageDialog(this, "Galing mo, man!");
			System.exit(0);
		}
	}

	private void move() {
		repaint();
	}

	public void start() {
		thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		thread = null;
		inited = false;
	}

	public void run() {
		while (thread != null && thread == Thread.currentThread()) {
			try {
				thread.sleep(1000 / (5 * (speed-1) + 1));
			} catch (InterruptedException e) {};
			move();
		}
	}

	private void clear(Graphics g) {
		for (Piece s: snake)
			s.clear(g);
		if (food != null) food.clear(g);
	}


	private class Piece {
		public int x;
		public int y;
		public int size;
		Piece(int x, int y, int size) {
			this.x = x;
			this.y = y;
			this.size = size;
		}
		public void move(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public void fill(Graphics g) {
			g.fillOval(x+1, y+1, size-2, size-2);
		}
		public void clear(Graphics g) {
			g.clearRect(x+1, y+1, size-2, size-2);
		}
		public String toString() {
			return "x=" + x +
					",y=" + y +
					",size=" + size;
		}
		public boolean equals(Object o) {
			if (o instanceof Piece) {
				Piece sq = (Piece)o;
				return sq.x == x && sq.y == y && sq.size == size;
			}
			return false;
		}
	}


	public static void main(String[] args) {

		new Snake();

	}
}