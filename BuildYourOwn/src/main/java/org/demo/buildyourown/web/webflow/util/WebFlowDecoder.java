package org.demo.buildyourown.web.webflow.util;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.webflow.definition.StateDefinition;
import org.springframework.webflow.execution.repository.FlowExecutionRestorationFailureException;
import org.springframework.webflow.execution.repository.NoSuchFlowExecutionException;

/**
 * This class will return the url path for a standard or webflow request.
 * <p>
 * In the case of a non-webflow request this decode method will simple return
 * <code>request.getServletPath()</code> in the event that the request is a
 * webflow request it will return the webflow path in the form flow/state so
 * that it can be used internally as a url.
 * </p>
 * For instance if you are in the "main" flow under "flows" in the "start" state
 * it will return:<br/>
 * /flows/main/state<br/>
 * <br/>
 * If you were in the "main" flow but in the subflow "userInfo" in the state
 * "address" it will return:<br/>
 * /flows/userInfo/address
 * 
 */
public class WebFlowDecoder
{

    //This kinda sucks.  I can't seam to find a way to interrogate the request to get this.
    public static final String SPRING_SERVLET = "Spring Dispatch";

    public static final String NO_MATCHING_FLOW_URL = "NO_MATCHING_FLOW_URL";
    /**
     * Regex used to determine if this is a flow call or not.
     */
    private static final Pattern FLOW_PATTERN = Pattern.compile(".*?.*e\\d+s\\d+.*");

    /**
     * Returns the a servlet path url for this request and normalized webflow
     * requests.
     * 
     * @param request
     *            to use.
     * @return servlet path.
     */
    public String decode(HttpServletRequest request)
    {
        if (webFlow(request))
        {
            return webflowUrl(request);
        }
        return getRequestUrl(request);

    }

    private String getRequestUrl(HttpServletRequest request)
    {
        return request.getServletPath();
    }

    private boolean webFlow(HttpServletRequest request)
    {
        if (request.getQueryString() == null)
        {
            return false;
        }
        return FLOW_PATTERN.matcher(request.getQueryString()).matches();
    }

    private String webflowUrl(HttpServletRequest request)
    {
        WebFlowContextReader contextReader = new WebFlowContextReader(request, SPRING_SERVLET);
        try
        {
            contextReader.open();
            StateDefinition state = contextReader.getState();
            String flow = state.getOwner().getId();
            String flowState = state.getId();
            return "/" + flow + "/" + flowState;
        } catch (FlowExecutionRestorationFailureException | NoSuchFlowExecutionException e)
        {
            return NO_MATCHING_FLOW_URL;
        } finally
        {
            contextReader.close();
        }

    }
}
