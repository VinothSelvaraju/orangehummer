$('.topic').click(function (e) {
  //e.preventDefault();
  //var id = $(this).attr('id');
  var id = this.id;
  $('#id_qtype').val(id);
  resetAll();
});
