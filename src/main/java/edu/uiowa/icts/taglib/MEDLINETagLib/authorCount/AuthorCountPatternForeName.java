package edu.uiowa.icts.taglib.MEDLINETagLib.authorCount;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.icts.taglib.MEDLINETagLib.MEDLINETagLibTagSupport;


@SuppressWarnings("serial")
public class AuthorCountPatternForeName extends MEDLINETagLibTagSupport {

	public int doStartTag() throws JspException {
		try {
			AuthorCountPattern theAuthorCountPattern = (AuthorCountPattern)findAncestorWithClass(this, AuthorCountPattern.class);
			if (!theAuthorCountPattern.commitNeeded) {
				pageContext.getOut().print(theAuthorCountPattern.getForeName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing AuthorCount for foreName tag ");
		}
		return SKIP_BODY;
	}

	public String getForeName() throws JspTagException {
		try {
		    AuthorCountPattern theAuthorCountPattern = (AuthorCountPattern)findAncestorWithClass(this, AuthorCountPattern.class);
			return theAuthorCountPattern.getForeName();
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing AuthorCount for foreName tag ");
		}
	}

	public void setForeName(String foreName) throws JspTagException {
		try {
		    AuthorCountPattern theAuthorCountPattern = (AuthorCountPattern)findAncestorWithClass(this, AuthorCountPattern.class);
		    theAuthorCountPattern.setForeName(foreName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing AuthorCount for foreName tag ");
		}
	}

}
