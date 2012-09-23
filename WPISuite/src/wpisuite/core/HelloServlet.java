package wpisuite.core;

import java.io.*;

import javax.servlet.http.*;
import javax.servlet.*;

import wpisuite.models.*;
public class HelloServlet extends HttpServlet 
{

	MockDataStore data;
	
	public HelloServlet()
	{
		data = new MockDataStore();
	}
	
	public void doGet (HttpServletRequest req,
                       HttpServletResponse res) throws ServletException, IOException
	{
        PrintWriter out = res.getWriter();
        out.println(data.getDude(Integer.parseInt(req.getPathInfo().substring(1))).toJSON());
        out.close();
	}
	
	public void doPost (HttpServletRequest req,
            HttpServletResponse res) throws ServletException, IOException
    {
		BufferedReader in = req.getReader();
		data.addDude(in.readLine());
    }
}