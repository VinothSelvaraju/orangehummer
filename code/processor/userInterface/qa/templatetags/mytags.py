from django.template import Library

register = Library()

@register.filter
def is_false(arg):
    if arg:
        return False
    else:
        return True
