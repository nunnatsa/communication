/*
 * Copyright 2015 Cisco Systems, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.cisco.oss.foundation.http.server;

import com.cisco.oss.foundation.flowcontext.FlowContextFactory;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Thia filter will extract the flow context from a known header and create it if ti doesn't exist.
 */
@Component
@Order(10)
public class AsyncHandlerSupportFilter extends AbstractInfraHttpFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(AsyncHandlerSupportFilter.class);

	public AsyncHandlerSupportFilter(){
		super();
	}

    public AsyncHandlerSupportFilter(String serviceName){
        super(serviceName);
    }

	
	@Override
	public void doFilterImpl(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		Boolean firstRequestProcessing = (Boolean)request.getAttribute("firstRequestProcessing");
		if(firstRequestProcessing != null && firstRequestProcessing){
			request.setAttribute("firstRequestProcessing",false);
		}else{
			request.setAttribute("firstRequestProcessing",true);
		}
        chain.doFilter(request, response);

	}

	
	@Override
	protected String getKillSwitchFlag() {
		return "http.asyncHandlerSupportFilter.isEnabled";
	}
	
	@Override
	protected boolean isEnabledByDefault() {
		return true;
	}
	
	

}
