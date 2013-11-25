# forms.py
from django import forms

FIVE_W_CHOICES = (("who","Who"),
                ("what","What"),
                ("when","When"),
                ("where","Where"))

COL_2_CHOICES = (("is","is"),
                ("was","was"),
                ("did","did"))

LAST_COL_CHOICES = (("born","born"),
                    ("married","married"),
                    ("spouse","spouse"),
                    ("children","children"))

NUM_CHOICES = (("first","first"),
                ("second","second"),
                ("third","third"))

QTYPE_CHOICES = (("person","person"),
                ("places","places"),
                ("movies","movies"))

class QuestionForm(forms.Form):
    qtype = forms.ChoiceField(choices=QTYPE_CHOICES, widget=forms.Select(attrs={'class':'form-control qtype'}))
    fiveW = forms.ChoiceField(choices=FIVE_W_CHOICES, widget=forms.Select(attrs={'class':'form-control'}))
    col2 = forms.ChoiceField(choices=COL_2_CHOICES, widget=forms.Select(attrs={'class':'form-control'}))
    noun = forms.CharField(max_length = 100, widget=forms.TextInput(attrs={'class':'form-control','placeholder':'Enter a Person Name'}))
    last_col = forms.ChoiceField(choices=LAST_COL_CHOICES, widget=forms.Select(attrs={'class':'form-control'}))
    num_col = forms.ChoiceField(choices=NUM_CHOICES, widget=forms.Select(attrs={'class':'form-control'}))