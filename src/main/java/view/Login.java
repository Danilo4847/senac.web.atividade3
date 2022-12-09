package view;

import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;
import org.json.JSONArray; 
import org.json.JSONObject;  
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import com.google.gson.JsonArray;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import model.vo.Logins;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String SENHA = "123456789";
    private static final String jwtIssuer = "DEMO_JWT";
    
    Logins login = new Logins();
    /**
     * @see HttpServlet#HttpServlet()
     */
    


    private void CORS(HttpServletResponse response) {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, HEAD");
		response.addHeader("Access-Control-Allow-Headers","X-PINGOTHER,Origin,X-Requested-With,Content-Type,Accept");
		response.addHeader("Access-Control-Max-Age", "1728000");
		
		
    }
    
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String createJWT(String id, String issuer, String subject, long ttlMillis) {
    	SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    	long nowMillis = System.currentTimeMillis();
    	Date now = new Date(nowMillis);
    	byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SENHA);
    	Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
    	JwtBuilder builder = Jwts.builder().setId(id)
    	.setIssuedAt(now)
    	.setSubject(subject)
    	.setIssuer(issuer)
    	.signWith(signatureAlgorithm, signingKey);
    	if (ttlMillis > 0) {
    	long expMillis = nowMillis + ttlMillis;
    	Date exp = new Date(expMillis);
    	builder.setExpiration(exp);
    	}
    	return builder.compact();
    	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		// codificação
		String jwtId = "SOME ID 1234";
		String jwtIssuer = "SOME APPLICATION";
		String jwtSubject = "SOME USER NAME";
		int jwtTimeToLive = 800000;//milliseconds
		String jwt = createJWT(jwtId,jwtIssuer,jwtSubject, jwtTimeToLive);
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.getWriter().append("JWT: <br/>").append(jwt);
		
		//decodificação
		Claims claims = decodeJWT(jwt);
		response.getWriter().append("===Decode===<br/>");
		response.getWriter().append("<br/>"+claims.getId());
		response.getWriter().append("<br/>"+claims.getIssuer());
		response.getWriter().append("<br/>"+claims.getSubject());


	}
	
	public static Claims decodeJWT(String jwt) {
		Claims claims = Jwts.parser()
		.setSigningKey(DatatypeConverter.parseBase64Binary(SENHA))
		.parseClaimsJws(jwt).getBody();
		return claims;
		}
	
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.CORS(response);
		
		//System.out.println(request.toString());
		//Teste
		final String usuarioValido = "danilo";
		final String usuarioValidoSenha = "123456";
	
		String requestBody=request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		
	
	
		
		JSONObject objeto =new JSONObject(requestBody);
	
		String login =objeto.getString("login");
		String senha =objeto.getString("password");

	


		
		if(login.equals(usuarioValido) && senha.equals(usuarioValidoSenha)) {
			String jwtId = usuarioValido ;
			String jwtSubject = usuarioValido;
			int jwtTimeToLive = 800000;
			String jwt = createJWT(jwtId,jwtIssuer,jwtSubject, jwtTimeToLive);
			response.getWriter().append(jwt);
			response.setStatus(200);
		}else {
			response.setStatus(401);
			
			
		}
	
	
		
	}

}
