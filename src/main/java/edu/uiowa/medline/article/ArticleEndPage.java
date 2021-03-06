package edu.uiowa.medline.article;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.medline.MEDLINETagLibTagSupport;

@SuppressWarnings("serial")
public class ArticleEndPage extends MEDLINETagLibTagSupport {
	private static final Log log = LogFactory.getLog(ArticleEndPage.class);


	public int doStartTag() throws JspException {
		try {
			Article theArticle = (Article)findAncestorWithClass(this, Article.class);
			if (!theArticle.commitNeeded) {
				pageContext.getOut().print(theArticle.getEndPage());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Article for endPage tag ", e);
			throw new JspTagException("Error: Can't find enclosing Article for endPage tag ");
		}
		return SKIP_BODY;
	}

	public int getEndPage() throws JspTagException {
		try {
			Article theArticle = (Article)findAncestorWithClass(this, Article.class);
			return theArticle.getEndPage();
		} catch (Exception e) {
			log.error(" Can't find enclosing Article for endPage tag ", e);
			throw new JspTagException("Error: Can't find enclosing Article for endPage tag ");
		}
	}

	public void setEndPage(int endPage) throws JspTagException {
		try {
			Article theArticle = (Article)findAncestorWithClass(this, Article.class);
			theArticle.setEndPage(endPage);
		} catch (Exception e) {
			log.error("Can't find enclosing Article for endPage tag ", e);
			throw new JspTagException("Error: Can't find enclosing Article for endPage tag ");
		}
	}

}
