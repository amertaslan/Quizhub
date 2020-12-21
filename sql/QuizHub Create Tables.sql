CREATE TABLE teacher
(
    id serial NOT NULL,
    serial_key character varying(50) NOT NULL,
    name character varying(50) NOT NULL,
    surname character varying(50) NOT NULL,
    password character varying(50) NOT NULL,
    email character varying(50) NOT NULL,
    gender character varying(50) NOT NULL,
    birthdate character varying(50) NOT NULL,
    CONSTRAINT teacher_pkey PRIMARY KEY (id),
    CONSTRAINT teacher_email_key UNIQUE (email),
	CONSTRAINT teacher_serial_key UNIQUE (serial_key)
);

CREATE TABLE student
(
    id serial NOT NULL,
    name character varying(50) NOT NULL,
    surname character varying(50) NOT NULL,
    password character varying(50) NOT NULL,
    email character varying(50) UNIQUE NOT NULL,
    gender character varying(10) NOT NULL,
    birthdate character varying(50) NOT NULL,
    CONSTRAINT student_pkey PRIMARY KEY (id)
);

CREATE TABLE classroom
(
    id serial NOT NULL,
    name character varying(50) NOT NULL,
    CONSTRAINT classroom_pkey PRIMARY KEY (id),
    CONSTRAINT classroom_name_key UNIQUE (name)
);

CREATE TABLE classroomstudent
(
    studentid integer NOT NULL,
    classid integer NOT NULL,
    teacherid integer NOT NULL,
	CONSTRAINT classroomstudent_pkey PRIMARY KEY (studentid, classid, teacherid),
    CONSTRAINT classroomstudent_studentid_fkey FOREIGN KEY (studentid)
        REFERENCES student (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT classroomstudent_classid_fkey FOREIGN KEY (classid)
        REFERENCES classroom (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
	CONSTRAINT classroomstudent_teacherid_fkey FOREIGN KEY (teacherid)
        REFERENCES teacher (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE quiz
(
    id serial NOT NULL,
    title character varying(100) NOT NULL,
    level character varying(1) NOT NULL,
    teacherid integer NOT NULL,
    CONSTRAINT quiz_pkey PRIMARY KEY (id),
    CONSTRAINT quiz_title_key UNIQUE (title),
	CONSTRAINT quiz_classid_fkey FOREIGN KEY (classid)
        REFERENCES classroom (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
	CONSTRAINT quiz_teacherid_fkey FOREIGN KEY (teacherid)
        REFERENCES teacher (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);


CREATE TABLE classroomquiz
(
    quizid integer NOT NULL,
    classid integer NOT NULL,
    CONSTRAINT quizid_classid_pk PRIMARY KEY (quizid, classid),
	CONSTRAINT classroomquiz_classid_fkey FOREIGN KEY (classid)
        REFERENCES classroom (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
	CONSTRAINT classroomquiz_quizid_fkey FOREIGN KEY (quizid)
        REFERENCES quiz (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);


CREATE TABLE quizstudent
(
    quizid integer NOT NULL,
    studentid integer NOT NULL,
    score integer NOT NULL,
	CONSTRAINT quizstudent_pk PRIMARY KEY (quizid, studentid),
	CONSTRAINT quizstudent_studentid_fkey FOREIGN KEY (studentid)
        REFERENCES student (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
	CONSTRAINT quizstudent_quizid_fkey FOREIGN KEY (quizid)
        REFERENCES quiz (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE question
(
    id serial NOT NULL,
    quizid integer NOT NULL,
    text character varying(1000) NOT NULL,
    answer1 character varying(100) NOT NULL,
    answer2 character varying(100) NOT NULL,
    answer3 character varying(100) NOT NULL,
    answer4 character varying(100) NOT NULL,
    trueanswer character varying(100) NOT NULL,
    explanation character varying(1000),
    number integer NOT NULL,
    CONSTRAINT question_pkey PRIMARY KEY (id),
	CONSTRAINT question_quizid_fkey FOREIGN KEY (quizid)
        REFERENCES quiz (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);


CREATE TABLE answer
(
    id serial NOT NULL,
    text character varying(100),
    quizid integer NOT NULL,
    studentid integer NOT NULL,
    questionnum integer NOT NULL,
    istrue boolean NOT NULL,
    CONSTRAINT answer_pkey PRIMARY KEY (id),
	CONSTRAINT answer_studentid_fkey FOREIGN KEY (studentid)
        REFERENCES student (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
	CONSTRAINT answer_quizid_fkey FOREIGN KEY (quizid)
        REFERENCES quiz (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);
