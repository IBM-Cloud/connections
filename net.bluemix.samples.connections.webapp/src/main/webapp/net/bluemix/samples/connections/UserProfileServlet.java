/*
 * Copyright IBM Corp. 2014
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.bluemix.samples.connections;

import java.io.IOException;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.bluemix.samples.connections.Person;
import com.google.gson.Gson;
import com.ibm.commons.util.io.json.JsonJavaObject;
import com.ibm.sbt.services.client.GenericService;
import com.ibm.sbt.services.client.Response;
import com.ibm.sbt.services.endpoints.SmartCloudBasicEndpoint;

/**
 * Servlet implementation class
 */
public class UserProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserProfileServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			/*
			 * Java doc: 
			 * http://infolib.lotus.com/resources/social_business_toolkit/javadoc/com/ibm/sbt/services/client/package-summary.html
			 * http://infolib.lotus.com/resources/social_business_toolkit/javadoc/com/ibm/sbt/services/endpoints/package-summary.html
			 */
			GenericService service = new GenericService("connections"); // reads config from managed-beans.xml
			SmartCloudBasicEndpoint e = (SmartCloudBasicEndpoint) service.getEndpoint(); // uses basic authentication
			
			/*
			 * insert context root in URL only if running locally, not on Bluemix
			 * documentation: https://www.ng.bluemix.net/docs/#starters/liberty/index.html#automaticconfigurationofboundservices
			 */
			String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
			if (VCAP_SERVICES == null) {				 
				e.setAuthenticationPage("net.bluemix.samples.connections.webapp/" + e.getAuthenticationPage());
			}			
			
			/*
			 * REST API documentation:
			 * http://www-10.lotus.com/ldd/appdevwiki.nsf/xpAPIViewer.xsp?lookupName=API+Reference#action=openDocument&res_title=IBM_Connections_People_API_ic50&content=apicontent
			 * 
			 * API explorer:
			 * https://greenhouse.lotus.com/sbt/SBTPlayground.nsf/Explorer.xsp?env=SmartCloud%20C1#api=Social_OpenSocial_API_IBM_Connections_People_API_IBM_Connections_People_API
			 * user: samantha_daryn@zetains.com, password: icsappdev2014
			*/					
			URL u = new URL(e.getUrl() + "/connections/opensocial/basic/rest/people/@me/@self");
			Response resp = service.getEndpoint().getClientService().get(u.toString());
			
			/*
			 * Json utilities Java doc:
			 * http://public.dhe.ibm.com/software/dw/lotus/Domino-Designer/JavaDocs/DesignerAPIs/com/ibm/commons/util/io/json/package-summary.html
			 */
			JsonJavaObject jsonObject = (JsonJavaObject) resp.getData();
			JsonJavaObject entry = (JsonJavaObject) jsonObject.get("entry");

			Person person = new Person();
			person.setDisplayName((String) entry.get("displayName"));
			person.setId((String) entry.get("id"));			
			
			response.setContentType("application/json");
			Gson gson = new Gson();
			response.getOutputStream().println(gson.toJson(person));
			
		} catch (Throwable e) {
			// handle error
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}
