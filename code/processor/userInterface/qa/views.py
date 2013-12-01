# Create your views here.
from django.shortcuts import render_to_response, render
from django.http import HttpResponse, HttpResponseRedirect
from qa.forms import QuestionForm
from django.views.generic.edit import FormView
import os
import json
import time
import traceback

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
            #call Java code
            #read from response
            #print os.getcwd()
            #print request.POST.get("qtype")
            #print request.POST.get("fiveW")
            #print request.POST.get("col2")
            #print request.POST.get("last_col")
            #print request.POST.get("noun")
            #print request.POST.get("num_col")
            #os.chdir("../../")
            try: 
                os.system("java -cp ../../processor/queryProcessor/bin/:../../solr/dist/:../../solr/dist/solrj-lib/ QueryMapper docs/ "+request.POST.get('qtype')+" "+request.POST.get('fiveW')+" "+request.POST.get('col2')+" "+request.POST.get('noun')+" "+request.POST.get('last_col'))
                #redirect
                with open("docs/queryOutput.json") as f:
                    t = f.read()
                # <process form cleaned data>
                resp_data = json.loads(t)
                print resp_data
                if int(resp_data['response']['numFound'])<=0:
                    response_text = "Sorry, no results were found for your query!"
                else:
                    response_text = ""
                    for i in range(int(resp_data['response']['numFound'])):
                        keys = resp_data['response']['docs'][i].keys()
                        print keys
                        if len(keys)!=3:
                            raise Exception
                        keys.remove('name')
                        
                        if "highlighting" in resp_data:
                            response_text += ", ".join(resp_data['highlighting'][resp_data['response']['docs'][i]['id']]['name'])
                        else: 
                            response_text += "<strong>"+ resp_data['response']['docs'][i]['name'] +"</strong>"

                        keys.remove('id')
                        response_text += "'s " + keys[0] + " is "
                        if keys[0]=="birthdate":
                            resp_data['response']['docs'][i][keys[0]]= time.strftime("%d %B %Y", time.strptime(resp_data['response']['docs'][i][keys[0]], "%Y-%m-%dT%H:%M:%SZ"))
                        if isinstance(resp_data['response']['docs'][i][keys[0]], str):
                            response_text += resp_data['response']['docs'][i][keys[0]]
                        elif isinstance(resp_data['response']['docs'][i][keys[0]], list):
                            response_text+=", ".join(resp_data['response']['docs'][i][keys[0]])
                        response_text+="<br/>"
                            
                print response_text
            except:
                print "There was an exception"
                print traceback.format_exc()
                response_text = "Oops. There was an exception"


            self.template_name = "qa/results.html"
            return render(request, self.template_name, {'form': form,'json_resp':t, 'response_text':response_text})
        #else:
            #print "Invalid"
        print form.errors
        return render(request, self.template_name, {'form': form})

"""
#Ignore the following
    def form_valid(self, form):
        # This method is called when valid form data has been POSTed.
        # It should return an HttpResponse.
        print "VALID"
        return super(HomePageView, self).form_valid(form)
"""

"""class PersonView(FormView):
    template_name = 'qa/home.html'
    form_class = QuestionForm
    success_url = '/thanks/'

    def get(self, request, *args, **kwargs):
        return HttpResponseRedirect("/")

    def form_invalid(self, form):
        print "INVALID"
        print form.errors
        return super(PersonView, self).form_valid(form)

    def form_valid(self, form):
        # This method is called when valid form data has been POSTed.
        # It should return an HttpResponse.
        print "POST PERSON"
        return super(PersonView, self).form_valid(form)

class PlacesView(FormView):
    template_name = 'qa/home.html'
    form_class = QuestionForm
    success_url = '/thanks/'

    def form_valid(self, form):
        # This method is called when valid form data has been POSTed.
        # It should return an HttpResponse.
        print "POST PLACES"
        return super(PlacesView, self).form_valid(form)

class MoviesView(FormView):
    template_name = 'qa/home.html'
    form_class = QuestionForm
    success_url = '/thanks/'

    def form_valid(self, form):
        # This method is called when valid form data has been POSTed.
        # It should return an HttpResponse.
        print "POST MOVIES"
        return super(MoviesView, self).form_valid(form)

"""
#def homepage(request):
#   return render_to_response("qa/home.html")
