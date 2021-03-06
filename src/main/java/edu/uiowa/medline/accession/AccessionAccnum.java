package edu.uiowa.medline.accession;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.medline.MEDLINETagLibTagSupport;

@SuppressWarnings("serial")
public class AccessionAccnum extends MEDLINETagLibTagSupport {
	private static final Log log = LogFactory.getLog(AccessionAccnum.class);


	public int doStartTag() throws JspException {
		try {
			Accession theAccession = (Accession)findAncestorWithClass(this, Accession.class);
			if (!theAccession.commitNeeded) {
				pageContext.getOut().print(theAccession.getAccnum());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Accession for accnum tag ", e);
			throw new JspTagException("Error: Can't find enclosing Accession for accnum tag ");
		}
		return SKIP_BODY;
	}

	public int getAccnum() throws JspTagException {
		try {
			Accession theAccession = (Accession)findAncestorWithClass(this, Accession.class);
			return theAccession.getAccnum();
		} catch (Exception e) {
			log.error(" Can't find enclosing Accession for accnum tag ", e);
			throw new JspTagException("Error: Can't find enclosing Accession for accnum tag ");
		}
	}

	public void setAccnum(int accnum) throws JspTagException {
		try {
			Accession theAccession = (Accession)findAncestorWithClass(this, Accession.class);
			theAccession.setAccnum(accnum);
		} catch (Exception e) {
			log.error("Can't find enclosing Accession for accnum tag ", e);
			throw new JspTagException("Error: Can't find enclosing Accession for accnum tag ");
		}
	}

}
