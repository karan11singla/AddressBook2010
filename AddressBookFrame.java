import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;
import java.util.*;
import java.applet.*;

class AddressBookFrame extends JFrame implements ChangeListener,ActionListener,Runnable{

    private JTabbedPane pane;
	private JToolBar toolBar;
	private JButton[] btn;
    private JPanel homePanel;
	private JLabel homeImageLbl;
	private JLabel userLbl,passwordLbl;
	private JTextField userField;
	private JPasswordField passwordField;
	private JButton loginButton,cancelButton;
	private AddPersonPanel addPersonPanel;
    private SearchPersonPanel searchPersonPanel;
    private DeletePersonPanel deletePersonPanel;
    private UpdatePersonPanel updatePersonPanel;
    private ViewAllPersonPanel viewAllPersonPanel;
	private QueryDesigner queryDesigner;
	private boolean Start=true;
	private Calendar c;
	private JLabel date;
	private Thread gameThread;
	private final int x = 110,  y = 50;
    private final int l = 150,  w = 20;
    private final int xInc = 100,  yInc = 40;
	
	public static void main(String[] args){
	AddressBookFrame obj=new AddressBookFrame("Addressbook 2010"); 
 
}	
	

	public AddressBookFrame(String title) {
			 super(title);
			try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception unused) {}
			
			loadDataBase();
			
