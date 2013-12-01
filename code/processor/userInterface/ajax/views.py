# Create your views here.
import json
import urllib
from django.http import HttpResponse


def suggest(request, q):
    #print request
    #print q
    qlow = q.lower()
    url = "http://localhost:8983/solr/QACollection/suggest?q=name:%s&wt=json&indent=true"%qlow
    #print url
    a = urllib.urlopen(url)
    resp = a.read()
    #print resp
    respJ = json.dumps(resp)
    #print "*"*100
    #print type(respJ)
    return HttpResponse(respJ, content_type="application/json")

