package edu.uiowa.medline.otherId;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.medline.MEDLINETagLibTagSupport;

@SuppressWarnings("serial")
public class OtherIdOtherId extends MEDLINETagLibTagSupport {
	private static final Log log = LogFactory.getLog(OtherIdOtherId.class);


	public int doStartTag() throws JspException {
		try {
			OtherId theOtherId = (OtherId)findAncestorWithClass(this, OtherId.class);
			if (!theOtherId.commitNeeded) {
				pageContext.getOut().print(theOtherId.getOtherId());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing OtherId for otherId tag ", e);
			throw new JspTagException("Error: Can't find enclosing OtherId for otherId tag ");
		}
		return SKIP_BODY;
	}

	public String getOtherId() throws JspTagException {
		try {
			OtherId theOtherId = (OtherId)findAncestorWithClass(this, OtherId.class);
			return theOtherId.getOtherId();
		} catch (Exception e) {
			log.error(" Can't find enclosing OtherId for otherId tag ", e);
			throw new JspTagException("Error: Can't find enclosing OtherId for otherId tag ");
		}
	}

	public void setOtherId(String otherId) throws JspTagException {
		try {
			OtherId theOtherId = (OtherId)findAncestorWithClass(this, OtherId.class);
			theOtherId.setOtherId(otherId);
		} catch (Exception e) {
			log.error("Can't find enclosing OtherId for otherId tag ", e);
			throw new JspTagException("Error: Can't find enclosing OtherId for otherId tag ");
		}
	}

}
