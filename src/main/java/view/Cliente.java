package view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import io.jsonwebtoken.Claims;
import model.vo.Clientes;

/**
 * Servlet implementation class Cliente
 */
@WebServlet("/Cliente")
public class Cliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cliente() {
        super();
        // TODO Auto-generated constructor stub
    }
    


    private void CORS(HttpServletResponse response) {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, HEAD");
		response.addHeader("Access-Control-Allow-Headers","X-PINGOTHER,Origin,X-Requested-With,Content-Type,Accept");
		response.addHeader("Access-Control-Max-Age", "1728000");
		
		
    }
 
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		this.CORS(response);
		
		String token = request.getHeader("TOKEN");
		
		if(token == null || token.equals("")) {
			response.setStatus(401);
			return;
		}
		try{
			Claims claims = Login.decodeJWT(token);
			String userId = claims.getId();
			if(userId != null) {
				
				//CHAMAR BANCO PARA PUXAR DADOS PARA O FRONT
				
				Clientes d1 = new Clientes("Gustavo","c", "c");
				Clientes d2 = new Clientes("Gustavo","c", "c");
				Clientes d3 = new Clientes("Gustavo","c", "c");
				Clientes[] lista = {d1,d2, d3};
				
				
				
				Gson gson = new Gson();
				String dividasJson = gson.toJson(lista);
				response.setContentType("application/json");
				response.getWriter().append(dividasJson);
				
			}else {
				response.setStatus(401);
			}
		}catch(Exception e) {
			response.setStatus(401);
			e.printStackTrace();
		}
	}

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
	}

}
