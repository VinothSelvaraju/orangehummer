import time
import traceback
queryMapperParams = {'filename':'QueryMapper',
                     'destination_path':'docs/',
                     }
classpaths = ['../../processor/queryProcessor/bin/',
              '../../solr/dist/',
              '../../solr/dist/solrj-lib/']
java_cp_arg = "-cp "+":".join(classpaths)


queryMapperParams.update({'cp':java_cp_arg})

queryMapperCmd = "java %(cp)s %(filename)s %(destination_path)s %(qtype)s %(fiveW)s %(col2)s %(noun)s %(last_col)s" 

responseMessages = {'noresults': 'Sorry! No Results were found for your query!',
            'exception': 'Oops! There was an exception! Try again. ',
            'yesresults': "%(name)s's %(field)s is/are %(answers)s",}


date_list = ['birthdate', 'deathdate']

def convertDate(date_in_z):
    try:
        return time.strftime("%d %B %Y", time.strptime(date_in_z, "%Y-%m-%dT%H:%M:%SZ")), ""
    except:
        print traceback.format_exc()    
        return "", "Exception"
    
