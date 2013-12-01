# Create your views here.
from django.shortcuts import render_to_response, render
from django.http import HttpResponse, HttpResponseRedirect
from qa.forms import QuestionForm
from django.views.generic.edit import FormView
import os
import json
import time
import traceback
from config import *

class HomePageView(FormView):
    template_name = 'qa/home.html'
    form_class = QuestionForm
    success_url = '/thanks/'

    def get(self, request, *args, **kwargs):
        form = self.form_class()
        return render(request, self.template_name, {'form': form})

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
        return render(request, self.template_name, {'form': form})


def resultsPage(request):
    dataDict = {'qtype': request.COOKIES.get('qtype'),
                'fiveW' : request.COOKIES.get('fiveW'),
                'col2' : request.COOKIES.get('col2'),
                'noun' : request.COOKIES.get('noun'),
                'last_col' : request.COOKIES.get('last_col'),}

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
            response_text = responseMessages['noresults']
            alternative_text = resp_data['spellcheck']['suggestions'][1]['suggestion'] if resp_data['spellcheck']['suggestions'] else []
        else:
            response_text = ""
            for i in range(int(resp_data['response']['numFound'])):
                keys = resp_data['response']['docs'][i].keys()
                print keys
                if len(keys)!=3:
                    if len(keys)<3:
                        #response_text = responseMessages['noresults']
                        #break
                        continue
                    else: 
                        raise Exception
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
    return render(request, template_name, {'form': form,'json_resp':t, 'response_text':response_text, 'alternatives':alternative_text, 'dataDict':dataDict})
    

def ask(request):
    print request.GET.copy()
    response = HttpResponseRedirect('results')
    response.set_cookie('qtype',request.GET.get('qtype'))
    response.set_cookie('fiveW',request.GET.get('fiveW'))
    response.set_cookie('col2',request.GET.get('col2'))
    response.set_cookie('noun',request.GET.get('noun'))
    response.set_cookie('last_col',request.GET.get('last_col'))
            
    return response
