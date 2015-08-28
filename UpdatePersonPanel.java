import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
class UpdatePersonPanel extends JPanel implements ActionListener{

    private JLabel nameLbl;
    public JTextField nameField;
    private JLabel phoneLbl;
    public JTextField phoneField;
   
    private JLabel mobileNoLbl;
    public JTextField mobileNoField;
    private JLabel emailLbl;
    public JTextField emailField;
    private JLabel addressLbl;
    public JTextField addressField;
    private JButton searchButton;
    private JButton resetButton;
    private JButton updateButton;
    private String oldName;
    private final int x = 110,  y = 50;
    private final int l = 150,  w = 20;
    private final int xInc = 100,  yInc = 40;
	
	 // data base variable
	Connection con=null;
		Statement st=null;
		ResultSet rs=null;
	

    public UpdatePersonPanel() {

      

        setLayout(null);
loadDataBase();
        addFields();
		
        searchButton = new JButton("Search");
        resetButton = new JButton("Reset");
        updateButton = new JButton("Update");
        searchButton.setBounds(x + 30, y + 6 * yInc, l - 60, w);
        resetButton.setBounds(x + 30 + xInc, y + 6 * yInc, l - 60, w);
        updateButton.setBounds(x + 30 + xInc*2, y + 6 * yInc, l - 60, w);
		add(searchButton);
        add(resetButton);
        add(updateButton);

        
        searchButton.addActionListener(this);
        resetButton.addActionListener(this);
        updateButton.addActionListener(this);

        addImageAndBackGround();

    }

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
        phoneField.setEditable(false);
		add(phoneLbl);
        add(phoneField);

        

        mobileNoLbl = new JLabel("Mobile No.");
        mobileNoField = new JTextField(10);
        mobileNoLbl.setBounds(x, y + 2 * yInc, l, w);
        mobileNoField.setBounds(x + xInc, y + 2 * yInc, l, w);
		mobileNoField.setEditable(false);
        add(mobileNoLbl);
        add(mobileNoField);

        emailLbl = new JLabel("Email");
        emailField = new JTextField(10);
        emailLbl.setBounds(x, y + 3 * yInc, l, w);
        emailField.setBounds(x + xInc, y + 3 * yInc, l, w);
		emailField.setEditable(false);
        add(emailLbl);
        add(emailField);

        addressLbl = new JLabel("Address");
        addressField = new JTextField(10);
        addressLbl.setBounds(x, y + 4 * yInc, l, w);
        addressField.setBounds(x + xInc, y + 4 * yInc, l, w);
        addressField.setEditable(false);
		add(addressLbl);
        add(addressField);

    }

    private void addImageAndBackGround() {

        
        JLabel imageLbl = new JLabel("", new ImageIcon(getClass().getResource(
                "imgs/update.png")), SwingConstants.LEFT);
        imageLbl.setBounds(x + 450, y, 256, 256);
        add(imageLbl);

        JLabel bgLbl = new JLabel("", new ImageIcon(getClass().getResource(
                "imgs/bground4.jpg")), SwingConstants.LEFT);
        bgLbl.setBounds(0, 0, 835, 551);
        add(bgLbl);

    }
	
	private void resetFields(){
			nameField.setText(null);
			nameField.requestFocus();
            phoneField.setText(null);
            phoneField.setEditable(false);
                  
            mobileNoField.setText(null);
            mobileNoField.setEditable(false);
            emailField.setText(null);
            emailField.setEditable(false);
            addressField.setText(null);
            addressField.setEditable(false);
			searchButton.setEnabled(true);
			}
	private void editFields(){
	   phoneField.setEditable(true);
                    
                    mobileNoField.setEditable(true);
                    emailField.setEditable(true);
                    addressField.setEditable(true);
	
	}
	public void loadDataBase(){
			
				try{
				//connect to database
				con=DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=AddressBook.mdb;DriverID=22}");
				//con=DriverManager.getConnection("jdbc:odbc:AddressBook");
				st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				}
				catch(Exception eee){
				JOptionPane.showMessageDialog(null, eee.toString(), "Error Message2", JOptionPane.ERROR_MESSAGE);
	}}
	  public void getData() {

           try{
			
            nameField.setText(rs.getString(1));
            phoneField.setText(rs.getString(2));
           
            mobileNoField.setText(rs.getString(3));
            emailField.setText(rs.getString(4));
            addressField.setText(rs.getString(5));
			}
			
			catch (Exception e) {
            JOptionPane.showMessageDialog(null,""+e.toString(),"Communication Error",JOptionPane.WARNING_MESSAGE);
        }
	
	}
	
	 public void actionPerformed(ActionEvent aEvent) {
	  String command = aEvent.getActionCommand();
	  if (command.equals("Reset")) {
                resetFields();
				nameField.setText(null);
				updateButton.setText("Update");
            }
	else if (command.equals("Update")) {
	if(nameField.getText().length()==0){
	JOptionPane.showMessageDialog(null,"Type Name and press Search ","Communication Error",JOptionPane.WARNING_MESSAGE);
	}
	else{
	updateButton.setText("Save");
	searchButton.setEnabled(false);
	editFields();
	 }
	 }
	else if (command.equals("Search")) {
	if(nameField.getText().length()==0){
	JOptionPane.showMessageDialog(null,"Type Name for Search","Communication Error",JOptionPane.WARNING_MESSAGE);
	}
	else{
	
	String SQL = "SELECT Name,Phone,Mobile,Email,address FROM Person WHERE Name ='" +nameField.getText()+ "'";
			try{
				rs=st.executeQuery(SQL);
				if(rs.next())
				getData();
				}
		catch (Exception e) {
            JOptionPane.showMessageDialog(null,""+e.toString(),"Communication Error",JOptionPane.WARNING_MESSAGE);
        }
	 }
	 }
	else if (command.equals("Save")) {
	String SQL = "UPDATE Person SET Name = '"+nameField.getText()+"', Phone = '"+phoneField.getText()+"', Mobile = '"+mobileNoField.getText()+"', Email = '"+emailField.getText()+"', Address = '"+addressField.getText()+"' WHERE name = '"+nameField.getText()+"'"; 
        //Communicates with database
       try {
            st.executeUpdate(SQL);
			resetFields();
			updateButton.setText("Update");
			
           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,""+e.toString(),"Communication Error",JOptionPane.WARNING_MESSAGE);
        }
	
	 }
}
	 } // end of Update class