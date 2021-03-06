package edu.uiowa.medline.journal;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.medline.MEDLINETagLibTagSupport;

@SuppressWarnings("serial")
public class JournalTitle extends MEDLINETagLibTagSupport {
	private static final Log log = LogFactory.getLog(JournalTitle.class);


	public int doStartTag() throws JspException {
		try {
			Journal theJournal = (Journal)findAncestorWithClass(this, Journal.class);
			if (!theJournal.commitNeeded) {
				pageContext.getOut().print(theJournal.getTitle());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Journal for title tag ", e);
			throw new JspTagException("Error: Can't find enclosing Journal for title tag ");
		}
		return SKIP_BODY;
	}

	public String getTitle() throws JspTagException {
		try {
			Journal theJournal = (Journal)findAncestorWithClass(this, Journal.class);
			return theJournal.getTitle();
		} catch (Exception e) {
			log.error(" Can't find enclosing Journal for title tag ", e);
			throw new JspTagException("Error: Can't find enclosing Journal for title tag ");
		}
	}

	public void setTitle(String title) throws JspTagException {
		try {
			Journal theJournal = (Journal)findAncestorWithClass(this, Journal.class);
			theJournal.setTitle(title);
		} catch (Exception e) {
			log.error("Can't find enclosing Journal for title tag ", e);
			throw new JspTagException("Error: Can't find enclosing Journal for title tag ");
		}
	}

}
