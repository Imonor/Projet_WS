package action;

/*
 * Action
 * Execute the action called by HTTP request
 */
/**
 *
 * @author Guilhem HERMET
 */

import javax.servlet.http.HttpServletRequest;

public abstract class Action {
    
    //Execute the request
    public abstract boolean executer(HttpServletRequest request);
    
}
