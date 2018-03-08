package API;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

import models.SearchModel;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

@Path("/Search")
public class SearchController {

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSearchJSON(@QueryParam("q") String q) throws IOException {
		Object resource = "";
		Gson gson = new Gson();
		if(!q.equals("")) {
		
		String google = "http://www.google.com/search?q=";
        String charset = "UTF-8";

        ArrayList<SearchModel> retornoBusca = new ArrayList<SearchModel>();
        
        Elements links = Jsoup.connect(google + URLEncoder.encode(q)).userAgent("").get().select(".g>.r>a");

        for (Element link : links) {
            String title = link.text();
            String url = link.absUrl("href"); 
            url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");

            if (!url.startsWith("http")) {
                continue; 
            }
            SearchModel aux = new SearchModel();
            aux.setTitle(title);
            aux.setUrl(url);
            retornoBusca.add(aux);
        }
        resource = retornoBusca;
		}else {
			resource = "Invalid input";
		}
		return Response.status(200).entity(gson.toJson(resource)).header("Access-Control-Allow-Origin", "*").build();
	}
}
