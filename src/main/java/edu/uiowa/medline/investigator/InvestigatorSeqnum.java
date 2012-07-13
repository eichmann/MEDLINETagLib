package edu.uiowa.medline.investigator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.medline.MEDLINETagLibTagSupport;

@SuppressWarnings("serial")
public class InvestigatorSeqnum extends MEDLINETagLibTagSupport {
	private static final Log log = LogFactory.getLog(InvestigatorSeqnum.class);


	public int doStartTag() throws JspException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			if (!theInvestigator.commitNeeded) {
				pageContext.getOut().print(theInvestigator.getSeqnum());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Investigator for seqnum tag ", e);
			throw new JspTagException("Error: Can't find enclosing Investigator for seqnum tag ");
		}
		return SKIP_BODY;
	}

	public int getSeqnum() throws JspTagException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			return theInvestigator.getSeqnum();
		} catch (Exception e) {
			log.error(" Can't find enclosing Investigator for seqnum tag ", e);
			throw new JspTagException("Error: Can't find enclosing Investigator for seqnum tag ");
		}
	}

	public void setSeqnum(int seqnum) throws JspTagException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			theInvestigator.setSeqnum(seqnum);
		} catch (Exception e) {
			log.error("Can't find enclosing Investigator for seqnum tag ", e);
			throw new JspTagException("Error: Can't find enclosing Investigator for seqnum tag ");
		}
	}

}
