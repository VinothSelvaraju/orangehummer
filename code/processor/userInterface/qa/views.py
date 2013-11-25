# Create your views here.
from django.shortcuts import render_to_response, render
from django.http import HttpResponse, HttpResponseRedirect
from qa.forms import QuestionForm
from django.views.generic.edit import FormView

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
            #redirect
            # <process form cleaned data>
            self.template_name = "qa/results.html"
            return render(request, self.template_name, {'form': form,'json_resp':"JSON RESPONSE"})
        else:
            print "Invalid"
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
