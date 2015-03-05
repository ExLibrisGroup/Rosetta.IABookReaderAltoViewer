package bookreader.viewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.PropertyResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import com.exlibris.dps.ws.delivery.DeliveryAccessWS;
import com.exlibris.dps.ws.delivery.DeliveryAccessWS_Service;
import com.exlibris.dps.ws.delivery.Exception_Exception;
public class BookReaderViewerAlto extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String PROP_FILE_NAME = "book.properties";
	private static final String PAGE_WIGHT="pageWight";
	private static final String PAGE_HIGHT="pageHight";
	private static final String LEAF_MAP="leafMap";
	private static final String BOOK_TITLE="bookTitle";
	private static final String FILE_PATH="filePath";
	private static final String EXTENSION = "extension";
	private static final String DVS = "ie_dvs";
	private static final String REP_PID = "rep_pid";

	public static final String ALTO_FILE="alto.xml";
	private static final String VIEWER_PROPERTIES = "/conf/viewer.properties";
	private static final String WSDL_LOCATION = "wsdlLocation";


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String wsdlLocation = getWsdUrl();
		URL wsdlLocationUrl = new URL(wsdlLocation);
		DeliveryAccessWS deliveryAccessWS = new DeliveryAccessWS_Service(wsdlLocationUrl,new QName("http://dps.exlibris.com/", "DeliveryAccessWS")).getDeliveryAccessWSPort();

		String redirect = "";
		try {
			initParam(request,deliveryAccessWS);
			redirect = "index.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			redirect = "error.jsp";
		}
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(redirect);
		requestDispatcher.forward(request, response);
	}

	private void initParam(HttpServletRequest request, DeliveryAccessWS deliveryAccessWS) throws Exception {

		String filePath  = getAltoFilePath(request, deliveryAccessWS);
		Properties prop = getProp(filePath);

		request.getSession().setAttribute(PAGE_WIGHT, prop.getProperty(PAGE_WIGHT));
		request.getSession().setAttribute(PAGE_HIGHT, prop.getProperty(PAGE_HIGHT));
		request.getSession().setAttribute(LEAF_MAP, prop.getProperty(LEAF_MAP));
		request.getSession().setAttribute(BOOK_TITLE, prop.getProperty(BOOK_TITLE));
		request.getSession().setAttribute(EXTENSION, prop.getProperty(EXTENSION));
		request.getSession().setAttribute(FILE_PATH,filePath);
		request.getSession().setAttribute("pageProgression",((request.getParameter("pageProgression") != null) ? request.getParameter("pageProgression"):"lr"));
		request.getSession().setAttribute("page",((request.getParameter("page") != null) ? request.getParameter("page"):"1"));
		request.getSession().setAttribute("hideSearch",((request.getParameter("hideSearch") != null) ? request.getParameter("hideSearch"):"yes"));
		request.getSession().setAttribute("sideBar",((request.getParameter("sideBar") != null) ? request.getParameter("sideBar"):""));
	}

	private Properties getProp(String path) throws Exception  {
		Properties prop = new Properties();
		URL url = new URL(path+PROP_FILE_NAME);
		InputStream in = url.openStream();

		prop.load(in);
		return prop;
	}

	private String getAltoFilePath(HttpServletRequest request,DeliveryAccessWS deliveryAccessWS) throws Exception_Exception {
		String dvs = (String) ((request.getParameter(DVS) != null) ? request.getParameter(DVS):request.getAttribute(DVS));
		String repPid = (String) ((request.getParameter(REP_PID) != null) ? request.getParameter(REP_PID):request.getAttribute(REP_PID));
		StringBuilder sb = new StringBuilder(deliveryAccessWS.getBaseFileUrl(dvs)).append(repPid).append("&file_index=");
		request.getSession().setAttribute(DVS,dvs);
		request.getSession().setAttribute(REP_PID,repPid);
		return sb.toString();
	}

	private String getWsdUrl() {
		PropertyResourceBundle resourceBundle = null;
		InputStream resourceAsStream = null;
		try {
			resourceAsStream = getServletContext().getResourceAsStream(VIEWER_PROPERTIES);
			resourceBundle = new PropertyResourceBundle(resourceAsStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				resourceAsStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String wsdlLocation =  resourceBundle.getString(WSDL_LOCATION);
		return wsdlLocation;
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}