package com.britesnow.contactsdemo.web;

import com.britesnow.contactsdemo.AppException;
import com.britesnow.snow.web.RequestContext;
import com.britesnow.snow.web.exception.WebExceptionContext;
import com.britesnow.snow.web.exception.annotation.WebExceptionCatcher;
import com.britesnow.snow.web.renderer.JsonRenderer;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class WebExceptionCatchers {

	@Inject
	private JsonRenderer jsonRenderer;

	@WebExceptionCatcher
	public void processAppException(AppException e, WebExceptionContext wec, RequestContext rc){
		jsonRenderer.render(WebResponse.fail(e),rc.getWriter());
	}

}
