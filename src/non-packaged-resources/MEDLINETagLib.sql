CREATE TABLE medline10.author_count (
       last_name TEXT NOT NULL
     , fore_name TEXT NOT NULL
     , count INT
     , PRIMARY KEY (last_name, fore_name)
);

CREATE TABLE medline_clustering.document_cluster (
       cid INT NOT NULL
     , last_name TEXT
     , fore_name TEXT
     , PRIMARY KEY (cid)
);

CREATE TABLE medline10.article (
       pmid INT NOT NULL
     , date_created DATE
     , date_completed DATE
     , date_revised DATE
     , title TEXT
     , start_page INT
     , end_page INT
     , medline_pgn TEXT
     , abstract_text TEXT
     , copyright TEXT
     , affiliation TEXT
     , type TEXT
     , vernacular_title TEXT
     , country TEXT
     , ta TEXT
     , nlm_unique_id TEXT
     , issn_linking TEXT
     , reference_count INT
     , pub_model TEXT
     , status TEXT
     , PRIMARY KEY (pmid)
);

CREATE TABLE medline10.data_bank (
       pmid INT NOT NULL
     , seqnum INT NOT NULL
     , name TEXT
     , PRIMARY KEY (pmid, seqnum)
     , CONSTRAINT FK_data_bank_1 FOREIGN KEY (pmid)
                  REFERENCES medline10.article (pmid)
);

CREATE TABLE medline10.mesh_heading (
       pmid INT NOT NULL
     , seqnum INT NOT NULL
     , descriptor_name TEXT
     , major BOOLEAN
     , PRIMARY KEY (pmid, seqnum)
     , CONSTRAINT FK_mesh_heading_1 FOREIGN KEY (pmid)
                  REFERENCES medline10.article (pmid)
);

CREATE TABLE medline10.elocation (
       pmid INT NOT NULL
     , seqnum INT NOT NULL
     , eid TEXT
     , PRIMARY KEY (pmid, seqnum)
     , CONSTRAINT FK_elocation_1 FOREIGN KEY (pmid)
                  REFERENCES medline10.article (pmid)
);

CREATE TABLE medline10.author (
       pmid INT NOT NULL
     , seqnum INT NOT NULL
     , last_name TEXT
     , fore_name TEXT
     , initials TEXT
     , suffix TEXT
     , collective_name TEXT
     , affiliation TEXT
     , PRIMARY KEY (pmid, seqnum)
     , CONSTRAINT FK_author_1 FOREIGN KEY (pmid)
                  REFERENCES medline10.article (pmid)
);

CREATE TABLE medline10.language (
       pmid INT NOT NULL
     , seqnum INT NOT NULL
     , language TEXT
     , PRIMARY KEY (pmid, seqnum)
     , CONSTRAINT FK_language_1 FOREIGN KEY (pmid)
                  REFERENCES medline10.article (pmid)
);

CREATE TABLE medline10.accession (
       pmid INT NOT NULL
     , seqnum INT NOT NULL
     , accnum INT NOT NULL
     , accession TEXT
     , PRIMARY KEY (pmid, seqnum, accnum)
     , CONSTRAINT FK_accession_1 FOREIGN KEY (pmid, seqnum)
                  REFERENCES medline10.data_bank (pmid, seqnum)
);

CREATE TABLE medline10.grant (
       pmid INT NOT NULL
     , seqnum INT NOT NULL
     , gid TEXT
     , acronym TEXT
     , agency TEXT
     , country TEXT
     , PRIMARY KEY (pmid, seqnum)
     , CONSTRAINT FK_grant_1 FOREIGN KEY (pmid)
                  REFERENCES medline10.article (pmid)
);

CREATE TABLE medline10.article_date (
       pmid INT NOT NULL
     , seqnum INT NOT NULL
     , year INT
     , month INT
     , day INT
     , PRIMARY KEY (pmid, seqnum)
     , CONSTRAINT FK_article_date_1 FOREIGN KEY (pmid)
                  REFERENCES medline10.article (pmid)
);

CREATE TABLE medline10.chemical (
       pmid INT NOT NULL
     , seqnum INT NOT NULL
     , registry_number TEXT
     , substance_name TEXT
     , PRIMARY KEY (pmid, seqnum)
     , CONSTRAINT FK_chemical_1 FOREIGN KEY (pmid)
                  REFERENCES medline10.article (pmid)
);

CREATE TABLE medline10.citation_subset (
       pmid INT NOT NULL
     , seqnum INT NOT NULL
     , label TEXT
     , PRIMARY KEY (pmid, seqnum)
     , CONSTRAINT FK_citation_subset_1 FOREIGN KEY (pmid)
                  REFERENCES medline10.article (pmid)
);

CREATE TABLE medline10.comments_corrections (
       pmid INT NOT NULL
     , seqnum INT NOT NULL
     , ref_type TEXT
     , source TEXT
     , ref_pmid INT
     , note TEXT
     , PRIMARY KEY (pmid, seqnum)
     , CONSTRAINT FK_comment_on_1 FOREIGN KEY (pmid)
                  REFERENCES medline10.article (pmid)
);

