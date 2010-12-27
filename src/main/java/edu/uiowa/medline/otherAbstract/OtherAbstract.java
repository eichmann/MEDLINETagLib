package edu.uiowa.medline.otherAbstract;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.medline.article.Article;

import edu.uiowa.medline.MEDLINETagLibTagSupport;
import edu.uiowa.medline.Sequence;

@SuppressWarnings("serial")

public class OtherAbstract extends MEDLINETagLibTagSupport {

	static OtherAbstract currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log =LogFactory.getLog(OtherAbstract.class);

	Vector<MEDLINETagLibTagSupport> parentEntities = new Vector<MEDLINETagLibTagSupport>();

	int pmid = 0;
	int seqnum = 0;
	String type = null;
	String abstractText = null;
	String copyright = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			Article theArticle = (Article)findAncestorWithClass(this, Article.class);
			if (theArticle!= null)
				parentEntities.addElement(theArticle);

			if (theArticle == null) {
			} else {
				pmid = theArticle.getPmid();
			}

			OtherAbstractIterator theOtherAbstractIterator = (OtherAbstractIterator)findAncestorWithClass(this, OtherAbstractIterator.class);

			if (theOtherAbstractIterator != null) {
				pmid = theOtherAbstractIterator.getPmid();
				seqnum = theOtherAbstractIterator.getSeqnum();
			}

			if (theOtherAbstractIterator == null && theArticle == null && seqnum == 0) {
				// no seqnum was provided - the default is to assume that it is a new OtherAbstract and to generate a new seqnum
				seqnum = Sequence.generateID();
				log.debug("generating new OtherAbstract " + seqnum);
				insertEntity();
			} else {
				// an iterator or seqnum was provided as an attribute - we need to load a OtherAbstract from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select type,abstract_text,copyright from medline10.other_abstract where pmid = ? and seqnum = ?");
				stmt.setInt(1,pmid);
				stmt.setInt(2,seqnum);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (type == null)
						type = rs.getString(1);
					if (abstractText == null)
						abstractText = rs.getString(2);
					if (copyright == null)
						copyright = rs.getString(3);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new JspTagException("Error: JDBC error retrieving seqnum " + seqnum);
		} finally {
			freeConnection();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update medline10.other_abstract set type = ?, abstract_text = ?, copyright = ? where pmid = ? and seqnum = ?");
				stmt.setString(1,type);
				stmt.setString(2,abstractText);
				stmt.setString(3,copyright);
				stmt.setInt(4,pmid);
				stmt.setInt(5,seqnum);
				stmt.executeUpdate();
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new JspTagException("Error: IOException while writing to the user");
		} finally {
			clearServiceState();
			freeConnection();
		}
		return super.doEndTag();
	}

	public void insertEntity() throws JspException {
		try {
			if (seqnum == 0) {
				seqnum = Sequence.generateID();
				log.debug("generating new OtherAbstract " + seqnum);
			}

			if (type == null)
				type = "";
			if (abstractText == null)
				abstractText = "";
			if (copyright == null)
				copyright = "";
			PreparedStatement stmt = getConnection().prepareStatement("insert into medline10.other_abstract(pmid,seqnum,type,abstract_text,copyright) values (?,?,?,?,?)");
			stmt.setInt(1,pmid);
			stmt.setInt(2,seqnum);
			stmt.setString(3,type);
			stmt.setString(4,abstractText);
			stmt.setString(5,copyright);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new JspTagException("Error: IOException while writing to the user");
		} finally {
			freeConnection();
		}
	}

	public int getPmid () {
		return pmid;
	}

	public void setPmid (int pmid) {
		this.pmid = pmid;
	}

	public int getActualPmid () {
		return pmid;
	}

	public int getSeqnum () {
		return seqnum;
	}

	public void setSeqnum (int seqnum) {
		this.seqnum = seqnum;
	}

	public int getActualSeqnum () {
		return seqnum;
	}

	public String getType () {
		if (commitNeeded)
			return "";
		else
			return type;
	}

	public void setType (String type) {
		this.type = type;
		commitNeeded = true;
	}

	public String getActualType () {
		return type;
	}

	public String getAbstractText () {
		if (commitNeeded)
			return "";
		else
			return abstractText;
	}

	public void setAbstractText (String abstractText) {
		this.abstractText = abstractText;
		commitNeeded = true;
	}

	public String getActualAbstractText () {
		return abstractText;
	}

	public String getCopyright () {
		if (commitNeeded)
			return "";
		else
			return copyright;
	}

	public void setCopyright (String copyright) {
		this.copyright = copyright;
		commitNeeded = true;
	}

	public String getActualCopyright () {
		return copyright;
	}

	public static int pmidValue() throws JspException {
		try {
			return currentInstance.getPmid();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function pmidValue()");
		}
	}

	public static int seqnumValue() throws JspException {
		try {
			return currentInstance.getSeqnum();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function seqnumValue()");
		}
	}

	public static String typeValue() throws JspException {
		try {
			return currentInstance.getType();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function typeValue()");
		}
	}

	public static String abstractTextValue() throws JspException {
		try {
			return currentInstance.getAbstractText();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function abstractTextValue()");
		}
	}

	public static String copyrightValue() throws JspException {
		try {
			return currentInstance.getCopyright();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function copyrightValue()");
		}
	}

	private void clearServiceState () {
		pmid = 0;
		seqnum = 0;
		type = null;
		abstractText = null;
		copyright = null;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<MEDLINETagLibTagSupport>();

	}

}
