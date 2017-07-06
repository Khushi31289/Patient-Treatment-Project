CREATE TABLE IF NOT EXISTS Users(
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    areaCode INT(3),
    prefix INT(3),
    suffix INT(4),
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (email)
);

CREATE TABLE IF NOT EXISTS Posts(
    postid INT(3) ZEROFILL NOT NULL AUTO_INCREMENT,
    title VARCHAR(255),
    author  VARCHAR(255) NOT NULL,
    msgcontent VARCHAR(255),
    dttime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    email VARCHAR(255) NOT NULL,
    PRIMARY KEY (postid)
);

CREATE TABLE IF NOT EXISTS Comments(
    commentid INT NOT NULL AUTO_INCREMENT,
    postid INT(3) ZEROFILL NOT NULL,
    commentcontent VARCHAR(255),
    dttime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    commentauthor VARCHAR(255),
    PRIMARY KEY (commentid),
    FOREIGN KEY (postid) 
        REFERENCES POSTS(postid) on delete cascade
);


INSERT INTO users (firstname, lastname, email, areaCode, prefix, suffix, password) VALUES ('Admin', 'User', 'Admin', 123, 313, 3130, '1234');

-- select * from COMMENTS order by dttime asc;
-- select * from Users;
-- select * from POSTS order by dttime asc
-- DELETE from posts WHERE postid = xx;
-- DELETE from COMMENTS WHERE commentid;
-- INSERT INTO comments (postid, commentcontent,commentauthor) VALUES(01,'abcd', 'khushi');
-- INSERT INTO posts (title, author, msgcontent, email) VALUES ('abcd', 'a', 'm', 'aa@s.com');
