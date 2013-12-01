from django.conf.urls import patterns, include, url
from django.views.generic import TemplateView
from django.contrib import admin
from qa.views import *
admin.autodiscover()

urlpatterns = patterns('',
    # Examples:
    url(r'^$', HomePageView.as_view(), name='home'),
    #url(r'^person$', PersonView.as_view(), name='person'),
    #url(r'^places$', PlacesView.as_view(), name='places'),
    #url(r'^movies$', MoviesView.as_view(), name='movies'),
    
    url(r'^meet$', TemplateView.as_view(template_name="static/meet.html")),
    url(r'^suggest/q=(.*)', 'ajax.views.suggest'),
    # url(r'^blog/', include('blog.urls')),

    url(r'^admin/', include(admin.site.urls)),
)
