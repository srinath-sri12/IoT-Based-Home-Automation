

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeviceServlet
 */
@WebServlet("/DeviceServlet")
public class DeviceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public DeviceServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	List<String> list = new ArrayList<String>();  
    	try{  
    		Class.forName("com.mysql.jdbc.Driver");  
    		Connection con=DriverManager.getConnection(  
    				"jdbc:mysql://localhost:3306/project","sridhar","sumathi2506");   
    		Statement stmt=con.createStatement();  
    		ResultSet rs=stmt.executeQuery("select * from devices");
    		while(rs.next())
    		{
    			String s = rs.getString(2)+":"+rs.getBoolean(3);
    			list.add(s);
    		}
    		con.close();  
    	}catch(Exception e){ System.out.println(e);}  

    	String finalStr = String.join(",", list);
    	response.getWriter().write(finalStr);
    	System.out.println(finalStr);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String device_name = (String) request.getParameter("id");
		String status = (String) request.getParameter("status");
		System.out.println(device_name+status);
		//String b="true";
		boolean ap = false;
		if(status.equalsIgnoreCase("true"))
		{
			ap=true;
		}
				
	
		try{  
    		Class.forName("com.mysql.jdbc.Driver");  
    		Connection con=DriverManager.getConnection(  
    				"jdbc:mysql://localhost:3306/project","sridhar","sumathi2506");   
    		Statement stmt=con.createStatement(); 
    		ResultSet rs=stmt.executeQuery("select * from devices where Device_name = '"+device_name+"'");
    		Integer device_id = null;
    		while(rs.next())
    		{
    			device_id = rs.getInt(1);
    			break;
    		}
    		  String query = "update devices set status = ? where id = ?";
    	      PreparedStatement preparedStmt = con.prepareStatement(query);
    	      preparedStmt.setBoolean  (1, ap);
    	      preparedStmt.setInt(2, device_id);
    	      
    	      preparedStmt.executeUpdate();
		}catch (Exception e) {
			// TODO: handle exception
		}
	
	}

	private boolean strcmp(String status, String string) {
		// TODO Auto-generated method stub
		return false;
	}

}
