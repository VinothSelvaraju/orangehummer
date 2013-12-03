from django.conf.urls import patterns, include, url
from django.views.generic import TemplateView
from django.contrib import admin
from qa.views import *
admin.autodiscover()

urlpatterns = patterns('',
    # Examples:
    url(r'^$', HomePageView.as_view(), name='home'),
    url(r'^meet$', TemplateView.as_view(template_name="static/meet.html")),
    url(r'^howitworks.html$', TemplateView.as_view(template_name="static/howitworks.html")),
    url(r'^facet$', TemplateView.as_view(template_name="qa/facet.html")),
    url(r'^facetList', 'ajax.views.facetList'),
    url(r'^facetExpansion', 'ajax.views.facetExpansion'),
    url(r'^vertSim', 'ajax.views.verticalSimilarity'),
    url(r'^horizSim', 'ajax.views.horizontalSimilarity'),
    url(r'^suggest/q=(.*)', 'ajax.views.suggest'),
    url(r'^results$', 'qa.views.resultsPage'),
    url(r'^ask', 'qa.views.ask'),
    url(r'^facetShow', 'qa.views.facetShow'),
    # url(r'^blog/', include('blog.urls')),

    url(r'^admin/', include(admin.site.urls)),
)
