import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;
import java.util.*;
import java.io.*;
class QueryDesigner extends JFrame implements ListSelectionListener,ActionListener,ItemListener {

    private JLabel tableLbl;
    private JComboBox tableBox;
    private JLabel attributesLbl;
	private JList  attributesList;
    private JTextField conditionField;
	private JLabel qTypeLbl;
	 private JComboBox qTypeBox;
    private JLabel whereLbl;
    private JTextField whereField;
	private JLabel oprationLbl;
    private JComboBox oprationBox;
	private JLabel valueLbl;
    private JTextField valueField;
    private JRadioButton andButton;
    private JRadioButton orButton;
    private JButton executeButton;
	private JButton genrateButton;
	private JButton refreshButton;
	private JLabel queryLbl;
    private JTextField queryField;
    private JButton resetButton;
	private JTable table;
	private JScrollPane personScrollPane;
	private JScrollPane attributeScrollPane;
	private JLabel imageLbl;
	private  JLabel bgLbl;
	private JFileChooser chooser;
	//private FileNameExtensionFilter filter;
	private JButton dbButton;
	private JLabel dbLbl;
	private JTextField dbField;
	private static String mdb;
    private final int x = 110,  y = 50;
    private int l = 150,  w = 20;
    private int xInc = 100,  yInc = 40;

    // data base variable
	private	Connection con=null;
	private	Statement st=null;
	private	ResultSet rs=null;
	private	DatabaseMetaData dbmd;
	private	ResultSetMetaData rsmd;
	final static String jdbcDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
	private	boolean start=false;
		
	public static void main(String[] arg){
	QueryDesigner obj=new QueryDesigner();
	
	}
	
