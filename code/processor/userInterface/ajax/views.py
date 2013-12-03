# Create your views here.
import os
import json
import urllib
import traceback
from django.http import HttpResponse
from config import *

def suggest(request, q):
    qlow = q.lower()
    url = "http://localhost:8983/solr/QACollection/suggest?q=name:%s&wt=json&indent=true"%qlow
    a = urllib.urlopen(url)
    resp = a.read()
    print type(resp)
    respPy = json.loads(resp)
    print respPy
    try: 
        temp_resp = respPy['spellcheck']['suggestions'][1]['suggestion']
        print json.dumps(temp_resp)
        for i in range(len(temp_resp)):
            respPy['spellcheck']['suggestions'][1]['suggestion'][i]=respPy['spellcheck']['suggestions'][1]['suggestion'][i].title()
        print respPy['spellcheck']['suggestions'][1]['suggestion']
        print temp_resp

    except:
        print "Exception"
        print traceback.format_exc()
        return HttpResponse("Exception")
    print "YOYOYOYOYOYOYOYOYO"*100
    print respPy
    respJ = json.dumps(respPy)
    print respJ

    return HttpResponse(respJ, content_type="application/json")

def facetList(request):
    qtype = request.GET.get('qtype')
    print qtype
    if not qtype:
        return HttpResponse("Error")
    queryFacetParams.update({'qtype':qtype}) 
    open(queryFacetFilename[qtype], 'w').close()
    os.system(queryFacetCmd%queryFacetParams)
    with open(queryFacetFilename[qtype]) as f:
        t = f.read()
    resp_data = json.loads(t)
    print json.dumps(resp_data, sort_keys=True, indent=4, separators=(',',': '))
    a = resp_data['facet_counts']['facet_fields'][facetBy[qtype]]
    facetDict ={}  
    facetTuple = sorted(zip(a[::2],a[1::2]), key=lambda x: -x[1])
        #facetDict.update({k:v})
    jsonResponse = json.dumps(facetTuple, sort_keys=True, indent=4)
    print jsonResponse
    return HttpResponse(jsonResponse, content_type="application/json")
    

def facetExpansion(request):
    qtype = request.GET.get('qtype')
    query = request.GET.get('expand')
    if (not qtype) or (not query):
        return HttpResponse("Error")
    queryFacetExpParams.update({'qtype':qtype, 'query':query})
    open(queryFacetExpFilename, 'w').close()
    os.system(queryFacetExpCmd%queryFacetExpParams)
    with open(queryFacetExpFilename) as f:
        t = f.read()
    resp_data = json.loads(t)
    print "##"*50
    print json.dumps(resp_data, sort_keys=True, indent=4, separators=(',',': '))
    a = resp_data['response']['docs']
    jsonResponse = json.dumps(a, sort_keys=True, indent=4)
    print jsonResponse
    return HttpResponse(jsonResponse, content_type="application/json")

def verticalSimilarity(request):
    qtype = request.GET.get('qtype')
    fiveW = request.GET.get('fiveW')
    col2 = request.GET.get('col2')
    noun = request.GET.get('noun')
    last_col = request.GET.get('last_col')
    if not (qtype and fiveW and col2 and noun and last_col):
        return HttpResponse("Error")
    queryVerticalParams.update({'qtype':qtype,
                                'fiveW':fiveW,
                                'col2':col2,
                                'noun':noun,
                                'last_col':last_col})

    open(queryVerticalFilename, 'w').close()
    os.system(queryVerticalCmd%queryVerticalParams)
    with open(queryVerticalFilename) as f:
        t = f.read()
    resp_data = json.loads(t)
    try: 
        key = resp_data['response']['docs'][0]['id'] #id of that particular document
        names = resp_data['moreLikeThis'][key]['docs'] #list of names similar to that document
    except KeyError:
        print "THERE WAS A KEY ERROR"
        keys = resp_data['moreLikeThis'].keys()
        key = keys[0]
        names = resp_data['moreLikeThis'][key]['docs'] #list of names similar to that document

    responseHTML="<strong>Same Question on Different People</strong><ul>"
    for each in names:
        name = each['name']
        responseHTML+="<li><a href='/ask?qtype=%s&fiveW=%s&col2=%s&noun=%s&last_col=%s'>"%(qtype,fiveW,col2,name,last_col)
        responseHTML+=fiveW.capitalize()+" "+col2+" "+name+" "+last_col+"?"
        responseHTML+="</a></li>"
    responseHTML+="</ul>" 
    return HttpResponse(responseHTML)
    #jsonResponse = json.dumps(resp_data, sort_keys=True, indent=4)
    #return HttpResponse(jsonResponse, content_type="application/json")

def horizontalSimilarity(request):
    qtype = request.GET.get('qtype')
    fiveW = request.GET.get('fiveW')
    col2 = request.GET.get('col2')
    noun = request.GET.get('noun')
    last_col = request.GET.get('last_col')
    if not (qtype and fiveW and col2 and noun and last_col):
        return HttpResponse("Error")
    queryHorizontalParams.update({'qtype':qtype,
                                'fiveW':fiveW,
                                'col2':col2,
                                'noun':noun,
                                'last_col':last_col})

    open(queryHorizontalFilename, 'w').close()
    os.system(queryHorizontalCmd%queryHorizontalParams)
    with open(queryHorizontalFilename) as f:
        t = f.read()
    resp_data = json.loads(t)
    print "*"*100
    keys = resp_data['response']['docs'][0].keys()
    keys.remove('name')
    responseHTML = "<strong>Same Person</strong><ul>"
    for each in keys:
        if each not in questionParams[qtype]:
            continue
        qParams = questionParams[qtype][each]
        responseHTML+="<li><a href='/ask?qtype=%s&fiveW=%s&col2=%s&noun=%s&last_col=%s'>"%(qtype,qParams[0],qParams[1],noun,qParams[2])
        responseHTML+=qParams[0].capitalize()+" "+qParams[1]+" "+noun+" "+qParams[2]+"?"
        responseHTML+="</a></li>"
    responseHTML+="</ul>" 
    #jsonResponse = json.dumps(resp_data, sort_keys=True, indent=4)
    return HttpResponse(responseHTML)

