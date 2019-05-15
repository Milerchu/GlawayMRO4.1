
package com.glaway.sddq.back.Interface.webservice.mdm.client;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.1 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "MdmToMroServiceImpService", targetNamespace = "http://mdm.webservice.Interface.back.sddq.glaway.com/", wsdlLocation = "http://127.0.0.1:58000/gwmro/mroservice/mdmTomro?wsdl")
public class MdmToMroServiceImpService
    extends Service
{

    private final static URL MDMTOMROSERVICEIMPSERVICE_WSDL_LOCATION;

    static {
        URL url = null;
        try {
            url = new URL("http://127.0.0.1:58000/gwmro/mroservice/mdmTomro?wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        MDMTOMROSERVICEIMPSERVICE_WSDL_LOCATION = url;
    }

    public MdmToMroServiceImpService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public MdmToMroServiceImpService() {
        super(MDMTOMROSERVICEIMPSERVICE_WSDL_LOCATION, new QName("http://mdm.webservice.Interface.back.sddq.glaway.com/", "MdmToMroServiceImpService"));
    }

    /**
     * 
     * @return
     *     returns MdmToMroService
     */
    @WebEndpoint(name = "MdmToMroServiceImpPort")
    public MdmToMroService getMdmToMroServiceImpPort() {
        return (MdmToMroService)super.getPort(new QName("http://mdm.webservice.Interface.back.sddq.glaway.com/", "MdmToMroServiceImpPort"), MdmToMroService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns MdmToMroService
     */
    @WebEndpoint(name = "MdmToMroServiceImpPort")
    public MdmToMroService getMdmToMroServiceImpPort(WebServiceFeature... features) {
        return (MdmToMroService)super.getPort(new QName("http://mdm.webservice.Interface.back.sddq.glaway.com/", "MdmToMroServiceImpPort"), MdmToMroService.class, features);
    }

}
