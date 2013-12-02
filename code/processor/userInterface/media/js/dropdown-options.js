  var col2_options ={'person': {'who':  {"Select--": "",
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
                    },
                    'film': {'who':  {"Select--": "",
                            "is": "is",
                            "are": "are",
                            "was": "was",
                            "were": "were"
                            },
                    'where': {"Select--": "",
                              "was": "was",
                              },
                    'when': {"Select--": "",
                             "was": "was",
                             "did": "did"
                             },
                    'what': {'Select--': '',
                             'is': 'is',
                             'was': 'was',
                             },
                    '':{'Please select in order': ''}
                    },
                    'places': {'who':  {"Select--": "",
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
                    },
                };

   var last_col_options ={'person': {'who':  {'': {'Please select in order': ''},
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
                                     'is': {'Select--':'',
                                            'known-for':'knownfor',
                                            'birth-place':'birthplace',
                                            'birth-name':'birthname',
                                            'almamater':'almamater',
                                            'salary': 'salary',
                                            'ethnicity': 'ethnicity',
                                            'caption': 'caption',
                                            'nationality': 'nationality',
                                            'height': 'height',
                                            'occupation': 'occupation',
                                            'active-years': 'yearsactive',
                                            'weight': 'weight', 
                                            'citizenship': 'citizenship',
                                            'fame':'knownfor',
                                            'website':'website',
                                            'education':'education',
                                            'title':'title',
                                            'nickname':'othernames',
                                            'networth':'networth'
                                            },
                                     'was': {'Select--':'',
                                             'birth-place':'birthplace',
                                            'title':'title',
                                            'nickname':'othernames',
                                             'known-for':'knownfor',
                                            'education':'education',
                                             'almamater':'almamater',
                                            'salary': 'salary',
                                            'ethnicity': 'ethnicity',
                                            'nationality': 'nationality',
                                            'height': 'height',
                                            'occupation': 'occupation',
                                            'active-years': 'yearsactive',
                                            'weight': 'weight', 
                                            'citizenship': 'citizenship',
                                            'fame':'knownfor',
                                            'networth':'networth',
                                             },
                                     'are': {'other-names':'othernames',
                                             'Select--':'', 
                                             'awards':'awards'
                                             }
                                     }
                            },
                            'places': {'who':  {'': {'Please select in order': ''},
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
                                            'birth-name':'birthname',
                                            'almamater':'almamater',
                                            'salary': 'salary',
                                            'ethnicity': 'ethnicity',
                                            'caption': 'caption',
                                            'nationality': 'nationality',
                                            'height': 'height',
                                            'occupation': 'occupation',
                                            'active-years': 'yearsactive',
                                            'weight': 'weight', 
                                            'citizenship': 'citizenship',
                                            'fame':'knownfor',
                                            'website':'website',
                                            'networth':'networth'
                                            },
                                     'was': {'birth-place':'birthplace',
                                             'known-for':'knownfor',
                                             'almamater':'almamater',
                                            'salary': 'salary',
                                            'ethnicity': 'ethnicity',
                                            'nationality': 'nationality',
                                            'height': 'height',
                                            'occupation': 'occupation',
                                            'active-years': 'yearsactive',
                                            'weight': 'weight', 
                                            'citizenship': 'citizenship',
                                            'fame':'knownfor',
                                            'networth':'networth',
                                            'website':'website',
                                            
                                             'Select--':''
                                             },
                                     'are': {'nicknames':'othernames',
                                             'Select--':'', 
                                             'awards':'awards'
                                             }
                                     }
                            },
                            'film': {'who':  {'': {'Please select in order': ''},
                                                'is': {'producer': 'producer',
                                                       'director': 'director',
                                                       'writer': 'writer',
                                                       'script-writer': 'scriptwriter',
                                                       'editor': 'editor',
                                                       'distributor': 'distributor',
                                                       'film-editor': 'filmeditor',
                                                       'Select--': ''
                                                      },
                                                'are': {
                                                       'producers': 'producer',
                                                       'directors': 'director',
                                                       'writers': 'writer',
                                                       'script-writers': 'scriptwriter',
                                                       'editors': 'editor',
                                                       'distributors': 'distributor',
                                                       'film-editors': 'filmeditor',
                                                       'Select--': ''

                                                       },
                                                "was": {
                                                       'producer': 'producer',
                                                       'director': 'director',
                                                       'writer': 'writer',
                                                       'script-writer': 'scriptwriter',
                                                       'editor': 'editor',
                                                       'distributor': 'distributor',
                                                       'film-editor': 'filmeditor',
                                                       'Select--': ''
                                                       }
                                                },
                                        'where': {'': {'Please select in order': ''},
                                                   "was": {'shot':'shot',
                                                           'studio':'studio',
                                                           'Select--':''
                                                           },
                                                    },
                           
                                         'when': {'': {'Please select in order': ''},
                                                  'was': {"Select--": "",
                                                          "shot":"shot",
                                                          "directed":"directed",
                                                          "completed":"completed",
                                                          "released":"released",
                                                         },
                                                  'did': {"Select--":"",
                                                          "release":"release"
                                                         }
                                                 },
                    
                                          'what': {'': {'Please select in order': ''},
                                                   'is': {'Select--':'',
                                                          'running-time':'runningtime',
                                                          'language':'language',
                                                          'budget':'budget',
                                                          'revenue':'revenue',
                                                          'cost':'cost',
                                                          'box-office-return':'boxofficereturn',
                                                          },
                                                   'was': {'Select--':'',
                                                          'running-time':'runningtime',
                                                          'language':'language',
                                                          'budget':'budget',
                                                          'revenue':'revenue',
                                                          'cost':'cost',
                                                          'box-office-return':'boxofficereturn',
                                                          
                                                           },
                                                   'are': {'Select--':'', 
                                                          'languages':'language',
                                                           }
                                                   }
                                          }
                                      };
 
