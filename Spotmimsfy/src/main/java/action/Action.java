/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

/**
 *
 * @author herme
 */
import javax.servlet.http.HttpServletRequest;

public abstract class Action {
    protected String path;
    
    public abstract boolean executer(HttpServletRequest request);
    
    public Action(String path){
        this.path = path;
    }
}
