
/*
 * GET home page.
 */
var mysql = require('mysql');
var conn = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database:'mydb',
    port: 3306
});


exports.index = function(req, res){
  res.render('index', { title: 'Express' });
};

//登陆验证
exports.login=function(req,res){

 	var userName=req.body.username;
	var passWord=req.body.password;
	var Type=req.body.type;
	console.log("Username:"+userName+"      Password:"+passWord+"      Type:"+Type+"" );
 //访问数据库
    conn=mysql.createConnection(conn.config);  
    conn.connect();
	if(Type=='1'){
		conn.query("SELECT * from user1 where username='"+userName+"'and password='"+passWord+"'", function(err, rows, fields) {
   if(err)throw err; 
	if(rows[0]==undefined)
	{
     res.header('Content-Type', 'text/plain');
	 res.send(JSON.stringify({ result:'none' }));
	
	 res.end();
	}
	else
	{
	 console.log(rows[0]);
	res.header('Content-Type', 'text/plain');
	 res.send(JSON.stringify({ result:'OK',type:'1' }));
	 res.end();
	}
   });
		}else{
			conn.query("SELECT * from user2 where username='"+userName+"'and password='"+passWord+"'", function(err, rows, fields) {
   if(err)throw err; 
	if(rows[0]==undefined)
	{
     res.header('Content-Type', 'text/plain');
	 res.send(JSON.stringify({ result:'none' }));
	 res.end();
	}
	else
	{
	 console.log(rows[0]);
	 res.header('Content-Type', 'text/plain');
	 res.send(JSON.stringify({ result:'OK' ,type:'2'}));
	 res.end();
	}
   });
			};
	
   conn.end(); 

};

//注册
exports.insert = function(req, res){
	var username = req.body.username;
	var password = req.body.password;
	var name = req.body.name;
	console.log("Username:"+username+"      Password:"+password+"      Name:"+name+"" );
	
	conn=mysql.createConnection(conn.config);  
    conn.connect();
	
	 conn.query("SELECT * from user1 where username='"+username+"'", function(err, rows, fields) {
        if (err) throw err;
        //console.log('The solution is: ', rows[0].solution);
	    console.log("查询结果------->"+rows[0]);
		if(rows[0] == undefined){
			  conn.query("insert into user1 (username,password,name) value ('"+username+"','"+password+"','"+name+"')", function(err, rows, fields) {
              if (err) {
				  
	             //res.writeHead(200, {'Content-Type': 'text/plain'}); 
				 console.log(err);
	             res.header('Content-Type', 'text/plain');
				 res.send(JSON.stringify({ result:'fail' }));
	             res.end();
			  }else{
				  console.log("sucess");
	             //res.writeHead(200, {'Content-Type': 'text/plain'}); 
	             //res.write(JSON.stringify("Sucess"));
				 res.header('Content-Type', 'text/plain');
				 res.send(JSON.stringify({ result:'OK' }));
	             res.end();
			  }
              //console.log('The solution is: ', rows[0].solution);
	           
   });
		}else{
			//res.writeHead(200, {'Content-Type': 'text/plain'}); 
			 console.log("Message : This User is existe");
			 res.header('Content-Type', 'text/plain');
	 		 res.send(JSON.stringify({ result:'existe' }));
	         
	         res.end();
		}
	   // res.writeHead(200, {'Content-Type': 'text/plain'}); 
	   // res.write(JSON.stringify(rows));
	    //res.end();
   });
	
};
//业务定制
exports.busin=function(req,res){
	var username = req.body.username;
	var room1 = req.body.room1;//大堂
	var room2 = req.body.room2;//房间
	var start_time = req.body.start_time;
	var over_time = req.body.over_time;
	
	console.log(username+" "+room1+" "+room2+" "+start_time+" "+over_time);
	
	conn=mysql.createConnection(conn.config);  
    conn.connect();
	conn.query("insert into busin (username,room1,room2,start_time,over_time) value ('"+username+"','"+room1+"','"+room2+"','"+start_time+"','"+over_time+"')", function(err, rows, fields) {
              if (err) {
				  
	             //res.writeHead(200, {'Content-Type': 'text/plain'}); 
				 console.log(err);
				 res.header('Content-Type', 'text/plain');
	 			 res.send(JSON.stringify({ result:'fail' }));
	              res.end();
			  }else{
				  console.log("sucess");
	             //res.writeHead(200, {'Content-Type': 'text/plain'}); 
	             //res.write(JSON.stringify("Sucess"));
				  res.header('Content-Type', 'text/plain');
	 			 res.send(JSON.stringify({ result:'OK' }));
	              res.end();
			  }
              //console.log('The solution is: ', rows[0].solution);
	           
   });

};
	
