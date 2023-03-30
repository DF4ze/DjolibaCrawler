$(function () {
    // ACTIVATION DU DATEPICKER 
    $('.datepicker').datepicker({
        clearBtn: true,
        format: "dd/mm/yyyy"
    });
});

$('.datepicker').on('changeDate', function(ev){
    $(this).datepicker('hide');
});
