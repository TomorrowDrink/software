// $(document).ready(function(){
//     $.ajax({
//         url: '/js/data.json',
//         type: 'get',
//         data: '',
//         dataType: 'json',
//         error: function (error) {
//             console.log(error);
//             alert(error);
//         },
//         success:function (data) {
//             $("#Stu").html("");
//             var tableStr = "<table>";
//
//             tableStr = tableStr + "<thead><th>id</th><th>filename</th><th>astype</th><th>state</th><th>taskname</th></thead>"
//             for (var i = 0; i < data.length; i++) {
//                 tableStr = tableStr + "<tr><td>" + data[i].id + "</td>" +
//                     "<td>" + data[i].filename + "</td>" +
//                     "<td>" + data[i].astype + "</td>" +
//                     "<td>" + data[i].state + "</td>" +
//                     "<td>" + data[i].taskname + "</td></tr>"
//             }
//             tableStr = tableStr + "</table>";
//             $("#Stu").html(tableStr);
//         }
//     })
// }

$(function(){
    $.ajax({
        url:"http://localhost:8082/student/rtest",
        type:"get",
        success: function(data){
            var obj=eval(data);
            var tbody=$('<tbody></tbody>');
            $(obj).each(function (index){
                var val=obj[index];
                var tr=$('<tr></tr>');
                tr.append('<td>'+ val.filename + '</td>' + '<td>'+ val.astype + '</td>' +'<td>'+ val.state + '</td>' +'<td>'+ val.taskname + '</td>');
                tbody.append(tr);
            });
            $('#myRTable tbody').replaceWith(tbody);
        }
    });
});
$(function(){
    $.ajax({
        url:"http://localhost:8082/student/wtest",
        type:"get",
        success: function(data){
            var obj=eval(data);
            var tbody=$('<tbody></tbody>');
            $(obj).each(function (index){
                var val=obj[index];
                var tr=$('<tr></tr>');
                tr.append('<td>'+ val.filename + '</td>' + '<td>'+ val.astype + '</td>' +'<td>'+ val.state + '</td>' +'<td>'+ val.taskname + '</td>');
                tbody.append(tr);
            });
            $('#myWTable tbody').replaceWith(tbody);
        }
    });
});
$(function(){
    $.ajax({
        url:"http://localhost:8082/student/ftest",
        type:"get",
        success: function(data){
            var obj=eval(data);
            var tbody=$('<tbody></tbody>');
            $(obj).each(function (index){
                var val=obj[index];
                var tr=$('<tr></tr>');
                tr.append('<td>'+ val.filename + '</td>' + '<td>'+ val.astype + '</td>' +'<td>'+ val.state + '</td>' +'<td>'+ val.taskname + '</td>');
                tbody.append(tr);
            });
            $('#myFTable tbody').replaceWith(tbody);
        }
    });
});
$(function(){
    $.ajax({
        url:"http://localhost:8082/student/ktest",
        type:"get",
        success: function(data){
            var obj=eval(data);
            var tbody=$('<tbody></tbody>');
            $(obj).each(function (index){
                var val=obj[index];
                var tr=$('<tr></tr>');
                tr.append('<td>'+ val.filename + '</td>' + '<td>'+ val.astype + '</td>' +'<td>'+ val.state + '</td>' +'<td>'+ val.taskname + '</td>');
                tbody.append(tr);
            });
            $('#myKTable tbody').replaceWith(tbody);
        }
    });
});
$(function(){
    $.ajax({
        url:"http://localhost:8082/student/ztest",
        type:"get",
        success: function(data){
            var obj=eval(data);
            var tbody=$('<tbody></tbody>');
            $(obj).each(function (index){
                var val=obj[index];
                var tr=$('<tr></tr>');
                tr.append('<td>'+ val.filename + '</td>' + '<td>'+ val.astype + '</td>' +'<td>'+ val.state + '</td>' +'<td>'+ val.taskname + '</td>');
                tbody.append(tr);
            });
            $('#myZTable tbody').replaceWith(tbody);
        }
    });
});
$(function(){
    $.ajax({
        url:"http://localhost:8082/student/gtest",
        type:"get",
        success: function(data){
            var obj=eval(data);
            var tbody=$('<tbody></tbody>');
            $(obj).each(function (index){
                var val=obj[index];
                var tr=$('<tr></tr>');
                tr.append('<td>'+ val.filename + '</td>' + '<td>'+ val.astype + '</td>' +'<td>'+ val.state + '</td>' +'<td>'+ val.taskname + '</td>');
                tbody.append(tr);
            });
            $('#myGTable tbody').replaceWith(tbody);
        }
    });
});
$(function(){
    $.ajax({
        url:"http://localhost:8082/student/ltest",
        type:"get",
        success: function(data){
            var obj=eval(data);
            var tbody=$('<tbody></tbody>');
            $(obj).each(function (index){
                var val=obj[index];
                var tr=$('<tr></tr>');
                tr.append('<td>'+ val.filename + '</td>' + '<td>'+ val.astype + '</td>' +'<td>'+ val.state + '</td>' +'<td>'+ val.taskname + '</td>');
                tbody.append(tr);
            });
            $('#myLTable tbody').replaceWith(tbody);
        }
    });
});