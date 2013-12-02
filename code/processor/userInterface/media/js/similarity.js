$(document).ready(function(){
   var qtype = $('#id_qtype').val();
   var fiveW = $('#id_fiveW').val();
   var col2 = $('#id_col2').val();
   var noun = $('#id_noun').val();
   var last_col = $('#id_last_col').val();
//Make AJAX Call for Vertical Similarity
    $.ajax({
        type: 'get',
        url: 'vertSim',
        data: 'qtype='+qtype+'&fiveW='+fiveW+'&col2='+col2+'&noun='+noun+'&last_col='+last_col,
        success: function(data){
                    console.log("SUCCESS");
                    $('.vertical').html(data);
                    $('.vertical').show();
                    },
        error: function(data){
                    console.log(data);
                    console.log("EXCEPTION");
                }
        });
    console.log("AJAX Done!!");
     $.ajax({
        type: 'get',
        url: 'horizSim',
        data: 'qtype='+qtype+'&fiveW='+fiveW+'&col2='+col2+'&noun='+noun+'&last_col='+last_col,
        success: function(data){
                    console.log("SUCCESS");
                    $('.horizontal').html(data);
                    $('.horizontal').show();
                    },
        error: function(data){
                    console.log(data);
                    console.log("EXCEPTION");
                }
        });

});
