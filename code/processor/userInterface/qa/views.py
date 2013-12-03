# Create your views here.
from django.shortcuts import render_to_response, render
from django.http import HttpResponse, HttpResponseRedirect
from qa.forms import QuestionForm
from django.views.generic.edit import FormView
import os
import md5
import json
import time
import urllib
import traceback
from config import *

class HomePageView(FormView):
    template_name = 'qa/home.html'
    form_class = QuestionForm
    success_url = '/thanks/'

    def get(self, request, *args, **kwargs):
        print "REEEEEE-------->>>>>>>>>>", request.META.get('HTTP_REFERER')
        if request.META.get('HTTP_REFERER') and 'facet' in request.META.get('HTTP_REFERER'):
            form = self.form_class(initial={'noun':request.COOKIES.get('noun'),'qtype':request.COOKIES.get('qtype')})
        else:
            form = self.form_class()
        return render(request, self.template_name, {'form': form, 'recent':{}})

    def post(self, request, *args, **kwargs):
        print request.POST
        form = self.form_class(request.POST)
        if form.is_valid():
            print "VALID"
            print request.POST.copy()
            response = HttpResponseRedirect('results')
            response.set_cookie('qtype',request.POST.get('qtype'))
            response.set_cookie('fiveW',request.POST.get('fiveW'))
            response.set_cookie('col2',request.POST.get('col2'))
            response.set_cookie('noun',request.POST.get('noun'))
            response.set_cookie('last_col',request.POST.get('last_col'))
            return response
        #else:
            #print "Invalid"
        print form.errors
        return render(request, self.template_name, {'form': form, 'recent':{}})


def resultsPage(request):
    dataDict = {'qtype': request.COOKIES.get('qtype'),
                'fiveW' : request.COOKIES.get('fiveW'),
                'col2' : request.COOKIES.get('col2'),
                'noun' : request.COOKIES.get('noun'),
                'last_col' : request.COOKIES.get('last_col'),}
    print dataDict
    if not (dataDict.get('qtype') and dataDict.get('fiveW') and dataDict.get('col2') and dataDict.get('noun') and dataDict.get('last_col')):
        return HttpResponseRedirect('/')
    dataDict['noun']=dataDict['noun'].title()
    form = QuestionForm(dataDict)
    print "VALID"
    alternative_text = []
    try: 
        queryMapperParams.update(dataDict)
        os.system(queryMapperCmd%queryMapperParams)
        with open("docs/queryOutput.json") as f:
            t = f.read()

        resp_data = json.loads(t)
        print resp_data
        if int(resp_data['response']['numFound'])<=0:
            alternative_text=[]
            img_source = ''
            response_text = responseMessages['noresults']
            collationList = resp_data['spellcheck']['suggestions'][3::2]
            for each in collationList:
                alternative_text.append(each[1].split(':')[1].replace('"',''))

            #alternative_text = resp_data['spellcheck']['suggestions'][1]['suggestion'] if resp_data['spellcheck']['suggestions'] else []
        else:
            response_text = ""
            img_source=""
            for i in range(min(10, int(resp_data['response']['numFound']))):
                keys = resp_data['response']['docs'][i].keys()
                print keys
                if len(keys)<3:
                    #response_text = responseMessages['noresults']
                    #break
                    continue
                elif len(keys)>4:
                    raise Exception
                elif len(keys)==4:
                    keys.remove('image')
                    img_name = resp_data['response']['docs'][i]['image']
                    img_name = img_name.replace(' ','_')
                    m = md5.new(img_name).hexdigest()
                    img_source = 'http://upload.wikimedia.org/wikipedia/commons/'
                    rem_source = m[0]+'/'+m[0]+m[1]+'/'+urllib.quote(img_name)
                    img_source+=rem_source


                keys.remove('name')
                keys.remove('id')

                field_text = keys[0] 
                if "highlighting" in resp_data:
                    name_text = ", ".join(resp_data['highlighting'][resp_data['response']['docs'][i]['id']]['name'])
                else: 
                    name_text += "<strong>"+ resp_data['response']['docs'][i]['name'] +"</strong>"
                result_text = resp_data['response']['docs'][i][keys[0]]
                if keys[0] in date_list:
                    result_text, dateException = convertDate(result_text)
                    if dateException:
                        response_text = responseMessages['exception']
                        break
                if isinstance(result_text, list):
                    result_text = ", ".join(result_text)
                responseParams = {'name':name_text,
                                    'field':field_text,
                                    'answers':result_text}
                each_return_string = responseMessages['yesresults']%responseParams
                response_text+=each_return_string+"<br/>"
            if not response_text:
                response_text = responseMessages['noresults']
                    
        print response_text
    except:
        print "There was an exception"
        print traceback.format_exc()
        response_text = responseMessages['exception']
    template_name = "qa/results.html"
    response = render(request, template_name, {'form': form,'json_resp':t, 'response_text':response_text, 'alternatives':alternative_text, 'dataDict':dataDict, 'recent':{}, 'img_source':img_source})
    response.delete_cookie('qtype')
    response.delete_cookie('fiveW')
    response.delete_cookie('col2')
    response.delete_cookie('noun')
    response.delete_cookie('last_col')
    return response
    

def ask(request):
    print request.GET.copy()
    response = HttpResponseRedirect('results')
    response.set_cookie('qtype',request.GET.get('qtype'))
    response.set_cookie('fiveW',request.GET.get('fiveW'))
    response.set_cookie('col2',request.GET.get('col2'))
    response.set_cookie('noun',request.GET.get('noun'))
    response.set_cookie('last_col',request.GET.get('last_col'))
            
    return response

def facetShow(request):
    print request.GET.copy()
    response = HttpResponseRedirect('/')
    response.set_cookie('qtype',request.GET.get('qtype'))
    response.delete_cookie('fiveW')
    response.delete_cookie('col2')
    response.set_cookie('noun',request.GET.get('noun'))
    response.delete_cookie('last_col')
            
    return response
