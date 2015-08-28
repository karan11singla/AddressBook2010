import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
class SearchPersonPanel extends JPanel implements ActionListener{

    private JLabel nameLbl;
    private JTextField nameField;
    private JButton searchButton;
    private JButton resetButton;
    private JScrollPane personScrollPane;
    JLabel imageLbl;
    JLabel bgLbl;
    private final int x = 410,  y = 30;
    private final int l = 150,  w = 20;
    private final int xInc = 100,  yInc = 40;
	
	 // data base variable
	Connection con=null;
		Statement st=null;
		ResultSet rs=null;

    public SearchPersonPanel() {

        

        setLayout(null);

        addFields();

        searchButton = new JButton("Search");
        resetButton = new JButton("Reset");
        searchButton.setBounds(x + 30, y + yInc, l - 60, w);
        resetButton.setBounds(x + 30 + xInc, y + yInc, l - 60, w);
        add(searchButton);
        add(resetButton);

       
        searchButton.addActionListener(this);
        resetButton.addActionListener(this);

        addImageAndBackGround();

    }

    private void addFields() {

       

        nameLbl = new JLabel("Name");
        nameField = new JTextField(10);
        nameLbl.setBounds(x, y, l, w);
        nameField.setBounds(x + xInc, y, l, w);
        add(nameLbl);
        add(nameField);

    }

    private void addImageAndBackGround() {

       

        imageLbl = new JLabel("", new ImageIcon(getClass().getResource(
                "imgs/p_search.png")), SwingConstants.LEFT);
        imageLbl.setBounds(x, y + 80, 256, 256);
        add(imageLbl);

        bgLbl = new JLabel("", new ImageIcon(getClass().getResource(
                "imgs/bground4.jpg")), SwingConstants.LEFT);
        bgLbl.setBounds(0, 0, 835, 551);
        add(bgLbl);

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
	
	 public void actionPerformed(ActionEvent aEvent) {
	 
	  if (aEvent.getActionCommand() == "Reset") {
                nameField.setText(null);}
		else  if (aEvent.getActionCommand() == "Search") {
				if(nameField.getText().length()==0){
	JOptionPane.showMessageDialog(null,"Type Name for Search","Communication Error",JOptionPane.WARNING_MESSAGE);
	}
	else{
                loadDataBase();
				try{
		 String SQL = "SELECT Name,Phone,Mobile,Email,address FROM Person WHERE Name ='" +nameField.getText()+ "'";
			
				rs=st.executeQuery(SQL);
				rs.next();
				 Vector rows = new Vector();
                    Vector<String> row = new Vector<String>();

                    row.add("Name: ");
                    row.add(rs.getString(1));
                    rows.add(row);

                    row = new Vector<String>();
                    row.add("Phone: ");
                    row.add(rs.getString(2));
                    rows.add(row);

                   

                    row = new Vector<String>();
                    row.add("Mobile No.: ");
                    row.add(rs.getString(3));
                    rows.add(row);

                    row = new Vector<String>();
                    row.add("Email: ");
                    row.add(rs.getString(4));
                    rows.add(row);

                    row = new Vector<String>();
                    row.add("Address: ");
                    row.add(rs.getString(5));
                    rows.add(row);
						con.close();
                    Vector cols = new Vector();
                    cols.add("Attributes");
                    cols.add("Person");

                    displayResult(rows, cols);
                    validate();

                    remove(imageLbl);
                    remove(bgLbl);
                    addImageAndBackGround();

                    nameField.setText(null);
				
				}
				
				catch(Exception err){
				JOptionPane.showMessageDialog(null, "Record Not Found", "Error Message", JOptionPane.INFORMATION_MESSAGE); 
				
				}
				}
				}
	 
	 }
	 
	  private void displayResult(Vector rows, Vector cols) {
		          
            JTable Table = new JTable(rows, cols);
            Table.setBackground(new Color(238, 238, 238));
			Table.setEnabled(false);
            personScrollPane = new JScrollPane(Table);
            personScrollPane.setBounds(x - 350, y + 100, 330, 105);
            personScrollPane.setAutoscrolls(true);
            add(personScrollPane);

        }
	}//end of search panael