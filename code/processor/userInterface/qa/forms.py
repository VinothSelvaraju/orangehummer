# forms.py
from django import forms

FIVE_W_CHOICES = (("","Select--"),
                ("who","Who"),
                ("what","What"),
                ("when","When"),
                ("where","Where"))

COL_2_CHOICES = (("","Please select in order"),
                ("is","is"),
                ("does","does"),
                ("was","was"),
                ("are","are"),
                ("did","did"))

LAST_COL_CHOICES = (("","Please select in order"),
                    ("born","born"),
                    ("spouse","spouse"),
                    ("partner","partner"),
                    ("employer","employer"),
                    ("family","family"),
                    ("children","children"),
                    ("parents","parents"),
                    ("work","work"),
                    ("live","live"),
                    ("from","from"),
                    ("residence","residence"),
                    ("die","die"),
                    ("knownfor","known-for"),
                    ("birthplace","birth-place"),
                    ("almamater","almamater"),
                    ("ethnicity","ethnicity"),
                    ("nationality","nationality"),
                    ("height","height"),
                    ("weight","weight"),
                    ("citizenship","citizenship"),
                    ("networth","networth"),
                    ("knownfor","fame"),
                    ("nicknames","nicknames"),
                    ("awards","awards"),
                    ("salary","salary"))

NUM_CHOICES = (("first","first"),
                ("second","second"),
                ("third","third"))

QTYPE_CHOICES = (("person","Person"),
                ("places","Places"),
                ("movies","Movies"))

select_box_errors = {'required':'Please select a value',
                     'invalid':'Please select a value'}

input_box_errors = {'required':'Please enter a value',
                    'invalid':'Invalid value'}

class QuestionForm(forms.Form):
    qtype = forms.ChoiceField(choices=QTYPE_CHOICES, 
                              widget=forms.Select(attrs={'class':'form-control qtype'}),
                              error_messages = select_box_errors)
    fiveW = forms.ChoiceField(choices=FIVE_W_CHOICES, 
                              widget=forms.Select(attrs={'class':'form-control'}),
                              error_messages = select_box_errors)
    col2 = forms.ChoiceField(choices=COL_2_CHOICES, 
                             widget=forms.Select(attrs={'class':'form-control'}),
                             error_messages = select_box_errors)
    noun = forms.CharField(max_length = 100, 
                           widget=forms.TextInput(attrs={'class':'form-control',
                                                         'placeholder':'Enter a Person Name', 
                                                         'data-provide':"typeahead", 
                                                         "autocomplete":"off"}),
                           error_messages = input_box_errors)
    last_col = forms.ChoiceField(choices=LAST_COL_CHOICES, 
                                 widget=forms.Select(attrs={'class':'form-control'}),
                                 error_messages = select_box_errors)
    num_col = forms.ChoiceField(choices=NUM_CHOICES, 
                                widget=forms.Select(attrs={'class':'form-control hid'}),
                                error_messages = select_box_errors)

