-- SCRIPTS
	DO $$ 
	BEGIN 
	IF NOT EXISTS (
	    SELECT 1
	    FROM  "user" 
	    WHERE  username = 'admin'
	    ) THEN
	  insert into "user" (username, password) values ('admin','welcome');
	END IF;
	END$$;