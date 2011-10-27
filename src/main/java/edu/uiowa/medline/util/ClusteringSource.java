package edu.uiowa.medline.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.sql.DataSource;

import edu.uiowa.loki.clustering.Instance;
import edu.uiowa.loki.clustering.Author;
import edu.uiowa.loki.clustering.Cluster;
import edu.uiowa.loki.clustering.ExternalSource;
import edu.uiowa.loki.clustering.Linkage;
import edu.uiowa.medline.article.Article;
import edu.uiowa.medline.journal.Journal;

public class ClusteringSource extends ExternalSource {

    Hashtable<String, Instance> pmidHash = new Hashtable<String, Instance>();
	Pattern medDatePattern = Pattern.compile("^([0-9][0-9][0-9][0-9])(-[0-9][0-9][0-9][0-9])? ?.*");
    
    public ClusteringSource() {
    	super();
    	idHash = pmidHash;
    }
    
    public Hashtable<String, Instance> instantiateIDHash() {
    	return pmidHash;
    }
    
    private Connection getConnection() throws NamingException, SQLException, ClassNotFoundException {
		Connection theConnection = null;

		if (tomcat) {
	    	DataSource theDataSource = (DataSource)new InitialContext().lookup("java:/comp/env/jdbc/MEDLINETagLib");
	    	theConnection = theDataSource.getConnection();
	    } else {
	        Class.forName("org.postgresql.Driver");
			Properties props = new Properties();
			props.setProperty("user", "eichmann");
			props.setProperty("password", "translational");
			props.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
			props.setProperty("ssl", "true");
			theConnection = DriverManager.getConnection("jdbc:postgresql://neuromancer.icts.uiowa.edu/bioinformatics", props);
	    	
	    }
		
		return theConnection;
    }