//统计功能
exports.statis1=function(req,res){
	var userName=req.body.username;
	
	console.log(userName);

	
	conn=mysql.createConnection(conn.config);  
    conn.connect();
    //conn.query("SELECT phone from usertable where username='"+userName+"'", function(err, rows, fields) {
	conn.query("SELECT count(*) from reserve where companyname='"+userName+"' and type='"+1+"'", function(err, rows, fields) {
    if (err) throw err;
     //console.log('The solution is: ', rows[0].solution);
	 console.log(rows[0]);
	 res.writeHead(200, {'Content-Type': 'text/plain'}); 
	// res.write(rows[0].password);
	//把变量转换为JSON字符串
	 res.write(JSON.stringify(rows));
	 
	 res.end();
   });
   conn.end(); 
	
	};
	
	
exports.statis2=function(req,res){
	var userName=req.body.username;
	
	console.log(userName);

	
	conn=mysql.createConnection(conn.config);  
    conn.connect();
    //conn.query("SELECT phone from usertable where username='"+userName+"'", function(err, rows, fields) {
	conn.query("SELECT count(*) from reserve where companyname='"+userName+"' and type='"+2+"'", function(err, rows, fields) {
    if (err) throw err;
     //console.log('The solution is: ', rows[0].solution);
	 console.log(rows[0]);
	 res.writeHead(200, {'Content-Type': 'text/plain'}); 
	// res.write(rows[0].password);
	//把变量转换为JSON字符串
	 res.write(JSON.stringify(rows));
	 
	 res.end();
   });
   conn.end(); 
	
	}
//获得已预约列表
exports.getlist=function(req,res){
	var userName=req.body.username;
	
	console.log(userName);

	
	conn=mysql.createConnection(conn.config);  
    conn.connect();
    //conn.query("SELECT phone from usertable where username='"+userName+"'", function(err, rows, fields) {
	conn.query("SELECT * from reserve where companyname='"+userName+"'", function(err, rows, fields) {
    if (err) throw err;
     //console.log('The solution is: ', rows[0].solution);
	 console.log(rows[0]);
	 res.writeHead(200, {'Content-Type': 'text/plain'}); 
	// res.write(rows[0].password);
	//把变量转换为JSON字符串
	 res.write(JSON.stringify(rows));
	 console.log(JSON.stringify(rows));
	 res.end();
   });
   conn.end(); 
	};
	
//可预约列表
exports.list=function(req,res){
	conn=mysql.createConnection(conn.config);  
    conn.connect();
    //conn.query("SELECT phone from usertable where username='"+userName+"'", function(err, rows, fields) {
	conn.query("SELECT * from user2", function(err, rows, fields) {
    if (err) throw err;
     //console.log('The solution is: ', rows[0].solution);
	 console.log(rows[0]);
	 res.writeHead(200, {'Content-Type': 'text/plain'}); 
	// res.write(rows[0].password);
	//把变量转换为JSON字符串
	 res.write(JSON.stringify(rows));
	 console.log(JSON.stringify(rows));
	 res.end();
   });
   conn.end(); 
	
	};
	
	
exports.getname=function(req,res){
	var userName=req.body.username;
	conn=mysql.createConnection(conn.config);  
    conn.connect();
    //conn.query("SELECT phone from usertable where username='"+userName+"'", function(err, rows, fields) {
	conn.query("SELECT name from user2 where username='"+userName+"'", function(err, rows, fields) {
    if (err) throw err;
     //console.log('The solution is: ', rows[0].solution);
	 console.log(rows[0]);
	 res.writeHead(200, {'Content-Type': 'text/plain'}); 
	// res.write(rows[0].password);
	//把变量转换为JSON字符串
	 res.write(JSON.stringify(rows));
	 console.log(JSON.stringify(rows));
	 res.end();
   });
   conn.end(); 
	
	};
	
exports.num=function(req,res){
	var userName=req.body.username;
	conn=mysql.createConnection(conn.config);  
    conn.connect();
    //conn.query("SELECT phone from usertable where username='"+userName+"'", function(err, rows, fields) {
	conn.query("SELECT num from reserve where companyname='"+userName+"'", function(err, rows, fields) {
    if (err) throw err;
     //console.log('The solution is: ', rows[0].solution);
	 console.log(rows[0]);
	 res.writeHead(200, {'Content-Type': 'text/plain'}); 
	// res.write(rows[0].password);
	//把变量转换为JSON字符串
	 res.write(JSON.stringify(rows));
	 console.log(JSON.stringify(rows));
	 res.end();
   });
   conn.end(); 
	
	};

//搜索
exports.seek=function(req,res){
	var Name=req.body.name;
	
	console.log(Name);
	conn=mysql.createConnection(conn.config);  
    conn.connect();
    //conn.query("SELECT phone from usertable where username='"+userName+"'", function(err, rows, fields) {
	conn.query("SELECT * from user2 where name like'%"+Name+"%'", function(err, rows, fields) {
    if (err) throw err;
     //console.log('The solution is: ', rows[0].solution);
	 console.log(rows[0]);
	 res.writeHead(200, {'Content-Type': 'text/plain'}); 
	// res.write(rows[0].password);
	//把变量转换为JSON字符串
	 res.write(JSON.stringify(rows));
	 console.log(JSON.stringify(rows));
	 res.end();
   });
   conn.end(); 
	};
	


