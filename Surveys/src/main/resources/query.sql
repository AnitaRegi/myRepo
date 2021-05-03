drop table if exists answers_offered;
drop table if exists question;
drop table if exists survey;
drop table if exists survey_question;

CREATE TABLE surveyDB.answers_offered IF NOT EXISTS (
answer_id bigint not null auto_increment, 
option_one varchar(255), 
option_two varchar(255), 
option_three varchar(255), 
option_four varchar(255),
other varchar(255),
PRIMARY KEY (answer_id)) engine=MyISAM;
)


CREATE TABLE surveyDB.question (
question_id bigint not null auto_increment, 
qn_name varchar(255), 
qn_type varchar(50), 
order big_int,
PRIMARY KEY (question_id)) engine=MyISAM;
)

CREATE TABLE surveyDB.survey (
  id bigint NOT NULL,
  created_by bigint DEFAULT NULL,
  PRIMARY KEY (id)
) engine=MyISAM;