    public void generateClusters(Vector<Cluster> clusters, Author author) {
    	try {
			cluster(clusters, author);
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    void cluster(Vector<Cluster> clusters, Author author) throws NamingException, SQLException, ClassNotFoundException {
		logger.debug(label + " clustering: " + author);

		Connection theConnection = getConnection();
		Pattern medDatePattern = Pattern.compile("^([0-9][0-9][0-9][0-9])(-[0-9][0-9][0-9][0-9])? ?.*");
        PreparedStatement stmt = theConnection.prepareStatement("select author.pmid,pub_year,article.title,medline_date from medline11.author,medline11.journal, medline11.article where journal.pmid=article.pmid and article.pmid=author.pmid and last_name = ? and fore_name = ? order by pmid desc");
        stmt.setString(1,author.getLastName());
        stmt.setString(2,author.getForeName());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int pmid = rs.getInt(1);
            int year = rs.getInt(2);
            String title = rs.getString(3);
            String medlineDate = rs.getString(4);
            
			if (year == 0) {
				Matcher medDateMatcher = medDatePattern.matcher(medlineDate);
				logger.debug("medlineDate: " + medlineDate);
				if (medDateMatcher.find()) {
					year = Integer.parseInt(medDateMatcher.group(1));
					logger.debug("\tyear: " + year);
				}
			}
            
            logger.debug("\tpmid: " + pmid + "\tyear: " + year + "\ttitle: " + title);
            Instance theInstance = new Instance();
            theInstance.setPmid(pmid);
            theInstance.setYear(year);
            theInstance.setTitle(title);
            theInstance.getLinkages().add(new Linkage(sid,pmid));
            pmidHash.put(""+pmid, theInstance);
            
            PreparedStatement authStmt = theConnection.prepareStatement("select last_name, fore_name, initials from medline11.author where pmid = ? order by 1,2");
            authStmt.setInt(1, pmid);
            ResultSet ars = authStmt.executeQuery();
            while (ars.next()) {
                String lname = ars.getString(1);
                String fname = ars.getString(2);
                String initials = ars.getString(3);
                if (!(author.getLastName().equals(lname) && author.getForeName().equals(fname))) {
                	logger.debug("\t\tauthor: " + lname + " " + fname);
                    theInstance.getAuthors().addElement(lname + " " + initials);
                }
            }
            authStmt.close();
            
            Cluster bestMatch = null;
            for (int i = 0; i<clusters.size() && bestMatch == null; i++) {
                if (authorMatch(theInstance, clusters.elementAt(i)))
            		bestMatch = clusters.elementAt(i);
            }
        	if (bestMatch == null) {
        		bestMatch = new Cluster();
        		clusters.addElement(bestMatch);
        	}
        	bestMatch.getInstances().addElement(theInstance);
        	for (int i = 0; i < theInstance.getAuthors().size(); i++) {
        		bestMatch.getAuthorHash().put(theInstance.getAuthors().elementAt(i), theInstance.getAuthors().elementAt(i));
        	}
        }
        stmt.close();
        theConnection.close();
    }
    
    public int getAuthorCountCount() {
    	int count = 0;
    	
		try {
			PreparedStatement stat = getConnection().prepareStatement("SELECT count(*) from medline11.author_count");

			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	
    	return count;
    }
    
	public boolean authorCountExists(String lastName, String foreName) {
		int count = 0;

		try {
			PreparedStatement stat = getConnection().prepareStatement("SELECT count(*) from medline11.author_count where"
																		+ " last_name = ?" + " and fore_name = ?");

			stat.setString(1, lastName);
			stat.setString(2, foreName);
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count > 0;
	}

    public String authorString(int pmid) {
        StringBuffer authorBuffer = new StringBuffer();

        try {
            Connection theConnection = getConnection();
			int count = 0;
			PreparedStatement loadStmt = getConnection().prepareStatement("select last_name,fore_name from medline11.author where pmid = ? order by seqnum");
			loadStmt.setInt(1,pmid);
			ResultSet lrs = loadStmt.executeQuery();
			while (lrs.next()) {
			    String lastName = lrs.getString(1);
			    String foreName = lrs.getString(2);
			    authorBuffer.append((count++ > 0 ? ", " : "") + lastName + ", " + foreName);
			}
			loadStmt.close();
			authorBuffer.append(".");
			
			theConnection.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return authorBuffer.toString();
    }

    public String citationString(int pmid) {
        String citationString = "";
    	return null;
    }
    
    public String hyperlinkedCitationString(int pmid, String hrefPrefix) {
        String citationString = "";
        int year = 0;

		try {
			Article theArticle = new Article();
			theArticle.setPmid(pmid);
			theArticle.doStartTag();
			Journal theJournal = new Journal();
			theJournal.setParent(theArticle);
			theJournal.doStartTag();
			year = theJournal.getPubYear();
			
			if (year == 0) {
				Matcher medDateMatcher = medDatePattern.matcher(theJournal.getMedlineDate());
				logger.info("medlineDate: " + theJournal.getMedlineDate());
				if (medDateMatcher.find()) {
					year = Integer.parseInt(medDateMatcher.group(1));
					logger.info("\tyear: " + year);
				}
			}
			
			citationString = authorString(pmid)
					+ " <a href=\"" + hrefPrefix + "?id="
					+ theArticle.getPmid() + "\">" + theArticle.getTitle()
					+ "</a> <i>" + theArticle.getTa() + "</i> "
					+ theJournal.getVolume() + "(" + theJournal.getIssue()
					+ "):" + theArticle.getMedlinePgn() + ", "
					+ year + ".";
			theJournal.freeConnection();
			theArticle.freeConnection();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JspTagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JspException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return citationString;
    }

    public int publicationYear(int pmid) {
        int year = 0;

		try {
			Article theArticle = new Article();
			theArticle.setPmid(pmid);
			theArticle.doStartTag();
			Journal theJournal = new Journal();
			theJournal.setParent(theArticle);
			theJournal.doStartTag();
			year = theJournal.getPubYear();

			if (year == 0) {
				Matcher medDateMatcher = medDatePattern.matcher(theJournal.getMedlineDate());
				logger.info("medlineDate: " + theJournal.getMedlineDate());
				if (medDateMatcher.find()) {
					year = Integer.parseInt(medDateMatcher.group(1));
					logger.info("\tyear: " + year);
				}
			}
			
			theJournal.freeConnection();
			theArticle.freeConnection();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JspTagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JspException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return year;
    }
}