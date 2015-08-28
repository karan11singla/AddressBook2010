import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
class DeletePersonPanel extends JPanel implements ActionListener,ItemListener{

    private JLabel nameLbl;
    private JLabel deleteAll;
    private JTextField nameField;
    private JButton deleteButton;
    private JButton resetButton;
	private JButton deleteAllButton;
	private JLabel warnLbl;
	private JComboBox nameBox;
	private JLabel chooseNameLabel;
	private JButton refresh;
	private final int x = 110,  y = 140;
    private final int l = 150,  w = 20;
    private final int xInc = 100,  yInc = 40;
	 // data base variable
	Connection con=null;
		Statement st=null;
		ResultSet rs=null;
	
    public DeletePersonPanel() {

     

        setLayout(null);

        addFields();

        deleteButton = new JButton("Delete");
        resetButton = new JButton("Reset");
		 deleteAllButton = new JButton("Delete All");
		
		chooseNameLabel=new JLabel("Choose Name");
		chooseNameLabel.setBounds(x,y-yInc-50,l,w);
		add(chooseNameLabel);
		nameBox=new JComboBox();
		nameBox.setBounds(x+xInc,y-yInc-50,l,w);
		nameBox.addItemListener(this);
		add(nameBox);
		
		refresh=new JButton("Refresh");
		refresh.setBounds(x+xInc*3,y-yInc-50,l-60,w);
		add(refresh);
		refresh.addActionListener(this);
		
		warnLbl = new JLabel();
		warnLbl.setBounds(x,y-yInc,l+l,w+20);
		add(warnLbl);
		 
        deleteButton.setBounds(x + 30, y + yInc, l - 60, w);
        resetButton.setBounds(x + 30 + xInc, y + yInc, l - 60, w);
		deleteAllButton.setBounds(x + 30 + xInc*2, y + yInc, l - 60, w);
        add(deleteButton);
        add(resetButton);
		add(deleteAllButton);
      
        deleteButton.addActionListener(this);
        resetButton.addActionListener(this);
		deleteAllButton.addActionListener(this);
		
        addImageAndBackGround();
		loadDataBase();
    }

    private void addFields() {

       

        nameLbl = new JLabel("Name");
        nameField = new JTextField(10);
        nameLbl.setBounds(x, y, l, w);
        nameField.setBounds(x + xInc, y, l, w);
        add(nameLbl);
        add(nameField);
        deleteAll = new JLabel("Note: To delete all records, enter 'Delete All'");
        deleteAll.setBounds(x, y + 2 * yInc, l + 100, w);
        add(deleteAll);

    }
	private void deleteLbl(){
	warnLbl.setForeground(Color.white);
	warnLbl.setText("    Record Deleted Successfully");
	warnLbl.setIcon(new ImageIcon(getClass().getResource(
                "icons/Apply.PNG")));
		}
		
		private void clearLabel(){
	warnLbl.setText("");
	warnLbl.setIcon(null);}

    private void addImageAndBackGround() {

        

        JLabel imageLbl = new JLabel("", new ImageIcon(getClass().getResource(
                "imgs/p_delete.png")), SwingConstants.LEFT);
        imageLbl.setBounds(x + 450, y - 90, 256, 256);
        add(imageLbl);

        JLabel bgLbl = new JLabel("", new ImageIcon(getClass().getResource(
                "imgs/bground4.jpg")), SwingConstants.LEFT);
        bgLbl.setBounds(0, 0, 835, 551);
        add(bgLbl);

    }
	
		public void loadDataBase(){
			 try {
           Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				} 
	   catch (Exception e) {
           JOptionPane.showMessageDialog(null,""+e.getMessage(),"JDBC Driver Error",JOptionPane.WARNING_MESSAGE);
			}
				try{
				//connect to database
				con=DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=AddressBook.mdb;DriverID=22}");
				//con=DriverManager.getConnection("jdbc:odbc:AddressBook");
				st=con.createStatement();
				
				
				}
				catch(Exception eee){
				JOptionPane.showMessageDialog(null, eee.toString(), "Error Message2", JOptionPane.ERROR_MESSAGE);
				}
	}
	
	public void itemStateChanged(ItemEvent e){
	String name=(String)nameBox.getSelectedItem();
	nameField.setText(name);
	clearLabel();
	}
	 public void actionPerformed(ActionEvent aEvent) {
	   if(aEvent.getActionCommand() == "Refresh"){
	   nameBox.removeAllItems();
	   nameBox.addItem(null);
try{	  rs=st.executeQuery("Select * from Person");
	  while(rs.next()){
				nameBox.addItem(rs.getString(1));
	}			}
	catch(Exception eee){}
	   
	   }
	 else if (aEvent.getActionCommand() == "Reset") {
                nameField.setText(null);
				clearLabel();
				}
	else if (aEvent.getActionCommand() == "Delete") {
	if(nameField.getText().length()==0){
	JOptionPane.showMessageDialog(null,"Type Name for Deletion","Communication Error",JOptionPane.WARNING_MESSAGE);
	}
	else{
	String SQL = "DELETE FROM Person WHERE Name = '" +nameField.getText()+ "'";
	try {
	
	int selectedValue;
	selectedValue=JOptionPane.showConfirmDialog(null, "Dou You Want TO Delete "+nameField.getText(), "Confirmation", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(selectedValue==0){
			st.executeUpdate(SQL);  
			nameField.setText(null);
			deleteLbl();
           } 
		   else{
		   nameField.setText(null);
		   }
		   }
	catch (Exception e) {
            JOptionPane.showMessageDialog(null,""+e.toString(),"Communication Error",JOptionPane.WARNING_MESSAGE);
        }
	
	}
	}
	else if (aEvent.getActionCommand() == "Delete All") {
			if(nameField.getText().equalsIgnoreCase("Delete All")){
			int selectedValue;
			selectedValue=JOptionPane.showConfirmDialog(null, "Dou You Want TO Delete All Records", "Confirmation", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(selectedValue==0){
			String SQL = "DELETE FROM Person ";
	try {
            st.executeUpdate(SQL);  
			nameField.setText(null);
			deleteLbl();
           } 
	catch (Exception e) {
            JOptionPane.showMessageDialog(null,""+e.toString(),"Communication Error",JOptionPane.WARNING_MESSAGE);
			
			
		}
			}else
			{nameField.setText(null);
			}
			
	
	
	}else{
	JOptionPane.showMessageDialog(null,"Type  'DELETE ALL'  in TextField For Delettion","Communication Error",JOptionPane.WARNING_MESSAGE);
	}
	 
	 
	} 
	 }
	}//end of delete person