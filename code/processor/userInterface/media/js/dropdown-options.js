$(document).ready(function(){
  alert($('#id_col2').val());

  $('#id_col2').empty();
  $('#id_last_col').empty();
var this_col2_options = {'Please select in order': ''};
    $.each(this_col2_options, function(key, value) {
      $('#id_col2').append($("<option></option>")
           .attr("value", value).text(key));
           });
      $('#id_col2').val('');
     var last_col_options = {'Please select in order':''};
     $.each(last_col_options, function(key, value) {
       $('#id_last_col').append($('<option></option>')
           .attr('value', value).text(key));
        });
      $('#id_last_col').val('');
});
$('#id_fiveW').change(function(e){
  $('#id_col2').empty();
  $('#id_last_col').empty();
  var fiveW_val = $('#id_fiveW').val();
  //alert(fiveW_val);

  var col2_options ={'who':  {"Select--": "",
                            "is": "is",
                            "are": "are",
                            "was": "was"
                            },
                    'where': {"Select--": "",
                              "does": "does",
                              "was": "was",
                              "is": "is"
                              },
                    'when': {"Select--": "",
                             "was": "was",
                             "did": "did"
                             },
                    'what': {'Select--': '',
                             'is': 'is',
                             'was': 'was',
                             'are': 'are'
                             },
                    '':{'Please select in order': ''}
                    };
    var this_col2_options = col2_options[fiveW_val];
    $.each(this_col2_options, function(key, value) {
      $('#id_col2').append($("<option></option>")
           .attr("value", value).text(key));
           });
      $('#id_col2').val('');
     var last_col_options = {'Please select in order':''}
     $.each(last_col_options, function(key, value) {
       $('#id_last_col').append($('<option></option>')
           .attr('value', value).text(key));
        });
      $('#id_last_col').val('');
});
$('#id_col2').change(function(e){
  $('#id_last_col').empty();
  var col2_val = $('#id_col2').val();
  var fiveW_val = $('#id_fiveW').val();
  //alert(fiveW_val);

  var last_col_options ={'who':  {'': {'Please select in order': ''},
                                  'is': {'spouse': 'spouse',
                                         'partner': 'partner',
                                         'employer': 'employer',
                                         'Select--': ''
                                        },
                                  'are': {'family':'family',
                                          'children':'children',
                                         'parents': 'parents',
                                         'Select--':''},
                                  "was": {'spouse': 'spouse',
                                          'Select--':''}
                                  },
                          'where': {'': {'Please select in order': ''},
                                    "does": {'work':'work',
                                             'live':'live',
                                             'Select--':''
                                            },
                                    "was": {'born':'born',
                                            'Select--':''
                                           },
                                    "is": {'from':'form',
                                           'residence':'residence',
                                           'Select--':''
                                          }
                                    },
                           
                           'when': {'': {'Please select in order': ''},
                                    'was': {"Select--": "",
                                            "born": "born"
                                           },
                                    'did': {"Select--":"",
                                            "die":"die"
                                           }
                                   },
                    
                            'what': {'': {'Please select in order': ''},
                                     'is': {'known-for':'knownfor',
                                            'birth-place':'birthplace',
                                            'almamater':'almamater',
                                            'salary': 'salary',
                                            'ethnicity': 'ethnicity',
                                            'nationality': 'nationality',
                                            'height': 'height',
                                            'weight': 'weight', 
                                            'citizenship': 'citizenship',
                                            'fame':'knownfor',
                                            'networth':'networth'
                                            },
                                     'was': {'birth-place':'birthplace',
                                             'Select--':''
                                             },
                                     'are': {'nicknames':'nicknames',
                                             'Select--':'', 
                                             'awards':'awards'
                                             }
                                     }
                            };
    var this_col_options = last_col_options[fiveW_val][col2_val];
    $.each(this_col_options, function(key, value) {
      $('#id_last_col').append($("<option></option>")
           .attr("value", value).text(key));
           });
      $('#id_last_col').val('');
});


