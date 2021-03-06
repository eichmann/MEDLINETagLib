package edu.uiowa.medline.author;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.medline.MEDLINETagLibTagSupport;

@SuppressWarnings("serial")
public class AuthorLastName extends MEDLINETagLibTagSupport {
	private static final Log log = LogFactory.getLog(AuthorLastName.class);


	public int doStartTag() throws JspException {
		try {
			Author theAuthor = (Author)findAncestorWithClass(this, Author.class);
			if (!theAuthor.commitNeeded) {
				pageContext.getOut().print(theAuthor.getLastName());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Author for lastName tag ", e);
			throw new JspTagException("Error: Can't find enclosing Author for lastName tag ");
		}
		return SKIP_BODY;
	}

	public String getLastName() throws JspTagException {
		try {
			Author theAuthor = (Author)findAncestorWithClass(this, Author.class);
			return theAuthor.getLastName();
		} catch (Exception e) {
			log.error(" Can't find enclosing Author for lastName tag ", e);
			throw new JspTagException("Error: Can't find enclosing Author for lastName tag ");
		}
	}

	public void setLastName(String lastName) throws JspTagException {
		try {
			Author theAuthor = (Author)findAncestorWithClass(this, Author.class);
			theAuthor.setLastName(lastName);
		} catch (Exception e) {
			log.error("Can't find enclosing Author for lastName tag ", e);
			throw new JspTagException("Error: Can't find enclosing Author for lastName tag ");
		}
	}

}
