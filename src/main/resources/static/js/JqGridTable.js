 $(document).ready(function(){
        $("#jqTable").jqGrid({
            /* url:'/WEB-INF/PaperInfo.json', */
            /* datatype:"json", */
            datatype:"local",
            colNames:["编号","课题名称","姓名","状态","编辑"],
            colModel:[
                {name:'id',index:'id',label:'编号',width:60,hidden:true,},
                {name:'taskname',index:'taskname',label:'课题名称',width:500,align:'center'},
                {name:'stuname',index:'stuname',label:'姓名',width:120,align:'center'},
                {name:'state',index:'state',label:'状态',width:150,align:'center'},
                {name:'edit',index:'edit',label:'编辑',width:120, align:'center',formatter:'showlink', formatoptions:{baseLinkUrl:'https://www.baidu.com/', addParam: '&action=edit'}
                }],
            sortname:"id",sortorder:"desc",viewrecords:true,rownumbers:true,autowidth:true,
            jsonReader:{
                repeatitems : false
            }, pager:"#pager",
        }).navGrid('#gridPager',{edit:false,add:false,del:false});;
        /* 		for (var i=0;i<rows.length;i++)
                    jQuery("#jqTable").jqGrid('addRowData',i+1,rows[i]); */
        var rows = [{
            "id":"1","taskname":"毕业设计管理系统","stuname":"张三","state":"已通过","edit":"编辑"
        },{
            "id":"2","taskname":"教学管理日历","stuname":"李四","state":"待评阅","edit":"编辑"
        },{
            "id":"3","taskname":"教学管理日历","stuname":"王五","state":"未通过","edit":"编辑"
        },{
            "id":"4","taskname":"毕业设计管理系统","stuname":"李华","state":"待评阅","edit":"编辑"
        }];
        for (var i=0;i<rows.length;i++)
            jQuery("#jqTable").jqGrid('addRowData',i+1,rows[i]);
    });
/* 	给单元格添加链接
	function processGridValue(){
	    var cell;
	    var array=gridTable.getDataIDs();
	    for ( var i = 0; i < array.length; i++) {
	        var rowarray=jqTable.getRowData(array[i])
	        for ( var rowname in rowarray) {
	            if(rowname.indexOf("编辑")>-1){
	                cell=jqTable.getCell(i+1,rowname);
	                jqTable.setCell(i+1,rowname,'<a href="#">编辑'+cell+'</a>');
	            }
	        }
	    }
	} */