CREATE TABLE medline10.gene_symbol (
       pmid INT NOT NULL
     , seqnum INT NOT NULL
     , symbol TEXT
     , PRIMARY KEY (pmid, seqnum)
     , CONSTRAINT FK_gene_symbol_1 FOREIGN KEY (pmid)
                  REFERENCES medline10.article (pmid)
);

CREATE TABLE medline10.mesh_qualifier (
       pmid INT NOT NULL
     , seqnum INT NOT NULL
     , qnum INT NOT NULL
     , qualifier_name TEXT
     , major BOOLEAN
     , PRIMARY KEY (pmid, seqnum, qnum)
     , CONSTRAINT FK_mesh_qualifier_1 FOREIGN KEY (pmid, seqnum)
                  REFERENCES medline10.mesh_heading (pmid, seqnum)
);

CREATE TABLE medline10.personal_name_subject (
       pmid INT NOT NULL
     , seqnum INT NOT NULL
     , last_name TEXT
     , fore_name TEXT
     , initials TEXT
     , suffix TEXT
     , PRIMARY KEY (pmid, seqnum)
     , CONSTRAINT FK_personal_name_subject_1 FOREIGN KEY (pmid)
                  REFERENCES medline10.article (pmid)
);

CREATE TABLE medline10.other_id (
       pmid INT NOT NULL
     , seqnum INT NOT NULL
     , source TEXT
     , other_id TEXT
     , PRIMARY KEY (pmid, seqnum)
     , CONSTRAINT FK_other_id_1 FOREIGN KEY (pmid)
                  REFERENCES medline10.article (pmid)
);

CREATE TABLE medline10.other_abstract (
       pmid INT NOT NULL
     , seqnum INT NOT NULL
     , type TEXT
     , abstract_text TEXT
     , copyright TEXT
     , PRIMARY KEY (pmid, seqnum)
     , CONSTRAINT FK_other_abstract_1 FOREIGN KEY (pmid)
                  REFERENCES medline10.article (pmid)
);

CREATE TABLE medline10.keyword (
       pmid INT NOT NULL
     , seqnum INT NOT NULL
     , keyword TEXT
     , major BOOLEAN
     , PRIMARY KEY (pmid, seqnum)
     , CONSTRAINT FK_keyword_1 FOREIGN KEY (pmid)
                  REFERENCES medline10.article (pmid)
);

CREATE TABLE medline10.spaceflight_mission (
       pmid INT NOT NULL
     , seqnum INT NOT NULL
     , mission TEXT
     , PRIMARY KEY (pmid, seqnum)
     , CONSTRAINT FK_spaceflight_mission_1 FOREIGN KEY (pmid)
                  REFERENCES medline10.article (pmid)
);

CREATE TABLE medline10.investigator (
       pmid INT NOT NULL
     , seqnum INT NOT NULL
     , last_name TEXT
     , fore_name TEXT
     , initials TEXT
     , suffix TEXT
     , affiliation TEXT
     , PRIMARY KEY (pmid, seqnum)
     , CONSTRAINT FK_investigator_1 FOREIGN KEY (pmid)
                  REFERENCES medline10.article (pmid)
);

CREATE TABLE medline10.general_note (
       pmid INT NOT NULL
     , seqnum INT NOT NULL
     , note TEXT
     , PRIMARY KEY (pmid, seqnum)
     , CONSTRAINT FK_general_note_1 FOREIGN KEY (pmid)
                  REFERENCES medline10.article (pmid)
);

CREATE TABLE medline10.publication_type (
       pmid INT NOT NULL
     , seqnum INT NOT NULL
     , label TEXT
     , PRIMARY KEY (pmid, seqnum)
     , CONSTRAINT FK_publication_type_1 FOREIGN KEY (pmid)
                  REFERENCES medline10.article (pmid)
);

CREATE TABLE medline_clustering.cluster_document (
       cid INT NOT NULL
     , pmid INT NOT NULL
     , PRIMARY KEY (cid, pmid)
     , CONSTRAINT FK_cluster_document_2 FOREIGN KEY (pmid)
                  REFERENCES medline10.article (pmid) ON DELETE CASCADE ON UPDATE CASCADE
     , CONSTRAINT FK_cluster_document_1 FOREIGN KEY (cid)
                  REFERENCES medline_clustering.document_cluster (cid) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE medline10.journal (
       pmid INT NOT NULL
     , issn TEXT
     , volume TEXT
     , issue TEXT
     , pub_year INT
     , pub_month TEXT
     , pub_day INT
     , pub_season TEXT
     , medline_date TEXT
     , title TEXT
     , iso_abbreviation TEXT
     , PRIMARY KEY (pmid)
     , CONSTRAINT FK_journal_1 FOREIGN KEY (pmid)
                  REFERENCES medline10.article (pmid)
);
