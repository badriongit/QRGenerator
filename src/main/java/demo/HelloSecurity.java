package demo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloSecurity {

	public class ResponseObj implements Serializable{

		//private static final long serialVersionUID = 1L;
		
		private String response;
		
		public ResponseObj(String res){
			this.response=res;
		}

		public String getResponse() {
			return response;
		}

		public void setResponse(String response) {
			this.response = response;
		}
		
	}
	
	@RequestMapping("/hello")
	public @ResponseBody String greeting(){
		return "Hello!!";
	}
//	
//	@RequestMapping(method=RequestMethod.OPTIONS, value="/hello")
//	public @ResponseBody String optionsHello(){
//		return "Hello options!!";
//	}
	
	@RequestMapping("/json")
	public @ResponseBody ResponseObj getJson(){
		ResponseObj jsonObj = new ResponseObj("Success");
		return jsonObj;
	}
	
	
	@RequestMapping(value = "/image", method = RequestMethod.GET, produces = "image/jpg")
	public ResponseEntity<byte[]> testphoto() throws IOException {
		try {
	        // Retrieve image from the classpath.
	        InputStream is = this.getClass().getResourceAsStream("tirupati.jpeg"); 

	        // Prepare buffered image.
	        BufferedImage img = ImageIO.read(is);

	        // Create a byte array output stream.
	        ByteArrayOutputStream bao = new ByteArrayOutputStream();

	        // Write to output stream
	        ImageIO.write(img,"JPG", bao);

	        final HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.IMAGE_JPEG);
	        
	        return new ResponseEntity<byte[]>(bao.toByteArray(), headers, HttpStatus.CREATED);
	        
	       // return bao.toByteArray();
	    } catch (IOException e) {
	       // Logger.error(e);
	        throw new RuntimeException(e);
	    }
	}
	
	@RequestMapping(value = "/qr", method = RequestMethod.GET, produces = "image/jpg")
	public ResponseEntity<byte[]> testphoto(@RequestParam("text") String qrText) throws Exception {
		try {
			
			
			QRGeneratorUtils.storeQRCode(qrText);
			
			
	        // Retrieve image from the classpath.
	        InputStream is = QRGeneratorUtils.getIStream("/Users/badrionapple/Documents/QR/newQR.png");//this.getClass().getResourceAsStream("/Users/badrionapple/Documents/CrunchifyQR.png"); 

	        // Prepare buffered image.
	        BufferedImage img = ImageIO.read(is);

	        // Create a byte array output stream.
	        ByteArrayOutputStream bao = new ByteArrayOutputStream();

	        // Write to output stream
	        ImageIO.write(img,"PNG", bao);

	        final HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.IMAGE_PNG);
	        
	        return new ResponseEntity<byte[]>(bao.toByteArray(), headers, HttpStatus.CREATED);
	        
	       // return bao.toByteArray();
	    } catch (IOException e) {
	       // Logger.error(e);
	        throw new RuntimeException(e);
	    }
	}
}