exports.getnum=function(req,res){
	var userName=req.body.username;
	
	console.log(userName);

	
	conn=mysql.createConnection(conn.config);  
    conn.connect();
    //conn.query("SELECT phone from usertable where username='"+userName+"'", function(err, rows, fields) {
	conn.query("SELECT count(*) from reserve where companyname='"+userName+"'", function(err, rows, fields) {
    if (err) throw err;
     //console.log('The solution is: ', rows[0].solution);
	 console.log(rows[0]);
	 res.writeHead(200, {'Content-Type': 'text/plain'}); 
	// res.write(rows[0].password);
	//把变量转换为JSON字符串
	 res.write(JSON.stringify(rows));
	 
	 res.end();
   });
   conn.end(); 
	
	}
//大堂预约
exports.reserve1=function(req,res){
	var personalname = req.body.personalname;
	var companyname = req.body.companyname;
	var num=req.body.num;
	
	console.log(personalname+" "+companyname+" "+num);
	
	conn=mysql.createConnection(conn.config);  
    conn.connect();
	
	conn.query("SELECT * from reserve where personalname='"+personalname+"' and companyname='"+companyname+"'", function(err, rows, fields) {
        if (err) throw err;
        //console.log('The solution is: ', rows[0].solution);
	    console.log("查询结果------->"+rows[0]);
		if(rows[0] == undefined){
			  conn.query("insert into reserve (personalname,companyname,num) value ('"+personalname+"','"+companyname+"','"+num+"')", function(err, rows, fields) {
              if (err) {
				  
	             //res.writeHead(200, {'Content-Type': 'text/plain'}); 
				 console.log(err);
				 res.header('Content-Type', 'text/plain');
	 			 res.send(JSON.stringify({ result:'fail' }));
	            
	              res.end();
			  }else{
				  console.log("sucess");
	             //res.writeHead(200, {'Content-Type': 'text/plain'}); 
	             //res.write(JSON.stringify("Sucess"));
				 res.header('Content-Type', 'text/plain');
	 			 res.send(JSON.stringify({ result:'OK' }));
	              res.end();
			  }
              //console.log('The solution is: ', rows[0].solution);
	           
   });
		}else{
			//res.writeHead(200, {'Content-Type': 'text/plain'}); 
			 console.log("Message : This User is existe");
			 res.header('Content-Type', 'text/plain');
	 		 res.send(JSON.stringify({ result:'existe' }));
	         res.end();
		}
	   // res.writeHead(200, {'Content-Type': 'text/plain'}); 
	   // res.write(JSON.stringify(rows));
	    //res.end();
   });
	
	};

//房间预约
exports.reserve2=function(req,res){
	var personalname = req.body.personalname;
	var companyname = req.body.companyname;
	var type="2";
	var num=req.body.num;
	console.log(personalname+" "+companyname+" "+num);
	
	conn=mysql.createConnection(conn.config);  
    conn.connect();
	
	
	conn.query("SELECT * from reserve where personalname='"+personalname+"' and companyname='"+companyname+"'", function(err, rows, fields) {
        if (err) throw err;
        //console.log('The solution is: ', rows[0].solution);
	    console.log("查询结果------->"+rows[0]);
		if(rows[0] == undefined){
			  conn.query("insert into reserve (personalname,companyname,type,num) value ('"+personalname+"','"+companyname+"','"+type+"','"+num+"')", function(err, rows, fields) {
              if (err) {
				  
	             //res.writeHead(200, {'Content-Type': 'text/plain'}); 
				 console.log(err);
				 res.header('Content-Type', 'text/plain');
	 			 res.send(JSON.stringify({ result:'fail' }));
	              res.end();
			  }else{
				  console.log("sucess");
	             //res.writeHead(200, {'Content-Type': 'text/plain'}); 
	             //res.write(JSON.stringify("Sucess"));
				 res.header('Content-Type', 'text/plain');
	 			 res.send(JSON.stringify({ result:'OK' }));
				
	              res.end();
			  }
              //console.log('The solution is: ', rows[0].solution);
	           
   });
		}else{
			//res.writeHead(200, {'Content-Type': 'text/plain'}); 
			 console.log("Message : This User is existe");
			 res.header('Content-Type', 'text/plain');
	 		 res.send(JSON.stringify({ result:'existe' }));
	        
	         res.end();
		}
	   // res.writeHead(200, {'Content-Type': 'text/plain'}); 
	   // res.write(JSON.stringify(rows));
	    //res.end();
   });
   
	};





