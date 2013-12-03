$('input#id_noun').typeahead({
  name: 'noun',
//  local: ['timtrueman', 'JakeHarding', 'vskarich']
  //valueKey: "a.spellcheck.suggestions[1].suggestion",
  remote: {
    //url: "http://localhost:8983/solr/QACollection/suggest?q=name%3A%QUERY&wt=json&indent=true&json.wrf=callback",
    url: "/suggest/q=%QUERY/qtype="+$('#id_qtype').val(),
    dataType: "json",
    filter: function(data){
      //alert("Filtering results");
//      var new_data = JSON.parse(data);
      //alert(new_data);
      var resultList = data.spellcheck.suggestions[1].suggestion;
      //alert(resultList);
      return resultList;
    }
  }
  
});

