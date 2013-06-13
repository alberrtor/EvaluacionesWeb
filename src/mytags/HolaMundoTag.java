package mytags;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class HolaMundoTag extends TagSupport {

    public int doEndTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            out.println("Hola mundo desde mi tag personalizado");
        }
        catch (Exception e) {
        }
        return super.doEndTag();
    }
}