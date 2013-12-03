HOME Directory will be the one, where this file is located.

Steps for executions for windows
	1) In the command prompt
			a) Traverse to $HOME\Code\OrangeHummer\Solr. To launch the Jetty with the Solr WAR
				java -jar start.jar
			b) Traverse to the directory. To post xml files to that index.
				java -Durl=http://localhost:8983/solr/QACollection/update -jar post.jar *.xml
			c) To stop jetty, press CTRL C
			
Miscellaneous:
	1) Logs will be written to $HOME\code\Solr\logs\solr.log . Keep monitoring.
	2) To get better understanding of the project, the documented report is present in the HOME directory.
	
To delete the Solr Complete index
    1) java -Durl=http://localhost:8983/solr/QACollection/update -Dcommit=false -Ddata=args -jar post.jar "<delete><query>*:*</query></delete>"
	2) java -Durl=http://localhost:8983/solr/QACollection/update -jar post.jar -

UI Setup
--------
UI Set requires Python 2.7 with Django 1.4.2. 
a) To run the web server, traverse to $HOME/code/processor/userInterface and type:
    python manage.py runserver
b) To visit the Orange Hummer website go to 
    http://localhost:8000/