	public QueryDesigner() {

       super("QueryDesigner");
	   try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception unused) {}
        setLayout(null);
	try{
			Class.forName(jdbcDriver);}
			catch(Exception errr){
			
			}		
			 setBounds(150, 150, 835, 551);
		addFields();
		 resetFields();
		
		
		addImageAndBackGround();
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setVisible(true);
		
    }
		
		
		private void loadDataBase(String database){
			
				try{
				//connect to database
				con=DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ="+database+";DriverID=22}");
				//con=DriverManager.getConnection(jdbcURL);
				st=con.createStatement();
				
				dbmd=con.getMetaData();
				}
				catch(Exception eee){
				
				JOptionPane.showMessageDialog(null, eee.toString(), "Error Message2", JOptionPane.ERROR_MESSAGE);
				}
					}	//end of database connection
		
    private void addFields() {

        dbLbl = new JLabel("Database :");
        dbField = new JTextField(10);
		dbLbl.setBounds(x, y-yInc, l, w);
        dbField.setBounds(x + xInc, y-yInc, l, w);
		dbField.setEditable(false);
		dbButton=new JButton("Browse");
		dbButton.setBounds(x+3*xInc,y-yInc,l-60,w);
		dbButton.addActionListener(this);
		add(dbLbl);
		add(dbField);
		add(dbButton);

        tableLbl = new JLabel("TableName :");
        tableBox = new JComboBox();
		tableBox.addItemListener(this);
        tableLbl.setBounds(x, y, l, w);
        tableBox.setBounds(x + xInc, y, l, w);

        add(tableLbl);
        add(tableBox);
		
       attributesList = new JList();
	   attributesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	   attributesList.addListSelectionListener(this);
	   attributesList.setSelectionBackground(Color.blue);
		attributeScrollPane=new JScrollPane( attributesList);
        attributeScrollPane.setBounds(x, y + 2*yInc, l, w*4);
		attributeScrollPane.setAutoscrolls(true);
        add(attributeScrollPane);

        String[] qType={"Select Query","Update Query","Delete Query"};
		qTypeLbl = new JLabel("Query Type");
        qTypeBox = new JComboBox(qType);
		qTypeBox.addItemListener(this);
        qTypeLbl.setBounds(x+2*xInc, y +  yInc, l-50, w);
		qTypeBox.setBounds(x + 3*xInc, y + yInc, l, w);
        add(qTypeLbl);
        add(qTypeBox);
		
		

        whereLbl = new JLabel("Where");
        whereField = new JTextField(10);
        whereLbl.setBounds(x+2*xInc, y + 2* yInc, l-50, w);
        whereField.setBounds(x + 3*xInc, y + 2*yInc, l, w);
        add(whereLbl);
        add(whereField);

		String[] oprant={"=","!=",">",">=","<","<="};
	   oprationLbl = new JLabel("Operations");
        oprationBox = new JComboBox(oprant);
		oprationBox.addItemListener(this);
        oprationLbl.setBounds(x+2*xInc, y + 3 * yInc, l, w);
       oprationBox.setBounds(x + 3*xInc, y + 3 * yInc, l, w);
        add(oprationLbl);
        add(oprationBox);

        valueLbl = new JLabel("Value");
        valueField = new JTextField(10);
        valueLbl.setBounds(x+2*xInc, y + 4 * yInc, l, w);
        valueField.setBounds(x + 3*xInc, y + 4 * yInc, l, w);
        add(valueLbl);
        add(valueField);
		
		queryLbl = new JLabel("Query");
        queryField = new JTextField(10);
        queryLbl.setBounds(x, y + 6 * yInc, l, w);
        queryField.setBounds(x +xInc, y + 6 * yInc, l*2, w);
        add(queryLbl);
        add(queryField);
		
		refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(this);
		refreshButton.setBounds(x+3*xInc , y , l - 60, w);
        add(refreshButton);
		
		executeButton = new JButton("Execute");
		executeButton.addActionListener(this);
		 executeButton.setBounds(x+10+4*xInc , y + 6 * yInc, l - 60, w);
        add(executeButton);
		
		resetButton = new JButton("Reset");
		resetButton.addActionListener(this);
		resetButton.setBounds(x + 4* xInc, y + 5 * yInc, l - 60, w);
		 add(resetButton);
		
		genrateButton = new JButton("Genrate Query");
		genrateButton.addActionListener(this);
		genrateButton.setBounds(x+3*xInc-60 , y + 5 * yInc, l - 20, w);
		add(genrateButton);
      
	  attributesLbl = new JLabel("Attributes");
	  attributesLbl.setBounds(x, y + yInc, l, w);
		add(attributesLbl);
       
           
    }
	//private void displayAtrributes(Vector listdata){}
	
	
	private void resetFields(){
			whereField.setText(null);
			valueField.setText(null);          
            queryField.setText(null);
                 
			}
			
			

    private void addImageAndBackGround() {

      

         imageLbl = new JLabel("", new ImageIcon(getClass().getResource(
                "imgs/query.PNG")), SwingConstants.LEFT);
        imageLbl.setBounds(x + 450, y-30, 256, 256);
        add(imageLbl);

         bgLbl = new JLabel("", new ImageIcon(getClass().getResource(
                "imgs/bground1.jpg")), SwingConstants.LEFT);
        bgLbl.setBounds(0, 0, 835, 551);
        add(bgLbl);

    }
	
	private void displayResult() {
		Vector rows = new Vector();
		 Vector cols = new Vector();
		
            Vector<String> row;

           try{
		    rsmd=rs.getMetaData();
           while (rs.next()) 
		   
		  
		   {
                row = new Vector<String>();
				for(int i=1;i<=rsmd.getColumnCount();i++)
				switch(rsmd.getColumnType(i)){
				case Types.VARCHAR:
				 row.add(rs.getString(i)); break;
				 case Types.INTEGER:
				  row.add(String.valueOf(rs.getInt(i))); break;
				  case Types.LONGVARCHAR:
				   row.add(rs.getString(i)); break;
				    case Types.DATE:
				   row.add(String.valueOf(rs.getDate(i))); break;
				  default :
				  System.out.println("Type is :"+rsmd.getColumnTypeName(i));
				}
               
               
               
                rows.add(row);
            }

           
		   for(int col = 1; col <=rsmd.getColumnCount(); col++) 
				cols.add(rsmd.getColumnLabel(col));
      

            validate();

            

        }
		catch(Exception eee){
				JOptionPane.showMessageDialog(null, eee.toString(), "Error Message2", JOptionPane.ERROR_MESSAGE);
	}
        
table=null;
        table = new JTable(rows, cols);
        table.setBackground(new Color(238, 238, 238));
		
		//table.setEnabled(false);
        personScrollPane = new JScrollPane(table);
        personScrollPane.setBounds(x, y+7*yInc, 500, 180);
        personScrollPane.setAutoscrolls(true);
        add(personScrollPane);
		
			remove(imageLbl);
            remove(bgLbl);
            addImageAndBackGround();
    }
	public void valueChanged(ListSelectionEvent lse){
	whereField.setText((String)attributesList.getSelectedValue());
	}
	
	public void itemStateChanged(ItemEvent ie){
	
	if(ie.getSource()==tableBox){
	String table=(String)tableBox.getSelectedItem();
	Vector colName=new Vector();
try{	
	ResultSet rs = st.executeQuery("SELECT * FROM "+ table);
	 rsmd = rs.getMetaData();
	 for(int col = 1; col <=rsmd.getColumnCount(); col++) 
	 colName.add(rsmd.getColumnLabel(col));
	 attributesList.setListData(colName);
      
	  
	}
	catch(Exception ee){
	System.out.println(ee.toString());
	}
	
	
	}}
	  
	 public void actionPerformed(ActionEvent aEvent) {
	 if(aEvent.getActionCommand().equals("Browse")){
	   chooser=new JFileChooser();
	
		int returnVal=chooser.showOpenDialog(null);
	 if(returnVal==JFileChooser.APPROVE_OPTION)
		{
		mdb=(String.format("%s",new File(chooser.getSelectedFile().getPath())));
		dbField.setText(String.format("%s",new File(chooser.getSelectedFile().getName())));			
		}				
	 }
	 
	 else if(aEvent.getActionCommand().equals("Refresh")){
	 if(dbField.getText().length()==0){
	 JOptionPane.showMessageDialog(null, "Select DataBase First", "Error Message", JOptionPane.ERROR_MESSAGE); 
	 }
	 else{
	 loadDataBase(mdb);
	 try{
	 tableBox.removeAllItems();
	  String[] tableTypes = { "TABLE" };
      ResultSet allTables = dbmd.getTables(null,null,null,tableTypes);
      while(allTables.next()) {
        String table_name = allTables.getString("TABLE_NAME");
         tableBox.addItem(table_name);
        
	 
	 }}
	 catch(Exception ee){}
	 }}
	 else if(aEvent.getActionCommand().equals("Reset")){
	
	resetFields();
	 }
	 else if(aEvent.getActionCommand().equals("Execute")){
	 int typeIndex=qTypeBox.getSelectedIndex();
	 if(typeIndex==0){
	 try{
	 rs=st.executeQuery(queryField.getText());
	 displayResult();
	 }
	 catch(Exception ee){}
	 }
	 else if(typeIndex==2){
	 try{
	 st.executeUpdate(queryField.getText());
	 }catch(Exception eee){}
	 }}
	 else if(aEvent.getActionCommand().equals("Genrate Query")){
	  int typeIndex=qTypeBox.getSelectedIndex();
	  String table=(String)tableBox.getSelectedItem();
	   String opration=(String)oprationBox.getSelectedItem();
	  if(typeIndex==0){
	  if(whereField.getText().length()==0&&valueField.getText().length()==0)
	   queryField.setText("Select * from "+table);
	   else  if(whereField.getText().length()==0||valueField.getText().length()==0)
	   JOptionPane.showMessageDialog(null, "Conditon is  Incorrect", "Error Message", JOptionPane.ERROR_MESSAGE); 
	  else
	  queryField.setText("Select * from "+table+" where "+whereField.getText()+" "+opration+" "+valueField.getText());
	  }
	  else if(typeIndex==1){
	   if(whereField.getText().length()==0&&valueField.getText().length()==0)
	   JOptionPane.showMessageDialog(null, "Condition is required", "Error Message", JOptionPane.ERROR_MESSAGE); 
	  else  if(whereField.getText().length()==0||valueField.getText().length()==0)
	   JOptionPane.showMessageDialog(null, "Conditon is  Incorrect", "Error Message", JOptionPane.ERROR_MESSAGE); 
	  else
	   queryField.setText("Update "+table+" set columnName=columnValue,.. "+" where "+whereField.getText()+" "+opration+" "+valueField.getText());
	  }
	  else if(typeIndex==2){
	  if(whereField.getText().length()==0&&valueField.getText().length()==0)
	    queryField.setText("Delete  from "+table);
	  else  if(whereField.getText().length()==0||valueField.getText().length()==0)
	   JOptionPane.showMessageDialog(null, "Conditon is Incorrect", "Error Message", JOptionPane.ERROR_MESSAGE); 
	  else
	  queryField.setText("Delete  from "+table+" where "+whereField.getText()+" "+opration+" "+valueField.getText());
	 
	  }
	 //resetFields();
	 }
	  }// end of ActionPerformed
	}//end of  Class