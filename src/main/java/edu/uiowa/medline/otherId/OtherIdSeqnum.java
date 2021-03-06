package edu.uiowa.medline.otherId;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.medline.MEDLINETagLibTagSupport;

@SuppressWarnings("serial")
public class OtherIdSeqnum extends MEDLINETagLibTagSupport {
	private static final Log log = LogFactory.getLog(OtherIdSeqnum.class);


	public int doStartTag() throws JspException {
		try {
			OtherId theOtherId = (OtherId)findAncestorWithClass(this, OtherId.class);
			if (!theOtherId.commitNeeded) {
				pageContext.getOut().print(theOtherId.getSeqnum());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing OtherId for seqnum tag ", e);
			throw new JspTagException("Error: Can't find enclosing OtherId for seqnum tag ");
		}
		return SKIP_BODY;
	}

	public int getSeqnum() throws JspTagException {
		try {
			OtherId theOtherId = (OtherId)findAncestorWithClass(this, OtherId.class);
			return theOtherId.getSeqnum();
		} catch (Exception e) {
			log.error(" Can't find enclosing OtherId for seqnum tag ", e);
			throw new JspTagException("Error: Can't find enclosing OtherId for seqnum tag ");
		}
	}

	public void setSeqnum(int seqnum) throws JspTagException {
		try {
			OtherId theOtherId = (OtherId)findAncestorWithClass(this, OtherId.class);
			theOtherId.setSeqnum(seqnum);
		} catch (Exception e) {
			log.error("Can't find enclosing OtherId for seqnum tag ", e);
			throw new JspTagException("Error: Can't find enclosing OtherId for seqnum tag ");
		}
	}

}
