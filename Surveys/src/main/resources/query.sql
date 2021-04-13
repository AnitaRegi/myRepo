drop table if exists answers_offered;
drop table if exists question;
drop table if exists survey;
drop table if exists survey_question;

CREATE TABLE surveyDb.answers_offered IF NOT EXISTS (
answer_id bigint not null auto_increment, 
option_one varchar(255), 
option_two varchar(255), 
option_three varchar(255), 
option_four varchar(255),
other varchar(255),
PRIMARY KEY (answer_id)) engine=MyISAM;
)


CREATE TABLE surveyDb.question (
question_id bigint not null auto_increment, 
qn_name varchar(255), 
qn_type varchar(50), 
PRIMARY KEY (question_id)) engine=MyISAM;
)

CREATE TABLE surveyDb.survey (
  survey_id bigint NOT NULL,
  created_by bigint DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  end_date date DEFAULT NULL,
  name varchar(255) NOT NULL,
  start_date date DEFAULT NULL,
  status varchar(255) DEFAULT NULL,
  version varchar(5) DEFAULT NULL,
  PRIMARY KEY (survey_id)
) engine=MyISAM;