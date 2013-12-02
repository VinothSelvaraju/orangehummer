//Works under the assumption that dropdown-options.js is called before this file
var resetAll = function(){
         $('#id_fiveW').val('');
         var this_col2_options = {'Please select in order': ''};
        $.each(this_col2_options, function(key, value) {
          $('#id_col2').append($("<option></option>")
               .attr("value", value).text(key));
               });
          $('#id_col2').val('');
         var this_col_options = {'Please select in order':''};
         $.each(this_col_options, function(key, value) {
           $('#id_last_col').append($('<option></option>')
               .attr('value', value).text(key));
            });
          $('#id_last_col').val(''); 
}
$(document).ready(function(){
  var fiveW_onload =$('#id_fiveW').val();
  var col2_onload =$('#id_col2').val();
  var last_col_onload =$('#id_last_col').val();

  $('#id_col2').empty();
  $('#id_last_col').empty();
  if(fiveW_onload==''){ 
      var this_col2_options = {'Please select in order': ''};
        $.each(this_col2_options, function(key, value) {
          $('#id_col2').append($("<option></option>")
               .attr("value", value).text(key));
               });
          $('#id_col2').val('');
         var this_col_options = {'Please select in order':''};
         $.each(this_col_options, function(key, value) {
           $('#id_last_col').append($('<option></option>')
               .attr('value', value).text(key));
            });
          $('#id_last_col').val('');
    }
    else {
       var this_col2_options = col2_options[fiveW_onload];
        $.each(this_col2_options, function(key, value) {
          $('#id_col2').append($("<option></option>")
               .attr("value", value).text(key));
               });
          $('#id_col2').val(col2_onload);
         var this_col_options = last_col_options[fiveW_onload][col2_onload];
         $.each(this_col_options, function(key, value) {
           $('#id_last_col').append($('<option></option>')
               .attr('value', value).text(key));
            });
          $('#id_last_col').val(last_col_onload);

    }
});
$('#id_fiveW').change(function(e){
  $('#id_col2').empty();
  $('#id_last_col').empty();
  var fiveW_val = $('#id_fiveW').val();
  //alert(fiveW_val);

    var this_col2_options = col2_options[fiveW_val];
    $.each(this_col2_options, function(key, value) {
      $('#id_col2').append($("<option></option>")
           .attr("value", value).text(key));
           });
      $('#id_col2').val('');
     var this_col_options = {'Please select in order':''}
     $.each(this_col_options, function(key, value) {
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

    var this_col_options = last_col_options[fiveW_val][col2_val];
    $.each(this_col_options, function(key, value) {
      $('#id_last_col').append($("<option></option>")
           .attr("value", value).text(key));
           });
      $('#id_last_col').val('');
});


