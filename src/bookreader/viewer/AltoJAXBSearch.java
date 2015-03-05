package bookreader.viewer;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.exlibris.dps.alto.json.Book;
import com.exlibris.dps.alto.json.Box;
import com.exlibris.dps.alto.json.Match;
import com.exlibris.dps.alto.json.Par;
import com.exlibris.dps.alto.xsd.Alto;
import com.exlibris.dps.alto.xsd.Alto.Layout.Page;
import com.exlibris.dps.alto.xsd.BlockType;
import com.exlibris.dps.alto.xsd.StringType;
import com.exlibris.dps.alto.xsd.TextBlockType;
import com.exlibris.dps.alto.xsd.TextBlockType.TextLine;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Deprecated
public class AltoJAXBSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String ACTION="action";
	private final static String SEARCH_PARAM="searchParam";
	private final static String TERM="term";
	private final static String ALTO_FILE_PATH = "altoFilePath";

	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		try{
		long startTime = System.currentTimeMillis();
		String action = request.getParameter(ACTION);
		if(SEARCH_PARAM.equals(action)){
			String term = request.getParameter(TERM);
		String json = getJSON(term,request);
		response.setStatus(200);
		response.setContentType("text/javascript");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("br.BRSearchCallback(" + json + ")");
		System.out.println(System.currentTimeMillis() - startTime);
		}
		}
		catch (Exception e){
			e.printStackTrace();
			String redirect = "error.jsp";
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(redirect);
			requestDispatcher.forward(request, response);
		}
	}


	private String getJSON(String term,HttpServletRequest request) {


		Match match = new Match("{{{"+term+"}}}");

		Par par = new Par(2391, 2328, 2742, 2160, 1176, 4524, 1);

		String altoFilePath = request.getParameter(ALTO_FILE_PATH);

		Alto alto = getAltoFromURL(altoFilePath);

		Book book = new Book("book", "reference", true,490, 759921, true);

		par.getBoxes().addAll(getBoxList(alto,term));

		match.getPar().add(par);
		book.getMatches().add(match);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(book);
		return json;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	public static void main(String[] args) throws Exception {

//		Alto alto = getAlto(altoFilePath);
//
//		getBoxList(alto,"die");
	}

	private List<Box> getBoxList(Alto alto,String term) {
		List<Box> allBoxs = new ArrayList<Box>();
		for (Page page : alto.getLayout().getPage()) {
			for (BlockType blockType : page.getPrintSpace()
					.getTextBlockOrIllustrationOrGraphicalElement()) {
				if (blockType instanceof TextBlockType) {
					TextBlockType textBlockType = (TextBlockType) blockType;
					for (TextLine textLine : textBlockType.getTextLine()) {
						for (Object object : textLine.getStringAndSP()) {
							if (object instanceof StringType) {
								StringType stringType = (StringType) object;
								if (stringType.getCONTENT().equalsIgnoreCase(term) || stringType.getSUBSCONTENT().equalsIgnoreCase(term)) {
									Box box = new Box(stringType.getHPOS()+ stringType.getWIDTH(),
											stringType.getVPOS()+ stringType.getHEIGHT(),
											stringType.getVPOS(), 1,stringType.getHPOS());
									allBoxs.add(box);
								}
							}
						}
					}

				}

			}
		}
		return allBoxs;
	}

	@SuppressWarnings("unused")
	private Alto getAltoFromFile(String filePath) {
		Alto alto = null;
		try {
			File file = new File(filePath);
			JAXBContext jaxbContext = JAXBContext.newInstance(Alto.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			alto = (Alto) jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return alto;
	}

	private Alto getAltoFromURL(String filePath) {
		Alto alto = null;
			URL url;
			try {
				url = new URL(filePath);
				JAXBContext jaxbContext = JAXBContext.newInstance(Alto.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				alto = (Alto) jaxbUnmarshaller.unmarshal(url);

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		return alto;
	}

	@SuppressWarnings("unused")
	private void showURL(HttpServletRequest request) {
		StringBuffer requestURL = request.getRequestURL();
		if (request.getQueryString() != null) {
			requestURL.append("?").append(request.getQueryString());
		}
		String completeURL = requestURL.toString();

		System.out.println(completeURL);
	}
}