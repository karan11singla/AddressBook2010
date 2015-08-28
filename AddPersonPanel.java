import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
class AddPersonPanel extends JPanel implements ActionListener {

    private JLabel nameLbl;
    private JTextField nameField;
    private JLabel phoneLbl;
    private JTextField phoneField;
    private JLabel mobileNoLbl;
    private JTextField mobileNoField;
    private JLabel emailLbl;
    private JTextField emailField;
    private JLabel addressLbl;
    private JTextField addressField;
    private JButton addButton;
    private JButton resetButton;
	private JLabel warnLbl;
	private Calendar c;
    private final int x = 110,  y = 50;
    private int l = 150,  w = 20;
    private int xInc = 100,  yInc = 40;

    // data base variable
	Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		boolean start=false;
		
	
	
	public AddPersonPanel() {

       

        setLayout(null);
		
		warnLbl = new JLabel();
		warnLbl.setBounds(x,y-yInc,l+l,w+20);
		add(warnLbl);
			
		addFields();
		 resetFields();
        addButton = new JButton("Add");
        resetButton = new JButton("Reset");
        addButton.setBounds(x + 30, y + 6 * yInc, l - 60, w);
        resetButton.setBounds(x + 30 + xInc, y + 6 * yInc, l - 60, w);
        add(addButton);
        add(resetButton);

       
        addButton.addActionListener(this);
        resetButton.addActionListener(this);

        addImageAndBackGround();

    }
	
	
	private void insertLbl(){
	warnLbl.setForeground(Color.red);
	warnLbl.setText("     Fields Are Emptty");
	warnLbl.setIcon(new ImageIcon(getClass().getResource(
                "icons/Danger.PNG")));
	}
	
	
	private void clearLabel(){
	warnLbl.setText("");
	warnLbl.setIcon(null);
	}
	
	
	private void insertRecordLbl(){
	warnLbl.setForeground(Color.white);
	warnLbl.setText("    Record Inserted Successfully");
	warnLbl.setIcon(new ImageIcon(getClass().getResource(
                "icons/Apply.PNG")));
		}
		
		
		private void loadDataBase(){
			try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");}
			catch(Exception errr){
			try{
			Class.forName("com.ms.jdbc.odbc.jdbcodbcDriver");
			}
			catch(Exception err){}
			}		
				try{
				//connect to database
				con=DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=AddressBook.mdb;DriverID=22}");
				//con=DriverManager.getConnection("jdbc:odbc:AddressBook");
				st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				}
				catch(Exception eee){
				
				JOptionPane.showMessageDialog(null, eee.toString(), "Error Message2", JOptionPane.ERROR_MESSAGE);
				}
					}	//end of database connection
		
    private void addFields() {

        

        nameLbl = new JLabel("Name");
        nameField = new JTextField(10);
        nameLbl.setBounds(x, y, l, w);
        nameField.setBounds(x + xInc, y, l, w);

        add(nameLbl);
        add(nameField);

        phoneLbl = new JLabel("Phone");
        phoneField = new JTextField(10);
        phoneLbl.setBounds(x, y + yInc, l, w);
        phoneField.setBounds(x + xInc, y + yInc, l, w);
        add(phoneLbl);
        add(phoneField);

        

        mobileNoLbl = new JLabel("Mobile No.");
        mobileNoField = new JTextField(10);
        mobileNoLbl.setBounds(x, y + 2 * yInc, l, w);
        mobileNoField.setBounds(x + xInc, y + 2 * yInc, l, w);
        add(mobileNoLbl);
        add(mobileNoField);

        emailLbl = new JLabel("Email");
        emailField = new JTextField(10);
        emailLbl.setBounds(x, y + 3 * yInc, l, w);
        emailField.setBounds(x + xInc, y + 3 * yInc, l, w);
        add(emailLbl);
        add(emailField);

        addressLbl = new JLabel("Address");
        addressField = new JTextField(10);
        addressLbl.setBounds(x, y + 4 * yInc, l, w);
        addressField.setBounds(x + xInc, y + 4 * yInc, l, w);
        add(addressLbl);
        add(addressField);

    }
	
	private void resetFields(){
			nameField.setEditable(false);
			nameField.setText(null);
			phoneField.setText(null);
            phoneField.setEditable(false);
           
            mobileNoField.setText(null);
            mobileNoField.setEditable(false);
            emailField.setText(null);
            emailField.setEditable(false);
            addressField.setText(null);
            addressField.setEditable(false);
			
			}
			
			private void editFields(){
					nameField.setEditable(true);
					phoneField.setEditable(true);
                    
                    mobileNoField.setEditable(true);
                    emailField.setEditable(true);
                    addressField.setEditable(true);
	
	}

    private void addImageAndBackGround() {

      

        JLabel imageLbl = new JLabel("", new ImageIcon(getClass().getResource(
                "imgs/p_add.png")), SwingConstants.LEFT);
        imageLbl.setBounds(x + 450, y, 256, 256);
        add(imageLbl);

        JLabel bgLbl = new JLabel("", new ImageIcon(getClass().getResource(
                "imgs/bground4.jpg")), SwingConstants.LEFT);
        bgLbl.setBounds(0, 0, 835, 551);
        add(bgLbl);

    }
	  
	 public void actionPerformed(ActionEvent aEvent) {
	  if(aEvent.getActionCommand().equals("Add")){
	  clearLabel();
	  editFields();
	  addButton.setText("Save");
	  }
	  else if(aEvent.getActionCommand().equals("Save")){
	 loadDataBase();
	 if(nameField.getText().length()==0&&phoneField.getText().length()==0&&mobileNoField.getText().length()==0&&emailField.getText().length()==0&&addressField.getText().length()==0)
		{insertLbl();
		addButton.setText("Add");
	 resetFields();
		}
	else{
	clearLabel();
	 
	try{
	 st.executeUpdate("INSERT INTO Person VALUES('" + nameField.getText() + "','" + phoneField.getText() + "','" + mobileNoField.getText() + "','"+emailField.getText()+"','" + addressField.getText() + "')");
	insertRecordLbl();
	addButton.setText("Add");
	 resetFields();
	}
	 catch(Exception ee){
	
	JOptionPane.showMessageDialog(null,"Name already exists", "Error Message", JOptionPane.ERROR_MESSAGE); 
	}
	 }}
	 else {
	 clearLabel();
	 resetFields();
	 addButton.setText("Add");}
	 }
	}//end of Add Person Class