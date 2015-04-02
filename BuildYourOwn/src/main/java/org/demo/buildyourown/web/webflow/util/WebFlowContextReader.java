package org.demo.buildyourown.web.webflow.util;

import java.io.Closeable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.webflow.context.ExternalContextHolder;
import org.springframework.webflow.context.servlet.ServletExternalContext;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.definition.StateDefinition;
import org.springframework.webflow.execution.FlowExecution;
import org.springframework.webflow.execution.FlowExecutionKey;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.executor.FlowExecutorImpl;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;

/**
 * <p>
 * Allows access to state and scopes of the webflow that will process the
 * current request.
 * </p>
 * The user must call open and close.<br>
 * This class should be called from within a try block: <code><br/>
 * WebflowContextreader reader = new WebflowContextReader(request, "Spring Dispatch");<br/>
 * try{<br/>
 *  reader.open();<br/>
 *  //...use the reader<br/>
 *  }catch(FlowException e){<br/>
 *      //Deal...<br/>
 *  }finally{<br/>
 *      reader.close();<br/>
 *  }<br/>
 *  </code>
 * 
 * 
 */
public class WebFlowContextReader implements Closeable
{
    /**
     * 
     * @author Copyright 2015 National Insurance Producer Registry
     * 
     */
    public static class NoAttributUpdateRequest extends HttpServletRequestWrapper
    {

        private final Map<String, Object> attributes = new LinkedHashMap<String, Object>();

        public NoAttributUpdateRequest(final HttpServletRequest request)
        {
            super(request);
        }

        @Override
        public Object getAttribute(final String name)
        {
            Object value = attributes.get(name);
            if (value != null)
            {
                return value;
            }
            return super.getAttribute(name);
        }

        @Override
        public void removeAttribute(final String name)
        {
            attributes.remove(name);
            super.removeAttribute(name);
        }

        @Override
        public void setAttribute(final String name, final Object o)
        {
            attributes.put(name, o);
        }

    }

    private static final String SPRING_SERVLET_APPLICATION_CONTEXT_PREFIX =
            "org.springframework.web.servlet.FrameworkServlet.CONTEXT.";
    private final HttpServletRequest request;
    private FlowExecutor flowExecutor;
    private FlowExecution flowExecution;
    private final String servletName;

    /**
     * 
     * @param request
     *            to read
     * @param servletName
     *            If you can figure out how to get the servlet name that was
     *            used in the web.xml file that has the webflow app context in
     *            it. Remove me.
     */
    public WebFlowContextReader(final HttpServletRequest request, final String servletName)
    {
        this.request = request;
        this.servletName = servletName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.io.Closeable#close()
     */
    @Override
    public void close()
    {
        ExternalContextHolder.setExternalContext(null);
    }

    public MutableAttributeMap getConversationScope()
    {
        return flowExecution.getConversationScope();
    }

    public FlowExecutor getExecutor()
    {
        return flowExecutor;
    }

    public MutableAttributeMap getFlashScope()
    {
        return flowExecution.getFlashScope();
    }

    public MutableAttributeMap getFlowScope()
    {
        return flowExecution.getActiveSession().getScope();
    }

    public StateDefinition getState()
    {
        return flowExecution.getActiveSession().getState();
    }

    public MutableAttributeMap getViewScopt()
    {
        return flowExecution.getActiveSession().getViewScope();
    }

    /**
     * Opens the flow for reading.
     */
    public void open()
    {
        ServletContext servletContext = request.getSession().getServletContext();
        String springServletApplicationContextName = SPRING_SERVLET_APPLICATION_CONTEXT_PREFIX + servletName;

        //Since response is never used we can pass null.
        //We must call this since webflow will try to pull it from thread local.
        ExternalContextHolder.setExternalContext(new ServletExternalContext(request.getSession().getServletContext(),
                new NoAttributUpdateRequest(request), null));
        ApplicationContext context =
                WebApplicationContextUtils.getWebApplicationContext(servletContext, springServletApplicationContextName);

        FlowHandlerMapping mapping = context.getBean(FlowHandlerMapping.class);
        FlowExecutorImpl executor = (FlowExecutorImpl) context.getBean("flowExecutor");
        flowExecutor = executor;
        String key = mapping.getFlowUrlHandler().getFlowExecutionKey(request);
        FlowExecutionKey fkey = executor.getExecutionRepository().parseFlowExecutionKey(key);
        flowExecution = executor.getExecutionRepository().getFlowExecution(fkey);
    }

}