			if(start){
			JOptionPane.showMessageDialog(null, "DataBase Connected Successfully",
                        "Connection", JOptionPane.INFORMATION_MESSAGE);
       

      

        setBounds(150, 150, 835, 515);
        setResizable(false);
		
		date();
        addMenuBar();
		addToolBar();
		
        pane = new JTabbedPane();
		pane.addChangeListener(this);
        add(pane);

      homePersonPanel();
        pane.addTab("Home", new ImageIcon(getClass().getResource(
                "icons/Home.png")), homePanel);
				
		addPersonPanel = new AddPersonPanel();
        pane.addTab("Add Person", new ImageIcon(getClass().getResource(
                "icons/p_add.png")), addPersonPanel);
        validate();

        searchPersonPanel = new SearchPersonPanel();
        pane.addTab("Search Person", new ImageIcon(getClass().getResource(
                "icons/p_search.png")), searchPersonPanel);

        deletePersonPanel = new DeletePersonPanel();
        pane.addTab("Delete Person", new ImageIcon(getClass().getResource(
                "icons/p_delete.png")), deletePersonPanel);

        updatePersonPanel = new UpdatePersonPanel();
        pane.addTab("Update Person", new ImageIcon(getClass().getResource(
                "icons/update.png")), updatePersonPanel);

        viewAllPersonPanel = new ViewAllPersonPanel();
        pane.addTab("View All Persons", new ImageIcon(getClass().getResource(
                "icons/p_viewall.png")), viewAllPersonPanel);
				
				  setVisible(true);
lock();
        addWindowListener(new WindowAdapter(){
		public void windowClosing(WindowEvent we){
		showAbout();
		JOptionPane.showMessageDialog(null, "Thank you for using AddressBook 2010!",
                        "Thank you!", JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);}});

       

    }
	
	}//end of constuctor
	
	
	private void homePersonPanel(){
	  homePanel = new JPanel();
	 homePanel.setLayout(null);
	  
		userLbl = new JLabel("UserName :"); 
		userField = new JTextField(10);
        userLbl.setBounds(x, y + yInc, l, w);
        userField.setBounds(x + xInc, y + yInc, l, w);
        homePanel.add(userLbl);
        homePanel.add(userField);

        

        passwordLbl = new JLabel("Password :");
        passwordField = new JPasswordField(10);
        passwordLbl.setBounds(x, y + 2 * yInc, l, w);
        passwordField.setBounds(x + xInc, y + 2 * yInc, l, w);
        homePanel.add(passwordLbl);
        homePanel.add(passwordField);
		
		loginButton = new JButton("Login");
        cancelButton = new JButton("Cancel");
        loginButton.setBounds(x + xInc, y + 3 * yInc, l - 60, w);
        cancelButton.setBounds(x + 2*xInc, y + 3 * yInc, l - 60, w);
        homePanel.add(loginButton);
        homePanel.add(cancelButton);

       
        loginButton.addActionListener(this);
        cancelButton.addActionListener(this);
		
		  homeImageLbl = new JLabel("", new ImageIcon(getClass().getResource(
                "imgs/Home.PNG")), SwingConstants.LEFT);
        homeImageLbl.setBounds(410, 30 , 256, 256);
        homePanel.add(homeImageLbl);
		

        JLabel homeBgLbl = new JLabel("", new ImageIcon(getClass().getResource(
                "imgs/bground4.jpg")), SwingConstants.LEFT);
        homeBgLbl.setBounds(0, 0, 835, 551);
        homePanel.add(homeBgLbl);
	}
	
	private void welcome(){
	userLbl.hide();
	userField.hide();
	passwordLbl.hide();
	passwordField.hide();
	loginButton.hide();
	cancelButton.hide();
	homeImageLbl.setIcon(new ImageIcon(getClass().getResource(
                "imgs/Welcome3.gif")));
	homeImageLbl.setBounds(50, 30 , 776, 240);
	}
	
	private void password(){
	userLbl.show();
	userField.show();
	passwordLbl.show();
	passwordField.show();
	loginButton.show();
	cancelButton.show();
	homeImageLbl.setIcon(new ImageIcon(getClass().getResource(
                "imgs/Home.PNG")));
	homeImageLbl.setBounds(410, 30 , 256,256);
	
	}
	
	public void lock(){
			boolean op=false;
			for(int i=0;i<12;i++)
			if(i!=9)
			btn[i].setEnabled(op);
			pane.setEnabledAt(0,op);
			pane.setEnabledAt(1,op);
			pane.setEnabledAt(2,op);
			pane.setEnabledAt(3,op);
			pane.setEnabledAt(4,op);
			pane.setEnabledAt(5,op);
			 pane.setSelectedIndex(0);
	}
	public void unlock(){
					boolean op=true;
					
					for(int i=0;i<12;i++)
					btn[i].setEnabled(op);
					pane.setEnabledAt(0,op);
					pane.setEnabledAt(1,op);
					pane.setEnabledAt(2,op);
					pane.setEnabledAt(3,op);
					pane.setEnabledAt(4,op);
					pane.setEnabledAt(5,op);
					 pane.setSelectedIndex(0);
	}
	
	private void addToolBar(){
	
	toolBar=new JToolBar();
	btn=new JButton[12];
	
	Icon addIcon=new ImageIcon(getClass().getResource(
                "icons/p_add.png"));
	Icon searchIcon=new ImageIcon(getClass().getResource(
	"icons/p_search.png"));
	Icon deleteIcon=new ImageIcon(getClass().getResource(
                "icons/p_delete.png"));
	Icon updateIcon=new ImageIcon(getClass().getResource(
                "icons/update.png"));
	Icon allIcon=new ImageIcon(getClass().getResource(
                "icons/p_viewall.png"));
	Icon backIcon=new ImageIcon(getClass().getResource(
                "icons/Back.png"));
	Icon fwdIcon=new ImageIcon(getClass().getResource(
                "icons/Forward.png"));
	Icon lockIcon=new ImageIcon(getClass().getResource(
                "icons/Lock.png"));
	Icon unlockIcon=new ImageIcon(getClass().getResource(
                "icons/Unlock.png"));
	Icon aboutIcon=new ImageIcon(getClass().getResource(
                "icons/About.png"));
	Icon exitIcon=new ImageIcon(getClass().getResource(
                "icons/exit.png"));
	Icon homeIcon=new ImageIcon(getClass().getResource(
                "icons/Home.png"));

Icon[] icons={backIcon,fwdIcon,homeIcon,addIcon,searchIcon,deleteIcon,updateIcon,allIcon
,lockIcon,unlockIcon,aboutIcon,exitIcon};
	
	String[] toolTips={"Previous Record","Next Record","Home","Add Person","Search Person","Delete Person"
	,"Update Person","View All Person"
	,"Lock","UnLock","About","Exit"};
	
	//toolBar.setMargin(new Insets(15,15,15,15));
	
	for(int i=0;i<icons.length;i++){
	 btn[i]=new JButton(icons[i]);
	btn[i].setToolTipText(toolTips[i]);
	btn[i].addActionListener(this);
	btn[i].setActionCommand(String.valueOf(i));
	toolBar.add(btn[i]);
	
	}
	toolBar.addSeparator();
	
	toolBar.add(new JLabel(new ImageIcon(getClass().getResource(
               "icons/MBIT2.gif"))        ));
	toolBar.add(new JLabel("      "));
	
	toolBar.add(date);
	this.add("North",toolBar);
	}
	
	private void date(){
	c=Calendar.getInstance();

	String day=String.valueOf(c.get(Calendar.DAY_OF_MONTH));
	String months[]={"Jan","Feb","Mar","Apr","May","June",
					"Jul","Aug","Sep","Oct","Nov","Dec"};
	String year=String.valueOf(c.get(Calendar.YEAR));
	String month=months[c.get(Calendar.MONTH)];
	 date=new JLabel(month+" "+day+" , "+year);
		date.setFont(new Font("Serif",Font.BOLD,16));
	
	}
	
	 private void addMenuBar() {
	   
      
		JMenuBar menuBar = new JMenuBar();

        JMenu fileJMenu = new JMenu("File");
        menuBar.add(fileJMenu);
	

        JMenuItem exitJMItem = new JMenuItem("Exit");
        exitJMItem.addActionListener(this);
        fileJMenu.add(exitJMItem);
	
	
      

        JMenu extrasJMenu = new JMenu("Extras");
        menuBar.add(extrasJMenu);

        JMenuItem aboutJMItem = new JMenuItem("About");
        aboutJMItem.addActionListener(this);
        extrasJMenu.add(aboutJMItem);
		 
		 JMenuItem snakeJMItem = new JMenuItem("Snake");
        snakeJMItem.addActionListener(this);
        extrasJMenu.add(snakeJMItem);
		
		 JMenuItem queryJMItem = new JMenuItem("Query Designer");
        queryJMItem.addActionListener(this);
        extrasJMenu.add(queryJMItem);

        setJMenuBar(menuBar);

    }
	private void backAndForwardRecord(){
			try{
			updatePersonPanel.nameField.setText(rs.getString(1));
			updatePersonPanel.phoneField.setText(rs.getString(2));
			
			updatePersonPanel.mobileNoField.setText(rs.getString(3));
			updatePersonPanel.emailField.setText(rs.getString(4));
			updatePersonPanel.addressField.setText(rs.getString(5));
			updatePersonPanel.nameField.requestFocus();
	}
	 catch(Exception eee){
	  JOptionPane.showMessageDialog(null,"YOu are At LAst Record", "Error Message", JOptionPane.ERROR_MESSAGE); 
	 }
	}
	
	public void stateChanged(ChangeEvent ce){
	
	if(pane.getSelectedIndex()!=4)
		Start=true;
	
	}
	

	public void actionPerformed(ActionEvent ae) {

           

            String action = ae.getActionCommand();
			 
			 if (action.equals("0")){
			 if(Start){
			 pane.setSelectedIndex(4);
			 Start=false;}
			 else{
			 //connectivity
			 try{if(rs.previous()){
				backAndForwardRecord();
			}
			 }
			 catch(Exception eee){
			 JOptionPane.showMessageDialog(null, "YOu are At First Record", "Error Message", JOptionPane.ERROR_MESSAGE); 
			 }
			 }
			   
			   }
			   else if (action.equals("1")){
			  if(Start){
			 pane.setSelectedIndex(4);
			 Start=false;}
			 else{
			 //connectivity
			
			try{if(rs.next())
			backAndForwardRecord();
			 }
			 catch(Exception eee){
			 JOptionPane.showMessageDialog(null,"YOu are At LAst Record", "Error Message3", JOptionPane.ERROR_MESSAGE); 
			 }
			 }
			 }
			 else if (action.equals("2")){
			 pane.setSelectedIndex(0);
			 }
			  else if (action.equals("3")){
			   pane.setSelectedIndex(1);}
			   else if (action.equals("4")){
			    pane.setSelectedIndex(2);}
			    else if (action.equals("5")){
				 pane.setSelectedIndex(3);}
				 else if (action.equals("6")){
				  pane.setSelectedIndex(4);}
				   else if (action.equals("7")){
				 pane.setSelectedIndex(5);
				  }
				   else if (action.equals("8")){
				   password();
				  lock();
				 }
				  else if (action.equals("9")){
				  JOptionPane.showMessageDialog(null,"UserName and Password require  ", "Warning!", JOptionPane.WARNING_MESSAGE); 
				userField.requestFocus();
				}
				 
				  else if (action.equals("10")){
				  showAbout();}
				   else if (action.equals("Login")){
				 if(userField.getText().equalsIgnoreCase("MBIT")&&passwordField.getText().equals("mbit"))
				 {unlock();
				 welcome();
				 userField.setText("");
				 passwordField.setText("");}
				else{
				JOptionPane.showMessageDialog(null, "Invalid UserName and Password", "Invalid", JOptionPane.ERROR_MESSAGE); 
				 userField.setText("");
				 passwordField.setText("");
				 }
				 }
				 else if (action.equals("Cancel"))
				 System.exit(0);
				  else if (action.equals("Query Designer"))
				queryDesigner= new QueryDesigner();
				  else if (action.equals("11")){
				  showAbout();
				   JOptionPane.showMessageDialog(null, "Thank you for using AddressBook 2010!",
                        "Thank you!", JOptionPane.INFORMATION_MESSAGE);
				  System.exit(0);}
            else if (action.equals("Exit")) {
                showAbout();
                JOptionPane.showMessageDialog(null, "Thank you for using AddressBook 2010!",
                        "Thank you!", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            } else if (action.equals("About")) {
               showAbout();
            }else if (action.equals("Snake")) {
			int b = JOptionPane.showConfirmDialog(this, "Would you like to play a game ??"
													, "Snake Game", JOptionPane.YES_NO_OPTION);
				if (b == JOptionPane.YES_OPTION) {
					 gameThread=new Thread(this);
			   gameThread.start();
				} 
              
            }
        }
		
		public void run(){
		
		new Snake();
		}
		
		public void showAbout(){
		JOptionPane.showMessageDialog(null, "Muhammad Abbas \n MBIT 2nd Semester\n Roll NO 4 ",
                        "Developer", JOptionPane.INFORMATION_MESSAGE);
		
		}
		
		// data base variable
	Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		boolean start=false;
		
	public void loadDataBase(){
			try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");}
			catch(ClassNotFoundException err){
		JOptionPane.showMessageDialog(null, err.toString(), "Error Message", JOptionPane.ERROR_MESSAGE); 
			
			}
				
				
				try{
				//connect to database
				con=DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=AddressBook.mdb;DriverID=22}");
						
				st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				
				
				rs=st.executeQuery("Select * From Person");
				}
				catch(Exception eee){
				JOptionPane.showMessageDialog(null, eee.toString(), "Error Message3", JOptionPane.ERROR_MESSAGE); 
				}
							
				start=true;
				
				
		}	//end of database connection
}// end of main claas


class Snake extends JFrame implements Runnable {

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
	final int ESCAPE=   4;
	final int ENTER=    5;
	
	int pieceSize = 10;

	int speed = 3;

	int fieldWidth = 30;
	int fieldHeight = 30;

	boolean inited = false;

	public Snake() {

		super("Snake");

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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
					case KeyEvent.VK_ESCAPE:
						direction = ESCAPE; break;
					case KeyEvent.VK_ENTER:
						direction = ENTER; break;
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
			case ESCAPE:stop();break;
			case ENTER :start();break;
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
				int b = JOptionPane.showConfirmDialog(this, "oh! Khalas \n your Score: " + score +
													"\n phir panga lainay ka?", "Snake", JOptionPane.YES_NO_OPTION);
				if (b == JOptionPane.YES_OPTION) {
					clear(g);
					init();
					start();
				} else	this.dispose();
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
			JOptionPane.showMessageDialog(this, "Congrats");
			this.dispose();
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
}
	
		
	
	
	
	


	
 
	