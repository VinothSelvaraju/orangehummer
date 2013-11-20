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

Test