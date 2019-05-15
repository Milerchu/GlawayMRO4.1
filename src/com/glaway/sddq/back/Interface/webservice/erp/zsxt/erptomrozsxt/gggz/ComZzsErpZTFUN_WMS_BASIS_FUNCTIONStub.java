/**
 * ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.8  Built on : May 19, 2018 (07:06:11 BST)
 */
package com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz;

/**
 * ERP公共过账接口类
 */

/*
 * ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub java implementation
 */
public class ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub extends
		org.apache.axis2.client.Stub {
	private static int counter = 0;
	protected org.apache.axis2.description.AxisOperation[] _operations;

	// hashmaps to keep the fault mapping
	private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
	private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
	private java.util.HashMap faultMessageMap = new java.util.HashMap();

	// ///////////////////////////////////////////////////////////////////////
	private javax.xml.namespace.QName[] opNameArray = null;

	/**
	 * Constructor that takes in a configContext
	 */
	public ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub(
			org.apache.axis2.context.ConfigurationContext configurationContext,
			java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
		this(configurationContext, targetEndpoint, false);
	}

	/**
	 * Constructor that takes in a configContext and useseperate listner
	 */
	public ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub(
			org.apache.axis2.context.ConfigurationContext configurationContext,
			java.lang.String targetEndpoint, boolean useSeparateListener)
			throws org.apache.axis2.AxisFault {
		// To populate AxisService
		populateAxisService();
		populateFaults();

		_serviceClient = new org.apache.axis2.client.ServiceClient(
				configurationContext, _service);

		_service.applyPolicy();

		_serviceClient.getOptions().setTo(
				new org.apache.axis2.addressing.EndpointReference(
						targetEndpoint));
		_serviceClient.getOptions().setUseSeparateListener(useSeparateListener);
	}

	/**
	 * Default Constructor
	 */
	public ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub(
			org.apache.axis2.context.ConfigurationContext configurationContext)
			throws org.apache.axis2.AxisFault {
		this(configurationContext,
				"http://esb.teg.cn:9990/com.zzs.erp.ZTFUN_WMS_BASIS_FUNCTION");
	}

	/**
	 * Default Constructor
	 */
	public ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub()
			throws org.apache.axis2.AxisFault {
		this("http://esb.teg.cn:9990/com.zzs.erp.ZTFUN_WMS_BASIS_FUNCTION");
	}

	/**
	 * Constructor taking the target endpoint
	 */
	public ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub(java.lang.String targetEndpoint)
			throws org.apache.axis2.AxisFault {
		this(null, targetEndpoint);
	}

	private static synchronized java.lang.String getUniqueSuffix() {
		// reset the counter if it is greater than 99999
		if (counter > 99999) {
			counter = 0;
		}

		counter = counter + 1;

		return java.lang.Long.toString(java.lang.System.currentTimeMillis())
				+ "_" + counter;
	}

	private void populateAxisService() throws org.apache.axis2.AxisFault {
		// creating the Service with a unique name
		_service = new org.apache.axis2.description.AxisService(
				"ComZzsErpZTFUN_WMS_BASIS_FUNCTION" + getUniqueSuffix());
		addAnonymousOperations();

		// creating the operations
		org.apache.axis2.description.AxisOperation __operation;

		_operations = new org.apache.axis2.description.AxisOperation[1];

		__operation = new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:soap:functions:mc-style",
				"ztfunWmsBasisFunction"));
		_service.addOperation(__operation);

		(__operation)
				.getMessage(
						org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_OUT_VALUE)
				.getPolicySubject()
				.attachPolicy(
						getPolicy("<wsp:Policy wsu:Id=\"6ef060861c4ccba1a82eae84014bade768b33a74f4b2a43a\" xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\"><wsp:ExactlyOne><wsp:All><wsaw:UsingAddressing xmlns:wsaw=\"http://schemas.xmlsoap.org/ws/2004/08/addressing\"></wsaw:UsingAddressing><saptrnbnd:OptimizedXMLTransfer wsp:Optional=\"true\" xmlns:saptrnbnd=\"http://www.sap.com/webas/710/soap/features/transportbinding/\" uri=\"http://xml.sap.com/2006/11/esi/esp/binxml\"></saptrnbnd:OptimizedXMLTransfer><sapattahnd:Enabled xmlns:sapattahnd=\"http://www.sap.com/710/features/attachment/\">false</sapattahnd:Enabled><sp:TransportBinding xmlns:sp=\"http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702\"><wsp:Policy xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\"><sp:TransportToken><wsp:Policy xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\"><sp:HttpsToken><wsp:Policy xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\"><sp:HttpBasicAuthentication/></wsp:Policy></sp:HttpsToken></wsp:Policy></sp:TransportToken><sp:AlgorithmSuite><wsp:Policy xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\"><sp:TripleDesRsa15/></wsp:Policy></sp:AlgorithmSuite><sp:Layout><wsp:Policy xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\"><sp:Strict/></wsp:Policy></sp:Layout></wsp:Policy></sp:TransportBinding></wsp:All></wsp:ExactlyOne></wsp:Policy>"));

		(__operation)
				.getMessage(
						org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE)
				.getPolicySubject()
				.attachPolicy(
						getPolicy("<wsp:Policy wsu:Id=\"6ef060861c4ccba1a82eae84014bade768b33a74f4b2a43a\" xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\"><wsp:ExactlyOne><wsp:All><wsaw:UsingAddressing xmlns:wsaw=\"http://schemas.xmlsoap.org/ws/2004/08/addressing\"></wsaw:UsingAddressing><saptrnbnd:OptimizedXMLTransfer wsp:Optional=\"true\" xmlns:saptrnbnd=\"http://www.sap.com/webas/710/soap/features/transportbinding/\" uri=\"http://xml.sap.com/2006/11/esi/esp/binxml\"></saptrnbnd:OptimizedXMLTransfer><sapattahnd:Enabled xmlns:sapattahnd=\"http://www.sap.com/710/features/attachment/\">false</sapattahnd:Enabled><sp:TransportBinding xmlns:sp=\"http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702\"><wsp:Policy xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\"><sp:TransportToken><wsp:Policy xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\"><sp:HttpsToken><wsp:Policy xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\"><sp:HttpBasicAuthentication/></wsp:Policy></sp:HttpsToken></wsp:Policy></sp:TransportToken><sp:AlgorithmSuite><wsp:Policy xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\"><sp:TripleDesRsa15/></wsp:Policy></sp:AlgorithmSuite><sp:Layout><wsp:Policy xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\"><sp:Strict/></wsp:Policy></sp:Layout></wsp:Policy></sp:TransportBinding></wsp:All></wsp:ExactlyOne></wsp:Policy>"));

		_operations[0] = __operation;
	}

	// populates the faults
	private void populateFaults() {
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTION#ztfunWmsBasisFunction
	 * @param ztfunWmsBasisFunction0
	 */
	public com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.ZtfunWmsBasisFunctionResponse ztfunWmsBasisFunction(
			com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.ZtfunWmsBasisFunction ztfunWmsBasisFunction0)
			throws java.rmi.RemoteException {
		org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[0].getName());
			_operationClient.getOptions().setAction("ZtfunWmsBasisFunction");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
					true);

			addPropertyToOperationClient(
					_operationClient,
					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
					"&");

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;

			env = toEnvelope(getFactory(_operationClient.getOptions()
					.getSoapVersionURI()), ztfunWmsBasisFunction0,
					optimizeContent(new javax.xml.namespace.QName(
							"urn:sap-com:document:sap:soap:functions:mc-style",
							"ztfunWmsBasisFunction")),
					new javax.xml.namespace.QName(
							"urn:sap-com:document:sap:soap:functions:mc-style",
							"ZtfunWmsBasisFunction"));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient
					.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext
					.getEnvelope();

			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement(),
					com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.ZtfunWmsBasisFunctionResponse.class);

			return (com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.ZtfunWmsBasisFunctionResponse) object;
		} catch (org.apache.axis2.AxisFault f) {
			org.apache.axiom.om.OMElement faultElt = f.getDetail();

			if (faultElt != null) {
				if (faultExceptionNameMap
						.containsKey(new org.apache.axis2.client.FaultMapKey(
								faultElt.getQName(), "ZtfunWmsBasisFunction"))) {
					// make the fault by reflection
					try {
						java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
								.get(new org.apache.axis2.client.FaultMapKey(
										faultElt.getQName(),
										"ZtfunWmsBasisFunction"));
						java.lang.Class exceptionClass = java.lang.Class
								.forName(exceptionClassName);
						java.lang.reflect.Constructor constructor = exceptionClass
								.getConstructor(java.lang.String.class);
						java.lang.Exception ex = (java.lang.Exception) constructor
								.newInstance(f.getMessage());

						// message class
						java.lang.String messageClassName = (java.lang.String) faultMessageMap
								.get(new org.apache.axis2.client.FaultMapKey(
										faultElt.getQName(),
										"ZtfunWmsBasisFunction"));
						java.lang.Class messageClass = java.lang.Class
								.forName(messageClassName);
						java.lang.Object messageObject = fromOM(faultElt,
								messageClass);
						java.lang.reflect.Method m = exceptionClass.getMethod(
								"setFaultMessage",
								new java.lang.Class[] { messageClass });
						m.invoke(ex, new java.lang.Object[] { messageObject });

						throw new java.rmi.RemoteException(ex.getMessage(), ex);
					} catch (java.lang.ClassCastException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.ClassNotFoundException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.NoSuchMethodException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.reflect.InvocationTargetException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.IllegalAccessException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					} catch (java.lang.InstantiationException e) {
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				} else {
					throw f;
				}
			} else {
				throw f;
			}
		} finally {
			if (_messageContext.getTransportOut() != null) {
				_messageContext.getTransportOut().getSender()
						.cleanup(_messageContext);
			}
		}
	}

	/**
	 * Auto generated method signature for Asynchronous Invocations
	 * 
	 * @see com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTION#startztfunWmsBasisFunction
	 * @param ztfunWmsBasisFunction0
	 */
	public void startztfunWmsBasisFunction(
			com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.ZtfunWmsBasisFunction ztfunWmsBasisFunction0,
			final com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONCallbackHandler callback)
			throws java.rmi.RemoteException {
		org.apache.axis2.client.OperationClient _operationClient = _serviceClient
				.createClient(_operations[0].getName());
		_operationClient
				.getOptions()
				.setAction(
						"urn:sap-com:document:sap:soap:functions:mc-style:ZTFUN_WMS_BASIS_FUNCTION:ZtfunWmsBasisFunctionRequest");
		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

		addPropertyToOperationClient(
				_operationClient,
				org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
				"&");

		// create SOAP envelope with that payload
		org.apache.axiom.soap.SOAPEnvelope env = null;
		final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

		// Style is Doc.
		env = toEnvelope(getFactory(_operationClient.getOptions()
				.getSoapVersionURI()), ztfunWmsBasisFunction0,
				optimizeContent(new javax.xml.namespace.QName(
						"urn:sap-com:document:sap:soap:functions:mc-style",
						"ztfunWmsBasisFunction")),
				new javax.xml.namespace.QName(
						"urn:sap-com:document:sap:soap:functions:mc-style",
						"ZtfunWmsBasisFunction"));

		// adding SOAP soap_headers
		_serviceClient.addHeadersToEnvelope(env);
		// create message context with that soap envelope
		_messageContext.setEnvelope(env);

		// add the message context to the operation client
		_operationClient.addMessageContext(_messageContext);

		_operationClient
				.setCallback(new org.apache.axis2.client.async.AxisCallback() {
					public void onMessage(
							org.apache.axis2.context.MessageContext resultContext) {
						try {
							org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext
									.getEnvelope();

							java.lang.Object object = fromOM(
									resultEnv.getBody().getFirstElement(),
									com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.ZtfunWmsBasisFunctionResponse.class);
							callback.receiveResultztfunWmsBasisFunction((com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.ZtfunWmsBasisFunctionResponse) object);
						} catch (org.apache.axis2.AxisFault e) {
							callback.receiveErrorztfunWmsBasisFunction(e);
						}
					}

					public void onError(java.lang.Exception error) {
						if (error instanceof org.apache.axis2.AxisFault) {
							org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
							org.apache.axiom.om.OMElement faultElt = f
									.getDetail();

							if (faultElt != null) {
								if (faultExceptionNameMap
										.containsKey(new org.apache.axis2.client.FaultMapKey(
												faultElt.getQName(),
												"ZtfunWmsBasisFunction"))) {
									// make the fault by reflection
									try {
										java.lang.String exceptionClassName = (java.lang.String) faultExceptionClassNameMap
												.get(new org.apache.axis2.client.FaultMapKey(
														faultElt.getQName(),
														"ZtfunWmsBasisFunction"));
										java.lang.Class exceptionClass = java.lang.Class
												.forName(exceptionClassName);
										java.lang.reflect.Constructor constructor = exceptionClass
												.getConstructor(java.lang.String.class);
										java.lang.Exception ex = (java.lang.Exception) constructor
												.newInstance(f.getMessage());

										// message class
										java.lang.String messageClassName = (java.lang.String) faultMessageMap
												.get(new org.apache.axis2.client.FaultMapKey(
														faultElt.getQName(),
														"ZtfunWmsBasisFunction"));
										java.lang.Class messageClass = java.lang.Class
												.forName(messageClassName);
										java.lang.Object messageObject = fromOM(
												faultElt, messageClass);
										java.lang.reflect.Method m = exceptionClass
												.getMethod(
														"setFaultMessage",
														new java.lang.Class[] { messageClass });
										m.invoke(
												ex,
												new java.lang.Object[] { messageObject });

										callback.receiveErrorztfunWmsBasisFunction(new java.rmi.RemoteException(
												ex.getMessage(), ex));
									} catch (java.lang.ClassCastException e) {
										// we cannot intantiate the class -
										// throw the original Axis fault
										callback.receiveErrorztfunWmsBasisFunction(f);
									} catch (java.lang.ClassNotFoundException e) {
										// we cannot intantiate the class -
										// throw the original Axis fault
										callback.receiveErrorztfunWmsBasisFunction(f);
									} catch (java.lang.NoSuchMethodException e) {
										// we cannot intantiate the class -
										// throw the original Axis fault
										callback.receiveErrorztfunWmsBasisFunction(f);
									} catch (java.lang.reflect.InvocationTargetException e) {
										// we cannot intantiate the class -
										// throw the original Axis fault
										callback.receiveErrorztfunWmsBasisFunction(f);
									} catch (java.lang.IllegalAccessException e) {
										// we cannot intantiate the class -
										// throw the original Axis fault
										callback.receiveErrorztfunWmsBasisFunction(f);
									} catch (java.lang.InstantiationException e) {
										// we cannot intantiate the class -
										// throw the original Axis fault
										callback.receiveErrorztfunWmsBasisFunction(f);
									} catch (org.apache.axis2.AxisFault e) {
										// we cannot intantiate the class -
										// throw the original Axis fault
										callback.receiveErrorztfunWmsBasisFunction(f);
									}
								} else {
									callback.receiveErrorztfunWmsBasisFunction(f);
								}
							} else {
								callback.receiveErrorztfunWmsBasisFunction(f);
							}
						} else {
							callback.receiveErrorztfunWmsBasisFunction(error);
						}
					}

					public void onFault(
							org.apache.axis2.context.MessageContext faultContext) {
						org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils
								.getInboundFaultFromMessageContext(faultContext);
						onError(fault);
					}

					public void onComplete() {
						try {
							_messageContext.getTransportOut().getSender()
									.cleanup(_messageContext);
						} catch (org.apache.axis2.AxisFault axisFault) {
							callback.receiveErrorztfunWmsBasisFunction(axisFault);
						}
					}
				});

		org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;

		if ((_operations[0].getMessageReceiver() == null)
				&& _operationClient.getOptions().isUseSeparateListener()) {
			_callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
			_operations[0].setMessageReceiver(_callbackReceiver);
		}

		// execute the operation client
		_operationClient.execute(false);
	}

	// //////////////////////////////////////////////////////////////////////
	private static org.apache.neethi.Policy getPolicy(
			java.lang.String policyString) {
		return org.apache.neethi.PolicyEngine
				.getPolicy(org.apache.axiom.om.OMXMLBuilderFactory
						.createOMBuilder(new java.io.StringReader(policyString))
						.getDocument().getXMLStreamReader(false));
	}

	private boolean optimizeContent(javax.xml.namespace.QName opName) {
		if (opNameArray == null) {
			return false;
		}

		for (int i = 0; i < opNameArray.length; i++) {
			if (opName.equals(opNameArray[i])) {
				return true;
			}
		}

		return false;
	}

	private org.apache.axiom.om.OMElement toOM(
			com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.ZtfunWmsBasisFunction param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {
		try {
			return param
					.getOMElement(
							com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.ZtfunWmsBasisFunction.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	private org.apache.axiom.om.OMElement toOM(
			com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.ZtfunWmsBasisFunctionResponse param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {
		try {
			return param
					.getOMElement(
							com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.ZtfunWmsBasisFunctionResponse.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory
									.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.ZtfunWmsBasisFunction param,
			boolean optimizeContent, javax.xml.namespace.QName elementQName)
			throws org.apache.axis2.AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory
					.getDefaultEnvelope();
			emptyEnvelope
					.getBody()
					.addChild(
							param.getOMElement(
									com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.ZtfunWmsBasisFunction.MY_QNAME,
									factory));

			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	/* methods to provide back word compatibility */

	/**
	 * get the default envelope
	 */
	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory) {
		return factory.getDefaultEnvelope();
	}

	private java.lang.Object fromOM(org.apache.axiom.om.OMElement param,
			java.lang.Class type) throws org.apache.axis2.AxisFault {
		try {
			if (com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.ZtfunWmsBasisFunction.class
					.equals(type)) {
				return com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.ZtfunWmsBasisFunction.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());
			}

			if (com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.ZtfunWmsBasisFunctionResponse.class
					.equals(type)) {
				return com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.gggz.ComZzsErpZTFUN_WMS_BASIS_FUNCTIONStub.ZtfunWmsBasisFunctionResponse.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());
			}
		} catch (java.lang.Exception e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

		return null;
	}

	// http://esb.teg.cn:9990/com.zzs.erp.ZTFUN_WMS_BASIS_FUNCTION
	public static class Char1 implements org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "char1", "ns1");

		/**
		 * field for Char1
		 */
		protected java.lang.String localChar1;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getChar1() {
			return localChar1;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Char1
		 */
		public void setChar1(java.lang.String param) {
			if ((java.lang.String.valueOf(param).length() <= 1)) {
				this.localChar1 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localChar1.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":char1", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "char1", xmlWriter);
				}
			}

			if (localChar1 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"char1 cannot be null !!");
			} else {
				xmlWriter.writeCharacters(localChar1);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Char1 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Char1 returnValue = new Char1();

				returnValue
						.setChar1(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(value));

				return returnValue;
			}

			public static Char1 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Char1.Factory.fromString(content, namespaceUri);
				} else {
					return Char1.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Char1 parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Char1 object = new Char1();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "char1"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setChar1(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Decimal234 implements
			org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "decimal23.4", "ns1");

		/**
		 * field for Decimal234
		 */
		protected java.math.BigDecimal localDecimal234;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.math.BigDecimal
		 */
		public java.math.BigDecimal getDecimal234() {
			return localDecimal234;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Decimal234
		 */
		public void setDecimal234(java.math.BigDecimal param) {
			java.lang.String totalDigitsDecimal = org.apache.axis2.databinding.utils.ConverterUtil
					.convertToStandardDecimalNotation("23").toPlainString();

			if (org.apache.axis2.databinding.utils.ConverterUtil.compare(param,
					totalDigitsDecimal) < 0) {
				this.localDecimal234 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localDecimal234.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":decimal23.4", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "decimal23.4", xmlWriter);
				}
			}

			if (localDecimal234 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"decimal23.4 cannot be null !!");
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localDecimal234));
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Decimal234 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Decimal234 returnValue = new Decimal234();

				returnValue
						.setDecimal234(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToDecimal(value));

				return returnValue;
			}

			public static Decimal234 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Decimal234.Factory.fromString(content, namespaceUri);
				} else {
					return Decimal234.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Decimal234 parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Decimal234 object = new Decimal234();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "decimal23.4"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setDecimal234(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToDecimal(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Char4 implements org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "char4", "ns1");

		/**
		 * field for Char4
		 */
		protected java.lang.String localChar4;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getChar4() {
			return localChar4;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Char4
		 */
		public void setChar4(java.lang.String param) {
			if ((java.lang.String.valueOf(param).length() <= 4)) {
				this.localChar4 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localChar4.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":char4", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "char4", xmlWriter);
				}
			}

			if (localChar4 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"char4 cannot be null !!");
			} else {
				xmlWriter.writeCharacters(localChar4);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Char4 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Char4 returnValue = new Char4();

				returnValue
						.setChar4(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(value));

				return returnValue;
			}

			public static Char4 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Char4.Factory.fromString(content, namespaceUri);
				} else {
					return Char4.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Char4 parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Char4 object = new Char4();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "char4"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setChar4(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Char20 implements org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "char20", "ns1");

		/**
		 * field for Char20
		 */
		protected java.lang.String localChar20;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getChar20() {
			return localChar20;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Char20
		 */
		public void setChar20(java.lang.String param) {
			if ((java.lang.String.valueOf(param).length() <= 20)) {
				this.localChar20 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localChar20.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":char20", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "char20", xmlWriter);
				}
			}

			if (localChar20 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"char20 cannot be null !!");
			} else {
				xmlWriter.writeCharacters(localChar20);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Char20 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Char20 returnValue = new Char20();

				returnValue
						.setChar20(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(value));

				return returnValue;
			}

			public static Char20 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Char20.Factory.fromString(content, namespaceUri);
				} else {
					return Char20.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Char20 parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Char20 object = new Char20();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "char20"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setChar20(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Char3 implements org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "char3", "ns1");

		/**
		 * field for Char3
		 */
		protected java.lang.String localChar3;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getChar3() {
			return localChar3;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Char3
		 */
		public void setChar3(java.lang.String param) {
			if ((java.lang.String.valueOf(param).length() <= 3)) {
				this.localChar3 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localChar3.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":char3", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "char3", xmlWriter);
				}
			}

			if (localChar3 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"char3 cannot be null !!");
			} else {
				xmlWriter.writeCharacters(localChar3);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Char3 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Char3 returnValue = new Char3();

				returnValue
						.setChar3(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(value));

				return returnValue;
			}

			public static Char3 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Char3.Factory.fromString(content, namespaceUri);
				} else {
					return Char3.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Char3 parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Char3 object = new Char3();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "char3"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setChar3(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class ZtfunWmsBasisFunction implements
			org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:soap:functions:mc-style",
				"ZtfunWmsBasisFunction", "ns2");

		/**
		 * field for MoveType
		 */
		protected Char3 localMoveType;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localMoveTypeTracker = false;

		/**
		 * field for Person
		 */
		protected Char10 localPerson;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localPersonTracker = false;

		/**
		 * field for TGvitem
		 */
		protected TableOfBapi2017GmItemCreate localTGvitem;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localTGvitemTracker = false;

		/**
		 * field for TPch
		 */
		protected TableOfZwmsPch localTPch;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localTPchTracker = false;

		/**
		 * field for TPzmx
		 */
		protected TableOfZwmsPzmx localTPzmx;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localTPzmxTracker = false;

		/**
		 * field for TSerial
		 */
		protected TableOfBapi2017GmSerialnumber localTSerial;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localTSerialTracker = false;

		/**
		 * field for Werks
		 */
		protected Char4 localWerks;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localWerksTracker = false;

		/**
		 * field for WmBudat
		 */
		protected Date localWmBudat;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localWmBudatTracker = false;

		public boolean isMoveTypeSpecified() {
			return localMoveTypeTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char3
		 */
		public Char3 getMoveType() {
			return localMoveType;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            MoveType
		 */
		public void setMoveType(Char3 param) {
			localMoveTypeTracker = param != null;

			this.localMoveType = param;
		}

		public boolean isPersonSpecified() {
			return localPersonTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getPerson() {
			return localPerson;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Person
		 */
		public void setPerson(Char10 param) {
			localPersonTracker = param != null;

			this.localPerson = param;
		}

		public boolean isTGvitemSpecified() {
			return localTGvitemTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return TableOfBapi2017GmItemCreate
		 */
		public TableOfBapi2017GmItemCreate getTGvitem() {
			return localTGvitem;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TGvitem
		 */
		public void setTGvitem(TableOfBapi2017GmItemCreate param) {
			localTGvitemTracker = param != null;

			this.localTGvitem = param;
		}

		public boolean isTPchSpecified() {
			return localTPchTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return TableOfZwmsPch
		 */
		public TableOfZwmsPch getTPch() {
			return localTPch;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TPch
		 */
		public void setTPch(TableOfZwmsPch param) {
			localTPchTracker = param != null;

			this.localTPch = param;
		}

		public boolean isTPzmxSpecified() {
			return localTPzmxTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return TableOfZwmsPzmx
		 */
		public TableOfZwmsPzmx getTPzmx() {
			return localTPzmx;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TPzmx
		 */
		public void setTPzmx(TableOfZwmsPzmx param) {
			localTPzmxTracker = param != null;

			this.localTPzmx = param;
		}

		public boolean isTSerialSpecified() {
			return localTSerialTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return TableOfBapi2017GmSerialnumber
		 */
		public TableOfBapi2017GmSerialnumber getTSerial() {
			return localTSerial;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TSerial
		 */
		public void setTSerial(TableOfBapi2017GmSerialnumber param) {
			localTSerialTracker = param != null;

			this.localTSerial = param;
		}

		public boolean isWerksSpecified() {
			return localWerksTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char4
		 */
		public Char4 getWerks() {
			return localWerks;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Werks
		 */
		public void setWerks(Char4 param) {
			localWerksTracker = param != null;

			this.localWerks = param;
		}

		public boolean isWmBudatSpecified() {
			return localWmBudatTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Date
		 */
		public Date getWmBudat() {
			return localWmBudat;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            WmBudat
		 */
		public void setWmBudat(Date param) {
			localWmBudatTracker = param != null;

			this.localWmBudat = param;
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:soap:functions:mc-style");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":ZtfunWmsBasisFunction",
							xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "ZtfunWmsBasisFunction", xmlWriter);
				}
			}

			if (localMoveTypeTracker) {
				if (localMoveType == null) {
					throw new org.apache.axis2.databinding.ADBException(
							"MoveType cannot be null!!");
				}

				localMoveType.serialize(new javax.xml.namespace.QName("",
						"MoveType"), xmlWriter);
			}

			if (localPersonTracker) {
				if (localPerson == null) {
					throw new org.apache.axis2.databinding.ADBException(
							"Person cannot be null!!");
				}

				localPerson.serialize(new javax.xml.namespace.QName("",
						"Person"), xmlWriter);
			}

			if (localTGvitemTracker) {
				if (localTGvitem == null) {
					throw new org.apache.axis2.databinding.ADBException(
							"TGvitem cannot be null!!");
				}

				localTGvitem.serialize(new javax.xml.namespace.QName("",
						"TGvitem"), xmlWriter);
			}

			if (localTPchTracker) {
				if (localTPch == null) {
					throw new org.apache.axis2.databinding.ADBException(
							"TPch cannot be null!!");
				}

				localTPch.serialize(new javax.xml.namespace.QName("", "TPch"),
						xmlWriter);
			}

			if (localTPzmxTracker) {
				if (localTPzmx == null) {
					throw new org.apache.axis2.databinding.ADBException(
							"TPzmx cannot be null!!");
				}

				localTPzmx.serialize(
						new javax.xml.namespace.QName("", "TPzmx"), xmlWriter);
			}

			if (localTSerialTracker) {
				if (localTSerial == null) {
					throw new org.apache.axis2.databinding.ADBException(
							"TSerial cannot be null!!");
				}

				localTSerial.serialize(new javax.xml.namespace.QName("",
						"TSerial"), xmlWriter);
			}

			if (localWerksTracker) {
				if (localWerks == null) {
					throw new org.apache.axis2.databinding.ADBException(
							"Werks cannot be null!!");
				}

				localWerks.serialize(
						new javax.xml.namespace.QName("", "Werks"), xmlWriter);
			}

			if (localWmBudatTracker) {
				if (localWmBudat == null) {
					throw new org.apache.axis2.databinding.ADBException(
							"WmBudat cannot be null!!");
				}

				localWmBudat.serialize(new javax.xml.namespace.QName("",
						"WmBudat"), xmlWriter);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace
					.equals("urn:sap-com:document:sap:soap:functions:mc-style")) {
				return "ns2";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static ZtfunWmsBasisFunction parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				ZtfunWmsBasisFunction object = new ZtfunWmsBasisFunction();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");

						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;

							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}

							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"ZtfunWmsBasisFunction".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);

								return (ZtfunWmsBasisFunction) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}
						}
					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "MoveType")
									.equals(reader.getName())) {
						object.setMoveType(Char3.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Person")
									.equals(reader.getName())) {
						object.setPerson(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "TGvitem")
									.equals(reader.getName())) {
						object.setTGvitem(TableOfBapi2017GmItemCreate.Factory
								.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "TPch")
									.equals(reader.getName())) {
						object.setTPch(TableOfZwmsPch.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "TPzmx")
									.equals(reader.getName())) {
						object.setTPzmx(TableOfZwmsPzmx.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "TSerial")
									.equals(reader.getName())) {
						object.setTSerial(TableOfBapi2017GmSerialnumber.Factory
								.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Werks")
									.equals(reader.getName())) {
						object.setWerks(Char4.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "WmBudat")
									.equals(reader.getName())) {
						object.setWmBudat(Date.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Char2 implements org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "char2", "ns1");

		/**
		 * field for Char2
		 */
		protected java.lang.String localChar2;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getChar2() {
			return localChar2;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Char2
		 */
		public void setChar2(java.lang.String param) {
			if ((java.lang.String.valueOf(param).length() <= 2)) {
				this.localChar2 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localChar2.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":char2", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "char2", xmlWriter);
				}
			}

			if (localChar2 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"char2 cannot be null !!");
			} else {
				xmlWriter.writeCharacters(localChar2);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Char2 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Char2 returnValue = new Char2();

				returnValue
						.setChar2(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(value));

				return returnValue;
			}

			public static Char2 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Char2.Factory.fromString(content, namespaceUri);
				} else {
					return Char2.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Char2 parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Char2 object = new Char2();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "char2"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setChar2(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Char40 implements org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "char40", "ns1");

		/**
		 * field for Char40
		 */
		protected java.lang.String localChar40;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getChar40() {
			return localChar40;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Char40
		 */
		public void setChar40(java.lang.String param) {
			if ((java.lang.String.valueOf(param).length() <= 40)) {
				this.localChar40 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localChar40.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":char40", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "char40", xmlWriter);
				}
			}

			if (localChar40 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"char40 cannot be null !!");
			} else {
				xmlWriter.writeCharacters(localChar40);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Char40 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Char40 returnValue = new Char40();

				returnValue
						.setChar40(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(value));

				return returnValue;
			}

			public static Char40 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Char40.Factory.fromString(content, namespaceUri);
				} else {
					return Char40.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Char40 parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Char40 object = new Char40();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "char40"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setChar40(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Char25 implements org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "char25", "ns1");

		/**
		 * field for Char25
		 */
		protected java.lang.String localChar25;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getChar25() {
			return localChar25;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Char25
		 */
		public void setChar25(java.lang.String param) {
			if ((java.lang.String.valueOf(param).length() <= 25)) {
				this.localChar25 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localChar25.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":char25", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "char25", xmlWriter);
				}
			}

			if (localChar25 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"char25 cannot be null !!");
			} else {
				xmlWriter.writeCharacters(localChar25);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Char25 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Char25 returnValue = new Char25();

				returnValue
						.setChar25(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(value));

				return returnValue;
			}

			public static Char25 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Char25.Factory.fromString(content, namespaceUri);
				} else {
					return Char25.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Char25 parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Char25 object = new Char25();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "char25"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setChar25(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Char24 implements org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "char24", "ns1");

		/**
		 * field for Char24
		 */
		protected java.lang.String localChar24;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getChar24() {
			return localChar24;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Char24
		 */
		public void setChar24(java.lang.String param) {
			if ((java.lang.String.valueOf(param).length() <= 24)) {
				this.localChar24 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localChar24.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":char24", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "char24", xmlWriter);
				}
			}

			if (localChar24 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"char24 cannot be null !!");
			} else {
				xmlWriter.writeCharacters(localChar24);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Char24 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Char24 returnValue = new Char24();

				returnValue
						.setChar24(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(value));

				return returnValue;
			}

			public static Char24 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Char24.Factory.fromString(content, namespaceUri);
				} else {
					return Char24.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Char24 parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Char24 object = new Char24();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "char24"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setChar24(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Bapi2017GmItemCreate implements
			org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had name =
		 * Bapi2017GmItemCreate Namespace URI =
		 * urn:sap-com:document:sap:soap:functions:mc-style Namespace Prefix =
		 * ns2
		 */

		/**
		 * field for Material
		 */
		protected Char18 localMaterial;

		/**
		 * field for Plant
		 */
		protected Char4 localPlant;

		/**
		 * field for StgeLoc
		 */
		protected Char4 localStgeLoc;

		/**
		 * field for Batch
		 */
		protected Char10 localBatch;

		/**
		 * field for MoveType
		 */
		protected Char3 localMoveType;

		/**
		 * field for StckType
		 */
		protected Char1 localStckType;

		/**
		 * field for SpecStock
		 */
		protected Char1 localSpecStock;

		/**
		 * field for Vendor
		 */
		protected Char10 localVendor;

		/**
		 * field for Customer
		 */
		protected Char10 localCustomer;

		/**
		 * field for SalesOrd
		 */
		protected Char10 localSalesOrd;

		/**
		 * field for SOrdItem
		 */
		protected Numeric6 localSOrdItem;

		/**
		 * field for SchedLine
		 */
		protected Numeric4 localSchedLine;

		/**
		 * field for ValType
		 */
		protected Char10 localValType;

		/**
		 * field for EntryQnt
		 */
		protected Quantum133 localEntryQnt;

		/**
		 * field for EntryUom
		 */
		protected Unit3 localEntryUom;

		/**
		 * field for EntryUomIso
		 */
		protected Char3 localEntryUomIso;

		/**
		 * field for PoPrQnt
		 */
		protected Quantum133 localPoPrQnt;

		/**
		 * field for OrderprUn
		 */
		protected Unit3 localOrderprUn;

		/**
		 * field for OrderprUnIso
		 */
		protected Char3 localOrderprUnIso;

		/**
		 * field for PoNumber
		 */
		protected Char10 localPoNumber;

		/**
		 * field for PoItem
		 */
		protected Numeric5 localPoItem;

		/**
		 * field for Shipping
		 */
		protected Char2 localShipping;

		/**
		 * field for CompShip
		 */
		protected Char2 localCompShip;

		/**
		 * field for NoMoreGr
		 */
		protected Char1 localNoMoreGr;

		/**
		 * field for ItemText
		 */
		protected Char50 localItemText;

		/**
		 * field for GrRcpt
		 */
		protected Char12 localGrRcpt;

		/**
		 * field for UnloadPt
		 */
		protected Char25 localUnloadPt;

		/**
		 * field for Costcenter
		 */
		protected Char10 localCostcenter;

		/**
		 * field for Orderid
		 */
		protected Char12 localOrderid;

		/**
		 * field for OrderItno
		 */
		protected Numeric4 localOrderItno;

		/**
		 * field for CalcMotive
		 */
		protected Char2 localCalcMotive;

		/**
		 * field for AssetNo
		 */
		protected Char12 localAssetNo;

		/**
		 * field for SubNumber
		 */
		protected Char4 localSubNumber;

		/**
		 * field for ReservNo
		 */
		protected Numeric10 localReservNo;

		/**
		 * field for ResItem
		 */
		protected Numeric4 localResItem;

		/**
		 * field for ResType
		 */
		protected Char1 localResType;

		/**
		 * field for Withdrawn
		 */
		protected Char1 localWithdrawn;

		/**
		 * field for MoveMat
		 */
		protected Char18 localMoveMat;

		/**
		 * field for MovePlant
		 */
		protected Char4 localMovePlant;

		/**
		 * field for MoveStloc
		 */
		protected Char4 localMoveStloc;

		/**
		 * field for MoveBatch
		 */
		protected Char10 localMoveBatch;

		/**
		 * field for MoveValType
		 */
		protected Char10 localMoveValType;

		/**
		 * field for MvtInd
		 */
		protected Char1 localMvtInd;

		/**
		 * field for MoveReas
		 */
		protected Numeric4 localMoveReas;

		/**
		 * field for RlEstKey
		 */
		protected Char8 localRlEstKey;

		/**
		 * field for RefDate
		 */
		protected Date localRefDate;

		/**
		 * field for CostObj
		 */
		protected Char12 localCostObj;

		/**
		 * field for ProfitSegmNo
		 */
		protected Numeric10 localProfitSegmNo;

		/**
		 * field for ProfitCtr
		 */
		protected Char10 localProfitCtr;

		/**
		 * field for WbsElem
		 */
		protected Char24 localWbsElem;

		/**
		 * field for Network
		 */
		protected Char12 localNetwork;

		/**
		 * field for Activity
		 */
		protected Char4 localActivity;

		/**
		 * field for PartAcct
		 */
		protected Char10 localPartAcct;

		/**
		 * field for AmountLc
		 */
		protected Decimal234 localAmountLc;

		/**
		 * field for AmountSv
		 */
		protected Decimal234 localAmountSv;

		/**
		 * field for RefDocYr
		 */
		protected Numeric4 localRefDocYr;

		/**
		 * field for RefDoc
		 */
		protected Char10 localRefDoc;

		/**
		 * field for RefDocIt
		 */
		protected Numeric4 localRefDocIt;

		/**
		 * field for Expirydate
		 */
		protected Date localExpirydate;

		/**
		 * field for ProdDate
		 */
		protected Date localProdDate;

		/**
		 * field for Fund
		 */
		protected Char10 localFund;

		/**
		 * field for FundsCtr
		 */
		protected Char16 localFundsCtr;

		/**
		 * field for CmmtItem
		 */
		protected Char14 localCmmtItem;

		/**
		 * field for ValSalesOrd
		 */
		protected Char10 localValSalesOrd;

		/**
		 * field for ValSOrdItem
		 */
		protected Numeric6 localValSOrdItem;

		/**
		 * field for ValWbsElem
		 */
		protected Char24 localValWbsElem;

		/**
		 * field for GlAccount
		 */
		protected Char10 localGlAccount;

		/**
		 * field for IndProposeQuanx
		 */
		protected Char1 localIndProposeQuanx;

		/**
		 * field for Xstob
		 */
		protected Char1 localXstob;

		/**
		 * field for EanUpc
		 */
		protected Char18 localEanUpc;

		/**
		 * field for DelivNumbToSearch
		 */
		protected Char10 localDelivNumbToSearch;

		/**
		 * field for DelivItemToSearch
		 */
		protected Numeric6 localDelivItemToSearch;

		/**
		 * field for SerialnoAutoNumberassignment
		 */
		protected Char1 localSerialnoAutoNumberassignment;

		/**
		 * field for Vendrbatch
		 */
		protected Char15 localVendrbatch;

		/**
		 * field for StgeType
		 */
		protected Char3 localStgeType;

		/**
		 * field for StgeBin
		 */
		protected Char10 localStgeBin;

		/**
		 * field for SuPlStck1
		 */
		protected Decimal30 localSuPlStck1;

		/**
		 * field for StUnQtyy1
		 */
		protected Quantum133 localStUnQtyy1;

		/**
		 * field for StUnQtyy1Iso
		 */
		protected Char3 localStUnQtyy1Iso;

		/**
		 * field for Unittype1
		 */
		protected Char3 localUnittype1;

		/**
		 * field for SuPlStck2
		 */
		protected Decimal30 localSuPlStck2;

		/**
		 * field for StUnQtyy2
		 */
		protected Quantum133 localStUnQtyy2;

		/**
		 * field for StUnQtyy2Iso
		 */
		protected Char3 localStUnQtyy2Iso;

		/**
		 * field for Unittype2
		 */
		protected Char3 localUnittype2;

		/**
		 * field for StgeTypePc
		 */
		protected Char3 localStgeTypePc;

		/**
		 * field for StgeBinPc
		 */
		protected Char10 localStgeBinPc;

		/**
		 * field for NoPstChgnt
		 */
		protected Char1 localNoPstChgnt;

		/**
		 * field for GrNumber
		 */
		protected Char10 localGrNumber;

		/**
		 * field for StgeTypeSt
		 */
		protected Char3 localStgeTypeSt;

		/**
		 * field for StgeBinSt
		 */
		protected Char10 localStgeBinSt;

		/**
		 * field for MatdocTrCancel
		 */
		protected Char10 localMatdocTrCancel;

		/**
		 * field for MatitemTrCancel
		 */
		protected Numeric4 localMatitemTrCancel;

		/**
		 * field for MatyearTrCancel
		 */
		protected Numeric4 localMatyearTrCancel;

		/**
		 * field for NoTransferReq
		 */
		protected Char1 localNoTransferReq;

		/**
		 * field for CoBusproc
		 */
		protected Char12 localCoBusproc;

		/**
		 * field for Acttype
		 */
		protected Char6 localActtype;

		/**
		 * field for SupplVend
		 */
		protected Char10 localSupplVend;

		/**
		 * field for MaterialExternal
		 */
		protected Char40 localMaterialExternal;

		/**
		 * field for MaterialGuid
		 */
		protected Char32 localMaterialGuid;

		/**
		 * field for MaterialVersion
		 */
		protected Char10 localMaterialVersion;

		/**
		 * field for MoveMatExternal
		 */
		protected Char40 localMoveMatExternal;

		/**
		 * field for MoveMatGuid
		 */
		protected Char32 localMoveMatGuid;

		/**
		 * field for MoveMatVersion
		 */
		protected Char10 localMoveMatVersion;

		/**
		 * field for FuncArea
		 */
		protected Char4 localFuncArea;

		/**
		 * field for TrPartBa
		 */
		protected Char4 localTrPartBa;

		/**
		 * field for ParCompco
		 */
		protected Char4 localParCompco;

		/**
		 * field for DelivNumb
		 */
		protected Char10 localDelivNumb;

		/**
		 * field for DelivItem
		 */
		protected Numeric6 localDelivItem;

		/**
		 * field for NbSlips
		 */
		protected Numeric3 localNbSlips;

		/**
		 * field for NbSlipsx
		 */
		protected Char1 localNbSlipsx;

		/**
		 * field for GrRcptx
		 */
		protected Char1 localGrRcptx;

		/**
		 * field for UnloadPtx
		 */
		protected Char1 localUnloadPtx;

		/**
		 * field for SpecMvmt
		 */
		protected Char1 localSpecMvmt;

		/**
		 * field for GrantNbr
		 */
		protected Char20 localGrantNbr;

		/**
		 * field for CmmtItemLong
		 */
		protected Char24 localCmmtItemLong;

		/**
		 * field for FuncAreaLong
		 */
		protected Char16 localFuncAreaLong;

		/**
		 * field for LineId
		 */
		protected Numeric6 localLineId;

		/**
		 * field for ParentId
		 */
		protected Numeric6 localParentId;

		/**
		 * field for LineDepth
		 */
		protected Numeric2 localLineDepth;

		/**
		 * field for Quantity
		 */
		protected Quantum133 localQuantity;

		/**
		 * field for BaseUom
		 */
		protected Unit3 localBaseUom;

		/**
		 * field for Longnum
		 */
		protected Char40 localLongnum;

		/**
		 * Auto generated getter method
		 * 
		 * @return Char18
		 */
		public Char18 getMaterial() {
			return localMaterial;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Material
		 */
		public void setMaterial(Char18 param) {
			this.localMaterial = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char4
		 */
		public Char4 getPlant() {
			return localPlant;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Plant
		 */
		public void setPlant(Char4 param) {
			this.localPlant = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char4
		 */
		public Char4 getStgeLoc() {
			return localStgeLoc;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            StgeLoc
		 */
		public void setStgeLoc(Char4 param) {
			this.localStgeLoc = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getBatch() {
			return localBatch;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Batch
		 */
		public void setBatch(Char10 param) {
			this.localBatch = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char3
		 */
		public Char3 getMoveType() {
			return localMoveType;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            MoveType
		 */
		public void setMoveType(Char3 param) {
			this.localMoveType = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char1
		 */
		public Char1 getStckType() {
			return localStckType;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            StckType
		 */
		public void setStckType(Char1 param) {
			this.localStckType = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char1
		 */
		public Char1 getSpecStock() {
			return localSpecStock;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            SpecStock
		 */
		public void setSpecStock(Char1 param) {
			this.localSpecStock = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getVendor() {
			return localVendor;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Vendor
		 */
		public void setVendor(Char10 param) {
			this.localVendor = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getCustomer() {
			return localCustomer;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Customer
		 */
		public void setCustomer(Char10 param) {
			this.localCustomer = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getSalesOrd() {
			return localSalesOrd;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            SalesOrd
		 */
		public void setSalesOrd(Char10 param) {
			this.localSalesOrd = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Numeric6
		 */
		public Numeric6 getSOrdItem() {
			return localSOrdItem;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            SOrdItem
		 */
		public void setSOrdItem(Numeric6 param) {
			this.localSOrdItem = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Numeric4
		 */
		public Numeric4 getSchedLine() {
			return localSchedLine;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            SchedLine
		 */
		public void setSchedLine(Numeric4 param) {
			this.localSchedLine = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getValType() {
			return localValType;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            ValType
		 */
		public void setValType(Char10 param) {
			this.localValType = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Quantum133
		 */
		public Quantum133 getEntryQnt() {
			return localEntryQnt;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            EntryQnt
		 */
		public void setEntryQnt(Quantum133 param) {
			this.localEntryQnt = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Unit3
		 */
		public Unit3 getEntryUom() {
			return localEntryUom;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            EntryUom
		 */
		public void setEntryUom(Unit3 param) {
			this.localEntryUom = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char3
		 */
		public Char3 getEntryUomIso() {
			return localEntryUomIso;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            EntryUomIso
		 */
		public void setEntryUomIso(Char3 param) {
			this.localEntryUomIso = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Quantum133
		 */
		public Quantum133 getPoPrQnt() {
			return localPoPrQnt;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            PoPrQnt
		 */
		public void setPoPrQnt(Quantum133 param) {
			this.localPoPrQnt = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Unit3
		 */
		public Unit3 getOrderprUn() {
			return localOrderprUn;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            OrderprUn
		 */
		public void setOrderprUn(Unit3 param) {
			this.localOrderprUn = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char3
		 */
		public Char3 getOrderprUnIso() {
			return localOrderprUnIso;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            OrderprUnIso
		 */
		public void setOrderprUnIso(Char3 param) {
			this.localOrderprUnIso = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getPoNumber() {
			return localPoNumber;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            PoNumber
		 */
		public void setPoNumber(Char10 param) {
			this.localPoNumber = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Numeric5
		 */
		public Numeric5 getPoItem() {
			return localPoItem;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            PoItem
		 */
		public void setPoItem(Numeric5 param) {
			this.localPoItem = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char2
		 */
		public Char2 getShipping() {
			return localShipping;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Shipping
		 */
		public void setShipping(Char2 param) {
			this.localShipping = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char2
		 */
		public Char2 getCompShip() {
			return localCompShip;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            CompShip
		 */
		public void setCompShip(Char2 param) {
			this.localCompShip = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char1
		 */
		public Char1 getNoMoreGr() {
			return localNoMoreGr;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            NoMoreGr
		 */
		public void setNoMoreGr(Char1 param) {
			this.localNoMoreGr = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char50
		 */
		public Char50 getItemText() {
			return localItemText;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            ItemText
		 */
		public void setItemText(Char50 param) {
			this.localItemText = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char12
		 */
		public Char12 getGrRcpt() {
			return localGrRcpt;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            GrRcpt
		 */
		public void setGrRcpt(Char12 param) {
			this.localGrRcpt = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char25
		 */
		public Char25 getUnloadPt() {
			return localUnloadPt;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            UnloadPt
		 */
		public void setUnloadPt(Char25 param) {
			this.localUnloadPt = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getCostcenter() {
			return localCostcenter;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Costcenter
		 */
		public void setCostcenter(Char10 param) {
			this.localCostcenter = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char12
		 */
		public Char12 getOrderid() {
			return localOrderid;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Orderid
		 */
		public void setOrderid(Char12 param) {
			this.localOrderid = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Numeric4
		 */
		public Numeric4 getOrderItno() {
			return localOrderItno;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            OrderItno
		 */
		public void setOrderItno(Numeric4 param) {
			this.localOrderItno = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char2
		 */
		public Char2 getCalcMotive() {
			return localCalcMotive;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            CalcMotive
		 */
		public void setCalcMotive(Char2 param) {
			this.localCalcMotive = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char12
		 */
		public Char12 getAssetNo() {
			return localAssetNo;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            AssetNo
		 */
		public void setAssetNo(Char12 param) {
			this.localAssetNo = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char4
		 */
		public Char4 getSubNumber() {
			return localSubNumber;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            SubNumber
		 */
		public void setSubNumber(Char4 param) {
			this.localSubNumber = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Numeric10
		 */
		public Numeric10 getReservNo() {
			return localReservNo;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            ReservNo
		 */
		public void setReservNo(Numeric10 param) {
			this.localReservNo = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Numeric4
		 */
		public Numeric4 getResItem() {
			return localResItem;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            ResItem
		 */
		public void setResItem(Numeric4 param) {
			this.localResItem = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char1
		 */
		public Char1 getResType() {
			return localResType;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            ResType
		 */
		public void setResType(Char1 param) {
			this.localResType = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char1
		 */
		public Char1 getWithdrawn() {
			return localWithdrawn;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Withdrawn
		 */
		public void setWithdrawn(Char1 param) {
			this.localWithdrawn = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char18
		 */
		public Char18 getMoveMat() {
			return localMoveMat;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            MoveMat
		 */
		public void setMoveMat(Char18 param) {
			this.localMoveMat = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char4
		 */
		public Char4 getMovePlant() {
			return localMovePlant;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            MovePlant
		 */
		public void setMovePlant(Char4 param) {
			this.localMovePlant = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char4
		 */
		public Char4 getMoveStloc() {
			return localMoveStloc;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            MoveStloc
		 */
		public void setMoveStloc(Char4 param) {
			this.localMoveStloc = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getMoveBatch() {
			return localMoveBatch;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            MoveBatch
		 */
		public void setMoveBatch(Char10 param) {
			this.localMoveBatch = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getMoveValType() {
			return localMoveValType;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            MoveValType
		 */
		public void setMoveValType(Char10 param) {
			this.localMoveValType = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char1
		 */
		public Char1 getMvtInd() {
			return localMvtInd;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            MvtInd
		 */
		public void setMvtInd(Char1 param) {
			this.localMvtInd = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Numeric4
		 */
		public Numeric4 getMoveReas() {
			return localMoveReas;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            MoveReas
		 */
		public void setMoveReas(Numeric4 param) {
			this.localMoveReas = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char8
		 */
		public Char8 getRlEstKey() {
			return localRlEstKey;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            RlEstKey
		 */
		public void setRlEstKey(Char8 param) {
			this.localRlEstKey = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Date
		 */
		public Date getRefDate() {
			return localRefDate;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            RefDate
		 */
		public void setRefDate(Date param) {
			this.localRefDate = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char12
		 */
		public Char12 getCostObj() {
			return localCostObj;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            CostObj
		 */
		public void setCostObj(Char12 param) {
			this.localCostObj = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Numeric10
		 */
		public Numeric10 getProfitSegmNo() {
			return localProfitSegmNo;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            ProfitSegmNo
		 */
		public void setProfitSegmNo(Numeric10 param) {
			this.localProfitSegmNo = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getProfitCtr() {
			return localProfitCtr;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            ProfitCtr
		 */
		public void setProfitCtr(Char10 param) {
			this.localProfitCtr = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char24
		 */
		public Char24 getWbsElem() {
			return localWbsElem;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            WbsElem
		 */
		public void setWbsElem(Char24 param) {
			this.localWbsElem = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char12
		 */
		public Char12 getNetwork() {
			return localNetwork;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Network
		 */
		public void setNetwork(Char12 param) {
			this.localNetwork = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char4
		 */
		public Char4 getActivity() {
			return localActivity;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Activity
		 */
		public void setActivity(Char4 param) {
			this.localActivity = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getPartAcct() {
			return localPartAcct;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            PartAcct
		 */
		public void setPartAcct(Char10 param) {
			this.localPartAcct = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Decimal234
		 */
		public Decimal234 getAmountLc() {
			return localAmountLc;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            AmountLc
		 */
		public void setAmountLc(Decimal234 param) {
			this.localAmountLc = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Decimal234
		 */
		public Decimal234 getAmountSv() {
			return localAmountSv;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            AmountSv
		 */
		public void setAmountSv(Decimal234 param) {
			this.localAmountSv = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Numeric4
		 */
		public Numeric4 getRefDocYr() {
			return localRefDocYr;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            RefDocYr
		 */
		public void setRefDocYr(Numeric4 param) {
			this.localRefDocYr = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getRefDoc() {
			return localRefDoc;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            RefDoc
		 */
		public void setRefDoc(Char10 param) {
			this.localRefDoc = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Numeric4
		 */
		public Numeric4 getRefDocIt() {
			return localRefDocIt;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            RefDocIt
		 */
		public void setRefDocIt(Numeric4 param) {
			this.localRefDocIt = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Date
		 */
		public Date getExpirydate() {
			return localExpirydate;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Expirydate
		 */
		public void setExpirydate(Date param) {
			this.localExpirydate = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Date
		 */
		public Date getProdDate() {
			return localProdDate;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            ProdDate
		 */
		public void setProdDate(Date param) {
			this.localProdDate = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getFund() {
			return localFund;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Fund
		 */
		public void setFund(Char10 param) {
			this.localFund = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char16
		 */
		public Char16 getFundsCtr() {
			return localFundsCtr;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            FundsCtr
		 */
		public void setFundsCtr(Char16 param) {
			this.localFundsCtr = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char14
		 */
		public Char14 getCmmtItem() {
			return localCmmtItem;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            CmmtItem
		 */
		public void setCmmtItem(Char14 param) {
			this.localCmmtItem = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getValSalesOrd() {
			return localValSalesOrd;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            ValSalesOrd
		 */
		public void setValSalesOrd(Char10 param) {
			this.localValSalesOrd = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Numeric6
		 */
		public Numeric6 getValSOrdItem() {
			return localValSOrdItem;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            ValSOrdItem
		 */
		public void setValSOrdItem(Numeric6 param) {
			this.localValSOrdItem = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char24
		 */
		public Char24 getValWbsElem() {
			return localValWbsElem;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            ValWbsElem
		 */
		public void setValWbsElem(Char24 param) {
			this.localValWbsElem = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getGlAccount() {
			return localGlAccount;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            GlAccount
		 */
		public void setGlAccount(Char10 param) {
			this.localGlAccount = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char1
		 */
		public Char1 getIndProposeQuanx() {
			return localIndProposeQuanx;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            IndProposeQuanx
		 */
		public void setIndProposeQuanx(Char1 param) {
			this.localIndProposeQuanx = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char1
		 */
		public Char1 getXstob() {
			return localXstob;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Xstob
		 */
		public void setXstob(Char1 param) {
			this.localXstob = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char18
		 */
		public Char18 getEanUpc() {
			return localEanUpc;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            EanUpc
		 */
		public void setEanUpc(Char18 param) {
			this.localEanUpc = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getDelivNumbToSearch() {
			return localDelivNumbToSearch;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            DelivNumbToSearch
		 */
		public void setDelivNumbToSearch(Char10 param) {
			this.localDelivNumbToSearch = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Numeric6
		 */
		public Numeric6 getDelivItemToSearch() {
			return localDelivItemToSearch;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            DelivItemToSearch
		 */
		public void setDelivItemToSearch(Numeric6 param) {
			this.localDelivItemToSearch = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char1
		 */
		public Char1 getSerialnoAutoNumberassignment() {
			return localSerialnoAutoNumberassignment;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            SerialnoAutoNumberassignment
		 */
		public void setSerialnoAutoNumberassignment(Char1 param) {
			this.localSerialnoAutoNumberassignment = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char15
		 */
		public Char15 getVendrbatch() {
			return localVendrbatch;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Vendrbatch
		 */
		public void setVendrbatch(Char15 param) {
			this.localVendrbatch = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char3
		 */
		public Char3 getStgeType() {
			return localStgeType;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            StgeType
		 */
		public void setStgeType(Char3 param) {
			this.localStgeType = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getStgeBin() {
			return localStgeBin;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            StgeBin
		 */
		public void setStgeBin(Char10 param) {
			this.localStgeBin = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Decimal30
		 */
		public Decimal30 getSuPlStck1() {
			return localSuPlStck1;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            SuPlStck1
		 */
		public void setSuPlStck1(Decimal30 param) {
			this.localSuPlStck1 = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Quantum133
		 */
		public Quantum133 getStUnQtyy1() {
			return localStUnQtyy1;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            StUnQtyy1
		 */
		public void setStUnQtyy1(Quantum133 param) {
			this.localStUnQtyy1 = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char3
		 */
		public Char3 getStUnQtyy1Iso() {
			return localStUnQtyy1Iso;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            StUnQtyy1Iso
		 */
		public void setStUnQtyy1Iso(Char3 param) {
			this.localStUnQtyy1Iso = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char3
		 */
		public Char3 getUnittype1() {
			return localUnittype1;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Unittype1
		 */
		public void setUnittype1(Char3 param) {
			this.localUnittype1 = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Decimal30
		 */
		public Decimal30 getSuPlStck2() {
			return localSuPlStck2;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            SuPlStck2
		 */
		public void setSuPlStck2(Decimal30 param) {
			this.localSuPlStck2 = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Quantum133
		 */
		public Quantum133 getStUnQtyy2() {
			return localStUnQtyy2;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            StUnQtyy2
		 */
		public void setStUnQtyy2(Quantum133 param) {
			this.localStUnQtyy2 = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char3
		 */
		public Char3 getStUnQtyy2Iso() {
			return localStUnQtyy2Iso;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            StUnQtyy2Iso
		 */
		public void setStUnQtyy2Iso(Char3 param) {
			this.localStUnQtyy2Iso = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char3
		 */
		public Char3 getUnittype2() {
			return localUnittype2;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Unittype2
		 */
		public void setUnittype2(Char3 param) {
			this.localUnittype2 = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char3
		 */
		public Char3 getStgeTypePc() {
			return localStgeTypePc;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            StgeTypePc
		 */
		public void setStgeTypePc(Char3 param) {
			this.localStgeTypePc = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getStgeBinPc() {
			return localStgeBinPc;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            StgeBinPc
		 */
		public void setStgeBinPc(Char10 param) {
			this.localStgeBinPc = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char1
		 */
		public Char1 getNoPstChgnt() {
			return localNoPstChgnt;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            NoPstChgnt
		 */
		public void setNoPstChgnt(Char1 param) {
			this.localNoPstChgnt = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getGrNumber() {
			return localGrNumber;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            GrNumber
		 */
		public void setGrNumber(Char10 param) {
			this.localGrNumber = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char3
		 */
		public Char3 getStgeTypeSt() {
			return localStgeTypeSt;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            StgeTypeSt
		 */
		public void setStgeTypeSt(Char3 param) {
			this.localStgeTypeSt = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getStgeBinSt() {
			return localStgeBinSt;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            StgeBinSt
		 */
		public void setStgeBinSt(Char10 param) {
			this.localStgeBinSt = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getMatdocTrCancel() {
			return localMatdocTrCancel;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            MatdocTrCancel
		 */
		public void setMatdocTrCancel(Char10 param) {
			this.localMatdocTrCancel = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Numeric4
		 */
		public Numeric4 getMatitemTrCancel() {
			return localMatitemTrCancel;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            MatitemTrCancel
		 */
		public void setMatitemTrCancel(Numeric4 param) {
			this.localMatitemTrCancel = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Numeric4
		 */
		public Numeric4 getMatyearTrCancel() {
			return localMatyearTrCancel;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            MatyearTrCancel
		 */
		public void setMatyearTrCancel(Numeric4 param) {
			this.localMatyearTrCancel = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char1
		 */
		public Char1 getNoTransferReq() {
			return localNoTransferReq;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            NoTransferReq
		 */
		public void setNoTransferReq(Char1 param) {
			this.localNoTransferReq = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char12
		 */
		public Char12 getCoBusproc() {
			return localCoBusproc;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            CoBusproc
		 */
		public void setCoBusproc(Char12 param) {
			this.localCoBusproc = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char6
		 */
		public Char6 getActtype() {
			return localActtype;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Acttype
		 */
		public void setActtype(Char6 param) {
			this.localActtype = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getSupplVend() {
			return localSupplVend;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            SupplVend
		 */
		public void setSupplVend(Char10 param) {
			this.localSupplVend = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char40
		 */
		public Char40 getMaterialExternal() {
			return localMaterialExternal;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            MaterialExternal
		 */
		public void setMaterialExternal(Char40 param) {
			this.localMaterialExternal = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char32
		 */
		public Char32 getMaterialGuid() {
			return localMaterialGuid;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            MaterialGuid
		 */
		public void setMaterialGuid(Char32 param) {
			this.localMaterialGuid = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getMaterialVersion() {
			return localMaterialVersion;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            MaterialVersion
		 */
		public void setMaterialVersion(Char10 param) {
			this.localMaterialVersion = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char40
		 */
		public Char40 getMoveMatExternal() {
			return localMoveMatExternal;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            MoveMatExternal
		 */
		public void setMoveMatExternal(Char40 param) {
			this.localMoveMatExternal = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char32
		 */
		public Char32 getMoveMatGuid() {
			return localMoveMatGuid;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            MoveMatGuid
		 */
		public void setMoveMatGuid(Char32 param) {
			this.localMoveMatGuid = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getMoveMatVersion() {
			return localMoveMatVersion;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            MoveMatVersion
		 */
		public void setMoveMatVersion(Char10 param) {
			this.localMoveMatVersion = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char4
		 */
		public Char4 getFuncArea() {
			return localFuncArea;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            FuncArea
		 */
		public void setFuncArea(Char4 param) {
			this.localFuncArea = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char4
		 */
		public Char4 getTrPartBa() {
			return localTrPartBa;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TrPartBa
		 */
		public void setTrPartBa(Char4 param) {
			this.localTrPartBa = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char4
		 */
		public Char4 getParCompco() {
			return localParCompco;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            ParCompco
		 */
		public void setParCompco(Char4 param) {
			this.localParCompco = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getDelivNumb() {
			return localDelivNumb;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            DelivNumb
		 */
		public void setDelivNumb(Char10 param) {
			this.localDelivNumb = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Numeric6
		 */
		public Numeric6 getDelivItem() {
			return localDelivItem;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            DelivItem
		 */
		public void setDelivItem(Numeric6 param) {
			this.localDelivItem = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Numeric3
		 */
		public Numeric3 getNbSlips() {
			return localNbSlips;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            NbSlips
		 */
		public void setNbSlips(Numeric3 param) {
			this.localNbSlips = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char1
		 */
		public Char1 getNbSlipsx() {
			return localNbSlipsx;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            NbSlipsx
		 */
		public void setNbSlipsx(Char1 param) {
			this.localNbSlipsx = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char1
		 */
		public Char1 getGrRcptx() {
			return localGrRcptx;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            GrRcptx
		 */
		public void setGrRcptx(Char1 param) {
			this.localGrRcptx = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char1
		 */
		public Char1 getUnloadPtx() {
			return localUnloadPtx;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            UnloadPtx
		 */
		public void setUnloadPtx(Char1 param) {
			this.localUnloadPtx = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char1
		 */
		public Char1 getSpecMvmt() {
			return localSpecMvmt;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            SpecMvmt
		 */
		public void setSpecMvmt(Char1 param) {
			this.localSpecMvmt = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char20
		 */
		public Char20 getGrantNbr() {
			return localGrantNbr;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            GrantNbr
		 */
		public void setGrantNbr(Char20 param) {
			this.localGrantNbr = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char24
		 */
		public Char24 getCmmtItemLong() {
			return localCmmtItemLong;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            CmmtItemLong
		 */
		public void setCmmtItemLong(Char24 param) {
			this.localCmmtItemLong = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char16
		 */
		public Char16 getFuncAreaLong() {
			return localFuncAreaLong;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            FuncAreaLong
		 */
		public void setFuncAreaLong(Char16 param) {
			this.localFuncAreaLong = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Numeric6
		 */
		public Numeric6 getLineId() {
			return localLineId;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            LineId
		 */
		public void setLineId(Numeric6 param) {
			this.localLineId = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Numeric6
		 */
		public Numeric6 getParentId() {
			return localParentId;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            ParentId
		 */
		public void setParentId(Numeric6 param) {
			this.localParentId = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Numeric2
		 */
		public Numeric2 getLineDepth() {
			return localLineDepth;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            LineDepth
		 */
		public void setLineDepth(Numeric2 param) {
			this.localLineDepth = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Quantum133
		 */
		public Quantum133 getQuantity() {
			return localQuantity;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Quantity
		 */
		public void setQuantity(Quantum133 param) {
			this.localQuantity = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Unit3
		 */
		public Unit3 getBaseUom() {
			return localBaseUom;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            BaseUom
		 */
		public void setBaseUom(Unit3 param) {
			this.localBaseUom = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char40
		 */
		public Char40 getLongnum() {
			return localLongnum;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Longnum
		 */
		public void setLongnum(Char40 param) {
			this.localLongnum = param;
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, parentQName));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:soap:functions:mc-style");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":Bapi2017GmItemCreate",
							xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "Bapi2017GmItemCreate", xmlWriter);
				}
			}

			if (localMaterial == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Material cannot be null!!");
			}

			localMaterial.serialize(new javax.xml.namespace.QName("",
					"Material"), xmlWriter);

			if (localPlant == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Plant cannot be null!!");
			}

			localPlant.serialize(new javax.xml.namespace.QName("", "Plant"),
					xmlWriter);

			if (localStgeLoc == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"StgeLoc cannot be null!!");
			}

			localStgeLoc.serialize(
					new javax.xml.namespace.QName("", "StgeLoc"), xmlWriter);

			if (localBatch == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Batch cannot be null!!");
			}

			localBatch.serialize(new javax.xml.namespace.QName("", "Batch"),
					xmlWriter);

			if (localMoveType == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"MoveType cannot be null!!");
			}

			localMoveType.serialize(new javax.xml.namespace.QName("",
					"MoveType"), xmlWriter);

			if (localStckType == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"StckType cannot be null!!");
			}

			localStckType.serialize(new javax.xml.namespace.QName("",
					"StckType"), xmlWriter);

			if (localSpecStock == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"SpecStock cannot be null!!");
			}

			localSpecStock.serialize(new javax.xml.namespace.QName("",
					"SpecStock"), xmlWriter);

			if (localVendor == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Vendor cannot be null!!");
			}

			localVendor.serialize(new javax.xml.namespace.QName("", "Vendor"),
					xmlWriter);

			if (localCustomer == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Customer cannot be null!!");
			}

			localCustomer.serialize(new javax.xml.namespace.QName("",
					"Customer"), xmlWriter);

			if (localSalesOrd == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"SalesOrd cannot be null!!");
			}

			localSalesOrd.serialize(new javax.xml.namespace.QName("",
					"SalesOrd"), xmlWriter);

			if (localSOrdItem == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"SOrdItem cannot be null!!");
			}

			localSOrdItem.serialize(new javax.xml.namespace.QName("",
					"SOrdItem"), xmlWriter);

			if (localSchedLine == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"SchedLine cannot be null!!");
			}

			localSchedLine.serialize(new javax.xml.namespace.QName("",
					"SchedLine"), xmlWriter);

			if (localValType == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"ValType cannot be null!!");
			}

			localValType.serialize(
					new javax.xml.namespace.QName("", "ValType"), xmlWriter);

			if (localEntryQnt == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"EntryQnt cannot be null!!");
			}

			localEntryQnt.serialize(new javax.xml.namespace.QName("",
					"EntryQnt"), xmlWriter);

			if (localEntryUom == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"EntryUom cannot be null!!");
			}

			localEntryUom.serialize(new javax.xml.namespace.QName("",
					"EntryUom"), xmlWriter);

			if (localEntryUomIso == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"EntryUomIso cannot be null!!");
			}

			localEntryUomIso.serialize(new javax.xml.namespace.QName("",
					"EntryUomIso"), xmlWriter);

			if (localPoPrQnt == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"PoPrQnt cannot be null!!");
			}

			localPoPrQnt.serialize(
					new javax.xml.namespace.QName("", "PoPrQnt"), xmlWriter);

			if (localOrderprUn == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"OrderprUn cannot be null!!");
			}

			localOrderprUn.serialize(new javax.xml.namespace.QName("",
					"OrderprUn"), xmlWriter);

			if (localOrderprUnIso == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"OrderprUnIso cannot be null!!");
			}

			localOrderprUnIso.serialize(new javax.xml.namespace.QName("",
					"OrderprUnIso"), xmlWriter);

			if (localPoNumber == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"PoNumber cannot be null!!");
			}

			localPoNumber.serialize(new javax.xml.namespace.QName("",
					"PoNumber"), xmlWriter);

			if (localPoItem == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"PoItem cannot be null!!");
			}

			localPoItem.serialize(new javax.xml.namespace.QName("", "PoItem"),
					xmlWriter);

			if (localShipping == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Shipping cannot be null!!");
			}

			localShipping.serialize(new javax.xml.namespace.QName("",
					"Shipping"), xmlWriter);

			if (localCompShip == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"CompShip cannot be null!!");
			}

			localCompShip.serialize(new javax.xml.namespace.QName("",
					"CompShip"), xmlWriter);

			if (localNoMoreGr == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"NoMoreGr cannot be null!!");
			}

			localNoMoreGr.serialize(new javax.xml.namespace.QName("",
					"NoMoreGr"), xmlWriter);

			if (localItemText == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"ItemText cannot be null!!");
			}

			localItemText.serialize(new javax.xml.namespace.QName("",
					"ItemText"), xmlWriter);

			if (localGrRcpt == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"GrRcpt cannot be null!!");
			}

			localGrRcpt.serialize(new javax.xml.namespace.QName("", "GrRcpt"),
					xmlWriter);

			if (localUnloadPt == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"UnloadPt cannot be null!!");
			}

			localUnloadPt.serialize(new javax.xml.namespace.QName("",
					"UnloadPt"), xmlWriter);

			if (localCostcenter == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Costcenter cannot be null!!");
			}

			localCostcenter.serialize(new javax.xml.namespace.QName("",
					"Costcenter"), xmlWriter);

			if (localOrderid == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Orderid cannot be null!!");
			}

			localOrderid.serialize(
					new javax.xml.namespace.QName("", "Orderid"), xmlWriter);

			if (localOrderItno == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"OrderItno cannot be null!!");
			}

			localOrderItno.serialize(new javax.xml.namespace.QName("",
					"OrderItno"), xmlWriter);

			if (localCalcMotive == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"CalcMotive cannot be null!!");
			}

			localCalcMotive.serialize(new javax.xml.namespace.QName("",
					"CalcMotive"), xmlWriter);

			if (localAssetNo == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"AssetNo cannot be null!!");
			}

			localAssetNo.serialize(
					new javax.xml.namespace.QName("", "AssetNo"), xmlWriter);

			if (localSubNumber == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"SubNumber cannot be null!!");
			}

			localSubNumber.serialize(new javax.xml.namespace.QName("",
					"SubNumber"), xmlWriter);

			if (localReservNo == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"ReservNo cannot be null!!");
			}

			localReservNo.serialize(new javax.xml.namespace.QName("",
					"ReservNo"), xmlWriter);

			if (localResItem == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"ResItem cannot be null!!");
			}

			localResItem.serialize(
					new javax.xml.namespace.QName("", "ResItem"), xmlWriter);

			if (localResType == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"ResType cannot be null!!");
			}

			localResType.serialize(
					new javax.xml.namespace.QName("", "ResType"), xmlWriter);

			if (localWithdrawn == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Withdrawn cannot be null!!");
			}

			localWithdrawn.serialize(new javax.xml.namespace.QName("",
					"Withdrawn"), xmlWriter);

			if (localMoveMat == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"MoveMat cannot be null!!");
			}

			localMoveMat.serialize(
					new javax.xml.namespace.QName("", "MoveMat"), xmlWriter);

			if (localMovePlant == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"MovePlant cannot be null!!");
			}

			localMovePlant.serialize(new javax.xml.namespace.QName("",
					"MovePlant"), xmlWriter);

			if (localMoveStloc == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"MoveStloc cannot be null!!");
			}

			localMoveStloc.serialize(new javax.xml.namespace.QName("",
					"MoveStloc"), xmlWriter);

			if (localMoveBatch == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"MoveBatch cannot be null!!");
			}

			localMoveBatch.serialize(new javax.xml.namespace.QName("",
					"MoveBatch"), xmlWriter);

			if (localMoveValType == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"MoveValType cannot be null!!");
			}

			localMoveValType.serialize(new javax.xml.namespace.QName("",
					"MoveValType"), xmlWriter);

			if (localMvtInd == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"MvtInd cannot be null!!");
			}

			localMvtInd.serialize(new javax.xml.namespace.QName("", "MvtInd"),
					xmlWriter);

			if (localMoveReas == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"MoveReas cannot be null!!");
			}

			localMoveReas.serialize(new javax.xml.namespace.QName("",
					"MoveReas"), xmlWriter);

			if (localRlEstKey == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"RlEstKey cannot be null!!");
			}

			localRlEstKey.serialize(new javax.xml.namespace.QName("",
					"RlEstKey"), xmlWriter);

			if (localRefDate == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"RefDate cannot be null!!");
			}

			localRefDate.serialize(
					new javax.xml.namespace.QName("", "RefDate"), xmlWriter);

			if (localCostObj == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"CostObj cannot be null!!");
			}

			localCostObj.serialize(
					new javax.xml.namespace.QName("", "CostObj"), xmlWriter);

			if (localProfitSegmNo == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"ProfitSegmNo cannot be null!!");
			}

			localProfitSegmNo.serialize(new javax.xml.namespace.QName("",
					"ProfitSegmNo"), xmlWriter);

			if (localProfitCtr == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"ProfitCtr cannot be null!!");
			}

			localProfitCtr.serialize(new javax.xml.namespace.QName("",
					"ProfitCtr"), xmlWriter);

			if (localWbsElem == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"WbsElem cannot be null!!");
			}

			localWbsElem.serialize(
					new javax.xml.namespace.QName("", "WbsElem"), xmlWriter);

			if (localNetwork == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Network cannot be null!!");
			}

			localNetwork.serialize(
					new javax.xml.namespace.QName("", "Network"), xmlWriter);

			if (localActivity == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Activity cannot be null!!");
			}

			localActivity.serialize(new javax.xml.namespace.QName("",
					"Activity"), xmlWriter);

			if (localPartAcct == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"PartAcct cannot be null!!");
			}

			localPartAcct.serialize(new javax.xml.namespace.QName("",
					"PartAcct"), xmlWriter);

			if (localAmountLc == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"AmountLc cannot be null!!");
			}

			localAmountLc.serialize(new javax.xml.namespace.QName("",
					"AmountLc"), xmlWriter);

			if (localAmountSv == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"AmountSv cannot be null!!");
			}

			localAmountSv.serialize(new javax.xml.namespace.QName("",
					"AmountSv"), xmlWriter);

			if (localRefDocYr == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"RefDocYr cannot be null!!");
			}

			localRefDocYr.serialize(new javax.xml.namespace.QName("",
					"RefDocYr"), xmlWriter);

			if (localRefDoc == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"RefDoc cannot be null!!");
			}

			localRefDoc.serialize(new javax.xml.namespace.QName("", "RefDoc"),
					xmlWriter);

			if (localRefDocIt == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"RefDocIt cannot be null!!");
			}

			localRefDocIt.serialize(new javax.xml.namespace.QName("",
					"RefDocIt"), xmlWriter);

			if (localExpirydate == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Expirydate cannot be null!!");
			}

			localExpirydate.serialize(new javax.xml.namespace.QName("",
					"Expirydate"), xmlWriter);

			if (localProdDate == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"ProdDate cannot be null!!");
			}

			localProdDate.serialize(new javax.xml.namespace.QName("",
					"ProdDate"), xmlWriter);

			if (localFund == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Fund cannot be null!!");
			}

			localFund.serialize(new javax.xml.namespace.QName("", "Fund"),
					xmlWriter);

			if (localFundsCtr == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"FundsCtr cannot be null!!");
			}

			localFundsCtr.serialize(new javax.xml.namespace.QName("",
					"FundsCtr"), xmlWriter);

			if (localCmmtItem == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"CmmtItem cannot be null!!");
			}

			localCmmtItem.serialize(new javax.xml.namespace.QName("",
					"CmmtItem"), xmlWriter);

			if (localValSalesOrd == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"ValSalesOrd cannot be null!!");
			}

			localValSalesOrd.serialize(new javax.xml.namespace.QName("",
					"ValSalesOrd"), xmlWriter);

			if (localValSOrdItem == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"ValSOrdItem cannot be null!!");
			}

			localValSOrdItem.serialize(new javax.xml.namespace.QName("",
					"ValSOrdItem"), xmlWriter);

			if (localValWbsElem == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"ValWbsElem cannot be null!!");
			}

			localValWbsElem.serialize(new javax.xml.namespace.QName("",
					"ValWbsElem"), xmlWriter);

			if (localGlAccount == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"GlAccount cannot be null!!");
			}

			localGlAccount.serialize(new javax.xml.namespace.QName("",
					"GlAccount"), xmlWriter);

			if (localIndProposeQuanx == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"IndProposeQuanx cannot be null!!");
			}

			localIndProposeQuanx.serialize(new javax.xml.namespace.QName("",
					"IndProposeQuanx"), xmlWriter);

			if (localXstob == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Xstob cannot be null!!");
			}

			localXstob.serialize(new javax.xml.namespace.QName("", "Xstob"),
					xmlWriter);

			if (localEanUpc == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"EanUpc cannot be null!!");
			}

			localEanUpc.serialize(new javax.xml.namespace.QName("", "EanUpc"),
					xmlWriter);

			if (localDelivNumbToSearch == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"DelivNumbToSearch cannot be null!!");
			}

			localDelivNumbToSearch.serialize(new javax.xml.namespace.QName("",
					"DelivNumbToSearch"), xmlWriter);

			if (localDelivItemToSearch == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"DelivItemToSearch cannot be null!!");
			}

			localDelivItemToSearch.serialize(new javax.xml.namespace.QName("",
					"DelivItemToSearch"), xmlWriter);

			if (localSerialnoAutoNumberassignment == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"SerialnoAutoNumberassignment cannot be null!!");
			}

			localSerialnoAutoNumberassignment.serialize(
					new javax.xml.namespace.QName("",
							"SerialnoAutoNumberassignment"), xmlWriter);

			if (localVendrbatch == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Vendrbatch cannot be null!!");
			}

			localVendrbatch.serialize(new javax.xml.namespace.QName("",
					"Vendrbatch"), xmlWriter);

			if (localStgeType == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"StgeType cannot be null!!");
			}

			localStgeType.serialize(new javax.xml.namespace.QName("",
					"StgeType"), xmlWriter);

			if (localStgeBin == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"StgeBin cannot be null!!");
			}

			localStgeBin.serialize(
					new javax.xml.namespace.QName("", "StgeBin"), xmlWriter);

			if (localSuPlStck1 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"SuPlStck1 cannot be null!!");
			}

			localSuPlStck1.serialize(new javax.xml.namespace.QName("",
					"SuPlStck1"), xmlWriter);

			if (localStUnQtyy1 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"StUnQtyy1 cannot be null!!");
			}

			localStUnQtyy1.serialize(new javax.xml.namespace.QName("",
					"StUnQtyy1"), xmlWriter);

			if (localStUnQtyy1Iso == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"StUnQtyy1Iso cannot be null!!");
			}

			localStUnQtyy1Iso.serialize(new javax.xml.namespace.QName("",
					"StUnQtyy1Iso"), xmlWriter);

			if (localUnittype1 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Unittype1 cannot be null!!");
			}

			localUnittype1.serialize(new javax.xml.namespace.QName("",
					"Unittype1"), xmlWriter);

			if (localSuPlStck2 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"SuPlStck2 cannot be null!!");
			}

			localSuPlStck2.serialize(new javax.xml.namespace.QName("",
					"SuPlStck2"), xmlWriter);

			if (localStUnQtyy2 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"StUnQtyy2 cannot be null!!");
			}

			localStUnQtyy2.serialize(new javax.xml.namespace.QName("",
					"StUnQtyy2"), xmlWriter);

			if (localStUnQtyy2Iso == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"StUnQtyy2Iso cannot be null!!");
			}

			localStUnQtyy2Iso.serialize(new javax.xml.namespace.QName("",
					"StUnQtyy2Iso"), xmlWriter);

			if (localUnittype2 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Unittype2 cannot be null!!");
			}

			localUnittype2.serialize(new javax.xml.namespace.QName("",
					"Unittype2"), xmlWriter);

			if (localStgeTypePc == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"StgeTypePc cannot be null!!");
			}

			localStgeTypePc.serialize(new javax.xml.namespace.QName("",
					"StgeTypePc"), xmlWriter);

			if (localStgeBinPc == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"StgeBinPc cannot be null!!");
			}

			localStgeBinPc.serialize(new javax.xml.namespace.QName("",
					"StgeBinPc"), xmlWriter);

			if (localNoPstChgnt == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"NoPstChgnt cannot be null!!");
			}

			localNoPstChgnt.serialize(new javax.xml.namespace.QName("",
					"NoPstChgnt"), xmlWriter);

			if (localGrNumber == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"GrNumber cannot be null!!");
			}

			localGrNumber.serialize(new javax.xml.namespace.QName("",
					"GrNumber"), xmlWriter);

			if (localStgeTypeSt == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"StgeTypeSt cannot be null!!");
			}

			localStgeTypeSt.serialize(new javax.xml.namespace.QName("",
					"StgeTypeSt"), xmlWriter);

			if (localStgeBinSt == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"StgeBinSt cannot be null!!");
			}

			localStgeBinSt.serialize(new javax.xml.namespace.QName("",
					"StgeBinSt"), xmlWriter);

			if (localMatdocTrCancel == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"MatdocTrCancel cannot be null!!");
			}

			localMatdocTrCancel.serialize(new javax.xml.namespace.QName("",
					"MatdocTrCancel"), xmlWriter);

			if (localMatitemTrCancel == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"MatitemTrCancel cannot be null!!");
			}

			localMatitemTrCancel.serialize(new javax.xml.namespace.QName("",
					"MatitemTrCancel"), xmlWriter);

			if (localMatyearTrCancel == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"MatyearTrCancel cannot be null!!");
			}

			localMatyearTrCancel.serialize(new javax.xml.namespace.QName("",
					"MatyearTrCancel"), xmlWriter);

			if (localNoTransferReq == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"NoTransferReq cannot be null!!");
			}

			localNoTransferReq.serialize(new javax.xml.namespace.QName("",
					"NoTransferReq"), xmlWriter);

			if (localCoBusproc == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"CoBusproc cannot be null!!");
			}

			localCoBusproc.serialize(new javax.xml.namespace.QName("",
					"CoBusproc"), xmlWriter);

			if (localActtype == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Acttype cannot be null!!");
			}

			localActtype.serialize(
					new javax.xml.namespace.QName("", "Acttype"), xmlWriter);

			if (localSupplVend == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"SupplVend cannot be null!!");
			}

			localSupplVend.serialize(new javax.xml.namespace.QName("",
					"SupplVend"), xmlWriter);

			if (localMaterialExternal == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"MaterialExternal cannot be null!!");
			}

			localMaterialExternal.serialize(new javax.xml.namespace.QName("",
					"MaterialExternal"), xmlWriter);

			if (localMaterialGuid == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"MaterialGuid cannot be null!!");
			}

			localMaterialGuid.serialize(new javax.xml.namespace.QName("",
					"MaterialGuid"), xmlWriter);

			if (localMaterialVersion == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"MaterialVersion cannot be null!!");
			}

			localMaterialVersion.serialize(new javax.xml.namespace.QName("",
					"MaterialVersion"), xmlWriter);

			if (localMoveMatExternal == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"MoveMatExternal cannot be null!!");
			}

			localMoveMatExternal.serialize(new javax.xml.namespace.QName("",
					"MoveMatExternal"), xmlWriter);

			if (localMoveMatGuid == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"MoveMatGuid cannot be null!!");
			}

			localMoveMatGuid.serialize(new javax.xml.namespace.QName("",
					"MoveMatGuid"), xmlWriter);

			if (localMoveMatVersion == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"MoveMatVersion cannot be null!!");
			}

			localMoveMatVersion.serialize(new javax.xml.namespace.QName("",
					"MoveMatVersion"), xmlWriter);

			if (localFuncArea == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"FuncArea cannot be null!!");
			}

			localFuncArea.serialize(new javax.xml.namespace.QName("",
					"FuncArea"), xmlWriter);

			if (localTrPartBa == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"TrPartBa cannot be null!!");
			}

			localTrPartBa.serialize(new javax.xml.namespace.QName("",
					"TrPartBa"), xmlWriter);

			if (localParCompco == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"ParCompco cannot be null!!");
			}

			localParCompco.serialize(new javax.xml.namespace.QName("",
					"ParCompco"), xmlWriter);

			if (localDelivNumb == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"DelivNumb cannot be null!!");
			}

			localDelivNumb.serialize(new javax.xml.namespace.QName("",
					"DelivNumb"), xmlWriter);

			if (localDelivItem == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"DelivItem cannot be null!!");
			}

			localDelivItem.serialize(new javax.xml.namespace.QName("",
					"DelivItem"), xmlWriter);

			if (localNbSlips == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"NbSlips cannot be null!!");
			}

			localNbSlips.serialize(
					new javax.xml.namespace.QName("", "NbSlips"), xmlWriter);

			if (localNbSlipsx == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"NbSlipsx cannot be null!!");
			}

			localNbSlipsx.serialize(new javax.xml.namespace.QName("",
					"NbSlipsx"), xmlWriter);

			if (localGrRcptx == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"GrRcptx cannot be null!!");
			}

			localGrRcptx.serialize(
					new javax.xml.namespace.QName("", "GrRcptx"), xmlWriter);

			if (localUnloadPtx == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"UnloadPtx cannot be null!!");
			}

			localUnloadPtx.serialize(new javax.xml.namespace.QName("",
					"UnloadPtx"), xmlWriter);

			if (localSpecMvmt == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"SpecMvmt cannot be null!!");
			}

			localSpecMvmt.serialize(new javax.xml.namespace.QName("",
					"SpecMvmt"), xmlWriter);

			if (localGrantNbr == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"GrantNbr cannot be null!!");
			}

			localGrantNbr.serialize(new javax.xml.namespace.QName("",
					"GrantNbr"), xmlWriter);

			if (localCmmtItemLong == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"CmmtItemLong cannot be null!!");
			}

			localCmmtItemLong.serialize(new javax.xml.namespace.QName("",
					"CmmtItemLong"), xmlWriter);

			if (localFuncAreaLong == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"FuncAreaLong cannot be null!!");
			}

			localFuncAreaLong.serialize(new javax.xml.namespace.QName("",
					"FuncAreaLong"), xmlWriter);

			if (localLineId == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"LineId cannot be null!!");
			}

			localLineId.serialize(new javax.xml.namespace.QName("", "LineId"),
					xmlWriter);

			if (localParentId == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"ParentId cannot be null!!");
			}

			localParentId.serialize(new javax.xml.namespace.QName("",
					"ParentId"), xmlWriter);

			if (localLineDepth == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"LineDepth cannot be null!!");
			}

			localLineDepth.serialize(new javax.xml.namespace.QName("",
					"LineDepth"), xmlWriter);

			if (localQuantity == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Quantity cannot be null!!");
			}

			localQuantity.serialize(new javax.xml.namespace.QName("",
					"Quantity"), xmlWriter);

			if (localBaseUom == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"BaseUom cannot be null!!");
			}

			localBaseUom.serialize(
					new javax.xml.namespace.QName("", "BaseUom"), xmlWriter);

			if (localLongnum == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Longnum cannot be null!!");
			}

			localLongnum.serialize(
					new javax.xml.namespace.QName("", "Longnum"), xmlWriter);

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace
					.equals("urn:sap-com:document:sap:soap:functions:mc-style")) {
				return "ns2";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Bapi2017GmItemCreate parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Bapi2017GmItemCreate object = new Bapi2017GmItemCreate();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");

						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;

							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}

							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"Bapi2017GmItemCreate".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);

								return (Bapi2017GmItemCreate) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}
						}
					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Material")
									.equals(reader.getName())) {
						object.setMaterial(Char18.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Plant")
									.equals(reader.getName())) {
						object.setPlant(Char4.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "StgeLoc")
									.equals(reader.getName())) {
						object.setStgeLoc(Char4.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Batch")
									.equals(reader.getName())) {
						object.setBatch(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "MoveType")
									.equals(reader.getName())) {
						object.setMoveType(Char3.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "StckType")
									.equals(reader.getName())) {
						object.setStckType(Char1.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "SpecStock")
									.equals(reader.getName())) {
						object.setSpecStock(Char1.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Vendor")
									.equals(reader.getName())) {
						object.setVendor(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Customer")
									.equals(reader.getName())) {
						object.setCustomer(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "SalesOrd")
									.equals(reader.getName())) {
						object.setSalesOrd(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "SOrdItem")
									.equals(reader.getName())) {
						object.setSOrdItem(Numeric6.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "SchedLine")
									.equals(reader.getName())) {
						object.setSchedLine(Numeric4.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "ValType")
									.equals(reader.getName())) {
						object.setValType(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "EntryQnt")
									.equals(reader.getName())) {
						object.setEntryQnt(Quantum133.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "EntryUom")
									.equals(reader.getName())) {
						object.setEntryUom(Unit3.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "EntryUomIso")
									.equals(reader.getName())) {
						object.setEntryUomIso(Char3.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "PoPrQnt")
									.equals(reader.getName())) {
						object.setPoPrQnt(Quantum133.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "OrderprUn")
									.equals(reader.getName())) {
						object.setOrderprUn(Unit3.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "OrderprUnIso")
									.equals(reader.getName())) {
						object.setOrderprUnIso(Char3.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "PoNumber")
									.equals(reader.getName())) {
						object.setPoNumber(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "PoItem")
									.equals(reader.getName())) {
						object.setPoItem(Numeric5.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Shipping")
									.equals(reader.getName())) {
						object.setShipping(Char2.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "CompShip")
									.equals(reader.getName())) {
						object.setCompShip(Char2.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "NoMoreGr")
									.equals(reader.getName())) {
						object.setNoMoreGr(Char1.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "ItemText")
									.equals(reader.getName())) {
						object.setItemText(Char50.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "GrRcpt")
									.equals(reader.getName())) {
						object.setGrRcpt(Char12.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "UnloadPt")
									.equals(reader.getName())) {
						object.setUnloadPt(Char25.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Costcenter")
									.equals(reader.getName())) {
						object.setCostcenter(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Orderid")
									.equals(reader.getName())) {
						object.setOrderid(Char12.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "OrderItno")
									.equals(reader.getName())) {
						object.setOrderItno(Numeric4.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "CalcMotive")
									.equals(reader.getName())) {
						object.setCalcMotive(Char2.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "AssetNo")
									.equals(reader.getName())) {
						object.setAssetNo(Char12.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "SubNumber")
									.equals(reader.getName())) {
						object.setSubNumber(Char4.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "ReservNo")
									.equals(reader.getName())) {
						object.setReservNo(Numeric10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "ResItem")
									.equals(reader.getName())) {
						object.setResItem(Numeric4.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "ResType")
									.equals(reader.getName())) {
						object.setResType(Char1.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Withdrawn")
									.equals(reader.getName())) {
						object.setWithdrawn(Char1.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "MoveMat")
									.equals(reader.getName())) {
						object.setMoveMat(Char18.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "MovePlant")
									.equals(reader.getName())) {
						object.setMovePlant(Char4.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "MoveStloc")
									.equals(reader.getName())) {
						object.setMoveStloc(Char4.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "MoveBatch")
									.equals(reader.getName())) {
						object.setMoveBatch(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "MoveValType")
									.equals(reader.getName())) {
						object.setMoveValType(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "MvtInd")
									.equals(reader.getName())) {
						object.setMvtInd(Char1.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "MoveReas")
									.equals(reader.getName())) {
						object.setMoveReas(Numeric4.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "RlEstKey")
									.equals(reader.getName())) {
						object.setRlEstKey(Char8.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "RefDate")
									.equals(reader.getName())) {
						object.setRefDate(Date.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "CostObj")
									.equals(reader.getName())) {
						object.setCostObj(Char12.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "ProfitSegmNo")
									.equals(reader.getName())) {
						object.setProfitSegmNo(Numeric10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "ProfitCtr")
									.equals(reader.getName())) {
						object.setProfitCtr(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "WbsElem")
									.equals(reader.getName())) {
						object.setWbsElem(Char24.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Network")
									.equals(reader.getName())) {
						object.setNetwork(Char12.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Activity")
									.equals(reader.getName())) {
						object.setActivity(Char4.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "PartAcct")
									.equals(reader.getName())) {
						object.setPartAcct(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "AmountLc")
									.equals(reader.getName())) {
						object.setAmountLc(Decimal234.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "AmountSv")
									.equals(reader.getName())) {
						object.setAmountSv(Decimal234.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "RefDocYr")
									.equals(reader.getName())) {
						object.setRefDocYr(Numeric4.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "RefDoc")
									.equals(reader.getName())) {
						object.setRefDoc(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "RefDocIt")
									.equals(reader.getName())) {
						object.setRefDocIt(Numeric4.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Expirydate")
									.equals(reader.getName())) {
						object.setExpirydate(Date.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "ProdDate")
									.equals(reader.getName())) {
						object.setProdDate(Date.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Fund")
									.equals(reader.getName())) {
						object.setFund(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "FundsCtr")
									.equals(reader.getName())) {
						object.setFundsCtr(Char16.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "CmmtItem")
									.equals(reader.getName())) {
						object.setCmmtItem(Char14.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "ValSalesOrd")
									.equals(reader.getName())) {
						object.setValSalesOrd(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "ValSOrdItem")
									.equals(reader.getName())) {
						object.setValSOrdItem(Numeric6.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "ValWbsElem")
									.equals(reader.getName())) {
						object.setValWbsElem(Char24.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "GlAccount")
									.equals(reader.getName())) {
						object.setGlAccount(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("",
									"IndProposeQuanx").equals(reader.getName())) {
						object.setIndProposeQuanx(Char1.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Xstob")
									.equals(reader.getName())) {
						object.setXstob(Char1.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "EanUpc")
									.equals(reader.getName())) {
						object.setEanUpc(Char18.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("",
									"DelivNumbToSearch").equals(reader
									.getName())) {
						object.setDelivNumbToSearch(Char10.Factory
								.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("",
									"DelivItemToSearch").equals(reader
									.getName())) {
						object.setDelivItemToSearch(Numeric6.Factory
								.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("",
									"SerialnoAutoNumberassignment")
									.equals(reader.getName())) {
						object.setSerialnoAutoNumberassignment(Char1.Factory
								.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Vendrbatch")
									.equals(reader.getName())) {
						object.setVendrbatch(Char15.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "StgeType")
									.equals(reader.getName())) {
						object.setStgeType(Char3.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "StgeBin")
									.equals(reader.getName())) {
						object.setStgeBin(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "SuPlStck1")
									.equals(reader.getName())) {
						object.setSuPlStck1(Decimal30.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "StUnQtyy1")
									.equals(reader.getName())) {
						object.setStUnQtyy1(Quantum133.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "StUnQtyy1Iso")
									.equals(reader.getName())) {
						object.setStUnQtyy1Iso(Char3.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Unittype1")
									.equals(reader.getName())) {
						object.setUnittype1(Char3.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "SuPlStck2")
									.equals(reader.getName())) {
						object.setSuPlStck2(Decimal30.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "StUnQtyy2")
									.equals(reader.getName())) {
						object.setStUnQtyy2(Quantum133.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "StUnQtyy2Iso")
									.equals(reader.getName())) {
						object.setStUnQtyy2Iso(Char3.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Unittype2")
									.equals(reader.getName())) {
						object.setUnittype2(Char3.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "StgeTypePc")
									.equals(reader.getName())) {
						object.setStgeTypePc(Char3.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "StgeBinPc")
									.equals(reader.getName())) {
						object.setStgeBinPc(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "NoPstChgnt")
									.equals(reader.getName())) {
						object.setNoPstChgnt(Char1.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "GrNumber")
									.equals(reader.getName())) {
						object.setGrNumber(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "StgeTypeSt")
									.equals(reader.getName())) {
						object.setStgeTypeSt(Char3.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "StgeBinSt")
									.equals(reader.getName())) {
						object.setStgeBinSt(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("",
									"MatdocTrCancel").equals(reader.getName())) {
						object.setMatdocTrCancel(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("",
									"MatitemTrCancel").equals(reader.getName())) {
						object.setMatitemTrCancel(Numeric4.Factory
								.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("",
									"MatyearTrCancel").equals(reader.getName())) {
						object.setMatyearTrCancel(Numeric4.Factory
								.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("",
									"NoTransferReq").equals(reader.getName())) {
						object.setNoTransferReq(Char1.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "CoBusproc")
									.equals(reader.getName())) {
						object.setCoBusproc(Char12.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Acttype")
									.equals(reader.getName())) {
						object.setActtype(Char6.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "SupplVend")
									.equals(reader.getName())) {
						object.setSupplVend(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("",
									"MaterialExternal")
									.equals(reader.getName())) {
						object.setMaterialExternal(Char40.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "MaterialGuid")
									.equals(reader.getName())) {
						object.setMaterialGuid(Char32.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("",
									"MaterialVersion").equals(reader.getName())) {
						object.setMaterialVersion(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("",
									"MoveMatExternal").equals(reader.getName())) {
						object.setMoveMatExternal(Char40.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "MoveMatGuid")
									.equals(reader.getName())) {
						object.setMoveMatGuid(Char32.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("",
									"MoveMatVersion").equals(reader.getName())) {
						object.setMoveMatVersion(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "FuncArea")
									.equals(reader.getName())) {
						object.setFuncArea(Char4.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "TrPartBa")
									.equals(reader.getName())) {
						object.setTrPartBa(Char4.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "ParCompco")
									.equals(reader.getName())) {
						object.setParCompco(Char4.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "DelivNumb")
									.equals(reader.getName())) {
						object.setDelivNumb(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "DelivItem")
									.equals(reader.getName())) {
						object.setDelivItem(Numeric6.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "NbSlips")
									.equals(reader.getName())) {
						object.setNbSlips(Numeric3.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "NbSlipsx")
									.equals(reader.getName())) {
						object.setNbSlipsx(Char1.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "GrRcptx")
									.equals(reader.getName())) {
						object.setGrRcptx(Char1.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "UnloadPtx")
									.equals(reader.getName())) {
						object.setUnloadPtx(Char1.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "SpecMvmt")
									.equals(reader.getName())) {
						object.setSpecMvmt(Char1.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "GrantNbr")
									.equals(reader.getName())) {
						object.setGrantNbr(Char20.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "CmmtItemLong")
									.equals(reader.getName())) {
						object.setCmmtItemLong(Char24.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "FuncAreaLong")
									.equals(reader.getName())) {
						object.setFuncAreaLong(Char16.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "LineId")
									.equals(reader.getName())) {
						object.setLineId(Numeric6.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "ParentId")
									.equals(reader.getName())) {
						object.setParentId(Numeric6.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "LineDepth")
									.equals(reader.getName())) {
						object.setLineDepth(Numeric2.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Quantity")
									.equals(reader.getName())) {
						object.setQuantity(Quantum133.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "BaseUom")
									.equals(reader.getName())) {
						object.setBaseUom(Unit3.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Longnum")
									.equals(reader.getName())) {
						object.setLongnum(Char40.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class ZwmsPzmx implements
			org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had name =
		 * ZwmsPzmx Namespace URI =
		 * urn:sap-com:document:sap:soap:functions:mc-style Namespace Prefix =
		 * ns2
		 */

		/**
		 * field for Mblnr
		 */
		protected Char10 localMblnr;

		/**
		 * field for Zeile
		 */
		protected Numeric4 localZeile;

		/**
		 * field for Matnr
		 */
		protected Char18 localMatnr;

		/**
		 * field for Lgort
		 */
		protected Char4 localLgort;

		/**
		 * field for Charg
		 */
		protected Char10 localCharg;

		/**
		 * field for Menge
		 */
		protected Quantum133 localMenge;

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getMblnr() {
			return localMblnr;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Mblnr
		 */
		public void setMblnr(Char10 param) {
			this.localMblnr = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Numeric4
		 */
		public Numeric4 getZeile() {
			return localZeile;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Zeile
		 */
		public void setZeile(Numeric4 param) {
			this.localZeile = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char18
		 */
		public Char18 getMatnr() {
			return localMatnr;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Matnr
		 */
		public void setMatnr(Char18 param) {
			this.localMatnr = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char4
		 */
		public Char4 getLgort() {
			return localLgort;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Lgort
		 */
		public void setLgort(Char4 param) {
			this.localLgort = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getCharg() {
			return localCharg;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Charg
		 */
		public void setCharg(Char10 param) {
			this.localCharg = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Quantum133
		 */
		public Quantum133 getMenge() {
			return localMenge;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Menge
		 */
		public void setMenge(Quantum133 param) {
			this.localMenge = param;
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, parentQName));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:soap:functions:mc-style");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":ZwmsPzmx", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "ZwmsPzmx", xmlWriter);
				}
			}

			if (localMblnr == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Mblnr cannot be null!!");
			}

			localMblnr.serialize(new javax.xml.namespace.QName("", "Mblnr"),
					xmlWriter);

			if (localZeile == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Zeile cannot be null!!");
			}

			localZeile.serialize(new javax.xml.namespace.QName("", "Zeile"),
					xmlWriter);

			if (localMatnr == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Matnr cannot be null!!");
			}

			localMatnr.serialize(new javax.xml.namespace.QName("", "Matnr"),
					xmlWriter);

			if (localLgort == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Lgort cannot be null!!");
			}

			localLgort.serialize(new javax.xml.namespace.QName("", "Lgort"),
					xmlWriter);

			if (localCharg == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Charg cannot be null!!");
			}

			localCharg.serialize(new javax.xml.namespace.QName("", "Charg"),
					xmlWriter);

			if (localMenge == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Menge cannot be null!!");
			}

			localMenge.serialize(new javax.xml.namespace.QName("", "Menge"),
					xmlWriter);

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace
					.equals("urn:sap-com:document:sap:soap:functions:mc-style")) {
				return "ns2";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static ZwmsPzmx parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				ZwmsPzmx object = new ZwmsPzmx();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");

						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;

							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}

							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"ZwmsPzmx".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);

								return (ZwmsPzmx) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}
						}
					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Mblnr")
									.equals(reader.getName())) {
						object.setMblnr(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Zeile")
									.equals(reader.getName())) {
						object.setZeile(Numeric4.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Matnr")
									.equals(reader.getName())) {
						object.setMatnr(Char18.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Lgort")
									.equals(reader.getName())) {
						object.setLgort(Char4.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Charg")
									.equals(reader.getName())) {
						object.setCharg(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Menge")
									.equals(reader.getName())) {
						object.setMenge(Quantum133.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Quantum133 implements
			org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "quantum13.3", "ns1");

		/**
		 * field for Quantum133
		 */
		protected java.math.BigDecimal localQuantum133;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.math.BigDecimal
		 */
		public java.math.BigDecimal getQuantum133() {
			return localQuantum133;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Quantum133
		 */
		public void setQuantum133(java.math.BigDecimal param) {
			java.lang.String totalDigitsDecimal = org.apache.axis2.databinding.utils.ConverterUtil
					.convertToStandardDecimalNotation("13").toPlainString();

			if (org.apache.axis2.databinding.utils.ConverterUtil.compare(param,
					totalDigitsDecimal) < 0) {
				this.localQuantum133 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localQuantum133.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":quantum13.3", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "quantum13.3", xmlWriter);
				}
			}

			if (localQuantum133 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"quantum13.3 cannot be null !!");
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localQuantum133));
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Quantum133 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Quantum133 returnValue = new Quantum133();

				returnValue
						.setQuantum133(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToDecimal(value));

				return returnValue;
			}

			public static Quantum133 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Quantum133.Factory.fromString(content, namespaceUri);
				} else {
					return Quantum133.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Quantum133 parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Quantum133 object = new Quantum133();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "quantum13.3"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setQuantum133(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToDecimal(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class ZtfunWmsBasisFunctionResponse implements
			org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:soap:functions:mc-style",
				"ZtfunWmsBasisFunctionResponse", "ns2");

		/**
		 * field for Matdocumentyear
		 */
		protected Numeric4 localMatdocumentyear;

		/**
		 * field for Materialdocument
		 */
		protected Char10 localMaterialdocument;

		/**
		 * field for Message
		 */
		protected Char255 localMessage;

		/**
		 * field for Return
		 */
		protected Char1 localReturn;

		/**
		 * field for TGvitem
		 */
		protected TableOfBapi2017GmItemCreate localTGvitem;

		/**
		 * field for TPch
		 */
		protected TableOfZwmsPch localTPch;

		/**
		 * field for TPzmx
		 */
		protected TableOfZwmsPzmx localTPzmx;

		/**
		 * field for TSerial
		 */
		protected TableOfBapi2017GmSerialnumber localTSerial;

		/**
		 * Auto generated getter method
		 * 
		 * @return Numeric4
		 */
		public Numeric4 getMatdocumentyear() {
			return localMatdocumentyear;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Matdocumentyear
		 */
		public void setMatdocumentyear(Numeric4 param) {
			this.localMatdocumentyear = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getMaterialdocument() {
			return localMaterialdocument;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Materialdocument
		 */
		public void setMaterialdocument(Char10 param) {
			this.localMaterialdocument = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char255
		 */
		public Char255 getMessage() {
			return localMessage;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Message
		 */
		public void setMessage(Char255 param) {
			this.localMessage = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char1
		 */
		public Char1 getReturn() {
			return localReturn;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Return
		 */
		public void setReturn(Char1 param) {
			this.localReturn = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return TableOfBapi2017GmItemCreate
		 */
		public TableOfBapi2017GmItemCreate getTGvitem() {
			return localTGvitem;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TGvitem
		 */
		public void setTGvitem(TableOfBapi2017GmItemCreate param) {
			this.localTGvitem = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return TableOfZwmsPch
		 */
		public TableOfZwmsPch getTPch() {
			return localTPch;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TPch
		 */
		public void setTPch(TableOfZwmsPch param) {
			this.localTPch = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return TableOfZwmsPzmx
		 */
		public TableOfZwmsPzmx getTPzmx() {
			return localTPzmx;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TPzmx
		 */
		public void setTPzmx(TableOfZwmsPzmx param) {
			this.localTPzmx = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return TableOfBapi2017GmSerialnumber
		 */
		public TableOfBapi2017GmSerialnumber getTSerial() {
			return localTSerial;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            TSerial
		 */
		public void setTSerial(TableOfBapi2017GmSerialnumber param) {
			this.localTSerial = param;
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:soap:functions:mc-style");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix
									+ ":ZtfunWmsBasisFunctionResponse",
							xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "ZtfunWmsBasisFunctionResponse", xmlWriter);
				}
			}

			if (localMatdocumentyear == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Matdocumentyear cannot be null!!");
			}

			localMatdocumentyear.serialize(new javax.xml.namespace.QName("",
					"Matdocumentyear"), xmlWriter);

			if (localMaterialdocument == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Materialdocument cannot be null!!");
			}

			localMaterialdocument.serialize(new javax.xml.namespace.QName("",
					"Materialdocument"), xmlWriter);

			if (localMessage == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Message cannot be null!!");
			}

			localMessage.serialize(
					new javax.xml.namespace.QName("", "Message"), xmlWriter);

			if (localReturn == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Return cannot be null!!");
			}

			localReturn.serialize(new javax.xml.namespace.QName("", "Return"),
					xmlWriter);

			if (localTGvitem == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"TGvitem cannot be null!!");
			}

			localTGvitem.serialize(
					new javax.xml.namespace.QName("", "TGvitem"), xmlWriter);

			if (localTPch == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"TPch cannot be null!!");
			}

			localTPch.serialize(new javax.xml.namespace.QName("", "TPch"),
					xmlWriter);

			if (localTPzmx == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"TPzmx cannot be null!!");
			}

			localTPzmx.serialize(new javax.xml.namespace.QName("", "TPzmx"),
					xmlWriter);

			if (localTSerial == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"TSerial cannot be null!!");
			}

			localTSerial.serialize(
					new javax.xml.namespace.QName("", "TSerial"), xmlWriter);

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace
					.equals("urn:sap-com:document:sap:soap:functions:mc-style")) {
				return "ns2";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static ZtfunWmsBasisFunctionResponse parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				ZtfunWmsBasisFunctionResponse object = new ZtfunWmsBasisFunctionResponse();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");

						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;

							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}

							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"ZtfunWmsBasisFunctionResponse".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);

								return (ZtfunWmsBasisFunctionResponse) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}
						}
					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("",
									"Matdocumentyear").equals(reader.getName())) {
						object.setMatdocumentyear(Numeric4.Factory
								.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("",
									"Materialdocument")
									.equals(reader.getName())) {
						object.setMaterialdocument(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Message")
									.equals(reader.getName())) {
						object.setMessage(Char255.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Return")
									.equals(reader.getName())) {
						object.setReturn(Char1.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "TGvitem")
									.equals(reader.getName())) {
						object.setTGvitem(TableOfBapi2017GmItemCreate.Factory
								.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "TPch")
									.equals(reader.getName())) {
						object.setTPch(TableOfZwmsPch.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "TPzmx")
									.equals(reader.getName())) {
						object.setTPzmx(TableOfZwmsPzmx.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "TSerial")
									.equals(reader.getName())) {
						object.setTSerial(TableOfBapi2017GmSerialnumber.Factory
								.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class TableOfZwmsPch implements
			org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had name =
		 * TableOfZwmsPch Namespace URI =
		 * urn:sap-com:document:sap:soap:functions:mc-style Namespace Prefix =
		 * ns2
		 */

		/**
		 * field for Item This was an Array!
		 */
		protected ZwmsPch[] localItem;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localItemTracker = false;

		public boolean isItemSpecified() {
			return localItemTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return ZwmsPch[]
		 */
		public ZwmsPch[] getItem() {
			return localItem;
		}

		/**
		 * validate the array for Item
		 */
		protected void validateItem(ZwmsPch[] param) {
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Item
		 */
		public void setItem(ZwmsPch[] param) {
			validateItem(param);

			localItemTracker = param != null;

			this.localItem = param;
		}

		/**
		 * Auto generated add method for the array for convenience
		 * 
		 * @param param
		 *            ZwmsPch
		 */
		public void addItem(ZwmsPch param) {
			if (localItem == null) {
				localItem = new ZwmsPch[] {};
			}

			// update the setting tracker
			localItemTracker = true;

			java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil
					.toList(localItem);
			list.add(param);
			this.localItem = (ZwmsPch[]) list.toArray(new ZwmsPch[list.size()]);
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, parentQName));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:soap:functions:mc-style");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":TableOfZwmsPch",
							xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "TableOfZwmsPch", xmlWriter);
				}
			}

			if (localItemTracker) {
				if (localItem != null) {
					for (int i = 0; i < localItem.length; i++) {
						if (localItem[i] != null) {
							localItem[i].serialize(
									new javax.xml.namespace.QName("", "item"),
									xmlWriter);
						} else {
							// we don't have to do any thing since minOccures is
							// zero
						}
					}
				} else {
					throw new org.apache.axis2.databinding.ADBException(
							"item cannot be null!!");
				}
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace
					.equals("urn:sap-com:document:sap:soap:functions:mc-style")) {
				return "ns2";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static TableOfZwmsPch parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				TableOfZwmsPch object = new TableOfZwmsPch();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");

						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;

							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}

							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"TableOfZwmsPch".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);

								return (TableOfZwmsPch) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}
						}
					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					java.util.ArrayList list1 = new java.util.ArrayList();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "item")
									.equals(reader.getName())) {
						// Process the array and step past its final element's
						// end.
						list1.add(ZwmsPch.Factory.parse(reader));

						// loop until we find a start element that is not part
						// of this array
						boolean loopDone1 = false;

						while (!loopDone1) {
							// We should be at the end element, but make sure
							while (!reader.isEndElement())
								reader.next();

							// Step out of this element
							reader.next();

							// Step to next element event.
							while (!reader.isStartElement()
									&& !reader.isEndElement())
								reader.next();

							if (reader.isEndElement()) {
								// two continuous end elements means we are
								// exiting the xml structure
								loopDone1 = true;
							} else {
								if (new javax.xml.namespace.QName("", "item")
										.equals(reader.getName())) {
									list1.add(ZwmsPch.Factory.parse(reader));
								} else {
									loopDone1 = true;
								}
							}
						}

						// call the converter utility to convert and set the
						// array
						object.setItem((ZwmsPch[]) org.apache.axis2.databinding.utils.ConverterUtil
								.convertToArray(ZwmsPch.class, list1));
					} // End of if for expected property start element

					else {
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class ExtensionMapper {
		public static java.lang.Object getTypeObject(
				java.lang.String namespaceURI, java.lang.String typeName,
				javax.xml.stream.XMLStreamReader reader)
				throws java.lang.Exception {
			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "unit3".equals(typeName)) {
				return Unit3.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:soap:functions:mc-style"
					.equals(namespaceURI) && "ZwmsPzmx".equals(typeName)) {
				return ZwmsPzmx.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:soap:functions:mc-style"
					.equals(namespaceURI)
					&& "Bapi2017GmItemCreate".equals(typeName)) {
				return Bapi2017GmItemCreate.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "char32".equals(typeName)) {
				return Char32.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "char50".equals(typeName)) {
				return Char50.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "char255".equals(typeName)) {
				return Char255.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "numeric2".equals(typeName)) {
				return Numeric2.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "char4".equals(typeName)) {
				return Char4.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "decimal3.0".equals(typeName)) {
				return Decimal30.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "char2".equals(typeName)) {
				return Char2.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "numeric4".equals(typeName)) {
				return Numeric4.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "numeric3".equals(typeName)) {
				return Numeric3.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "char3".equals(typeName)) {
				return Char3.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:soap:functions:mc-style"
					.equals(namespaceURI) && "TableOfZwmsPch".equals(typeName)) {
				return TableOfZwmsPch.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "char1".equals(typeName)) {
				return Char1.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "numeric6".equals(typeName)) {
				return Numeric6.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "numeric5".equals(typeName)) {
				return Numeric5.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "date".equals(typeName)) {
				return Date.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "quantum13.3".equals(typeName)) {
				return Quantum133.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "char24".equals(typeName)) {
				return Char24.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "char25".equals(typeName)) {
				return Char25.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "char40".equals(typeName)) {
				return Char40.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "char20".equals(typeName)) {
				return Char20.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:soap:functions:mc-style"
					.equals(namespaceURI)
					&& "TableOfBapi2017GmSerialnumber".equals(typeName)) {
				return TableOfBapi2017GmSerialnumber.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "char6".equals(typeName)) {
				return Char6.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "numeric10".equals(typeName)) {
				return Numeric10.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "char8".equals(typeName)) {
				return Char8.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "char18".equals(typeName)) {
				return Char18.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:soap:functions:mc-style"
					.equals(namespaceURI)
					&& "TableOfBapi2017GmItemCreate".equals(typeName)) {
				return TableOfBapi2017GmItemCreate.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "char16".equals(typeName)) {
				return Char16.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "char15".equals(typeName)) {
				return Char15.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "char14".equals(typeName)) {
				return Char14.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:soap:functions:mc-style"
					.equals(namespaceURI)
					&& "Bapi2017GmSerialnumber".equals(typeName)) {
				return Bapi2017GmSerialnumber.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "char12".equals(typeName)) {
				return Char12.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:soap:functions:mc-style"
					.equals(namespaceURI) && "ZwmsPch".equals(typeName)) {
				return ZwmsPch.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "char10".equals(typeName)) {
				return Char10.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:rfc:functions".equals(namespaceURI)
					&& "decimal23.4".equals(typeName)) {
				return Decimal234.Factory.parse(reader);
			}

			if ("urn:sap-com:document:sap:soap:functions:mc-style"
					.equals(namespaceURI) && "TableOfZwmsPzmx".equals(typeName)) {
				return TableOfZwmsPzmx.Factory.parse(reader);
			}

			throw new org.apache.axis2.databinding.ADBException(
					"Unsupported type " + namespaceURI + " " + typeName);
		}
	}

	public static class Char32 implements org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "char32", "ns1");

		/**
		 * field for Char32
		 */
		protected java.lang.String localChar32;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getChar32() {
			return localChar32;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Char32
		 */
		public void setChar32(java.lang.String param) {
			if ((java.lang.String.valueOf(param).length() <= 32)) {
				this.localChar32 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localChar32.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":char32", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "char32", xmlWriter);
				}
			}

			if (localChar32 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"char32 cannot be null !!");
			} else {
				xmlWriter.writeCharacters(localChar32);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Char32 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Char32 returnValue = new Char32();

				returnValue
						.setChar32(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(value));

				return returnValue;
			}

			public static Char32 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Char32.Factory.fromString(content, namespaceUri);
				} else {
					return Char32.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Char32 parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Char32 object = new Char32();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "char32"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setChar32(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Char12 implements org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "char12", "ns1");

		/**
		 * field for Char12
		 */
		protected java.lang.String localChar12;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getChar12() {
			return localChar12;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Char12
		 */
		public void setChar12(java.lang.String param) {
			if ((java.lang.String.valueOf(param).length() <= 12)) {
				this.localChar12 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localChar12.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":char12", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "char12", xmlWriter);
				}
			}

			if (localChar12 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"char12 cannot be null !!");
			} else {
				xmlWriter.writeCharacters(localChar12);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Char12 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Char12 returnValue = new Char12();

				returnValue
						.setChar12(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(value));

				return returnValue;
			}

			public static Char12 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Char12.Factory.fromString(content, namespaceUri);
				} else {
					return Char12.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Char12 parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Char12 object = new Char12();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "char12"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setChar12(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Char10 implements org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "char10", "ns1");

		/**
		 * field for Char10
		 */
		protected java.lang.String localChar10;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getChar10() {
			return localChar10;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Char10
		 */
		public void setChar10(java.lang.String param) {
			if ((java.lang.String.valueOf(param).length() <= 10)) {
				this.localChar10 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localChar10.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":char10", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "char10", xmlWriter);
				}
			}

			if (localChar10 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"char10 cannot be null !!");
			} else {
				xmlWriter.writeCharacters(localChar10);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Char10 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Char10 returnValue = new Char10();

				returnValue
						.setChar10(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(value));

				return returnValue;
			}

			public static Char10 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Char10.Factory.fromString(content, namespaceUri);
				} else {
					return Char10.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Char10 parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Char10 object = new Char10();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "char10"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setChar10(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Char16 implements org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "char16", "ns1");

		/**
		 * field for Char16
		 */
		protected java.lang.String localChar16;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getChar16() {
			return localChar16;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Char16
		 */
		public void setChar16(java.lang.String param) {
			if ((java.lang.String.valueOf(param).length() <= 16)) {
				this.localChar16 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localChar16.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":char16", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "char16", xmlWriter);
				}
			}

			if (localChar16 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"char16 cannot be null !!");
			} else {
				xmlWriter.writeCharacters(localChar16);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Char16 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Char16 returnValue = new Char16();

				returnValue
						.setChar16(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(value));

				return returnValue;
			}

			public static Char16 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Char16.Factory.fromString(content, namespaceUri);
				} else {
					return Char16.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Char16 parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Char16 object = new Char16();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "char16"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setChar16(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Char15 implements org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "char15", "ns1");

		/**
		 * field for Char15
		 */
		protected java.lang.String localChar15;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getChar15() {
			return localChar15;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Char15
		 */
		public void setChar15(java.lang.String param) {
			if ((java.lang.String.valueOf(param).length() <= 15)) {
				this.localChar15 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localChar15.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":char15", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "char15", xmlWriter);
				}
			}

			if (localChar15 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"char15 cannot be null !!");
			} else {
				xmlWriter.writeCharacters(localChar15);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Char15 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Char15 returnValue = new Char15();

				returnValue
						.setChar15(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(value));

				return returnValue;
			}

			public static Char15 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Char15.Factory.fromString(content, namespaceUri);
				} else {
					return Char15.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Char15 parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Char15 object = new Char15();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "char15"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setChar15(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Char14 implements org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "char14", "ns1");

		/**
		 * field for Char14
		 */
		protected java.lang.String localChar14;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getChar14() {
			return localChar14;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Char14
		 */
		public void setChar14(java.lang.String param) {
			if ((java.lang.String.valueOf(param).length() <= 14)) {
				this.localChar14 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localChar14.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":char14", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "char14", xmlWriter);
				}
			}

			if (localChar14 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"char14 cannot be null !!");
			} else {
				xmlWriter.writeCharacters(localChar14);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Char14 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Char14 returnValue = new Char14();

				returnValue
						.setChar14(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(value));

				return returnValue;
			}

			public static Char14 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Char14.Factory.fromString(content, namespaceUri);
				} else {
					return Char14.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Char14 parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Char14 object = new Char14();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "char14"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setChar14(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Char50 implements org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "char50", "ns1");

		/**
		 * field for Char50
		 */
		protected java.lang.String localChar50;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getChar50() {
			return localChar50;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Char50
		 */
		public void setChar50(java.lang.String param) {
			if ((java.lang.String.valueOf(param).length() <= 50)) {
				this.localChar50 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localChar50.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":char50", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "char50", xmlWriter);
				}
			}

			if (localChar50 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"char50 cannot be null !!");
			} else {
				xmlWriter.writeCharacters(localChar50);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Char50 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Char50 returnValue = new Char50();

				returnValue
						.setChar50(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(value));

				return returnValue;
			}

			public static Char50 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Char50.Factory.fromString(content, namespaceUri);
				} else {
					return Char50.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Char50 parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Char50 object = new Char50();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "char50"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setChar50(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class TableOfBapi2017GmSerialnumber implements
			org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had name =
		 * TableOfBapi2017GmSerialnumber Namespace URI =
		 * urn:sap-com:document:sap:soap:functions:mc-style Namespace Prefix =
		 * ns2
		 */

		/**
		 * field for Item This was an Array!
		 */
		protected Bapi2017GmSerialnumber[] localItem;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localItemTracker = false;

		public boolean isItemSpecified() {
			return localItemTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Bapi2017GmSerialnumber[]
		 */
		public Bapi2017GmSerialnumber[] getItem() {
			return localItem;
		}

		/**
		 * validate the array for Item
		 */
		protected void validateItem(Bapi2017GmSerialnumber[] param) {
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Item
		 */
		public void setItem(Bapi2017GmSerialnumber[] param) {
			validateItem(param);

			localItemTracker = param != null;

			this.localItem = param;
		}

		/**
		 * Auto generated add method for the array for convenience
		 * 
		 * @param param
		 *            Bapi2017GmSerialnumber
		 */
		public void addItem(Bapi2017GmSerialnumber param) {
			if (localItem == null) {
				localItem = new Bapi2017GmSerialnumber[] {};
			}

			// update the setting tracker
			localItemTracker = true;

			java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil
					.toList(localItem);
			list.add(param);
			this.localItem = (Bapi2017GmSerialnumber[]) list
					.toArray(new Bapi2017GmSerialnumber[list.size()]);
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, parentQName));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:soap:functions:mc-style");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix
									+ ":TableOfBapi2017GmSerialnumber",
							xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "TableOfBapi2017GmSerialnumber", xmlWriter);
				}
			}

			if (localItemTracker) {
				if (localItem != null) {
					for (int i = 0; i < localItem.length; i++) {
						if (localItem[i] != null) {
							localItem[i].serialize(
									new javax.xml.namespace.QName("", "item"),
									xmlWriter);
						} else {
							// we don't have to do any thing since minOccures is
							// zero
						}
					}
				} else {
					throw new org.apache.axis2.databinding.ADBException(
							"item cannot be null!!");
				}
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace
					.equals("urn:sap-com:document:sap:soap:functions:mc-style")) {
				return "ns2";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static TableOfBapi2017GmSerialnumber parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				TableOfBapi2017GmSerialnumber object = new TableOfBapi2017GmSerialnumber();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");

						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;

							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}

							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"TableOfBapi2017GmSerialnumber".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);

								return (TableOfBapi2017GmSerialnumber) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}
						}
					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					java.util.ArrayList list1 = new java.util.ArrayList();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "item")
									.equals(reader.getName())) {
						// Process the array and step past its final element's
						// end.
						list1.add(Bapi2017GmSerialnumber.Factory.parse(reader));

						// loop until we find a start element that is not part
						// of this array
						boolean loopDone1 = false;

						while (!loopDone1) {
							// We should be at the end element, but make sure
							while (!reader.isEndElement())
								reader.next();

							// Step out of this element
							reader.next();

							// Step to next element event.
							while (!reader.isStartElement()
									&& !reader.isEndElement())
								reader.next();

							if (reader.isEndElement()) {
								// two continuous end elements means we are
								// exiting the xml structure
								loopDone1 = true;
							} else {
								if (new javax.xml.namespace.QName("", "item")
										.equals(reader.getName())) {
									list1.add(Bapi2017GmSerialnumber.Factory
											.parse(reader));
								} else {
									loopDone1 = true;
								}
							}
						}

						// call the converter utility to convert and set the
						// array
						object.setItem((Bapi2017GmSerialnumber[]) org.apache.axis2.databinding.utils.ConverterUtil
								.convertToArray(Bapi2017GmSerialnumber.class,
										list1));
					} // End of if for expected property start element

					else {
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Char18 implements org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "char18", "ns1");

		/**
		 * field for Char18
		 */
		protected java.lang.String localChar18;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getChar18() {
			return localChar18;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Char18
		 */
		public void setChar18(java.lang.String param) {
			if ((java.lang.String.valueOf(param).length() <= 18)) {
				this.localChar18 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localChar18.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":char18", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "char18", xmlWriter);
				}
			}

			if (localChar18 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"char18 cannot be null !!");
			} else {
				xmlWriter.writeCharacters(localChar18);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Char18 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Char18 returnValue = new Char18();

				returnValue
						.setChar18(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(value));

				return returnValue;
			}

			public static Char18 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Char18.Factory.fromString(content, namespaceUri);
				} else {
					return Char18.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Char18 parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Char18 object = new Char18();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "char18"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setChar18(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Decimal30 implements
			org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "decimal3.0", "ns1");

		/**
		 * field for Decimal30
		 */
		protected java.math.BigDecimal localDecimal30;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.math.BigDecimal
		 */
		public java.math.BigDecimal getDecimal30() {
			return localDecimal30;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Decimal30
		 */
		public void setDecimal30(java.math.BigDecimal param) {
			java.lang.String totalDigitsDecimal = org.apache.axis2.databinding.utils.ConverterUtil
					.convertToStandardDecimalNotation("3").toPlainString();

			if (org.apache.axis2.databinding.utils.ConverterUtil.compare(param,
					totalDigitsDecimal) < 0) {
				this.localDecimal30 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localDecimal30.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":decimal3.0", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "decimal3.0", xmlWriter);
				}
			}

			if (localDecimal30 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"decimal3.0 cannot be null !!");
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localDecimal30));
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Decimal30 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Decimal30 returnValue = new Decimal30();

				returnValue
						.setDecimal30(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToDecimal(value));

				return returnValue;
			}

			public static Decimal30 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Decimal30.Factory.fromString(content, namespaceUri);
				} else {
					return Decimal30.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Decimal30 parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Decimal30 object = new Decimal30();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "decimal3.0"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setDecimal30(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToDecimal(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Numeric10 implements
			org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "numeric10", "ns1");

		/**
		 * field for Numeric10
		 */
		protected java.lang.String localNumeric10;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getNumeric10() {
			return localNumeric10;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Numeric10
		 */
		public void setNumeric10(java.lang.String param) {
			if ((-1 <= java.lang.String.valueOf(param).length())
					&& (java.lang.String.valueOf(param).length() <= 10)
					&& (org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(param).matches("\\d*"))) {
				this.localNumeric10 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localNumeric10.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":numeric10", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "numeric10", xmlWriter);
				}
			}

			if (localNumeric10 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"numeric10 cannot be null !!");
			} else {
				xmlWriter.writeCharacters(localNumeric10);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Numeric10 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Numeric10 returnValue = new Numeric10();

				returnValue
						.setNumeric10(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(value));

				return returnValue;
			}

			public static Numeric10 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Numeric10.Factory.fromString(content, namespaceUri);
				} else {
					return Numeric10.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Numeric10 parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Numeric10 object = new Numeric10();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "numeric10"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setNumeric10(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class TableOfBapi2017GmItemCreate implements
			org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had name =
		 * TableOfBapi2017GmItemCreate Namespace URI =
		 * urn:sap-com:document:sap:soap:functions:mc-style Namespace Prefix =
		 * ns2
		 */

		/**
		 * field for Item This was an Array!
		 */
		protected Bapi2017GmItemCreate[] localItem;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localItemTracker = false;

		public boolean isItemSpecified() {
			return localItemTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Bapi2017GmItemCreate[]
		 */
		public Bapi2017GmItemCreate[] getItem() {
			return localItem;
		}

		/**
		 * validate the array for Item
		 */
		protected void validateItem(Bapi2017GmItemCreate[] param) {
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Item
		 */
		public void setItem(Bapi2017GmItemCreate[] param) {
			validateItem(param);

			localItemTracker = param != null;

			this.localItem = param;
		}

		/**
		 * Auto generated add method for the array for convenience
		 * 
		 * @param param
		 *            Bapi2017GmItemCreate
		 */
		public void addItem(Bapi2017GmItemCreate param) {
			if (localItem == null) {
				localItem = new Bapi2017GmItemCreate[] {};
			}

			// update the setting tracker
			localItemTracker = true;

			java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil
					.toList(localItem);
			list.add(param);
			this.localItem = (Bapi2017GmItemCreate[]) list
					.toArray(new Bapi2017GmItemCreate[list.size()]);
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, parentQName));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:soap:functions:mc-style");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix
									+ ":TableOfBapi2017GmItemCreate", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "TableOfBapi2017GmItemCreate", xmlWriter);
				}
			}

			if (localItemTracker) {
				if (localItem != null) {
					for (int i = 0; i < localItem.length; i++) {
						if (localItem[i] != null) {
							localItem[i].serialize(
									new javax.xml.namespace.QName("", "item"),
									xmlWriter);
						} else {
							// we don't have to do any thing since minOccures is
							// zero
						}
					}
				} else {
					throw new org.apache.axis2.databinding.ADBException(
							"item cannot be null!!");
				}
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace
					.equals("urn:sap-com:document:sap:soap:functions:mc-style")) {
				return "ns2";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static TableOfBapi2017GmItemCreate parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				TableOfBapi2017GmItemCreate object = new TableOfBapi2017GmItemCreate();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");

						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;

							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}

							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"TableOfBapi2017GmItemCreate".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);

								return (TableOfBapi2017GmItemCreate) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}
						}
					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					java.util.ArrayList list1 = new java.util.ArrayList();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "item")
									.equals(reader.getName())) {
						// Process the array and step past its final element's
						// end.
						list1.add(Bapi2017GmItemCreate.Factory.parse(reader));

						// loop until we find a start element that is not part
						// of this array
						boolean loopDone1 = false;

						while (!loopDone1) {
							// We should be at the end element, but make sure
							while (!reader.isEndElement())
								reader.next();

							// Step out of this element
							reader.next();

							// Step to next element event.
							while (!reader.isStartElement()
									&& !reader.isEndElement())
								reader.next();

							if (reader.isEndElement()) {
								// two continuous end elements means we are
								// exiting the xml structure
								loopDone1 = true;
							} else {
								if (new javax.xml.namespace.QName("", "item")
										.equals(reader.getName())) {
									list1.add(Bapi2017GmItemCreate.Factory
											.parse(reader));
								} else {
									loopDone1 = true;
								}
							}
						}

						// call the converter utility to convert and set the
						// array
						object.setItem((Bapi2017GmItemCreate[]) org.apache.axis2.databinding.utils.ConverterUtil
								.convertToArray(Bapi2017GmItemCreate.class,
										list1));
					} // End of if for expected property start element

					else {
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Char6 implements org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "char6", "ns1");

		/**
		 * field for Char6
		 */
		protected java.lang.String localChar6;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getChar6() {
			return localChar6;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Char6
		 */
		public void setChar6(java.lang.String param) {
			if ((java.lang.String.valueOf(param).length() <= 6)) {
				this.localChar6 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localChar6.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":char6", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "char6", xmlWriter);
				}
			}

			if (localChar6 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"char6 cannot be null !!");
			} else {
				xmlWriter.writeCharacters(localChar6);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Char6 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Char6 returnValue = new Char6();

				returnValue
						.setChar6(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(value));

				return returnValue;
			}

			public static Char6 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Char6.Factory.fromString(content, namespaceUri);
				} else {
					return Char6.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Char6 parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Char6 object = new Char6();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "char6"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setChar6(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Numeric5 implements
			org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "numeric5", "ns1");

		/**
		 * field for Numeric5
		 */
		protected java.lang.String localNumeric5;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getNumeric5() {
			return localNumeric5;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Numeric5
		 */
		public void setNumeric5(java.lang.String param) {
			if ((-1 <= java.lang.String.valueOf(param).length())
					&& (java.lang.String.valueOf(param).length() <= 5)
					&& (org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(param).matches("\\d*"))) {
				this.localNumeric5 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localNumeric5.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":numeric5", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "numeric5", xmlWriter);
				}
			}

			if (localNumeric5 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"numeric5 cannot be null !!");
			} else {
				xmlWriter.writeCharacters(localNumeric5);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Numeric5 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Numeric5 returnValue = new Numeric5();

				returnValue
						.setNumeric5(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(value));

				return returnValue;
			}

			public static Numeric5 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Numeric5.Factory.fromString(content, namespaceUri);
				} else {
					return Numeric5.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Numeric5 parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Numeric5 object = new Numeric5();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "numeric5"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setNumeric5(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Char8 implements org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "char8", "ns1");

		/**
		 * field for Char8
		 */
		protected java.lang.String localChar8;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getChar8() {
			return localChar8;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Char8
		 */
		public void setChar8(java.lang.String param) {
			if ((java.lang.String.valueOf(param).length() <= 8)) {
				this.localChar8 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localChar8.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":char8", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "char8", xmlWriter);
				}
			}

			if (localChar8 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"char8 cannot be null !!");
			} else {
				xmlWriter.writeCharacters(localChar8);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Char8 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Char8 returnValue = new Char8();

				returnValue
						.setChar8(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(value));

				return returnValue;
			}

			public static Char8 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Char8.Factory.fromString(content, namespaceUri);
				} else {
					return Char8.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Char8 parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Char8 object = new Char8();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "char8"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setChar8(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Char255 implements org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "char255", "ns1");

		/**
		 * field for Char255
		 */
		protected java.lang.String localChar255;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getChar255() {
			return localChar255;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Char255
		 */
		public void setChar255(java.lang.String param) {
			if ((java.lang.String.valueOf(param).length() <= 255)) {
				this.localChar255 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localChar255.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":char255", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "char255", xmlWriter);
				}
			}

			if (localChar255 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"char255 cannot be null !!");
			} else {
				xmlWriter.writeCharacters(localChar255);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Char255 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Char255 returnValue = new Char255();

				returnValue
						.setChar255(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(value));

				return returnValue;
			}

			public static Char255 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Char255.Factory.fromString(content, namespaceUri);
				} else {
					return Char255.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Char255 parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Char255 object = new Char255();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "char255"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setChar255(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Numeric6 implements
			org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "numeric6", "ns1");

		/**
		 * field for Numeric6
		 */
		protected java.lang.String localNumeric6;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getNumeric6() {
			return localNumeric6;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Numeric6
		 */
		public void setNumeric6(java.lang.String param) {
			if ((-1 <= java.lang.String.valueOf(param).length())
					&& (java.lang.String.valueOf(param).length() <= 6)
					&& (org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(param).matches("\\d*"))) {
				this.localNumeric6 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localNumeric6.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":numeric6", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "numeric6", xmlWriter);
				}
			}

			if (localNumeric6 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"numeric6 cannot be null !!");
			} else {
				xmlWriter.writeCharacters(localNumeric6);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Numeric6 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Numeric6 returnValue = new Numeric6();

				returnValue
						.setNumeric6(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(value));

				return returnValue;
			}

			public static Numeric6 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Numeric6.Factory.fromString(content, namespaceUri);
				} else {
					return Numeric6.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Numeric6 parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Numeric6 object = new Numeric6();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "numeric6"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setNumeric6(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Date implements org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "date", "ns1");

		/**
		 * field for Date
		 */
		protected java.lang.String localDate;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getDate() {
			return localDate;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Date
		 */
		public void setDate(java.lang.String param) {
			if ((-1 <= java.lang.String.valueOf(param).length())
					&& (java.lang.String.valueOf(param).length() <= 10)
					&& (org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(param)
							.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d"))) {
				this.localDate = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localDate.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":date", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "date", xmlWriter);
				}
			}

			if (localDate == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"date cannot be null !!");
			} else {
				xmlWriter.writeCharacters(localDate);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Date fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Date returnValue = new Date();

				returnValue
						.setDate(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(value));

				return returnValue;
			}

			public static Date fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Date.Factory.fromString(content, namespaceUri);
				} else {
					return Date.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Date parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Date object = new Date();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "date"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setDate(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Numeric3 implements
			org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "numeric3", "ns1");

		/**
		 * field for Numeric3
		 */
		protected java.lang.String localNumeric3;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getNumeric3() {
			return localNumeric3;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Numeric3
		 */
		public void setNumeric3(java.lang.String param) {
			if ((-1 <= java.lang.String.valueOf(param).length())
					&& (java.lang.String.valueOf(param).length() <= 3)
					&& (org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(param).matches("\\d*"))) {
				this.localNumeric3 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localNumeric3.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":numeric3", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "numeric3", xmlWriter);
				}
			}

			if (localNumeric3 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"numeric3 cannot be null !!");
			} else {
				xmlWriter.writeCharacters(localNumeric3);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Numeric3 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Numeric3 returnValue = new Numeric3();

				returnValue
						.setNumeric3(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(value));

				return returnValue;
			}

			public static Numeric3 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Numeric3.Factory.fromString(content, namespaceUri);
				} else {
					return Numeric3.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Numeric3 parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Numeric3 object = new Numeric3();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "numeric3"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setNumeric3(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Bapi2017GmSerialnumber implements
			org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had name =
		 * Bapi2017GmSerialnumber Namespace URI =
		 * urn:sap-com:document:sap:soap:functions:mc-style Namespace Prefix =
		 * ns2
		 */

		/**
		 * field for MatdocItm
		 */
		protected Numeric4 localMatdocItm;

		/**
		 * field for Serialno
		 */
		protected Char18 localSerialno;

		/**
		 * Auto generated getter method
		 * 
		 * @return Numeric4
		 */
		public Numeric4 getMatdocItm() {
			return localMatdocItm;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            MatdocItm
		 */
		public void setMatdocItm(Numeric4 param) {
			this.localMatdocItm = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char18
		 */
		public Char18 getSerialno() {
			return localSerialno;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Serialno
		 */
		public void setSerialno(Char18 param) {
			this.localSerialno = param;
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, parentQName));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:soap:functions:mc-style");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type",
							namespacePrefix + ":Bapi2017GmSerialnumber",
							xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "Bapi2017GmSerialnumber", xmlWriter);
				}
			}

			if (localMatdocItm == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"MatdocItm cannot be null!!");
			}

			localMatdocItm.serialize(new javax.xml.namespace.QName("",
					"MatdocItm"), xmlWriter);

			if (localSerialno == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Serialno cannot be null!!");
			}

			localSerialno.serialize(new javax.xml.namespace.QName("",
					"Serialno"), xmlWriter);

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace
					.equals("urn:sap-com:document:sap:soap:functions:mc-style")) {
				return "ns2";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Bapi2017GmSerialnumber parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Bapi2017GmSerialnumber object = new Bapi2017GmSerialnumber();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");

						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;

							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}

							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"Bapi2017GmSerialnumber".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);

								return (Bapi2017GmSerialnumber) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}
						}
					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "MatdocItm")
									.equals(reader.getName())) {
						object.setMatdocItm(Numeric4.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Serialno")
									.equals(reader.getName())) {
						object.setSerialno(Char18.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Numeric4 implements
			org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "numeric4", "ns1");

		/**
		 * field for Numeric4
		 */
		protected java.lang.String localNumeric4;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getNumeric4() {
			return localNumeric4;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Numeric4
		 */
		public void setNumeric4(java.lang.String param) {
			if ((-1 <= java.lang.String.valueOf(param).length())
					&& (java.lang.String.valueOf(param).length() <= 4)
					&& (org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(param).matches("\\d*"))) {
				this.localNumeric4 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localNumeric4.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":numeric4", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "numeric4", xmlWriter);
				}
			}

			if (localNumeric4 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"numeric4 cannot be null !!");
			} else {
				xmlWriter.writeCharacters(localNumeric4);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Numeric4 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Numeric4 returnValue = new Numeric4();

				returnValue
						.setNumeric4(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(value));

				return returnValue;
			}

			public static Numeric4 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Numeric4.Factory.fromString(content, namespaceUri);
				} else {
					return Numeric4.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Numeric4 parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Numeric4 object = new Numeric4();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "numeric4"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setNumeric4(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class ZwmsPch implements org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had name =
		 * ZwmsPch Namespace URI =
		 * urn:sap-com:document:sap:soap:functions:mc-style Namespace Prefix =
		 * ns2
		 */

		/**
		 * field for PoNumber
		 */
		protected Char10 localPoNumber;

		/**
		 * field for PoItem
		 */
		protected Numeric5 localPoItem;

		/**
		 * field for Charg
		 */
		protected Char10 localCharg;

		/**
		 * field for DelivNumb
		 */
		protected Char10 localDelivNumb;

		/**
		 * field for DelivItem
		 */
		protected Numeric6 localDelivItem;

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getPoNumber() {
			return localPoNumber;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            PoNumber
		 */
		public void setPoNumber(Char10 param) {
			this.localPoNumber = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Numeric5
		 */
		public Numeric5 getPoItem() {
			return localPoItem;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            PoItem
		 */
		public void setPoItem(Numeric5 param) {
			this.localPoItem = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getCharg() {
			return localCharg;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Charg
		 */
		public void setCharg(Char10 param) {
			this.localCharg = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Char10
		 */
		public Char10 getDelivNumb() {
			return localDelivNumb;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            DelivNumb
		 */
		public void setDelivNumb(Char10 param) {
			this.localDelivNumb = param;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return Numeric6
		 */
		public Numeric6 getDelivItem() {
			return localDelivItem;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            DelivItem
		 */
		public void setDelivItem(Numeric6 param) {
			this.localDelivItem = param;
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, parentQName));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:soap:functions:mc-style");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":ZwmsPch", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "ZwmsPch", xmlWriter);
				}
			}

			if (localPoNumber == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"PoNumber cannot be null!!");
			}

			localPoNumber.serialize(new javax.xml.namespace.QName("",
					"PoNumber"), xmlWriter);

			if (localPoItem == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"PoItem cannot be null!!");
			}

			localPoItem.serialize(new javax.xml.namespace.QName("", "PoItem"),
					xmlWriter);

			if (localCharg == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"Charg cannot be null!!");
			}

			localCharg.serialize(new javax.xml.namespace.QName("", "Charg"),
					xmlWriter);

			if (localDelivNumb == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"DelivNumb cannot be null!!");
			}

			localDelivNumb.serialize(new javax.xml.namespace.QName("",
					"DelivNumb"), xmlWriter);

			if (localDelivItem == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"DelivItem cannot be null!!");
			}

			localDelivItem.serialize(new javax.xml.namespace.QName("",
					"DelivItem"), xmlWriter);

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace
					.equals("urn:sap-com:document:sap:soap:functions:mc-style")) {
				return "ns2";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static ZwmsPch parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				ZwmsPch object = new ZwmsPch();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");

						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;

							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}

							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"ZwmsPch".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);

								return (ZwmsPch) ExtensionMapper.getTypeObject(
										nsUri, type, reader);
							}
						}
					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "PoNumber")
									.equals(reader.getName())) {
						object.setPoNumber(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "PoItem")
									.equals(reader.getName())) {
						object.setPoItem(Numeric5.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "Charg")
									.equals(reader.getName())) {
						object.setCharg(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "DelivNumb")
									.equals(reader.getName())) {
						object.setDelivNumb(Char10.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "DelivItem")
									.equals(reader.getName())) {
						object.setDelivItem(Numeric6.Factory.parse(reader));

						reader.next();
					} // End of if for expected property start element

					else {
						// 1 - A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Numeric2 implements
			org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "numeric2", "ns1");

		/**
		 * field for Numeric2
		 */
		protected java.lang.String localNumeric2;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getNumeric2() {
			return localNumeric2;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Numeric2
		 */
		public void setNumeric2(java.lang.String param) {
			if ((-1 <= java.lang.String.valueOf(param).length())
					&& (java.lang.String.valueOf(param).length() <= 2)
					&& (org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(param).matches("\\d*"))) {
				this.localNumeric2 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localNumeric2.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":numeric2", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "numeric2", xmlWriter);
				}
			}

			if (localNumeric2 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"numeric2 cannot be null !!");
			} else {
				xmlWriter.writeCharacters(localNumeric2);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Numeric2 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Numeric2 returnValue = new Numeric2();

				returnValue
						.setNumeric2(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(value));

				return returnValue;
			}

			public static Numeric2 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Numeric2.Factory.fromString(content, namespaceUri);
				} else {
					return Numeric2.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Numeric2 parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Numeric2 object = new Numeric2();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "numeric2"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setNumeric2(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class Unit3 implements org.apache.axis2.databinding.ADBBean {
		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"urn:sap-com:document:sap:rfc:functions", "unit3", "ns1");

		/**
		 * field for Unit3
		 */
		protected java.lang.String localUnit3;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getUnit3() {
			return localUnit3;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Unit3
		 */
		public void setUnit3(java.lang.String param) {
			if ((java.lang.String.valueOf(param).length() <= 3)) {
				this.localUnit3 = param;
			} else {
				throw new java.lang.RuntimeException(
						"Input values do not follow defined XSD restrictions");
			}
		}

		public java.lang.String toString() {
			return localUnit3.toString();
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, MY_QNAME));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			// We can safely assume an element has only one type associated with
			// it
			java.lang.String namespace = parentQName.getNamespaceURI();
			java.lang.String _localName = parentQName.getLocalPart();

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:rfc:functions");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":unit3", xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "unit3", xmlWriter);
				}
			}

			if (localUnit3 == null) {
				throw new org.apache.axis2.databinding.ADBException(
						"unit3 cannot be null !!");
			} else {
				xmlWriter.writeCharacters(localUnit3);
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace.equals("urn:sap-com:document:sap:rfc:functions")) {
				return "ns1";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			public static Unit3 fromString(java.lang.String value,
					java.lang.String namespaceURI) {
				Unit3 returnValue = new Unit3();

				returnValue
						.setUnit3(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(value));

				return returnValue;
			}

			public static Unit3 fromString(
					javax.xml.stream.XMLStreamReader xmlStreamReader,
					java.lang.String content) {
				if (content.indexOf(":") > -1) {
					java.lang.String prefix = content.substring(0,
							content.indexOf(":"));
					java.lang.String namespaceUri = xmlStreamReader
							.getNamespaceContext().getNamespaceURI(prefix);

					return Unit3.Factory.fromString(content, namespaceUri);
				} else {
					return Unit3.Factory.fromString(content, "");
				}
			}

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Unit3 parse(javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				Unit3 object = new Unit3();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					while (!reader.isEndElement()) {
						if (reader.isStartElement() || reader.hasText()) {
							if (reader.isStartElement() || reader.hasText()) {
								nillableValue = reader
										.getAttributeValue(
												"http://www.w3.org/2001/XMLSchema-instance",
												"nil");

								if ("true".equals(nillableValue)
										|| "1".equals(nillableValue)) {
									throw new org.apache.axis2.databinding.ADBException(
											"The element: " + "unit3"
													+ "  cannot be null");
								}

								java.lang.String content = reader
										.getElementText();

								object.setUnit3(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(content));
							} // End of if for expected property start element

							else {
								// 3 - A start element we are not expecting
								// indicates an invalid parameter was passed
								throw new org.apache.axis2.databinding.ADBException(
										"Unexpected subelement "
												+ reader.getName());
							}
						} else {
							reader.next();
						}
					} // end of while loop
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}

	public static class TableOfZwmsPzmx implements
			org.apache.axis2.databinding.ADBBean {
		/*
		 * This type was generated from the piece of schema that had name =
		 * TableOfZwmsPzmx Namespace URI =
		 * urn:sap-com:document:sap:soap:functions:mc-style Namespace Prefix =
		 * ns2
		 */

		/**
		 * field for Item This was an Array!
		 */
		protected ZwmsPzmx[] localItem;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localItemTracker = false;

		public boolean isItemSpecified() {
			return localItemTracker;
		}

		/**
		 * Auto generated getter method
		 * 
		 * @return ZwmsPzmx[]
		 */
		public ZwmsPzmx[] getItem() {
			return localItem;
		}

		/**
		 * validate the array for Item
		 */
		protected void validateItem(ZwmsPzmx[] param) {
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Item
		 */
		public void setItem(ZwmsPzmx[] param) {
			validateItem(param);

			localItemTracker = param != null;

			this.localItem = param;
		}

		/**
		 * Auto generated add method for the array for convenience
		 * 
		 * @param param
		 *            ZwmsPzmx
		 */
		public void addItem(ZwmsPzmx param) {
			if (localItem == null) {
				localItem = new ZwmsPzmx[] {};
			}

			// update the setting tracker
			localItemTracker = true;

			java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil
					.toList(localItem);
			list.add(param);
			this.localItem = (ZwmsPzmx[]) list
					.toArray(new ZwmsPzmx[list.size()]);
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(
				final javax.xml.namespace.QName parentQName,
				final org.apache.axiom.om.OMFactory factory)
				throws org.apache.axis2.databinding.ADBException {
			return factory
					.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
							this, parentQName));
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			serialize(parentQName, xmlWriter, false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,
				javax.xml.stream.XMLStreamWriter xmlWriter,
				boolean serializeType)
				throws javax.xml.stream.XMLStreamException,
				org.apache.axis2.databinding.ADBException {
			java.lang.String prefix = null;
			java.lang.String namespace = null;

			prefix = parentQName.getPrefix();
			namespace = parentQName.getNamespaceURI();
			writeStartElement(prefix, namespace, parentQName.getLocalPart(),
					xmlWriter);

			if (serializeType) {
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,
						"urn:sap-com:document:sap:soap:functions:mc-style");

				if ((namespacePrefix != null)
						&& (namespacePrefix.trim().length() > 0)) {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", namespacePrefix + ":TableOfZwmsPzmx",
							xmlWriter);
				} else {
					writeAttribute("xsi",
							"http://www.w3.org/2001/XMLSchema-instance",
							"type", "TableOfZwmsPzmx", xmlWriter);
				}
			}

			if (localItemTracker) {
				if (localItem != null) {
					for (int i = 0; i < localItem.length; i++) {
						if (localItem[i] != null) {
							localItem[i].serialize(
									new javax.xml.namespace.QName("", "item"),
									xmlWriter);
						} else {
							// we don't have to do any thing since minOccures is
							// zero
						}
					}
				} else {
					throw new org.apache.axis2.databinding.ADBException(
							"item cannot be null!!");
				}
			}

			xmlWriter.writeEndElement();
		}

		private static java.lang.String generatePrefix(
				java.lang.String namespace) {
			if (namespace
					.equals("urn:sap-com:document:sap:soap:functions:mc-style")) {
				return "ns2";
			}

			return org.apache.axis2.databinding.utils.BeanUtil
					.getUniquePrefix();
		}

		/**
		 * Utility method to write an element start tag.
		 */
		private void writeStartElement(java.lang.String prefix,
				java.lang.String namespace, java.lang.String localPart,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
			} else {
				if (namespace.length() == 0) {
					prefix = "";
				} else if (prefix == null) {
					prefix = generatePrefix(namespace);
				}

				xmlWriter.writeStartElement(prefix, localPart, namespace);
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}
		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,
				java.lang.String namespace, java.lang.String attName,
				java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

			if (writerPrefix != null) {
				xmlWriter.writeAttribute(writerPrefix, namespace, attName,
						attValue);
			} else {
				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
				xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,
				java.lang.String attName, java.lang.String attValue,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attValue);
			} else {
				xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
						namespace, attName, attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,
				java.lang.String attName, javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String attributeNamespace = qname.getNamespaceURI();
			java.lang.String attributePrefix = xmlWriter
					.getPrefix(attributeNamespace);

			if (attributePrefix == null) {
				attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
			}

			java.lang.String attributeValue;

			if (attributePrefix.trim().length() > 0) {
				attributeValue = attributePrefix + ":" + qname.getLocalPart();
			} else {
				attributeValue = qname.getLocalPart();
			}

			if (namespace.equals("")) {
				xmlWriter.writeAttribute(attName, attributeValue);
			} else {
				registerPrefix(xmlWriter, namespace);
				xmlWriter.writeAttribute(attributePrefix, namespace, attName,
						attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */
		private void writeQName(javax.xml.namespace.QName qname,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String namespaceURI = qname.getNamespaceURI();

			if (namespaceURI != null) {
				java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

				if (prefix == null) {
					prefix = generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix, namespaceURI);
					xmlWriter.setPrefix(prefix, namespaceURI);
				}

				if (prefix.trim().length() > 0) {
					xmlWriter.writeCharacters(prefix
							+ ":"
							+ org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				} else {
					// i.e this is the default namespace
					xmlWriter
							.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qname));
				}
			} else {
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,
				javax.xml.stream.XMLStreamWriter xmlWriter)
				throws javax.xml.stream.XMLStreamException {
			if (qnames != null) {
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
				java.lang.String namespaceURI = null;
				java.lang.String prefix = null;

				for (int i = 0; i < qnames.length; i++) {
					if (i > 0) {
						stringToWrite.append(" ");
					}

					namespaceURI = qnames[i].getNamespaceURI();

					if (namespaceURI != null) {
						prefix = xmlWriter.getPrefix(namespaceURI);

						if ((prefix == null) || (prefix.length() == 0)) {
							prefix = generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix, namespaceURI);
							xmlWriter.setPrefix(prefix, namespaceURI);
						}

						if (prefix.trim().length() > 0) {
							stringToWrite
									.append(prefix)
									.append(":")
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						} else {
							stringToWrite
									.append(org.apache.axis2.databinding.utils.ConverterUtil
											.convertToString(qnames[i]));
						}
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				}

				xmlWriter.writeCharacters(stringToWrite.toString());
			}
		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(
				javax.xml.stream.XMLStreamWriter xmlWriter,
				java.lang.String namespace)
				throws javax.xml.stream.XMLStreamException {
			java.lang.String prefix = xmlWriter.getPrefix(namespace);

			if (prefix == null) {
				prefix = generatePrefix(namespace);

				javax.xml.namespace.NamespaceContext nsContext = xmlWriter
						.getNamespaceContext();

				while (true) {
					java.lang.String uri = nsContext.getNamespaceURI(prefix);

					if ((uri == null) || (uri.length() == 0)) {
						break;
					}

					prefix = org.apache.axis2.databinding.utils.BeanUtil
							.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix, namespace);
				xmlWriter.setPrefix(prefix, namespace);
			}

			return prefix;
		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory {
			private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
					.getLog(Factory.class);

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static TableOfZwmsPzmx parse(
					javax.xml.stream.XMLStreamReader reader)
					throws java.lang.Exception {
				TableOfZwmsPzmx object = new TableOfZwmsPzmx();

				int event;
				javax.xml.namespace.QName currentQName = null;
				java.lang.String nillableValue = null;
				java.lang.String prefix = "";
				java.lang.String namespaceuri = "";

				try {
					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					currentQName = reader.getName();

					if (reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type") != null) {
						java.lang.String fullTypeName = reader
								.getAttributeValue(
										"http://www.w3.org/2001/XMLSchema-instance",
										"type");

						if (fullTypeName != null) {
							java.lang.String nsPrefix = null;

							if (fullTypeName.indexOf(":") > -1) {
								nsPrefix = fullTypeName.substring(0,
										fullTypeName.indexOf(":"));
							}

							nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

							java.lang.String type = fullTypeName
									.substring(fullTypeName.indexOf(":") + 1);

							if (!"TableOfZwmsPzmx".equals(type)) {
								// find namespace for the prefix
								java.lang.String nsUri = reader
										.getNamespaceContext().getNamespaceURI(
												nsPrefix);

								return (TableOfZwmsPzmx) ExtensionMapper
										.getTypeObject(nsUri, type, reader);
							}
						}
					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();

					reader.next();

					java.util.ArrayList list1 = new java.util.ArrayList();

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()
							&& new javax.xml.namespace.QName("", "item")
									.equals(reader.getName())) {
						// Process the array and step past its final element's
						// end.
						list1.add(ZwmsPzmx.Factory.parse(reader));

						// loop until we find a start element that is not part
						// of this array
						boolean loopDone1 = false;

						while (!loopDone1) {
							// We should be at the end element, but make sure
							while (!reader.isEndElement())
								reader.next();

							// Step out of this element
							reader.next();

							// Step to next element event.
							while (!reader.isStartElement()
									&& !reader.isEndElement())
								reader.next();

							if (reader.isEndElement()) {
								// two continuous end elements means we are
								// exiting the xml structure
								loopDone1 = true;
							} else {
								if (new javax.xml.namespace.QName("", "item")
										.equals(reader.getName())) {
									list1.add(ZwmsPzmx.Factory.parse(reader));
								} else {
									loopDone1 = true;
								}
							}
						}

						// call the converter utility to convert and set the
						// array
						object.setItem((ZwmsPzmx[]) org.apache.axis2.databinding.utils.ConverterUtil
								.convertToArray(ZwmsPzmx.class, list1));
					} // End of if for expected property start element

					else {
					}

					while (!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if (reader.isStartElement()) {
						// 2 - A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException(
								"Unexpected subelement " + reader.getName());
					}
				} catch (javax.xml.stream.XMLStreamException e) {
					throw new java.lang.Exception(e);
				}

				return object;
			}
		} // end of factory class
	}
}
