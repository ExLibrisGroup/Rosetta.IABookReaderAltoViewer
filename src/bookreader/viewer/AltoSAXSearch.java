package bookreader.viewer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.exlibris.dps.alto.json.Book;
import com.exlibris.dps.alto.json.Box;
import com.exlibris.dps.alto.json.Match;
import com.exlibris.dps.alto.json.Par;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AltoSAXSearch extends HttpServlet implements ContentHandler {

	private static final long serialVersionUID = 1L;
	private final static String ACTION="action";
	private final static String SEARCH_PARAM="searchParam";
	private final static String TERM="term";
	private final static String ALTO_FILE_PATH = "altoFilePath";

	private Book book;
	private Match match;
	private Par par;
	private List<Box> allBoxs = new ArrayList<Box>();


	@SuppressWarnings("unused")
	private String temp;
	private String term;

	private boolean isAlto;
	private boolean isPage;
	private boolean isPrintSpace;
	private boolean isTextBlock;
	private boolean isTextLine;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		long startTime = System.currentTimeMillis();
		initBook();

		String action = request.getParameter(ACTION);
		String altoFileURL = request.getParameter(ALTO_FILE_PATH)+BookReaderViewerAlto.ALTO_FILE;

		if (SEARCH_PARAM.equals(action)) {
			term = request.getParameter(TERM).toLowerCase().replaceAll("(^\\p{Punct})|(\\p{Punct}$)","").trim();

			if (null != term && !"".equals(term)) {
				parsXML(altoFileURL);
			}
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(book);

			response.setStatus(200);
			response.setContentType("text/javascript");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("br.BRSearchCallback(" + json + ")");

			System.out.println(System.currentTimeMillis() - startTime);
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	private void parsXML(String altoFileURL) {

			try {
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				XMLReader xr = sp.getXMLReader();
				xr.setContentHandler(this);
		        URL url = new URL(altoFileURL);
		        InputSource xml = new InputSource(url.openStream());
				xr.parse(xml);
			}  catch (Exception e) {
				e.printStackTrace();
			}

	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		isAlto =false;
		isPage = false;
		isPrintSpace = false;
		isTextBlock = false;
		isTextLine = false;
		book=null;
		match=null;
		par=null;
		allBoxs = new ArrayList<Box>();
		term=null;
	}

	/*
	 * Every time the parser encounters the beginning of a new element, it calls this method.
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		temp = "";
		if((qName.equalsIgnoreCase("Alto"))){
			isAlto=true;
		}
		else if (isAlto && qName.equalsIgnoreCase("Page") ) {
			isPage = true;
			initPar(attributes);
		} else if (isPage &&qName.equalsIgnoreCase("PrintSpace") ) {
			isPrintSpace = true;
		}else if (isPage && isPrintSpace && qName.equalsIgnoreCase("TextBlock")) {
			String currentTitle = ((attributes.getValue("TITLE") == null) ? "" : attributes.getValue("TITLE").toLowerCase());
			if(currentTitle.contains(term) || "".equals(currentTitle)){
				initMatch(attributes.getValue("TITLE"));
				isTextBlock = true;
			}
		}else if(isPage && isPrintSpace && isTextBlock && qName.equalsIgnoreCase("TextLine")){
				isTextLine = true;
		}else if (isPage && isPrintSpace && isTextBlock && isTextLine && qName.equalsIgnoreCase("String")) {
			String content = attributes.getValue("CONTENT").toLowerCase();
			String subsContent = ((attributes.getValue("SUBS_CONTENT") == null) ? "" : attributes.getValue("SUBS_CONTENT").toLowerCase());
			if(content.contains(term) || subsContent.contains(term)){
				Box box = initBox(attributes);
				allBoxs.add(box);
				}
		}
	}

	/*
	 * Every time the parser encounters the ending of a element, it calls this method.
	 */
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equalsIgnoreCase("Page")) {
			isPage = false;
			isPrintSpace = false;
			isTextBlock = false;
			isTextLine = false;
			par = null;
			match = null;
		} else if (qName.equalsIgnoreCase("PrintSpace")) {
			isPrintSpace = false;
			isTextBlock = false;
			isTextLine = false;
		} else if (qName.equalsIgnoreCase("TextBlock")) {
			if (null != allBoxs && allBoxs .size()>0) {
				par.getBoxes().addAll(allBoxs);
				match.getPar().add(par);
				book.getMatches().add(match);
				par = new Par(par.getB(), par.getT(), par.getPage_width(), par.getR(), par.getL(), par.getPage_height(), par.getPage());
				allBoxs.clear();
			}
			match=null;
			isTextBlock=false;
			isTextLine=false;
		}else if (qName.equalsIgnoreCase("TextLine")) {
			isTextLine=false;
		}
	}

	private Box initBox(Attributes attributes) {
		float hops = Float.parseFloat(attributes.getValue("HPOS"));
		float width = Float.parseFloat(attributes.getValue("WIDTH"));
		float height = Float.parseFloat(attributes.getValue("HEIGHT"));
		float vpos = Float.parseFloat(attributes.getValue("VPOS"));
		return new Box(hops+width,vpos+height,vpos, par.getPage(),hops);
	}

	private void initMatch(String title) {
		title = title.replaceAll("(?i)"+term, "{{{"+term+"}}}");
		match = new Match(title);
	}

	private void initBook() {
		book = new Book("book", term, true, 1, 1, true);
	}

	private void initPar(Attributes attributes) {
		int pageWidth=Integer.parseInt(attributes.getValue("WIDTH"));
		int pageHight=Integer.parseInt(attributes.getValue("HEIGHT"));
		int pageID;
		try {
			pageID = Integer.parseInt(attributes.getValue("ID"));
		} catch (NumberFormatException e) {
			pageID = 1;
		}
		par = new Par(0,0,pageWidth,0,0,pageHight,pageID);
	}

	public void characters(char[] buffer, int start, int length) {
		temp = new String(buffer, start, length);
	}

	public void ignorableWhitespace(char[] ch, int start, int length)throws SAXException {}

	public void processingInstruction(String target, String data)throws SAXException {}

	public void skippedEntity(String name) throws SAXException {}

	public void setDocumentLocator(Locator locator) {}

	public void startDocument() throws SAXException {}

	public void endDocument() throws SAXException {}

	public void startPrefixMapping(String prefix, String uri)throws SAXException {}

	public void endPrefixMapping(String prefix) throws SAXException {}
}
